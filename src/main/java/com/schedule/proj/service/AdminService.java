package com.schedule.proj.service;

import com.schedule.proj.model.Student;
import com.schedule.proj.model.User;
import com.schedule.proj.model.UserRole;
import com.schedule.proj.repository.SubjectRepository;
import com.schedule.proj.repository.TeacherRepository;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.security.jwt.JwtProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private static final Logger logger = LogManager.getLogger();
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public AdminService(TeacherRepository teacherRepository, SubjectRepository subjectRepository, JwtProvider jwtProvider, UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> createBD() {
       for(int i=0;i<5;i++){
           User user = new User();
           user.setEmail(i+"gmail.com");
           user.setLastName(i+"LastName");
           user.setFirstName(i+"Firstname");
           user.setPassword("1234qwerT");
           if(i%2==0)
           user.setUserRole(UserRole.TEACHER);
           else
               user.setUserRole(UserRole.STUDENT);

       }
            return new ResponseEntity<String>(HttpStatus.OK);

    }

}
