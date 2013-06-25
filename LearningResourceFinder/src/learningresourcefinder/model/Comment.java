package learningresourcefinder.model;

import javax.persistence.*;

@Entity
public class Comment extends BaseEntity
{
	@Column(columnDefinition="VARCHAR(250)", nullable=false)
	private String title;
	
	@Column(columnDefinition="TEXT", nullable=false)
	private String description;
	
	/**************************** Getters *************************************/

	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	/**************************** Setters *************************************/

	

	public void setDescription(String description) {
		this.description = description;
	}
}
