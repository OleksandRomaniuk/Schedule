package com.schedule.proj.controller;


import com.schedule.proj.model.DTO.LoginDTO;
import com.schedule.proj.repository.TeacherRepository;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ResetPasswordController {


    JavaMailSender emailSender;
    EmailService emailService;
    PasswordService passwordService;
    UserService userService;
    ResetPasswordService resetPasswordService;
    TeacherService teacherService;
    UserRepository userRepository;
    StudentService studentService;
    AuthenticationService authenticationService;

    @Autowired
    public ResetPasswordController(JavaMailSender emailSender, EmailService emailService, PasswordService passwordService, UserService userService, ResetPasswordService resetPasswordService, TeacherService teacherService, UserRepository userRepository, StudentService studentService, AuthenticationService authenticationService) {
        this.emailSender = emailSender;
        this.emailService = emailService;
        this.passwordService = passwordService;
        this.userService = userService;
        this.resetPasswordService = resetPasswordService;
        this.teacherService = teacherService;
        this.userRepository = userRepository;
        this.studentService = studentService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "send security test email")
    @GetMapping("/sendSecurityEmail")
    public String sendSecurityEmail(@ModelAttribute("loginDTO") LoginDTO loginDTO) throws MessagingException {
       if(userService.getUserByEmail(loginDTO.getEmail())!=null) {
           resetPasswordService.chekemail(loginDTO.getEmail());
           return "redirect:/api/auth/login";
       }
        else{
           return "redirect:/api/auth/reset";
        }
    }

}
