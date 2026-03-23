package com.sachin.movies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sachin.movies.dto.ReviewRequest;
import com.sachin.movies.dto.ReviewResponse;
import com.sachin.movies.model.Movie;
import com.sachin.movies.model.Review;
import com.sachin.movies.repository.MovieRepository;
import com.sachin.movies.repository.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    /*
    Add a new review for a movie
    Throws if : movie not found, or reviewer already reviewed this movie
    */

    @Transactional
    public ReviewResponse addReview(Long movieId, ReviewRequest request){
        // Verify if the movie exists
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow( () -> new EntityNotFoundException("Movie not found with id: "+movieId));
            
        // Prevent duplicate reviews from the same reviewer for the same movie
        reviewRepository.findByMovieIdAndReviewerNameIgnoreCase(movieId, request.reviewerName())
                        .ifPresent(existing -> {
                            throw new IllegalStateException("'" + request.reviewerName() + "' has already reviewed this movie.");
                        });

        // Build and save the movie

        Review review = Review.builder()
                        .movie(movie)
                        .reviewerName(request.reviewerName())
                        .rating(request.rating())
                        .comment(request.comment())
                        .build();
        return toResponse(reviewRepository.save(review));
    }


    // -Read--------------------------
    /**
     * Get all reviews for a movie, newest first.
     */
    public List<ReviewResponse> getReviewForMovie(Long movieId){
        if (!movieRepository.existsById(movieId)){
            throw new EntityNotFoundException("Movie not found with id: "+movieId);
        }
        return reviewRepository.findByMovieIdOrderByCreatedAtDesc(movieId)
                                .stream()
                                .map(this::toResponse)
                                .toList();
    }

    /**
     * Get a single review by its ID, scoped to a movie.
    */
    public ReviewResponse getReviewById(Long movieId, Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow( () -> new EntityNotFoundException(
                            "Review not found with id: "+reviewId
                        ));
    
    // Safety check - ensure the review actually belongs to this movie
    if (!review.getMovie().getId().equals(movieId)){
        throw new EntityNotFoundException(
            "Review "+ reviewId + " does not belong to movie "+movieId);
        }              
        return toResponse(review);
    }

        // ── Update ────────────────────────────────────────────────────────────────
 
    /**
     * Update an existing review (rating and/or comment).
     */

    @Transactional
    public ReviewResponse updateReview(Long movieId, Long reviewId, ReviewRequest request){
        // Verify the review exists AND belongs to this movie
        if(!reviewRepository.existsByIdAndMovieId(reviewId, movieId)){
            throw new EntityNotFoundException(
                "Review "+ reviewId + " not found for the movie "+ movieId);
        }
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setRating(request.rating());
        review.setComment(request.comment());

        // reviewerName is not updated - you cant change who wrote the review

        return toResponse(reviewRepository.save(review));
    }

        // ── Delete ────────────────────────────────────────────────────────────────
 
    /**
     * Delete a review, scoped to a movie.
     */

    @Transactional
    public void deleteReview(Long movieId, Long reviewId){
        if(!reviewRepository.existsByIdAndMovieId(reviewId, movieId)){
            throw new EntityNotFoundException(
                "Review "+ reviewId + " not found for movie "+movieId
            );
        }
        reviewRepository.deleteById(reviewId);
    }

    // Helper---------------------------------------------
    
    private ReviewResponse toResponse(Review review){
        return new ReviewResponse(
            review.getId(),
            review.getMovie().getId(),
            review.getMovie().getTitle(),
            review.getReviewerName(),
            review.getRating(),
            review.getComment(),
            review.getCreatedAt(),
            review.getUpdatedAt()
        );
    }

}
