package com.example.hotel_reservation_system.servlet;

import com.example.hotel_reservation_system.dao.ReservationDAO;
import com.example.hotel_reservation_system.model.Reservation;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("viewAll".equals(action)) {
            List<Reservation> reservations = reservationDAO.getAllReservationsSortedByCheckInDate();
            request.setAttribute("reservations", reservations);
            request.getRequestDispatcher("view_reservations.jsp").forward(request, response);
        }
        else if ("searchByDate".equals(action)) {
            try {
                Date searchDate = Date.valueOf(request.getParameter("searchDate"));
                List<Reservation> reservations = reservationDAO.searchReservationsByCheckInDate(searchDate);
                request.setAttribute("reservations", reservations);
                request.getRequestDispatcher("view_reservations.jsp").forward(request, response);
            } catch (IllegalArgumentException e) {
                response.sendRedirect("reservation_history.jsp?error=Invalid+Date+Format");
            }
        }
        else if ("view".equals(action)) {
            String reservationId = request.getParameter("reservationId");
            Reservation reservation = reservationDAO.getReservationById(reservationId);
            if (reservation != null) {
                request.setAttribute("reservation", reservation);
                request.getRequestDispatcher("view_reservation.jsp").forward(request, response);
            } else {
                response.sendRedirect("reservation_history.jsp?error=Reservation+Not+Found");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("reservation.jsp?error=Missing+Action");
            return;
        }

        switch (action) {
            case "book":
                handleBooking(request, response);
                break;
            case "update":
                handleUpdate(request, response);
                break;
            case "cancel":
                handleCancel(request, response);
                break;
            case "delete":
                handleDelete(request, response);
                break;
            default:
                response.sendRedirect("reservation.jsp?error=Invalid+Action");
        }
    }

    private void handleBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String reservationId = generateReservationId();
            String userId = request.getParameter("userId");
            String roomNumber = request.getParameter("roomNumber");
            Date checkInDate = Date.valueOf(request.getParameter("checkInDate"));
            Date checkOutDate = Date.valueOf(request.getParameter("checkOutDate"));

            Reservation reservation = new Reservation(
                    reservationId, userId, roomNumber,
                    checkInDate, checkOutDate, "Booked"
            );

            reservationDAO.saveReservation(reservation);
            response.sendRedirect("reservation_confirmation.jsp?reservationId=" + reservationId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("reservation.jsp?error=Booking+Failed");
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String reservationId = request.getParameter("reservationId");
            Date checkInDate = Date.valueOf(request.getParameter("checkInDate"));
            Date checkOutDate = Date.valueOf(request.getParameter("checkOutDate"));

            Reservation reservation = reservationDAO.getReservationById(reservationId);
            if (reservation != null) {
                reservation.setCheckInDate(checkInDate);
                reservation.setCheckOutDate(checkOutDate);
                reservationDAO.updateReservation(reservation);
                response.sendRedirect("ReservationServlet?action=view&reservationId=" + reservationId);
            } else {
                response.sendRedirect("reservation_history.jsp?error=Reservation+Not+Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("reservation_history.jsp?error=Update+Failed");
        }
    }

    private void handleCancel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String reservationId = request.getParameter("reservationId");
            reservationDAO.cancelReservation(reservationId);
            response.sendRedirect("ReservationServlet?action=view&reservationId=" + reservationId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("reservation_history.jsp?error=Cancellation+Failed");
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String reservationId = request.getParameter("reservationId");
            reservationDAO.deleteReservation(reservationId);
            response.sendRedirect("reservation_history.jsp?success=Reservation+Deleted");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("reservation_history.jsp?error=Deletion+Failed");
        }
    }

    private String generateReservationId() {
        return "RES-" + System.currentTimeMillis();
    }
}