package com.example.demo.controllers;

import com.example.demo.controllers.utils.R;
import com.example.demo.domain.User;
import com.example.demo.domain.UserWithLabels;
import com.example.demo.services.IUserLabelService;
import com.example.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping
    public R insert(@RequestBody User user) {
        boolean res = userService.save(user);
        if (res) {
            return new R(true, "You have successfully created an account!");

        }
        return new R(false, "You have failed to create an account!");
    }

    @DeleteMapping("{uid}")
    public R deleteById(@PathVariable Integer uid) {
        boolean res = userService.removeById(uid);
        return new R(res);
    }

    @PutMapping
    public R updateById(@RequestBody User user) {
        userService.updateById(user);
        return new R(true, "You have successfully updated the account!");
    }

    @GetMapping("{id}")
    public R getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return new R(true, user);
    }

    @GetMapping("getLabels/{id}")
    public R getUserLabelById(@PathVariable Integer id) {
        UserWithLabels userWithLabels = userService.getUserWithLabels(id);
        return new R(true, userWithLabels);
    }

    @PostMapping("insertLabel")
    public R insertLabelByUserId(@RequestBody Integer uid, String labelName) {
        Boolean res = userService.insertLabelWithUserId(uid, labelName);
        return new R(res);
    }

    @DeleteMapping("removeLabel/{uid}/{lid}")
    public R removeLabelByUserIdAndLabelId(@PathVariable Integer uid, @PathVariable Integer lid) {
        Boolean res = userService.removeLabel(uid, lid);
        if (res) {
            return new R(true, "You have removed a label!");
        }
        return new R(false, "Remove failed");
    }
}
