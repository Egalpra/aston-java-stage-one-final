package ru.aston.sort_app.sort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.sort_app.QuickSort;
import ru.aston.sort_app.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    private List<Movie> movies;

    @BeforeEach
    void setUp() {
        movies = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            movies.add(new Movie.MovieBuilder()
                    .id((long) i)
                    .name("1" + i)
                    .year(1900 + i)
                    .director("2" + i)
                    .rate((double) i)
                    .build());
        }
    }

    @Test
    public void testSortById() {
        List<Movie> moviesById = new ArrayList<>(movies);
        QuickSort.sort(moviesById, Movie.BY_ID);
        movies.sort(Movie.BY_ID);
        assertEquals(movies, moviesById);
    }

    @Test
    public void testSortByName() {
        List<Movie> moviesByName = new ArrayList<>(movies);
        QuickSort.sort(moviesByName, Movie.BY_NAME);
        movies.sort(Movie.BY_NAME);
        assertEquals(movies, moviesByName);
    }

    @Test
    public void testSortByYear() {
        List<Movie> moviesByYear = new ArrayList<>(movies);
        QuickSort.sort(moviesByYear, Movie.BY_YEAR);
        movies.sort(Movie.BY_YEAR);
        assertEquals(movies, moviesByYear);
    }

    @Test
    public void testSortByDirector() {
        List<Movie> moviesByDir = new ArrayList<>(movies);
        QuickSort.sort(moviesByDir, Movie.BY_DIR);
        movies.sort(Movie.BY_DIR);
        assertEquals(movies, moviesByDir);
    }

    @Test
    public void testSortByRating() {
        List<Movie> moviesByRating = new ArrayList<>(movies);
        QuickSort.sort(moviesByRating, Movie.BY_RATE);
        movies.sort(Movie.BY_RATE);
        assertEquals(movies, moviesByRating);
    }
}