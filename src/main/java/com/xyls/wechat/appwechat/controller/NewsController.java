package com.xyls.wechat.appwechat.controller;

import com.xyls.wechat.appwechat.dto.support.ServerResponse;
import com.xyls.wechat.appwechat.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/news/")
@Slf4j
public class NewsController {

    @Autowired
    private NewsService newsService;


    @GetMapping("recommend")
    public ServerResponse recommend(@PageableDefault(size = 8) Pageable page) {

        if (page.getPageSize() > 10) {
            return ServerResponse.failure("参数非法");
        }

        try {
            return ServerResponse.success(newsService.findRecommend(page));
        } catch (Exception e) {
            return ServerResponse.failure(e.getMessage());
        }
    }

    @GetMapping("category")
    public ServerResponse list(@PageableDefault(size = 8) Pageable page, @RequestParam("typeId") String id) {

        if (page.getPageSize() > 10) {
            return ServerResponse.failure("参数非法");
        }

        try {
            return ServerResponse.success(newsService.findByCategory(id, page));
        } catch (Exception e) {
            return ServerResponse.failure(e.getMessage());
        }

    }


    @GetMapping("fabulous")
    public ServerResponse fabulous(String id) {

        if (id == null) {
            return ServerResponse.failure("参数非法");
        }
        try {
            newsService.modifyFabulous(id);
            return ServerResponse.success("ok");
        } catch (Exception e) {
            return ServerResponse.failure(e.getMessage());
        }
    }

    @GetMapping("views")
    public ServerResponse views(String id) {

        if (id == null) {
            return ServerResponse.failure("参数非法");
        }
        try {
            newsService.modifyViews(id);
            return ServerResponse.success("ok");
        } catch (Exception e) {
            return ServerResponse.failure(e.getMessage());
        }
    }


    @GetMapping("detail")
    public ServerResponse detail(String id) {
        try {
            return ServerResponse.success(newsService.findById(id));
        } catch (Exception e) {
            return ServerResponse.failure(e.getMessage());
        }
    }

    /**
     * 批量灌水
     * @return

     @GetMapping("random") public ServerResponse manager() {
     try{
     newsService.modifyFabulousAndViews();
     return ServerResponse.success("ok");
     }catch (Exception e){
     return ServerResponse.failure(e.getMessage());
     }
     }
     */

}
