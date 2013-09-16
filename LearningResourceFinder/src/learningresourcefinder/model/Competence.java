package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.persistence.OrderBy;

import learningresourcefinder.search.Searchable;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;



@Entity
public class Competence extends BaseEntity implements Searchable{

    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
	@Column(length = 40)
	@Size(max=40, message="le nom d'une compétence ne peut contenir que 40 caractères maximum")
	private String name;

	@Column(length = 8, unique = true, nullable=false)
	@Size(max=8, message="le code d'une compétence ne peut contenir que 50 caractères maximum")
	private String code;   // Short identifier that users can use to quickly refer a Competence
	
	
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;

	@ManyToMany
	List <Resource> resources  = new ArrayList<>();

	@ManyToOne
	Competence parent;
	
	@OneToMany (mappedBy="parent")
	@OrderBy("code")
	List<Competence> children = new ArrayList <Competence>();

	@ManyToOne
	Cycle cycle;   // Could be null (typical of non leaf cycles)
	
	public Competence() {} // No arg constructor for Hibernate

	public Competence(String aCode, String aName) {
		this.code = aCode;
		this.name = aName;
	}
	
	public Competence(String aCode, String aName,Competence parent) {
		this.code = aCode;
		this.name = aName;
		this.parent = parent;
	}

	public Competence(String codeCompetence, String nameCompetence,
			Competence parent, String descriptionCompetence) {
			this.code = codeCompetence;
			this.name = nameCompetence;
			this.parent = parent;
			this.description = descriptionCompetence;
			
	}

	//recursion for competence's childen and grandchildren
	public List<Competence> getChildrenAndSubChildren() {
		List <Competence> result = new ArrayList <Competence> ();
		for (Competence child : this.getChildren()) {
			child.getChildrenRecursively(result);
		}
		return result;
	}

	private void getChildrenRecursively(List<Competence> result) {
		result.add(this);
		for (Competence child : this.getChildren()){
			child.getChildrenRecursively(result);
		}
	}

	public void addResource(Resource resource) {
		this.getResources().add(resource);
		resource.getProgramPoints().add(this);
	}

	public void addChild(Competence child) {
		this.children.add(child);
		child.setParent(this);
	}

	@Override
	public String toString()  {
		return this.getId() + "-" + this.getName();
	}
	
	
	
	////////////////////:///////////Getters & Setters //////////////////////////////////////////
		
		
	public String getName() {
		return name;
	}

	public Competence getParent() {
		return parent;
	}

	public void setParent(Competence parent) {
		this.parent = parent;
	}

	public List<Competence> getChildren() {
		return children;
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

	public List<Resource> getResources() {
		return resources;
	}
	
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
    public Long getId() {
        return id;
    }

	@Override
	public Map<String, String> getCriterias() {
        Map<String,String> criterias = new HashMap<String,String>();
        criterias.put("name",StringUtils.defaultIfEmpty(name,""));
        criterias.put("description",StringUtils.defaultIfEmpty(description,""));
        return criterias;
	}

	@Override
	public String getBoostedCriteriaName() {
		return "name";
	}
}
