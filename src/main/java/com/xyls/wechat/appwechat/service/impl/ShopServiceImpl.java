package com.xyls.wechat.appwechat.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.xyls.wechat.appwechat.consts.MapKeyConst;
import com.xyls.wechat.appwechat.dto.ShopDTO;
import com.xyls.wechat.appwechat.dto.support.Shop2DTOConvert;
import com.xyls.wechat.appwechat.model.Shop;
import com.xyls.wechat.appwechat.model.User;
import com.xyls.wechat.appwechat.properties.ProjectProperties;
import com.xyls.wechat.appwechat.repository.ShopRepository;
import com.xyls.wechat.appwechat.repository.UserRepository;
import com.xyls.wechat.appwechat.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectProperties projectProperties;

    @Override
    public Map<String, Object> findShop(Pageable pageable) {


        Page<Shop> page = shopRepository.findAll(pageable);

        List<ShopDTO> shopDTOList = Shop2DTOConvert.shop2Dto(page, projectProperties.getFile().getUrlPrefix());

        if (shopDTOList.size() > 0) {
            List<User> users = userRepository.findAllById(Shop2DTOConvert.shopOwners(page));

            for (ShopDTO shopDTO : shopDTOList) {
                for (User user : users) {
                    if (StringUtils.equals(shopDTO.getUserId(), user.getUserId())) {
                        shopDTO.setUserName(user.getUserNickName());
                        shopDTO.setPhone(user.getPhone());
                        break;
                    }
                }
            }

        }

        Map<String, Object> result = new HashMap<>();

        result.put(MapKeyConst.LIST, shopDTOList);

        result.put(MapKeyConst.HAS_NEXT, page.hasNext());

        return result;
    }
}
