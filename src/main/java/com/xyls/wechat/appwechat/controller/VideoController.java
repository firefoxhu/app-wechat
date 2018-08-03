package com.xyls.wechat.appwechat.controller;

import com.xyls.wechat.appwechat.dto.support.ServerResponse;
import com.xyls.wechat.appwechat.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/video/")
@Slf4j
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("list")
    public ServerResponse list(@PageableDefault(size = 8) Pageable page,String categoryId){
        if(page.getPageSize() > 10){
            return ServerResponse.failure("参数非法");
        }

        try{
            return ServerResponse.success(videoService.findByCategory(categoryId,page));
        }catch (Exception e){
            return ServerResponse.failure(e.getMessage());
        }
    }

}
