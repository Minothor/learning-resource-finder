package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;



@Entity
public class Cycle extends BaseEntity {

    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
	@Column(length = 40)
	@Size(max=40, message="le nom d'un point de programme ne peut contenir que 40 caractères maximum")
	private String name;

	public Cycle() {} // No arg constructor for Hibernate

	public Cycle(String aName) {
		this.name = aName;
	}

	
	@Override
	public String toString()  {
		return this.getId() + "-" + this.getName();
	}
	
	////////////////////:///////////Getters & Setters //////////////////////////////////////////
		
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

    @Override
    public Long getId() {
        return id;
    }
}
