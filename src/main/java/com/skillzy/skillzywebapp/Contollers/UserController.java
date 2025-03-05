package com.skillzy.skillzywebapp.Contollers;

import com.skillzy.skillzywebapp.DTOs.CourseDtos.CoursesDto;
import com.skillzy.skillzywebapp.DTOs.CourseDtos.SingleCourseDto;
import com.skillzy.skillzywebapp.Models.Review;
import com.skillzy.skillzywebapp.Models.User;
import com.skillzy.skillzywebapp.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/{id}")
public class UserController {

   private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Set<CoursesDto>> getAllCoursesOfUser(@PathVariable Long id){

        return new ResponseEntity( userService.getAllCoursesOfUser(id), HttpStatus.OK);
    }

    @PatchMapping("")
    @DeleteMapping("")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId,@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(userId,user),HttpStatus.OK);
    }



    @DeleteMapping("")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long userId){
        return new ResponseEntity<>(userService.deleteUser(userId),HttpStatus.OK);
    }

    @GetMapping("/course/{courseid}")
    public ResponseEntity<SingleCourseDto> getOneCourseOfUser(@PathVariable("id") Long user_id,@PathVariable("courseid") Long course_id){
      return new ResponseEntity<>(userService.getOneCoursesOfUser(user_id,course_id),HttpStatus.OK);
    }

    @PatchMapping("/course/{courseid}")
    public ResponseEntity<SingleCourseDto> addUserToCourse(@PathVariable("id") Long user_id,@PathVariable("courseid") Long course_id){
        return new ResponseEntity<>(userService.addUserToCourse(user_id,course_id),HttpStatus.OK);
    }

    @GetMapping("/review")
    public ResponseEntity<Set<Review>> getAllReviewsOfUser(@PathVariable("id") Long userId){
        return new ResponseEntity(userService.getAllReviewsOfUser(userId),HttpStatus.OK);
    }


    @PostMapping("/course/{courseid}/review")
    public ResponseEntity<Review> adduUserReview(@PathVariable("id") Long userId, @PathVariable("courseid") Long courseId, @RequestBody Review review){
        return new ResponseEntity(userService.addUserReview(userId,courseId,review),HttpStatus.OK);
    }

    @PatchMapping("/course/{courseId}/review")
    public ResponseEntity<Review> updateUserReview(@PathVariable Long id ,@PathVariable Long courseId,@RequestBody Review review){
        return new ResponseEntity<>(userService.updateUserReview(id,courseId,review),HttpStatus.OK);
    }


}
