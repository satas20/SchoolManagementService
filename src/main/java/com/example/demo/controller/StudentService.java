package com.example.demo.controller;

import com.example.demo.model.entity.OutputMessage;
import com.example.demo.model.entity.School;
import com.example.demo.model.repository.SchoolRepository;
import com.example.demo.model.entity.Student;
import com.example.demo.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {


    @Autowired
    private  SimpMessagingTemplate messagingTemplate;
    private  final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    public StudentService(StudentRepository studentRepository, SchoolRepository schoolRepository) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
    }


    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Object getStudentByName(String name) {
        return studentRepository.findStudentByName(name).orElseThrow(() -> new IllegalStateException("Student with name " + name + " does not exist"));
    }


    public Student updateStudent(Long id, Student student) {
        Student studentToUpdate = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Student with id " + id + " does not exist"));
        studentToUpdate.setName(student.getName());
        studentToUpdate.setEmail(student.getEmail());
        studentToUpdate.setPhone(student.getPhone());
        studentToUpdate.setImagePath(student.getImagePath());
        messagingTemplate.convertAndSend("/topic/notification",
                new OutputMessage("System Log", "StudentUpdated- "+student.getId()+" "+student.getName(), "time"));
        return studentRepository.save(studentToUpdate);
    }
    public void addNewStudent(Student student) {

        studentRepository.save(student);
        messagingTemplate.convertAndSend("/topic/notification",
                new OutputMessage("System Log:", "student_added - ID:"+student.getId()+" "+student.getName(), "time"));
    }
    public Student deleteStudent(Long id) {
        try {
            Student st=getStudentById(id);
            if (st==null){
                return null;
            }
            messagingTemplate.convertAndSend("/topic/notification",
                    new OutputMessage("System Log", "StudentDeleted - "+st.getName(), "time"));
            Path filePath = Paths.get(st.getImagePath());
            Files.delete(filePath);
            System.out.println("File deleted successfully.");
        } catch (NoSuchFileException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error deleting file: " + e.getMessage());
        }
        studentRepository.deleteById(id);

        return null;
    }
    public File getStudentImage(Long id) {
        Student student=studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Student with id " + id + " does not exist"));
        Path file = Path.of(student.getImagePath());
        File image = new File(file.toString());
        return image;
    }
    public void addStudentToSchool(Long studentId, Long schoolId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        School school = schoolRepository.findById(schoolId).orElseThrow();
        messagingTemplate.convertAndSend("/topic/notification",
                new OutputMessage("System Log",
                        "Student registered - "+studentId+" " +student.getName() +", to school "+schoolId+" "+school.getName(),
                        "time"));
        school.getStudents().add(student);
        student.setSchool(school);
        try {
            studentRepository.save(student);
        }catch (Exception e){
            throw new RuntimeException();
        }// Saves changes to both
    }
}
