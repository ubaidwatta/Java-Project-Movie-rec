package recommender;

import model.Movie;
import model.Rating;
import java.util.*;

public class Recommender {

    private final List<Rating> ratings;
    private final Map<Integer, Movie> movies;

    public Recommender(List<Rating> ratings, Map<Integer, Movie> movies) {
        this.ratings = ratings;
        this.movies = movies;
    }

    // returns sorted movieId -> average rating
    public List<Map.Entry<Integer, Double>> getTopRatedMovies(int topN) {
        Map<Integer, List<Double>> movieRatings = new HashMap<>();

        for (Rating r : ratings) {
            movieRatings.computeIfAbsent(r.movieId, k -> new ArrayList<>()).add(r.rating);
        }

        Map<Integer, Double> avgRatings = new HashMap<>();
        for (var entry : movieRatings.entrySet()) {
            double avg = entry.getValue().stream().mapToDouble(x -> x).average().orElse(0.0);
            avgRatings.put(entry.getKey(), avg);
        }

        List<Map.Entry<Integer, Double>> sorted = new ArrayList<>(avgRatings.entrySet());
        sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        return sorted.subList(0, Math.min(topN, sorted.size()));
    }

    // returns top-rated movies filtered by genre
    public List<Map.Entry<Integer, Double>> getTopRatedMoviesByGenre(String genre, int topN) {
        Map<Integer, List<Double>> movieRatings = new HashMap<>();

        for (Rating r : ratings) {
            Movie m = movies.get(r.movieId);
            if (m != null && m.hasGenre(genre)) {
                movieRatings.computeIfAbsent(r.movieId, k -> new ArrayList<>()).add(r.rating);
            }
        }

        Map<Integer, Double> avgRatings = new HashMap<>();
        for (var entry : movieRatings.entrySet()) {
            double avg = entry.getValue().stream().mapToDouble(x -> x).average().orElse(0.0);
            avgRatings.put(entry.getKey(), avg);
        }

        List<Map.Entry<Integer, Double>> sorted = new ArrayList<>(avgRatings.entrySet());
        sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        return sorted.subList(0, Math.min(topN, sorted.size()));
    }
}
