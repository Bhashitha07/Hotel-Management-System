package com.example.hotel_reservation_system.dao;



import com.example.hotel_reservation_system.model.Admin;
import com.example.hotel_reservation_system.model.Guest;
import com.example.hotel_reservation_system.model.Staff;
import com.example.hotel_reservation_system.model.User;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String FILE_PATH = "C:\\Users\\Bhashitha\\OneDrive\\Desktop\\New folder\\users.txt";
    private final List<User> users = new ArrayList<>();

    public UserManager() {
        loadUsers();
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public void updateUser(String id, String email, String password) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                u.setEmail(email);
                u.setPassword(password);
                break;
            }
        }
        saveUsers();
    }

    public void updateUserRole(String id, String role) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                u.setRole(role);
                break;
            }
        }
        saveUsers();
    }

    public void deleteUser(String id) {
        users.removeIf(u -> u.getId().equals(id));
        saveUsers();
    }

    public User getByUsernameAndPassword(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                writer.write(String.join(",", u.getId(), u.getUsername(), u.getPassword(), u.getRole(), u.getEmail()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        users.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 5) {
                    User user = switch (p[3]) {
                        case "Guest" -> new Guest(p[0], p[1], p[2], p[4]);
                        case "Staff" -> new Staff(p[0], p[1], p[2], p[4]);
                        case "Admin" -> new Admin(p[0], p[1], p[2], p[4]);
                        default -> new User(p[0], p[1], p[2], p[3], p[4]);
                    };
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
