package learningresourcefinder.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import learningresourcefinder.model.Contribution;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.util.Action;

@Repository
@SuppressWarnings("unchecked")
public class ContributionRepository extends BaseRepository<Contribution>
	{

		public List<Contribution> findAllByUser(User user) {
			return em.createQuery("SELECT c FROM Contribution c WHERE c.user = :user")
					.setParameter("user", user)
					.getResultList();
		}

		public List<Contribution> findByUserAndRessource(User user, Resource resource) {
			return em.createQuery("select c from Contribution c where c.user = :user and c.ressource=:ressource")
					.setParameter("user", user)
					.setParameter("ressource", resource)
					.getResultList();
		}
	}
