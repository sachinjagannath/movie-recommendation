package com.sachin.movies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // Find movies where the average user review is >= minRating
    // This uses a JOIN and Group By to filter by crowd-sourced rating
    // Having filters after grouping (like where but for aggregated results )

    @Query("""
            SELECT m from Movie m
            JOIN Review r ON r.movie.id = m.id
            GROUP BY m.id
            HAVING AVG(r.rating) >= :minRating
            ORDER BY AVG(r.rating) DESC 
        """)
        List<Movie> findByAverageUserRatingGreaterThanEqual(@Param("minRating") double minRating);
}
