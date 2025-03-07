package com.skillzy.skillzywebapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Instructor extends BaseModel{

    @Transient
    private String name;
    private String about;
    private Long totalStudents;

    @OneToOne
    @JoinColumn(name = "user_id") // FK referencing User
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "instructor",cascade = {CascadeType.REMOVE},fetch = FetchType.LAZY)
    private Set<Course> createdCourses = new HashSet<>();

    public String getName() {
        name = this.user.getName();
        return name;
    }
}
