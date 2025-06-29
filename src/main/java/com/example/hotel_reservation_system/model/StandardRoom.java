package com.example.hotel_reservation_system.model;

// Standard Room implementation
public class StandardRoom extends Room {
    public StandardRoom(String roomNumber, double price, String amenities) {
        super(roomNumber, "Standard", price, amenities);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Standard Room " + roomNumber +
                " - Price: $" + price +
                " - Amenities: " + amenities);
    }
}
