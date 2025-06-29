<%@ page import="com.example.hotel_reservation_system.model.Reservation" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reservation Management</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
            color: #333;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        .search-panel {
            background-color: #eaf2f8;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .search-panel form {
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .search-panel label {
            font-weight: bold;
        }
        .search-panel input[type="date"] {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-panel button, .action-btn {
            padding: 8px 15px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }
        .search-panel button:hover, .action-btn:hover {
            background-color: #2980b9;
        }
        .action-btn.edit {
            background-color: #2ecc71;
        }
        .action-btn.edit:hover {
            background-color: #27ae60;
        }
        .action-btn.cancel {
            background-color: #e74c3c;
        }
        .action-btn.cancel:hover {
            background-color: #c0392b;
        }
        .action-btn.delete {
            background-color: #7f8c8d;
        }
        .action-btn.delete:hover {
            background-color: #95a5a6;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #2c3e50;
            color: white;
            position: sticky;
            top: 0;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .no-reservations {
            text-align: center;
            padding: 20px;
            color: #7f8c8d;
            font-style: italic;
        }
        .status-booked {
            color: #27ae60;
            font-weight: bold;
        }
        .status-cancelled {
            color: #e74c3c;
            font-weight: bold;
        }
        .actions-cell {
            display: flex;
            gap: 5px;
        }
        .new-reservation-btn {
            display: inline-block;
            margin-bottom: 20px;
            padding: 10px 15px;
            background-color: #2ecc71;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .new-reservation-btn:hover {
            background-color: #27ae60;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Reservation Management</h1>

    <a href="reservation.jsp" class="new-reservation-btn"> Add New Reservation</a>

    <div class="search-panel">
        <form action="ReservationServlet" method="get">
            <input type="hidden" name="action" value="searchByDate">
            <label for="searchDate">Search by Check-In Date:</label>
            <input type="date" id="searchDate" name="searchDate" required>
            <button type="submit">Search</button>
            <a href="ReservationServlet?action=viewAll" class="action-btn">Show All</a>
        </form>
    </div>

    <%
        List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (reservations == null || reservations.isEmpty()) {
    %>
    <div class="no-reservations">No reservations found.</div>
    <%
    } else {
    %>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>User ID</th>
            <th>Room</th>
            <th>Check-In</th>
            <th>Check-Out</th>
            <th>Total</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Reservation reservation : reservations) { %>
        <tr>
            <td><%= reservation.getReservationId() %></td>
            <td><%= reservation.getUserId() %></td>
            <td><%= reservation.getRoomNumber() %></td>
            <td><%= dateFormat.format(reservation.getCheckInDate()) %></td>
            <td><%= dateFormat.format(reservation.getCheckOutDate()) %></td>
            <td>$<%= String.format("%.2f", reservation.getTotalPrice()) %></td>
            <td class="status-<%= reservation.getStatus().toLowerCase() %>">
                <%= reservation.getStatus() %>
            </td>
            <td class="actions-cell">
                <a href="ReservationServlet?action=view&reservationId=<%= reservation.getReservationId() %>"
                   class="action-btn">View</a>
                <a href="reservation_modify.jsp?reservationId=<%= reservation.getReservationId() %>"
                   class="action-btn edit">Edit</a>
                <a href="ReservationServlet?action=cancel&reservationId=<%= reservation.getReservationId() %>"
                   class="action-btn cancel">Cancel</a>
                <a href="ReservationServlet?action=delete&reservationId=<%= reservation.getReservationId() %>"
                   class="action-btn delete">Delete</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <%
        }
    %>
</div>
</body>
</html>