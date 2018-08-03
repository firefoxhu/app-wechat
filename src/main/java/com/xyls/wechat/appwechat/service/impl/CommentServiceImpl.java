package com.xyls.wechat.appwechat.service.impl;
import com.xyls.wechat.appwechat.dto.CommentDTO;
import com.xyls.wechat.appwechat.dto.form.CommentForm;
import com.xyls.wechat.appwechat.model.Comment;
import com.xyls.wechat.appwechat.repository.CommentRepository;
import com.xyls.wechat.appwechat.service.CommentService;
import com.xyls.wechat.appwechat.util.DateUtil;
import com.xyls.wechat.appwechat.util.GenKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Map<String,Object> query(Pageable page, String id) {
        page.getSortOr(new Sort(Sort.Direction.ASC,"createTime"));

        Page<Comment> pageResult = commentRepository.findAll((root,query,criteriaBuilder)->{
            Path<String> _newsId = root.get("newsId");
            Predicate _key = criteriaBuilder.equal(_newsId,id);
            return criteriaBuilder.and(_key);
        },page);

        Map<String,Object> result = new HashMap();

        result.put("hasNext", pageResult.hasNext());

        result.put("list",pageResult.stream()
                .map(e->new CommentDTO(e.getId(),e.getCommentator(),e.getContent(),e.getCreateTime()))
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    public CommentDTO comment(HttpServletRequest request,CommentForm commentForm) {
        String ip = request.getRemoteAddr();
        Comment comment = commentRepository.save(new Comment(GenKeyUtil.key(),"用户"+ip,commentForm.getId(),commentForm.getContent(),ip, DateUtil.todayDateTime()));
        return new CommentDTO(comment.getId(),comment.getCommentator(),comment.getContent(),comment.getCreateTime());
    }
}
