package ru.aston.sort_app.utils;

import ru.aston.sort_app.model.Movie;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MovieCSVWriter {
    private final Path pathToCSV;
    private boolean isNewFile = false;

    public MovieCSVWriter(String path) throws IOException {
        this.pathToCSV = Paths.get(path);
        createFileIfNotExist(pathToCSV);
    }

    private void createFileIfNotExist(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
            isNewFile = true;
        }
    }

    private String toCSV(Movie movie) {
        return String.format(
                Locale.US,
                "%d,\"%s\",%d,\"%s\",%.1f",
                movie.getId(),
                movie.getName().replaceAll("\"", "\"\""),
                movie.getYear(),
                movie.getDirector().replaceAll("\"", "\"\""),
                movie.getRate()
        );
    }

    public void writeMovies(List<Movie> movies) {
        StringBuilder sb = new StringBuilder();

        if (!movies.isEmpty() && !isNewFile) {
            sb.append("\n");
        }

        sb.append(
                movies.stream()
                .map(this::toCSV)
                .collect(Collectors.joining(System.lineSeparator()))
        );

        try {
            Files.writeString(this.pathToCSV, sb.toString(), Charset.defaultCharset(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
