package service.resource;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebToken.Payload;

import authentication.Authentication;
import dbController.StoreData;
import dbController.User;
import dbController.UserDAO;


@Path("")
public class UserRessource {
	
	

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("/user")
	public Response createNewUser(@Context HttpServletRequest request, @HeaderParam("token") String token) {
		
		//Authentication auth = new Authentication();
		//String result = auth.getRequestToGoole(request);
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
				.setAudience(Collections.singletonList("928528246241-16jj872cam6bd6b6v5l93nqskt65g25e.apps.googleusercontent.com")).build();
		
		System.out.println("RAW TOKEN: "+token);
		
		GoogleIdToken idToken;
		try {
			idToken = verifier.verify(token);
			System.out.println("IDTOKEN: "+idToken);
			if(idToken!=null) {
				Payload payload = idToken.getPayload();
				String userId = payload.getSubject();
				System.out.println("User ID: "+userId);
				String email = (String) payload.get("email");
				System.out.println(email);
				User u = new User();
				u.setEmail(email);
				u.setToken(userId);
				Date d = new Date();
				u.setStamp(new Timestamp(d.getTime()));
				StoreData.getInstance().getUserDAO().createUser(u);
			}
			else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} catch (GeneralSecurityException e) {
			
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (IOException e) {
			
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.CREATED).build();
		
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/user/verify")
	private Response test(@Context HttpServletRequest request) {
		 Authentication auth = new Authentication();
		 String answer = auth.getRequestToGoole(request);
		return Response.ok(answer).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{id}")
	public Response deleteUser(@PathParam("id") String email, @HeaderParam("token") String token) {
		User u = new User();
		u.setEmail(email);
		u.setToken(token);
		boolean success = StoreData.getInstance().getUserDAO().deleteUser(u);
		
		if(success) {
			return Response.ok().build();
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	
}