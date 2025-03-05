package com.skillzy.skillzywebapp.Contollers;

import com.skillzy.skillzywebapp.DTOs.CourseDtos.CoursesDto;
import com.skillzy.skillzywebapp.DTOs.CourseDtos.SingleCourseDto;
import com.skillzy.skillzywebapp.Models.User;
import com.skillzy.skillzywebapp.Repositories.UserRepo;
import com.skillzy.skillzywebapp.Services.HomeService;
import com.skillzy.skillzywebapp.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/")
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

    @PostMapping("/signup")
     public ResponseEntity<?> addUser(@RequestBody User user){
        return new ResponseEntity<>(homeService.addUser(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> checkUser(@RequestBody User user){
        homeService.checkUser(user);
        return new ResponseEntity<>("Login Success", HttpStatus.OK);
    }
}
