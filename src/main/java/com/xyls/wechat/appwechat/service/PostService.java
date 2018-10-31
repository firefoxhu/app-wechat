package com.xyls.wechat.appwechat.service;

import com.xyls.wechat.appwechat.dto.form.PostForm;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PostService {

    /**
     * 根据类别查找
     */
    Map<String, Object> findAll(Pageable pageable);


    /**
     * 发帖
     *
     * @return
     */
    PostForm sendPost(PostForm postForm, HttpServletRequest request);

    /**
     * 灌水检查
     */
    long todayCountByIp(String ip);

}
