package com.schedule.proj.controller;


import com.schedule.proj.model.DTO.SubjectGroupDTO;
import com.schedule.proj.security.jwt.JwtProvider;
import com.schedule.proj.service.CooperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/Cooperation")
public class CooperationController {

    @Autowired
    CooperationService cooperationService;

    @Autowired
    JwtProvider jwtProvider;

    // @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/cooperation")
    public ResponseEntity<String> createCooperation(HttpServletRequest request, @RequestBody SubjectGroupDTO subjectGroupDTO) {
        try {
            cooperationService.createCooperation(request, subjectGroupDTO);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.LOCKED);
        }
    }
}


