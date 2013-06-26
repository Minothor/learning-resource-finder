package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Comment;
import learningresourcefinder.model.Resource;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class CommentRepository extends BaseRepository<Comment>
{
	public List<Comment> findCommentByProblem(Resource resource) {
		List<Comment> results = (List<Comment>) em.createQuery("SELECT c FROM Comment c WHERE c.resource = :resource")
				.setParameter("resource", resource);
		return results;
	}
	
}
