package com.example.demo.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Label;
import com.example.demo.domain.UserLabel;
import com.example.demo.mapper.LabelMapper;
import com.example.demo.mapper.UserLabelMapper;
import com.example.demo.services.ILabelService;
import com.example.demo.services.IUserLabelService;
import org.springframework.stereotype.Service;

@Service
public class UserLabelServiceImpl extends ServiceImpl<UserLabelMapper, UserLabel>
        implements IUserLabelService {

}
