package com.xyls.wechat.appwechat.util;

import java.util.UUID;

public class GenKeyUtil {
    public static String key(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
