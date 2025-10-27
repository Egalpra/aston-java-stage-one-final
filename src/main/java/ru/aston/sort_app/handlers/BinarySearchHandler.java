package ru.aston.sort_app.handlers;

import ru.aston.sort_app.model.Movie;
import ru.aston.sort_app.utils.BinarySearch;
import ru.aston.sort_app.utils.MovieSorter;
import ru.aston.sort_app.interfaces.SortAlgorithm;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class BinarySearchHandler implements UserChoiceHandler {

    private final List<Movie> movies;
    private final Scanner scanner;
    private final BinarySearch<Movie> binarySearch;
    private final MovieSorter movieSorter;
    private final Logger logger = Logger.getLogger(BinarySearchHandler.class.getName());

    public BinarySearchHandler(List<Movie> movies) {
        this.movies = movies;
        this.scanner = new Scanner(System.in);
        this.binarySearch = new BinarySearch<>();
        // TODO: Заменить на реальную реализацию SortAlgorithm из другой ветки
        this.movieSorter = new MovieSorter(createDefaultSortAlgorithm());
    }

    @Override
    public void handle() {
        if (movies.isEmpty()) {
            System.out.println("Список фильмов пуст. Сначала загрузите фильмы из файла.");
            return;
        }

        logger.info("Запуск бинарного поиска фильмов");
        System.out.println("\n=== Бинарный поиск фильмов ===");
        System.out.println("Выберите критерий поиска:");

        // Выводим все доступные стратегии
        for (SearchStrategy strategy : SearchStrategy.values()) {
            System.out.println(strategy.getChoice() + " - " + strategy.getDescription());
        }

        String choice = scanner.nextLine().trim();
        logger.info("Пользователь выбрал критерий поиска: " + choice);

        SearchStrategy strategy = SearchStrategy.fromChoice(choice);

        if (strategy != null) {
            logger.info("Выполнение стратегии поиска: " + strategy.getDescription());
            strategy.execute(movies, binarySearch, movieSorter, scanner);
        } else {
            logger.warning("Неверный выбор пользователя: " + choice);
            System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    // TODO: заменить на нашу реализацию сортировки
    private SortAlgorithm<Movie> createDefaultSortAlgorithm() {
        return new SortAlgorithm<Movie>() {
            @Override
            public void sort(Movie[] array) {
                if (array == null) {
                    throw new IllegalArgumentException("Массив не может быть null");
                }
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
                return "Bubble Sort (временная реализация)";
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
    }
}
