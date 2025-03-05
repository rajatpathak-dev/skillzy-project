package com.skillzy.skillzywebapp.DTOs.CourseDtos;

import com.skillzy.skillzywebapp.Models.Enums.Language;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursesDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Language language;
    private String instructorName;
    private Float rating;

}
