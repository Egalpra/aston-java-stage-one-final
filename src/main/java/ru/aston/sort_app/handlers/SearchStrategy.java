package ru.aston.sort_app.handlers;

import ru.aston.sort_app.model.Movie;
import ru.aston.sort_app.utils.BinarySearch;
import ru.aston.sort_app.utils.MovieSorter;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public enum SearchStrategy {

    SEARCH_BY_ID("1", "Поиск по ID") {
        @Override
        public void execute(List<Movie> movies, BinarySearch<Movie> binarySearch, MovieSorter movieSorter,
                Scanner scanner) {
            System.out.print("Введите ID фильма для поиска: ");
            try {
                Long id = Long.parseLong(scanner.nextLine().trim());
                logger.info("Поиск фильма по ID: " + id);

                Movie searchTarget = Movie.builder()
                        .id(id)
                        .name("")
                        .year(1900)
                        .director("")
                        .rate(0.0)
                        .build();

                logger.info("Сортировка фильмов по ID для бинарного поиска");
                movieSorter.sortById(movies);

                int index = binarySearch.search(movies.toArray(new Movie[0]), searchTarget);

                if (index != -1) {
                    Movie foundMovie = movies.get(index);
                    logger.info("Фильм найден на позиции " + index + ": " + foundMovie.getName());
                    System.out.println("Фильм найден:");
                    printMovieInfo(foundMovie);
                } else {
                    logger.info("Фильм с ID " + id + " не найден");
                    System.out.println("Фильм с ID " + id + " не найден.");
                }

            } catch (NumberFormatException e) {
                logger.warning("Ошибка ввода ID: " + e.getMessage());
                System.out.println("Ошибка: введите корректный ID (число).");
            }
        }
    },

    SEARCH_BY_NAME("2", "Поиск по названию") {
        @Override
        public void execute(List<Movie> movies, BinarySearch<Movie> binarySearch, MovieSorter movieSorter,
                Scanner scanner) {
            System.out.print("Введите название фильма для поиска: ");
            String name = scanner.nextLine().trim();
            logger.info("Поиск фильма по названию: " + name);

            if (name.isEmpty()) {
                logger.warning("Попытка поиска с пустым названием");
                System.out.println("Название не может быть пустым.");
                return;
            }

            Movie searchTarget = Movie.builder()
                    .id(0L)
                    .name(name)
                    .year(1900)
                    .director("")
                    .rate(0.0)
                    .build();

            logger.info("Сортировка фильмов по названию для бинарного поиска");
            movieSorter.sortByName(movies);

            int index = binarySearch.search(movies.toArray(new Movie[0]), searchTarget);

            if (index != -1) {
                Movie foundMovie = movies.get(index);
                logger.info("Фильм найден на позиции " + index + ": " + foundMovie.getName());
                System.out.println("Фильм найден:");
                printMovieInfo(foundMovie);
            } else {
                logger.info("Фильм с названием '" + name + "' не найден");
                System.out.println("Фильм с названием '" + name + "' не найден.");
            }
        }
    },

    SEARCH_BY_YEAR("3", "Поиск по году") {
        @Override
        public void execute(List<Movie> movies, BinarySearch<Movie> binarySearch, MovieSorter movieSorter,
                Scanner scanner) {
            System.out.print("Введите год выпуска фильма для поиска: ");
            try {
                int year = Integer.parseInt(scanner.nextLine().trim());
                logger.info("Поиск фильма по году: " + year);

                Movie searchTarget = Movie.builder()
                        .id(0L)
                        .name("")
                        .year(year)
                        .director("")
                        .rate(0.0)
                        .build();

                logger.info("Сортировка фильмов по году для бинарного поиска");
                movieSorter.sortByYear(movies);

                int index = binarySearch.search(movies.toArray(new Movie[0]), searchTarget);

                if (index != -1) {
                    Movie foundMovie = movies.get(index);
                    logger.info("Фильм найден на позиции " + index + ": " + foundMovie.getName());
                    System.out.println("Фильм найден:");
                    printMovieInfo(foundMovie);
                } else {
                    logger.info("Фильм " + year + " года не найден");
                    System.out.println("Фильм " + year + " года не найден.");
                }

            } catch (NumberFormatException e) {
                logger.warning("Ошибка ввода года: " + e.getMessage());
                System.out.println("Ошибка: введите корректный год (число).");
            }
        }
    },

    SEARCH_BY_DIRECTOR("4", "Поиск по режиссеру") {
        @Override
        public void execute(List<Movie> movies, BinarySearch<Movie> binarySearch, MovieSorter movieSorter,
                Scanner scanner) {
            System.out.print("Введите имя режиссера для поиска: ");
            String director = scanner.nextLine().trim();
            logger.info("Поиск фильма по режиссеру: " + director);

            if (director.isEmpty()) {
                logger.warning("Попытка поиска с пустым именем режиссера");
                System.out.println("Имя режиссера не может быть пустым.");
                return;
            }

            Movie searchTarget = Movie.builder()
                    .id(0L)
                    .name("")
                    .year(1900)
                    .director(director)
                    .rate(0.0)
                    .build();

            logger.info("Сортировка фильмов по режиссеру для бинарного поиска");
            movieSorter.sortByDirector(movies);

            int index = binarySearch.search(movies.toArray(new Movie[0]), searchTarget);

            if (index != -1) {
                Movie foundMovie = movies.get(index);
                logger.info("Фильм найден на позиции " + index + ": " + foundMovie.getName());
                System.out.println("Фильм найден:");
                printMovieInfo(foundMovie);
            } else {
                logger.info("Фильм режиссера '" + director + "' не найден");
                System.out.println("Фильм режиссера '" + director + "' не найден.");
            }
        }
    },

    BACK_TO_MENU("0", "Назад в главное меню") {
        @Override
        public void execute(List<Movie> movies, BinarySearch<Movie> binarySearch, MovieSorter movieSorter,
                Scanner scanner) {
            logger.info("Возврат в главное меню из бинарного поиска");
            System.out.println("Возврат в главное меню...");
        }
    };

    private static final Logger logger = Logger.getLogger(SearchStrategy.class.getName());

    private final String choice;
    private final String description;

    SearchStrategy(String choice, String description) {
        this.choice = choice;
        this.description = description;
    }

    public String getChoice() {
        return choice;
    }

    public String getDescription() {
        return description;
    }

    public abstract void execute(List<Movie> movies, BinarySearch<Movie> binarySearch, MovieSorter movieSorter,
            Scanner scanner);

    public static SearchStrategy fromChoice(String choice) {
        for (SearchStrategy strategy : values()) {
            if (strategy.choice.equals(choice)) {
                return strategy;
            }
        }
        return null;
    }

    protected static void printMovieInfo(Movie movie) {
        System.out.println("ID: " + movie.getId());
        System.out.println("Название: " + movie.getName());
        System.out.println("Год: " + movie.getYear());
        System.out.println("Режиссер: " + movie.getDirector());
        System.out.println("Рейтинг: " + movie.getRate());
        System.out.println("---");
    }
}