package dbController;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name ="WEATHER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Weather {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(columnDefinition = "UNSIGNED INT(10)", name="id")
	private int id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	private Location location;
	
	
	@Override
	public String toString() {
		return "Weather [id=" + id + ", location=" + location + ", date=" + date + ", rainfall=" + rainfall
				+ ", sun_hours=" + sun_hours + ", temperature=" + temperature + "]";
	}
	@Column(name = "date")
	private Timestamp date;
	@Column(name = "rainfall")
	private float rainfall;
	@Column(name = "sun_hours")
	private float sun_hours;
	@Column (name = "temperature")
	private double temperature;

	
	public int getId() {
		return id;
	}


	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public float getRainfall() {
		return rainfall;
	}
	public void setRainfall(float rainfall) {
		this.rainfall = rainfall;
	}
	public float getSun_hours() {
		return sun_hours;
	}
	public void setSun_hours(float sun_hours) {
		this.sun_hours = sun_hours;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
		

}
