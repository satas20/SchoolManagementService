package com.example.demo.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Getter
@Setter
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
