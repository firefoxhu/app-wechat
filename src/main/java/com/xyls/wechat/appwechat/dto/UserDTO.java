package com.xyls.wechat.appwechat.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO  implements Serializable {

    private String name;

    private String phone;
}
