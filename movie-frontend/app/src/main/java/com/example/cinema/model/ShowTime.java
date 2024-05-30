package com.example.cinema.model;

import java.util.Date;
import java.util.List;

public class ShowTime {
    private int id;
    private Date showTime;
    private List<String> seats;

    public ShowTime(int id, Date showTime, List<String> seats) {
        this.id = id;
        this.showTime = showTime;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }
}
