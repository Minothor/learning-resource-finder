package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import learningresourcefinder.util.HTMLUtil;

@Entity
public class School extends BaseEntity {
	
    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
	@Column(length=50)
	private String name;
	@Column(length=50)
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = HTMLUtil.removeHtmlTags(name);
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = HTMLUtil.removeHtmlTags(address);
	}
	
    @Override
    public Long getId() {
        return id;
    }
}
