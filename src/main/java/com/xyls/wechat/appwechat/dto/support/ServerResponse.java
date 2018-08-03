package com.xyls.wechat.appwechat.dto.support;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
@Data
@AllArgsConstructor
public class ServerResponse<T> implements Serializable {

    private int  code;
    private String msg;
    private T data;

    public static <T> ServerResponse success(T data){
       return new ServerResponse<>(0,"处理成功！",data);
    }

    public static  ServerResponse failure(String message){
        return new ServerResponse<>(-1,message,null);
    }


}

