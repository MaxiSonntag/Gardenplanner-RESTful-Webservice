package authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.annotations.SerializedName;

public class AuthenticationCallbackData {
	
	@SerializedName("access_token")
	private String accessToken;
	
	@SerializedName("expires_in")
	private int expiresIn;
	
	@SerializedName("id_token")
	private String idToken;
	
	
//	private transient JsonObject decodedIdToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

//	public JsonObject getDecodedIdToken() {
//		return decodedIdToken;
//	}
//
//	public void setDecodedIdToken(JsonObject decodedIdToken) {
//		this.decodedIdToken = decodedIdToken;
//	}
//	
//	
//	
	public DecodedJWT decodeIdToken(String idToken){
		
		 DecodedJWT jwt = JWT.decode(idToken);
		 return jwt;
		 //String email = jwt.getClaim("email").asString();
		 //System.out.println("E-Mail: "+email);
	}
	
	

	@Override
	public String toString() {
		return "AuthenticationCallbackData [accessToken=" + accessToken + ", idToken="
				+ idToken + "]";
	}
	
	

}