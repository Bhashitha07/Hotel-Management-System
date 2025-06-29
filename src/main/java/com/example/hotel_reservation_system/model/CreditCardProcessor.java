package com.example.hotel_reservation_system.Payment;

public class CreditCardProcessor implements PaymentProcessor {
    private final String cardNumber;
    private final String cvv;

    public CreditCardProcessor(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(double amount) {
        // Dummy logic for credit card validation
        return cardNumber != null && cardNumber.matches("\\d{16}") &&
                cvv != null && cvv.matches("\\d{3}");
    }
}
