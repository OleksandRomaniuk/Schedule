package com.schedule.proj.controller;


import com.schedule.proj.model.DTO.LoginDTO;
import com.schedule.proj.model.Speciality;
import com.schedule.proj.model.User;
import com.schedule.proj.security.jwt.CustomUserDetails;
import com.schedule.proj.security.jwt.CustomUserDetailsService;
import com.schedule.proj.security.jwt.JwtProvider;
import com.schedule.proj.service.AdminService;
import com.schedule.proj.service.SubjectService;
import com.schedule.proj.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Operation(summary = "AddBD")
    @PostMapping("/addBD")
    ResponseEntity<?> addBD() {
        ResponseEntity<String> result = adminService.createBD();
        return new ResponseEntity<String>(String.valueOf(result), HttpStatus.OK);
    }

    @GetMapping("/schedule")
    public String loginUserForm(@CookieValue("Authorization") String token, Model model,
                                @RequestParam String speciality){
        String userLogin = jwtProvider.getLoginFromToken(token);
        User user = userService.findUserByEmail(userLogin);

        model.addAttribute("user", user);
//        return "user-page";
        model.addAttribute("days", subjectService.getScheduleDaysBySpecility(speciality));
        return "admin-schedule";
    }

}
