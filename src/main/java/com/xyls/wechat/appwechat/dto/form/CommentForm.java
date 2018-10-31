package com.xyls.wechat.appwechat.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor


@NoArgsConstructor
public class CommentForm {

    @NotEmpty(message = "文章id参数非法")
    private String id;

    @NotEmpty(message = "请输入评论内容")
    @Length(min = 3, max = 60, message = "评论长度必须在3到60个字符之间")
    private String content;

}
