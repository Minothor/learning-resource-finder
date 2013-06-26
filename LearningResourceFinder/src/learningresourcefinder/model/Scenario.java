package learningresourcefinder.model;

import javax.persistence.Entity;

import javax.persistence.ManyToOne;

@Entity
public class Scenario extends BaseEntity {
	
	private String name;
	
	@ManyToOne
	private User user;
	
	///////// Getters & Setters //////////
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
