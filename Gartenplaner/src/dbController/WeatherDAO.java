package dbController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Constants;
import model.weather.WeatherRoot;
import requests.APIRequest;

public class WeatherDAO {
	
	private SessionFactory factory;
	//private Session session;
	
	public WeatherDAO(SessionFactory f) {
		factory = f;
		
	}
	

	
	
	public boolean updateWeatherForBed(Beds bed) {
		Weather w = StoreData.getInstance().getLocationDAO().loadAndSaveWeatherDataForLocation(bed.getLocation());
		if(w.getSun_hours() == -1) {
			return false;
		}
		
		Weather oldest = new Weather();
		oldest.setDate(new Timestamp(new java.util.Date().getTime()));
		for(Weather weather : bed.getLocation().getWeather()) {
			if(weather.getDate().before(oldest.getDate())) {
				oldest = weather;
			}
		}
		
		Collection<Weather> weathers;
		boolean shouldDeleteOldest = false;
		boolean deleted = false;
		if(bed.getLocation().getWeather().isEmpty()) {
			weathers = new ArrayList<Weather>();
			weathers.add(oldest);
			bed.getLocation().setWeather(weathers);
		}else {
			weathers = bed.getLocation().getWeather();
			if(weathers.size() >= 5) {
				weathers.remove(oldest);
				shouldDeleteOldest = true;
				
			}
			weathers.add(w);
			bed.getLocation().setWeather(weathers);
		}
		
		StoreData.getInstance().saveData(bed);
		if(shouldDeleteOldest) 
			deleted = deleteWeather(oldest);
		
		
		
		System.out.println("OLDEST DELETED: "+deleted);
		return true;
	}
	
	private boolean deleteWeather(Weather w) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Weather> cq = cb.createQuery(Weather.class);
		Root<Weather> root = cq.from(Weather.class);
		cq.select(root).where(cb.equal(root.get("id"), w.getId()));
		List<Weather> weathers = new ArrayList<Weather>();
		weathers = session.createQuery(cq).getResultList();
		
		if(weathers.isEmpty()) {
			return false;
		}
		Weather dbWeather = weathers.get(0);
		session.remove(dbWeather);
		t.commit();
		//session.close();
		return true;
		
	}

}
