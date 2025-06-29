package com.example.hotel_reservation_system.model;

import java.util.Date;

public abstract class Review {
    protected String id;
    protected String message;
    protected Date date;
    protected String status;

    public Review(String id, String message, Date date, String status) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.status = status;
    }

    public abstract String getReviewer();

    public String getId() { return id; }
    public String getMessage() { return message; }
    public Date getDate() { return date; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
