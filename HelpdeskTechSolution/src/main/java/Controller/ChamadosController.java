package Controller;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import javax.sql.DataSource;

import Model.ChamadoDAO;
import Model.chamadosModel;
import Model.clienteModel;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ChamadosController")
public class ChamadosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ChamadoDAO cDAO;
	
	@Resource(name = "bancoTechsolutions")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		cDAO = new ChamadoDAO(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao").toLowerCase();

        switch (acao) {

        case "vertickets":
        	listarChamadosCliente(request, response);
            break;

		default:
			System.out.println("Erro! - Operacao nao encontrada");
        }
    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao").toLowerCase();

        switch (acao) {

		case "registrar":
			cadastrarChamado(request, response);
		break;
		
		default:
			System.out.println("Erro! - Operawcao nao encontrada");
        }
    }

	private void cadastrarChamado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		clienteModel cliente = (clienteModel) session.getAttribute("usuarioLogado");
		
		int usuarioId = cliente.getId();
        String prioridade = request.getParameter("prioridade");
        String descricao = request.getParameter("descricao");
        String status = "aberto";
        Date dataAbertura = new Date();
		

		boolean inserido = cDAO.registrarChamado(usuarioId, prioridade, status, descricao, dataAbertura);

		request.setAttribute("status", inserido);
		request.setAttribute("operacao", "cadastrada");
				
		request.getRequestDispatcher("/status.jsp").forward(request, response);
	}
	 
	 public void listarChamadosCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
		 clienteModel cliente = (clienteModel) session.getAttribute("usuarioLogado");
		 ArrayList<chamadosModel> listaChamadosCliente = cDAO.listaChamadosCliente(cliente.getId());
		 
		 request.setAttribute("listaChamadosCliente", listaChamadosCliente);
			
		 RequestDispatcher dispatcher = request.getRequestDispatcher("/exibeChamadosCliente.jsp");
			
		 dispatcher.forward(request, response);
	    
	    }
	
}
