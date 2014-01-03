package models;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="Subscription")
@Entity@Access(AccessType.FIELD)
public class StudentLesson extends Subscription {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StudentLesson() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentLesson(Long id, Long studentId, Long lessonId) {
		super(id, studentId, lessonId);
		// TODO Auto-generated constructor stub
	}

	public StudentLesson(Long studentId, Long lessonId) {
		super(studentId, lessonId);
		// TODO Auto-generated constructor stub
	}

}
