package ru.aston.sort_app;

import java.util.*;
import java.util.logging.Logger;

import ru.aston.sort_app.handlers.*;
import ru.aston.sort_app.model.Movie;

public class SortApp {
    private final Map<UserChoice, UserChoiceHandler> handlerMap = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private final Logger logger = Logger.getLogger(SortApp.class.getName());
    private final List<Movie> movies = new ArrayList<>();

    public SortApp() {
        initHandlerMap();
    }

    private void initHandlerMap() {
        handlerMap.put(UserChoice.CloseApplication, new CloseApplicationHandler());
        handlerMap.put(UserChoice.ReadMoviesFromFile, new ReadMovieFileHandler(movies));
        handlerMap.put(UserChoice.WriteMoviesFromFile, new WriteMovieFileHandler(movies));
        handlerMap.put(UserChoice.SortMovies, new SortHandler(movies));
        handlerMap.put(UserChoice.BinarySearch, new BinarySearchHandler(movies));
        handlerMap.put(UserChoice.ManualFill, new ManualFillHandler(movies, scanner));
    }

    public void run() {
        while (true) {
            try {
                printMenu();
                String input = getUserInput();
                UserChoice choice = UserChoice.fromInput(input);

                if (choice == null) {
                    throw new IllegalArgumentException("Некорекктый ввод в меню");
                }

                UserChoiceHandler handler = handlerMap.get(choice);

                if (handler == null) {
                    throw new IllegalArgumentException("К данному выбору не привязан обработчик");
                }

                handler.handle();
            } catch (IllegalArgumentException ex) {
                logger.info(ex.getMessage());
            }
        }
    }

    private String getUserInput() {
        return scanner.nextLine().trim();
    }

    private void printMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("\n=== Главное меню ===");
        menu.append("\n" + UserChoice.CloseApplication.getUserInput() + " - Выход");
        menu.append("\n" + UserChoice.ReadMoviesFromFile.getUserInput() + " - Загрузка из файла");
        menu.append("\n" + UserChoice.WriteMoviesFromFile.getUserInput() + " - Запись в файл");
        menu.append("\n" + UserChoice.SortMovies.getUserInput() + " - Сортировка фильмов");
        menu.append("\n" + UserChoice.BinarySearch.getUserInput() + " - Бинарный поиск фильмов");
        menu.append("\n" + UserChoice.ManualFill.getUserInput() + " - Ручная запись");

        logger.info(menu.toString());
    }
}
