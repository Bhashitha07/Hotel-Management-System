package com.example.hotel_reservation_system.dao;

import com.example.hotel_reservation_system.model.AnonymousReview;
import com.example.hotel_reservation_system.model.Review;
import com.example.hotel_reservation_system.model.VerifiedReview;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReviewDAO {
    private static final String FILE_PATH = "C:\\Users\\Bhashitha\\OneDrive\\Desktop\\New folder\\reviews.txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void saveReview(Review review) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(formatReview(review));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatReview(Review review) {
        return String.join("|",
                review.getId(),
                review instanceof VerifiedReview ? "verified" : "anonymous",
                review.getReviewer(),
                review.getMessage(),
                sdf.format(review.getDate()),
                review.getStatus()
        );
    }

    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return reviews;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String id = parts[0];
                    String type = parts[1];
                    String reviewer = parts[2];
                    String message = parts[3];
                    Date date = sdf.parse(parts[4]);
                    String status = parts[5];

                    Review review = "verified".equalsIgnoreCase(type)
                            ? new VerifiedReview(id, message, date, status, reviewer)
                            : new AnonymousReview(id, message, date, status);
                    reviews.add(review);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void updateReviewStatus(String id, String newStatus) {
        List<Review> reviews = getAllReviews();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Review r : reviews) {
                if (r.getId().equals(id)) {
                    r.setStatus(newStatus);
                }
                writer.write(formatReview(r));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteReview(String id) {
        List<Review> reviews = getAllReviews();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Review r : reviews) {
                if (!r.getId().equals(id)) {
                    writer.write(formatReview(r));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
