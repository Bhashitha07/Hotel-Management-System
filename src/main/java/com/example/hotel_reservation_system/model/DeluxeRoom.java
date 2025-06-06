package com.example.hotel_reservation_system.model;

// Deluxe Room implementation
public class DeluxeRoom extends Room {
    private boolean hasBalcony;

    public DeluxeRoom(String roomNumber, double price, String amenities, boolean hasBalcony) {
        super(roomNumber, "Deluxe", price, amenities);
        this.hasBalcony = hasBalcony;
    }

    public boolean hasBalcony() { return hasBalcony; }

    @Override
    public void displayRoomDetails() {
        System.out.println("Deluxe Room " + roomNumber +
                " - Price: $" + price +
                " - Balcony: " + (hasBalcony ? "Yes" : "No") +
                " - Amenities: " + amenities);
    }
}
