package com.sachin.movies.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
    Long id,
    Long movieId,
    String movieTitle,
    String reviewerName,
    int rating,
    String comment,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
