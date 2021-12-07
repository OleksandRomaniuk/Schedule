package com.schedule.proj.service;


import com.schedule.proj.model.User;
import com.schedule.proj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class EmailService {

    @Autowired
    UserRepository userRepository;


    public EmailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //check user email is existing in database
    public boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public String updateEmail(String email, int id) {

        //TODO check email to valid with sending testEmail
        if (isEmailValidRegEx(email)) {

            if (userRepository.findByEmail(email).isEmpty()) {

                User userToUpdate = userRepository.findOneById(id);
                userToUpdate.setEmail(email);
                userRepository.save(userToUpdate);
                return "Email updated to " + userRepository.findOneById(id).getEmail();
            } else {
                return "email " + email + " is exist";
            }
        } else {
            return "Something wrong with thr email ->  " + email;
        }
    }
    public boolean isEmailValidRegEx(String email) {
        // get emails name length it must be not more 129
        int length = email.length();

        // simple pattern check @  .
        Pattern pattern = Pattern.compile(".+@.+\\..+");
        Matcher matcher = pattern.matcher(email);

        return (matcher.matches() && length < 130);
    }

}
