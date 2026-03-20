package com.example.assignment_0696;

public class Movie {
    String name, genre, trailerUrl;
    int image;
    boolean isComingSoon;

    public Movie(String name, String genre, int image, String trailerUrl, boolean isComingSoon) {
        this.name = name;
        this.genre = genre;
        this.image = image;
        this.trailerUrl = trailerUrl;
        this.isComingSoon = isComingSoon;
    }

    public String getName() { return name; }
    public String getGenre() { return genre; }
    public int getImage() { return image; }
    public String getTrailerUrl() { return trailerUrl; }
    public boolean isComingSoon() { return isComingSoon; }
}
