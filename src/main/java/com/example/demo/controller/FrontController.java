package com.example.demo.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController implements ErrorController {

    @GetMapping("/")
    public String entrenc(){
        return "/index.html";
    }

    @GetMapping("/login")
    public String login(){
        return "/index.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
