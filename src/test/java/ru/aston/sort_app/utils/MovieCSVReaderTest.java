package ru.aston.sort_app.utils;

import org.junit.jupiter.api.Test;
import ru.aston.sort_app.model.Movie;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieCSVReaderTest {

    @Test
    void when_getFile_then_returnListOfMovies() throws URISyntaxException {

        List<Movie> testMovies = List.of(
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

        URL resource = getClass().getResource("/movies.csv");
        assertNotNull(resource, "Файл ресурса не найден");

        Path path = Paths.get(resource.toURI());

        MovieCSVReader reader = new MovieCSVReader(path.toString());
        List<Movie> moviesFromReader = reader.getMovies();

        assertEquals(testMovies, moviesFromReader);
    }
}
