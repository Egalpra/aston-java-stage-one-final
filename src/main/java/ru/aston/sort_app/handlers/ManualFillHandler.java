package ru.aston.sort_app.handlers;
import ru.aston.sort_app.model.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ManualFillHandler implements UserChoiceHandler {
    private static final Logger LOGGER = Logger.getLogger(ManualFillHandler.class.getName());
    private final List<Movie> movies;
    private final Scanner scanner;
    public ManualFillHandler(List<Movie> movies, Scanner scanner) {
        this.movies = movies;
        this.scanner = scanner;
    }
    @Override
    public void handle() {
        LOGGER.info("Начало ручного ввода данных о фильмах");
        LOGGER.info("Введите данные фильма (для завершения ввода оставте название пустым");
        while (true) {
            try {
                LOGGER.info("Название фильма (или пустая строка для завершения");
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    LOGGER.info("Ручной ввод данных о фильмах завершен");
                    break;
                }
                LOGGER.info("ID фильма: ");
                String idInput = scanner.nextLine().trim();
                Long id;
                try {
                    id = Long.parseLong(idInput);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Неверный формат ID: " + idInput);
                    LOGGER.info("Ошибка: ID должен быть числом");
                    continue;
                }
                LOGGER.info("Год выпуска: ");
                String yearInput = scanner.nextLine().trim();
                int year;
                try {
                    year = Integer.parseInt(yearInput);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Неверный формат года: " + yearInput);
                    LOGGER.info("Ошибка: Год должен быть числом");
                    continue;
                }
                LOGGER.info("Режиссер: ");
                String director = scanner.nextLine().trim();
                LOGGER.info("Рейтинг: ");
                String rateInput = scanner.nextLine().trim();
                double rate;
                try {
                    rate = Double.parseDouble(rateInput);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Неверный формат рейтинга: " + rateInput);
                    LOGGER.info("Ошибка: Рейтинг должен быть числом");
                    continue;
                }
                Movie movie = Movie.builder()
                        .id(id)
                        .name(name)
                        .year(year)
                        .director(director)
                        .rate(rate)
                        .build();
                movies.add(movie);
                LOGGER.info("Добавлен фильм: " + movie.getName());
                LOGGER.info("Фильм успешно добавлен: " + movie.getName());
            } catch (IllegalStateException e) {
                LOGGER.severe("Ошибка валидации: " + e.getMessage());
                LOGGER.info("Ошибка валидации: " + e.getMessage());
            } catch (Exception e) {
                LOGGER.severe("Непредвиденная ошибка: " + e.getMessage());
                LOGGER.info("Произошла ошибка: " + e.getMessage());
            }
        }
    }
}