package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Scenario;


import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings ("unchecked")
public class ScenarioRepository extends BaseRepository<Scenario> {

	public List<Scenario> findScenarioByUser (String user){
	
		List <Scenario> result = em.createQuery("SELECT s FROM Scenario s WHERE s.user=:user ").setParameter("user", user).getResultList();
		return result;
		
	}
}
