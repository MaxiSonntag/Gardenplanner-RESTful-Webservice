package service.resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import authentication.Authentication;
import authentication.AuthenticationCallbackData;
import dbController.StoreData;
import model.Constants;
import model.crops.CropList;
import model.nutrition.data.NutritionData;
import model.nutrition.search.NutritionDataList;
import model.recipes.RecipeList;
import model.weather.WeatherRoot;
import requests.APIRequest;
import weatherLoader.WeatherData;

@Path("/planner")
public class GartenplanRessource {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAbout(@Context HttpServletRequest request){
		/*MyHttpRequest request = new MyHttpRequest();
		String response = request.loadDataFromInternet();
		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeAdapter(RecipeList.class, new RecipeListDeserializer());
		gson.registerTypeAdapter(RecipeListItem.class, new ListItemDeserializer());
		
		Gson gsonObj = gson.create();
		
		RecipeList list = gsonObj.fromJson(response, RecipeList.class);
		
		return "<h1>Gartenplaner</h1>" + 
					"<p>Jersey Intro Web Services is running" + "\n" +
		list.toString() + "/p>";*/
		
		APIRequest APIrequest = new APIRequest();
		GsonBuilder gson = new GsonBuilder();
		Gson gsonObj = gson.create();
//		String[] paramName = {"q"};
//		String[] paramValue = {"Lasagne"};
//		String[][] params = {paramName, paramValue};
		//String response = APIrequest.loadDataFromApi(Constants.REQUEST_RECIPELIST, params);
		//RecipeList list = gsonObj.fromJson(response, RecipeList.class);
		//return "<h1>RecipeList</h1>\n<p>"+list+"</p>";
		
//		Authentication auth = new Authentication();
//		String resp = auth.getRequestToGoole(request);
//		System.out.println(resp);
		
//		return resp;
		
//		//Crops information
//		String[] param1 = {"format", "q", "max", "offset"};
//		String[] param2 = {"json", "corn raw", "50", "0"};
//		String[] param1 = {"ndbno"};
//		String[] param2 = {"09003"};
		
		//Weather
		String[] param1 = {"q"};
		String[] param2 = {"Hof,Germany"};
		
//		//Recipelist
//		String[] param1 = {"q"};
//		String[] param2 = {"Lasagne"};
		
//		//Recipe
//		String[] param1 = {"rId"};
//		String[] param2 = {"0063b5"};
		
//		//Crops infos
//		String[] param1 = {"filter"};
//		String[] param2 = {"Tomato"};
//		
		String[][] params = {param1, param2};
		
//		String response = APIrequest.loadDataFromApi(Constants.REQUEST_NUTRITIONDATA, params);
//		String response = APIrequest.loadDataFromApi(Constants.REQUEST_NUTRITIONDATALIST, params);
		String response = APIrequest.loadDataFromApi(Constants.WEATHER_API, params);
//		String response = request.loadDataFromApi(Constants.REQUEST_RECIPELIST, params);
//		String response = APIrequest.loadDataFromApi(Constants.REQUEST_RECIPE, params);
//		String response = APIrequest.loadDataFromApi(Constants.CROPS_API, params);
//		NutritionDataList list = gsonObj.fromJson(response, NutritionDataList.class);
//		NutritionData nutDat = gsonObj.fromJson(response, NutritionData.class);
		
//		CropList cropList = gsonObj.fromJson(response, CropList.class);
		WeatherRoot weatherRoot = gsonObj.fromJson(response, WeatherRoot.class);
//		return weatherRoot.toString();
		//StoreData stro = StoreData.getInstance();
		//stro.saveData();#
		
		//WeatherData wd = new WeatherData();
		//wd.loadCurrentWeatherForAllLocations();
		return weatherRoot.toString();
	}

}
