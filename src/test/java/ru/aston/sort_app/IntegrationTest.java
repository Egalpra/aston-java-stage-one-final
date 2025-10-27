package ru.aston.sort_app;

import org.junit.jupiter.api.Test;
import ru.aston.sort_app.model.Movie;
import ru.aston.sort_app.utils.BinarySearch;
import ru.aston.sort_app.utils.MovieSorter;
import ru.aston.sort_app.interfaces.SortAlgorithm;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционный тест для проверки работы всех компонентов вместе.
 */
class IntegrationTest {

    @Test
    void testBinarySearchWithMovieSorter() {
        // Создаем список фильмов
        List<Movie> movies = new ArrayList<>();
        movies.add(Movie.builder().id(3L).name("Фильм C").year(2020).director("Режиссер C").rate(8.5).build());
        movies.add(Movie.builder().id(1L).name("Фильм A").year(2019).director("Режиссер A").rate(7.8).build());
        movies.add(Movie.builder().id(2L).name("Фильм B").year(2021).director("Режиссер B").rate(9.2).build());

        // Создаем временный алгоритм сортировки
        SortAlgorithm<Movie> tempSortAlgorithm = new SortAlgorithm<Movie>() {
            @Override
            public void sort(Movie[] array) {
                // Простая сортировка пузырьком
                for (int i = 0; i < array.length - 1; i++) {
                    for (int j = 0; j < array.length - i - 1; j++) {
                        if (array[j].compareTo(array[j + 1]) > 0) {
                            Movie temp = array[j];
                            array[j] = array[j + 1];
                            array[j + 1] = temp;
                        }
                    }
                }
            }

            @Override
            public String getAlgorithmName() {
                return "Bubble Sort (Test)";
            }

            @Override
            public String getBestCaseComplexity() {
                return "O(n)";
            }

            @Override
            public String getAverageCaseComplexity() {
                return "O(n²)";
            }

            @Override
            public String getWorstCaseComplexity() {
                return "O(n²)";
            }
        };

        // Создаем MovieSorter
        MovieSorter movieSorter = new MovieSorter(tempSortAlgorithm);

        // Сортируем фильмы по ID
        movieSorter.sortById(movies);

        // Проверяем, что фильмы отсортированы
        assertEquals(1L, movies.get(0).getId());
        assertEquals(2L, movies.get(1).getId());
        assertEquals(3L, movies.get(2).getId());

        // Создаем BinarySearch
        BinarySearch<Movie> binarySearch = new BinarySearch<>();

        // Ищем фильм с ID = 2
        Movie target = Movie.builder().id(2L).name("Фильм B").year(2021).director("Режиссер B").rate(9.2).build();
        int index = binarySearch.search(movies.toArray(new Movie[0]), target);

        // Проверяем результат
        assertEquals(1, index);
        assertEquals("Фильм B", movies.get(index).getName());
    }

    @Test
    void testMovieComparable() {
        Movie movie1 = Movie.builder().id(1L).name("Фильм A").year(2020).director("Режиссер A").rate(8.0).build();
        Movie movie2 = Movie.builder().id(2L).name("Фильм B").year(2020).director("Режиссер A").rate(8.0).build();
        Movie movie3 = Movie.builder().id(1L).name("Фильм A").year(2020).director("Режиссер A").rate(8.0).build();

        // Проверяем сравнение по ID
        assertTrue(movie1.compareTo(movie2) < 0); // movie1 < movie2 (ID: 1 < 2)
        assertTrue(movie2.compareTo(movie1) > 0); // movie2 > movie1 (ID: 2 > 1)
        assertEquals(0, movie1.compareTo(movie3)); // movie1 == movie3 (одинаковые ID)
    }

    @Test
    void testSearchStrategyIntegration() {
        // Создаем список фильмов
        List<Movie> movies = new ArrayList<>();
        movies.add(Movie.builder().id(1L).name("Матрица").year(1999).director("Вачовски").rate(8.7).build());
        movies.add(Movie.builder().id(2L).name("Интерстеллар").year(2014).director("Нолан").rate(8.6).build());
        movies.add(Movie.builder().id(3L).name("Начало").year(2010).director("Нолан").rate(8.8).build());

        // Создаем временный алгоритм сортировки
        SortAlgorithm<Movie> tempSortAlgorithm = new SortAlgorithm<Movie>() {
            @Override
            public void sort(Movie[] array) {
                java.util.Arrays.sort(array);
            }

            @Override
            public String getAlgorithmName() {
                return "Arrays.sort (Test)";
            }

            @Override
            public String getBestCaseComplexity() {
                return "O(n log n)";
            }

            @Override
            public String getAverageCaseComplexity() {
                return "O(n log n)";
            }

            @Override
            public String getWorstCaseComplexity() {
                return "O(n log n)";
            }
        };

        MovieSorter movieSorter = new MovieSorter(tempSortAlgorithm);
        BinarySearch<Movie> binarySearch = new BinarySearch<>();

        // Тестируем поиск по названию
        movieSorter.sortByName(movies);

        Movie target = Movie.builder().id(0L).name("Интерстеллар").year(0).director("").rate(0.0).build();
        int index = binarySearch.search(movies.toArray(new Movie[0]), target);

        assertEquals(0, index); // "Интерстеллар" должен быть первым после сортировки по названию
        assertEquals("Интерстеллар", movies.get(index).getName());
    }
}
