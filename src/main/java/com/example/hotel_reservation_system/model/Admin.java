package com.example.hotel_reservation_system.model;

import com.example.hotel_reservation_system.model.User;

public class Admin extends User {
    public Admin(String id, String username, String password, String email) {
        super(id, username, password, "Admin", email);
    }
}