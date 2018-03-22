package dbController;

import javax.persistence.Entity;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name="LOCATIONS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location {
	
	@Id
	@Column(name="id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	private int id;
	
	@Column(name = "zip")
	private String postalCode;
	
	@Column(name = "city")
	private String city;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="location", cascade=CascadeType.ALL, orphanRemoval=true)
	@JsonIgnoreProperties("location")
	private Collection<Beds> beds;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="location", cascade=CascadeType.ALL)
	@JsonIgnoreProperties("location")
	private Collection<Weather> weather;

	@Column(name = "country")
	private String country;
	
	@Column(name="indoor")
	private boolean indoor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String zip) {
		this.postalCode = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isIndoor() {
		return indoor;
	}

	public void setIndoor(boolean indoor) {
		this.indoor = indoor;
	}
	
	public Collection<Weather> getWeather() {
		return weather;
	}

	public void setWeather(Collection<Weather> weather) {
		this.weather = weather;
	}
	
	public Collection<Beds> getBeds() {
		return beds;
	}

	public void setBeds(Collection<Beds> beds) {
		this.beds = beds;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", postalCode=" + postalCode + ", city=" + city + ", beds=" + beds + ", country="
				+ country + ", indoor=" + indoor + "]";
	}

}