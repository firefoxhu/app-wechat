package com.xyls.wechat.appwechat.repository;

import com.xyls.wechat.appwechat.model.Post;

public interface PostRepository extends XylsRepository<Post> {

    long countPostByCreateDateAndIp(String todayDate, String ip);
}
