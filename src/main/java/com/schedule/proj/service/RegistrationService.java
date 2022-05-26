package com.schedule.proj.service;

import com.schedule.proj.exсeption.RegistrationException;
import com.schedule.proj.model.DTO.UserDTO;
import com.schedule.proj.model.User;
import com.schedule.proj.model.UserRole;
import com.schedule.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service @RequiredArgsConstructor
public class RegistrationService {


    private final  EmailService emailService;
    private final  PasswordService passwordService;

    private final  UserRepository userRepository;



    public String registration(UserDTO userDTO) throws RegistrationException{

        String email = userDTO.getEmail();
        if(emailService.emailExist(email)){
            throw new RegistrationException("User with email = "+ email + " already exist");
        }
      User user = new User();  
        user.setEmail(email);
        //checking user password is valid
        String password = userDTO.getPassword();
        if(!passwordService.isValidPassword(password)){
            throw new RegistrationException("Password is not valid");
        }
        user.setPassword(passwordService.encodePassword(userDTO.getPassword()));



        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

         user.setUserRole(UserRole.USER);

         userRepository.save(user);

        return "User created";
    }

}
