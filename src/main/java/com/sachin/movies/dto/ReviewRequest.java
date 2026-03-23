package com.sachin.movies.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(
    @NotBlank(message="Reviewer name is requried")
    String reviewerName,

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must be at most 10")
    int rating,
    
    // Comment is optional — a user might just leave a star rating
    String comment
){}
