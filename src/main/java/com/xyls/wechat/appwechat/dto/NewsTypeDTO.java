package com.xyls.wechat.appwechat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsTypeDTO implements Serializable {

    private String id;

    private String title;

    private String imgUrl;

    private String num;
}
