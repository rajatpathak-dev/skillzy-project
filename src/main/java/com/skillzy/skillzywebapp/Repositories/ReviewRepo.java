package com.skillzy.skillzywebapp.Repositories;

import com.skillzy.skillzywebapp.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review,Long> {

    @Query("SELECT COALESCE(ROUND(AVG(r.rating), 1), 0.0) FROM Review r WHERE r.course.id = :courseId")
    Float findAverageRatingByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT r FROM Review r WHERE r.course.id = :courseId AND r.student.id = :studentId")
    Optional<Review> findReviewByCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);


}
