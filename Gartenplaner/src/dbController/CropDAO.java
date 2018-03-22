package dbController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Constants;
import requests.APIRequest;

public class CropDAO {

	private SessionFactory factory;
	//private Session session;
	
	public CropDAO(SessionFactory f) {
		factory = f;
	//	session = s;
	}
	
	
	public List<Crops> getAllCrops(User u, int bedId) {
		Session session = factory.openSession();
		//EntityManager em = factory.createEntityManager();
		Transaction t = session.getTransaction();
		t.begin();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root).where(cb.equal(root.get("email"), u.getEmail()));
		List<User> users = session.createQuery(cq).getResultList();
		
		List<Beds> beds = users.get(0).getBeds();
		List<Beds> filteredList = beds.stream().filter(b -> b.getId()==bedId).collect(Collectors.toList());
		
		if(filteredList.isEmpty()) {
			return new ArrayList<Crops>();
		}
		
		List<Crops> result = filteredList.get(0).getCrops();
		t.commit();
		//session.close();
		return result;
	}
	
	public Crops createCrop(Crops crop, int bedId, User u) {
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Beds> cq = cb.createQuery(Beds.class);
		Root<Beds> root = cq.from(Beds.class);
		cq.select(root).where(cb.equal(root.get("id"), bedId));
		List<Beds> beds = session.createQuery(cq).getResultList();
		
		Crops aCrop = new Crops();
		aCrop.setId(-1);
		
		if(!beds.isEmpty() && beds.get(0).getUser().getEmail().equals(u.getEmail())) {
			crop.setBeds(beds.get(0));
			aCrop = (Crops)session.merge(crop);
		}
		t.commit();
		//session.close();
		
		return aCrop;
	}
	
	public boolean deleteAllCropsInBed(int bedId, User u) {
		
		Session session = factory.openSession();
		Transaction t = session.getTransaction();
		t.begin();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root).where(cb.equal(root.get("email"), u.getEmail()));
		List<User> users = session.createQuery(cq).getResultList();
		t.commit();
		
		List<Beds> beds = users.get(0).getBeds();
		List<Beds> filteredList = beds.stream().filter(b -> b.getId()==bedId).collect(Collectors.toList());
		
		if(filteredList.isEmpty() || filteredList.get(0).getCrops().isEmpty()) {
			return false;
		}
		
		boolean successful = true;
		
		while(filteredList.get(0).getCrops().size()>0 && successful) {
			Crops c = filteredList.get(0).getCrops().get(0);
			successful = this.deleteCropById(bedId, c.getId(), u);
		}
		
		
		return successful;
		
	}
	
	public Crops getCropForId(int bedId, int cropId, User u) {
		
		Session session = factory.openSession();
		Transaction t = session.getTransaction();
		t.begin();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root).where(cb.equal(root.get("email"), u.getEmail()));
		List<User> users = session.createQuery(cq).getResultList();
		
		List<Beds> beds = users.get(0).getBeds();
		List<Beds> filteredList = beds.stream().filter(b -> b.getId()==bedId).collect(Collectors.toList());
		t.commit();
		Crops result = new Crops();
		result.setId(-1);
		
		if(filteredList.isEmpty()) {
			return result;
		}
		
		Beds b = filteredList.get(0);
		List<Crops> cropsInBed = b.getCrops();
		
		if(cropsInBed.isEmpty()) {
			return result;
		}
		
		List<Crops> filteredCrops = cropsInBed.stream().filter(c -> c.getId()==cropId).collect(Collectors.toList());
		if(filteredCrops.isEmpty()) {
			return result;
		}
		
		result = filteredCrops.get(0);
		
		return result;
	}
	
	public boolean deleteCropById(int bedId, int cropId, User u) {
		
		Session session = factory.openSession();
		Transaction t = session.getTransaction();
		t.begin();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root).where(cb.equal(root.get("email"), u.getEmail()));
		List<User> users = session.createQuery(cq).getResultList();
		t.commit();
		
		List<Beds> beds = users.get(0).getBeds();
		List<Beds> filteredList = beds.stream().filter(b -> b.getId()==bedId).collect(Collectors.toList());
		
		if(filteredList.isEmpty() || filteredList.get(0).getCrops().isEmpty()) {
			return false;
		}
		
		boolean found = false;
		int sizeBefore = filteredList.get(0).getCrops().size();
		
		for(int i = 0; i < sizeBefore && !found; i++) {
			Crops cr = filteredList.get(0).getCrops().get(i);
			if(cr.getId() == cropId) {
				found = true;
				t = session.beginTransaction();
				filteredList.get(0).getCrops().remove(cr);
				session.remove(cr);
				t.commit();
			}
		}
		
		return found;
	}
	
	
	public Crops updateCrop(int bedId, int cropId, User u, Crops c) {
		Session session = factory.openSession();
		Crops savedCrop = this.getCropForId(bedId, cropId, u);
		if(savedCrop.getId() == -1) {
			return savedCrop;
		}
		
		c.setId(savedCrop.getId());
		c.setBeds(savedCrop.getBeds());
		
		Transaction t = session.beginTransaction();
		Crops res = (Crops)session.merge(c);
		t.commit();
		
		return res;
	}
	
	/*public Weather getWeatherForBed(String bedId, User u) {
		Beds bed = StoreData.getInstance().queryBedById(u, bedId);
		Location l = bed.getLocation();
		
		APIRequest APIrequest = new APIRequest();
		GsonBuilder gson = new GsonBuilder();
		Gson gsonObj = gson.create();
		
		String[] param1 = {"q"};
		String[] param2 = {l.getCity() + "," + l.getCountry()};
		
		String[][] params = {param1, param2};
		
		String response = APIrequest.loadDataFromApi(Constants.WEATHER_API, params);

		
	}*/
	
	
}
