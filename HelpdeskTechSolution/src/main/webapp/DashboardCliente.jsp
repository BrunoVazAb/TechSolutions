<!DOCTYPE html>
<%@ page import="jakarta.servlet.http.*,Model.clienteModel"%>
<%
	clienteModel cliente = (clienteModel) session.getAttribute("usuarioLogado");
	if (cliente == null) {
	    response.sendRedirect("loginCliente.jsp");
	    return;
	}
	
%>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Dashboard do Cliente</title>
</head>
<body>
    <h2>Bem-vindo, ${usuarioLogado.nome}!</h2>
	
		<p><a href="registrarChamado.html"><input type="button" value="Cadastrar chamado"></a></p>
		<p><a href="ChamadosController?acao=verTickets"><input type="button" value="Meus Tickets"></a></p>

</body>
</html>