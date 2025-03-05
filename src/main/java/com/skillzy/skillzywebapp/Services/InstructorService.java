package com.skillzy.skillzywebapp.Services;

import com.skillzy.skillzywebapp.DTOs.CourseDtos.CoursesDto;
import com.skillzy.skillzywebapp.DTOs.CourseDtos.SingleCourseDto;
import com.skillzy.skillzywebapp.Exceptions.CourseNotFoundException;
import com.skillzy.skillzywebapp.Exceptions.UserNotFoundException;
import com.skillzy.skillzywebapp.Models.Course;
import com.skillzy.skillzywebapp.Models.Instructor;
import com.skillzy.skillzywebapp.Models.Student;
import com.skillzy.skillzywebapp.Models.User;
import com.skillzy.skillzywebapp.Repositories.CourseRepo;
import com.skillzy.skillzywebapp.Repositories.InstructorRepo;
import com.skillzy.skillzywebapp.Repositories.ReviewRepo;
import com.skillzy.skillzywebapp.Repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class InstructorService {
    private InstructorRepo instructorRepo;
    private UserRepo userRepo;
    private CourseRepo courseRepo;
    private ReviewRepo reviewRepo;

    public Instructor addInstructor(Long userId, Instructor instructor) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(userId);
        if(userOptional.isEmpty()){
            throw  new UserNotFoundException("user with id "+userId+" does not exist");
        }

        User user = userOptional.get();

        instructor.setAbout(instructor.getAbout());
        instructor.setTotalStudents(0L);
        instructor.setUser(user);

        return instructorRepo.save(instructor);
    }

    public SingleCourseDto addCourse(Long userId, Course course) throws UserNotFoundException{
        Optional<Instructor> instructorOptional = instructorRepo.findByUserId(userId);
        if(instructorOptional.isEmpty()){
            throw  new UserNotFoundException("Instructor with id "+userId+" does not exist");
        }

        Instructor instructor = instructorOptional.get();
        course.setInstructor(instructor);
        course.setLearners(0l);
        instructor.getCreatedCourses().add(course);
        return convertCourseToSingleCourseDto(courseRepo.save(course));

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

    public Set<CoursesDto> getAllCoursesOfInstructor(Long userId) {
        Optional<Instructor> instructorOptional = instructorRepo.findByUserId(userId);
        if(instructorOptional.isEmpty()){
            throw  new UserNotFoundException("Instructor with id "+userId+" does not exist");
        }

        Instructor instructor = instructorOptional.get();
        Set<CoursesDto> instructorCourses = new HashSet<>();
        for(Course course:instructor.getCreatedCourses()){
            instructorCourses.add(convertCourseToCourseDto(course));
        }
        return instructorCourses;
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

    public SingleCourseDto updateCourse(Long userId, Long courseId, Course course) throws UserNotFoundException, CourseNotFoundException {
        Optional<Instructor> instructorOptional = instructorRepo.findByUserId(userId);
        if(instructorOptional.isEmpty()){
            throw  new UserNotFoundException("Instructor with id "+userId+" does not exist");
        }

        Instructor instructor = instructorOptional.get();
        Optional<Course> courseOptional = courseRepo.findByIdAndInstructorId(courseId,instructor.getId());
        if(courseOptional.isEmpty()){
            throw  new CourseNotFoundException("Course does not exist");
        }

        Course savedCourse = courseOptional.get();
        if(course.getTitle()!=null){
            savedCourse.setTitle(course.getTitle());
        }

        if(course.getDescription()!=null){
            savedCourse.setDescription(course.getDescription());
        }

        if(course.getPrice() != null){
            savedCourse.setPrice(course.getPrice());
        }

        if(course.getLanguage()!=null){
            savedCourse.setLanguage(course.getLanguage());
        }

        return convertCourseToSingleCourseDto(courseRepo.save(savedCourse));
    }

    public SingleCourseDto deleteCourse(Long userId, Long courseId) throws UserNotFoundException,CourseNotFoundException {
        Optional<Instructor> instructorOptional = instructorRepo.findByUserId(userId);
        if(instructorOptional.isEmpty()){
            throw  new UserNotFoundException("Instructor with id "+userId+" does not exist");
        }

        Instructor instructor = instructorOptional.get();
        Optional<Course> courseOptional = courseRepo.findByIdAndInstructorId(courseId,instructor.getId());
        if(courseOptional.isEmpty()){
            throw  new CourseNotFoundException("Course does not exist");
        }

        Course savedCourse = courseOptional.get();
        Set<Student> students = savedCourse.getStudents();
        for(Student student:students){
            student.getEnrolledCourses().remove(savedCourse);
        }
        savedCourse.getStudents().clear();
        courseRepo.deleteById(savedCourse.getId());

        return convertCourseToSingleCourseDto(savedCourse);
    }
}
