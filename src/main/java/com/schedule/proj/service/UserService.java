package com.schedule.proj.service;

import com.schedule.proj.exсeption.RegistrationException;
import com.schedule.proj.model.User;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private static final Logger logger = LogManager.getLogger();
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordService passwordService;
    @Autowired
    JwtProvider jwtProvider;

    public UserService() {

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int userId) {
        return userRepository.findOneById(userId);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User getUserByRequest(HttpServletRequest request) {
        String token = jwtProvider.getTokenFromRequest(request);
        String email =jwtProvider.getLoginFromToken(token);
        return userRepository.findUserByEmail(email);
    }


    public  User findUserByEmail(String email){
       return userRepository.findUserByEmail(email);
    }

    public String changePassword(HttpServletRequest request, String oldPassword, String newPassword) {
        String token = jwtProvider.getTokenFromRequest(request);
        String email =jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found ");
        }
        if(passwordService.equalsPassword(oldPassword, user.getPassword())){
            if(!passwordService.isValidPassword(newPassword)){
                throw new RegistrationException("Password is not valid");
            }
            user.setPassword(passwordService.encodePassword(newPassword));
            userRepository.save(user);
            return "Password changed";
        }else{
            throw new RegistrationException("The old password is incorrect");
        }
    }

}
