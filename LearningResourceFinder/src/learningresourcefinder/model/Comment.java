package learningresourcefinder.model;

import javax.persistence.*;

import org.apache.commons.lang3.StringEscapeUtils;

import learningresourcefinder.util.HTMLUtil;

@Entity
public class Comment extends BaseEntity
{
    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
	@Column(columnDefinition="VARCHAR(250)", nullable=false)
	private String title;
	
	@Column(columnDefinition="TEXT", nullable=false)
	private String description;
	
	@ManyToOne
	private Problem problem;
	
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
	
	/**************************** Setters *************************************/

	public void setTitle(String title) {
		this.title = HTMLUtil.removeHtmlTags(title);
	}

	public void setDescription(String description) {
		this.description = HTMLUtil.removeHtmlTags(description);
	}
	
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
    @Override
    public Long getId() {
        return id;
    }
}
