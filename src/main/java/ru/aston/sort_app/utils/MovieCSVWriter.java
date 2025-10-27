package ru.aston.sort_app.utils;

import ru.aston.sort_app.model.Movie;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MovieCSVWriter {
    private final Path pathToCSV;
    private final Logger logger;

    public MovieCSVWriter(String path) throws IOException {
        this.pathToCSV = Paths.get(path);
        this.logger = Logger.getLogger(this.getClass().getName());

        createFileIfNotExist(pathToCSV);
    }

    private void createFileIfNotExist(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    private String toCSV(Movie movie) {
        return String.format(
                "%d,\"%s\",%d,\"%s\",%.1f",
                movie.getId(),
                movie.getName().replaceAll("\"", "\"\""),
                movie.getYear(),
                movie.getDirector().replaceAll("\"", "\"\""),
                movie.getRate()
        );
    }

    public void writeMovies(List<Movie> movies) {
        String content = movies.stream()
                .map(this::toCSV)
                .collect(Collectors.joining(System.lineSeparator()));
        try {
            Files.writeString(this.pathToCSV, content, Charset.defaultCharset(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
