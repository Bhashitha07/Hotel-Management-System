<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.example.hotel_reservation_system.Payment.Payment" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Payment History</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container">
    <a class="navbar-brand" href="#">Hotel System</a>
    <div class="navbar-nav ms-auto">
      <a class="nav-link" href="${pageContext.request.contextPath}/paymentForm.jsp">Payment</a>
      <a class="nav-link" href="${pageContext.request.contextPath}/profile.jsp">Profile</a>
      <a class="nav-link" href="${pageContext.request.contextPath}/logout.jsp">Logout</a>
    </div>
  </div>
</nav>

<div class="container my-5">
  <h2 class="mb-4">Payment History</h2>

  <div class="card p-4 shadow-sm mb-4">
    <table class="table table-bordered bg-white">
      <thead class="table-primary">
      <tr>
        <th>Payment Method</th>
        <th>Amount</th>
        <th>Date</th>
        <th>Status</th>
      </tr>
      </thead>
      <tbody>
      <%
        List<Payment> payments = (List<Payment>) request.getAttribute("payments");
        if (payments != null && !payments.isEmpty()) {
          for (Payment p : payments) {
      %>
      <tr>
        <td><%= p.getPaymentMethod() %></td>
        <td>$<%= String.format("%.2f", p.getAmount()) %></td>
        <td><%= p.getDate() %></td>
        <td><%= p.getStatus() %></td>
      </tr>
      <%
        }
      } else {
      %>
      <tr><td colspan="4">No payments found.</td></tr>
      <%
        }
      %>
      </tbody>
    </table>
  </div>

  <a href="payments?action=new" class="btn btn-primary">Make Another Payment</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
