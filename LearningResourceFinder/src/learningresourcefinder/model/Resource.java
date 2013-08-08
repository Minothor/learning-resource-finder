package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import learningresourcefinder.search.Searchable;

import org.hibernate.annotations.Type;

@Entity
@Table(name="resource")
public class Resource extends BaseEntity implements Searchable
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
	
	public Resource() {} // No arg constructor for Hibernate
	
	public Resource(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**************************** Getters & Setters ************************************/
	
	
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

	public Set<Problem> getProblems() {
		return problems;
	}

	public List<ProgramPoint> getProgramPoints() {
		return programPoints;
	}

    @Override
    public Map<String, String> getCriterias() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getBoostedCriteriaName() {
        // TODO Auto-generated method stub
        return null;
    }
}
