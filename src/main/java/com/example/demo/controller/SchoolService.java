package com.example.demo.controller;

import com.example.demo.model.entity.OutputMessage;
import com.example.demo.model.entity.School;
import com.example.demo.model.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    @Autowired
    private  SimpMessagingTemplate messagingTemplate;
    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public void addNewSchool(School school) {
        schoolRepository.save(school);
        messagingTemplate.convertAndSend("/topic/notification",
                new OutputMessage("System Log", "School added- "+school.getId()+" "+school.getName(), "time"));
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
         schoolRepository.save(schoolToUpdate);

        messagingTemplate.convertAndSend("/topic/notification",
                new OutputMessage("System Log", "School- updated "+school.getId()+" "+school.getName(), "time"));
        return schoolToUpdate;
    }



    public List<School> getSchools() {
        return schoolRepository.findAll();
    }
}
