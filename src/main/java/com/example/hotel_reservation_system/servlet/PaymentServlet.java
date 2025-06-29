package com.example.hotel_reservation_system.Payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/payments")
public class PaymentServlet extends HttpServlet {
    private final PaymentDAO paymentDao = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("new".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/paymentForm.jsp").forward(request, response);
        } else {
            List<Payment> payments = paymentDao.getAllPayments();
            request.setAttribute("payments", payments);
            request.getRequestDispatcher("/payments.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String paymentMethod = request.getParameter("paymentMethod");
            String amountStr = request.getParameter("amount");

            if (paymentMethod == null || amountStr == null || amountStr.isEmpty()) {
                throw new IllegalArgumentException("Payment method and amount are required.");
            }

            double amount = Double.parseDouble(amountStr);

            Payment payment = new Payment();
            payment.setPaymentMethod(paymentMethod);
            payment.setAmount(amount);
            payment.setDate(new Date());
            payment.setStatus("Processing");

            PaymentProcessor processor;

            if ("credit".equalsIgnoreCase(paymentMethod)) {
                String cardNumber = request.getParameter("cardNumber");
                String cvv = request.getParameter("cvv");

                if (cardNumber == null || cardNumber.length() != 16 || cvv == null || cvv.length() != 3) {
                    throw new IllegalArgumentException("Invalid credit card details.");
                }

                processor = new CreditCardProcessor(cardNumber, cvv);
            } else {
                // Fallback processor for other types (e.g., cash)
                processor = amount1 -> true;
            }

            boolean success = processor.processPayment(amount);
            payment.setStatus(success ? "Completed" : "Failed");

            paymentDao.savePayment(payment);
            response.sendRedirect("payments");

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Payment error: " + e.getMessage());
            request.getRequestDispatcher("/paymentForm.jsp").forward(request, response);
        }
    }
}
