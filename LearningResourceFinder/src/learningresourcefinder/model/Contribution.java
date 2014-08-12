package learningresourcefinder.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sun.org.apache.xpath.internal.operations.Equals;

import learningresourcefinder.util.Action;

@Entity
public class Contribution extends BaseEntity
	{

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;

		@ManyToOne
		private User user;

		@Column(nullable = false)
		private int points;

		@ManyToOne
		private Resource ressource;

		@Column(nullable = false)
		private Action action;

//		public enum ElementTypeValues {
//			TITLE, DESCRIPTION, PLATFORM, FORMAT, NATURE, LANGUAGE, DURATION, AUTHOR, TOPIC, ADVERTISING
//		};

		public Contribution(User user, Resource ressource, int points, Action action)
			{
				super();
				setRessource(ressource);
				setUser(user);
				setPoints(points);
				setAction(action);
			}		

		public Contribution()
			{
			}

		public Resource getRessource() {
			return ressource;
		}

		public void setRessource(Resource ressource) {
			this.ressource = ressource;
		}

		

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Long getId() {
			return id;
		}

		public int getPoints() {
			return points;
		}

		public void setPoints(int points) {
			this.points = points;
		}
		
		public Action getAction() {
			return action;
		}

		public void setAction(Action action) {
			this.action = action;
		}
		
	
		@Override
		public boolean equals(Object other) {
			Contribution contribution=((Contribution) other);		
			return (contribution.getAction()==this.getAction()) && (contribution.getUser()==this.getUser())&&(contribution.getRessource()==this.getRessource());
		}

	}
