package com.skillzy.skillzywebapp.Contollers;

import com.skillzy.skillzywebapp.DTOs.CourseDtos.CoursesDto;
import com.skillzy.skillzywebapp.DTOs.LoginRequest;
import com.skillzy.skillzywebapp.DTOs.CourseDtos.SingleCourseDto;
import com.skillzy.skillzywebapp.DTOs.UserRequestDto;
import com.skillzy.skillzywebapp.Models.Instructor;
import com.skillzy.skillzywebapp.Services.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    private HomeService homeService;


    public HomeController(HomeService homeService){
        this.homeService = homeService;

    }


    @GetMapping("")
    public List<CoursesDto> getAllCourses(){
        return homeService.getAllCourses();
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<SingleCourseDto> getOneCourse(@PathVariable Long id){
       return new ResponseEntity(homeService.getOneCourse(id),HttpStatus.OK);
    }

    @GetMapping("/course/{courseid}/instructor")
    public ResponseEntity<Instructor> getInstructor(@PathVariable long courseid){
        return new ResponseEntity<>(homeService.getInstructor(courseid),HttpStatus.OK);
    }

    @PostMapping("/signup")
     public ResponseEntity<?> addUser(@RequestBody UserRequestDto user){
        return new ResponseEntity<>(homeService.addUser(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> checkUser(@RequestBody LoginRequest loginRequest){
        homeService.checkUser(loginRequest);
        return new ResponseEntity<>("Login Success", HttpStatus.OK);
    }

    @GetMapping("/session")
    public String SessionId(HttpServletRequest httpServletRequest){
        return "session id id "+httpServletRequest.getSession().getId();
    }


}
