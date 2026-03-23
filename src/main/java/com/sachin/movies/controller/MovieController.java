package com.sachin.movies.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.movies.dto.MovieRequest;
import com.sachin.movies.dto.MovieResponse;
import com.sachin.movies.service.MovieService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    // CRUD ENDPOINTS

    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody MovieRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(request));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id){
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequest request){
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    // RECOMMENDATION ENDPOINTS
    @GetMapping("/recommend/genre")
    public ResponseEntity<List<MovieResponse>> recommendByGenre(@RequestParam String genre){
        return ResponseEntity.ok(movieService.recommendByGenre(genre));
    }

    @GetMapping("recommend/top-rated")
    public ResponseEntity<List<MovieResponse>> recommendTopRated(@RequestParam(defaultValue = "7.0") double minRating){
        return ResponseEntity.ok(movieService.recommendTopRated(minRating));
    }

    public ResponseEntity<List<MovieResponse>> recommendByYear(@RequestParam int year){
        return ResponseEntity.ok(movieService.recommendYear(year));
    }

      /**
     * GET /api/movies/recommend/user-rated?minRating=7.0
     * NEW — recommends movies based on average USER review rating.
     * Only movies with at least one review are returned.
     */

      @GetMapping("/recommend/user-rated")
      public ResponseEntity<List<MovieResponse>> recommendByUserRating(
        @RequestParam(defaultValue = "7.0") double minRating
      ){
        return ResponseEntity.ok(movieService.recommendByUserRating(minRating));
      }
}
