package ru.aston.sort_app.handlers;

import ru.aston.sort_app.model.Movie;
import ru.aston.sort_app.utils.MovieCSVReader;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ReadMovieFileHandler implements UserChoiceHandler {

    private final List<Movie> movies;
    private final Logger logger;

    public ReadMovieFileHandler(List<Movie> movies) {
        this.movies = movies;
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @Override
    public void handle() {
        logger.info("Стуруктура колонок -> id,name,year,director,rate \nВведите путь до csv-файла: ");

        Scanner scanner = new Scanner(System.in);

        String path = scanner.nextLine();

        List<Movie> moviesToAdd = new MovieCSVReader(path).getMovies();
        movies.addAll(moviesToAdd);

        logger.info(
                String.format(
                        "Добавлено %d записей\r\n", moviesToAdd.size()
                ));
    }
}
