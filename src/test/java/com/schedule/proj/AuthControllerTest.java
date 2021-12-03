package com.schedule.proj;

import com.schedule.proj.controller.AuthController;
import com.schedule.proj.model.DTO.UserDTO;
import com.schedule.proj.service.AuthenticationService;
import com.schedule.proj.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private RegistrationService registrationService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginTest() throws Exception {

       String login = "test@ukma.edu.ua";
       String pass = "Q1W2E3rtyuiop";
       registrationService.registration(new UserDTO("ivan","maister",login,pass,"STUDENT"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login"));
    }
}