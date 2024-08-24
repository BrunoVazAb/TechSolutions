<%@ page import="jakarta.servlet.http.*,Model.administradorModel"%>
<%
	administradorModel admin = (administradorModel) session.getAttribute("admin");
	if (admin == null) {
	    response.sendRedirect("login.jsp");
	    return;
	}
	
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Administrador</title>
</head>
<body>
    <h2>Bem-vindo, <%= admin.getNome() %>!</h2>
    <p>Dashboard de administrador.</p>
</body>
</html>