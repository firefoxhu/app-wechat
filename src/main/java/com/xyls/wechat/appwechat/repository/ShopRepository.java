package com.xyls.wechat.appwechat.repository;


import com.xyls.wechat.appwechat.model.Shop;

public interface ShopRepository extends XylsRepository<Shop> {

    Shop findByName(String name);
}
