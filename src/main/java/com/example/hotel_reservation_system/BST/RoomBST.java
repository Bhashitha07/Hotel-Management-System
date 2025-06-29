package com.example.hotel_reservation_system.BST;

import com.example.hotel_reservation_system.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomBST {
    private Node root;

    private class Node {
        Room room;
        Node left, right;

        Node(Room room) {
            this.room = room;
        }
    }

    // Add a room to BST
    public void addRoom(Room room) {
        root = addRecursive(root, room);
    }

    private Node addRecursive(Node current, Room room) {
        if (current == null) {
            return new Node(room);
        }

        if (room.compareTo(current.room) < 0) {
            current.left = addRecursive(current.left, room);
        } else if (room.compareTo(current.room) > 0) {
            current.right = addRecursive(current.right, room);
        } else {
            // Room already exists
            return current;
        }

        return current;
    }

    // Find a room by number
    public Room findRoom(String roomNumber) {
        return findRecursive(root, roomNumber);
    }

    private Room findRecursive(Node current, String roomNumber) {
        if (current == null) {
            return null;
        }

        if (roomNumber.equals(current.room.getRoomNumber())) {
            return current.room;
        }

        return roomNumber.compareTo(current.room.getRoomNumber()) < 0
                ? findRecursive(current.left, roomNumber)
                : findRecursive(current.right, roomNumber);
    }

    // Delete a room
    public void deleteRoom(String roomNumber) {
        root = deleteRecursive(root, roomNumber);
    }

    private Node deleteRecursive(Node current, String roomNumber) {
        if (current == null) {
            return null;
        }

        if (roomNumber.equals(current.room.getRoomNumber())) {
            // Node to delete found
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }

            Room smallestValue = findSmallestValue(current.right);
            current.room = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue.getRoomNumber());
            return current;
        }

        if (roomNumber.compareTo(current.room.getRoomNumber()) < 0) {
            current.left = deleteRecursive(current.left, roomNumber);
            return current;
        }
        current.right = deleteRecursive(current.right, roomNumber);
        return current;
    }

    private Room findSmallestValue(Node root) {
        return root.left == null ? root.room : findSmallestValue(root.left);
    }

    // In-order traversal to get all rooms sorted
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        inOrderTraversal(root, rooms);
        return rooms;
    }

    private void inOrderTraversal(Node node, List<Room> rooms) {
        if (node != null) {
            inOrderTraversal(node.left, rooms);
            rooms.add(node.room);
            inOrderTraversal(node.right, rooms);
        }
    }

    // Search rooms by type
    public List<Room> searchByType(String type) {
        List<Room> result = new ArrayList<>();
        searchByTypeRecursive(root, type, result);
        return result;
    }

    private void searchByTypeRecursive(Node node, String type, List<Room> result) {
        if (node != null) {
            searchByTypeRecursive(node.left, type, result);
            if (node.room.getType().equalsIgnoreCase(type)) {
                result.add(node.room);
            }
            searchByTypeRecursive(node.right, type, result);
        }
    }

    // Search rooms by price range
    public List<Room> searchByPriceRange(double min, double max) {
        List<Room> result = new ArrayList<>();
        searchByPriceRangeRecursive(root, min, max, result);
        return result;
    }

    private void searchByPriceRangeRecursive(Node node, double min, double max, List<Room> result) {
        if (node != null) {
            searchByPriceRangeRecursive(node.left, min, max, result);
            if (node.room.getPrice() >= min && node.room.getPrice() <= max) {
                result.add(node.room);
            }
            searchByPriceRangeRecursive(node.right, min, max, result);
        }
    }
}