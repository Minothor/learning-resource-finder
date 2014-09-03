package learningresourcefinder.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import learningresourcefinder.service.CompetenceService;
import learningresourcefinder.util.HTMLUtil;
import learningresourcefinder.web.ContextUtil;

import org.apache.commons.lang3.StringEscapeUtils;
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

	@OneToMany (mappedBy="cycle")
	@OrderBy("code")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<Competence> competences;
	
	// This is a cache (not to be persisted) of competences concerned by this cycle.
	// Basically it is Cycle.competences + all the parents from these competences.
	// It's needed to filter out competences from other cycles when we display this cycle and its competences.
    transient private List<Competence> computedCompetences = null;  // null means that the cache has not been computed yet
	
	
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
		this.name = HTMLUtil.removeHtmlTags(name);
	}

    @Override
    public Long getId() {
        return id;
    }

    public List<Competence> getComputedCompetences() {
        synchronized(this) {  // To prevent 2 threads (rare case) from building the cache at the same time.
            if (computedCompetences == null) {  // cache not yet computed
                CompetenceService competenceService = ContextUtil.getSpringBean(CompetenceService.class);
                competenceService.computeAllCompetencesRelatedToCycle(this);
            }
        }
        return computedCompetences;
    }

    public void setComputedCompetences(List<Competence> computedCompetences) {
        this.computedCompetences = computedCompetences;
    }

    public List<Competence> getCompetences() {
        return competences;
    }
    
    
}
