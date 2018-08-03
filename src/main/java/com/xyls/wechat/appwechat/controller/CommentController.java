package com.xyls.wechat.appwechat.controller;


import com.xyls.wechat.appwechat.dto.form.CommentForm;
import com.xyls.wechat.appwechat.dto.support.ServerResponse;
import com.xyls.wechat.appwechat.service.CommentService;
import com.xyls.wechat.appwechat.util.ValidateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/comment/")
@Slf4j
public class CommentController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping("list")
    public ServerResponse list(@PageableDefault(size = 8) Pageable page,String id){

        if(id == null){
            return ServerResponse.failure("参数非法");
        }
        try{
            return ServerResponse.success(commentService.query(page,id));
        }catch (Exception e){
            return ServerResponse.failure(e.getMessage());
        }
    }


    @PostMapping("article")
    public ServerResponse article(@RequestBody @Valid CommentForm commentForm, HttpServletRequest request, BindingResult result){

        try{
            ValidateForm.validate(result,messageSource);
            return ServerResponse.success(commentService.comment(request,commentForm));
        }catch (Exception e){
            return ServerResponse.failure(e.getMessage());
        }
    }



}
