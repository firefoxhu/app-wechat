package com.xyls.wechat.appwechat.share;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class ShareController {

    @Autowired
    private WxConfig wxConfig;

    @GetMapping("/config")
    public Map<String, Object> share(HttpServletRequest request, String url) {

        return wxConfig.getSignature("wx15abe4c8d13d35b0", "17dc93aead2dce3378be49989d244ed2", url);
    }

}
