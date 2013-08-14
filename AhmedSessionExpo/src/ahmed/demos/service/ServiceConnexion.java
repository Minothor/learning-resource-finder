package ahmed.demos.service;


import javax.servlet.http.HttpServletRequest;

import ahmed.demo.reporsitory.DBUsers;
import ahmed.demos.model.Users;


public class ServiceConnexion {
	
	public static final String CHAMP_EMAIL = "email";
	public static final String CHAMP_PASS = "motdepasse";


	private static final ServiceConnexion INSTANCE = new ServiceConnexion();
	
	public static ServiceConnexion getInstance(){
		
		return INSTANCE;
		
	}
	
	public static Users userConnected(HttpServletRequest request){
		
		String email  = getValeur(request,CHAMP_EMAIL);
		String password = getValeur(request,CHAMP_PASS);
		
		return DBUsers.DAOUsers.findUsersByEmailAndPassword(email, password);
	
	}
	
	public static String getValeur(HttpServletRequest request , String value){
		
		String chaine = (String) request.getParameter(value);
		
		if(chaine == null || chaine.trim().length() == 0){
			
			return null;
		}else{
			
			return chaine;
		}
		
	}
	

}
