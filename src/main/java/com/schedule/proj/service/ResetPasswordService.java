package com.schedule.proj.service;


import com.schedule.proj.model.User;
import com.schedule.proj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Random;


@Service
public class ResetPasswordService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");
    private static final Random RANDOM = new SecureRandom();
    @Autowired
    EmailService emailService;
    PasswordService passwordService;
    UserRepository userRepository;


    public ResetPasswordService(EmailService emailService, PasswordService passwordService, UserRepository userRepository) {
        this.emailService = emailService;
        this.passwordService = passwordService;
        this.userRepository = userRepository;

    }
    public void chekemail(String email) throws MessagingException {
        String password = generateRandomPassword();
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found ");
        }
        user.setPassword(passwordService.encodePassword(password));
        userRepository.save(user);
        if (emailService.emailExist(email)) {
            emailService.resetPasswordmessage(email, "" , password );
        }
    }
    public static String generateRandomPassword() {
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGH1234567891234567890JKMNPQRSTUVWXYZ";
        String pw = "";
        for (int i = 0; i < 10; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        return pw;
    }

}
