package ru.aston.sort_app.sort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.sort_app.interfaces.SortCriteria;
import ru.aston.sort_app.utils.QuickSort;
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
        SortCriteria byId = SortCriteria.BY_ID;
        List<Movie> moviesById = new ArrayList<>(movies);
        QuickSort.sort(moviesById, byId.getComparator());
        movies.sort(byId.getComparator());
        assertEquals(movies, moviesById);
    }

    @Test
    public void testSortByName() {
        SortCriteria byName = SortCriteria.BY_NAME;
        List<Movie> moviesByName = new ArrayList<>(movies);
        QuickSort.sort(moviesByName, byName.getComparator());
        movies.sort(byName.getComparator());
        assertEquals(movies, moviesByName);
    }

    @Test
    public void testSortByYear() {
        SortCriteria byYear = SortCriteria.BY_YEAR;
        List<Movie> moviesByYear = new ArrayList<>(movies);
        QuickSort.sort(moviesByYear, byYear.getComparator());
        movies.sort(byYear.getComparator());
        assertEquals(movies, moviesByYear);
    }

    @Test
    public void testSortByDirector() {
        SortCriteria byDir = SortCriteria.BY_DIRECTOR;
        List<Movie> moviesByDir = new ArrayList<>(movies);
        QuickSort.sort(moviesByDir, byDir.getComparator());
        movies.sort(byDir.getComparator());
        assertEquals(movies, moviesByDir);
    }

    @Test
    public void testSortByRating() {
        SortCriteria byRate = SortCriteria.BY_RATING;
        List<Movie> moviesByRating = new ArrayList<>(movies);
        QuickSort.sort(moviesByRating, byRate.getComparator());
        movies.sort(byRate.getComparator());
        assertEquals(movies, moviesByRating);
    }
}