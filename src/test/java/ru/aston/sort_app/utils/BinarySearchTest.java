package ru.aston.sort_app.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.sort_app.model.Movie;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTest {

    private BinarySearch<Movie> binarySearch;
    private Movie[] movies;

    @BeforeEach
    void setUp() {
        binarySearch = new BinarySearch<>();

        // Создаем отсортированный массив фильмов по ID
        movies = new Movie[] {
                Movie.builder().id(1L).name("Фильм 1").year(2020).director("Режиссер 1").rate(8.5).build(),
                Movie.builder().id(2L).name("Фильм 2").year(2021).director("Режиссер 2").rate(7.8).build(),
                Movie.builder().id(3L).name("Фильм 3").year(2019).director("Режиссер 3").rate(9.2).build(),
                Movie.builder().id(4L).name("Фильм 4").year(2022).director("Режиссер 4").rate(8.0).build(),
                Movie.builder().id(5L).name("Фильм 5").year(2023).director("Режиссер 5").rate(7.5).build()
        };
    }

    @Test
    void testSearchExistingElement() {
        Movie target = Movie.builder().id(3L).name("Фильм 3").year(2019).director("Режиссер 3").rate(9.2).build();

        int result = binarySearch.search(movies, target);

        assertEquals(2, result);
    }

    @Test
    void testSearchNonExistingElement() {
        Movie target = Movie.builder().id(10L).name("Несуществующий фильм").year(2020).director("Режиссер").rate(5.0)
                .build();

        int result = binarySearch.search(movies, target);

        assertEquals(-1, result);
    }

    @Test
    void testSearchFirstElement() {
        Movie target = Movie.builder().id(1L).name("Фильм 1").year(2020).director("Режиссер 1").rate(8.5).build();

        int result = binarySearch.search(movies, target);

        assertEquals(0, result);
    }

    @Test
    void testSearchLastElement() {
        Movie target = Movie.builder().id(5L).name("Фильм 5").year(2023).director("Режиссер 5").rate(7.5).build();

        int result = binarySearch.search(movies, target);

        assertEquals(4, result);
    }

    @Test
    void testSearchRecursiveExistingElement() {
        Movie target = Movie.builder().id(3L).name("Фильм 3").year(2019).director("Режиссер 3").rate(9.2).build();

        int result = binarySearch.searchRecursive(movies, target);

        assertEquals(2, result);
    }

    @Test
    void testSearchRecursiveNonExistingElement() {
        Movie target = Movie.builder().id(10L).name("Несуществующий фильм").year(2020).director("Режиссер").rate(5.0)
                .build();

        int result = binarySearch.searchRecursive(movies, target);

        assertEquals(-1, result);
    }

    @Test
    void testSearchWithNullArray() {
        Movie target = Movie.builder().id(1L).name("Фильм").year(2020).director("Режиссер").rate(8.0).build();

        assertThrows(IllegalArgumentException.class, () -> {
            binarySearch.search(null, target);
        });
    }

    @Test
    void testSearchWithNullTarget() {
        assertThrows(IllegalArgumentException.class, () -> {
            binarySearch.search(movies, null);
        });
    }

    @Test
    void testSearchRecursiveWithNullArray() {
        Movie target = Movie.builder().id(1L).name("Фильм").year(2020).director("Режиссер").rate(8.0).build();

        assertThrows(IllegalArgumentException.class, () -> {
            binarySearch.searchRecursive(null, target);
        });
    }

    @Test
    void testSearchRecursiveWithNullTarget() {
        assertThrows(IllegalArgumentException.class, () -> {
            binarySearch.searchRecursive(movies, null);
        });
    }

    @Test
    void testFindFirstWithDuplicates() {
        // Создаем массив с дубликатами
        Movie[] moviesWithDuplicates = new Movie[] {
                Movie.builder().id(1L).name("Фильм 1").year(2020).director("Режиссер 1").rate(8.5).build(),
                Movie.builder().id(2L).name("Фильм 2").year(2021).director("Режиссер 2").rate(7.8).build(),
                Movie.builder().id(2L).name("Фильм 2").year(2021).director("Режиссер 2").rate(7.8).build(),
                Movie.builder().id(2L).name("Фильм 2").year(2021).director("Режиссер 2").rate(7.8).build(),
                Movie.builder().id(3L).name("Фильм 3").year(2019).director("Режиссер 3").rate(9.2).build()
        };

        Movie target = Movie.builder().id(2L).name("Фильм 2").year(2021).director("Режиссер 2").rate(7.8).build();

        int result = binarySearch.findFirst(moviesWithDuplicates, target);

        assertEquals(1, result);
    }

    @Test
    void testFindLastWithDuplicates() {
        // Создаем массив с дубликатами
        Movie[] moviesWithDuplicates = new Movie[] {
                Movie.builder().id(1L).name("Фильм 1").year(2020).director("Режиссер 1").rate(8.5).build(),
                Movie.builder().id(2L).name("Фильм 2").year(2021).director("Режиссер 2").rate(7.8).build(),
                Movie.builder().id(2L).name("Фильм 2").year(2021).director("Режиссер 2").rate(7.8).build(),
                Movie.builder().id(2L).name("Фильм 2").year(2021).director("Режиссер 2").rate(7.8).build(),
                Movie.builder().id(3L).name("Фильм 3").year(2019).director("Режиссер 3").rate(9.2).build()
        };

        Movie target = Movie.builder().id(2L).name("Фильм 2").year(2021).director("Режиссер 2").rate(7.8).build();

        int result = binarySearch.findLast(moviesWithDuplicates, target);

        assertEquals(3, result);
    }

    @Test
    void testEmptyArray() {
        Movie[] emptyArray = new Movie[0];
        Movie target = Movie.builder().id(1L).name("Фильм").year(2020).director("Режиссер").rate(8.0).build();

        int result = binarySearch.search(emptyArray, target);

        assertEquals(-1, result);
    }

    @Test
    void testSingleElementArray() {
        Movie[] singleElementArray = new Movie[] {
                Movie.builder().id(1L).name("Фильм").year(2020).director("Режиссер").rate(8.0).build()
        };

        Movie target = Movie.builder().id(1L).name("Фильм").year(2020).director("Режиссер").rate(8.0).build();

        int result = binarySearch.search(singleElementArray, target);

        assertEquals(0, result);
    }
}
