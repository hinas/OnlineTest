package com.crossover.OnlineTest.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.crossover.OnlineTest.entity.Question;
import com.crossover.OnlineTest.entity.Student;
import com.crossover.OnlineTest.entity.StudentResponse;
import com.crossover.OnlineTest.entity.TestDetail;
import com.crossover.OnlineTest.entity.TestHistory;

public class OnlineTestDAOImpl implements OnlineTestDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Student getStudentDetails(String studentUserName) {

		Criteria critera = sessionFactory.openSession().createCriteria(Student.class);
		critera.add(Restrictions.eq("username", studentUserName));
		critera.setMaxResults(1);

		return (Student) critera.uniqueResult();
	}

	public TestDetail getTestDetails(int testId) {
		Criteria critera = sessionFactory.openSession().createCriteria(TestDetail.class);
		critera.add(Restrictions.eq("test_id", testId));
		critera.setMaxResults(1);
		return (TestDetail) critera.uniqueResult();
	}

	public Question getExamQuestion(int question_id) {
		Session session = sessionFactory.openSession();
		Question question = session.get(Question.class, new Integer(question_id));
		session.close();
		return question;
		
	}

	public int saveStudentResponse(StudentResponse studentResponse) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(studentResponse);

			tx.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return 0;
		} finally {
			session.close();
		}

		return studentResponse.getId();
	} 

	public int deleteStudentResponse(int id) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			StudentResponse studentResponse= session.load(StudentResponse.class, id);
			session.delete(studentResponse);
			tx.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return 0;
		} finally {
			session.close();
		}

		return 1;
	}

	public List<StudentResponse> getStudentResponseForTest(int student_id, int test_id) {
		String hql = "FROM StudentResponse E WHERE E.student_id= :student_id AND E.test_id= :test_id";
		Session session= sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("student_id",student_id);
		query.setParameter("test_id",test_id);
		List list= query.list();
		session.close();
		return list;
	}

	public StudentResponse getStudentResponseForQuestion(int student_id, int test_id, int question_id) {
		String hql = "FROM StudentResponse E WHERE E.student_id= :student_id AND E.test_id= :test_id AND E.question_id= :question_id";
		Session session= sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("student_id",student_id);
		query.setParameter("test_id",test_id);
		query.setParameter("question_id",question_id);
		StudentResponse studentResposne= (StudentResponse) query.uniqueResult();
		session.close();
		return studentResposne;
	}

	public int updateExistingOptionStudentResponse(int id, int newOption) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			StudentResponse studentResponse= session.load(StudentResponse.class, id);
			studentResponse.setOption_no(newOption);
			session.save(studentResponse);
			tx.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return 0;
		} finally {
			session.close();
		}

		return 1;
	}

	public int saveTestHistory(TestHistory testHistory) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(testHistory);

			tx.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return 0;
		} finally {
			session.close();
		}

		return testHistory.getId();
	}

}
