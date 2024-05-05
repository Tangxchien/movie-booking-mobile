package com.example.cinema.model;

public class Movie {
    private Integer id;
    private String image;
    private String title;
    private String description;
    private String director;
    private String actors;
    private float price;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Movie(Integer id, String title, String description, String director, String actors, int price, String image) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.director = director;
        this.actors = actors;
        this.price = price;
        this.image = image;
    }
}
