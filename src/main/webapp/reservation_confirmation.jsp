<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reservation Confirmed | HotelSys</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #e3f2fd, #f1f8e9);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .confirmation-box {
            background: white;
            padding: 40px 30px;
            border-radius: 20px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 500px;
            animation: slideIn 0.6s ease-out;
        }

        @keyframes slideIn {
            from { transform: translateY(40px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
        }

        .confirmation-box h2 {
            color: #2ecc71;
            margin-bottom: 15px;
        }

        .confirmation-box p {
            font-size: 18px;
            color: #333;
            margin: 12px 0;
        }

        .confirmation-box a {
            display: inline-block;
            margin: 10px 5px;
            font-size: 16px;
            color: #2980b9;
            text-decoration: none;
            transition: color 0.2s;
        }

        .confirmation-box a:hover {
            color: #1c5980;
        }

        .button {
            margin-top: 25px;
            padding: 12px 28px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        .button:hover {
            background-color: #2c80b4;
        }
    </style>
</head>
<body>

<div class="confirmation-box">
    <h2>🎉 Reservation Successful!</h2>

    <%
        String successMessage = request.getParameter("success");
        if (successMessage != null) {
    %>
    <p style="color: #27ae60;"><%= successMessage %></p>
    <%
    } else {
    %>
    <p style="color: #27ae60;">Your reservation has been successfully submitted.</p>
    <%
        }
    %>



    <br>
    <button class="button" onclick="window.location.href='paymentForm.jsp'">💳 Proceed to Payment</button>
    <button class="button" onclick="window.location.href='view_reservations.jsp'">View Reservation</button>
</div>

</body>
</html>
