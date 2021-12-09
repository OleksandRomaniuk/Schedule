package com.schedule.proj.service;

import com.schedule.proj.exсeption.RegistrationException;
import com.schedule.proj.exсeption.StudentNotFoundException;
import com.schedule.proj.model.DTO.StudentResponseDTO;
import com.schedule.proj.model.Student;
import com.schedule.proj.model.User;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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
    PasswordService passwordService;
    JwtProvider jwtProvider;


    public UserService() {

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int userId) {
        return userRepository.findOneById(userId);
    }


    public ResponseEntity<String> updateUser(User userToUpdate, StudentResponseDTO request) {
        String emailNew = request.getEmail();
        int id = userToUpdate.getId();

        if (userToUpdate != null) {

            if (request.getFirstName().isEmpty()) {
                userToUpdate.setFirstName("");
            }
            else{
                    userToUpdate.setFirstName(request.getFirstName());
                }

                if (request.getLastName().isEmpty()) {
                    userToUpdate.setLastName("");
                }
            else{
                        userToUpdate.setLastName(request.getLastName());
                    }

                    //update email using method from emailService
                    //if emails are equals do nothing
                    if (!userToUpdate.getEmail().equals(emailNew)) {
                        //TODO create new token with new email
                        String reportUpdate = emailService.updateEmail(emailNew, id);
                    }
                    userRepository.save(userToUpdate);



                } else {
                    throw new StudentNotFoundException("User with id = " + id + " not found");
                }
                return new ResponseEntity<String>(HttpStatus.OK);
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
