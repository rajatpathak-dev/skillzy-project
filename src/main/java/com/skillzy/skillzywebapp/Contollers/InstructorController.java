package com.skillzy.skillzywebapp.Contollers;

import com.skillzy.skillzywebapp.DTOs.CourseDtos.CoursesDto;
import com.skillzy.skillzywebapp.DTOs.CourseDtos.SingleCourseDto;
import com.skillzy.skillzywebapp.Models.Course;
import com.skillzy.skillzywebapp.Models.Instructor;
import com.skillzy.skillzywebapp.Services.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/{userId}/instructor")
public class InstructorController {
    private InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }


    @GetMapping("/course")
    public ResponseEntity<Set<CoursesDto>> getAllCoursesOfInstructor(@PathVariable Long userId){
        return  new ResponseEntity<>(instructorService.getAllCoursesOfInstructor(userId),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Instructor> addInstructor(@PathVariable Long userId, @RequestBody Instructor instructor){
        return new ResponseEntity<>(instructorService.addInstructor(userId, instructor), HttpStatus.OK);
    }

    @PostMapping("/course")
    public ResponseEntity<SingleCourseDto> addCourse(@PathVariable Long userId,@RequestBody Course course){
        return  new ResponseEntity<>(instructorService.addCourse(userId,course),HttpStatus.OK);
    }

    @PatchMapping("/course/{courseId}")
    public ResponseEntity<SingleCourseDto> updateCourse(@PathVariable Long userId,@PathVariable Long courseId,@RequestBody Course course){
        return  new ResponseEntity<>(instructorService.updateCourse(userId,courseId,course),HttpStatus.OK);
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<SingleCourseDto> deleteCourse(@PathVariable Long userId,@PathVariable Long courseId){
        return  new ResponseEntity<>(instructorService.deleteCourse(userId,courseId),HttpStatus.OK);
    }
}
