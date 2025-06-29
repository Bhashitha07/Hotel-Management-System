package com.example.hotel_reservation_system.dao;

import com.example.hotel_reservation_system.BST.RoomBST;
import com.example.hotel_reservation_system.model.DeluxeRoom;
import com.example.hotel_reservation_system.model.Room;
import com.example.hotel_reservation_system.model.StandardRoom;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class RoomManager {
    private static final String ROOMS_FILE = "C:\\Users\\Bhashitha\\OneDrive\\Desktop\\New folder\\rooms.txt";
    private final RoomBST roomBST = new RoomBST();

    public RoomManager() {
        loadRooms();
    }

    public void addRoom(Room room) {
        roomBST.addRoom(room);
        saveRooms();
    }

    public Room getRoom(String roomNumber) {
        return roomBST.findRoom(roomNumber);
    }

    public void updateRoom(Room updatedRoom) {
        roomBST.deleteRoom(updatedRoom.getRoomNumber());
        roomBST.addRoom(updatedRoom);
        saveRooms();
    }

    public void deleteRoom(String roomNumber) {
        roomBST.deleteRoom(roomNumber);
        saveRooms();
    }

    public List<Room> getAllRooms() {
        return roomBST.getAllRooms();
    }

    public List<Room> searchRooms(String type, Double minPrice, Double maxPrice) {
        List<Room> allRooms;

        // Handle "All Types" or null/empty type by using all rooms
        if (type == null || type.trim().isEmpty() || type.equalsIgnoreCase("All Types")) {
            allRooms = roomBST.getAllRooms();
        } else {
            allRooms = roomBST.searchByType(type);
        }

        // Filter by price range if minPrice and maxPrice are not null
        return allRooms.stream()
                .filter(room -> (minPrice == null || room.getPrice() >= minPrice) &&
                        (maxPrice == null || room.getPrice() <= maxPrice))
                .collect(Collectors.toList());
    }

    private void loadRooms() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Room room;
                    if ("Standard".equalsIgnoreCase(parts[1])) {
                        room = new StandardRoom(parts[0], Double.parseDouble(parts[2]), parts[3]);
                    } else if ("Deluxe".equalsIgnoreCase(parts[1])) {
                        boolean hasBalcony = parts.length > 4 && Boolean.parseBoolean(parts[4]);
                        room = new DeluxeRoom(parts[0], Double.parseDouble(parts[2]), parts[3], hasBalcony);
                    } else {
                        continue; // Unknown room type
                    }
                    roomBST.addRoom(room);
                }
            }
        } catch (IOException e) {
            // Log the exception if necessary
            e.printStackTrace();
        }
    }

    private void saveRooms() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ROOMS_FILE))) {
            for (Room room : roomBST.getAllRooms()) {
                String line = room.getRoomNumber() + "," + room.getType() + "," +
                        room.getPrice() + "," + room.getAmenities();
                if (room instanceof DeluxeRoom) {
                    line += "," + ((DeluxeRoom) room).hasBalcony();
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
