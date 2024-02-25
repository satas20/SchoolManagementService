package com.example.demo.Student;


import com.example.demo.School.School;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;


@Data
@RequiredArgsConstructor
@Entity
public class Student {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private String phone;

    @ManyToOne // Represents the many side in one-to-many
    @JoinColumn(name = "school_id") // Creates a foreign key column
    @JsonBackReference
    private School school;
}
