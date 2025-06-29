<%@ page import="com.example.hotel_reservation_system.model.Reservation" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.hotel_reservation_system.dao.RoomManager" %>
<%@ page import="com.example.hotel_reservation_system.model.Room" %>
<%
  String role = (String) session.getAttribute("role");
  if (!"Staff".equals(role)) {
    response.sendRedirect("login.jsp?error=Unauthorized+Access");
    return;
  }

  RoomManager roomManager = new RoomManager();
  List<Room> rooms = (List<Room>) request.getAttribute("rooms");
  if (rooms == null) {
    rooms = roomManager.getAllRooms();
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title>Staff Dashboard</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background: #f8f9fa;
    }
    .dashboard-header {
      background: linear-gradient(to right, #2575fc, #6a11cb);
      color: white;
    }
    .card {
      border-radius: 0.5rem;
      box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
      transition: transform 0.3s;
    }
    .card:hover {
      transform: translateY(-5px);
    }
    .table-container {
      background: white;
      border-radius: 0.5rem;
      padding: 1.5rem;
      box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
    }
  </style>
</head>
<body>
<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-dark dashboard-header">
  <div class="container">
    <a class="navbar-brand" href="#">Hotel Management System</a>
    <div class="navbar-nav ms-auto">
      <span class="nav-link">Welcome, <%= session.getAttribute("username") %> (Staff)</span>
      <a class="nav-link" href="profile.jsp">Profile</a>
      <a class="nav-link" href="logout.jsp">Logout</a>
    </div>
  </div>
</nav>

<div class="container my-5">
  <!-- Quick Stats Cards -->
  <div class="row mb-4">
    <div class="col-md-4 mb-3">
      <div class="card text-white bg-primary h-100">
        <div class="card-body">
          <h5 class="card-title">Total Rooms</h5>
          <p class="card-text display-6"><%= roomManager.getAllRooms().size() %></p>
        </div>
      </div>
    </div>
    <div class="col-md-4 mb-3">
      <div class="card text-white bg-success h-100">
        <div class="card-body">
          <h5 class="card-title">Available Rooms</h5>
          <p class="card-text display-6">
            <%= roomManager.getAllRooms().stream()
                    .filter(Room::isAvailable).count() %>
          </p>
        </div>
      </div>
    </div>
    <div class="col-md-4 mb-3">
      <div class="card text-white bg-info h-100">
        <div class="card-body">
          <h5 class="card-title">Deluxe Rooms</h5>
          <p class="card-text display-6">
            <%= roomManager.searchRooms("Deluxe", null, null).size() %>
          </p>
        </div>
      </div>
    </div>
  </div>

  <!-- Room Management Section -->
  <div class="card shadow mb-5">
    <div class="card-header bg-white">
      <h4 class="mb-0">Room Management</h4>
    </div>
    <div class="card-body">
      <!-- Search Form -->
      <form action="RoomServlet" method="post" class="mb-4">
        <input type="hidden" name="action" value="search">
        <div class="row g-3">
          <div class="col-md-4">
            <label class="form-label">Room Type</label>
            <select name="type" class="form-select">
              <option value="">All Types</option>
              <option value="Standard">Standard</option>
              <option value="Deluxe">Deluxe</option>
            </select>
          </div>
          <div class="col-md-4">
            <label class="form-label">Min Price</label>
            <input type="number" class="form-control" placeholder="Min" name="minPrice">
          </div>
          <div class="col-md-4">
            <label class="form-label">Max Price</label>
            <input type="number" class="form-control" placeholder="Max" name="maxPrice">
          </div>
          <div class="col-12">
            <button type="submit" class="btn btn-primary">Search Rooms</button>
            <a href="staff_dashboard.jsp" class="btn btn-secondary">Reset</a>
          </div>
        </div>
      </form>



      <!-- Rooms Table -->
      <div class="table-responsive">
        <table class="table table-striped table-hover">
          <thead class="table-light">
          <tr>
            <th>Room #</th>
            <th>Type</th>
            <th>Price</th>

            <th>Amenities</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <% for (Room room : rooms) { %>
          <tr>
            <td><%= room.getRoomNumber() %></td>
            <td>
              <span class="badge <%= "Deluxe".equals(room.getType()) ? "bg-warning" : "bg-secondary" %>">
                <%= room.getType() %>
              </span>
            </td>
            <td>$<%= String.format("%.2f", room.getPrice()) %></td>

            <td><%= room.getAmenities() %></td>
            <td>
              <div class="d-flex gap-2">
                <!-- Edit Button -->
                <form action="RoomServlet" method="get" style="display:inline;">
                  <input type="hidden" name="roomNumber" value="<%= room.getRoomNumber() %>"/>
                  <button type="submit" class="btn btn-warning btn-sm">Edit</button>
                </form>
                <!-- Delete Button -->
                <form action="RoomServlet" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this room?');">
                  <input type="hidden" name="action" value="delete"/>
                  <input type="hidden" name="roomNumber" value="<%= room.getRoomNumber() %>"/>
                  <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
                <!-- Update Reservation Button -->
                <form action="ReservationServlet" method="get" style="display:inline;">
                  <input type="hidden" name="roomNumber" value="<%= room.getRoomNumber() %>"/>
                  <button type="submit" class="btn btn-info btn-sm">Update Reservation</button>
                </form>
              </div>
            </td>
          </tr>
          <% } %>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <!-- Quick Actions -->
  <div class="row">
    <div class="col-md-6 mb-3">
      <div class="card h-100">
        <div class="card-header">
          <h5 class="mb-0">Quick Actions</h5>
        </div>
        <div class="card-body">
          <div class="d-grid gap-2">
            <a href="edit_room.jsp" class="btn btn-primary">Add New Room</a>
            <a href="ReservationServlet?action=viewAll" class="btn btn-outline-primary">View Reservations</a>
            <a href="payments?action=history" class="btn btn-outline-secondary">View all payments</a>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6 mb-3">
      <div class="card h-100">
        <div class="card-header">
          <h5 class="mb-0">Recent Activity</h5>
        </div>
        <div class="card-body">
          <ul class="list-group list-group-flush">
            <li class="list-group-item">Updated Room 101 details</li>
            <li class="list-group-item">Marked Room 205 as occupied</li>
            <li class="list-group-item">Added new Deluxe room</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white text-center py-3 mt-5">
  <div class="container">
    <p class="mb-0">Hotel Management System &copy; <%= java.time.Year.now().getValue() %></p>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>