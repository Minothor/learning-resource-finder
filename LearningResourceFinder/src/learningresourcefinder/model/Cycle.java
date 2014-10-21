package learningresourcefinder.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import learningresourcefinder.util.HTMLUtil;
import learningresourcefinder.web.Slugify;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cycle extends BaseEntity  {

    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @NotBlank(message="Entrez le nom du cycle")
	@Column(length = 15)
	@Size(max=15, message="le nom d'un cycle ne peut contenir que 15 caractères maximum")
	private String name;
    
    @NotBlank(message="Entrez la description du cycle")
    @Column(length = 50)
    @Size(max=50, message="la description d'un cycle ne peut contenir que 50 caractères maximum")
    private String description;
    
    @Column(length=50, nullable=false)
    private String slug;

	public Cycle() {} // No arg constructor for Hibernate

    public Cycle(String name, String description) {
        this.name = name;
        this.description = HTMLUtil.removeHtmlTags(description);
        this.slug = Slugify.slugify(description);
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
		this.name = HTMLUtil.removeHtmlTags(name);
	}

    @Override
    public Long getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = HTMLUtil.removeHtmlTags(slug);
    }
}
