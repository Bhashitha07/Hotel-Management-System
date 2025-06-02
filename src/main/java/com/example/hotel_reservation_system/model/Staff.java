package com.example.hotel_reservation_system.model;

public class Staff extends User {
    public Staff(String id, String username, String password, String email) {
        super(id, username, password, "Staff", email);
    }
}
