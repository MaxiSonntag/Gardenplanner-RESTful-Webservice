package dbController;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import service.resource.HypermediaLink;

@Entity
@Table(name="BEDS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Beds {
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="id")
	private int id; 
	
	@ManyToOne(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY)
	//@JoinColumn(name ="owner", referencedColumnName = "email", insertable = true, updatable = true)
	@JsonIgnore
	private User user;
	
	@Column(name="name")
	private String name;
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnoreProperties("beds")
	//@JoinColumn(name ="locationID", referencedColumnName = "id", insertable = true, updatable = true)
	private Location location;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="beds", fetch=FetchType.LAZY)
	private List<Crops> crops;
	
	@Column(name="etag")
	@JsonIgnore
	private String eTag;
	/*
	//@InjectLinks({
		
	//})
	@Transient
	@XmlElement(name="link")
	//@XmlElementWrapper(name="_links")
	//@XmlJavaTypeAdapter(Link.JaxbAdapter.class)
	@InjectLink(resource= BedRessource.class,
	style=Style.ABSOLUTE,
	rel="self",
	bindings= {@Binding(name="userId", value="penis"),
			@Binding(name="id", value="1")},
	method="getSpecificBed"
	)
	String link;
	
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}*/
	
	@Transient
	@XmlElement(name="_links")
	private List<HypermediaLink> links = new ArrayList<>();

	public List<HypermediaLink> getLinks() {
		return links;
	}

	public void setLinks(List<HypermediaLink> links) {
		this.links = links;
	}

	public String geteTag() {
		return eTag;
	}

	public void seteTag(String eTag) {
		this.eTag = eTag;
	}

	public List<Crops> getCrops() {
		return crops;
	}

	public void setCrops(List<Crops> crops) {
		this.crops = crops;
	}

	
	public int getId() {
		return id;
	}

	//public Beds(User user) {
	//	super();
	//	this.user = user;
	//}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Beds [id=" + id + ", user=" + user + ", name=" + name + ", location=" + location.getCity() + ", crops=" + crops
				+ "]";
	}

	

	
	
	

}
