package com.deltamate.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class viewTestController {

    @GetMapping("/home")
    public String home(Model model) {
        return "view/home";
    }
}