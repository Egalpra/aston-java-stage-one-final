package ru.aston.sort_app.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.aston.sort_app.model.Movie;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MovieCSVWriterTest {

    private static final List<Movie> movies;
    private static String CSVContent =
                    "1,\"The Shadow of Olympus\",2000,\"Ethan Hawke\",8.5\n" +
                    "2,\"Whispers in the Wind\",1998,\"Sofia Coppola\",7.2\n" +
                    "3,\"Crimson Tide\",2021,\"Spike Lee\",9.1";

    @TempDir
    private File tempDir;

    static {
        movies = List.of(
                Movie.builder()
                        .id(1L)
                        .name("The Shadow of Olympus")
                        .year(2000)
                        .director("Ethan Hawke")
                        .rate(8.5)
                        .build(),
                Movie.builder()
                        .id(2L)
                        .name("Whispers in the Wind")
                        .year(1998)
                        .director("Sofia Coppola")
                        .rate(7.2)
                        .build(),
                Movie.builder()
                        .id(3L)
                        .name("Crimson Tide")
                        .year(2021)
                        .director("Spike Lee")
                        .rate(9.1)
                        .build()
        );

    }

    @Test
    void when_writeMovies_then_appendLines() throws IOException {
        String oldContent = "old content\n";

        Path tempFile = tempDir.toPath().resolve("test_write_file.csv");
        Files.writeString(tempFile, oldContent, Charset.defaultCharset(), StandardOpenOption.CREATE_NEW);

        MovieCSVWriter writer = new MovieCSVWriter(tempFile.toString());

        writer.writeMovies(movies);

        String expected = oldContent  + CSVContent;

        try (Stream<String> lines = Files.lines(tempFile)) {
            assertEquals(expected, lines.collect(Collectors.joining("\n")));
        }

        Files.delete(tempFile);
    }

    @Test
    void when_writeMovies_then_createFile() throws IOException {
        String oldContent = "old content\n";

        Path tempFile = tempDir.toPath().resolve("test_write_file.csv");

        MovieCSVWriter writer = new MovieCSVWriter(tempFile.toString());

        writer.writeMovies(movies);
        String expected = CSVContent;

        try (Stream<String> lines = Files.lines(tempFile)) {
            assertEquals(expected, lines.collect(Collectors.joining("\n")));
        }

        Files.delete(tempFile);
    }
}