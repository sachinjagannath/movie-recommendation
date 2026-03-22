package com.sachin.movies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sachin.movies.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

    // Find all movies by genre (case-insensitive){
    List<Movie> findByGenreIgnoreCase(String genre);

     // Find movies with rating >= minRating, sorted highest first
    List<Movie> findByRatingGreaterThanEqualOrderByRatingDesc(double minRating);

    // Find movies released in a specific year
    List<Movie> findByReleaseYear(int year);
}
