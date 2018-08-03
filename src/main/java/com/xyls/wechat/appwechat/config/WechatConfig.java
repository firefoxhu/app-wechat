package com.xyls.wechat.appwechat.config;

import com.xyls.wechat.appwechat.properties.ProjectProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ProjectProperties.class)
public class WechatConfig {
}
