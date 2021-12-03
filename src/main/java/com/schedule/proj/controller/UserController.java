package com.schedule.proj.controller;


import com.schedule.proj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    //select all accounts
//    @Operation(summary = "select all users")
    @GetMapping("/{id}")
    public String getUserPage(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "user-page";
    }













}

