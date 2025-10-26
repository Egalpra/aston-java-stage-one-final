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
    public void testSortByName() {
        List<Movie> booksByName = new ArrayList<>(movies);
        QuickSort.sort(booksByName, Movie.BY_NAME);
        movies.sort(Movie.BY_NAME);
        assertEquals(movies, booksByName);
    }

    @Test
    public void testSortByDate() {
        List<Movie> booksByDate = new ArrayList<>(movies);
        QuickSort.sort(booksByDate, Movie.BY_DATE);
        movies.sort(Movie.BY_DATE);
        assertEquals(movies, booksByDate);
    }

    @Test
    public void testSortByRating() {
        List<Movie> booksByRating = new ArrayList<>(movies);
        QuickSort.sort(booksByRating, Movie.BY_RATE);
        movies.sort(Movie.BY_RATE);
        assertEquals(movies, booksByRating);
    }
}