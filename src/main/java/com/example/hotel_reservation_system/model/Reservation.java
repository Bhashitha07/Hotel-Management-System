package com.example.hotel_reservation_system.model;

import java.io.Serializable;
import java.sql.Date;

public class Reservation implements Serializable {
    private String reservationId;
    private String userId;
    private String roomNumber;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private String status;

    public Reservation(String reservationId, String userId, String roomNumber,
                       Date checkInDate, Date checkOutDate, String status) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        // Simple calculation: $100 per night
        long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
        long diff = diffInMillies / (24 * 60 * 60 * 1000);
        return diff * 100;
    }

    // Getters and Setters
    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
        this.totalPrice = calculateTotalPrice();
    }
    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice();
    }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", userId='" + userId + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }
}