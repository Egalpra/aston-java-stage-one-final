package ru.aston.sort_app.interfaces;

public interface SortAlgorithm<T extends Comparable<T>> {

    void sort(T[] array);

    String getAlgorithmName();

    String getBestCaseComplexity();

    String getAverageCaseComplexity();

    String getWorstCaseComplexity();
}
