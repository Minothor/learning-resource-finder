package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

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
	
	@ManyToMany
	List <Resource> resource  = new ArrayList<>();

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

	
	
	
}
