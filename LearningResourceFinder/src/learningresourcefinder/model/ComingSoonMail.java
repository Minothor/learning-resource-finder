package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ComingSoonMail extends BaseEntity{

	@Id   @GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(nullable=false)
	private String email;

	@Override
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
