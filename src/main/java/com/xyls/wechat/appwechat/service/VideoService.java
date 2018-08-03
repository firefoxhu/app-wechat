package com.xyls.wechat.appwechat.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface VideoService {

    /**
     * 根据类别查找
     */
    Map<String,Object> findByCategory(String category, Pageable pageable);
}
