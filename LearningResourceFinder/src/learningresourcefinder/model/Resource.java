package learningresourcefinder.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="resource")
public class Resource extends BaseEntity
{
	@Column(nullable=false, columnDefinition="VARCHAR(50)")
	private String title;
	
	@Column(columnDefinition="TEXT", nullable=false)
	private String description;
	
	@OneToMany(mappedBy="resource")
	private Set<Problem> problems;
	
	public Resource()
	{
		problems = new HashSet<>();
	}
	
	/**************************** Getters *************************************/
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	/**************************** Setters *************************************/

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**************************** Methods *************************************/
	
	public void addProblem(Problem p) {
		this.problems.add(p);
	}
	
	public void removeProblem(Problem p) {
		this.problems.remove(p);
	}
	
}
