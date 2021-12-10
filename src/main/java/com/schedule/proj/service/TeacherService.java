package com.schedule.proj.service;

import com.schedule.proj.exсeption.DuplicateUserEmailException;
import com.schedule.proj.exсeption.TeacherNotFoundException;
import com.schedule.proj.exсeption.SubjectNotFoundException;
import com.schedule.proj.model.*;
import com.schedule.proj.model.DTO.StudentGeneralResponseDTO;
import com.schedule.proj.model.DTO.TeacherGeneralResponseDTO;
import com.schedule.proj.repository.SubjectRepository;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schedule.proj.repository.TeacherRepository;


import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private static final Logger logger = LogManager.getLogger();
    private final JwtProvider jwtProvider;
    private final  UserRepository userRepository;
    final static Marker MARKER_TEACHER = MarkerManager.getMarker("TeacherService");

    public Teacher getTeacher(Integer id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(Math.toIntExact(id));

        if (optionalTeacher.isEmpty())
            throw new TeacherNotFoundException();

        return optionalTeacher.get();
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }


    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public long countTeachers() {
        return teacherRepository.count();
    }

    public Teacher addTeacher(Teacher teacher) {
        Teacher t = teacherRepository.save(teacher);
        ThreadContext.put("username",t.getUser().getFirstName() + " "+teacher.getUser().getLastName() );
        ThreadContext.put("ID", t.getTeacherId().toString());
        logger.info(MARKER_TEACHER,"Create teacher");
        ThreadContext.clearMap();
        return t;
    }


    public ResponseEntity<String> updateTeachertByToken(TeacherGeneralResponseDTO dto, HttpServletRequest request) {

        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);
        if(user.getUserRole() == UserRole.TEACHER && user!=null) {
            Teacher teacher = teacherRepository.getByUserId(user.getId());
            //user.setFirstName(dto.getFirstname());
            //user.setLastName(dto.getLastname());
            teacher.setCathedra(dto.getCathedra());
            teacher.setFaculty(dto.getFaculty());
            teacher.setRank(dto.getRank());
            teacherRepository.save(teacher);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else return  new ResponseEntity<String>(HttpStatus.NOT_FOUND);

    }

}
