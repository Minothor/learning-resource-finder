package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Program extends BaseEntity {
	
	@Column(columnDefinition = "VARCHAR(50)")
	private String name;
	@Column(columnDefinition = "VARCHAR(50)")
	private String description;
	@Column(columnDefinition = "INTEGER(5)")
	private int duration;    // duration of the program in days
	@Column(columnDefinition = "VARCHAR(50)")
	private int level;
	
	@OneToMany
	List <Resource> resource  = new ArrayList<>();

	/*Program parent;
	
	List <Program> childs = new ArrayList<>();
	

	public void addChild(Program child){
		child.parent=this;
		this.getChilds().add(child);
		
	}
	
	public void walkTree (Program program){
		
		for( Program child : program.childs){
			walkTree(child);
		}
	}*/
	
	///////////Getters & Setters //////////////
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	
	
	public List<Resource> getResource() {
		return resource;
	}

	

	/*public Program getParent() {
		return parent;
	}

	

	public List<Program> getChilds() {
		return childs;
	}*/

	

	////////// Methods //////////
	public void addResource(Resource res){
		resource.add(res);
		
	}
	
	public void removeResource(Resource res){
		resource.remove(res);
	}
	
	
}
