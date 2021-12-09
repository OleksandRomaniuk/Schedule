package com.schedule.proj.controller;


import com.schedule.proj.model.DTO.LoginDTO;
import com.schedule.proj.repository.TeacherRepository;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ResetPasswordController {

    @Autowired
    EmailService emailService;
    PasswordService passwordService;
    UserService userService;
    ResetPasswordService resetPasswordService;
    TeacherService teacherService;
    UserRepository userRepository;
    StudentService studentService;
    AuthenticationService authenticationService;

    public ResetPasswordController(ResetPasswordService resetPasswordService, AuthenticationService authenticationService, EmailService emailService, PasswordService passwordService, StudentService studentService, UserRepository userRepository, TeacherRepository teacherRepository) {
        this.authenticationService = authenticationService;
        this.emailService = emailService;
        this.passwordService = passwordService;
        this.studentService = studentService;
        this.userRepository = userRepository;
        this.resetPasswordService= resetPasswordService;
    }

    @Operation(summary = "send security test email")
    @GetMapping("/sendSecurityEmail/{sendTo}")
    public void sendSecurityEmail(@PathVariable(value = "sendTo") String sendTo) throws MessagingException {
        resetPasswordService.chekemail(sendTo);
    }

}
