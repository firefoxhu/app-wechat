package com.xyls.wechat.appwechat.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ShopService {


    Map<String,Object> findShop(Pageable pageable);


}
