package com.skillzy.skillzywebapp.Services;

import com.skillzy.skillzywebapp.DTOs.CourseDtos.CoursesDto;
import com.skillzy.skillzywebapp.DTOs.CourseDtos.LoginRequest;
import com.skillzy.skillzywebapp.DTOs.CourseDtos.SingleCourseDto;
import com.skillzy.skillzywebapp.Exceptions.*;
import com.skillzy.skillzywebapp.Models.Course;
import com.skillzy.skillzywebapp.Models.Instructor;
import com.skillzy.skillzywebapp.Models.Student;
import com.skillzy.skillzywebapp.Models.User;
import com.skillzy.skillzywebapp.Repositories.*;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HomeService {
    private CourseRepo courseRepo;
    private ReviewRepo reviewRepo;
    private UserRepo userRepo;
    private StudentRepo studentRepo;
    private InstructorRepo instructorRepo;

    public HomeService(CourseRepo courseRepo, ReviewRepo reviewRepo, UserRepo userRepo,StudentRepo studentRepo) {
        this.courseRepo = courseRepo;
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
        this.studentRepo = studentRepo;
    }

    public List<CoursesDto> getAllCourses(){
        List<Course> courses = courseRepo.findAll();
        List<CoursesDto> courseDtoList = new ArrayList<>();
        for(Course course:courses){
            courseDtoList.add(convertCourseToCoursesDto(course));
        }

        return courseDtoList;
    }

    public User addUser(User user) throws EmailAlreadyExistException{
        if(userRepo.findByEmail(user.getEmail())!=null){
            throw new EmailAlreadyExistException("This email is already registered. Please use a different email.");
        }

        User user1 = userRepo.save(user);

        return user1;
    }

    public User checkUser(LoginRequest loginRequest) throws EmailNotFoundException,PasswordNotmatchException {
        Optional<User> userOptional = userRepo.findByEmail(loginRequest.getEmail());

        if(userOptional.isEmpty()){
            throw new EmailNotFoundException("Email does not exist");
        }

        User savedUser = userOptional.get();

        if(!savedUser.getPassword().equals(loginRequest.getPassword())){
            throw new PasswordNotmatchException("Password does not match! please retry");
        }

        return savedUser;
    }

    public CoursesDto convertCourseToCoursesDto(Course course){
        CoursesDto courseDto = new CoursesDto();
        courseDto.setId(course.getId());
        courseDto.setTitle(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setPrice(course.getPrice());
        courseDto.setInstructorName(course.getInstructor().getUser().getName());
        courseDto.setRating(reviewRepo.findAverageRatingByCourseId(course.getId()));
        courseDto.setLanguage(course.getLanguage());
        return  courseDto;
    }

    public SingleCourseDto getOneCourse(Long id) throws CourseNotFoundException {
        Optional<Course> course = courseRepo.findById(id);
        if(course.isEmpty()){
            throw  new CourseNotFoundException("Course with "+id+" does not exist");
        }

        SingleCourseDto singleCourseDto = convertCourseToSingleCourseDto(course.get());
        return  singleCourseDto;
    }

    private SingleCourseDto convertCourseToSingleCourseDto(Course course) {
        SingleCourseDto courseDto = new SingleCourseDto();
        courseDto.setId(course.getId());
        courseDto.setTitle(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setPrice(course.getPrice());
        courseDto.setInstructorName(course.getInstructor().getUser().getName());
        courseDto.setRating(reviewRepo.findAverageRatingByCourseId(course.getId()));
        courseDto.setReviewList(course.getReviews());
        courseDto.setLanguage(course.getLanguage());
        return  courseDto;
    }

    public Instructor getInstructor(Long courseId) throws CourseNotFoundException {
        Optional<Course> courseOptional  = courseRepo.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new CourseNotFoundException("course with id "+courseId+" not found");
        }

        Course course = courseOptional.get();

        return course.getInstructor();
    }
}
