package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Pais;
import service.PaisService;

/**
 * Servlet implementation class ManterClienteController
 */
@WebServlet("/ManterPais.do")
public class ManterPaisController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pAcao = request.getParameter("acao");
		String pId = request.getParameter("id");
		String pNome = request.getParameter("nome");
		String pPopulacao = request.getParameter("populacao");
		String pArea = request.getParameter("area");
		int id = -1;
		try {
			id = Integer.parseInt(pId);
		} catch (NumberFormatException e) {

		}
		long populacao = 0;
		
		try {
			populacao = Long.parseLong(pPopulacao);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		double area = 0;
		
		try {
			
			area = Double.parseDouble(pArea);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		Pais pais = new Pais();
		pais.setId(id);
		pais.setNome(pNome);
		pais.setPopulacao(populacao);
		pais.setArea(area);
		PaisService cs = new PaisService();
		RequestDispatcher view = null;
		HttpSession session = request.getSession();

		if ("Criar".equals(pAcao)) {
			cs.criar(pais);
			ArrayList<Pais> lista = new ArrayList<>();
			lista.add(pais);
			session.setAttribute("lista", lista);
			view = request.getRequestDispatcher("ListarPais.jsp");
		} else if (("Excluir").equals(pAcao)) {
			cs.excluir(pais.getId());
			ArrayList<Pais> lista = (ArrayList<Pais>) session.getAttribute("lista");
			lista.remove(busca(pais, lista));
			session.setAttribute("lista", lista);
			view = request.getRequestDispatcher("ListarPais.jsp");
		} else if ("Alterar".equals(pAcao)) {
			cs.atualizar(pais);
			ArrayList<Pais> lista = (ArrayList<Pais>) session.getAttribute("lista");
			int pos = busca(pais, lista);
			lista.remove(pos);
			lista.add(pos, pais);
			session.setAttribute("lista", lista);
			request.setAttribute("pais", pais);
			view = request.getRequestDispatcher("VisualizarPais.jsp");
		} else if ("Visualizar".equals(pAcao)) {
			pais = cs.carregar(pais.getId());
			request.setAttribute("pais", pais);
			view = request.getRequestDispatcher("VisualizarPais.jsp");
		} else if ("Editar".equals(pAcao)) {
			pais = cs.carregar(pais.getId());
			request.setAttribute("pais", pais);
			view = request.getRequestDispatcher("AlterarPais.jsp");
		}

		view.forward(request, response);

	}

	public int busca(Pais pais, ArrayList<Pais> lista) {
		Pais to;
		for (int i = 0; i < lista.size(); i++) {
			to = lista.get(i);
			if (to.getId() == pais.getId()) {
				return i;
			}
		}
		return -1;
	}

}
