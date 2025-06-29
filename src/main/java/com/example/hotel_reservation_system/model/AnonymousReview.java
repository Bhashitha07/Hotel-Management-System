package com.example.hotel_reservation_system.model;

import java.util.Date;

public class AnonymousReview extends Review {
    public AnonymousReview(String id, String message, Date date, String status) {
        super(id, message, date, status);
    }

    @Override
    public String getReviewer() {
        return "Anonymous";
    }
}
