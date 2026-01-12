<html>
<body>
<h2>Login</h2>
<br>
<form method="post" action="/login/auth">
    <input type="number" name="user-id" required><br>
    <select name="role" required>
        <option value="customer" >Customer</option>
        <option value="driver">Driver</option>
    </select><br>
    <button type="submit">Login</button>
</form>
</body>
</html>