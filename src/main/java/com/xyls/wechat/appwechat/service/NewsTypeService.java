package com.xyls.wechat.appwechat.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface NewsTypeService {

    Map<String,Object> findCategory(Pageable pageable,String classId);
}
