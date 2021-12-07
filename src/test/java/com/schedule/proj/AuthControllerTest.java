package com.schedule.proj;

import com.schedule.proj.controller.AuthController;
import com.schedule.proj.model.DTO.LoginDTO;
import com.schedule.proj.model.User;
import com.schedule.proj.model.UserRole;
import com.schedule.proj.security.jwt.CustomUserDetailsService;
import com.schedule.proj.security.jwt.JwtConfigurer;
import com.schedule.proj.security.jwt.JwtProvider;
import com.schedule.proj.service.AuthenticationService;
import com.schedule.proj.service.RegistrationService;
import com.schedule.proj.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtConfigurer jwtConfigurer;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginTest() throws Exception {

       String login = "vkd@ukma.edu.ua";
       String pass = "Q1W2E3rtyuiop";
        LoginDTO loginDTO = new LoginDTO(login,pass);

        when(userService.findUserByEmail(login)).thenReturn(new User(UserRole.STUDENT,login,pass,"","",""));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("email",login)
                .param("password",pass))
                .andExpect(status().is3xxRedirection());
   }
}