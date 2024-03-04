package com.example.demo.model.repository;

import com.example.demo.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository  extends JpaRepository<School, Long> {
}
