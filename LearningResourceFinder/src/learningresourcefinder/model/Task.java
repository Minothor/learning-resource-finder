package learningresourcefinder.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Task extends BaseEntity
{
	private String name;
	
	@ManyToOne
	private User assigner;
	
	@ManyToOne
	private User student;
	
	@ManyToMany
	private Resource resource;
	
	public String getName() {
		return this.name;
	}
	
	public User getAssigner() {
		return this.assigner;
	}
	
	public User getUser() {
		return this.student;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAssigner(User assigner) {
		this.assigner = assigner;
	}

	public void setUser(User student) {
		this.student = student;
	}
	
	
}
