package Controller;

import java.io.IOException;

import javax.sql.DataSource;
import Model.ClienteDAO;
import Model.clienteModel;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ClientesController")
public class ClientesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ClienteDAO cDAO;
	
	@Resource(name = "bancoTechsolutions")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		cDAO = new ClienteDAO(dataSource);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao").toLowerCase();

        switch (acao) {
        case "login":
            loginUsuario(request, response);
            break;
		
		default:
			System.out.println("Erro! - Operacao nao encontrada");
        }
    }
	
	 private void loginUsuario(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
			String email = request.getParameter("email");
	        String senha = request.getParameter("senha");
	    	Boolean login;

	        clienteModel cliente = cDAO.autenticarUsuario(email, senha);

	        if (cliente != null) {
	            HttpSession session = request.getSession();
	            session.setAttribute("usuarioLogado",cliente );

	            if ("cliente".equals(cliente.getTipo())) {
		        	login = true;

		            request.getRequestDispatcher("/DashboardCliente.jsp").forward(request, response);
		            
	            } else if ("tecnico".equals(cliente.getTipo())) {
		        	login = true;
		            request.getRequestDispatcher("/DashboardTecnico.jsp").forward(request, response);
	            }
	        } else {
	        	login= false;
				request.setAttribute("status", login);
	            request.getRequestDispatcher("/loginUsuarios.jsp").forward(request, response);
	          }		
	}
}
