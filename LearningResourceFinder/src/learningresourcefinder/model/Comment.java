package learningresourcefinder.model;

import javax.persistence.*;

@Entity
public class Comment extends BaseEntity
{
	@Column(columnDefinition="VARCHAR(250)", nullable=false)
	private String title;
	
	@Column(columnDefinition="TEXT", nullable=false)
	private String description;
	
	@ManyToOne
	private Problem problem;
	
	@ManyToOne
	private User author;
	
	/**************************** Getters *************************************/

	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}

	public Problem getProblem() {
		return this.problem;
	}
	
	public User getAuthor() {
		return this.author;
	}
	
	/**************************** Setters *************************************/

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	public void setAuthor(User user) {
		this.author = user;
	}
}
