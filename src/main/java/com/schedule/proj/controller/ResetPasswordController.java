package com.schedule.proj.controller;


import com.schedule.proj.model.DTO.LoginDTO;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Controller
@AllArgsConstructor
public class ResetPasswordController {


    JavaMailSender emailSender;
    EmailService emailService;
    PasswordService passwordService;
    UserService userService;
    ResetPasswordService resetPasswordService;

    UserRepository userRepository;

    AuthenticationService authenticationService;



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
