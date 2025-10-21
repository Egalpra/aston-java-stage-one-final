package ru.aston.sort_app.utils;

import ru.aston.sort_app.model.Movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieCSVReader {
    private final Path pathToCSV;
    private final Logger logger;

    public MovieCSVReader(String path) {
        this.pathToCSV = Paths.get(path);
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();

        try (Stream<String> lines = Files.lines(pathToCSV)) {
            movies = lines.map(this::tryMapMovie)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
        } catch (IllegalStateException | IOException e) {
            logger.warning(e.getMessage());
        }

        return movies;
    }

    private Optional<Movie> tryMapMovie(String line) {
        String[] columns = line.split(",");

        if (columns.length < 5) {
            logger.warning("Недостаточно колонок");
            return Optional.empty();
        }

        try {
            Movie.MovieBuilder builder = Movie.builder()
                    .id(Long.parseLong(columns[0]))
                    .name(columns[1])
                    .year(Integer.parseInt(columns[2]))
                    .director(columns[3])
                    .rate(Double.parseDouble(columns[4]));

            List<String> errors = builder.validate();

            if (errors.size() > 0) {
                logger.warning(
                        errors.stream().collect(
                                Collectors.joining(", ")
                        ));

                return Optional.empty();
            }

            return Optional.of(builder.build());
        } catch (Exception e) {
            logger.warning("Ошибка при парсинге строки " + line);
            return Optional.empty();
        }
    }
}
