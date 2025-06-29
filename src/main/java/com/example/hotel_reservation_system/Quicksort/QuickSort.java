package com.example.hotel_reservation_system.Quicksort;

import com.example.hotel_reservation_system.model.Reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class QuickSort {

    public static void sortByCheckInDate(List<Reservation> reservations) {
        if (reservations == null || reservations.size() <= 1) {
            return;
        }
        quickSort(reservations, 0, reservations.size() - 1);
    }

    private static void quickSort(List<Reservation> reservations, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(reservations, low, high);
            quickSort(reservations, low, pivotIndex - 1);
            quickSort(reservations, pivotIndex + 1, high);
        }
    }

    private static int partition(List<Reservation> reservations, int low, int high) {
        Date pivot = reservations.get(high).getCheckInDate();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (reservations.get(j).getCheckInDate().before(pivot) ||
                    reservations.get(j).getCheckInDate().equals(pivot)) {
                i++;
                Collections.swap(reservations, i, j);
            }
        }
        Collections.swap(reservations, i + 1, high);
        return i + 1;
    }

    public static Reservation binarySearchByCheckInDate(List<Reservation> sortedReservations, Date targetDate) {
        int low = 0;
        int high = sortedReservations.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Date midDate = sortedReservations.get(mid).getCheckInDate();

            if (midDate.equals(targetDate)) {
                return sortedReservations.get(mid);
            } else if (midDate.before(targetDate)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public static List<Reservation> findAllByCheckInDate(List<Reservation> sortedReservations, Date targetDate) {
        int index = binarySearchFirstOccurrence(sortedReservations, targetDate);
        List<Reservation> results = new ArrayList<>();

        if (index != -1) {
            for (int i = index; i < sortedReservations.size() &&
                    sortedReservations.get(i).getCheckInDate().equals(targetDate); i++) {
                results.add(sortedReservations.get(i));
            }
        }

        return results;
    }

    private static int binarySearchFirstOccurrence(List<Reservation> reservations, Date targetDate) {
        int low = 0;
        int high = reservations.size() - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Date midDate = reservations.get(mid).getCheckInDate();

            if (midDate.equals(targetDate)) {
                result = mid;
                high = mid - 1;
            } else if (midDate.before(targetDate)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
}