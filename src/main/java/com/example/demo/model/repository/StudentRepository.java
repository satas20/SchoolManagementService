package com.example.demo.model.repository;

import com.example.demo.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByName(String name);


    @Query("SELECT s FROM Student s WHERE LENGTH(s.name) > 5")
    List<Student> findStudentsWithNameLongerThanFive();
}
