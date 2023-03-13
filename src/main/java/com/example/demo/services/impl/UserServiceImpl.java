package com.example.demo.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Label;
import com.example.demo.domain.User;
import com.example.demo.domain.UserLabel;
import com.example.demo.domain.UserWithLabels;
import com.example.demo.mapper.LabelMapper;
import com.example.demo.mapper.UserLabelMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.services.IUserLabelService;
import com.example.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements IUserService {
    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private UserLabelMapper userLabelMapper;


    @Override
    public UserWithLabels getUserWithLabels(Integer uid) {
        List<Label> labelList =labelMapper.selectLabelsByUserId(uid);
        User user = getById(uid);
        return new UserWithLabels(user, labelList);
    }

    @Override
    public Boolean insertLabelWithUserId(Integer uid, String labelName) {
        // create the new label
        Label label = new Label();
        label.setName(labelName);

        // insert into label db
        int lRes = labelMapper.insert(label);

        // create user label obj
        UserLabel userLabel = new UserLabel();
        userLabel.setUserId(uid);
        userLabel.setLabelId(label.getId());

        // insert into user label db
        int ulRes = userLabelMapper.insert(userLabel);

        return lRes > 0 && ulRes > 0;
    }

    @Override
    public Boolean removeLabel(Integer uid, Integer lid) {
        int lRes = labelMapper.deleteById(lid);
        Integer ulRes = userLabelMapper.deleteByUserIdAndLabelId(uid, lid);
        return lRes > 0 && ulRes > 0;
    }

}
