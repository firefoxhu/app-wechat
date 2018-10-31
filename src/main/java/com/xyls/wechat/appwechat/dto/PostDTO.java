package com.xyls.wechat.appwechat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @link Post
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private String pid;

    private String content;

    private String concat;

    private String[] pic;

    private String top;

    private String createTime;

}
