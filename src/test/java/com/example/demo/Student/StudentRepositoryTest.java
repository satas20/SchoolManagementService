package com.example.demo.Student;

import com.example.demo.model.entity.Student;
import com.example.demo.model.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;



    @Test
    void findStudentByName() {
        //given
        Student student = new Student();
        student.setName("John");
        underTest.save(student);

        //when
        Student student1 = underTest.findStudentByName(student.getName()).get();
        //then
        assertEquals(student, student1);
    }
    @Test
    void findStudentsWithLongName() {
        //given
        Student student = new Student();
        Student student2 = new Student();
        student.setName("asdfasdf");
        student2.setName("sss");
        underTest.save(student);
        underTest.save(student2);

        //when
        List<Student> students = underTest.findStudentsWithNameLongerThanFive();

        //then
        assertThat(students).isEqualTo(List.of(student));
    }

}