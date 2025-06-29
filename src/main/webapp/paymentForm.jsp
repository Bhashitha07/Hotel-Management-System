<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Make a Payment</title>
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
    <div class="card p-4 mx-auto shadow" style="max-width: 500px;">
        <h3 class="text-center mb-4">Make a Payment</h3>

        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
        <div class="alert alert-danger text-center">
            <%= errorMessage %>
        </div>
        <% } %>

        <form action="payments" method="post">
            <div class="mb-3">
                <label for="paymentMethod" class="form-label">Payment Method:</label>
                <select name="paymentMethod" id="paymentMethod" class="form-select" onchange="toggleCardFields(this.value)">
                    <option value="cash">Cash</option>
                    <option value="credit">Credit Card</option>
                </select>
            </div>

            <div id="creditFields" style="display:none;">
                <div class="mb-3">
                    <label for="cardNumber" class="form-label">Card Number:</label>
                    <input type="text" name="cardNumber" id="cardNumber" class="form-control" maxlength="16"/>
                </div>

                <div class="mb-3">
                    <label for="cvv" class="form-label">CVV:</label>
                    <input type="text" name="cvv" id="cvv" class="form-control" maxlength="3"/>
                </div>
            </div>

            <div class="mb-3">
                <label for="amount" class="form-label">Amount:</label>
                <input type="number" step="0.01" name="amount" id="amount" class="form-control" required/>
            </div>

            <button type="submit" class="btn btn-primary w-100">Submit Payment</button>
        </form>
    </div>
</div>

<script>
    function toggleCardFields(method) {
        const creditFields = document.getElementById("creditFields");
        creditFields.style.display = method === "credit" ? "block" : "none";
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
