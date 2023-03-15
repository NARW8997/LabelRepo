package com.example.demo.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.User;
import com.example.demo.domain.UserWithLabels;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

public interface IUserService extends IService<User> {
    UserWithLabels getUserWithLabels(Integer uid);

    Boolean insertLabelWithUserId(Integer uid, String labelName);

    Boolean removeLabel(Integer uid, Integer lid);

//    void storeUserInSession(HttpSession session, Integer uid);
//
//    UserWithLabels getUserFromSession(HttpSession session);
    Boolean register(User user);
}
