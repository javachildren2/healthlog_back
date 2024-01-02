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

}
