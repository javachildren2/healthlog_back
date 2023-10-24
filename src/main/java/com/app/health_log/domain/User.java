package com.app.health_log.domain;

import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.Objects;

@Data
@Alias("User")
@NoArgsConstructor
public class User {
    private String id;
    private String user_id;
    private String pwd;
    private Date reg_date;


    public User(String id, String user_id, String pwd, Date reg_date) {
        this.id = id;
        this.user_id = user_id;
        this.pwd = pwd;
        this.reg_date = reg_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(user_id, user.user_id) && Objects.equals(pwd, user.pwd) && Objects.equals(reg_date, user.reg_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, pwd, reg_date);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }


}