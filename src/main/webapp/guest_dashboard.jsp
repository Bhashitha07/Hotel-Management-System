<%@ page import="com.example.hotel_reservation_system.model.Room.*" %>
<%@ page import="com.example.hotel_reservation_system.model.Review.*" %>
<%@ page import="com.example.hotel_reservation_system.dao.UserManager" %>
<%@ page import="com.example.hotel_reservation_system.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.hotel_reservation_system.dao.RoomManager" %>
<%@ page import="com.example.hotel_reservation_system.dao.ReviewDAO" %>
<%@ page import="com.example.hotel_reservation_system.model.Review" %>
<%@ page import="com.example.hotel_reservation_system.model.Room" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  String role = (String) session.getAttribute("role");
  if (!"Guest".equals(role)) {
    response.sendRedirect(request.getContextPath() + "/login.jsp?error=Unauthorized+Access");
    return;
  }

  String userId = (String) session.getAttribute("userId");
  String username = (String) session.getAttribute("username");

  RoomManager roomManager = new RoomManager();

  // Get search parameters
  String type = request.getParameter("type");
  String minPriceStr = request.getParameter("minPrice");
  String maxPriceStr = request.getParameter("maxPrice");

  Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
  Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

  // Perform search - if no filters, search all
  List<Room> rooms = roomManager.searchRooms(
          (type != null && !type.isEmpty()) ? type : null,
          minPrice,
          maxPrice
  );

  // Filter only available rooms
  rooms.removeIf(room -> !room.isAvailable());
  request.setAttribute("rooms", rooms);

  // Load reviews and users (optional if needed)
  ReviewDAO reviewDAO = new ReviewDAO();
  List<Review> allReviews = reviewDAO.getAllReviews();
  request.setAttribute("allReviews", allReviews);

  UserManager userManager = new UserManager();
  List<User> allUsers = userManager.getAllUsers();
  request.setAttribute("users", allUsers);
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Guest Dashboard | Hotel Reservation</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet" />
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(135deg, #d0e7ff 0%, #ffffff 100%);
      min-height: 100vh;
      color: #222;
    }
    .navbar {
      background: #0d6efd !important;
      box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    }
    .navbar-brand {
      font-weight: bold;
    }
    .card {
      border-radius: 0.5rem;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    .table td, .table th {
      vertical-align: middle;
    }
  </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark">
  <div class="container">
    <a class="navbar-brand" href="#">Hotel Reservation System</a>
    <div class="navbar-nav ms-auto">
      <span class="nav-link text-white">Welcome, <%= username %> (Guest)</span>
      <a class="nav-link text-white" href="profile.jsp">Profile</a>
      <a class="nav-link text-white" href="logout.jsp">Logout</a>
    </div>
  </div>
</nav>

<div class="container mt-5">
  <h2 class="mb-4">Search Available Rooms</h2>
  <form method="get" action="guest_dashboard.jsp" class="row g-3">
    <div class="col-md-4">
      <label for="type" class="form-label">Room Type</label>
      <select name="type" id="type" class="form-select">
        <option value="">All Types</option>
        <option value="Standard" <%= "Standard".equals(type) ? "selected" : "" %>>Standard</option>
        <option value="Deluxe" <%= "Deluxe".equals(type) ? "selected" : "" %>>Deluxe</option>
      </select>
    </div>
    <div class="col-md-4">
      <label for="minPrice" class="form-label">Min Price</label>
      <input type="number" step="0.01" class="form-control" id="minPrice" name="minPrice" value="<%= minPriceStr != null ? minPriceStr : "" %>">
    </div>
    <div class="col-md-4">
      <label for="maxPrice" class="form-label">Max Price</label>
      <input type="number" step="0.01" class="form-control" id="maxPrice" name="maxPrice" value="<%= maxPriceStr != null ? maxPriceStr : "" %>">
    </div>
    <div class="col-12">
      <button type="submit" class="btn btn-primary">Search</button>
      <a href="guest_dashboard.jsp" class="btn btn-secondary">Reset</a>
    </div>
  </form>

  <hr class="my-4" />

  <h4>Available Rooms</h4>
  <div class="table-responsive">
    <table class="table table-hover table-bordered">
      <thead class="table-light">
      <tr>
        <th>Room #</th>
        <th>Type</th>
        <th>Price</th>
        <th>Amenities / Action</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="room" items="${rooms}">
        <tr>
          <td>${room.roomNumber}</td>
          <td>
              <span class="badge ${room.type == 'Deluxe' ? 'bg-warning' : 'bg-secondary'}">
                  ${room.type}
              </span>
          </td>
          <td>$${room.price}</td>
          <td>
              ${room.amenities}
            <form action="reservation.jsp" method="get" class="mt-2">
              <input type="hidden" name="roomNumber" value="${room.roomNumber}" />
              <button type="submit" class="btn btn-sm btn-success">Book</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${rooms.size() == 0}">
        <tr><td colspan="4" class="text-center">No rooms found.</td></tr>
      </c:if>
      </tbody>
    </table>
  </div>
</div>

<footer class="text-center py-4 mt-5 bg-light">
  <p class="mb-0">&copy; <%= java.time.Year.now().getValue() %> Hotel Reservation System</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
