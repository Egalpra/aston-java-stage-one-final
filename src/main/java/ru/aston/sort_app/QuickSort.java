package ru.aston.sort_app;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class QuickSort {

    private static final int SEQUENTIAL_THRESHOLD = 10000;
    private static ExecutorService executor;

    public static <T> void sort(List<T> list, Comparator<T> comparator) {

        int numCores = Runtime.getRuntime().availableProcessors();
        executor = new ThreadPoolExecutor(
                numCores,
                numCores,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()
        );

        try {
            quicksort(list, 0, list.size() - 1, comparator).join();
        } finally {
            executor.shutdown();
        }
    }

    private static <T> CompletableFuture<Void> quicksort(List<T> list, int low, int high, Comparator<T> comparator) {

        if (low >= high) {
            return CompletableFuture.completedFuture(null);
        }

        if (high - low < SEQUENTIAL_THRESHOLD) {
            list.subList(low, high + 1).sort(comparator);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.supplyAsync(() -> {
            return partition(list, low, high, comparator);
        }, executor).thenCompose(pivotIndex -> {
            CompletableFuture<Void> left = quicksort(list, low, pivotIndex - 1, comparator);
            CompletableFuture<Void> right = quicksort(list, pivotIndex + 1, high, comparator);
            return CompletableFuture.allOf(left, right);
        });
    }

    private static <T> int partition(List<T> list, int low, int high, Comparator<T> comparator) {

        int mid = low + (high - low) / 2;
        if (comparator.compare(list.get(low), list.get(mid)) > 0) {
            Collections.swap(list, low, mid);
        }
        if (comparator.compare(list.get(low), list.get(high)) > 0) {
            Collections.swap(list, low, high);
        }
        if (comparator.compare(list.get(mid), list.get(high)) > 0) {
            Collections.swap(list, mid, high);
        }
        Collections.swap(list, mid, high);
        T pivot = list.get(high);

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }
}