package com.sachin.movies.dto;

/**
 * Output DTO returned to the client for a movie.
 *
 * Now includes:
 *   - averageUserRating  — calculated from all user reviews (null if no reviews yet)
 *   - totalReviews       — how many reviews this movie has
 *
 * The original 'rating' field is the editorial/curated rating set when the movie was added.
 * 'averageUserRating' is the crowd-sourced rating from actual user reviews.
 */
public record MovieResponse (
        Long id,
        String title,
        String genre,
        int releaseYear,
        double rating,              // editorial rating (set by admin when adding movie)
        String description,
        Double averageUserRating,   // calculated from reviews — null if no reviews yet
        long totalReviews           // count of all reviews for this movie
){}
