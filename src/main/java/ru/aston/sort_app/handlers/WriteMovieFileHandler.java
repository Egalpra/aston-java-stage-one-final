package ru.aston.sort_app.handlers;

import ru.aston.sort_app.model.Movie;
import ru.aston.sort_app.utils.MovieCSVReader;
import ru.aston.sort_app.utils.MovieCSVWriter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class WriteMovieFileHandler implements UserChoiceHandler {

    private final List<Movie> movies;
    private final Logger logger;

    public WriteMovieFileHandler(List<Movie> movies) {
        this.movies = movies;
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @Override
    public void handle() {
        logger.info("Введите путь до csv-файла: ");

        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();

        try {
            MovieCSVWriter writer = new MovieCSVWriter(path);
            writer.writeMovies(movies);

            logger.info(
                    String.format("Записанно %d фильмов", movies.size())
            );
        } catch (IOException e) {
            logger.warning("Неудалось создать writer " + e.getMessage());
        }
    }

}
