package service.resource;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import model.Constants;
import model.recipes.Recipe;
import model.recipes.RecipeList;
import model.recipes.RecipeRoot;
import requests.APIRequest;
import service.resource.caching.HTTPCaching;

@Path("")
public class RecipeRessource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/recipes")
	public Response getRecipes(@QueryParam("q") String query){
		
		String queryName[] = {"q"};
		String queryValue[] = {query};
		String params[][] = {queryName, queryValue};
		
		APIRequest request = new APIRequest();
		String response = request.loadDataFromApi(Constants.REQUEST_RECIPELIST, params);
		
		RecipeList recipeList = new Gson().fromJson(response, RecipeList.class);
		
		return Response.ok(recipeList).expires(HTTPCaching.addDays(1)).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/recipe")
	public Response getRecipe(@QueryParam("id") String id){
		
		String queryName[] = {"rId"};
		String queryValue[] = {id};
		String params[][] = {queryName, queryValue};
		
		APIRequest request = new APIRequest();
		String response = request.loadDataFromApi(Constants.REQUEST_RECIPE, params);
		System.out.println(response);
		
		RecipeRoot recipeRoot = new Gson().fromJson(response, RecipeRoot.class);
		
		return Response.ok(recipeRoot).expires(HTTPCaching.addDays(1)).build();
	}

}
