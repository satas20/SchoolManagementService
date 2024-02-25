package com.example.demo.Student;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/api/v1/student")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/api/v1/student/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }


    @PostMapping("/api/v1/student")
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @PutMapping("/api/v1/student/{id}")
    public void updateStudent(@PathVariable Long id, @RequestBody Student student) {
        studentService.updateStudent(id, student);
    }

    @DeleteMapping("/api/v1/student/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PutMapping("/api/v1/student/{studentId}/school/{schoolId}")
    public void addStudentToSchool(@PathVariable Long studentId, @PathVariable Long schoolId) {
        studentService.addStudentToSchool(studentId, schoolId);
    }


}
