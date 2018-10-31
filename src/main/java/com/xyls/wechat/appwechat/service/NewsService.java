package com.xyls.wechat.appwechat.service;

import com.xyls.wechat.appwechat.dto.NewsDTO;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface NewsService {

    /**
     * 热榜推荐
     *
     * @param page
     * @return
     */
    Map<String, Object> findRecommend(Pageable page);

    /**
     * 根据类别查找
     */
    Map<String, Object> findByCategory(String TypeId, Pageable pageable);


    /**
     * 用户浏览
     */
    void modifyViews(String id);


    /**
     * 分享查
     *
     * @param id
     * @return
     */
    NewsDTO findById(String id);

    /**
     * 用户点赞
     */

    void modifyFabulous(String id);


    void modifyFabulousAndViews();
}
