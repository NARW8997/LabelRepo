package com.example.demo.controllers;

import com.example.demo.controllers.utils.R;
import com.example.demo.domain.Label;
import com.example.demo.domain.User;
import com.example.demo.services.ILabelService;
import com.example.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("label/")
public class LabelController {

    @Autowired
    private ILabelService labelService;


    @PostMapping
    public R insert(@RequestBody Label label) {
        labelService.save(label);
        return new R(true, "You have successfully created an label!");
    }

    @DeleteMapping("{id}")
    public R deleteById(@PathVariable Integer id) {
        labelService.removeById(id);
        return new R(true, "You have successfully deleted the label!");
    }

    @PutMapping
    public R updateById(@RequestBody Label labe) {
        labelService.updateById(labe);
        return new R(true, "You have successfully updated the label!");
    }

    @GetMapping("{id}")
    public R getById(@PathVariable Integer id) {
        Label label = labelService.getById(id);
        return new R(true, label);
    }
}
