package com.example.demo.controllers;

import com.example.demo.controllers.utils.R;
import com.example.demo.domain.Label;
import com.example.demo.domain.LoginQuery;
import com.example.demo.domain.User;
import com.example.demo.domain.UserWithLabels;
import com.example.demo.services.ILabelService;
import com.example.demo.services.ILoginService;
import com.example.demo.services.IUserService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Transactional
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

    @PostMapping("insertLabel")
    public ModelAndView insertLabelByUser(@RequestParam String labelName, HttpSession session) {
        log.info("label name {}", labelName);
        ModelAndView mv = new ModelAndView();
        if (checkSessionWithUserLabels(session)) {
            mv.setViewName("redirect:/addEmp");
            return mv;
        }
        UserWithLabels userWithLabels = (UserWithLabels) session.getAttribute("userWithLabels");
        int uid = userWithLabels.getUser().getId();
        Boolean res = userService.insertLabelWithUserId(uid, labelName);
        if (res) {
            setAttribute(session, userService.getUserWithLabels(uid));
            mv.setViewName("redirect:/home");
            return mv;
        }
        mv.setViewName("redirect:/addEmp");
        return mv;
    }

    @GetMapping("removeLabel")
    public ModelAndView removeLabelByUserAndLabelId(@RequestParam Integer lid, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (checkSessionWithUserLabels(session)) {
            mv.setViewName("redirect:/home");
            return mv;
        }
        UserWithLabels userWithLabels = (UserWithLabels) session.getAttribute("userWithLabels");
        int uid = userWithLabels.getUser().getId();
        Boolean res = userService.removeLabel(uid, lid);
        if (res) {
            setAttribute(session, userService.getUserWithLabels(uid));
            mv.setViewName("redirect:/home");
            return mv;
        }
        mv.setViewName("redirect:/home");
        return mv;
    }

    @GetMapping("updateLabel")
    public ModelAndView updateLabelByUser(@RequestParam Integer id, @RequestParam String labelName, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (checkSessionWithUserLabels(session)) {
            mv.setViewName("redirect:/updateEmp");
            return mv;
        }
        UserWithLabels userWithLabels = (UserWithLabels) session.getAttribute("userWithLabels");
        int uid = userWithLabels.getUser().getId();
        Label label = labelService.getById(id);
        label.setName(labelName);
        boolean res = labelService.updateById(label);
        if (res) {
            setAttribute(session, userService.getUserWithLabels(uid));
            mv.setViewName("redirect:/home");
            return mv;
        }
        mv.setViewName("redirect:/updateEmp");
        return mv;
    }

    @GetMapping("/displayLabel")
    public R displayLabels(HttpSession session, Model model) {
        if (checkSessionWithUserLabels(session)) {
            return new R(false, "Session Error!");
        }
        UserWithLabels userWithLabels = (UserWithLabels) session.getAttribute("userWithLabels");
        if (userWithLabels != null) {
            List<Label> labels = userWithLabels.getLabelList();
            model.addAttribute("user", userWithLabels.getUser().getUsername());
            model.addAttribute("labels", labels);
            return new R(true, userWithLabels);
        }
        return new R(false);
    }

    @PostMapping("/login")
    public ModelAndView userLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        log.info("dashboard login username#{} password#{}", username, password);
        ModelAndView mv = new ModelAndView();
        LoginQuery query = new LoginQuery(username, password);
        UserWithLabels userWithLabels = loginService.login(query);
        if (userWithLabels == null) {
            mv.setViewName("redirect:/login");
            return mv;
        }
        removeAttribute(session);
        setAttribute(session, userWithLabels);
        mv.setViewName("redirect:/home");
        return mv;
    }

    private void setAttribute(HttpSession session, UserWithLabels userWithLabels) {
        User user = userWithLabels.getUser();
        if (session == null) {
            log.warn("session is null, username#{}, uid#{}", user.getUsername(), user.getId());
        } else {
            session.setAttribute("userWithLabels", userWithLabels);
        }
    }


    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        removeAttribute(session);
        session.invalidate();
        mv.setViewName("redirect:/login");
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String firstName, @RequestParam String lastName) {
        log.debug("username: {}, password: {}", username, password);
        ModelAndView mv = new ModelAndView();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        Boolean res = userService.register(user);
        if (res) {
            mv.setViewName("redirect:/login");
            return mv;
        }
        mv.setViewName("redirect:/register");
        return mv;
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
