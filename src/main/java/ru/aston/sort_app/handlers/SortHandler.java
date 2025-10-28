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
            logger.info("Список фильмов пуст. Сначала загрузите фильмы из файла.");
            return;
        }

        logger.info("Запуск сортировки фильмов");

        StringBuilder sb = new StringBuilder();

        sb.append("\n=== Сортировка фильмов ===");
        sb.append("\nВыберите критерий сортировки:");
        sb.append("\n1 - По ID");
        sb.append("\n2 - По названию");
        sb.append("\n3 - По году");
        sb.append("\n4 - По режиссеру");
        sb.append("\n5 - По рейтингу");
        sb.append("\n0 - Назад в главное меню");

        logger.info(sb.toString());

        String choice = scanner.nextLine().trim();
        logger.info("Пользователь выбрал критерий сортировки: " + choice);

        SortCriteria criteria = getCriteriaFromChoice(choice);

        if (criteria != null) {
            logger.info("Сортировка фильмов по критерию: " + criteria.getDescription());
            QuickSort.sort(movies, criteria.getComparator());
            logger.info("Фильмы отсортированы по " + criteria.getDescription().toLowerCase());
            logger.info("Количество отсортированных элементов: " + movies.size());
        } else if ("0".equals(choice)) {
            logger.info("Возврат в главное меню из сортировки");
        } else {
            logger.warning("Неверный выбор пользователя: " + choice);
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
