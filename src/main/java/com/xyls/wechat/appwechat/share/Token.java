package com.xyls.wechat.appwechat.share;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Token implements Serializable {



    private String token;

    private String ticket;

    private int expiresIn;

    private LocalDateTime expireTime;

    public Token(int expireIn) {
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public Token(String token,int expireIn){
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
        this.token = token;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }



}
