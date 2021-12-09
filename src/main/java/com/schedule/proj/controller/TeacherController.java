package com.schedule.proj.controller;

import com.schedule.proj.logger.ExecutionTime;
import com.schedule.proj.logger.MethodParamsRes;
import com.schedule.proj.model.DTO.StudentGeneralResponseDTO;
import com.schedule.proj.model.DTO.TeacherGeneralResponseDTO;
import com.schedule.proj.model.Teacher;
import com.schedule.proj.service.SubjectService;
import com.schedule.proj.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    @GetMapping()
    @ResponseBody
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping(path = "{teacherId}")
    @ResponseBody
    public Teacher getTeacher(@PathVariable Long teacherId) {
        return teacherService.getTeacher(teacherId);
    }

    @PostMapping(consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    @ExecutionTime
    @MethodParamsRes
    public Teacher addTeacher(@RequestBody @Valid Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }



    @Operation(summary = "update teacher by token")
    @PutMapping("/UpdateTeacher/")
    @ExecutionTime
    @MethodParamsRes
    ResponseEntity<String> updateTeacherByToken
            (HttpServletRequest req, @RequestBody TeacherGeneralResponseDTO dto) {
        return teacherService.updateTeachertByToken(dto,req);
    }

}
