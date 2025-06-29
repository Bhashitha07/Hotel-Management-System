package com.example.hotel_reservation_system.servlet;


import com.example.hotel_reservation_system.dao.AdminManager;
import com.example.hotel_reservation_system.model.Admin;
import com.example.hotel_reservation_system.model.Guest;
import com.example.hotel_reservation_system.model.Staff;
import com.example.hotel_reservation_system.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private AdminManager adminManager;

    @Override
    public void init() {
        adminManager = new AdminManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action != null ? action : "") {
                case "delete" -> deleteUser(request, response);
                case "list" -> listUsers(request, response);
                default -> response.sendRedirect("admin_dashboard.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin_dashboard.jsp?error=Internal+Server+Error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addUser(request, response);
            } else if ("updateRole".equals(action)) {
                updateUserRole(request, response);
            } else {
                response.sendRedirect("admin_dashboard.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin_dashboard.jsp?error=Something+went+wrong");
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = adminManager.getAllUsers();
        request.setAttribute("userList", users);
        request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userId = request.getParameter("id");
        if (userId != null && !userId.isEmpty()) {
            adminManager.deleteUser(userId);
        }
        response.sendRedirect("AdminServlet?action=list");
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String email = request.getParameter("email");

        User newUser = switch (role) {
            case "Guest" -> new Guest(null, username, password, email);
            case "Staff" -> new Staff(null, username, password, email);
            case "Admin" -> new Admin(null, username, password, email);
            default -> null;
        };

        if (newUser != null) {
            adminManager.addUser(newUser);
            response.sendRedirect("AdminServlet?action=list&success=User+added+successfully");
        } else {
            response.sendRedirect("admin_dashboard.jsp?error=Invalid+Role");
        }
    }

    private void updateUserRole(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userId = request.getParameter("id");
        String newRole = request.getParameter("role");

        if (userId != null && newRole != null) {
            adminManager.updateUserRole(userId, newRole);
        }
        response.sendRedirect("AdminServlet?action=list&success=Role+updated+successfully");
    }
}
