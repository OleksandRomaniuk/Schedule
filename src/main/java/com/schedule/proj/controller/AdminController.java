package com.schedule.proj.controller;


import com.schedule.proj.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "AddBD")
    @PostMapping("/addBD")
    ResponseEntity<?> addBD() {
        ResponseEntity<String> result = adminService.createBD();
        return new ResponseEntity<String>(String.valueOf(result), HttpStatus.OK);
    }

}
