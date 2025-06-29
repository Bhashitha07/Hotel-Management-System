package com.example.hotel_reservation_system.servlet;

import com.example.hotel_reservation_system.dao.ReviewDAO;
import com.example.hotel_reservation_system.model.AnonymousReview;
import com.example.hotel_reservation_system.model.Review;
import com.example.hotel_reservation_system.model.VerifiedReview;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {
    private final ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Review> reviews = reviewDAO.getAllReviews();
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("reviews.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = request.getParameter("message");
        String type = request.getParameter("type");
        String username = request.getParameter("username");

        Review review;
        String id = UUID.randomUUID().toString();
        Date now = new Date();
        String status = "Pending";

        if ("verified".equalsIgnoreCase(type) && username != null) {
            review = new VerifiedReview(id, message, now, status, username);
        } else {
            review = new AnonymousReview(id, message, now, status);
        }

        reviewDAO.saveReview(review);
        response.sendRedirect("reviews");
    }
}
