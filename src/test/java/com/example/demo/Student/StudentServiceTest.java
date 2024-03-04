package com.example.demo.Student;

import com.example.demo.model.repository.SchoolRepository;
import com.example.demo.controller.StudentService;
import com.example.demo.model.entity.Student;
import com.example.demo.model.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DataJpaTest
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
     @Mock
     private StudentRepository studentRepository;
     @Mock
     private SchoolRepository schoolRepository;

    @InjectMocks
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository, schoolRepository);
    }

    @Test
    void getStudents() {
        //when
        underTest.getStudents();
        //then
        verify(studentRepository).findAll();
    }
    @Test
    @Transactional
    void addNewStudent() {
        //when
        Student student = new Student();
        student.setName("testStudent");
        underTest.addNewStudent(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        List<Student> students = studentRepository.findAll();
        assertEquals(capturedStudent, student);
    }
    @Test
    void deleteStudent() {

        assertNull(underTest.deleteStudent(1L));
    }

    @Test
    void getStudentById() {
        StudentRepository studentRepositoryMock = Mockito.mock(StudentRepository.class);
        Student expectedStudent = new Student();
        when(studentRepositoryMock.findById(1L)).thenReturn(Optional.of(expectedStudent));
        StudentService studentService = new StudentService(studentRepositoryMock, schoolRepository);


        Student actualStudent = studentService.getStudentById(1L);


        verify(studentRepositoryMock).findById(1L);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getStudentByName() {
        StudentService studentService = mock(StudentService.class);
        Student expectedStudent = new Student();
        expectedStudent.setName("TestStudent");
        when(studentService.getStudentByName("TestStudent")).thenReturn(expectedStudent);
        assertEquals(studentService.getStudentByName("TestStudent"), expectedStudent);
    }
}