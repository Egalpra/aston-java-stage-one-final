package ru.aston.sort_app.handlers;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ManualFill {
    private Long id;
    private String name;
    private int year;
    private String director;
    private double rate;
    private List<Movie> movies;
    private List<String> temp;
    private List<String> messages = Arrays.asList(
            "Enter movie ID: ", "Enter movie name: ",
            "Enter movie year: ","Enter movie director: ",
            "Enter movie rate: ");

    public void manualFill() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            boolean valid = true;
            fieldDefault();
            System.out.println("Fill the movie");
            for (int i = 0; i < messages.size(); i++) {
                System.out.print(messages.get(i));
                String movieData = scanner.nextLine();
                if (!validationSet(movieData, i)) {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Object was not recorded");
                continue;
            }
            System.out.println("Recording movie");
//            movies.add(
//            new Movie.builder(id, name, year)
//                    .director
//                    .rate
//                    .build(););
            System.out.println("Movie added");
        }
    }

    private boolean validationSet(String movieData, int i) {
        boolean isValid = true;
        switch (messages.get(i)) {
            case "Enter movie ID: ":
                if (movieData.matches("\\d+")) id = Long.valueOf(movieData);
                else { System.out.println("ID must be contain by only numbers");
                    isValid = false; }
                return isValid;
            case "Enter movie name: ":
                name = movieData;
                return isValid;
            case "Enter movie year: ":
                if ((movieData.length() == 4)
                        && (movieData.matches("\\d+"))
                        && Integer.valueOf(movieData) > 1900) {
                    year = Integer.valueOf(movieData);
                } else { System.out.println("Incorrect year");
                    isValid = false; }
                return isValid;
            case "Enter movie director: ":
                director = movieData;
                return isValid;
            case "Enter movie rate: ":
                rate = Double.valueOf(movieData);
                return isValid;
        }
        return false;
    }

    private void fieldDefault() {
        id = 0L;
        name = "";
        year = 0;
        director = "";
        rate = 0.0;
    }
}