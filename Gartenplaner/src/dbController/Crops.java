package dbController;

import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
@Table(name="CROPS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Crops {
	
	@Id
	@Column(columnDefinition = "UNSIGNED INT(11)", name="id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	//@JsonIgnoreProperties({"crops", "name", "location"})
	//@JsonUnwrapped
	@JsonIgnore
	private Beds beds;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "sow_date")
	private Date sowDate;
	
	@Column(name = "water_interval")
	private String waterInterval;
	
	@Column(name = "last_poured")
	private String lastPoured;
	
	@Column(name = "maturing_time")
	private String maturingTime;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Beds getBeds() {
		return beds;
	}

	public void setBeds(Beds beds) {
		this.beds = beds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSowDate() {
		return sowDate;
	}

	public void setSowDate(Date sowDate) {
		this.sowDate = sowDate;
	}

	public String getWaterInterval() {
		return waterInterval;
	}

	public void setWaterInterval(String waterInterval) {
		this.waterInterval = waterInterval;
	}

	public String getLastPoured() {
		return lastPoured;
	}

	public void setLastPoured(String lastPoured) {
		this.lastPoured = lastPoured;
	}

	public String getMaturingTime() {
		return maturingTime;
	}

	public void setMaturingTime(String maturingTime) {
		this.maturingTime = maturingTime;
	}

	@Override
	public String toString() {
		return "Crops [id=" + id + ", beds=" + beds.getId() + ", name=" + name + ", description=" + description + ", sowDate="
				+ sowDate + ", waterInterval=" + waterInterval + ", lastPoured=" + lastPoured + ", maturingTime="
				+ maturingTime + "]";
	}

}

