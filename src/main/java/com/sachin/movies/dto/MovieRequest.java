package com.sachin.movies.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MovieRequest(
     @NotBlank(message = "Title is required")
     String title,

    @NotBlank(message = "Genre is required")
     String genre,

    @Min(value = 1888, message="Release year must be 1888 or later")
     int releaseYear,

    @DecimalMax(value="10.0", message = "Rating must be between 0.00 and 10.0")
    @DecimalMin(value="0.0", message = "Rating must be between 0.00 and 10.0")
     double rating,

     String description
) {}
