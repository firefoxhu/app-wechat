package com.xyls.wechat.appwechat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@ToString
public class CommentDTO {

    private String id;

    private String commentator;

    private String content;

    private String createTime;

}
