package ru.aston.sort_app.interfaces;

import ru.aston.sort_app.model.Movie;

import java.util.Comparator;

public enum SortCriteria {

    BY_ID("По ID", (m1, m2) -> m1.getId().compareTo(m2.getId())),
    BY_NAME("По названию", (m1, m2) -> m1.getName().compareTo(m2.getName())),
    BY_YEAR("По году", (m1, m2) -> Integer.compare(m1.getYear(), m2.getYear())),
    BY_DIRECTOR("По режиссеру", (m1, m2) -> m1.getDirector().compareTo(m2.getDirector())),
    BY_RATING("По рейтингу", (m1, m2) -> Double.compare(m1.getRate(), m2.getRate()));

    private final String description;
    private final Comparator<Movie> comparator;

    SortCriteria(String description, Comparator<Movie> comparator) {
        this.description = description;
        this.comparator = comparator;
    }

    public String getDescription() {
        return description;
    }

    public Comparator<Movie> getComparator() {
        return comparator;
    }

    public static SortCriteria fromDescription(String description) {
        for (SortCriteria criteria : values()) {
            if (criteria.description.equals(description)) {
                return criteria;
            }
        }
        return null;
    }
}
