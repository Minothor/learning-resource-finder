package be.demos.fb;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.WebRequestor;
import com.restfb.types.User;

public class SecondServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String APP_ID = "350985091585275";
	private final String SECRET_KEY = "3de40a5fe635e975a7d0d8e10719daa4";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		FacebookClient.AccessToken token = getFacebookUserToken(req.getParameter("code"), "http://localhost:8080/ConnectingProject/Profile");
		
		String accessToken = token.getAccessToken();

		FacebookClient fclient = new DefaultFacebookClient(accessToken);
		
		User u = fclient.fetchObject("me",User.class);
		
		System.out.println(u.getId() + " --> " + u.getEmail());

		this.getServletContext().getRequestDispatcher("/Second.jsp").forward(req, resp);
		
	}
	
	private FacebookClient.AccessToken getFacebookUserToken(String code, String redirectUrl) throws IOException {
	    String appId = APP_ID;
	    String secretKey = SECRET_KEY;

	    WebRequestor wr = new DefaultWebRequestor();
	    WebRequestor.Response accessTokenResponse = wr.executeGet(
	            "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectUrl
	            + "&client_secret=" + secretKey + "&code=" + code);
	    
	    System.out.println(accessTokenResponse.getBody());
	    
	    return  DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody()); 
	}
	
}
