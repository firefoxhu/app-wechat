package com.xyls.wechat.appwechat.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopDTO implements Serializable {

    private String id;

    private String shopName;

    private String[] picture;

    private String address;

    private String street;

    private String goOut;

    private String telephone;

    private String description;

    private String x;

    private String y;

    private String userId;

    private String userName;

    private String phone;

}
