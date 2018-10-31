package com.xyls.wechat.appwechat.repository;

import com.xyls.wechat.appwechat.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoRepository extends XylsRepository<Video> {

    /**
     * 根据类型查找
     */
    @Query("select n from Video n where n.categoryId=:category and n.status = '1' order by n.createTime desc")
    Page<Video> findByVideoTypeId(@Param("category") String category, Pageable page);
}
