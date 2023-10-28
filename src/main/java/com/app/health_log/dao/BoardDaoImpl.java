package com.app.health_log.dao;

import com.app.health_log.dao.BoardDao;
import com.app.health_log.domain.BoardDto;
import com.app.health_log.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDaoImpl implements BoardDao {

    @Autowired
    private SqlSession session;
    private static String namespace = "com.app.health_log.dao.BoardDao.";

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    }
    @Override
    public int deleteAll() {
        return session.delete(namespace+"deleteAll");
    }


    @Override
    public int delete(Integer bno, String writer) throws Exception {
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace+"delete", map);
    }
    public int update(BoardDto boardDto) throws Exception {
        return session.update(namespace+"update", boardDto);
    } // int update(String statement, Object parameter)
    public  int updateViewCnt(BoardDto boardDto ) throws Exception{
        return session.update(namespace+"updateViewCnt",boardDto);
    }
    public String searchWriter(Integer bno) throws Exception {
        return session.selectOne(namespace + "searchWriter", bno);
    }




    @Override
    public int insert(BoardDto boardDto) throws Exception {
        return session.insert(namespace+"insert", boardDto);
    }

    @Override
    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    }

    @Override
    public BoardDto select(Integer bno) throws Exception {
        return session.selectOne(namespace+"select", bno);
    }

    @Override
    public List<BoardDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace+"selectPage", map);
    }




    @Override
    public int searchResultCnt(SearchCondition sc) throws Exception {
        System.out.println("sc in searchResultCnt() = " + sc);
        System.out.println("session = " + session);
        return session.selectOne(namespace+"searchResultCnt", sc);
    }
    @Override
    public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception {
        return session.selectList(namespace+"searchSelectPage", sc);
    }
}
