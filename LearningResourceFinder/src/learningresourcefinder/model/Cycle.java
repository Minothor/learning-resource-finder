package learningresourcefinder.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;



@Entity
public class Cycle extends BaseEntity  {

    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @NotBlank(message="Entrez le nom du cycle")
	@Column(length = 15)
	@Size(max=15, message="le nom d'un cycle ne peut contenir que 15 caractères maximum")
	private String name;

	@OneToMany (mappedBy="cycle")
	@OrderBy("code")
	private List<Competence> competences;
	
	
	
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
