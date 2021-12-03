package com.schedule.proj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping()
public class AccountController {

    @GetMapping("/")
    public String validateUserForm(){
        return "redirect:/api/auth/login";
    }
}
