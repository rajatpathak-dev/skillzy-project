package com.skillzy.skillzywebapp.DTOs.CourseDtos;


import com.skillzy.skillzywebapp.Models.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SingleCourseDto extends CoursesDto {
    private Set<Review> reviewList;
}
