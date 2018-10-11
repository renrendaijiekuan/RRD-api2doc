package com.renrendai.loan.beetle.demo;

import com.renrendai.loan.beetle.commons.api2doc.annotations.ApiComment;

/**
 * Created by wudi on 2018/10/10
 */
public class User {
    @ApiComment("用户ID")
    private long uid;
    @ApiComment("用户名称")
    private String name;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
