<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.hotel_reservation_system.model.Room" %>
<%@ page import="com.example.hotel_reservation_system.model.DeluxeRoom" %>
<!DOCTYPE html>
<html>
<head>
    <title>Room Search Results</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        table {
            width: 80%;
            border-collapse: collapse;
            margin: 20px auto;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #f2f2f2;
        }

        h2 {
            text-align: center;
            margin-top: 30px;
        }

        .message {
            text-align: center;
            margin-top: 20px;
            color: #888;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<h2>Room Search Results</h2>

<%
    List<Room> rooms = (List<Room>) request.getAttribute("rooms");
    if (rooms != null && !rooms.isEmpty()) {
%>
<table>
    <tr>
        <th>Room Number</th>
        <th>Type</th>
        <th>Price</th>
        <th>Amenities</th>
        <th>Balcony</th>
    </tr>
    <%
        for (Room room : rooms) {
    %>
    <tr>
        <td><%= room.getRoomNumber() %></td>
        <td><%= room.getType() %></td>
        <td>$<%= String.format("%.2f", room.getPrice()) %></td>
        <td><%= room.getAmenities() %></td>
        <td>
            <%= (room instanceof DeluxeRoom)
                    ? (((DeluxeRoom) room).hasBalcony() ? "Yes" : "No")
                    : "N/A" %>
        </td>
    </tr>
    <%
        }
    %>
</table>
<% } else { %>
<p class="message">No rooms found matching the criteria.</p>
<% } %>

<a href="staff_dashboard.jsp">← Back to Dashboard</a>
</body>
</html>
