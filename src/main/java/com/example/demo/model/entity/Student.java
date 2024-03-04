package com.example.demo.model.entity;


import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.*;


@Data
@RequiredArgsConstructor
@Entity
@Getter
@Setter
public class Student {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String imagePath;

    @ManyToOne(cascade = CascadeType.PERSIST) // Or CascadeType.ALL for more operations
    @JoinColumn(name = "school_id")
    @JsonBackReference
    private School school;


}
