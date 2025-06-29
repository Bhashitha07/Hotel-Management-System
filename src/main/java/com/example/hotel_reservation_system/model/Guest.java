package com.example.hotel_reservation_system.model;

public class Guest extends User {
    public Guest(String id, String username, String password, String email) {
        super(id, username, password, "Guest", email);
    }

    // Optionally override getId/setId if needed, but call super methods
    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }
}