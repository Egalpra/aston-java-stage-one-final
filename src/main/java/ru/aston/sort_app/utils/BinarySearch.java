package ru.aston.sort_app.utils;

import ru.aston.sort_app.interfaces.Comparable;

public class BinarySearch<T extends Comparable<T>> {

    public int search(T[] array, T target) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        if (target == null) {
            throw new IllegalArgumentException("Искомый элемент не может быть null");
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparison = array[mid].compareTo(target);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public int searchRecursive(T[] array, T target) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        if (target == null) {
            throw new IllegalArgumentException("Искомый элемент не может быть null");
        }

        return searchRecursiveHelper(array, target, 0, array.length - 1);
    }

    private int searchRecursiveHelper(T[] array, T target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;
        int comparison = array[mid].compareTo(target);

        if (comparison == 0) {
            return mid;
        } else if (comparison < 0) {
            return searchRecursiveHelper(array, target, mid + 1, right);
        } else {
            return searchRecursiveHelper(array, target, left, mid - 1);
        }
    }

    public int findFirst(T[] array, T target) {
        if (array == null || target == null) {
            throw new IllegalArgumentException("Массив и искомый элемент не могут быть null");
        }

        int left = 0;
        int right = array.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = array[mid].compareTo(target);

            if (comparison == 0) {
                result = mid;
                right = mid - 1;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    public int findLast(T[] array, T target) {
        if (array == null || target == null) {
            throw new IllegalArgumentException("Массив и искомый элемент не могут быть null");
        }

        int left = 0;
        int right = array.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = array[mid].compareTo(target);

            if (comparison == 0) {
                result = mid;
                left = mid + 1;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }
}
