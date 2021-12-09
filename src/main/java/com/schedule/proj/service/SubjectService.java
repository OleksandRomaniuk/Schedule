package com.schedule.proj.service;

import com.schedule.proj.exсeption.SubjectNotFoundException;
import com.schedule.proj.model.*;
import com.schedule.proj.model.DTO.ScheduleDayDTO;
import com.schedule.proj.model.DTO.ScheduleTimeDTO;
import com.schedule.proj.model.DTO.SubjectDTO;
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
import java.util.*;
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

    public List<Subject> findSubjectsBySpeciality(String speciality) {
        return subjectRepository.findAllBySubjectSpeciality(speciality);
    }

    public List<ScheduleDayDTO> getScheduleDaysBySpecility(String speciality) {
        List<Subject> subjects = findSubjectsBySpeciality(speciality);
        List<ScheduleDayDTO> resDays = new LinkedList<>();
        resDays.add(getSubjectsByDay(subjects, DayOfWeek.MONDAY));
        resDays.add(getSubjectsByDay(subjects, DayOfWeek.TUESDAY));
        resDays.add(getSubjectsByDay(subjects, DayOfWeek.WEDNESDAY));
        resDays.add(getSubjectsByDay(subjects, DayOfWeek.THURSDAY));
        resDays.add(getSubjectsByDay(subjects, DayOfWeek.FRIDAY));
        return resDays;
    }

    private ScheduleDayDTO getSubjectsByDay(List<Subject> subjects, DayOfWeek day) {
        LinkedList<ScheduleTimeDTO> res = new LinkedList<>();
        res.add(getSubjectsByTime(subjects, day, LocalTime.of(8, 30, 0)));
        res.add(getSubjectsByTime(subjects, day, LocalTime.of(10, 0, 0)));
        res.add(getSubjectsByTime(subjects, day, LocalTime.of(11, 40, 0)));
        res.add(getSubjectsByTime(subjects, day, LocalTime.of(13, 30, 0)));
        res.add(getSubjectsByTime(subjects, day, LocalTime.of(15, 0, 0)));
        res.add(getSubjectsByTime(subjects, day, LocalTime.of(16, 30, 0)));
        res.add(getSubjectsByTime(subjects, day, LocalTime.of(18, 0, 0)));
        return new ScheduleDayDTO(day, res);
    }

    private ScheduleTimeDTO getSubjectsByTime(List<Subject> subjects, DayOfWeek day, LocalTime time) {
        List<SubjectDTO> subjectsByTime = subjects
                .stream()
                .filter(s -> s.getDayOfWeek().equals(day) && s.getSubjectTime().equals(time))
                .map(x -> new SubjectDTO(x.getSubjectId().toString(),
                        x.getSubjectName(),
                        x.getSubjectTeacher().getUser().getFirstName() + " " + x.getSubjectTeacher().getUser().getLastName(),
                        x.getSubjectGroup(), convertToWeeks(x.getWeeks())))
                .collect(Collectors.toList());
        return new ScheduleTimeDTO(time.getHour() + ":" + "00", subjectsByTime);
    }

    private static String convertToWeeks(Collection<Integer> weeks) {
        if (weeks.isEmpty())
            return "";

        List<Integer> sorted = weeks.stream().sorted().collect(Collectors.toList());

        if (sorted.size() == 1)
            return sorted.get(0).toString();

        int head = sorted.get(0);
        int tail = sorted.get(1);

        LinkedList<String> resStr = new LinkedList<>();

        for (int i = 1; i < weeks.size(); ++i) {
            if (sorted.get(i) - tail > 1) {
                if (head == tail)
                    resStr.add(head + "");
                else
                    resStr.add(head + "-" + tail);
                head = sorted.get(i);
            }
            tail = sorted.get(i);
        }

        if (head == tail)
            resStr.add(head + "");
        else
            resStr.add(head + "-" + tail);

        return String.join(",", resStr);
    }
}
