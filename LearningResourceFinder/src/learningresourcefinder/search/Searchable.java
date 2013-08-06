package learningresourcefinder.search;

import java.util.Map;

public interface Searchable {
    
    // this interface would be implemented by the class that may be searchable
	
    public Map<String ,String>  getCriterias();
    
    public String getBoostedCriteriaName();
    
    public Long getId();
    
    
}
