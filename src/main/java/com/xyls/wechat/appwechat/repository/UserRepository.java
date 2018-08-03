package com.xyls.wechat.appwechat.repository;
import com.xyls.wechat.appwechat.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends XylsRepository<User> {

    User findByUserName(String username);

}
