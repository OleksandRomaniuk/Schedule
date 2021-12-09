package com.schedule.proj.service;

import com.schedule.proj.config.NaukmaCacheManager;
import com.schedule.proj.exсeption.StudentNotFoundException;
import com.schedule.proj.model.DTO.StudentResponseDTO;
import com.schedule.proj.model.Student;
import com.schedule.proj.model.User;
import com.schedule.proj.repository.UserRepository;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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
    @Autowired
    NaukmaCacheManager naukmaCacheManager;

    public UserService() {

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "user")
    public User getUserById(int userId) {
        logger.info("No cache info for user with id = {}", userId);
        return userRepository.findOneById(userId);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void evictAllUsers() {
        naukmaCacheManager.evictAllCacheValues("user");
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

}
