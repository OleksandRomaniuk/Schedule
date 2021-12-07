package com.schedule.proj.service;


import com.schedule.proj.model.Cooperation;
import com.schedule.proj.model.DTO.SubjectGroupDTO;
import com.schedule.proj.model.Student;
import com.schedule.proj.model.Subject;
import com.schedule.proj.model.User;
import com.schedule.proj.repository.*;
import com.schedule.proj.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class CooperationService {

    @Autowired
    CooperationRepository cooperationRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    SubjectRepository subjectRepository;

    public String createCooperation(HttpServletRequest request , SubjectGroupDTO dto){
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);

        Subject subjecttt = subjectRepository.findBySubjectGroupAndSubjectName(dto.getGroup(), dto.getSubjectname());
        User user = userRepository.findUserByEmail(email);
        Student student = studentRepository.getByUserId(user.getId());
        Cooperation c;
                c =new Cooperation();
                c.setStatus(true);
          c.setStudent(studentRepository.getById((student.getStudentId())));
         c.setSubject(subjectRepository.getById(subjecttt.getSubjectId()));
         cooperationRepository.save(c);

        return  "Student add this subject";
    }



    public Boolean showInformation(HttpServletRequest request,int subjectId){
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        Student student = studentRepository.getById(userRepository.findUserByEmail(email).getId());
        return (checkInfo(student.getStudentId(),subjectId));
    }
    public Boolean checkInfo(int sudentId,int subjectId){
        Cooperation cooperation = cooperationRepository.coopIsPresent(sudentId,subjectId);
        if(cooperation == null)  return false;
        if(cooperation.getStatus() == true)
            return true;
        else return false;
    }



    public void deleSybjectforStudent(HttpServletRequest request, SubjectGroupDTO dto) {
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        Subject subjecttt = subjectRepository.findBySubjectGroupAndSubjectName(dto.getGroup(), dto.getSubjectname());
        User user = userRepository.findUserByEmail(email);
        Student student = studentRepository.getByUserId(user.getId());
        cooperationRepository.deleteAllByStudentAndSubject(student,subjecttt);
    }
}
