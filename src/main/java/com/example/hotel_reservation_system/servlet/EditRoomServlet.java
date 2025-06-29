package com.example.hotel_reservation_system.servlet;

import com.example.hotel_reservation_system.dao.RoomManager;
import com.example.hotel_reservation_system.model.Room;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditRoom")
public class EditRoomServlet extends HttpServlet {
    private final RoomManager roomManager = new RoomManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomNumber = request.getParameter("roomNumber");
        Room room = roomManager.getRoom(roomNumber);

        request.setAttribute("room", room);
        request.getRequestDispatcher("edit_room.jsp").forward(request, response);
    }
}

