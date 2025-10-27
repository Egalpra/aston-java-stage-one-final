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
        List<String> columns = parseCSVLine(line);

        if (columns.size() < 5) {
            logger.warning("Недостаточно колонок в строке: " + line);
            return Optional.empty();
        }

        try {
            Movie.MovieBuilder builder = Movie.builder()
                    .id(Long.parseLong(columns.get(0)))
                    .name(removeQuotes(columns.get(1)))
                    .year(Integer.parseInt(columns.get(2)))
                    .director(removeQuotes(columns.get(3)))
                    .rate(Double.parseDouble(columns.get(4)));

            List<String> errors = builder.validate();

            if (errors.size() > 0) {
                logger.warning(
                        errors.stream().collect(
                                Collectors.joining(", ")));

                return Optional.empty();
            }

            return Optional.of(builder.build());
        } catch (Exception e) {
            logger.warning("Ошибка при парсинге строки " + line);
            return Optional.empty();
        }
    }

    private List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        result.add(currentField.toString());

        return result;
    }

    private String removeQuotes(String value) {
        if (value == null) {
            return null;
        }
        value = value.trim();
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
