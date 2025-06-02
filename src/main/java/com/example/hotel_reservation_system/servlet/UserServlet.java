package com.example.hotel_reservation_system.servlet;

import com.example.hotel_reservation_system.dao.UserManager;
import com.example.hotel_reservation_system.model.Admin;
import com.example.hotel_reservation_system.model.Guest;
import com.example.hotel_reservation_system.model.Staff;
import com.example.hotel_reservation_system.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private final UserManager userManager = new UserManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("index.jsp?error=No+action+specified");
            return;
        }

        switch (action) {
            case "register" -> handleRegister(request, response);
            case "login" -> handleLogin(request, response);
            case "update" -> handleUpdate(request, response);
            case "delete" -> handleDelete(request, response);
            default -> response.sendRedirect("index.jsp?error=Unknown+Action");
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = UUID.randomUUID().toString();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String email = request.getParameter("email");

        if (username == null || password == null || role == null || email == null) {
            response.sendRedirect("register.jsp?error=Missing+Fields");
            return;
        }

        User user = switch (role) {
            case "Guest" -> new Guest(id, username, password, email);
            case "Staff" -> new Staff(id, username, password, email);
            case "Admin" -> new Admin(id, username, password, email);
            default -> null;
        };

        if (user != null) {
            userManager.addUser(user);
            response.sendRedirect("login.jsp?success=Registration+Successful");
        } else {
            response.sendRedirect("register.jsp?error=Invalid+Role");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            response.sendRedirect("login.jsp?error=Missing+Credentials");
            return;
        }

        User user = userManager.getByUsernameAndPassword(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("userId", user.getId());

            switch (user.getRole()) {
                case "Admin" -> response.sendRedirect("admin_dashboard.jsp");
                case "Staff" -> response.sendRedirect("staff_dashboard.jsp");
                case "Guest" -> response.sendRedirect("guest_dashboard.jsp");
                default -> response.sendRedirect("login.jsp?error=Unknown+Role");
            }
        } else {
            response.sendRedirect("login.jsp?error=Invalid+credentials");
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (id == null || email == null || password == null) {
            response.sendRedirect("profile.jsp?error=Missing+Fields");
            return;
        }

        userManager.updateUser(id, email, password);
        response.sendRedirect("profile.jsp?success=Profile+Updated");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            response.sendRedirect("admin_dashboard.jsp?error=Missing+User+ID");
            return;
        }

        userManager.deleteUser(id);
        response.sendRedirect("admin_dashboard.jsp?success=User+Deleted");
    }
}
