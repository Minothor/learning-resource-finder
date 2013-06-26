package learningresourcefinder.repository;

import java.util.List;


import learningresourcefinder.model.School;


import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class SchoolRepository extends BaseRepository<School> {

	
	public List<School> findSchoolByName (String nameSchool)  {
		
		List<School> result = em.createQuery("SELECT s FROM School s WHERE s.name=:nameSchool")
		        .setParameter("nameSchool", nameSchool)
		        .getResultList();
		
		return result;
		
	}
	
	public List<School> findSchoolByCountry (String country) {
		List<School> result = em.createQuery("SELECT s FROM School s WHERE s.country=:country").setParameter("country", country).getResultList();
		return result;
	}
}
