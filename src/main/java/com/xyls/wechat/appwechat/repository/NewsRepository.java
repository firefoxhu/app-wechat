package com.xyls.wechat.appwechat.repository;

import com.xyls.wechat.appwechat.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends XylsRepository<News> {

    /**
     * 查询热点
     *
     * @param page
     * @return
     */
    @Query("select n from News n where n.newsIsTop = '1' and n.status = '1' order by n.createTime desc")
    Page<News> findRecommend(Pageable page);


    /**
     * 根据类型查找
     */
    @Query("select n from News n where n.newsTypeId=:typeId and n.status = '1' order by n.createTime desc")
    Page<News> findByNewsTypeId(@Param("typeId") String typeId, Pageable page);

    /**
     * 模糊查找
     */
    @Query("select n from News n where n.newsTitle like CONCAT('%',:keyWord,'%') and  n.status = '1' order by n.createTime desc")
    Page<News> findLikeByTitle(@Param("keyWord") String keyWord, Pageable page);


    @Modifying
    @Query("update News n set n.newsViews = :views where n.newsId = :id")
    void modifyViews(@Param("views") String views, @Param("id") String id);

    @Modifying
    @Query("update News n set n.fabulous= :fabulous where n.newsId = :id")
    void modifyFabulous(@Param("fabulous") String fabulous, @Param("id") String id);

    @Modifying
    @Query("update News n set n.fabulous= :fabulous,n.newsViews=:views where n.newsId = :id")
    void modifyFabulousAndViews(@Param("fabulous") String fabulous, @Param("views") String views, @Param("id") String id);


}
