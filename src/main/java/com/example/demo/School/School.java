package com.example.demo.School;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.example.demo.Student.Student;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class School {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL) // See explanation below
    @JsonManagedReference
    private List<Student> students;
}
