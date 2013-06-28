package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class School extends BaseEntity {
	
	@Column(columnDefinition = "VARCHAR(50)")
	private String name;
	@Column(columnDefinition = "VARCHAR(50)")
	private String address;
	@Column(columnDefinition = "VARCHAR(50)")
	private String country;
	
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
