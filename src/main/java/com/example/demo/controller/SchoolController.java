package com.example.demo.controller;

import com.example.demo.model.entity.School;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/api/v1/school")
    public List<School> getSchools() {
        return schoolService.getSchools();
    }
    @GetMapping("/api/v1/school/{id}")
    public School getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }
    @PostMapping("/api/v1/school")
    public void registerNewSchool(@RequestBody School school) {
        schoolService.addNewSchool(school);
    }

}
