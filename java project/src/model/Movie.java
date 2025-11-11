package model;

public class Movie {
    private int movieId;
    private String title;
    private String genres;

    public Movie(int movieId, String title, String genres) {
        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    public boolean hasGenre(String genre) {
        if (genre == null || genre.isEmpty()) return false;
        return genres.toLowerCase().contains(genre.toLowerCase());
    }
}
