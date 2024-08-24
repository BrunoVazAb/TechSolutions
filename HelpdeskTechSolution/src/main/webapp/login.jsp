<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Administrador</title>
</head>
<body>
    <h2>Login do Administrador</h2>
    <form action="login" method="post">
        <label for="email">Email:</label>
        <input type="email" name="email" required>
        <br>
        <label for="senha">Senha:</label>
        <input type="password" name="senha" required>
        <br>
        <input type="submit" value="Login">
    </form>

    <%
        if(request.getParameter("error") != null) {
            out.println("<p style='color:red;'>Email ou senha inválidos!</p>");
        }
    %>
</body>
</html>