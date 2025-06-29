package com.example.hotel_reservation_system.Payment;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentDAO {
    private static final String FILE_PATH = "C:\\Users\\Bhashitha\\OneDrive\\Desktop\\hello\\payments.txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void savePayment(Payment payment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String line = String.join("|",
                    payment.getPaymentMethod(),
                    String.valueOf(payment.getAmount()),
                    sdf.format(payment.getDate()),
                    payment.getStatus()
            );
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return payments;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    Payment payment = new Payment();
                    payment.setPaymentMethod(parts[0]);
                    payment.setAmount(Double.parseDouble(parts[1]));
                    payment.setDate(sdf.parse(parts[2]));
                    payment.setStatus(parts[3]);
                    payments.add(payment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payments;
    }

    public void updatePayment(String transactionId, String newStatus) {
        // Placeholder for future functionality if transactionId is implemented
        // Currently unused due to lack of ID in Payment
    }
}
