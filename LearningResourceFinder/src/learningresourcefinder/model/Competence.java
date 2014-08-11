package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import learningresourcefinder.search.Searchable;
import learningresourcefinder.util.HTMLUtil;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;



@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Competence extends BaseEntity implements Searchable{

    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
	@Column(length = 255)
	@Size(max=255, message="l'intitulé d'une compétence ne peut contenir que 255 caractères maximum")
	private String name;

	@Column(length = 10, unique = true, nullable=false)
	@Size(max=10, message="le code d'une compétence ne peut contenir que 8 caractères maximum")
	private String code;   // Short identifier that users can use to quickly refer a Competence
	
	
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;


	@ManyToOne
	Competence parent;
	
	@OneToMany (mappedBy="parent")
	@OrderBy("code")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	List<Competence> children = new ArrayList <Competence>();

	@ManyToOne
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	Cycle cycle;   // Could be null (typical of non leaf cycles)
	
	
	String vraisForumPage;  // We remember it when we created the competence automatically from that site: http://findecycle.vraiforum.com/index.php
	                        // Maybe some day we'll be happy to have it.  John 2014-01
	
	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

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
			this.code = HTMLUtil.removeHtmlTags(codeCompetence);
			this.name = HTMLUtil.removeHtmlTags(nameCompetence);
			this.parent = parent;
			this.description = HTMLUtil.removeHtmlTags(descriptionCompetence);
			
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
	

	public void addChild(Competence child) {
		this.children.add(child);
		child.setParent(this);
	}

	@Override
	public String toString()  {
		return this.getId() + "-" + this.getCode() + "-" + this.getName();
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
		this.name = HTMLUtil.removeHtmlTags(name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = HTMLUtil.removeHtmlTags(description);
	}

	
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = HTMLUtil.removeHtmlTags(code);
	}

	
	
	public String getVraisForumPage() {
        return vraisForumPage;
    }

    public void setVraisForumPage(String vraisForumPage) {
        this.vraisForumPage = vraisForumPage;
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

	public String getFullName() {
		return getCode() + " - " + getName();
	}

	public boolean isOrIsChildOrSubChildOf(Competence competence) {
		// Est-ce que this est un (petit) enfant de competence ?
		Competence myParent = this; 
		while (myParent != null){
			if (myParent.equals(competence)) { // Found
				return true;
			}
			myParent = myParent.getParent();
		}
		return false;
	}

	/// Sets the bidirectional relationship in on call
	public void bindWithParent(Competence newParent) {
		this.parent = newParent;
		newParent.getChildren().add(this);
	}
}
