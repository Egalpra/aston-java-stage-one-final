package ru.aston.sort_app.handlers;

public class CloseApplicationHandler implements UserChoiceHandler {
    @Override
    public void handle() {
        System.exit(0);
    }
}
