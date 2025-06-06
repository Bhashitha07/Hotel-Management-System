<%@ page import="com.example.hotel_reservation_system.model.Room" %>
<%@ page import="com.example.hotel_reservation_system.model.DeluxeRoom" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
  Room room = (Room) request.getAttribute("room");
  boolean isEdit = room != null;
%>
<!DOCTYPE html>
<html>
<head>
  <title><%= isEdit ? "Edit Room" : "Add Room" %></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
  <div class="card shadow">
    <div class="card-header">
      <h2 class="mb-0"><%= isEdit ? "Edit Room" : "Add New Room" %></h2>
    </div>

    <div class="card-body">
      <form action="RoomServlet" method="post">
        <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
        <div class="mb-3">
          <label class="form-label">Room Number</label>
          <input type="text" class="form-control" name="roomNumber" value="<%= isEdit ? room.getRoomNumber() : "" %>" <%= isEdit ? "readonly" : "required" %>>
        </div>
        <div class="mb-3">
          <label class="form-label">Room Type</label>
          <select class="form-select" name="type" required onchange="toggleBalcony(this.value)">
            <option value="Standard" <%= isEdit && "Standard".equals(room.getType()) ? "selected" : "" %>>Standard</option>
            <option value="Deluxe" <%= isEdit && "Deluxe".equals(room.getType()) ? "selected" : "" %>>Deluxe</option>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Price</label>
          <input type="number" class="form-control" name="price" step="0.01" value="<%= isEdit ? room.getPrice() : "" %>" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Amenities</label>
          <input type="text" class="form-control" name="amenities" value="<%= isEdit ? room.getAmenities() : "" %>">
        </div>
        <div class="form-check mb-3" id="balconySection" style="<%= isEdit && "Deluxe".equals(room.getType()) ? "" : "display:none;" %>">
          <%
            boolean hasBalcony = isEdit && room instanceof DeluxeRoom && ((DeluxeRoom) room).hasBalcony();
          %>
          <input type="checkbox" class="form-check-input" name="hasBalcony" id="hasBalcony" <%= hasBalcony ? "checked" : "" %>>
          <label class="form-check-label" for="hasBalcony">Has Balcony</label>
        </div>
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
          <button type="submit" class="btn btn-primary"><%= isEdit ? "Update Room" : "Add Room" %></button>
          <a href="staff_dashboard.jsp" class="btn btn-secondary">Cancel</a>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function toggleBalcony(type) {
    const balconySection = document.getElementById("balconySection");
    balconySection.style.display = type === "Deluxe" ? "block" : "none";
  }
</script>
</body>
</html>
