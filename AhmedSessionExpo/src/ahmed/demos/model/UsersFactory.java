package ahmed.demos.model;

public class UsersFactory {

	private static final UsersFactory INSTANCE = new UsersFactory();

	public static UsersFactory getInstance() {

		return INSTANCE;
	}

	public Users getNewUser() {

		return new Users();
	}

	public Users getNewUser(long id ,String name, String email, String password) {

		return new Users(id,name, email, password);

	}

}
