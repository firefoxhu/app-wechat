package com.xyls.wechat.appwechat.service.impl;

import com.xyls.wechat.appwechat.consts.MapKeyConst;
import com.xyls.wechat.appwechat.dto.support.News2DTOConvert;
import com.xyls.wechat.appwechat.model.News;
import com.xyls.wechat.appwechat.properties.ProjectProperties;
import com.xyls.wechat.appwechat.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchProcessor{

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ProjectProperties projectProperties;

    public Map<String,Object> search(String keyWord,Pageable pageable){
        Page<News> page = newsRepository.findLikeByTitle(keyWord,pageable);
        Map<String,Object> result =new HashMap<>();

        result.put(MapKeyConst.HAS_NEXT,page.hasNext());

        result.put(MapKeyConst.LIST, News2DTOConvert.news2Dto(page,projectProperties.getFile().getUrlPrefix()));

        return result;
    }


}
