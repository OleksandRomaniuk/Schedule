package com.schedule.proj.controller;

import com.schedule.proj.exсeption.InvalidStudentException;
import com.schedule.proj.logger.ExecutionTime;
import com.schedule.proj.logger.MethodParamsRes;
import com.schedule.proj.model.DTO.StudentGeneralResponseDTO;
import com.schedule.proj.model.Student;
import com.schedule.proj.repository.StudentRepository;
import com.schedule.proj.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping()
    @ResponseBody
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }


    @PostMapping(consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    @ExecutionTime
    @MethodParamsRes
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentService.addStudent(student);
    }



    @DeleteMapping(path = "{studentId}")
    @ExecutionTime
    @MethodParamsRes
    public void deleteStudent(@PathVariable Integer studentId) {
        studentService.deleteStudent(studentId);
    }

    @Operation(summary = "update student by token")
    @PutMapping("/UpdateStudent/")
    @ExecutionTime
    @MethodParamsRes
    ResponseEntity<String> updateStudentByToken
            (HttpServletRequest req, @RequestBody StudentGeneralResponseDTO dto) {
        return studentService.updateStudentByToken(dto,req);
    }

}
