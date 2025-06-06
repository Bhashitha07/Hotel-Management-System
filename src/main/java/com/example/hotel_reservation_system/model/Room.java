package com.example.hotel_reservation_system.model;

// Base Room class
public abstract class Room implements Comparable<Room> {
    protected String roomNumber;
    protected String type;
    protected double price;
    protected boolean isAvailable;
    protected String amenities;

    public Room(String roomNumber, String type, double price, String amenities) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.amenities = amenities;
        this.isAvailable = true;
    }

    // Getters and setters
    public String getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public String getAmenities() { return amenities; }

    public void setPrice(double price) { this.price = price; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    // For BST comparison (we'll sort by room number)
    @Override
    public int compareTo(Room other) {
        return this.roomNumber.compareTo(other.roomNumber);
    }

    // Abstract method for polymorphic behavior
    public abstract void displayRoomDetails();
}

