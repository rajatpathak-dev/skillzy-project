package com.skillzy.skillzywebapp.Repositories;

import com.skillzy.skillzywebapp.Models.Instructor;
import com.skillzy.skillzywebapp.Models.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepo extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByUserId(Long userId);
}
