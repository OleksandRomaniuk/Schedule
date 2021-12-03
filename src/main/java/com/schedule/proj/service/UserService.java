package com.schedule.proj.service;

import com.schedule.proj.model.User;
import com.schedule.proj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {



    @Autowired
   ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    UserRepository userRepository;

   public List<User> getAllUsers(){
        return userRepository.findAll();
    }

   public Optional<User> getUserById(int userId) {
        return userRepository.findById(userId);
   }

    public  User findUserByEmail(String email){
       return userRepository.findUserByEmail(email);
    }
}
