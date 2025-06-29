package com.example.hotel_reservation_system.servlet;

import com.example.hotel_reservation_system.dao.ReviewDAO;
import com.example.hotel_reservation_system.model.AnonymousReview;
import com.example.hotel_reservation_system.model.Review;
import com.example.hotel_reservation_system.model.VerifiedReview;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@WebServlet("/submitReview")
public class SubmitReviewServlet extends HttpServlet {
    private ReviewDAO reviewDAO;

    @Override
    public void init() {
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String content = request.getParameter("content");
        boolean isAnonymous = request.getParameter("anonymous") != null;

        String id = UUID.randomUUID().toString();
        Date now = new Date();
        String status = "Published"; // Default status

        Review review;
        if (isAnonymous) {
            review = new AnonymousReview(id, content, now, status);
        } else {
            review = new VerifiedReview(id, content, now, status, userId);
        }

        reviewDAO.saveReview(review);

        response.sendRedirect(request.getContextPath() + "/guest_dashboard.jsp");
    }
}
