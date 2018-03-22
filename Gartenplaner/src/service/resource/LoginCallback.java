package service.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.*;
import org.glassfish.jersey.client.ClientConfig;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import authentication.AuthenticationCallbackData;
import dbController.StoreData;
import dbController.User;
import dbController.UserDAO;
import model.Constants;

@Path("/callback")
public class LoginCallback {
	
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response receiveCallback(){
		
		if(!request.getParameter("state").equals(request.getSession().getAttribute("state"))){
			response.setStatus(401);
			return Response.ok(new Gson().toJson("Invalid state parameter")).build();
		}
		
		ClientConfig config = new ClientConfig();
		
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target("https://www.googleapis.com/oauth2/v4/token");
        
        Form form = new Form(); 
        form.param("code", request.getParameter("code")).param("client_id", Constants.CLIENT_ID).
        param("client_secret", Constants.CLIENT_SECRET).param("redirect_uri", Constants.CALLBACK_URL).
        param("grant_type", "authorization_code");
        Entity<Form> entity = Entity.form(form);
        
        
       Response response = target.request(MediaType.APPLICATION_JSON).post(entity);
       String responseAsString = response.readEntity(String.class);
		GsonBuilder gson = new GsonBuilder();
		Gson gsonObj = gson.create();
		AuthenticationCallbackData callbackData = gsonObj.fromJson(responseAsString, AuthenticationCallbackData.class);
		
		User u = new User();
		DecodedJWT jwt = callbackData.decodeIdToken(callbackData.getIdToken());
		u.setEmail(jwt.getClaim("email").asString());
		u.setToken(callbackData.getAccessToken());
		User dbUser = StoreData.getInstance().getUserDAO().createUser(u);
		
		
        return Response.ok(dbUser).build();
	}
	
	

}