<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Room Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function toggleBalconyField() {
            const roomType = document.getElementById('type').value;
            document.getElementById('balconyField').style.display =
                roomType === 'Deluxe' ? 'block' : 'none';
        }

        function confirmDelete(roomNumber) {
            return confirm('Are you sure you want to delete room ' + roomNumber + '?');
        }
    </script>
</head>
<body class="container mt-4">
<h2 class="mb-4">Room Management</h2>

<!-- Success/Error Messages -->
<c:if test="${not empty param.success}">
    <div class="alert alert-success">${param.success}</div>
</c:if>
<c:if test="${not empty param.error}">
    <div class="alert alert-danger">${param.error}</div>
</c:if>

<!-- Search Results -->
<c:if test="${not empty rooms}">
    <div class="card">
        <div class="card-header">Search Results</div>
        <div class="card-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Room Number</th>
                    <th>Type</th>
                    <th>Price</th>
                    <th>Amenities</th>
                    <th>Balcony</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${rooms}" var="room">
                    <tr>
                        <td>${room.roomNumber}</td>
                        <td>${room.type}</td>
                        <td>$${room.price}</td>
                        <td>${room.amenities}</td>
                        <td>
                            <c:if test="${room.getClass().simpleName == 'DeluxeRoom'}">
                                ${room.hasBalcony ? 'Yes' : 'No'}
                            </c:if>
                        </td>
                        <td>
                            <a href="RoomServlet?roomNumber=${room.roomNumber}"
                               class="btn btn-sm btn-warning">Edit</a>
                            <form action="RoomServlet" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="roomNumber" value="${room.roomNumber}">
                                <button type="submit" class="btn btn-sm btn-danger"
                                        onclick="return confirmDelete('${room.roomNumber}')">
                                    Delete
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>