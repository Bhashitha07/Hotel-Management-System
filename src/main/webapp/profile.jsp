<%@ page import="com.example.hotel_reservation_system.*" %>
<%
    String userId = (String) session.getAttribute("userId");
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");

    if (userId == null) {
        response.sendRedirect("login.jsp?error=Session+Expired");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
        }
        .card {
            border-radius: 1rem;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="card p-4 shadow mx-auto" style="max-width: 500px;">
        <h3 class="text-center mb-3">👤 My Profile</h3>

        <% if (request.getParameter("success") != null) { %>
        <div class="alert alert-success text-center"><%= request.getParameter("success") %></div>
        <% } %>

        <div class="mb-3">
            <label class="form-label">Role</label>
            <input type="text" class="form-control" value="<%= role %>" disabled />
        </div>

        <form action="UserServlet" method="post">
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="id" value="<%= userId %>" />

            <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text" class="form-control" value="<%= username %>" disabled />
            </div>

            <div class="mb-3">
                <label class="form-label">New Email</label>
                <input type="email" name="email" class="form-control" required />
            </div>

            <div class="mb-3">
                <label class="form-label">New Password</label>
                <input type="password" name="password" class="form-control" required />
            </div>

            <button type="submit" class="btn btn-primary w-100">Update</button>
        </form>

        <div class="text-center mt-3">
            <a href="dashboard.jsp" class="btn btn-secondary">← Back to Dashboard</a>
        </div>
    </div>
</div>
</body>
</html>