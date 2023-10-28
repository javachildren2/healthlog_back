package com.app.health_log.service;

import com.app.health_log.dao.BoardDao;
import com.app.health_log.domain.BoardDto;
import com.app.health_log.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    @Autowired
    BoardDao boardDao;


    public int getCount() throws Exception {
        return boardDao.count();
    }


    public int remove(Integer bno, String writer) throws Exception {
        return boardDao.delete(bno, writer);
    }
    public int modify(BoardDto boardDto) throws Exception {
        return boardDao.update(boardDto);
    }
    public int upViewCnt(BoardDto boardDto) throws Exception{
        return boardDao.updateViewCnt(boardDto);
    }

    public int write(BoardDto boardDto) throws Exception {
        return boardDao.insert(boardDto);
    }

    public List<BoardDto> getList() throws Exception {
        return boardDao.selectAll();
    }


    public List<BoardDto> getPage(Map map) throws Exception {
        return boardDao.selectPage(map);
    }
    public String getBnoWriter(Integer bno) throws Exception{
        return boardDao.searchWriter(bno);
    }
    public BoardDto read(Integer bno) throws Exception {
        BoardDto boardDto = boardDao.select(bno);
        return boardDto;
    }
    public int getSearchResultCnt(SearchCondition sc) throws Exception {
        return boardDao.searchResultCnt(sc);
    }

    public List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception {
        return boardDao.searchSelectPage(sc);
    }
    public void deleteItem(Long itemId, String userId) {
        // userId를 사용하여 해당 항목을 삭제하는 로직 구현
        // 예를 들어, 데이터베이스에서 해당 항목을 조회하고 사용자 ID와 비교하여 권한을 확인한 후 삭제하는 방식을 사용할 수 있습니다.

    }

}
