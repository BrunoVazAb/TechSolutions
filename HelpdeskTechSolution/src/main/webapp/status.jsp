<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Status do cadastro</title>
		<link rel="stylesheet" href="./css/css.css">
		<link rel="shortcut icon" type="image/x-icon" href="./icons/task.png">
	</head>
	<body>
		<div id="div1">
			<div id="div2">
				<h3 id="titulo">Status da operação</h3>
		
		
		<% boolean status = (boolean) request.getAttribute("status"); 
			String operacao = (String) request.getAttribute("operacao");
			
			String msg;
		
			if(status)
				msg = "<span style='color: green'>A pessoa foi " + operacao + " com sucesso!</span>";
			else
				msg = "<span style='color: red'>ERRO! - A pessoa não foi " + operacao + " .</span>";
		%>
	
		<p><i> <%= msg %> </i></p>

				<p><a href="./DashboardCliente.jsp" class="link">Voltar para o menu principal</a></p>
			</div>
		</div>
	</body>
</html>