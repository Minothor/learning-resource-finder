package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import learningresourcefinder.util.HTMLUtil;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="urlresource")
public class UrlResource extends BaseEntity
{
    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
	@Column(length = 50, nullable=true)
	@Size(max=50, message="le num d'une ressource ne peut contenir que 50 caractères maximum")
	private String name;
	
	@NotBlank
	@Column(nullable=false)
	private String url;
	
	@ManyToOne
	private Resource resource;
	
	public UrlResource() {}
	
	public UrlResource(String name, String url, Resource resource) {
		this.name = HTMLUtil.removeHtmlTags(name);
		this.url = url;
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = HTMLUtil.removeHtmlTags(name);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
    @Override
    public Long getId() {
        return id;
    }
}
