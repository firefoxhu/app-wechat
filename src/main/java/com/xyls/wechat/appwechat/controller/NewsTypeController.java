package com.xyls.wechat.appwechat.controller;

import com.xyls.wechat.appwechat.dto.support.ServerResponse;
import com.xyls.wechat.appwechat.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/category")
public class NewsTypeController {

    @Autowired
    private NewsTypeService newsTypeService;

    @GetMapping
    public ServerResponse category(@PageableDefault(size = 14) Pageable page,String classId){
        try{
            return ServerResponse.success(newsTypeService.findCategory(page,classId));
        }catch (Exception e){
            return ServerResponse.failure(e.getMessage());
        }
    }
}
