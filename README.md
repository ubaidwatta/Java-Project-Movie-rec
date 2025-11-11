# Java-Project-Movie-rec
movie recommendation system
Project Structure:
java project/
├── data/
│   ├── movies.csv       ← movie details (id, title, genres)
│   └── ratings.csv      ← user ratings (userId, movieId, rating, timestamp)
│
├── src/
│   ├── model/           ← contains data models
│   │   ├── Movie.java
│   │   └── Rating.java
│   │
│   ├── util/            ← helper utilities
│   │   └── DataLoader.java
│   │
│   ├── recommender/     ← recommendation logic
│   │   └── Recommender.java
│   │
│   └── Main.java        ← entry point
│
└── out/                 ← compiled .class files

File-by-File Summary
🟩 Movie.java

Represents a movie record:

Fields: movieId, title, and genres

Provides getters and a hasGenre() method that checks if a movie belongs to a specific genre.

🟩 Rating.java

Represents a user rating record:

Fields: userId, movieId, rating, and timestamp

Simple data class used for calculations.

🟩 DataLoader.java

Handles CSV reading:

loadMovies() → reads movies.csv and creates Movie objects.

loadRatings() → reads ratings.csv and creates Rating objects.

Includes a helper method splitCsvLine() to safely parse titles containing commas or quotes.

🟩 Recommender.java

Performs the recommendation logic:

getTopRatedMovies() → calculates average ratings for all movies.

getTopRatedMoviesByGenre() → filters movies by the user’s chosen genre, then ranks them by average rating (descending).

It uses a Map<Integer, List<Double>> to group ratings by movieId and compute averages.

🟩 Main.java

The entry point of the application:

Prompts the user for a genre (e.g. Comedy or Action).

Loads all movies and ratings from the data/ folder.

Calls the recommender to get the top 10 movies in that genre.

Prints the results in a clean, formatted table.

If no movies match the genre, it shows a “No movies found” message.

