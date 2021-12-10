package com.schedule.proj.controller;


import com.schedule.proj.exсeption.JwtAuthenticationException;
import com.schedule.proj.model.DTO.LoginDTO;
import com.schedule.proj.model.DTO.UserDTO;
import com.schedule.proj.model.User;
import com.schedule.proj.security.jwt.CustomUserDetails;
import com.schedule.proj.security.jwt.cache.CurrentUser;
import com.schedule.proj.service.AuthenticationService;
import com.schedule.proj.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String loginUserForm(@ModelAttribute("loginDTO")LoginDTO loginDTO, Model model){
        model.addAttribute("loginDTO", loginDTO);
        return "user-login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDTO")LoginDTO loginDTO,
                                   HttpServletResponse response){
        Map<String, String> res = new HashMap<>();
        try {
            String token = authenticationService.login(loginDTO);

            int userId = userService.findUserByEmail(loginDTO.getEmail()).getId();
            String path = "/api/user/"+userId;

            Cookie cookie = new Cookie(HttpHeaders.AUTHORIZATION, token);
            //cookie.setPath(path);
            cookie.setPath("/");

            response.addCookie(cookie);

//            HttpHeaders headers = new HttpHeaders();
//
//            headers.add("Location",path );
//            headers.add(HttpHeaders.AUTHORIZATION, token);
//            ResponseEntity<String> resEnt = new ResponseEntity<String>(headers, HttpStatus.FOUND);

            //return "redirect:" + path;
            return "redirect:/api/user/profile";
            //return ResponseEntity.ok(res);
        }catch (AuthenticationException ex) {
            //res.put("message", ex.getMessage());
            //return ResponseEntity.badRequest().body(res);
            return "user-login";
        }catch (Exception exception) {
            exception.printStackTrace();
            return "user-login";
            //return ResponseEntity.badRequest().body(res);
        }

    }


    @PostMapping("/check")
    public ResponseEntity<?> checkTokenExpire(@RequestBody Map<String,String> request){
        Map<String, String> res = new HashMap<>();
        try{
            String message = authenticationService.checkExpiration(request.get("token"));
            res.put("message", message);
            return ResponseEntity.ok(res);
        }catch (JwtAuthenticationException | ExpiredJwtException e){
            res.put("message",e.getMessage());
            return ResponseEntity.status(401).body(res);
        }
    }

    @GetMapping("/reset")
    public String resetPassword(Model model){
        model.addAttribute("loginDTO", new LoginDTO());
        return "user-reset";
    }

}
