package com.xyls.wechat.appwechat.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class NewsDTO implements Serializable {

    private String id;

    private String title;

    private String views;

    private String resource;

    private String time;

    private String[] images;

    private String content;

    private String author;

    private String showType;

    private String fabulous;
}
