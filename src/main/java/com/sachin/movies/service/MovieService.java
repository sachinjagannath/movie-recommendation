package com.sachin.movies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sachin.movies.dto.MovieRequest;
import com.sachin.movies.dto.MovieResponse;
import com.sachin.movies.model.Movie;
import com.sachin.movies.repository.MovieRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    // CRUD
    public MovieResponse addMovie(MovieRequest request){
        Movie movie = toEntity(request);
        return toResponse(movieRepository.save(movie));
    }

    public List<MovieResponse> getAllMovies(){
        return movieRepository.findAll()
                .stream()
                .map(this :: toResponse)
                .toList();
    }

    public MovieResponse getMovieById(Long id){
        return toResponse(findOrThrow(id));
    }

    public MovieResponse updateMovie(Long id, MovieRequest request){
        Movie existing = findOrThrow(id);
        existing.setTitle(request.title());
        existing.setGenre(request.genre());
        existing.setReleaseYear(request.releaseYear());
        existing.setRating(request.rating());
        existing.setDescription(request.description());
        return toResponse(movieRepository.save(existing));
    }

    public void deleteMovie(Long id){
        findOrThrow(id);
        movieRepository.deleteById(id);
    }

    //---- RECOMMENDATIONS

    public List<MovieResponse> recommendByGenre(String genre){
        return movieRepository.findByGenreIgnoreCase(genre)
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public List<MovieResponse> recommendTopRated(double minRating){
        return movieRepository.findByRatingGreaterThanEqualOrderByRatingDesc(minRating)
            .stream()
            .map(this :: toResponse)
            .toList();
    }

    public List<MovieResponse> recommendYear(int year){
        return movieRepository.findByReleaseYear(year)
            .stream()
            .map(this :: toResponse)
            .toList();
    }

    //---------Helpers

    private Movie findOrThrow(Long id){
        return movieRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: "+id));
    }

    private Movie toEntity(MovieRequest req){
        return Movie.builder()
        .title(req.title())
        .genre(req.genre())
        .releaseYear(req.releaseYear())
        .rating(req.rating())
        .description(req.description())
        .build();
    }

    private MovieResponse toResponse(Movie movie){
        return new MovieResponse(
            movie.getId(),
            movie.getTitle(),
            movie.getGenre(),
            movie.getReleaseYear(),
            movie.getRating(),
            movie.getDescription());
    }
}
