package ru.aston.sort_app.handlers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserChoiceTest {

    @Test
    void when_userInputZero_returnCloseApplication() {
        assertEquals("0", UserChoice.CloseApplication.getUserInput());
    }
}
