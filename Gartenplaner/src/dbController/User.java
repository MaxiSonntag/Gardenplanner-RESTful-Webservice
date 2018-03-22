package dbController;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name ="USERS")
public class User{
	
	public User() {
		super();
	}

	@Id
    @Column(name = "eMail", nullable = false, unique=true)
	private String email;
	
    @Column(name = "token")
	private String token;
    
    @Column(name = "timestamp")
    @JsonIgnore
    private Timestamp stamp;
    
    

	public Timestamp getStamp() {
		return stamp;
	}

	public void setStamp(Timestamp stamp) {
		this.stamp = stamp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@OneToMany(mappedBy="user")
	private List<Beds> beds;
	
	
	public List<Beds> getBeds() {
		return beds;
	}

	public void setBeds(List<Beds> beds) {
		this.beds = beds;
	}


	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Email=" + email + " Token=" + token;
	}
	
	
}
