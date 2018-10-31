package com.xyls.wechat.appwechat.service.impl;

import com.xyls.wechat.appwechat.consts.MapKeyConst;
import com.xyls.wechat.appwechat.dto.VideoDTO;
import com.xyls.wechat.appwechat.model.Video;
import com.xyls.wechat.appwechat.properties.ProjectProperties;
import com.xyls.wechat.appwechat.repository.VideoRepository;
import com.xyls.wechat.appwechat.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private ProjectProperties projectProperties;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public Map<String, Object> findByCategory(String category, Pageable pageable) {

        Map<String, Object> result = new HashMap<>();
        Page<Video> page = videoRepository.findByVideoTypeId(category, pageable);
        result.put(MapKeyConst.HAS_NEXT, page.hasNext());
        result.put(MapKeyConst.LIST, page.getContent().stream().map(e -> {
                    VideoDTO videoDTO = new VideoDTO();
                    BeanUtils.copyProperties(e, videoDTO);
                    videoDTO.setPic(projectProperties.getFile().getUrlPrefix() + e.getPic());
                    videoDTO.setUrl(projectProperties.getFile().getUrlPrefix() + e.getUrl());
                    return videoDTO;
                })
        );
        return result;
    }
}
