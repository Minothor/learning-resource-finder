package learningresourcefinder.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import learningresourcefinder.util.HTMLUtil;

import org.hibernate.annotations.Type;

@Entity
public class Problem extends BaseEntity
{
    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
	@Column(length = 50)
	@Size(max=40, message="le nom d'un problème ne peut contenir que 50 caractères maximum")
	private String name;

	
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;

	
	@ManyToOne
	private Resource resource;
	
	@OneToMany(mappedBy="problem",cascade=CascadeType.REMOVE)
	private Set<Comment> comments;
	
    @OneToMany(mappedBy="problem")
    private List<Discussion> problemDiscussions;

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
	
	public User getSolver() {
		return this.solver;
	}
	
	public boolean getResolved() {
		return this.resolved;
	}
	
	public List<Discussion> getProblemDiscussions() {
        return problemDiscussions;
    }

    /**************************** Setters *************************************/

	public void setName(String name) {
		this.name = HTMLUtil.removeHtmlTags(name);
	}

	public void setDescription(String description) {
		this.description = HTMLUtil.removeHtmlTags(description);
	}
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	public void setSolver(User user) {
		this.solver = user;
	}
	
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}
	
    public void setProblemDiscussions(List<Discussion> problemDiscussions) {
        this.problemDiscussions = problemDiscussions;
    }
		
	/**************************** Methods *************************************/
	
	public void addComment(Comment c) {
		this.comments.add(c);
	}
	
	public void removeComment(Comment c) {
		this.comments.remove(c);
	}
	
   public void addDiscussion(Discussion d) {
        this.problemDiscussions.add(d);
    }
    
    public void removeDiscussion(Discussion d) {
        this.problemDiscussions.remove(d);
    }
	    
    @Override
    public Long getId() {
        return id;
    }
}
