package ru.aston.sort_app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Movie {
    private final Long id;
    private final String name;
    private final int year;
    private final String director;
    private final double rate;

    private Movie(MovieBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.year = builder.year;
        this.director = builder.director;
        this.rate = builder.rate;
    }

    public static MovieBuilder builder() {
        return new MovieBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year && Double.compare(rate, movie.rate) == 0 && Objects.equals(id, movie.id) && Objects.equals(name, movie.name) && Objects.equals(director, movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, director, rate);
    }

    public static class MovieBuilder {
        private Long id;
        private String name;
        private int year;
        private String director;
        private double rate;

        public MovieBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MovieBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MovieBuilder year(int year) {
            this.year = year;
            return this;
        }

        public MovieBuilder director(String directorName) {
            this.director = directorName;
            return this;
        }

        public MovieBuilder rate(double rate) {
            this.rate = rate;
            return this;
        }

        public Movie build() {
            List<String> errors = validate();

            if (errors.size() > 0) {
                throw new IllegalStateException(
                        errors.stream().collect(
                                Collectors.joining(", ")
                        ));
            }

            return new Movie(this);
        }

        public List<String> validate() {
            List<String> errors = new ArrayList<>();

            if (this.id == null) {
                errors.add("ID является обязательным полем");
            }

            if (this.name == null || this.name.trim().isEmpty()) {
                errors.add("Название фильма обязательно");
            }
            if (this.year < 1900) {
                errors.add("Не корректный год выпуска "  + year);
            }

            return errors;
        }

    }
}