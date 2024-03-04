package com.example.demo.controller;

import com.example.demo.model.entity.School;
import com.example.demo.model.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public void addNewSchool(School school) {
        schoolRepository.save(school);
    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }

    public School getSchoolById(Long id) {
        return schoolRepository.findById(id).orElseThrow(() -> new IllegalStateException("School with id " + id + " does not exist"));
    }

    public School updateSchool(Long id, School school) {
        School schoolToUpdate = schoolRepository.findById(id).orElseThrow(() -> new IllegalStateException("School with id " + id + " does not exist"));
        schoolToUpdate.setName(school.getName());
        schoolToUpdate.setAddress(school.getAddress());
        return schoolRepository.save(schoolToUpdate);
    }

    public List<School> getSchools() {
        return schoolRepository.findAll();
    }
}
