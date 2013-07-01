package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class School extends BaseEntity {
	
	@Column(length=50)
	private String name;
	@Column(length=50)
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
