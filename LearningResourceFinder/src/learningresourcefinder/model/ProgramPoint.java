package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class ProgramPoint extends BaseEntity {
	
	
	private String name;
	
	private String description;
	
	private int duration;    // duration of the program in days
	
	private int level;
	
	@ManyToMany
	List <Resource> resources  = new ArrayList<>();

	/*ProgramPoint parent;
	
	List <ProgramPoint> childs = new ArrayList<>();
	

	public void addChild(ProgramPoint child){
		child.parent=this;
		this.getChilds().add(child);
		
	}*/
	
	/*
	 * We list all the resources in the given programpoint or any of its childs (or sub-childs...)
	 *    .... (ProgramPoint startProgramPoint)
	 *  List<ProgramPoint> programPoints = myLittleRecursiveMethod(startProgramPoint)
	////////   select r from Resource r where r.programPoint in (:programPoints) 
	   .addParameterList("programPoints", programPoints)
	   ...
	   */
	
	/*public void walkTree (Program program){
		
		for( Program child : program.childs){
			walkTree(child);
		}
	}*/
	
	@ManyToOne
	ProgramPoint parent;
	
	@OneToMany (mappedBy="parent")
	List<ProgramPoint> children = new ArrayList <ProgramPoint>();
	
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
	
	///////////Getters & Setters //////////////
		
		
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

	////////// Methods //////////
	public void addResource(Resource res){
		resources.add(res);
		
	}
	
	public void removeResource(Resource res){
		resources.remove(res);
	}


	
	
	
}
