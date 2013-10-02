package learningresourcefinder.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import learningresourcefinder.util.TextUtils;

// Use this entity when you need a short id (for URLs) based on the id. for exemple "AZ3" instead of 14223 
@MappedSuperclass
public abstract class BaseEntityWithShortId extends BaseEntity  {

         
    @Column(nullable=true) // nullable here but fed in baseentity/postpersist from new id
    String shortId;
   
    // Must be abstract to redefine in all inheritance models
    // to be able to redefine an id annotation per model
    public abstract Long getId();
        
    public String getShortId() {
        return getOrComputeShortId();
    }
    
    public void setShortId(String shortId) {
        this.shortId = shortId;
    }
    
    public String getOrComputeShortId() {
        
        if (shortId == null) {
            // update the shortId from the new id
            // shortId is initialized once here at first call because we need
            // the new id and so we must wait the nextVal() of persist(resource)
            // call before calculate the shortId
            if (this.getId() != null) {
                this.setShortId(TextUtils.generateShortId(this.getId()));
            } else {
                throw new RuntimeException(
                        "Bug: id can't be null during call to getShortedId. Programmer is not supposed to call this method before em.persist(entity).");
            }
        }
        return shortId;
    }
  
}
