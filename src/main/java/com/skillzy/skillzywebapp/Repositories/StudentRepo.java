package com.skillzy.skillzywebapp.Repositories;

import com.skillzy.skillzywebapp.Models.Course;
import com.skillzy.skillzywebapp.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Long> {

    Optional<Student> findByUserId(Long userId);
    @Query("SELECT c FROM Student s JOIN s.enrolledCourses c WHERE s.id = :studentId AND c.id = :courseId")
    Optional<Course> findCourseByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}
