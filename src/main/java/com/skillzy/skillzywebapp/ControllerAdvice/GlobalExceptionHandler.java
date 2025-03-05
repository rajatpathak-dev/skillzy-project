package com.skillzy.skillzywebapp.ControllerAdvice;

import com.skillzy.skillzywebapp.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistException(EmailAlreadyExistException emailAlreadyExistException){
                return  new ResponseEntity<>(emailAlreadyExistException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFoundException(EmailNotFoundException emailNotFoundException){
        return  new ResponseEntity<>(emailNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<?> handleReviewNotFoundException(ReviewNotFoundException reviewNotFoundException){
        return  new ResponseEntity<>(reviewNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(PasswordNotmatchException.class)
    public ResponseEntity<?> handlePasswordNotMatchException(PasswordNotmatchException passwordNotmatchException){
        return  new ResponseEntity<>(passwordNotmatchException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleIdNotFoundException(UserNotFoundException idNotFoundException){
        return  new ResponseEntity<>(idNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<?> handleCourseNotFoundException(CourseNotFoundException courseNotFoundException){
        return  new ResponseEntity<>(courseNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
