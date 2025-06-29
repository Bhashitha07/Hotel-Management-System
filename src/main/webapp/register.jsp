<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register | HotelSys</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .bg-hero {
            background-image: url('https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }

        .navbar-brand {
            font-weight: bold;
            letter-spacing: 1px;
        }

        .nav-link {
            color: white !important;
            transition: color 0.3s;
        }

        .nav-link:hover {
            color: #ffc107 !important;
        }

        .frosted-card {
            backdrop-filter: blur(20px);
            background-color: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.25);
            border-radius: 1rem;
            padding: 2.5rem;
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.3);
            animation: fadeIn 1s ease-in-out;
        }

        .frosted-card h3 {
            font-weight: bold;
            letter-spacing: 1px;
        }

        .frosted-card input, .frosted-card select {
            background-color: rgba(255, 255, 255, 0.85);
            border: none;
            color: #000;
        }

        .frosted-card input::placeholder, .frosted-card select {
            color: #555;
        }

        .btn-primary {
            background-color: #0077cc;
            border: none;
            transition: background-color 0.3s;
        }

        .btn-primary:hover {
            background-color: #005fa3;
        }

        .text-warning {
            color: #ffc107 !important;
        }

        .text-warning:hover {
            color: #ffdb70 !important;
        }

        footer {
            background-color: #1e1e1e;
            color: white;
            padding: 20px 0;
        }

        footer a {
            color: #adb5bd;
            text-decoration: none;
            transition: color 0.3s;
        }

        footer a:hover {
            color: #fff;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
    <div class="container-fluid px-4">
        <a class="navbar-brand" href="#">HotelSys</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Register Section -->
<div class="bg-hero min-vh-100 d-flex align-items-center justify-content-center">
    <div class="frosted-card text-white" style="width: 100%; max-width: 430px;">
        <h3 class="text-center mb-4">Create Your Account</h3>

        <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-danger text-center">
            <%= request.getParameter("error").replace("+", " ") %>
        </div>
        <% } %>

        <form action="UserServlet" method="post">
            <%
                String uuid = java.util.UUID.randomUUID().toString();
            %>
            <input type="hidden" name="action" value="register" />
            <input type="hidden" name="userId" value="<%= uuid %>" />

            <div class="mb-3">
                <input type="text" name="username" class="form-control" placeholder="Username" required
                       value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>"/>
            </div>

            <div class="mb-3">
                <input type="password" name="password" class="form-control" placeholder="Password" required />
            </div>

            <div class="mb-3">
                <input type="email" name="email" class="form-control" placeholder="Email" required
                       value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>"/>
            </div>

            <div class="mb-4">
                <select name="role" class="form-select" required>
                    <option value="">Select Role</option>
                    <option value="Guest" <%= "Guest".equals(request.getParameter("role")) ? "selected" : "" %>>Guest</option>
                    <option value="Staff" <%= "Staff".equals(request.getParameter("role")) ? "selected" : "" %>>Staff</option>
                    <option value="Admin" <%= "Admin".equals(request.getParameter("role")) ? "selected" : "" %>>Admin</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary w-100">Register</button>
        </form>

        <div class="text-center mt-3">
            Already have an account?
            <a href="login.jsp" class="text-warning text-decoration-underline">Login Here</a>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="text-center mt-5">
    <div class="container">
        <p class="mb-1">&copy; 2025 HotelSys. All rights reserved.</p>
        <small>
            <a href="about.jsp">About</a> |
            <a href="contact.jsp">Contact</a> |
            <a href="terms.jsp">Terms</a>
        </small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>