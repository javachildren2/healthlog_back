package com.app.health_log.controller;

import com.app.health_log.dao.BoardDao;

import com.app.health_log.domain.BoardDto;
import com.app.health_log.domain.SearchCondition;
import com.app.health_log.service.BoardService;
import com.app.health_log.service.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/board")
public class BoardController {

    @Value("${jwt.secret}") // application.properties 또는 application.yml에서 jwt.secret 값을 가져옵니다.
    private String jwtSecret;
    private final BoardService boardService;
    private final SecurityService securityService;
    private final BoardDao boardDao;

    public BoardController(BoardService boardService, SecurityService securityService, BoardDao boardDao) {
        this.boardService = boardService;
        this.securityService = securityService;
        this.boardDao = boardDao;
    }

    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody BoardDto boardDto, HttpServletRequest request) {
        String token = extractTokenFromHeader(request.getHeader("Authorization"));

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다");
        }

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다");
        }

        String writer = claims.getSubject();
        boardDto.setWriter(writer);
        try {
            int result = boardService.write(boardDto);
            if (result == 1) {
                return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 게시글이 작성되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 작성에 실패했습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 작성 중 오류가 발생하였습니다.");
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<Map<String, String>> remove(@RequestParam Integer bno, @RequestHeader("Authorization") String jwtToken) {
        Map<String, String> response = new HashMap<>();
        String writer = getCurrentUser(jwtToken);

        if (writer != null) {
            try {
                int rowCnt = boardService.remove(bno, writer);
                if (rowCnt != 1) {
                    throw new Exception("삭제에 실패했습니다");
                }
                response.put("status", "삭제 되었습니다.");
            } catch (Exception e) {
                e.printStackTrace();
                response.put("status", "삭제 시도 중 오류가 발생했습니다.");
                response.put("error_message", e.getMessage());
            }
        } else {
            response.put("status", "권한이 없습니다.");
        }

        return ResponseEntity.ok(response);
    }
    @GetMapping("/read/{bno}")
    public ResponseEntity<BoardDto> read(@PathVariable Integer bno) {

        try {
            BoardDto boardDto = boardService.read(bno);

            if (boardDto != null) {
               boardDto.setView_cnt(boardDto.getView_cnt() + 1);

//                int updatedRows = boardDao.updateViewCnt(boardDto);
                int updatedRows = boardService.upViewCnt(boardDto);
                if (updatedRows == 1) {
                    return ResponseEntity.ok(boardDto);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<BoardDto>> getAllBoardData() {
        try {
            List<BoardDto> boardData = boardService.getList(); // selectAll 메서드를 호출하여 모든 게시물 데이터를 가져옵니다.
            return ResponseEntity.ok(boardData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/modify")
    public ResponseEntity<String> modify(@RequestBody BoardDto boardDto, @RequestHeader("Authorization") String jwtToken) throws Exception {
        String writer = getCurrentUser(jwtToken);
        if (writer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }

        if (!writer.equals(boardService.getBnoWriter(boardDto.getBno()))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("금지된 접근입니다.");
        }

        try {
            int result = boardService.modify(boardDto);

            if (result == 1) {
                return ResponseEntity.status(HttpStatus.OK).body("게시물이 수정되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 수정 중 오류가발생했습니다.");
        }
    }@GetMapping("/scp")
    public ResponseEntity<Map<String, Object>> getBoardPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        try {
            int offset = (page - 1) * pageSize;

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("offset", offset);
            paramMap.put("pageSize", pageSize);

            List<BoardDto> boardPage = boardService.getPage(paramMap);


            int totalCnt = boardDao.count();

            Map<String, Object> result = new HashMap<>();
            result.put("boardPage", boardPage);
            result.put("totalCnt", totalCnt);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/searchPage")
    public ResponseEntity<Map<String, Object>> getSearchResultPage(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam(value = "option", required = false, defaultValue = "T") String option,
            @RequestParam("keyword") String keyword
    ) {
        try {
            int offset = (page - 1) * pageSize;

            SearchCondition searchCondition = new SearchCondition();
            searchCondition.setKeyword(keyword);
            searchCondition.setOption(option);
            searchCondition.setOffset(offset);
            searchCondition.setPageSize(pageSize);

            List<BoardDto> boardPage = boardService.getSearchResultPage(searchCondition);
            int totalCnt = boardService.getSearchResultCnt(searchCondition);

            Map<String, Object> resultSearch = new HashMap<>();
            resultSearch.put("boardPage", boardPage);
            resultSearch.put("totalCnt", totalCnt);

            return ResponseEntity.ok(resultSearch);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }













//여기 밑에는 메서드




    private String extractTokenFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // "Bearer " 다음의 문자열이 토큰입니다.
        }
        return null;
    }
    private String getCurrentUser(String jwtToken) {
           try {
            Claims claims = Jwts.parser()
                    .setSigningKey(securityService.generateSecretKey()) // 시크릿 키 설정
                    .parseClaimsJws(jwtToken.substring(7))
                    .getBody();

            // JWT에서 사용자 ID를 추출
            String user_Id = claims.getSubject();
            
            return user_Id;
        } catch (Exception e) {
            return null;
        }
    }


}