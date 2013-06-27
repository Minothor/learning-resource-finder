package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Program extends BaseEntity {
	
	
	private String name;
	
	private String description;
	
	private int duration;    // duration of the program in days
	
	private int level;
	
	@ManyToMany
	List <Resource> resources  = new ArrayList<>();

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
		return resources;
	}

	

	/*public Program getParent() {
		return parent;
	}

	

	public List<Program> getChilds() {
		return childs;
	}*/

	

	////////// Methods //////////
	public void addResource(Resource res){
		resources.add(res);
		
	}
	
	public void removeResource(Resource res){
		resources.remove(res);
	}
	
	
}
