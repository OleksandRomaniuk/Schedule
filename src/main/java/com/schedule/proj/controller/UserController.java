package com.schedule.proj.controller;


import com.schedule.proj.exсeption.RegistrationException;
import com.schedule.proj.model.DTO.*;
import com.schedule.proj.model.*;
import com.schedule.proj.security.jwt.CustomUserDetails;
import com.schedule.proj.security.jwt.JwtProvider;
import com.schedule.proj.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;


    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/{id}")
    public String getUserPage(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        //model.addAttribute("user", new User());
        return "user-page";
    }

    @GetMapping("/{id}/profile")
    public String getUserPageProfile(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        return "user-page-profile";
    }

    @GetMapping("/profile")
    public String getUserPageProfile(@CookieValue("Authorization") String token, Model model){
        String userLogin = jwtProvider.getLoginFromToken(token);
        User user = userService.findUserByEmail(userLogin);
        model.addAttribute("user", userService.getUserById(user.getId()));
        return "user-page-profile";
    }

    @GetMapping("/{id}/profile/edit")
    public String getUserPageProfileEdit(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        model.addAttribute("years", new int[]{1, 2, 3, 4, 5, 6});
        return "user-page-profile-edit";
    }


    @GetMapping("{id}/changePassword")
    public String changePasswordPassword(@ModelAttribute("loginDTO") PasswordDTO passwordDTO,
                                         Model model, @PathVariable Long id){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        model.addAttribute("passwordDTO", passwordDTO);
        return "user-page-reset-password";
    }

    @Operation(summary = "change password")
    @PostMapping("{id}/changePassword")
    public String changePassword(@PathVariable Long id,
                                 @ModelAttribute("passwordDTO") PasswordDTO dto,
                                 HttpServletRequest request ) {
        try {
            userService.changePassword(request, dto.getOldPassword(), dto.getNewPassword());
            User u = userService.getUserByRequest(request);
            CustomUserDetails user = CustomUserDetails.fromUserEntityToCustomUserDetails(u);
            String message = authenticationService.logout(request, user);
            return "redirect:/api/auth/login";
        } catch (RegistrationException | UsernameNotFoundException e) {
            return "user-page-reset-password";
        }
    }

    @PostMapping ("{id}/logout")
    public String logout(HttpServletRequest request){
        User u = userService.getUserByRequest(request);
        CustomUserDetails user = CustomUserDetails.fromUserEntityToCustomUserDetails(u);
        String message = authenticationService.logout(request, user);
        return "redirect:/api/auth/login";
    }







}

