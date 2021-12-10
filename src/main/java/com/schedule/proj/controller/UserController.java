package com.schedule.proj.controller;


import com.schedule.proj.exсeption.RegistrationException;
import com.schedule.proj.model.DTO.*;
import com.schedule.proj.model.*;
import com.schedule.proj.security.jwt.CustomUserDetails;
import com.schedule.proj.security.jwt.JwtProvider;
import com.schedule.proj.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    CooperationService cooperationService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/{id}")
    public String getUserPage(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        //model.addAttribute("user", new User());
        return "user-page";
    }

    @GetMapping("/{id}/profile")
    public String getUserPageProfile(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        return "user-page-profile";
    }

    @GetMapping("/profile")
    public String getUserPageProfile(@CookieValue("Authorization") String token, Model model){
        String userLogin = jwtProvider.getLoginFromToken(token);
        User user = userService.findUserByEmail(userLogin);
        model.addAttribute("user", userService.getUserById(user.getId()));
        return "user-page-profile";
    }

    @GetMapping("/{id}/profile/edit")
    public String getUserPageProfileEdit(@PathVariable("id")Long id, Model model){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        model.addAttribute("years", new int[]{1, 2, 3, 4, 5, 6});
        return "user-page-profile-edit";
    }

    @PostMapping("/{id}/profile/edit")
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

    @PostMapping("/{id}/add/group")
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

    @GetMapping("/{id}/subjects")
    public String getUserAllSubjects(@PathVariable("id")Long id,
                                     Model model,
                                     HttpServletRequest request){
        User user = userService.getUserByRequest(request);
        List<Subject> subjc = new ArrayList<>();
        if (user.getUserRole().equals(UserRole.STUDENT)){
            subjc = subjectService.findStudentubjectByToken(request);
        }
        else {
            subjc = subjectService.findTeachersSubjectByToken(request);
        }
        model.addAttribute("user", user);
        model.addAttribute("subjects",subjc);
        return "user-page-subjects";
    }

    @GetMapping("/{id1}/deleteSubject/{id2}")
    public String deleteSubjectByToken(@PathVariable("id1")Long uid,
                                       @PathVariable("id2")Long sid,
                                       Model model, HttpServletRequest request) {
        Subject s = subjectService.getSubject(sid.intValue());
        SubjectGroupDTO subjectGroupDTO = new SubjectGroupDTO(s.getSubjectGroup(),s.getSubjectName());
        cooperationService.deleSybjectforStudent(request , subjectGroupDTO);
        return "redirect:/api/user/"+uid.toString()+"/subjects";
    }

    @GetMapping("{id}/changePassword")
    public String changePasswordPassword(@ModelAttribute("loginDTO") PasswordDTO passwordDTO,
                                         Model model, @PathVariable Long id){
        model.addAttribute("user", userService.getUserById(id.intValue()));
        model.addAttribute("passwordDTO", passwordDTO);
        return "user-page-reset-password";
    }

    @Operation(summary = "change password")
    @PostMapping("{id}/changePassword")
    public String changePassword(@PathVariable Long id,
                                 @ModelAttribute("passwordDTO") PasswordDTO dto,
                                 HttpServletRequest request ) {
        try {
            userService.changePassword(request, dto.getOldPassword(), dto.getNewPassword());
            User u = userService.getUserByRequest(request);
            CustomUserDetails user = CustomUserDetails.fromUserEntityToCustomUserDetails(u);
            String message = authenticationService.logout(request, user);
            return "redirect:/api/auth/login";
        } catch (RegistrationException | UsernameNotFoundException e) {
            return "user-page-reset-password";
        }
    }

    @PostMapping ("{id}/logout")
    public String logout(HttpServletRequest request){
        User u = userService.getUserByRequest(request);
        CustomUserDetails user = CustomUserDetails.fromUserEntityToCustomUserDetails(u);
        String message = authenticationService.logout(request, user);
        return "redirect:/api/auth/login";
    }





    @GetMapping("/{id}/admin/schedule")
    public String loginUserFor(@PathVariable Long id, @CookieValue(HttpHeaders.AUTHORIZATION) String token, Model model,
                                @RequestParam String speciality){
        // String userLogin = jwtProvider.getLoginFromToken(token);
        User user = userService.getUserById(id.intValue());

        model.addAttribute("curSpec", speciality);
        model.addAttribute("user", user);
//        return "user-page";
        model.addAttribute("days", subjectService.getScheduleDaysBySpecility(speciality));
        return "admin-schedule";
    }
    @PostMapping(path = "/{id}/admin/subject/delete/{subjectId}")
    public void deleteSubjectPost(@PathVariable Long id, @PathVariable Integer subjectId) {
        subjectService.deleteSubject(subjectId);
    }

    @PostMapping("/{id}/admin/subject/edit/{subjectId}")
    public String editSubject(@PathVariable Long id, @ModelAttribute("subject") SubjectDTO subject, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "subject-edit";
        }

        Subject newSubj = new Subject(subject.getSubjectId(), subject.getSubjectTeacher(), subject.getSubjectName(),
                subject.getDayOfWeek(), LocalTime.parse(subject.getSubjectTime(), DateTimeFormatter.ofPattern("HH:mm")),
                subject.getSubjectGroup(), subject.getSubjectFaculty(), subject.getSubjectSpeciality(), subject.getEducationFormat());

        subjectService.updateSubject(newSubj);
        return "redirect:/api/user/1/admin/schedule?speciality=" + subject.getSubjectSpeciality();
    }

    @GetMapping("/{id}/admin/subject/edit/{subjectId}")
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

    @GetMapping("/{id}/admin/subject/add")
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

    @PostMapping("/{id}/admin/subject/add")
    public String addSubjectP(@ModelAttribute("subject") SubjectDTO subject, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "subject-add";
        }

        Subject newSubj = new Subject(0, subject.getSubjectTeacher(), subject.getSubjectName(),
                subject.getDayOfWeek(), LocalTime.parse(subject.getSubjectTime(), DateTimeFormatter.ofPattern("HH:mm")),
                subject.getSubjectGroup(), subject.getSubjectFaculty(), subject.getSubjectSpeciality(), subject.getEducationFormat());

        subjectService.createSubject(newSubj);
        return "redirect:/api/user/1/admin/schedule?speciality=" + subject.getSubjectSpeciality();
    }




}

