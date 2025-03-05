package com.skillzy.skillzywebapp.Repositories;

import com.skillzy.skillzywebapp.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course,Long> {

    Optional<Course> findByIdAndInstructorId(Long courseId, Long instructorId);
}
