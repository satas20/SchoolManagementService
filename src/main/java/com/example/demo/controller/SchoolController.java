package com.example.demo.controller;

import com.example.demo.model.entity.School;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/api/v1/school")
    @Operation(security = { @SecurityRequirement(name = "Bearer Authentication") })

    public List<School> getSchools() {
        return schoolService.getSchools();
    }
    @GetMapping("/api/v1/school/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer Authentication") })

    public School getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    @Operation(security = { @SecurityRequirement(name = "Bearer Authentication") })
    @PostMapping("/api/v1/school")
    public void registerNewSchool(@RequestBody School school) {
        schoolService.addNewSchool(school);
    }

}
