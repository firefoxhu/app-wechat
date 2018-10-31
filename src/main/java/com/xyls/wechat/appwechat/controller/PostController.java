package com.xyls.wechat.appwechat.controller;

import com.xyls.wechat.appwechat.dto.form.PostForm;
import com.xyls.wechat.appwechat.dto.support.ServerResponse;
import com.xyls.wechat.appwechat.service.PostService;
import com.xyls.wechat.appwechat.util.ValidateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/post/")
@Slf4j
public class PostController {


    @Autowired
    private PostService postService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping("list")
    public ServerResponse list(@PageableDefault(size = 8, sort = {"top", "createTime"}, direction = Sort.Direction.DESC) Pageable page) {

        if (page.getPageSize() > 8) {
            return ServerResponse.failure("参数非法");
        }
        try {
            return ServerResponse.success(postService.findAll(page));
        } catch (Exception e) {
            return ServerResponse.failure(e.getMessage());
        }
    }


    @PostMapping("send")
    public ServerResponse send(@RequestBody @Valid PostForm postForm, HttpServletRequest request, BindingResult result) {

        try {
            ValidateForm.validate(result, messageSource);
            postForm.setIp(request.getRemoteHost());
            return ServerResponse.success(postService.sendPost(postForm, request));
        } catch (Exception e) {
            return ServerResponse.failure(e.getMessage());
        }
    }

}
