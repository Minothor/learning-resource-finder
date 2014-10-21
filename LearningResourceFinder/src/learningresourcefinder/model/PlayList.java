package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import learningresourcefinder.search.Searchable;
import learningresourcefinder.util.HTMLUtil;
import learningresourcefinder.web.Slugify;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@SequenceGenerator(name="PlayListSequence", sequenceName="PLAYLIST_SEQUENCE")
public class PlayList extends BaseEntityWithShortId implements Searchable {
    
    @Id  @GeneratedValue(generator="PlayListSequence") 
    Long id;
    
    @NotBlank(message="Entrez le nom de la séquence")
    @Size(max=50, message="Le nom de la séquence ne peut contenir que 50 caractères maximum")
    @Column(length=50,nullable=false )
    private String name;

    @Column(length=50, nullable=false)
    private String slug;
    
    @Column()
    private Boolean picture;
    
    @Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.StringClobType")
    String description;

    @ManyToMany
    @OrderColumn(name="LIST_INDEX")
    private List<Resource> resources = new ArrayList<Resource>();

    public PlayList() {} // No arg constructor for Hibernate
    
    public PlayList(String name, String description) {
		this.name = HTMLUtil.removeHtmlTags(name);
		this.description = HTMLUtil.removeHtmlTags(description);
		this.slug = Slugify.slugify(name);
	}

	///////// Getters & Setters //////////
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
        if (description != null && description.length() > MAX_LENGTH_DESCRIPTION_CUT) {
            return description.substring(0 , MAX_LENGTH_DESCRIPTION_CUT) + "...";
        } else {
            return description;
        }
    }
    public void setDescription(String description) {
        this.description = HTMLUtil.removeHtmlTags(description);
    }

    public List<Resource> getResources() {
        return resources;
    }

    public String getSlug() {
        return slug;
    }
    public void setSlug(String slug) {
        this.slug = HTMLUtil.removeHtmlTags(slug);
    }

    @Override
    public Long getId() {
        return id;
    }
    
	public Boolean getPicture() {
		return picture;
	}

	public void setPicture(Boolean picture) {
		this.picture = picture;
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
	
}
