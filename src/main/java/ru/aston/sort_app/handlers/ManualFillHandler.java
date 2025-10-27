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
        System.out.println("Введите данные фильма (для завершения ввода оставте название пустым");
        while (true) {
            try {
                System.out.print("Название фильма (или пустая строка для"); // поправить
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    LOGGER.info("Ручной ввод данных о фильмах завершен");
                    break;
                }
                System.out.print("ID фильма: ");
                String idInput = scanner.nextLine().trim();
                Long id;
                try {
                    id = Long.parseLong(idInput);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Неверный формат ID: " + idInput);
                    System.out.println("Ошибка: ID должен быть числом");
                    continue;
                }
                System.out.print("Год выпуска: ");
                String yearInput = scanner.nextLine().trim();
                int year;
                try {
                    year = Integer.parseInt(yearInput);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Неверный формат года: " + yearInput);
                    System.out.println("Ошибка: Год должен быть числом");
                    continue;
                }
                System.out.print("Режиссер: ");
                String director = scanner.nextLine().trim();
                System.out.print("Рейтинг: ");
                String rateInput = scanner.nextLine().trim();
                double rate;
                try {
                    rate = Double.parseDouble(rateInput);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Неверный формат рейтинга: " + rateInput);
                    System.out.println("Ошибка: Рейтинг должен быть числом");
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
                System.out.println("Фильм успешно добавлен: " + movie.getName());
            } catch (IllegalStateException e) {
                LOGGER.severe("Ошибка валидации: " + e.getMessage());
                System.out.println("Ошибка валидации: " + e.getMessage());
            } catch (Exception e) {
                LOGGER.severe("Непредвиденная ошибка: " + e.getMessage());
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }
    }
}