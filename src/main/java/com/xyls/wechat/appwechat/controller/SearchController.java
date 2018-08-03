package com.xyls.wechat.appwechat.controller;

import com.xyls.wechat.appwechat.dto.support.ServerResponse;
import com.xyls.wechat.appwechat.service.impl.SearchProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class SearchController {


    @Autowired
    private SearchProcessor searchProcessor;

    @GetMapping("search")
    public ServerResponse search(@PageableDefault(size = 8) Pageable page, @RequestParam("keyWord") String key){

        try{
            return ServerResponse.success(searchProcessor.search(key,page));
        }catch (Exception e){
            return ServerResponse.failure(e.getMessage());
        }

    }

}
