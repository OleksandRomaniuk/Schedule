package com.schedule.proj.controller;

import com.schedule.proj.logger.ExecutionTime;
import com.schedule.proj.logger.MethodParamsRes;
import com.schedule.proj.model.DTO.LoginDTO;
import com.schedule.proj.model.DTO.SubjectDTO;
import com.schedule.proj.model.DTO.SubjectGroupDTO;
import com.schedule.proj.model.DTO.TeacherGeneralResponseDTO;
import com.schedule.proj.model.Subject;
import com.schedule.proj.repository.CooperationRepository;
import com.schedule.proj.security.jwt.JwtProvider;
import com.schedule.proj.service.CooperationService;
import com.schedule.proj.service.SubjectService;
import com.schedule.proj.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "api/subject")
public class SubjectController {
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final CooperationService cooperationService;
    private final JwtProvider jwtProvider;

    @GetMapping
    @ResponseBody
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }


    @MethodParamsRes
    @GetMapping(path = "{subjectId}")
    @ResponseBody
    public Subject getSubject(@PathVariable Integer subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @PostMapping
    @ResponseBody
    @ExecutionTime
    @MethodParamsRes
    public Subject addSubject(@RequestBody @Valid Subject subject) {
        return subjectService.createSubject(subject);
    }

    @PutMapping(path = "{subjectId}")
    @ExecutionTime
    @MethodParamsRes
    public Subject updateSubject(@RequestBody Subject subject) {
        return subjectService.updateSubject(subject);
    }

    @DeleteMapping(path = "{subjectId}")
    @ExecutionTime
    @MethodParamsRes
    public void deleteSubject(@PathVariable Integer subjectId) {
        subjectService.deleteSubject(subjectId);
    }

    // todo: add students, getStudents(subjId), getTeacher(subjId), deleteStudent(subjId, studentId) ???

    @GetMapping("/teachersSubject")
    public ResponseEntity<?> findTeachersSubjectByToken(HttpServletRequest request){

        return ResponseEntity.ok(subjectService.findTeachersSubjectByToken(request));
    }
    @GetMapping("/studentSubject")
    @ExecutionTime
    @MethodParamsRes
    public ResponseEntity<?> findTStudentSubjectByToken(HttpServletRequest request){
        return ResponseEntity.ok(subjectService.findStudentubjectByToken(request));
    }
    @DeleteMapping("/deletetSubject")
    public ResponseEntity<?> deleteSubjectByToken(HttpServletRequest request , @RequestBody SubjectGroupDTO subjectGroupDTO){
        cooperationService.deleSybjectforStudent(request ,  subjectGroupDTO);
        return ResponseEntity.ok(subjectService.findStudentubjectByToken(request));
    }

    @PostMapping(path = "/delete/{subjectId}")
    public void deleteSubjectPost(@PathVariable Integer subjectId) {
        subjectService.deleteSubject(subjectId);
    }

    @GetMapping("/subjectByWeek")
    public ResponseEntity<?> findsubjectByWeek(HttpServletRequest request,@RequestParam(required = false) String week){
        return ResponseEntity.ok(subjectService.findStudentSubjectByTokenAndWeek( request , week));
    }

    @PostMapping("/edit/{subjectId}")
    public String editSubject(@ModelAttribute("subject") SubjectDTO subject, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "subject-edit";
        }

        Subject newSubj = new Subject(subject.getSubjectId(), subject.getSubjectTeacher(), subject.getSubjectName(),
                subject.getDayOfWeek(), LocalTime.parse(subject.getSubjectTime(), DateTimeFormatter.ofPattern("HH:mm")),
                subject.getSubjectGroup(), subject.getSubjectFaculty(), subject.getSubjectSpeciality(), subject.getEducationFormat());

        subjectService.updateSubject(newSubj);
        return "redirect:/api/admin/schedule?speciality=" + subject.getSubjectSpeciality();
    }

    @GetMapping("/edit/{subjectId}")
    public String editSubject(@PathVariable Integer subjectId, Model model){
        model.addAttribute("subject", subjectService.getSubjectDTO(subjectId));
        model.addAttribute("times",
                new LocalTime[]{LocalTime.of(8, 30),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 40),
                        LocalTime.of(13, 30),
                        LocalTime.of(15, 0),
                        LocalTime.of(16, 30),
                        LocalTime.of(18, 0)});
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "subject-edit";
    }

    @GetMapping("/add")
    public String addSubject(Model model){
        model.addAttribute("subject", new SubjectDTO());
        model.addAttribute("times",
                new LocalTime[]{LocalTime.of(8, 30),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 40),
                        LocalTime.of(13, 30),
                        LocalTime.of(15, 0),
                        LocalTime.of(16, 30),
                        LocalTime.of(18, 0)});
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "subject-add";
    }

    @PostMapping("/add")
    public String addSubjectP(@ModelAttribute("subject") SubjectDTO subject, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "subject-add";
        }

        Subject newSubj = new Subject(0, subject.getSubjectTeacher(), subject.getSubjectName(),
                subject.getDayOfWeek(), LocalTime.parse(subject.getSubjectTime(), DateTimeFormatter.ofPattern("HH:mm")),
                subject.getSubjectGroup(), subject.getSubjectFaculty(), subject.getSubjectSpeciality(), subject.getEducationFormat());

        subjectService.createSubject(newSubj);
        return "redirect:/api/admin/schedule?speciality=" + subject.getSubjectSpeciality();
    }

}
