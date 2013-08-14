package ahmed.demo.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ahmed.demo.reporsitory.DBUsers;
import ahmed.demos.model.Users;
import ahmed.demos.model.UsersFactory;
import ahmed.demos.service.ServiceConnexion;

public class Connexion extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String SHOW_JSP = "/First.jsp";
	public final static String USER_SESSION = "user";
	public static final String CHAMP_RESULT = "result";
	public String resultat = "";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher(SHOW_JSP)
				.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Users u = ServiceConnexion.userConnected(req);

		HttpSession mySession = req.getSession();
		
		if (u != null) {

			mySession.setAttribute(USER_SESSION, u);
			resultat = "Connexion reussie";

		} else {

			mySession.setAttribute(USER_SESSION, null);
			resultat = "Echec de la connexion";

		}

		req.setAttribute(CHAMP_RESULT, resultat);

		this.getServletContext().getRequestDispatcher(SHOW_JSP)
				.forward(req, resp);

	}

}
