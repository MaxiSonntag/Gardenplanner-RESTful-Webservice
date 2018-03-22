package dbController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class BedDAO {
	
	private SessionFactory factory;
	//private Session session;
	
	public BedDAO(SessionFactory f) {
		factory = f;
	//	session = s;
	}
	
public List<Beds> queryBedsForUser(User u){
		
	Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Beds> cq = cb.createQuery(Beds.class);
		Root<Beds> root = cq.from(Beds.class);
		cq.select(root).where(cb.equal(root.get("user"), u));
		
		
		
		List<Beds> queryResult = session.createQuery(cq).getResultList();
		//System.out.println("RESULT:\n"+queryResult);
		t.commit();
		//session.close();
	
		if(StoreData.getInstance().isResultEmpty(queryResult)){
			return new ArrayList<Beds>();
		}
		
		for(Beds b : queryResult) {
			Hibernate.initialize(b.getCrops());
			Hibernate.initialize(b.getUser());
			Hibernate.initialize(b.getLocation().getWeather());
		}
		session.close();
		return queryResult;
	}
	
	public Beds queryBedById(User u, String id){
		
		Session session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root).where(cb.equal(root.get("email"), u.getEmail()));
		
		List<User> queryResult = session.createQuery(cq).getResultList();
		
		List<Beds> filteredList = queryResult.get(0).getBeds().stream().filter(b ->b.getId()==Integer.parseInt(id)).collect(Collectors.toList()); 
		//session.close();
		
		if(StoreData.getInstance().isResultEmpty(filteredList)){
			Beds b = new Beds();
			b.setId(-1);
			return b;
		}
		
		return filteredList.get(0);
		
	}
	
	public Beds updateBedById(String id, Beds bed){
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Beds> cq = cb.createQuery(Beds.class);
		Root<Beds> root = cq.from(Beds.class);
		cq.select(root).where(cb.equal(root.get("id"), id));
		Beds b = session.createQuery(cq).getResultList().get(0);
		
		
		bed.getLocation().setId(b.getId());
		ArrayList<Beds> bedList = new ArrayList<>();
		bedList.add(b);
		bed.getLocation().setBeds(bedList);
		b.setLocation(bed.getLocation());
		b.setName(bed.getName());
		bed.setUser(b.getUser());
		System.out.println(bed.toString());
		b.seteTag(bed.geteTag());
		session.merge(b);
		
		t.commit();
		
				
		//session.close();
		
		return b;
		
	}
	
	
	public boolean deleteBed(String uId, String id){
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root).where(cb.equal(root.get("email"), uId));
		User user = session.createQuery(cq).getResultList().get(0);
		
		List<Beds> beds = user.getBeds().stream()
				.filter(b -> b.getId()==Integer.parseInt(id))
				.collect(Collectors.toList());
		
		if(beds.isEmpty()) {
			return false;
		}
		Beds bedToDelete = beds.get(0);
		
		bedToDelete.getLocation().getBeds().remove(bedToDelete);
		bedToDelete.setUser(null);

		//Test Freitag 19.01.2018
		//bedToDelete.getUser().getBeds().remove(bedToDelete);
		//Test Ende
		session.remove(bedToDelete);
		
		t.commit();
		session.close();
		return true;
	}

}
