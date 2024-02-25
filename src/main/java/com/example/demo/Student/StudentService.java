package com.example.demo.Student;

import com.example.demo.School.School;
import com.example.demo.School.SchoolRepository;
import org.springframework.stereotype.Service;

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
        return studentRepository.save(studentToUpdate);
    }
    public void addNewStudent(Student student) {
        studentRepository.save(student);
    }
    public Student deleteStudent(Long id) {
        studentRepository.deleteById(id);
        return null;
    }

    public void addStudentToSchool(Long studentId, Long schoolId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        School school = schoolRepository.findById(schoolId).orElseThrow();

        school.getStudents().add(student);
        student.setSchool(school);

        studentRepository.save(student); // Saves changes to both
    }
}
