package com.xyls.wechat.appwechat.service.impl;
import com.xyls.wechat.appwechat.consts.MapKeyConst;
import com.xyls.wechat.appwechat.dto.NewsTypeDTO;
import com.xyls.wechat.appwechat.model.NewsType;
import com.xyls.wechat.appwechat.properties.ProjectProperties;
import com.xyls.wechat.appwechat.repository.NewsTypeRepository;
import com.xyls.wechat.appwechat.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsTypeServiceImpl implements NewsTypeService{

    @Autowired
    private NewsTypeRepository newsTypeRepository;


    @Autowired
    private ProjectProperties projectProperties;


    @Override
    public Map<String, Object> findCategory(Pageable pageable,String classId) {

        Specification<NewsType>  specification= (root, criteriaQuery, criteriaBuilder)-> {
            Path<String> _classId= root.get("newsTypeParentId");
            Predicate _key = criteriaBuilder.equal(_classId,classId);
            return criteriaBuilder.and(_key);
        };

        Page<NewsType> newsTypes=newsTypeRepository.findAll(specification,pageable);

        Map<String,Object> result = new HashMap<>();

        result.put(MapKeyConst.HAS_NEXT,newsTypes.hasNext());

        result.put(MapKeyConst.LIST,newsTypes.getContent().stream().map(e->new NewsTypeDTO(e.getNewsTypeId(),e.getNewsTypeName(),projectProperties.getFile().getUrlPrefix()+e.getNewsTypeThumbnail(),"0")).collect(Collectors.toList()));
        return result;
    }
}
