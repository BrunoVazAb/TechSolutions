<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="Model.chamadosModel, java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
    <title>Meus Chamados</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>Meus Chamados</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Prioridade</th>
                <th>Status</th>
                <th>Descrição</th>
                <th>Data de Abertura</th>
            </tr>
        </thead>
        <tbody>
            <% 
                ArrayList<chamadosModel> chamados = (ArrayList<chamadosModel>) request.getAttribute("listaChamadosCliente");
                
                for (chamadosModel c : chamados) {
                	
                	out.println(
	            		"<tr>" + 
		                    "<td>"+ c.getId() + "</td>" +
		                   	"<td>"+ c.getPrioridade() + "</td>" +
		                    "<td>"+ c.getStatus() + "</td>" +
		                    "<td>"+c.getDescricao() + "</td>" +
		                    "<td>"+ c.getData_abertura() + "</td>" +
		                    
	                	"</tr>");
                } 
            %>
        </tbody>
    </table>
    <a href="DashboardCliente.jsp">Voltar para o Painel</a>
</body>
</html>
