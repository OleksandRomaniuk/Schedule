package com.schedule.proj;

import com.schedule.proj.controller.AuthController;
import com.schedule.proj.model.DTO.LoginDTO;
import com.schedule.proj.model.User;
import com.schedule.proj.model.UserRole;
import com.schedule.proj.repository.CooperationRepository;
import com.schedule.proj.repository.StudentRepository;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.security.jwt.CustomUserDetailsService;
import com.schedule.proj.security.jwt.JwtConfigurer;
import com.schedule.proj.security.jwt.JwtProvider;
import com.schedule.proj.service.AuthenticationService;
import com.schedule.proj.service.RegistrationService;
import com.schedule.proj.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CooperationRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CooperationRepository cooperationRepository;

    @Test
    public void getCooperationTest() {
        var cooperation = cooperationRepository.coopIsPresent(1,1);
        Assert.assertEquals(cooperation.getStatus(),true);
    }

}