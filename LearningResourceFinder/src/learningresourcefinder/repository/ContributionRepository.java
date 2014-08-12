package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Contribution;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.util.Action;

import org.springframework.stereotype.Repository;


@Repository
@SuppressWarnings("unchecked")
public class ContributionRepository extends BaseRepository<Contribution> {

	public List<Contribution> findAllByUser(User user) {
		return em.createQuery("SELECT c FROM Contribution c WHERE c.user = :user order by c.createdOn DESC")
				.setParameter("user", user)
				.getResultList();
	}

	public List<Contribution> findByUserAndRessource(User user, Resource resource,Action action) {
		return em.createQuery("select c from Contribution c where c.user = :user and c.ressource=:ressource and c.action=:action")
				.setParameter("user", user)
				.setParameter("ressource", resource)
				.setParameter("action", action)
				.getResultList();
	}

	public Long sumByUser(User user) {
		return (Long) em.createQuery("SELECT sum(points) FROM Contribution c WHERE c.user = :user").setParameter("user", user).getSingleResult();
	}
}
