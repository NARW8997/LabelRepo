package com.example.demo.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.User;
import com.example.demo.domain.UserWithLabels;

import java.io.Serializable;

public interface IUserService extends IService<User> {
    UserWithLabels getUserWithLabels(Integer uid);

    Boolean insertLabelWithUserId(Integer uid, String labelName);

    Boolean removeLabel(Integer uid, Integer lid);

}
