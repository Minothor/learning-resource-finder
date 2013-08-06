package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Scenario extends BaseEntity {

	@Column(length=50)
	private String name;
	

	@ManyToOne
	private User author;
	

	///////// Getters & Setters //////////
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
