package learningresourcefinder.model;

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
}
