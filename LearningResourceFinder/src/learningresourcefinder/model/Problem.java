package learningresourcefinder.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Problem extends BaseEntity
{
	@Column(columnDefinition="VARCHAR(250)", nullable=false)
	private String title;
	
	@Column(columnDefinition="TEXT", nullable=false)
	private String description;
	
	@ManyToOne
	private Resource resource;
	
	@OneToMany(mappedBy="problem")
	private Set<Comment> comments;
	
	public Problem() {
		this.comments = new HashSet<>();
	}
	
	/**************************** Getters *************************************/

	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Resource getResource() {
		return this.resource;
	}

	/**************************** Setters *************************************/

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
/**************************** Methods *************************************/
	
	public void addComment(Comment c) {
		this.comments.add(c);
	}
	
	public void removeComment(Comment c) {
		this.comments.remove(c);
	}
}
