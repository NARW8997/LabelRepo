package com.example.demo.controllers;

import com.example.demo.controllers.utils.R;
import com.example.demo.domain.Label;
import com.example.demo.services.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public R updateById(@RequestBody Label label) {
        labelService.updateById(label);
        return new R(true, "You have successfully updated the label!");
    }

    @GetMapping("{id}")
    public R getById(@PathVariable Integer id) {
        Label label = labelService.getById(id);
        return new R(true, label);
    }

    @GetMapping("detail")
    public ModelAndView getLabelDetail(@RequestParam Integer id) {
        Label label = labelService.getById(id);
        ModelAndView mv = new ModelAndView();
//        model.addAttribute("label", label);
        mv.setViewName("updateEmp");
        mv.addObject("label", label);
        return mv;
    }
}
