package ru.aston.sort_app.utils;

import ru.aston.sort_app.model.Movie;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MovieCSVWriter {
    private final Path pathToCSV;
    private boolean isNewFile = false;

    public MovieCSVWriter(String path) throws IOException {
        this.pathToCSV = Paths.get(path);
        createFileIfNotExist(pathToCSV);
    }

    private void createFileIfNotExist(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
            isNewFile = true;
        }
    }

    private String toCSV(Movie movie) {
        return String.format(
                Locale.US,
                "%d,\"%s\",%d,\"%s\",%.1f",
                movie.getId(),
                movie.getName().replaceAll("\"", "\"\""),
                movie.getYear(),
                movie.getDirector().replaceAll("\"", "\"\""),
                movie.getRate()
        );
    }

    private boolean fileEndsWithNewline(Path path) throws IOException {
        long size = Files.size(path);
        if (size == 0) return false;

        try (SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ)) {
            channel.position(size - 1);
            ByteBuffer buffer = ByteBuffer.allocate(1);
            channel.read(buffer);
            buffer.flip();
            byte lastByte = buffer.get();

            if (lastByte == (byte) '\n') {
                return true;
            }

            if (lastByte == (byte) '\r') {
                return true;
            }
        }
        return false;
    }

    public void writeMovies(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) return;

        StringBuilder sb = new StringBuilder();

        try {
            boolean needLeadingNewline = false;

            if (!isNewFile) {
                long size = Files.size(pathToCSV);
                if (size > 0) {
                    needLeadingNewline = !fileEndsWithNewline(pathToCSV);
                }
            }

            if (needLeadingNewline) {
                sb.append(System.lineSeparator());
            }

            sb.append(
                    movies.stream()
                            .map(this::toCSV)
                            .collect(Collectors.joining(System.lineSeparator()))
            );

            Files.writeString(
                    this.pathToCSV,
                    sb.toString(),
                    Charset.defaultCharset(),
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
