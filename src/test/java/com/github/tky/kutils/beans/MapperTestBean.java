package com.github.tky.kutils.beans;

import java.sql.Date;

public class MapperTestBean {

    private Long id ;
    private int optimistic ;
    private String username ;
    private String password ;
    private String phoneNo ;
    private Date createTime ;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getOptimistic() {
        return optimistic;
    }
    public void setOptimistic(int optimistic) {
        this.optimistic = optimistic;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
