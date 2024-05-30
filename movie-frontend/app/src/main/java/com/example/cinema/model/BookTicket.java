package com.example.cinema.model;

import java.util.List;

public class BookTicket {
    private int accountId;
    private int showtimeId;
    private List<String> seatList;
    int totalPrice;

    public BookTicket(int accountId, int showtimeId, List<String> seatList, int totalPrice) {
        this.accountId = accountId;
        this.showtimeId = showtimeId;
        this.seatList = seatList;
        this.totalPrice = totalPrice;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public List<String> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<String> seatList) {
        this.seatList = seatList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "BookTicket{" +
                "accountId=" + accountId +
                ", showtimeId=" + showtimeId +
                ", seatList=" + seatList +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
