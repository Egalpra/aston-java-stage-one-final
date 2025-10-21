package ru.aston.sort_app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void when_buildMovie_then_returnCorrectObject() {

        Long id = 1000l;
        String name = "The passengers";
        int year = 2016;
        String director = "?Jennifer Lawrence";
        double rate = 9d;

        Movie movie = Movie.builder()
                .id(id)
                .name(name)
                .year(year)
                .director(director)
                .rate(rate)
                .build();

        assertEquals(id, movie.getId());
        assertEquals(name, movie.getName());
        assertEquals(year, movie.getYear());
        assertEquals(director, movie.getDirector());
        assertEquals(rate, movie.getRate());
    }

    @Test
    void  when_buildMovieWithoutId_trowException(){
        String name = "The passengers";
        int year = 2016;
        String director = "?Jennifer Lawrence";
        double rate = 9d;

        Movie.MovieBuilder builder= Movie.builder()
                .name(name)
                .year(year)
                .director(director)
                .rate(rate);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void  when_buildMovieWithoutName_trowException(){
        Long id = 1000l;;
        int year = 2016;
        String director = "?Jennifer Lawrence";
        double rate = 9d;

        Movie.MovieBuilder builder= Movie.builder()
                .id(id)
                .year(year)
                .director(director)
                .rate(rate);

        assertThrows(IllegalStateException.class, builder::build);

        builder.name("  ");
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void  when_buildMovieWithIncorrectYear_trowException(){

        Long id = 1000l;
        String name = "The passengers";
        int year = 0;
        String director = "?Jennifer Lawrence";
        double rate = 9d;

        Movie.MovieBuilder builder = Movie.builder()
                .id(id)
                .name(name)
                .year(year)
                .director(director)
                .rate(rate);

        assertThrows(IllegalStateException.class, builder::build);

        builder.year(1899);
        assertThrows(IllegalStateException.class, builder::build);
    }
}