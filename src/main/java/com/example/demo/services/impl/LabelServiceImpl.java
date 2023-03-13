package com.example.demo.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Label;
import com.example.demo.mapper.LabelMapper;
import com.example.demo.services.ILabelService;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label>
        implements ILabelService {

}
