package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.search.Searchable;
import learningresourcefinder.util.HTMLUtil;
import learningresourcefinder.web.Slugify;

import org.apache.commons.lang3.StringEscapeUtils;
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

	public enum ValidationStatus {
		ACCEPT("validée"), REJECT("rejetée");	
		private String description;	
		ValidationStatus(String description) 	{  this.description = description;}		
		public String getDescription() 		{ return description; }
	}

	public static final int MAX_TITLE_LENGTH = 50; 
    
	@Id   @GeneratedValue(generator="ResourceSequence") // We want Resources to have ids as short as possible (=> they get their own numbering and not the global HIBERNATE_SEQUENCE)
    Long id;
        
    @Column(length = MAX_TITLE_LENGTH, nullable=false)
    @Size(max=MAX_TITLE_LENGTH, message="Le nom d'une ressource ne peut contenir que "+MAX_TITLE_LENGTH+" caractères maximum")
	private String name;
	
    @Column(length=50, nullable=false)
    @Size(max=50)
    private String slug;
    
    @Column(length=20, nullable = true)
    @Enumerated(EnumType.STRING)
    private Topic topic;  // Temporary quick selection when adding a resource, before a contributor has the time to bing the resource with  a competency.
    
    @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Cycle minCycle;  // Minimum age for which this resource is suitable (younger kids would usually be in trouble)
    
    @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Cycle maxCycle;  // Maximum age for which this resource is suitable (older kids would usually find this too simple or stupid)
    

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
	private Language language;
	
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Format format;

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
		
	@ManyToOne
    @JoinColumn(nullable=true) 
	private User validator;
	
	private Date validationDate;
	
	@Column(nullable = true)
    @Enumerated(EnumType.STRING)
	private ValidationStatus validationStatus;
	
	@Column(nullable = false)
	private Long viewcount = 0L;
	  
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
	
	
	
	@Transient   // We will store this collection through this.platformsCollOnString
    Set<Platform> platforms = new HashSet<Platform>();
    
    private String platformsCollOnString;   // To store this.platforms
    
    
    public Set<Platform> getPlatforms() {
        return Collections.unmodifiableSet(platforms);  // We prevent modifying this set  without going through setPlatforms().
    }

    public void setPlatforms(Set<Platform> platformSet) {
        this.platforms = platformSet;
        
        // changing this.platforms is not enough because it's transient and will not alone triggers dirty checking auto-save.
        // This string will not be saved in the DB, becaus it will be changed in the prePersistUpdate.
        platformsCollOnString = "Please save this entity";  
    }
    
    
    @PrePersist  @PreUpdate  
    public void prePersistUpdate(){
        // We set the value of this.platformsOnString from this.platforms collection.
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (Platform current : platforms) {
            sb.append(delim).append(current.name());
            delim = ",";
        }
        this.platformsCollOnString=sb.toString();
    }

    @PostLoad
    public void postLoad()  {
        // We populate this.platform set from this.platformsOnString
        platforms.clear();
        if (StringUtils.isNotBlank(this.platformsCollOnString)) {
            String[] values = this.platformsCollOnString.split(",");
            for (String value : values) {
                Platform current = getEnumValueFor(value);
                if (current != null) {
                    platforms.add(current);
                }
            }
        }
    }

    private Platform getEnumValueFor(String name) {
        for (Platform current : Platform.class.getEnumConstants()) {
            if (current.name().equals(name)) {
                return (Platform) current;
            }
        }
        return null;
    }
	
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
        criterias.put("topic", topic==null ? "" : topic.getDescription() );
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
	
    public Cycle getMinCycle() {
        return minCycle;
    }

    public void setMinCycle(Cycle minCycle) {
        this.minCycle = minCycle;
    }

    public Cycle getMaxCycle() {
        return maxCycle;
    }

    public void setMaxCycle(Cycle maxCycle) {
        this.maxCycle = maxCycle;
    }	
    
    public User getValidator() {
    	return this.validator;
    }

    public void setValidator(User validator) {
    	this.validator = validator;
    }
    
    public Date getValidationDate() {
    	return this.validationDate;
    }

    public void setValidationDate(Date validationDate) {
    	this.validationDate = validationDate;
    }
    
    public ValidationStatus getValidationStatus() {
    	return this.validationStatus;
    }
    
    public void setValidationStatus(ValidationStatus validationStatus) {
    	this.validationStatus = validationStatus;
    }

	public Long getViewcount() {
		return viewcount;
	}

	public void setViewcount(Long viewcount) {
		this.viewcount = viewcount;
	}   
    
    
}
