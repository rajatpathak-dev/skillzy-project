package com.skillzy.skillzywebapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skillzy.skillzywebapp.Models.Enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Course extends BaseModel{


    private String title;
    private String description;
    private Double price;
    private Long learners;


    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @JsonIgnore
    private Instructor instructor;


    @OneToMany(mappedBy = "course",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<Review> reviews = new HashSet<>();


    @ManyToMany(mappedBy = "enrolledCourses")
    private Set<Student> students = new HashSet<>();
}
