package learningresourcefinder.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

@Entity
public class Problem extends BaseEntity
{
	@Column(length = 50)
	@Size(max=40, message="le nom d'un problème ne peut contenir que 50 caractères maximum")
	private String name;

	
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;

	
	@ManyToOne
	private Resource resource;
	
	@OneToMany(mappedBy="problem")
	private Set<Comment> comments;
	
	@ManyToOne
	private User author;
	
	@ManyToOne
	private User solver;
	
	private boolean resolved = false;
	
	public Problem() {
		this.comments = new HashSet<>();
	}
	
	public String toString() {
		return this.name; 
	}
	
	/**************************** Getters *************************************/

	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Resource getResource() {
		return this.resource;
	}
	
	public User getAuthor() {
		return this.author;
	}
	
	public User getSolver() {
		return this.solver;
	}
	
	public boolean getResolved() {
		return this.resolved;
	}

	/**************************** Setters *************************************/

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	public void setAuthor(User user) {
		this.author = user;
	}
	
	public void setSolver(User user) {
		this.solver = user;
	}
	
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}
		
	/**************************** Methods *************************************/
	
	public void addComment(Comment c) {
		this.comments.add(c);
	}
	
	public void removeComment(Comment c) {
		this.comments.remove(c);
	}
}
