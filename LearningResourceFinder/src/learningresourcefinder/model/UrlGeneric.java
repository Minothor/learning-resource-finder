package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class UrlGeneric extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotBlank
    @Column(nullable = false)
    private String url;

    public UrlGeneric() {}

    public Long getId() {
        return id;
    }

   public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
