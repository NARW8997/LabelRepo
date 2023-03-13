package com.example.demo.controllers;

import com.example.demo.controllers.utils.R;
import com.example.demo.domain.UserLabel;
import com.example.demo.services.IUserLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/find/user/label")
public class UserLabelControlelr {
    @Autowired
    private IUserLabelService userLabelService;

    @PostMapping
    public R insert(@RequestBody UserLabel userLabel) {
        userLabelService.save(userLabel);
        return new R(true, "user-label saved!");
    }

}
