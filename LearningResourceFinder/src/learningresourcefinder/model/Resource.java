package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.search.Searchable;
import learningresourcefinder.util.HTMLUtil;
import learningresourcefinder.web.Slugify;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;


@Entity
@Table(name="resource")
@SequenceGenerator(name="ResourceSequence", sequenceName="RESOURCE_SEQUENCE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource extends BaseEntityWithShortId implements Searchable {
    

    public enum Topic {
        MATH("Math"), FRENCH("Français"), DUTCH("Néerlandais"), ENGLISH("Anglais"), HISTORY("Histoire"), GEO("Géographie"), SCIENCE("Sciences"), OTHER("Autre");
        private Topic(String description){this.description = description;}
        private final String description;
        public String getDescription(){return description;}
    }

    
	@Id   @GeneratedValue(generator="ResourceSequence") // We want Resources to have ids as short as possible (=> they get their own numbering and not the global HIBERNATE_SEQUENCE)
    Long id;
        
    @Column(length = 50, nullable=false)
    @Size(max=50, message="Le nom d'une ressource ne peut contenir que 50 caractères maximum")
	private String name;
	
    @Column(length=50, nullable=false)
    @Size(max=50)
    private String slug;
    
    @Column(length=20, nullable = true)
    @Enumerated(EnumType.STRING)
    private Topic topic;  // Temporary quick selection when adding a resource, before a contributor has the time to bing the resource with  a competency.
    


    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
	private Language language;
	
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Format format;
	
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Nature nature;
	
	@Column()
	private Boolean advertising;
	
	@Column()
	private Integer duration;
	
	
	@Column()
	private int numberImage;
	
	
	@Column()
    private Double avgRatingScore;
	
	@Column()
	private Long countRating;
    
	@Column()
	private String author;
	
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;
	
	@OneToMany(mappedBy="resource")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Problem> problems = new HashSet<>();
    
	@ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	List<Competence> competences = new ArrayList<>();
	
	public Resource() {} // No arg constructor for Hibernate
	
	public Resource(String name, String description, User author) {
		this.name = HTMLUtil.removeHtmlTags(name);
		this.description = HTMLUtil.removeHtmlTags(description);
		this.slug = Slugify.slugify(name);
		this.createdBy = author;  // We are probably executing this constructor with params in a test batch code (=> no logged in user to be the author).
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@OneToMany(mappedBy="resource")
	List<UrlResource> urlResources = new ArrayList<>();
	
	
	public int addImageOnDB(){
		return numberImage;
	}
	
	
	/**************************** Getters & Setters ************************************/
	
    public Double getAvgRatingScore() {
		return avgRatingScore;
	}

	public void setAvgRatingScore(Double score) {
		this.avgRatingScore = score;
	}
	
	public Long getCountRating() {
		return countRating;
	}

	public void setCountRating(Long counter) {
		this.countRating = counter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = HTMLUtil.removeHtmlTags(name);
	}

	public String getDescription() {
		return description;
	}

	/** Shorter version of the description (for lists) */
	public String getDescriptionCut() {
	    final int MAX_LENGTH_DESCRIPTION_CUT = 150;
	    if (description == null) {
	        return "";
	    }
	    if (description.length() > MAX_LENGTH_DESCRIPTION_CUT) {
	        return description.substring(0 , MAX_LENGTH_DESCRIPTION_CUT) + "...";
	    } else {
	        return description;
	    }
	}
	public void setDescription(String description) {
		this.description = HTMLUtil.removeHtmlTags(description);
	}

	public Set<Problem> getProblems() {
		return problems;
	}

	public List<Competence> getCompetences() {
		return competences;
	}
	
	public List<UrlResource> getUrlResources() {
		return this.urlResources;
	}
	public Boolean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(Boolean advertising) {
        this.advertising = advertising;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    @Override
    public Long getId() {
        return id;
    }
    
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

	public int getNumberImage() {
		return numberImage;
	}

	public void setNumberImage(int resourceid) {
		this.numberImage = resourceid;
	}
	public Topic getTopic() {
        return topic;
    }
	public void setTopic(Topic aTopic) {
	    this.topic = aTopic;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
	
	
}
