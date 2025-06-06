<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.hotel_reservation_system.model.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <title>Admin Dashboard | HotelSystem</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background: linear-gradient(to right, #007BFF, #00BFFF);
      min-height: 100vh;
      color: #333;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .dashboard-container {
      background-color: #ffffff;
      padding: 2rem;
      border-radius: 1rem;
      box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
    }

    h2, h4 {
      color: #007BFF;
      font-weight: bold;
    }

    .table {
      background-color: #f9f9f9;
      color: #000;
    }

    .table th {
      background-color: #007BFF;
      color: #fff;
    }

    .table td {
      background-color: #ffffff;
    }

    .form-control,
    .form-select {
      background-color: #ffffff;
      color: #000;
      border: 1px solid #ccc;
    }

    .form-control:focus,
    .form-select:focus {
      border-color: #007BFF;
      box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
    }

    .card {
      background-color: #f1faff;
      border: 1px solid #cce5ff;
      box-shadow: 0 6px 20px rgba(0, 123, 255, 0.1);
    }

    .btn-primary {
      background-color: #007BFF;
      border: none;
    }

    .btn-danger {
      background-color: #dc3545;
    }

    .btn-success {
      background-color: #28a745;
    }

    .btn-secondary {
      background-color: #6c757d;
    }

    .btn:hover {
      opacity: 0.9;
    }

    .alert {
      border-radius: 0.5rem;
    }
  </style>
</head>
<body>
<div class="container mt-5 dashboard-container">
  <h2 class="text-center mb-4">Admin Dashboard</h2>

  <% if (request.getParameter("error") != null) { %>
  <div class="alert alert-danger text-center">
    <%= request.getParameter("error").replace("+", " ") %>
  </div>
  <% } %>

  <% if (request.getParameter("success") != null) { %>
  <div class="alert alert-success text-center">
    <%= request.getParameter("success").replace("+", " ") %>
  </div>
  <% } %>

  <%
    List<User> userList = (List<User>) request.getAttribute("userList");
    if (userList == null) {
      response.sendRedirect("AdminServlet?action=list");
      return;
    }
  %>

  <!-- Users Table -->
  <div class="table-responsive">
    <table class="table table-bordered table-hover mt-4">
      <thead>
      <tr>
        <th>Username</th>
        <th>Email</th>
        <th>Role</th>
        <th>Change Role</th>
        <th>Delete</th>
      </tr>
      </thead>
      <tbody>
      <% for (User user : userList) { %>
      <tr>
        <td><%= user.getUsername() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getRole() %></td>
        <td>
          <form action="AdminServlet" method="post" class="d-flex">
            <input type="hidden" name="action" value="updateRole">
            <input type="hidden" name="id" value="<%= user.getId() %>">
            <select name="role" class="form-select me-2" required>
              <option value="Guest" <%= user.getRole().equals("Guest") ? "selected" : "" %>>Guest</option>
              <option value="Staff" <%= user.getRole().equals("Staff") ? "selected" : "" %>>Staff</option>
              <option value="Admin" <%= user.getRole().equals("Admin") ? "selected" : "" %>>Admin</option>
            </select>
            <button type="submit" class="btn btn-primary btn-sm">Update</button>
          </form>
        </td>
        <td>
          <a href="AdminServlet?action=delete&id=<%= user.getId() %>"
             class="btn btn-danger btn-sm"
             onclick="return confirm('Are you sure you want to delete this user?');">
            Delete
          </a>
        </td>
      </tr>
      <% } %>
      </tbody>
    </table>
  </div>

  <!-- Add User Form -->
  <h4 class="mt-5 mb-3">Add New User</h4>
  <div class="border border-primary rounded p-3 bg-white">
    <form action="AdminServlet" method="post" class="card p-4">
      <input type="hidden" name="action" value="add">

      <div class="row g-3">
        <div class="col-md-6">
          <label for="username" class="form-label">Username</label>
          <input type="text" name="username" id="username" class="form-control" required>
        </div>

        <div class="col-md-6">
          <label for="password" class="form-label">Password</label>
          <input type="password" name="password" id="password" class="form-control" required>
        </div>

        <div class="col-md-6">
          <label for="email" class="form-label">Email</label>
          <input type="email" name="email" id="email" class="form-control" required>
        </div>

        <div class="col-md-6">
          <label for="role" class="form-label">Role</label>
          <select name="role" id="role" class="form-select" required>
            <option value="Guest">Guest</option>
            <option value="Staff">Staff</option>
            <option value="Admin">Admin</option>
          </select>
        </div>
      </div>

      <div class="mt-4 text-end">
        <button type="submit" class="btn btn-success">Add User</button>
      </div>
    </form>
  </div>

  <!-- Back Button -->
  <div class="text-center mt-4">
    <a href="index.jsp" class="btn btn-secondary">⬅ Back to Main Dashboard</a>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
