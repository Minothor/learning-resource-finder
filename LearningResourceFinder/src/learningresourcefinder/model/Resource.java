package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="resource")
public class Resource extends BaseEntity
{
    @Column(length = 50)
    @Size(max=50, message="le num d'une ressource ne peut contenir que 50 caractères maximum")
	private String name;
	
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;
	
	@OneToMany(mappedBy="resource")
	private Set<Problem> problems = new HashSet<>();

	@ManyToMany(mappedBy="resources")
	List<ProgramPoint> programPoints = new ArrayList<>();
	
	@ManyToOne
	@Column(nullable=false)
	private User user;
	
	public Resource(String aName, String aDescription) {
		this.name = aName;
		this.description = aDescription;
	}
	
	@Override
	public String toString() {
		return name;
	}


	/**************************** Getters & Setters *************************************/
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Problem> getProblems() {
		return problems;
	}

	public List<ProgramPoint> getProgramPoints() {
		return programPoints;
	}
	
	
}
