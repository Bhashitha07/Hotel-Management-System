package com.example.hotel_reservation_system.dao;


import com.example.hotel_reservation_system.model.Admin;
import com.example.hotel_reservation_system.model.Guest;
import com.example.hotel_reservation_system.model.Staff;
import com.example.hotel_reservation_system.model.User;

import java.io.*;
import java.util.*;

public class AdminManager {

    private static final String FILE_PATH = "C:\\Users\\Bhashitha\\OneDrive\\Desktop\\New folder\\users.txt";
    private List<User> users;

    public AdminManager() {
        users = loadUsers();
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }
        users.add(user);
        saveUsers();
    }

    public void deleteUser(String id) {
        users.removeIf(user -> user.getId().equals(id));
        saveUsers();
    }

    public void updateUserRole(String id, String newRole) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setRole(newRole);
                break;
            }
        }
        saveUsers();
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(String.join(",", user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getEmail()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<User> loadUsers() {
        List<User> loadedUsers = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return loadedUsers;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    User user = switch (data[3]) {
                        case "Guest" -> new Guest(data[0], data[1], data[2], data[4]);
                        case "Staff" -> new Staff(data[0], data[1], data[2], data[4]);
                        case "Admin" -> new Admin(data[0], data[1], data[2], data[4]);
                        default -> new User(data[0], data[1], data[2], data[3], data[4]);
                    };
                    loadedUsers.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedUsers;
    }
}
