package com.sachin.movies.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sachin.movies.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovieIdOrderByCreatedAtDesc(Long movieId);

    //Check if a reviewer has already reviewed a movie.
    // (prevents duplicate reviews from same person for same movie)
    Optional<Review> findByMovieIdAndReviewerNameIgnoreCase(Long movieId, String reviewerName);
    
    // Count how many reviews a movie has
    long countcountByMovieId(Long movieId);

    //Calculate average rating for a movie from all its reviews.
    //@Query lets us write JPQL (like SQL but uses entity /field names, not table/column names)

    @Query("Select AVG(r.rating) from Review r where r.movie.id = :movieId")
    Optional<Double> findAverageRatingByMovieId(@Param("movieId") Long movieId);

    // Check if a specific review belongs to a specific movie (for update/delete safety)
    boolean existsByIdAndMovieId(Long reviewId, Long movieId);
}
