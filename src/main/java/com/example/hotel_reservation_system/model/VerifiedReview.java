package com.example.hotel_reservation_system.model;

import java.util.Date;

public class VerifiedReview extends Review {
    private String username;

    public VerifiedReview(String id, String message, Date date, String status, String username) {
        super(id, message, date, status);
        this.username = username;
    }

    @Override
    public String getReviewer() {
        return username;
    }
}