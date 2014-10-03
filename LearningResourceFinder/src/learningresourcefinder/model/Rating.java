package learningresourcefinder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Rating extends BaseEntity{
	 @Id   
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 Long id;
	 
	 private Double score;
		 
     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
	 @JoinColumn	
	 private  Resource resource;
    
     @ManyToOne
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
