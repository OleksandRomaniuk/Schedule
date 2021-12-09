package com.schedule.proj.service;

import com.schedule.proj.exсeption.SubjectNotFoundException;
import com.schedule.proj.model.*;
import com.schedule.proj.repository.*;
import com.schedule.proj.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final JwtProvider jwtProvider;
    private  final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final CooperationRepository cooperationRepository;
    private static final Logger logger = LogManager.getLogger();
    final static Marker MARKER_SUBJECT = MarkerManager.getMarker("SubjectService");


    public Subject getSubject(Integer id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty())
            throw new SubjectNotFoundException();

        return optionalSubject.get();
    }

    public Subject createSubject(Subject subject) {
        Subject t = subjectRepository.save(subject);
        logger.info(MARKER_SUBJECT,"Create subject");
        return t;
    }


    @Transactional
    public Subject updateSubject(Subject newSubject) {
        Subject subject = subjectRepository.findById(newSubject.getSubjectId())
                .orElseThrow(SubjectNotFoundException::new);

        if (newSubject.getSubjectName() != null) {
            subject.setSubjectName(newSubject.getSubjectName());
        }

        if (newSubject.getDayOfWeek() != null) {
            subject.setDayOfWeek(newSubject.getDayOfWeek());
        }

        if (newSubject.getSubjectTime() != null) {
            subject.setSubjectTime(newSubject.getSubjectTime());
        }

        if (newSubject.getSubjectGroup() != null) {
            subject.setSubjectGroup(newSubject.getSubjectGroup());
        }

        return subject;
    }

    public List<Subject> findByName(String name){
        return subjectRepository.findAllBySubjectName(name);
    }

    public void deleteSubject(Subject subject) {
        subjectRepository.delete(subject);
    }

    public void deleteSubject(Integer id) {
        subjectRepository.deleteById(id);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public long countSubjects() {
        return subjectRepository.count();
    }

    public List<List<Subject>> getSordetListByTime(List<Subject> allSubj) {
        List<List<Subject>> res = new ArrayList<>();
        String[] timeSlots = {"08:30", "10:00", "11:40", "13:30", "15:00", "16:30", "18:00"};
        for(String timeSlot:timeSlots){
            List<Subject> temp = new ArrayList<>();
            DayOfWeek currentDay = DayOfWeek.MONDAY;
            for (int i = 1; i <= 5; i++) {
                for (Subject subj:allSubj) {
                    if(subj.getSubjectTime().toString().equals(timeSlot)
                            && subj.getDayOfWeek().equals(currentDay)){
                        temp.add(subj);
                        break;
                    }
                }
                if(temp.size() != i){
                    temp.add(new Subject(null,null, LocalTime.parse(timeSlot),null));
                }
                currentDay = currentDay.plus(1L);
            }
            res.add(temp);
        }
        return res;
    }

    public List<Subject> findTeachersSubjectByToken(HttpServletRequest request) {
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);
        Teacher teacher = teacherRepository.getByUserId(user.getId());
        return subjectRepository.findAllBySubjectTeacher(teacher);
    }

    public List<Subject> findStudentubjectByToken(HttpServletRequest request) {
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);
        Student student = studentRepository.getByUserId(user.getId());
        return cooperationRepository.findAllByStudent_StudentId(student.getStudentId()).stream().map(Cooperation::getSubject).collect(Collectors.toList());
    }

    public List<Subject> findStudentSubjectByTokenAndWeek(HttpServletRequest request , String week) {
        int i = Integer.parseInt(week);
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);
        Student student = studentRepository.getByUserId(user.getId());
        List<Subject> copy =  cooperationRepository.findAllByStudent_StudentId(student.getStudentId()).stream().map(Cooperation::getSubject).collect(Collectors.toList());
        List<Subject> res = new ArrayList<>();
        for(Subject m : copy){
                if(m.getWeeks().contains(i))
                    res.add(m);
           }
        return res;

    }

    public List<Subject> findTeacherSubjectByTokenAndWeek(HttpServletRequest request , String week) {
        int i = Integer.parseInt(week);
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);
        Teacher teacher = teacherRepository.getByUserId(user.getId());
        List<Subject> copy =  subjectRepository.findAllBySubjectTeacher(teacher);
        List<Subject> res = new ArrayList<>();
        for(Subject m : copy){
            if(m.getWeeks().contains(i))
                res.add(m);
        }
        return res;

    }

}
