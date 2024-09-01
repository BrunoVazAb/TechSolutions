package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.sql.DataSource;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PessoaDAO;
import model.pessoaModel;

@WebServlet("/pessoaController")
public class pessoaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PessoaDAO pDAO;

	@Resource(name = "bancoTarefas")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		pDAO = new PessoaDAO(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operacao = request.getParameter("operacao").toLowerCase();

		switch (operacao) {
		case "listar":
			listarPessoas(request, response);
		break;
		case "remover":
			excluirPessoa(request, response);
			break;
		case "buscar":
			buscarPessoa(request, response);
		break;
		case "metodos":
			buscaPessoaMetodo(request, response);
		break;
		
		
		default:
			System.out.println("ERRO! - Operacao nao encontrada");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operacao = request.getParameter("operacao").toLowerCase();

		switch (operacao) {
			case "cadastrar":
				cadastrarPessoa(request, response);
			break;
			
			case "atualizar":
				atualizarPessoa(request, response);
			break;

			case "calcular":
				calcularPessoa(request, response);
			break;
			
			
			default:
				System.out.println("ERRO! - Operacao nao encontrada");
		}
	}
		

	private void cadastrarPessoa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double deducao = Double.parseDouble(request.getParameter("Deducao"));
		double renda = Double.parseDouble(request.getParameter("RendaBruta"));
		int dependentes = Integer.parseInt(request.getParameter("Dependentes"));
		int idade = Integer.parseInt(request.getParameter("Idade"));
		double IRanterior = Double.parseDouble(request.getParameter("IRanterior"));
		String cpf = request.getParameter("CPF");
		String nome = request.getParameter("nome");

		boolean inserido = pDAO.inserirPessoa(cpf, nome, idade, renda, dependentes, deducao, IRanterior);

		request.setAttribute("status", inserido);
		request.setAttribute("operacao", "cadastrada");
			
		request.getRequestDispatcher("/status.jsp").forward(request, response);
	}

	private void listarPessoas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<pessoaModel> listaPessoa = pDAO.consultarPessoas();
		
		request.setAttribute("pessoasBanco", listaPessoa);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/exibePessoas.jsp");
		
		dispatcher.forward(request, response);
	}
	
	private void excluirPessoa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpf = request.getParameter("cpf");

		boolean excluiu = pDAO.excluirPessoa(cpf);
		
		request.setAttribute("status", excluiu);
		request.setAttribute("operacao", "removida");
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/status.jsp");
		dispatcher.forward(request, response);
	}
	
	private void buscarPessoa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpf = request.getParameter("cpf");
		
		pessoaModel pessoa= pDAO.procurarPessoa(cpf);
		
		request.setAttribute("pessoa", pessoa);
		request.getRequestDispatcher("/Atualizar.jsp").forward(request, response);
	}
	
	private void atualizarPessoa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double deducao = Double.parseDouble(request.getParameter("Deducao"));
		double renda = Double.parseDouble(request.getParameter("RendaBruta"));
		int dependentes = Integer.parseInt(request.getParameter("Dependentes"));
		int idade = Integer.parseInt(request.getParameter("Idade"));
		double IRanterior = Double.parseDouble(request.getParameter("IRanterior"));
		String cpf = request.getParameter("cpf");
		String nome = request.getParameter("nome");
		
		boolean atualizou = pDAO.modificarPessoa(cpf, nome, idade, renda, dependentes, deducao, IRanterior);
		
		request.setAttribute("status", atualizou);
		request.setAttribute("operacao", "atualizada");
		
		request.getRequestDispatcher("/status.jsp").forward(request, response);
	}
	
	private void buscaPessoaMetodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpf = request.getParameter("cpf");
		
		pessoaModel pessoa= pDAO.procurarPessoa(cpf);
		
		request.setAttribute("pessoaMetodos", pessoa);
		request.getRequestDispatcher("/selecMetodo.jsp").forward(request, response);
		
	}
	private void calcularPessoa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double deducao = Double.parseDouble(request.getParameter("Deducao"));
		double renda = Double.parseDouble(request.getParameter("RendaBruta"));
		int dependentes = Integer.parseInt(request.getParameter("Dependentes"));
		int idade = Integer.parseInt(request.getParameter("Idade"));
		double IRanterior = Double.parseDouble(request.getParameter("IRanterior"));
		String cpf = request.getParameter("cpf");
		String nome = request.getParameter("nome");
		String metodo = request.getParameter("metodo");

		pessoaModel pessoa = new pessoaModel(deducao, renda, dependentes, idade, IRanterior, cpf, nome);
		
		switch (metodo) {
			case "verfIsencao":
				String isento = pessoa.calcIsencao();
				request.setAttribute("isento",isento);
				break;
	
			case "calcImposto":
				double imposto = pessoa.calcImpostoRenda( );
				request.setAttribute("imposto",imposto);
				break;
				
			case "calcRestituicao":
				double restituicao = pessoa.calcRestituicao();
				request.setAttribute("restituicao",restituicao);
				break;
				
			default:
				String resultN="Nenhuma operação selecionada";
				request.setAttribute("resultN",resultN);
				break;
			}

		request.setAttribute("metodo", metodo);
		request.setAttribute("pessoa", pessoa);

		
		request.getRequestDispatcher("/resultado.jsp").forward(request, response);
	}
	

	

}
