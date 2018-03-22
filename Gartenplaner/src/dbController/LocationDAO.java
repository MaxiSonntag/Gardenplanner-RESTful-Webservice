package dbController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
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

public class LocationDAO {
	
	private SessionFactory factory;
	//private Session session;
	
	public LocationDAO(SessionFactory f) {
		factory = f;
	//	session = s;
	}
	
	public List<Location> queryAllLocations(){
		Session session = factory.openSession();
		TypedQuery<Location> q = session.createQuery("from Location");
		
		List<Location> queryResult = q.getResultList();
		
		//session.close();
		
		return queryResult;
	}
	
	public List<Location> queryLocations(){
		Session session = factory.openSession();
		Transaction t = session.getTransaction();
		t.begin();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Location> cq = cb.createQuery(Location.class);
		Root<Location> root = cq.from(Location.class);
		cq.select(root);
		List<Location> locations = new ArrayList<Location>();
		locations = session.createQuery(cq).getResultList();
		
		
		t.commit();
		//session.close();
		return locations;
	}
	
public Weather loadAndSaveWeatherDataForLocation(Location l) {
		
		APIRequest APIrequest = new APIRequest();
		GsonBuilder gson = new GsonBuilder();
		Gson gsonObj = gson.create();
		
		//Weather
		String[] param1 = {"q"};
		String[] param2 = {l.getCity() + "," + l.getCountry()};
		
		String[][] params = {param1, param2};
		
		String response = APIrequest.loadDataFromApi(Constants.WEATHER_API, params);

		WeatherRoot weatherRoot = gsonObj.fromJson(response, WeatherRoot.class);
		
		Weather w = new Weather();
		w.setSun_hours(-1);
		
		if(!weatherRoot.getData().isValid()) {
			return w;
		}
		
		w.setDate(new Timestamp(new java.util.Date().getTime()));
		w.setLocation(l);
		
		
		w.setRainfall(Float.parseFloat(weatherRoot.getData().getCurrentCondition().get(0).getRainfall()));
		w.setSun_hours(Float.parseFloat(weatherRoot.getData().getWeather().get(0).getSunHour()));
		w.setTemperature(Double.parseDouble(weatherRoot.getData().getCurrentCondition().get(0).getTemperature()));
		
		return w;
		
	}

}
