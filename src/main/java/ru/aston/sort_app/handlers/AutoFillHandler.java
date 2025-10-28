package ru.aston.sort_app.handlers;

import ru.aston.sort_app.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.Random;
import java.util.Scanner;

public class AutoFillHandler implements UserChoiceHandler {
    private static final Logger LOGGER = Logger.getLogger(AutoFillHandler.class.getName());
    private final List<Movie> movies;
    private final Scanner scanner;
    private final Random random = new Random();

    private final List<String> directors = new ArrayList<>(Arrays.asList(
            "Андрей Тарковский", "Сергей Эйзенштейн", "Никита Михалков", "Эльдар Рязанов",
            "Леонид Гайдай", "Александр Сокуров", "Георгий Данелия", "Владимир Меньшов",
            "Павел Чухрай", "Иван Пырьев", "Григорий Александров", "Дмитрий Астрахан",
            "Алексей Герман", "Виктор Аристов", "Юрий Норштейн", "Алексей Балабанов",
            "Тимур Бекмамбетов", "Фёдор Бондарчук", "Сергей Бодров (старший)", "Сергей Урсуляк"
    ));

    private final List<String> titlePool = Arrays.asList(
            "Солярис", "Сталкер", "Зеркало", "Броненосец «Потёмкин»", "Иван Грозный",
            "12", "Утомлённые солнцем", "Любовь и голуби", "Ирония судьбы", "Операция «Ы»",
            "Кавказская пленница", "Мимино", "Москва слезам не верит", "Вор", "Иди и смотри",
            "Брат", "Брат 2", "Ночной дозор", "9 рота", "Сталинград"
    );

    public AutoFillHandler(List<Movie> movies, Scanner scanner) {
        this.movies = movies;
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        LOGGER.info("=== Запуск автозаполнения ===");
        LOGGER.info("Сколько фильмов сгенерировать? (введите число от 1 до 1000)");
        int count = readPositiveInt(1, 1000);

        for (long id = 1; id <= count; id++) {
            Movie movie = generateRandomMovie(id);
            movies.add(movie);
            LOGGER.info("Добавлен фильм: " + movie.getName() + " (ID=" + id + ")");
        }

        LOGGER.info("Автозаполнение завершено. Добавлено " + count + " фильмов.");
        LOGGER.info("Готово! Добавлено " + count + " случайных фильмов.");
    }

    private int readPositiveInt(int min, int max) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                LOGGER.info("Пожалуйста, введите число от " + min +  "до " + max);
            } catch (NumberFormatException e) {
                LOGGER.info("Неверный формат. Введите целое число:");
            }
        }
    }

    private Movie generateRandomMovie(long id) {
        String name = titlePool.get(random.nextInt(titlePool.size()));
        int year = 1950 + random.nextInt(75);
        String director = directors.get(random.nextInt(directors.size()));
        double rate = 0.0 + random.nextDouble() * 5.0;

        return Movie.builder()
                .id(id)
                .name(name)
                .year(year)
                .director(director)
                .rate(roundToOneDecimal(rate))
                .build();
    }

    private double roundToOneDecimal(double value) {
        return Math.round(value * 5.0) / 5.0;
    }
}
