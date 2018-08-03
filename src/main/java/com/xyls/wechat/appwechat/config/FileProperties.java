package com.xyls.wechat.appwechat.config;

import lombok.Data;

@Data
public class FileProperties {

    // 文件目标位置 最终存放位置
    private String fileDir;

    // 服务器的url www.baidu.com
    private String urlPrefix;

    // 上传文件零时目录
    private String fileTemp;


    // 上传文件的前缀
    private String folderPrefix;
}
