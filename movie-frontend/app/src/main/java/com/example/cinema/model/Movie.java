package com.example.cinema.model;


import java.util.Date;
import java.util.List;

public class Movie {
    private Integer id;
    private String image;
    private String title;
    private String description;
    private String director;
    private Date releaseDate;
    private int ageLimit;
    private List<String> genres;

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    private String actors;
    private int price;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Movie(Integer id, String image, String title, String description, String director, Date releaseDate, int ageLimit, String actors, int price) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.director = director;
        this.releaseDate = releaseDate;
        this.ageLimit = ageLimit;
        this.actors = actors;
        this.price = price;
    }
}
