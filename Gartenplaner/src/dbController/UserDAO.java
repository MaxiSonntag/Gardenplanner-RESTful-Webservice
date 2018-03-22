package dbController;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UserDAO {
	
	private SessionFactory factory;
	//private Session session;
	
	public UserDAO(SessionFactory f) {
		factory = f;
	//	session = s;
	}
	
	
	public User createUser(User u) {
		
		Session session = StoreData.getInstance().getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		
		User result = (User)session.merge(u);

		t.commit();
		session.evict(result);
		session.close();
		
		return result;
	}

	public boolean deleteUser(User u) {
		Session session = StoreData.getInstance().getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		
		
		User user=getUser(u);

		if(user!=null) {
		session.remove(user);
		}
		t.commit();
		session.close();
		return true;
	}
	
	public User getUser(User u) {
		Session session = StoreData.getInstance().getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root).where(cb.and(cb.equal(root.get("email"), u.getEmail()) , cb.equal(root.get("token"), u.getToken())));
		//session.createCriteria(User.class).add(Restrictions.eq("email", u.getEmail())).add(Restrictions.eq("token", u.getToken())).uniqueResult();
		User user = session.createQuery(cq).uniqueResult();
		t.commit();
		if(user!=null) {
		session.evict(user);
		}
		session.close();
		return user;
	}
	
	public boolean queryUser(User u){
		
		Session session = factory.openSession();
		TypedQuery<User> q = session.createQuery("from User where eMail=:mail and token=:token");
		q
		.setParameter("mail", u.getEmail())
		.setParameter("token", u.getToken());
		
		List<User> queryResult = q.getResultList();
		
		//session.close();
		
		if(StoreData.getInstance().isResultEmpty(queryResult)){
			System.out.println("UserQuery empty");
			return false;
		}
		for(User queryUser : queryResult){
			if(queryUser.getEmail().equals(u.getEmail()) && queryUser.getToken().equals(u.getToken())){
				return true;
			}
		}
		return false;
	}
	
	public List<User> queryAllUsers(){
		Session session = factory.openSession();
		TypedQuery<User> q = session.createQuery("from User");
		
		List<User> queryResult = q.getResultList();
		
		//session.close();
		
		return queryResult;
	}
}