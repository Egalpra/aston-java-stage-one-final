package ru.aston.sort_app.handlers;

import ru.aston.sort_app.interfaces.SortCriteria;
import ru.aston.sort_app.model.Movie;
import ru.aston.sort_app.utils.QuickSort;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class SortHandler implements UserChoiceHandler {

    private final List<Movie> movies;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(SortHandler.class.getName());

    public SortHandler(List<Movie> movies) {
        this.movies = movies;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void handle() {
        if (movies.isEmpty()) {
            System.out.println("Список фильмов пуст. Сначала загрузите фильмы из файла.");
            return;
        }

        logger.info("Запуск сортировки фильмов");
        System.out.println("\n=== Сортировка фильмов ===");
        System.out.println("Выберите критерий сортировки:");
        System.out.println("1 - По ID");
        System.out.println("2 - По названию");
        System.out.println("3 - По году");
        System.out.println("4 - По режиссеру");
        System.out.println("5 - По рейтингу");
        System.out.println("0 - Назад в главное меню");

        String choice = scanner.nextLine().trim();
        logger.info("Пользователь выбрал критерий сортировки: " + choice);

        SortCriteria criteria = getCriteriaFromChoice(choice);

        if (criteria != null) {
            logger.info("Сортировка фильмов по критерию: " + criteria.getDescription());
            QuickSort.sort(movies, criteria.getComparator());
            System.out.println("Фильмы отсортированы по " + criteria.getDescription().toLowerCase());
            System.out.println("Количество отсортированных элементов: " + movies.size());
        } else if ("0".equals(choice)) {
            logger.info("Возврат в главное меню из сортировки");
            System.out.println("Возврат в главное меню...");
        } else {
            logger.warning("Неверный выбор пользователя: " + choice);
            System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private SortCriteria getCriteriaFromChoice(String choice) {
        switch (choice) {
            case "1":
                return SortCriteria.BY_ID;
            case "2":
                return SortCriteria.BY_NAME;
            case "3":
                return SortCriteria.BY_YEAR;
            case "4":
                return SortCriteria.BY_DIRECTOR;
            case "5":
                return SortCriteria.BY_RATING;
            default:
                return null;
        }
    }
}
