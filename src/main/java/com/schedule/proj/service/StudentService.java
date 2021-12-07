package com.schedule.proj.service;

import com.schedule.proj.model.*;
import com.schedule.proj.model.DTO.StudentGeneralResponseDTO;
import com.schedule.proj.repository.StudentRepository;
import com.schedule.proj.repository.SubjectRepository;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger();
    final static Marker MARKER_STUDENT = MarkerManager.getMarker("StudentService");

    public Student createStudent(Student student) {
        Student t = studentRepository.save(student);
        //ThreadContext.put("username", t.getUser().getFirstName() + " " + student.getUser().getLastName());
        // ThreadContext.put("ID", t.getUser().getUserId().toString());
        logger.info(MARKER_STUDENT, "Create student");
        ThreadContext.clearMap();
        return t;
    }


    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public long countStudents() {
        return studentRepository.count();
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }


    public ResponseEntity<String> updateStudentByToken(StudentGeneralResponseDTO dto, HttpServletRequest request) {
        int k = 5;
        String token = jwtProvider.getTokenFromRequest(request);
        int b = 5;
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);
        if (user.getUserRole() == UserRole.STUDENT && user != null) {
            Student student = studentRepository.getByUserId(user.getId());
            int i = 5;
            //user.setFirstName(dto.getFirstname());
            //user.setLastName(dto.getLastname());
            student.setStudentYear(dto.getStudentYear());
            student.setFaculty(dto.getFaculty());
            student.setSpeciality(dto.getSpeciality());
            studentRepository.save(student);
            return new ResponseEntity<String>(HttpStatus.OK);
        } else return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

    }




}






