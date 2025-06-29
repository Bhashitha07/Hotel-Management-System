package com.example.hotel_reservation_system.servlet;

import com.example.hotel_reservation_system.dao.RoomManager;
import com.example.hotel_reservation_system.model.DeluxeRoom;
import com.example.hotel_reservation_system.model.Room;
import com.example.hotel_reservation_system.model.StandardRoom;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/RoomServlet")
public class RoomServlet extends HttpServlet {
    private final RoomManager roomManager = new RoomManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomNumber = request.getParameter("roomNumber");
        Room room = roomManager.getRoom(roomNumber);

        if (room != null) {
            request.setAttribute("room", room);
            request.getRequestDispatcher("edit_room.jsp").forward(request, response);
        } else {
            response.sendRedirect("staff_dashboard.jsp?error=Room+not+found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                handleAddRoom(request, response);
                break;
            case "update":
                handleUpdateRoom(request, response);
                break;
            case "delete":
                handleDeleteRoom(request, response);
                break;
            case "search":
                handleSearchRooms(request, response);
                break;
            default:
                response.sendRedirect("edit_rooms.jsp?error=Invalid+action");
        }
    }

    private void handleAddRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roomNumber = request.getParameter("roomNumber");
        String type = request.getParameter("type");
        double price = Double.parseDouble(request.getParameter("price"));
        String amenities = request.getParameter("amenities");

        Room room;
        if ("Standard".equals(type)) {
            room = new StandardRoom(roomNumber, price, amenities);
        } else {
            boolean hasBalcony = "on".equals(request.getParameter("hasBalcony"));
            room = new DeluxeRoom(roomNumber, price, amenities, hasBalcony);
        }

        roomManager.addRoom(room);
        response.sendRedirect("staff_dashboard.jsp?success=Room+added");
    }

    private void handleUpdateRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roomNumber = request.getParameter("roomNumber");
        Room existingRoom = roomManager.getRoom(roomNumber);

        if (existingRoom != null) {
            double price = Double.parseDouble(request.getParameter("price"));
            String amenities = request.getParameter("amenities");

            if (existingRoom instanceof StandardRoom) {
                StandardRoom updatedRoom = new StandardRoom(roomNumber, price, amenities);
                roomManager.updateRoom(updatedRoom);
            } else {
                boolean hasBalcony = "on".equals(request.getParameter("hasBalcony"));
                DeluxeRoom updatedRoom = new DeluxeRoom(roomNumber, price, amenities, hasBalcony);
                roomManager.updateRoom(updatedRoom);
            }
            response.sendRedirect("staff_dashboard.jsp?success=Room+updated");
        } else {
            response.sendRedirect("staff_dashboard.jsp?error=Room+not+found");
        }
    }

    private void handleDeleteRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roomNumber = request.getParameter("roomNumber");
        roomManager.deleteRoom(roomNumber);
        response.sendRedirect("staff_dashboard.jsp?success=Room+deleted");
    }

    private void handleSearchRooms(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");

        Double minPrice = minPriceStr.isEmpty() ? null : Double.parseDouble(minPriceStr);
        Double maxPrice = maxPriceStr.isEmpty() ? null : Double.parseDouble(maxPriceStr);

        List<Room> rooms = roomManager.searchRooms(type, minPrice, maxPrice);
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("room_search_results.jsp").forward(request, response);
    }
}
