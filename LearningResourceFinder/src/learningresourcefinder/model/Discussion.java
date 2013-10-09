package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Discussion extends BaseEntity {

    
    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
 
    @ManyToOne
    @JoinColumn(nullable = true)
    private Problem problem;
    
    @Type(type = "org.hibernate.type.StringClobType") //https://github.com/Jasig/uPortal/pull/47
    @Column(nullable = false)
    private String message;
    
    @Override
    public Long getId() {
        return id;
    }


}
