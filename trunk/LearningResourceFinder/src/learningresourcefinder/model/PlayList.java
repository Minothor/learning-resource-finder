package learningresourcefinder.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import learningresourcefinder.web.Slugify;

import org.hibernate.annotations.Type;

@Entity
@SequenceGenerator(name="PlayListSequence", sequenceName="PLAYLIST_SEQUENCE")
public class PlayList extends BaseEntityWithShortId {
    
    @Id  @GeneratedValue(generator="PlayListSequence") 
    Long id;
    
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
    private List<Resource> resourceList = new ArrayList<Resource>();

    public PlayList() {} // No arg constructor for Hibernate
    
    public PlayList(String name, String description) {
		this.name = name;
		this.description = description;
		this.slug = Slugify.slugify(name);
	}

	///////// Getters & Setters //////////
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

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public String getSlug() {
        return slug;
    }
    public void setSlug(String slug) {
        this.slug = slug;
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
	
}