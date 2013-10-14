package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import learningresourcefinder.util.HTMLUtil;

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
    
    
    public Discussion() {
    }

    public Discussion(String message) {
        super();
        this.message = HTMLUtil.removeHtmlTags(message);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = HTMLUtil.removeHtmlTags(message);
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
