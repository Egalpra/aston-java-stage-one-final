package ru.aston.sort_app.handlers;

import ru.aston.sort_app.model.Movie;
import ru.aston.sort_app.utils.BinarySearch;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class BinarySearchHandler implements UserChoiceHandler {

    private final List<Movie> movies;
    private final Scanner scanner;
    private final BinarySearch<Movie> binarySearch;
    private final Logger logger = Logger.getLogger(BinarySearchHandler.class.getName());

    public BinarySearchHandler(List<Movie> movies) {
        this.movies = movies;
        this.scanner = new Scanner(System.in);
        this.binarySearch = new BinarySearch<>();
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

        for (SearchStrategy strategy : SearchStrategy.values()) {
            System.out.println(strategy.getChoice() + " - " + strategy.getDescription());
        }

        String choice = scanner.nextLine().trim();
        logger.info("Пользователь выбрал критерий поиска: " + choice);

        SearchStrategy strategy = SearchStrategy.fromChoice(choice);

        if (strategy != null) {
            logger.info("Выполнение стратегии поиска: " + strategy.getDescription());
            strategy.execute(movies, binarySearch, scanner);
        } else {
            logger.warning("Неверный выбор пользователя: " + choice);
            System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }
}
