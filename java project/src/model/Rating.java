package model;

public class Rating {
    public int userId;
    public int movieId;
    public double rating;
    public long timestamp;

    public Rating(int userId, int movieId, double rating, long timestamp) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.timestamp = timestamp;
    }
}
