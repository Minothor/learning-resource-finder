package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;



@Entity
public class ProgramPoint extends BaseEntity {

    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
	@Column(length = 40)
	@Size(max=40, message="le nom d'un point de programme ne peut contenir que 40 caractères maximum")
	private String name;

	@Column(length = 8, unique = true, nullable=false)
	@Size(max=8, message="le code d'un point de programme ne peut contenir que 50 caractères maximum")
	private String code;   // Short identifier that users can use to quickly refer a ProgramPoint
	
	
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;

	@ManyToMany
	List <Resource> resources  = new ArrayList<>();

	@ManyToOne
	ProgramPoint parent;
	
	@OneToMany (mappedBy="parent")
	List<ProgramPoint> children = new ArrayList <ProgramPoint>();

	
	public ProgramPoint() {} // No arg constructor for Hibernate

	public ProgramPoint(String aCode, String aName) {
		this.code = aCode;
		this.name = aName;
	}

	//recursion for article's childen and grandchildren
	public List<ProgramPoint> getChildrenAndSubChildren() {
		List <ProgramPoint> result = new ArrayList <ProgramPoint> ();
		for (ProgramPoint child : this.getChildren()) {
			child.getChildrenRecursively(result);
		}
		return result;
	}

	private void getChildrenRecursively(List<ProgramPoint> result) {
		result.add(this);
		for (ProgramPoint child : this.getChildren()){
			child.getChildrenRecursively(result);
		}
	}

	public void addResource(Resource resource) {
		this.getResources().add(resource);
		resource.getProgramPoints().add(this);
	}

	public void addChild(ProgramPoint child) {
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

	public ProgramPoint getParent() {
		return parent;
	}

	public void setParent(ProgramPoint parent) {
		this.parent = parent;
	}

	public List<ProgramPoint> getChildren() {
		return children;
	}

	public void setChildren(List<ProgramPoint> children) {
		this.children = children;
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
	
    @Override
    public Long getId() {
        return id;
    }
}
