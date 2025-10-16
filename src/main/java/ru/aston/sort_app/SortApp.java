package ru.aston.sort_app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ru.aston.sort_app.handlers.CloseApplicationHandler;
import ru.aston.sort_app.handlers.UserChoice;
import ru.aston.sort_app.handlers.UserChoiceHandler;

public class SortApp {
    private final Map<UserChoice, UserChoiceHandler> handlerMap = new HashMap<>();
    private final Scanner  scanner = new Scanner(System.in);

    public SortApp() {
        initHandlerMap();
    }

    private void initHandlerMap() {
        handlerMap.put(UserChoice.CloseApplication, new CloseApplicationHandler());
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
        System.out.printf("%s - Выход \r\n", UserChoice.CloseApplication.getUserInput());
    }
}
