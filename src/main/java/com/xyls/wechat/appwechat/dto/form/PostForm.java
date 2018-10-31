package com.xyls.wechat.appwechat.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {

    private String pid;

    @NotEmpty(message = "请输入您要发布的信息")
    @Length(min = 5, max = 256, message = "信息长度必须在5到256个字之间")
    private String content;

    private String pic;

    @NotEmpty(message = "请输入您的联系方式如QQ、微信、手机号等...")
    private String concat;

    private String ip;

    private String top;

    private String status;
}
