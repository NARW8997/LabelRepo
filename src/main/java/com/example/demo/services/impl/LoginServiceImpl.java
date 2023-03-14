package com.example.demo.services.impl;

import com.example.demo.domain.Label;
import com.example.demo.domain.LoginQuery;
import com.example.demo.domain.User;
import com.example.demo.domain.UserWithLabels;
import com.example.demo.mapper.LabelMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.services.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public UserWithLabels login(LoginQuery query) {
        User found = userMapper.getByUsernameAndPassword(query.getUsername(), query.getPassword());
        UserWithLabels res = null;
        if (found != null) {
            List<Label> labelList =labelMapper.selectLabelsByUserId(found.getId());
            res = new UserWithLabels(found, labelList);
        }
        return res;
    }
}
