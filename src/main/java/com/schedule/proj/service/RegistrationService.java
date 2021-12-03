package com.schedule.proj.service;

import com.schedule.proj.exсeption.RegistrationException;
import com.schedule.proj.model.DTO.UserDTO;
import com.schedule.proj.model.Student;
import com.schedule.proj.model.Teacher;
import com.schedule.proj.model.User;
import com.schedule.proj.model.UserRole;
import com.schedule.proj.repository.StudentRepository;
import com.schedule.proj.repository.TeacherRepository;
import com.schedule.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service @RequiredArgsConstructor
public class RegistrationService {


    private final  EmailService emailService;
    private final  PasswordService passwordService;
    private final   TeacherRepository teacherRepository;
    private final  UserRepository userRepository;
    private final  StudentRepository studentRepository;
    private final   TeacherService teacherService;
    private final   StudentService studentService;


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
        //add role and create record
        if(userDTO.getRole().equals("TEACHER")){
            Teacher teacher = new Teacher();
            user.setUserRole(UserRole.TEACHER);
            teacher.setUser(user);
            teacherService.addTeacher(teacher);

        }else{
            Student student = new Student();
            user.setUserRole(UserRole.STUDENT);
            student.setUser(user);
            studentService.addStudent(student);
        }
        return "User created";
    }

}
