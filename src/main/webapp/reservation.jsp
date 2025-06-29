<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
  <title>Book a Reservation | HotelSys</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background: #f2f4f7;
    }
    .reservation-card {
      max-width: 600px;
      margin: auto;
      margin-top: 50px;
      padding: 30px;
      border-radius: 1rem;
      background: white;
      box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
    }
    .form-label {
      font-weight: 500;
    }
    .hidden {
      display: none;
    }
  </style>
  <script>
    function toggleBalcony(type) {
      const balcony = document.getElementById("balconyOption");
      balcony.classList.toggle("hidden", type !== "Deluxe");
    }
  </script>
</head>
<body>

<div class="container">
  <div class="reservation-card">
    <h3 class="text-center mb-4">Book a Reservation</h3>

    <form action="ReservationServlet" method="post">
      <input type="hidden" name="action" value="book">

      <div class="mb-3">
        <label class="form-label">Reservation ID</label>
        <input type="text" name="reservationId" class="form-control" placeholder="Enter Reservation ID" required>
      </div>

      <div class="mb-3">
        <label class="form-label">User ID</label>
        <input type="text" name="userId" class="form-control" placeholder="Enter Your User ID" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Room Number</label>
        <input type="text" name="roomNumber" class="form-control" placeholder="Room No." required>
      </div>

      <div class="mb-3">
        <label class="form-label">Room Type</label>
        <select name="roomType" class="form-select" onchange="toggleBalcony(this.value)" required>
          <option value="">Select Room Type</option>
          <option value="Standard">Standard</option>
          <option value="Deluxe">Deluxe</option>
        </select>
      </div>

      <div class="mb-3">
        <label class="form-label">Amenities</label>
        <input type="text" name="amenities" class="form-control" placeholder="WiFi, TV, AC..." required>
      </div>

      <div id="balconyOption" class="mb-3 hidden">
        <label class="form-check-label">
          <input type="checkbox" name="hasBalcony" class="form-check-input">
          Has Balcony
        </label>
      </div>

      <div class="mb-3">
        <label class="form-label">Check-In Date</label>
        <input type="date" name="checkInDate" class="form-control" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Check-Out Date</label>
        <input type="date" name="checkOutDate" class="form-control" required>
      </div>

      <button type="submit" class="btn btn-primary w-100">Confirm Reservation</button>
    </form>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
