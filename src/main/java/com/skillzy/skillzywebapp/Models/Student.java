package com.skillzy.skillzywebapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseModel{


    @OneToOne
    @JoinColumn(name = "user_id") // FK referencing User
    private User user;


    @ManyToMany
    @JoinTable(name = "enrollments",
            joinColumns = @JoinColumn(name = "Student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> enrolledCourses = new HashSet<>();

    @OneToMany(mappedBy = "student",cascade = {CascadeType.REMOVE})
    private Set<Review> reviews = new HashSet<>();


}
