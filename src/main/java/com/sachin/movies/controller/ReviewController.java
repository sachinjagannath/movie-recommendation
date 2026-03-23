package com.sachin.movies.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.movies.dto.ReviewRequest;
import com.sachin.movies.dto.ReviewResponse;
import com.sachin.movies.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/movies/{movieId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    
    private final ReviewService reviewService;

     /** POST /api/movies/{movieId}/reviews — add a review */
     @PostMapping
     public ResponseEntity<ReviewResponse> addReview(
                                    @PathVariable Long movieId,
                                    @Valid @RequestBody ReviewRequest request
     ){
        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(reviewService.addReview(movieId, request));
     }

    /** GET /api/movies/{movieId}/reviews — get all reviews for a movie */
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable Long movieId){
        return ResponseEntity.ok(reviewService.getReviewForMovie(movieId));    
    }

    /** GET /api/movies/{movieId}/reviews/{reviewId} — get one review */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReviewById(
        @PathVariable Long movieId,
        @PathVariable Long reviewId
    ){
        return ResponseEntity.ok(reviewService.getReviewById(movieId, reviewId));
    }

    /** PUT /api/movies/{movieId}/reviews/{reviewId} — update a review */
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
        @PathVariable Long movieId,
        @PathVariable Long reviewId,
        @Valid @RequestBody ReviewRequest request
    ) {
        return ResponseEntity.ok(reviewService.updateReview(movieId, reviewId, request));
    }

    /** DELETE /api/movies/{movieId}/reviews/{reviewId} — delete a review */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
        @PathVariable Long movieId,
        @PathVariable Long reviewId
    ){
        reviewService.deleteReview(movieId, reviewId);
        return  ResponseEntity.noContent().build();
    }
}
