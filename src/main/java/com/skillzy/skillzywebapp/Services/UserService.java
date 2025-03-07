package com.skillzy.skillzywebapp.Services;

import com.skillzy.skillzywebapp.DTOs.CourseDtos.CoursesDto;
import com.skillzy.skillzywebapp.DTOs.CourseDtos.SingleCourseDto;
import com.skillzy.skillzywebapp.Exceptions.CourseNotFoundException;
import com.skillzy.skillzywebapp.Exceptions.ReviewNotFoundException;
import com.skillzy.skillzywebapp.Exceptions.UserNotFoundException;
import com.skillzy.skillzywebapp.Models.*;
import com.skillzy.skillzywebapp.Repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    private StudentRepo studentRepo;
    private ReviewRepo reviewRepo;
    private CourseRepo courseRepo;
    private InstructorRepo instructorRepo;




    public Set<CoursesDto> getAllCoursesOfUser(Long id) throws UserNotFoundException {
        Optional<User> user = userRepo.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("user with " + id + " not exist");
        }

        Optional<Student> student = studentRepo.findByUserId(user.get().getId());
        Set<CoursesDto> courseDtos  = new HashSet<>();
        if(student.isPresent()){
            Set<Course> courses = student.get().getEnrolledCourses();
            for(Course course : courses){
                courseDtos.add(convertCourseToCourseDto(course));
            }
        }

        return  courseDtos;
    }

    public CoursesDto convertCourseToCourseDto(Course course){
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




    public SingleCourseDto addUserToCourse(Long userId, Long courseId) throws UserNotFoundException, CourseNotFoundException {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " does not exist");
        }

        Optional<Course> courseOpt = courseRepo.findById(courseId);
        if (courseOpt.isEmpty()) {
            throw new CourseNotFoundException("Course with ID " + courseId + " does not exist");
        }

        Course course = courseOpt.get();
        User user = userOpt.get();


        Student student = studentRepo.findByUserId(userId).orElseGet(()->{
            Student newStudent = new Student();
            newStudent.setUser(user);
            return studentRepo.save(newStudent);
        });

        student.getEnrolledCourses().add(course);
        course.setLearners(course.getLearners()+1);
        course.getInstructor().setTotalStudents(course.getInstructor().getTotalStudents()+1);
        course.getStudents().add(student);



        studentRepo.save(student);
        courseRepo.save(course);
        return convertCourseToSingleCourseDto(course);
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

    public SingleCourseDto getOneCoursesOfUser(Long userId, Long courseId) throws UserNotFoundException,CourseNotFoundException {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("user with " + userId + " not exist");
        }

        Optional<Course> courseOpt = courseRepo.findById(courseId);
        if (courseOpt.isEmpty()) {
            throw new CourseNotFoundException("Course with ID " + courseId + " does not exist");
        }

        Optional<Student> student = studentRepo.findByUserId(user.get().getId());
        Course course = null;
        if(student.isPresent() && student.get().getEnrolledCourses().contains(courseOpt.get())){
          course = courseOpt.get();
        }else{
            throw  new CourseNotFoundException("user is not enrolled in this course please enroll");
        }


        return convertCourseToSingleCourseDto(course);
    }

    public Review addUserReview(Long userId, Long courseId, Review review) throws UserNotFoundException,CourseNotFoundException{
        Optional<Student> studentOptional = studentRepo.findByUserId(userId);
        if(studentOptional.isEmpty()){
            throw new UserNotFoundException("user with "+userId+" not exist");
        }

        Student student = studentOptional.get();
        Optional<Course> courseOptional = studentRepo.findCourseByStudentIdAndCourseId(student.getId(),courseId);

        if(courseOptional.isEmpty()){
            throw new CourseNotFoundException("you are not enrolled in this course");
        }
        Course course = courseOptional.get();
        review.setName(student.getUser().getName());
        review.setStudent(student);
        review.setCourse(course);
        student.getReviews().add(review);
        reviewRepo.save(review);
        return review;
    }


    public Set<Review> getAllReviewsOfUser(Long userId) throws UserNotFoundException {
        if(userRepo.findById(userId).isEmpty()){
            throw new UserNotFoundException("user with id " +userId+" not exist");
        }

        Optional<Student> studentOptional = studentRepo.findByUserId(userId);
        Set<Review> reviews = new HashSet<>();
        if(studentOptional.isPresent()){
            reviews = studentOptional.get().getReviews();
        }
        return reviews;
    }

    public Review updateUserReview(Long userId,Long courseId,Review review) throws UserNotFoundException, ReviewNotFoundException {
        if(userRepo.findById(userId).isEmpty() || studentRepo.findByUserId(userId).isEmpty()){
            throw new UserNotFoundException("user with id " +userId+" not exist");
        }

        Optional<Review> reviewOptional = reviewRepo.findReviewByCourseIdAndStudentId(courseId,studentRepo.findByUserId(userId).get().getId());
        if(reviewOptional.isEmpty()){
            throw new ReviewNotFoundException("You do not have any review for this course");
        }

        Review savedReview = reviewOptional.get();
        if(review!=null && review.getRating()!=null){
            savedReview.setRating(review.getRating());
        }

        if(review!=null && review.getComment()!=null){
            savedReview.setComment(review.getComment());
        }

        return reviewRepo.save(savedReview);
    }

    public User deleteUser(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id "+userId+" not exixt");
        }
        User user = userOptional.get();
        userRepo.deleteById(userId);
        return user;
    }

    public User updateUser(Long userId, User user)throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id "+userId+" not exixt");
        }
        User savedUser = userOptional.get();

        if(user.getName()!=null){
            savedUser.setName(user.getName());
        }

        if(user.getPassword()!=null){
            savedUser.setPassword(user.getPassword());
        }

        return userRepo.save(savedUser);
    }

    public User getUser(long id) throws UserNotFoundException{
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("user with id "+id+" not found");
        }

        User user = userOptional.get();
        return user;
    }


}
