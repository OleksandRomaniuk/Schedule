package com.schedule.proj;

import com.schedule.proj.controller.AuthController;
import com.schedule.proj.controller.UserController;
import com.schedule.proj.model.DTO.LoginDTO;
import com.schedule.proj.model.User;
import com.schedule.proj.model.UserRole;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.security.jwt.CustomUserDetailsService;
import com.schedule.proj.security.jwt.JwtConfigurer;
import com.schedule.proj.security.jwt.JwtProvider;
import com.schedule.proj.service.AuthenticationService;
import com.schedule.proj.service.RegistrationService;
import com.schedule.proj.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtConfigurer jwtConfigurer;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void apiWithoutTokenTest() throws Exception {

        mockMvc.perform(get("/api/user/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());  //403 Access Denied
    }

//    @Test
//    void apiWithTokenTest() throws Exception {
//
//        String login = "vkd@ukma.edu.ua";
//        String pass = "Q1W2E3rtyuiop";
//        LoginDTO loginDTO = new LoginDTO(login,pass);
//
//        when(authenticationService.login(loginDTO)).thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2a2RAdWttYS5lZHUudWEiLCJyb2xlIjpbeyJhdXRob3JpdHkiOiJTVFVERU5UIn1dLCJpZCI6IjEiLCJpYXQiOjE2Mzg2MTYxOTgsImV4cCI6MTYzOTIyMDk5OH0.s93DGSnvABHGR3BX7LBNkv9QqhgpGYiAaGUd702sFzTMFl04xFAmhNEWqe-0IF4UFzpBUcw3qicN81aCyCzsFg");
//        when(userRepository.findUserByEmail(login)).thenReturn(new User(UserRole.STUDENT,login,pass,"","",""));
//
//        String token = authenticationService.login(loginDTO);
//
//        Cookie cookie = new Cookie(HttpHeaders.AUTHORIZATION, token);
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.add(HttpHeaders.AUTHORIZATION, token);
//
//        mockMvc.perform(get("/api/user/student/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .headers(headers)
//                .cookie(cookie))
//                .andExpect(status().isOk());
//    }
}