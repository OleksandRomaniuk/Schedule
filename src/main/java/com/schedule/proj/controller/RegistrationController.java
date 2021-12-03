package com.schedule.proj.controller;


import com.schedule.proj.exсeption.RegistrationException;
import com.schedule.proj.model.DTO.UserDTO;
import com.schedule.proj.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/registration")
public class RegistrationController {


    @Autowired
    RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String createUserForm(@ModelAttribute("userDTO")  UserDTO userDTO){
        //model.addAttribute("userDTO", new());
        return "user-create";
    }

    //method for registration
    @Operation(summary = "method for registration")
    @PostMapping
    String registration(@ModelAttribute("userDTO") @Valid UserDTO userDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/registration");
            ResponseEntity<String> resEnt = new ResponseEntity<String>(headers,HttpStatus.FOUND);
            //return resEnt;
            return "user-create";
        }

        Map<String, String> res = new HashMap<>();

        try{
            String result = registrationService.registration(userDTO);
            res.put("message", result);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/auth/login");
            ResponseEntity<String> resEnt = new ResponseEntity<String>(headers,HttpStatus.FOUND);
            //return resEnt;
            return "redirect:/api/auth/login";
        }
        catch(RegistrationException e){
            res.put("message",e.getMessage());
            return e.getMessage();
            //return ResponseEntity.badRequest().body(res);
        }

    }
}