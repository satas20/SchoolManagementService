package com.example.demo.Student;

import com.example.demo.School.School;
import com.example.demo.School.SchoolRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class StudentService {

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
        return studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Student with id " + id + " does not exist"));
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
        return studentRepository.save(studentToUpdate);
    }
    public void addNewStudent(Student student) {
        studentRepository.save(student);
    }
    public Student deleteStudent(Long id) {
        try {
            Path filePath = Paths.get(getStudentById(id).getImagePath());
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

        school.getStudents().add(student);
        student.setSchool(school);

        studentRepository.save(student); // Saves changes to both
    }
}
