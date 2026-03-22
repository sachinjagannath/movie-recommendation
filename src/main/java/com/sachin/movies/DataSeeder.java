package com.sachin.movies;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sachin.movies.model.Movie;
import com.sachin.movies.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {
 
    private final MovieRepository movieRepository;
 
    @Override
    public void run(String... args) {
        if (movieRepository.count() > 0) {
            log.info("Database already has data - skipping seed.");
            return;
        }
 
        List<Movie> movies = List.of(
                Movie.builder().title("The Shawshank Redemption").genre("Drama")
                        .releaseYear(1994).rating(9.3)
                        .description("Two imprisoned men bond over years, finding solace.").build(),
 
                Movie.builder().title("The Godfather").genre("Crime")
                        .releaseYear(1972).rating(9.2)
                        .description("An aging patriarch transfers control of his empire.").build(),
 
                Movie.builder().title("The Dark Knight").genre("Action")
                        .releaseYear(2008).rating(9.0)
                        .description("Batman faces the Joker, a criminal mastermind.").build(),
 
                Movie.builder().title("Inception").genre("Sci-Fi")
                        .releaseYear(2010).rating(8.8)
                        .description("A thief who enters the dreams of others.").build(),
 
                Movie.builder().title("Pulp Fiction").genre("Crime")
                        .releaseYear(1994).rating(8.9)
                        .description("Interconnected stories of criminals in Los Angeles.").build(),
 
                Movie.builder().title("Forrest Gump").genre("Drama")
                        .releaseYear(1994).rating(8.8)
                        .description("Life of a man with a low IQ but a big heart.").build(),
 
                Movie.builder().title("Interstellar").genre("Sci-Fi")
                        .releaseYear(2014).rating(8.6)
                        .description("Explorers travel through a wormhole in space.").build(),
 
                Movie.builder().title("The Matrix").genre("Sci-Fi")
                        .releaseYear(1999).rating(8.7)
                        .description("A hacker discovers reality is a simulation.").build(),
 
                Movie.builder().title("Goodfellas").genre("Crime")
                        .releaseYear(1990).rating(8.7)
                        .description("The story of Henry Hill and his life in the mob.").build(),
 
                Movie.builder().title("The Lion King").genre("Animation")
                        .releaseYear(1994).rating(8.5)
                        .description("A lion prince flees and must reclaim his kingdom.").build()
        );
 
        movieRepository.saveAll(movies);
        log.info("Seeded {} movies into the database.", movies.size());
    }
}