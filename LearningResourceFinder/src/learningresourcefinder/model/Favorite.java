package learningresourcefinder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Favorite extends BaseEntity {

	@Id   
	@GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
	
	@OneToOne
	private Resource resource;

	@OneToOne
	private User user;
	
	@Override
	public Long getId() {
		return null;
	}
	
	public User getUser() {
		return user;
	}
	
	public Resource getResource() {
		return resource;
	}
}
