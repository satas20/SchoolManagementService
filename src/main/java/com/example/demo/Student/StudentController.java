package com.example.demo.Student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class StudentController {
    String uploadDirectory = "images/";
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
    @GetMapping("/api/v1/student/image/{id}")
    public ResponseEntity<byte[]>  getStudentImage(@PathVariable Long id) throws IOException {
        File imageFile= studentService.getStudentImage(id);
        byte[] imageData = Files.readAllBytes(imageFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set the correct content type
        headers.setContentLength(imageData.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageData);
    }




    @PostMapping(value = "/api/v1/student", consumes = "multipart/form-data")
    public ResponseEntity<String> fileUploading(@RequestParam("file") MultipartFile file,  @RequestParam("studentData")  String studentData) throws IOException {

        Student student = new Student();
        if (file.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            student = objectMapper.readValue(studentData, Student.class);
        }
        else {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = Paths.get(uploadDirectory + fileName);

            // Ensure the directory exists (create if it does not)
            Files.createDirectories(targetLocation.getParent());

            // Save the file to disk using Files.copy
            Files.copy(file.getInputStream(), targetLocation);

            ObjectMapper objectMapper = new ObjectMapper();
            student = objectMapper.readValue(studentData, Student.class);

            student.setImagePath(targetLocation.toString());
        }


        studentService.addNewStudent(student);
        return ResponseEntity.ok("Successfully created student" + student.toString());
    }

    @PutMapping(value = "api/v1/student/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateStudent(@PathVariable Long id,
                                                @RequestParam("file") MultipartFile file,
                                                @RequestParam("studentData")  String studentData) throws IOException {


        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }
        else {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = Paths.get(uploadDirectory + fileName);

            // Ensure the directory exists (create if it does not)
            Files.createDirectories(targetLocation.getParent());

            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation);

            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(studentData, Student.class);

            student.setImagePath(targetLocation.toString());

            studentService.updateStudent(id, student);
        }

        return ResponseEntity.ok("Successfully updated student" + studentData.toString());
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
