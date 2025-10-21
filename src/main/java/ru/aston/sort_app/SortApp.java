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
                System.out.println(ex.getMessage());
            }
        }
    }

    private String getUserInput() {
        return scanner.nextLine().trim();
    }

    private void printMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("\n");
        menu.append(UserChoice.CloseApplication.getUserInput() + " - Выход \n");
        menu.append(UserChoice.ReadMoviesFromFile.getUserInput() + " - Загрузка из файла \n");
        menu.append(UserChoice.WriteMoviesFromFile.getUserInput() + " - Запись в файл \n");

        logger.info(menu.toString());
    }
}
