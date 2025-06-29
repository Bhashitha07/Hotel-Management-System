package com.example.hotel_reservation_system.dao;

import com.example.hotel_reservation_system.Quicksort.QuickSort;
import com.example.hotel_reservation_system.model.Reservation;

import java.io.*;
import java.util.*;
import java.sql.Date;

public class ReservationDAO {
    private static final String FILE_PATH = "C:\\Users\\Bhashitha\\OneDrive\\Desktop\\hello\\reservations.txt";
    private static final Object fileLock = new Object();

    public List<Reservation> getAllReservations() {
        synchronized (fileLock) {
            List<Reservation> reservations = new ArrayList<>();
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return reservations;
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List<?>) {
                    reservations = (List<Reservation>) obj;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return reservations;
        }
    }

    public List<Reservation> getAllReservationsSortedByCheckInDate() {
        List<Reservation> reservations = getAllReservations();
        QuickSort.sortByCheckInDate(reservations);
        return reservations;
    }

    public Reservation getReservationById(String id) {
        for (Reservation r : getAllReservations()) {
            if (r.getReservationId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public List<Reservation> searchReservationsByCheckInDate(Date checkInDate) {
        List<Reservation> sortedReservations = getAllReservationsSortedByCheckInDate();
        return QuickSort.findAllByCheckInDate(sortedReservations, checkInDate);
    }

    public void saveReservation(Reservation reservation) {
        synchronized (fileLock) {
            List<Reservation> reservations = getAllReservations();
            reservations.add(reservation);
            saveAllReservations(reservations);
        }
    }

    public void updateReservation(Reservation updatedReservation) {
        synchronized (fileLock) {
            List<Reservation> reservations = getAllReservations();
            for (int i = 0; i < reservations.size(); i++) {
                if (reservations.get(i).getReservationId().equals(updatedReservation.getReservationId())) {
                    reservations.set(i, updatedReservation);
                    break;
                }
            }
            saveAllReservations(reservations);
        }
    }

    public void cancelReservation(String reservationId) {
        synchronized (fileLock) {
            List<Reservation> reservations = getAllReservations();
            for (Reservation r : reservations) {
                if (r.getReservationId().equals(reservationId)) {
                    r.setStatus("Cancelled");
                    break;
                }
            }
            saveAllReservations(reservations);
        }
    }

    public void deleteReservation(String reservationId) {
        synchronized (fileLock) {
            List<Reservation> reservations = getAllReservations();
            reservations.removeIf(r -> r.getReservationId().equals(reservationId));
            saveAllReservations(reservations);
        }
    }

    private void saveAllReservations(List<Reservation> reservations) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}