package util;

import model.Movie;
import model.Rating;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataLoader {
    public static Map<Integer, Movie> loadMovies(String path) throws Exception {
        Map<Integer, Movie> movies = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] parts = splitCsvLine(line);
                if (parts.length < 3) continue;
                int movieId = Integer.parseInt(parts[0]);
                String title = parts[1];
                String genres = parts[2];
                movies.put(movieId, new Movie(movieId, title, genres));
            }
        }
        return movies;
    }

    public static List<Rating> loadRatings(String path) throws Exception {
        List<Rating> ratings = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 4) continue;
                int userId = Integer.parseInt(tokens[0]);
                int movieId = Integer.parseInt(tokens[1]);
                double rating = Double.parseDouble(tokens[2]);
                long ts = Long.parseLong(tokens[3]);
                ratings.add(new Rating(userId, movieId, rating, ts));
            }
        }
        return ratings;
    }

    // helper: naive CSV split that handles quoted fields for title
    private static String[] splitCsvLine(String line) {
        List<String> cols = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (c == ',' && !inQuotes) {
                cols.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        cols.add(cur.toString());
        if (cols.size() > 3) {
            String id = cols.get(0);
            String genres = cols.get(cols.size() - 1);
            StringBuilder title = new StringBuilder();
            for (int i = 1; i < cols.size() - 1; i++) {
                if (i > 1) title.append(",");
                title.append(cols.get(i));
            }
            return new String[]{id, title.toString(), genres};
        }
        return cols.toArray(new String[0]);
    }
}
