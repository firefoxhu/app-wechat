package com.xyls.wechat.appwechat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {

    private String id;

    private String categoryId;

    private String title;

    private String abstracts;

    private String url;

    private String pic;

    private String playNumber;

    private String author;

    private String source;

    private String fabulous;

    private String createTime;


}
