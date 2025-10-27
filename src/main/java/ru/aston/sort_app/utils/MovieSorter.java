package ru.aston.sort_app.utils;

import ru.aston.sort_app.interfaces.SortAlgorithm;
import ru.aston.sort_app.interfaces.SortCriteria;
import ru.aston.sort_app.model.Movie;

import java.util.List;

public class MovieSorter {

    private final SortAlgorithm<Movie> sortAlgorithm;

    public MovieSorter(SortAlgorithm<Movie> sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }

    public void sortMovies(List<Movie> movies, SortCriteria criteria) {
        if (movies == null) {
            throw new IllegalArgumentException("Список фильмов не может быть null");
        }

        if (criteria == null) {
            throw new IllegalArgumentException("Критерий сортировки не может быть null");
        }

        if (movies.isEmpty()) {
            return;
        }

        Movie[] movieArray = movies.toArray(new Movie[0]);

        sortAlgorithm.sort(movieArray);

        movies.clear();
        for (Movie movie : movieArray) {
            movies.add(movie);
        }
    }

    public void sortById(List<Movie> movies) {
        sortMovies(movies, SortCriteria.BY_ID);
    }

    public void sortByName(List<Movie> movies) {
        sortMovies(movies, SortCriteria.BY_NAME);
    }

    public void sortByYear(List<Movie> movies) {
        sortMovies(movies, SortCriteria.BY_YEAR);
    }

    public void sortByDirector(List<Movie> movies) {
        sortMovies(movies, SortCriteria.BY_DIRECTOR);
    }

    public void sortByRating(List<Movie> movies) {
        sortMovies(movies, SortCriteria.BY_RATING);
    }

    public String getSortAlgorithmInfo() {
        return String.format(
                "Алгоритм: %s\n" +
                        "Лучший случай: %s\n" +
                        "Средний случай: %s\n" +
                        "Худший случай: %s",
                sortAlgorithm.getAlgorithmName(),
                sortAlgorithm.getBestCaseComplexity(),
                sortAlgorithm.getAverageCaseComplexity(),
                sortAlgorithm.getWorstCaseComplexity());
    }
}
