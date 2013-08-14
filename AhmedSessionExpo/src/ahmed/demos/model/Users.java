package ahmed.demos.model;

public final class Users {

	private long id ;
	private String name;
	private String email;
	private String password;

	protected Users() {/* default constructor */
	}

	protected Users(long id,String name, String email, String password) {

		this.name = name;
		this.email = email;
		this.password = password;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

}
