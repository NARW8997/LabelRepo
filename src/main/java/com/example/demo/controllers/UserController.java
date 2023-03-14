package com.example.demo.controllers;

import com.example.demo.controllers.utils.R;
import com.example.demo.domain.Label;
import com.example.demo.domain.LoginQuery;
import com.example.demo.domain.User;
import com.example.demo.domain.UserWithLabels;
import com.example.demo.services.ILabelService;
import com.example.demo.services.ILoginService;
import com.example.demo.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("user/")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private ILabelService labelService;

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

    @GetMapping("insertLabel/{labelName}")
    public R insertLabelByUser(@PathVariable String labelName, HttpSession session) {
        if (checkSessionWithUserLabels(session)) {
            return new R(false, "Session Error!");
        }
        UserWithLabels userWithLabels = (UserWithLabels) session.getAttribute("userWithLabels");
        int uid = userWithLabels.getUser().getId();
        Boolean res = userService.insertLabelWithUserId(uid, labelName);
        if (res) {
            setAttribute(session, userService.getUserWithLabels(uid));
            return new R(true, "Insert Success!");
        }
        return new R(false, "Insert Failed!");
    }

    @DeleteMapping("removeLabel/{lid}")
    public R removeLabelByUserAndLabelId(@PathVariable Integer lid, HttpSession session) {
        if (checkSessionWithUserLabels(session)) {
            return new R(false, "Session Error!");
        }
        UserWithLabels userWithLabels = (UserWithLabels) session.getAttribute("userWithLabels");
        int uid = userWithLabels.getUser().getId();
        Boolean res = userService.removeLabel(uid, lid);
        if (res) {
            setAttribute(session, userService.getUserWithLabels(uid));
            return new R(true, "You have removed a label!");
        }
        return new R(false, "Remove failed");
    }

    @PutMapping("updateLabel")
    public R updateLabelByUser(@RequestBody Label label, HttpSession session) {
        if (checkSessionWithUserLabels(session)) {
            return new R(false, "Session Error!");
        }
        UserWithLabels userWithLabels = (UserWithLabels) session.getAttribute("userWithLabels");
        int uid = userWithLabels.getUser().getId();
        boolean res = labelService.updateById(label);
        if (res) {
            setAttribute(session, userService.getUserWithLabels(uid));
            return new R(true, "You have updated a label!");
        }
        return new R(false, "Remove failed");
    }

    @GetMapping("/displayLabel")
    public R displayLabels(HttpSession session, Model model) {
        if (checkSessionWithUserLabels(session)) {
            return new R(false, "Session Error!");
        }
        UserWithLabels userWithLabels = (UserWithLabels) session.getAttribute("userWithLabels");
        if (userWithLabels != null) {
            List<Label> labels = userWithLabels.getLabelList();
            model.addAttribute("user", userWithLabels.getUser());
            model.addAttribute("labels", labels);
            return new R(true, userWithLabels);
        }
        return new R(false);
    }

    @PostMapping("login")
    public R userLogin(@RequestBody LoginQuery query, HttpSession session) {
        log.info("dashboard login username#{} password#{}", query.getUsername(), query.getPassword());
        UserWithLabels userWithLabels = loginService.login(query);
        if (userWithLabels == null) {
            return new R(false, "Username Or Password Wrong!");
        }
        removeAttribute(session);
        setAttribute(session, userWithLabels);
        return new R(true, "Login Success!");
    }

    private void setAttribute(HttpSession session, UserWithLabels userWithLabels) {
        User user = userWithLabels.getUser();
        if (session == null) {
            log.warn("session is null, username#{}, uid#{}", user.getUsername(), user.getId());
        } else {
            session.setAttribute("userWithLabels", userWithLabels);
        }
    }


    @PostMapping("/logout")
    public R logout(HttpSession session) {
        removeAttribute(session);
        return new R(true, "session removed!");
    }

    private void removeAttribute(HttpSession session) {
        if (session != null) {
            session.removeAttribute("userWithLabels");
        }
    }

    private boolean checkSessionWithUserLabels(HttpSession session) {
        return session == null || session.getAttribute("userWithLabels") == null;
    }
}
