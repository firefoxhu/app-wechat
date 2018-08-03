package com.xyls.wechat.appwechat.service;
import com.xyls.wechat.appwechat.dto.CommentDTO;
import com.xyls.wechat.appwechat.dto.form.CommentForm;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CommentService {

    /**
     *
     * @param page 分页获取评论
     * @param id 传入文章的唯一标识id
     * @return 返回当前文章的所有评论
     */
    Map<String,Object> query(Pageable page, String id);

    /**
     *
     * @param request 请求对象 获取请求的ip地址等信息
     * @param commentForm 传入文章的唯一标识id 文章评论信息
     */
    CommentDTO comment(HttpServletRequest request, CommentForm commentForm);


}
