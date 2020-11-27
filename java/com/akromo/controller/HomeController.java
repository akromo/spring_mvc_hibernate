package com.akromo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class HomeController {
    @RequestMapping("/")
    public String getIndex(){
        return "home/index";
    }
}
