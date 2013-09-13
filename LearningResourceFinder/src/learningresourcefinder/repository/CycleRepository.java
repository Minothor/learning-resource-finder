package learningresourcefinder.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import learningresourcefinder.model.Cycle;
@Repository
@SuppressWarnings("unchecked")
public class CycleRepository extends BaseRepository<Cycle> {

    
    public List<Cycle> findAllCycles() {
        List<Cycle> result=em.createQuery("SELECT c FROM Cycle c ORDER BY c.name ASC")
                .getResultList(); 
        return result;
        
    }
    
    public Cycle findByName(String name){
        
        return getSingleOrNullResult(
                em.createQuery("SELECT c FROM Cycle c WHERE lower(c.name) = :name")
                .setParameter("name",name)
                );
    }
        
}
