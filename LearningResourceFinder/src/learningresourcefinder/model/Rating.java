package learningresourcefinder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Rating extends BaseEntity{
	 @Id   
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 Long id;
	 
	 private Double score;
		 
     @OneToOne
	 @JoinColumn	
	 private  Resource resource;
    
     @OneToOne
	 @JoinColumn	
	 private User user;
	
	 public Long getId() {
	   return id;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Rating() {
		super();
		
	}

	public Rating(Double score, Resource resource, User user) {
		super();
		this.score = score;
		this.resource = resource;
		this.user = user;
	}

	

}
