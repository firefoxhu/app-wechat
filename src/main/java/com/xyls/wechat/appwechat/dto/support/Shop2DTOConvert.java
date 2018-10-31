package com.xyls.wechat.appwechat.dto.support;

import com.xyls.wechat.appwechat.dto.ShopDTO;
import com.xyls.wechat.appwechat.model.Shop;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class Shop2DTOConvert {


    public static List<ShopDTO> shop2Dto(Page<Shop> page, String urlPrefix) {
        List<ShopDTO> shopDTOS = new ArrayList<>();
        if (page.getContent().size() > 0) {

            page.getContent().forEach(e -> {
                ShopDTO shopDTO = new ShopDTO();
                shopDTO.setId(e.getId());
                shopDTO.setShopName(e.getName());
                shopDTO.setGoOut(e.getGoOut());
                String[] origin = e.getPicture().split(",");
                String[] temp = new String[origin.length];
                for (int i = 0; i < origin.length; i++) {
                    temp[i] = urlPrefix + origin[i];
                }
                shopDTO.setPicture(temp);
                shopDTO.setTelephone(e.getTelephone());
                shopDTO.setAddress(e.getProvince() + "-" + e.getCity() + "-" + e.getArea());
                shopDTO.setStreet(e.getStreet());
                shopDTO.setX(e.getPositionX());
                shopDTO.setY(e.getPositionY());
                shopDTO.setUserId(e.getUserId());
                shopDTO.setDescription(e.getDescription());
                shopDTOS.add(shopDTO);
            });
        }
        return shopDTOS;
    }

    public static List<String> shopOwners(Page<Shop> page) {
        List<String> ids = new ArrayList<>();
        if (page.getContent().size() > 0) {
            page.getContent().forEach(e -> ids.add(e.getUserId()));
        }
        return ids;
    }

}
