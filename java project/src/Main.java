import model.Movie;
import model.Rating;
import util.DataLoader;
import recommender.Recommender;

import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a genre to filter (e.g. Comedy, Action, Drama): ");
            String genre = sc.nextLine().trim();

            // Use correct paths relative to your project
            String moviesFile = Paths.get("data", "movies.csv").toString();
            String ratingsFile = Paths.get("data", "ratings.csv").toString();

            Map<Integer, Movie> movies = DataLoader.loadMovies(moviesFile);
            List<Rating> ratings = DataLoader.loadRatings(ratingsFile);

            Recommender recommender = new Recommender(ratings, movies);
            List<Map.Entry<Integer, Double>> recommended = recommender.getTopRatedMoviesByGenre(genre, 10);

            System.out.println("\n====================================");
            System.out.println("Top Rated Movies in Genre: " + genre);
            System.out.println("====================================");

            String format = "| %-3s | %-35s | %-45s | %-6s |%n";
            System.out.println("+-----+-------------------------------------+-----------------------------------------------+--------+");
            System.out.printf(format, "#", "Title", "Genres", "Rating");
            System.out.println("+-----+-------------------------------------+-----------------------------------------------+--------+");

            int index = 1;
            for (var entry : recommended) {
                int movieId = entry.getKey();
                double rating = entry.getValue();
                Movie m = movies.get(movieId);
                if (m == null) continue;

                String title = m.getTitle().length() > 35 ? m.getTitle().substring(0, 32) + "..." : m.getTitle();
                String genres = m.getGenres().length() > 45 ? m.getGenres().substring(0, 42) + "..." : m.getGenres();

                System.out.printf(format, index++, title, genres, String.format("%.2f", rating));
            }

            if (recommended.isEmpty()) {
                System.out.println("| No movies found for genre: " + genre);
            }

            System.out.println("+-----+-------------------------------------+-----------------------------------------------+--------+");
            System.out.println("====================================");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
