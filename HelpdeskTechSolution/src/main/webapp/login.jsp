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
        <input type="email"  id="email" name="email" required><br><br>
        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" required><br><br>
        <input type="submit" value="Login">
        
		<% 
		    Boolean status = (Boolean) request.getAttribute("status"); 
		    String msg = null;
		
		    if (status != null) {
		        if (status == false) {
		        	msg = "<span style='color: red'>ERRO! - Email ou senha incorreto .</span>";
		        }
		    }
		%>
	
		<% if (msg != null) { %>
		    <p><%= msg %></p>
		<% } %>
    </form>
</body>
</html>