package com.app.health_log.domain;
import java.util.Date;
import java.util.Objects;

public class BoardDto {
    private Integer bno;
    private String  title;
    private String  content;
    private String  writer;
    private Date reg_date;
    private Integer view_cnt;

    public BoardDto(Integer bno, String title, String content, String writer, Date reg_date, Integer view_cnt) {
        this.bno = bno;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.reg_date = reg_date;
        this.view_cnt = view_cnt;
        }

    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public Integer getView_cnt() {
        return view_cnt;
    }

    public void setView_cnt(Integer view_cnt) {
        this.view_cnt = view_cnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardDto boardDto = (BoardDto) o;
        return Objects.equals(bno, boardDto.bno) && Objects.equals(title, boardDto.title) && Objects.equals(content, boardDto.content) && Objects.equals(writer, boardDto.writer) && Objects.equals(view_cnt, boardDto.view_cnt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bno, title, content, writer, view_cnt);
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "bno=" + bno +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", reg_date=" + reg_date +
                ", view_cnt=" + view_cnt +
                '}';
    }
}
