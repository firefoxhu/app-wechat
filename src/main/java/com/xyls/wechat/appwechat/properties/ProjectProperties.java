package com.xyls.wechat.appwechat.properties;

import com.xyls.wechat.appwechat.config.FileProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xyls.wechat")
@Data
public class ProjectProperties {

    /**
     * 文件上传服务配置
     */
    private FileProperties file = new FileProperties();

}
