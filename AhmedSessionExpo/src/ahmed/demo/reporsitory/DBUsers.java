package ahmed.demo.reporsitory;

import java.util.ArrayList;
import java.util.List;

import ahmed.demos.model.Users;
import ahmed.demos.model.UsersFactory;

public class DBUsers {

	private static final DBUsers INSTANCE = new DBUsers();
	public static List<Users> listOfUsers = new ArrayList<Users>();
	
	static{
		
		listOfUsers.add(UsersFactory.getInstance().getNewUser(1L, "ahmed", "ahmed.idoumhaidi@gmail.com", "123456"));
		
	}
	

	public static DBUsers getInstance() {

		return INSTANCE;
	}

	// ==============
	// Table Users :
	// ==============


	// ========================
	// Inner Class DAO Users :
	// ========================

	public static class DAOUsers {

		public static Users findUsersById(long id) {
			
			for(Users u : listOfUsers){
				
				if(u.getId() == id)
					return u;
				
			}

			return null;
		}

		public static void addUsers(Users u) {

			listOfUsers.add(u);

		}
		
		public static Users findUsersByEmailAndPassword(String email , String password) {
			
	
			for(Users u : listOfUsers){
				
				if(u.getEmail().equals(email) && u.getPassword().equals(password))
					return u;
				
			}

			return null;
		}
		
		
	public static Users findUsersByEmail(String email) {
			
			for(Users u : listOfUsers){
				
				if(u.getEmail().equals(email) )
					return u;
				
			}

			return null;
		}
	
	public static Users findUsersByPassword(String password) {
		
		for(Users u : listOfUsers){
			
			if(u.getPassword().equals(password))
				return u;
			
		}

		return null;
	}
		

	}

}
