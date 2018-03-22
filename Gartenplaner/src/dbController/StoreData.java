package dbController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.*;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Constants;
import model.weather.WeatherRoot;
import requests.APIRequest;
import service.resource.caching.HTTPCaching;

public class StoreData {

	private static StoreData instance;
	private SessionFactory factory;

	private BedDAO bedDAO;
	private CropDAO cropDAO;
	private LocationDAO locationDAO;
	private UserDAO userDAO;
	private WeatherDAO weatherDAO;

	private StoreData() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		factory = cfg.buildSessionFactory();
		bedDAO = new BedDAO(factory);
		cropDAO = new CropDAO(factory);
		locationDAO = new LocationDAO(factory);
		userDAO = new UserDAO(factory);
		weatherDAO = new WeatherDAO(factory);
	}

	public static StoreData getInstance() {
		if (StoreData.instance == null) {
			StoreData.instance = new StoreData();
		}
		return StoreData.instance;
	}

	public SessionFactory getSessionFactory() {
		return factory;
	}

	public BedDAO getBedDAO() {
		return bedDAO;
	}

	public void setBedDAO(BedDAO bedDAO) {
		this.bedDAO = bedDAO;
	}

	public CropDAO getCropDAO() {
		return cropDAO;
	}

	public void setCropDAO(CropDAO cropDAO) {
		this.cropDAO = cropDAO;
	}

	public LocationDAO getLocationDAO() {
		return locationDAO;
	}

	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public WeatherDAO getWeatherDAO() {
		return weatherDAO;
	}

	public void setWeatherDAO(WeatherDAO weatherDAO) {
		this.weatherDAO = weatherDAO;
	}

	public Beds saveData(Beds bed) {

		// creating session object
		Session session = instance.factory.openSession();

		// creating transaction object
		Transaction t = session.beginTransaction();
		Beds resultBed = new Beds();
		if (bed.getClass() == Beds.class) {
			Beds b = (Beds) bed;
			ArrayList<Beds> beds = new ArrayList<>();
			beds.add(b);
			resultBed = (Beds) session.merge(b);
			// System.out.println("ID: "+b.getId()+"\nName:"+b.getName()+"\nEMail: "
			// +b.getUser().getEmail()+"\nToken:"+b.getUser().getToken()+"\nLocID:
			// "+b.getLocation().getId()+"\nLocName: "+b.getLocation().getCity()+"\nLocZip:
			// "+b.getLocation().getPostalCode()+"\nLocCountry:
			// "+b.getLocation().getCountry()+"\nLocId:
			// "+b.getLocation().getId()+"\n##########\n\n");
		}
		t.commit();
		// session.close();

		return resultBed;
	}

	// Errorhandling
	public boolean isBedValid(Beds bed) {
		Location l = bed.getLocation();
		if (bed.getName().isEmpty() || l.getCity().isEmpty() || l.getCountry().isEmpty()
				|| l.getPostalCode().isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean isUserValid(User u) {
		User dbUser = instance.getUserDAO().getUser(u);
		System.out.println("PassedUser: "+u);
		System.out.println("USER: "+dbUser);
		if (null != dbUser) {
			Timestamp ts = dbUser.getStamp();
			
			Calendar c = Calendar.getInstance();
			c.setTime(ts);
			c.add(Calendar.HOUR, 1);
			
			Calendar c2 = Calendar.getInstance();
			c2.setTime(new Date());
			
			if(c.before(c2)){
				System.out.println("TIMESTAMP INVALID");
				return false;
			}
			else {
				return true;
			}
		}
		return false;
	}

	public boolean isTokenValid(String token) {
		return !(null == token || token.isEmpty());
	}

	public boolean isTokenAndBedValid(String token, Beds bed) {
		return isTokenValid(token) && isBedValid(bed);
	}

	<T> boolean isResultEmpty(List<T> res) {

		return (res.isEmpty() || res.size() == 0 || res == null);

	}

}