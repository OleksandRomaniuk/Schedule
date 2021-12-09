package com.schedule.proj.controller;


import com.schedule.proj.logger.ExecutionTime;
import com.schedule.proj.logger.MethodParamsRes;
import com.schedule.proj.model.*;
import com.schedule.proj.model.DTO.StudentGeneralResponseDTO;
import com.schedule.proj.model.DTO.SubjectGroupDTO;
import com.schedule.proj.model.DTO.TeacherGeneralResponseDTO;
import com.schedule.proj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;


@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    CooperationService cooperationService;

    @GetMapping("/{id}")
    public String getUserPage(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        return "user-page";
    }

    @GetMapping("/{id}/profile")
    public String getUserPageProfile(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        return "user-page-profile";
    }

    @GetMapping("/{id}/profile/edit")
    public String getUserPageProfileEdit(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        model.addAttribute("years", new int[]{1, 2, 3, 4, 5, 6});
        return "user-page-profile-edit";
    }

    @PostMapping("/{id}/profile/edit")
    @ExecutionTime
    @MethodParamsRes
    public String updateUserPageProfileEdit(@ModelAttribute("user") User user,
                                            @PathVariable("id")Long id, Model model,
                                            HttpServletRequest request,
                                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("user", userService.getUserById(id.intValue()));
            return "user-page-profile-edit";
        }
        if(userService.getUserById(id.intValue()).getUserRole().equals(UserRole.STUDENT)){
            Student st = user.getStudent();
            studentService.updateStudentByToken(new StudentGeneralResponseDTO(null,
                    null,
                    st.getFaculty(),
                    st.getSpeciality(),
                    st.getStudentYear()), request );
        }else{
            Teacher st = user.getTeacher();
            teacherService.updateTeachertByToken(new TeacherGeneralResponseDTO(
                    st.getFaculty(),
                    st.getCathedra(),
                    st.getRank(),
                    null,
                    null), request );
        }
        return "redirect:/api/user/"+id.toString()+"/profile";
    }

    @GetMapping("/{id}/add")
    public String getUserPageAddSubject(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        model.addAttribute("subject", new Subject());
        model.addAttribute("subjects", new TreeSet(subjectService.getAllSubjects()) {
        });
        return "user-page-profile-add-subject";
    }

    @PostMapping("/{id}/add")
    @ExecutionTime
    @MethodParamsRes
    public String updateUserPageAddSubject(@ModelAttribute("subject") Subject subject,
                                      @PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        model.addAttribute("subject", subject);
        model.addAttribute("groups", subjectService.findByName(subject.getSubjectName())
                .stream()
                .map(Subject::getSubjectGroup)
                .toArray());
        return "user-page-profile-add-group";
    }

//    @GetMapping("/{id}/add/group")
//    public String getUserPageAddGroup(@ModelAttribute("subject") Subject subject,
//                                      @PathVariable("id")Long id, Model model){
//        model.addAttribute("user", userService.getUserById(id.intValue()));
//        model.addAttribute("subject", subject);
//        model.addAttribute("groups", subjectService.findByName(subject.getSubjectName())
//                .stream()
//                .map(Subject::getSubjectGroup)
//                .toArray());
//        return "user-page-profile-add-group";
//    }

    @PostMapping("/{id}/add/group")
    @ExecutionTime
    @MethodParamsRes
    public String updateUserSubject(@ModelAttribute("subject") Subject subject,
                                            @PathVariable("id")Long id, Model model,
                                            HttpServletRequest request){
        SubjectGroupDTO subjectGroupDTO = new SubjectGroupDTO(subject.getSubjectGroup(), subject.getSubjectName());
            try {
                cooperationService.createCooperation(request, subjectGroupDTO);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            } finally {
                return "redirect:/api/user/"+id.toString()+"/add";
            }
    }

    @GetMapping("/{id}/schedule")
    public String getUserSchedule(@PathVariable("id")Long id,
                                  @RequestParam(required = false) String week,
                                  HttpServletRequest request,
                                  Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        if(week == null){
            model.addAttribute("weeks", new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
            return "user-page-schedule";
        }
        List<Subject> subjOnWeek;
        if (userService.getUserById(id.intValue()).getUserRole().equals(UserRole.STUDENT)){
            subjOnWeek = subjectService.findStudentSubjectByTokenAndWeek( request , week);
        }
        else{
            subjOnWeek = subjectService.findTeacherSubjectByTokenAndWeek( request , week);
        }
        model.addAttribute("subjectsSlots", subjectService.getSordetListByTime(subjOnWeek));
        model.addAttribute("days", new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"});
        return "user-page-schedule-week";
    }










}

