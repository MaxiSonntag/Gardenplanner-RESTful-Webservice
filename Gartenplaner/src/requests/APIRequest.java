package requests; 

import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import model.Constants;
import model.crops.CropList;

import javax.servlet.http.*;

import org.glassfish.jersey.client.ClientConfig;

public class APIRequest {
	
	public APIRequest(){}
	
	public String loadDataFromApi(String target, String[][] params){
		String api = "";
		if(target.equals(Constants.REQUEST_RECIPE)  || target.equals(Constants.REQUEST_RECIPELIST))
        	api = Constants.FOOD2FORK_API;
		else if(target.equals(Constants.REQUEST_NUTRITIONDATALIST) || target.equals(Constants.REQUEST_NUTRITIONDATA))
			api = Constants.CROPS_INFORMATION_API;
		else
        	api = target;
		
		
		
		
		URI baseUri = getBaseUriForRequest(api);
		
		ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget webTarget = client.target(baseUri);
        
        String response = "No response received";
        
		switch(api){
		case Constants.FOOD2FORK_API:{
			String path = "";
			if(target == Constants.REQUEST_RECIPELIST)
				path = "search";
			else
				path = "get";
			
			response = webTarget.
					path("api").
					path(path).
                    queryParam("key", Constants.FOOD2FORK_API_KEY).
                    queryParam(params[0][0] ,params[1][0]).
                    request().
                    accept(MediaType.TEXT_XML).
                    get(String.class).
                    toString();
    		break;
		}
		
		case Constants.WEATHER_API:{
			
			response = webTarget.queryParam(params[0][0], params[1][0]).
					queryParam("key", Constants.WEATHER_API_KEY).
					queryParam("format", "JSON").
					request().
					accept(MediaType.APPLICATION_JSON).
					get(String.class).
					toString();
			
			break;
		}
		
		case Constants.CROPS_INFORMATION_API:{
//			webTarget.path("ndb").path("search/");
//			for(int i = 0; i<params.length; i++){
//				webTarget.queryParam(params[0][i], params[1][i]);
//			}
//			webTarget.queryParam("api_key", Constants.CROPS_API_KEY);
			if(target.equals(Constants.REQUEST_NUTRITIONDATALIST)){
				response = webTarget.
						path("ndb").
						path("search").
						queryParam(params[0][0],params[1][0]).
						queryParam(params[0][1], params[1][1]).
						queryParam(params[0][2], params[1][2]).
						queryParam(params[0][3], params[1][3]).
						queryParam("api_key", Constants.CROPS_INFORMATION_API_KEY).
						request().
						accept(MediaType.TEXT_XML).
						get(String.class).
						toString();
				break;
			}
			else{
				response = webTarget
						.path("ndb")
						.path("nutrients")
						.queryParam("format", "json")
						.queryParam("api_key", Constants.CROPS_INFORMATION_API_KEY)
						.queryParam("nutrients", "205").queryParam("nutrients", "204")
						.queryParam("nutrients", "208")
						.queryParam(params[0][0], params[1][0])
						.request()
						.accept(MediaType.APPLICATION_JSON)
						.get(String.class);
				break;
			}
		}
		case Constants.CROPS_API:{
			response = webTarget
					.path("crops")
					.queryParam(params[0][0], params[1][0])
					.request()
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			break;
		}
		case Constants.AUTHENTICATION_API:{
			
			response = webTarget.
					path("o").
					path("oauth2").
					path("v2").
					path("auth").
					queryParam(params[0][0], params[1][0]).
					queryParam(params[0][1], params[1][1]).
					queryParam(params[0][2], params[1][2]).
					queryParam(params[0][3], params[1][3]).
					queryParam(params[0][4], params[1][4]).
					queryParam(params[0][5], params[1][5]).
					queryParam(params[0][6], params[1][6]).
					queryParam(params[0][7], params[1][7]).
					request().
					accept(MediaType.TEXT_HTML).
					get(String.class).
					toString();
			break;
		}
		}
		return response;
	}
	
	public CropList downloadCropList(String api, String[][] params){
		URI baseUri = getBaseUriForRequest(api);
		
		ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget webTarget = client.target(baseUri);
        		
		CropList list = webTarget
				.path("crops")
				.queryParam(params[0][0], params[1][0])
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(CropList.class);
		return list;
	}

	private URI getBaseUriForRequest(String target) {
		// TODO Auto-generated method stub
		switch (target){
		case Constants.FOOD2FORK_API:{
			return UriBuilder.fromUri("https://www.food2fork.com").build();
		}
		case Constants.WEATHER_API:{
			return UriBuilder.fromUri("https://api.worldweatheronline.com/premium/v1/weather.ashx").build();
		}
		case Constants.CROPS_INFORMATION_API:{
			return UriBuilder.fromUri("https://api.nal.usda.gov").build();
		}
		case Constants.AUTHENTICATION_API:{
			return UriBuilder.fromUri("https://accounts.google.com").build();
		}
		case Constants.CROPS_API:{
			return UriBuilder.fromUri("https://openfarm.cc/api/v1/").build();
		}
		}
		return null;
	}
}