package service.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.gson.Gson;

import dbController.CropDAO;
import dbController.Crops;
import dbController.StoreData;
import dbController.User;
import model.Constants;
import model.crops.Crop;
import model.crops.CropList;
import model.nutrition.data.NutritionData;
import model.nutrition.search.NutritionDataList;
import model.outputModel.CropOutputModel;
import model.outputModel.NutritionOutputModel;
import requests.APIRequest;

@Path("")
@JsonAutoDetect
public class CropRessource {

	@Context
	UriInfo uriInfo;

	@GET
	@Path("/user/{userId}/beds/{id}/crops")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCropsForUser(@PathParam("userId") String email, @PathParam("id") String bedId,
			@HeaderParam("token") String token) {
		if (!StoreData.getInstance().isTokenValid(token)) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		List<Crops> crops = StoreData.getInstance().getCropDAO().getAllCrops(u, Integer.parseInt(bedId));

		return Response.ok(crops).build();
	}

	@POST
	@Path("/user/{userId}/beds/{id}/crops")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCropForUser(@PathParam("userId") String email, @PathParam("id") String bedId,
			@HeaderParam("Token") String token, Crops crop) {

		if (!StoreData.getInstance().isTokenValid(token)) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		Crops aCrop = StoreData.getInstance().getCropDAO().createCrop(crop, Integer.parseInt(bedId), u);

		if (aCrop.getId() == -1) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		final URI location = uriInfo.getBaseUriBuilder().path(BedRessource.class)
				.path(CropRessource.class, "getCropById").build(email, aCrop.getBeds().getId(), aCrop.getId());
		

		return Response.created(location).entity(aCrop).build();
	}

	@DELETE
	@Path("/user/{userId}/beds/{id}/crops")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAllCropsFromBed(@PathParam("userId") String email, @PathParam("id") String bedId,
			@HeaderParam("Token") String token) {

		if (!StoreData.getInstance().isTokenValid(token)) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		boolean successful = StoreData.getInstance().getCropDAO().deleteAllCropsInBed(Integer.parseInt(bedId), u);

		if (!successful) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok().build();
	}

	@GET
	@Path("/user/{userId}/beds/{id}/crops/{cropId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCropById(@PathParam("userId") String email, @PathParam("id") String bedId,
			@PathParam("cropId") String cropId, @HeaderParam("token") String token) {
		if (!StoreData.getInstance().isTokenValid(token)) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		Crops c = StoreData.getInstance().getCropDAO().getCropForId(Integer.parseInt(bedId), Integer.parseInt(cropId),
				u);

		if (c.getId() == -1) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok(c).build();

	}

	@DELETE
	@Path("/user/{userId}/beds/{id}/crops/{cropId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCropById(@PathParam("userId") String email, @PathParam("id") String bedId,
			@PathParam("cropId") String cropId, @HeaderParam("token") String token) {
		if (!StoreData.getInstance().isTokenValid(token)) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		boolean successful = StoreData.getInstance().getCropDAO().deleteCropById(Integer.parseInt(bedId),
				Integer.parseInt(cropId), u);
		if (!successful) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok().build();
	}

	@PUT
	@Path("/user/{userId}/beds/{id}/crops/{cropId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCrop(@PathParam("userId") String email, @PathParam("id") String bedId,
			@PathParam("cropId") String cropId, @HeaderParam("token") String token, Crops c) {
		if (!StoreData.getInstance().isTokenValid(token)) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		Crops saved = StoreData.getInstance().getCropDAO().updateCrop(Integer.parseInt(bedId), Integer.parseInt(cropId),
				u, c);

		if (saved.getId() == -1) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok(saved).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/crop/{cropName}")
	public Response getCrop(@PathParam("cropName") String cropQuery) {
		String queryName[] = { "filter" };
		String queryValue[] = { cropQuery };
		String params[][] = { queryName, queryValue };

		APIRequest request = new APIRequest();
		String response = request.loadDataFromApi(Constants.CROPS_API, params);

		CropList list = new Gson().fromJson(response, CropList.class);

		Crop crop = list.getListItem().get(0).getCrop();

		CropOutputModel output = new CropOutputModel(crop.getName(), crop.getDescription(), crop.getSunRequirements(),
				crop.getSowingMethod(), crop.getHeight(), crop.getImageUrl());

		return Response.ok(output).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/crop/{cropName}/nutrition")
	public Response getNutrition(@PathParam("cropName") String cropName) {
		String queryName[] = { "format", "q", "max", "offset" };
		String queryValue[] = { "json", "raw " + cropName, "50", "0" };
		String params[][] = { queryName, queryValue };

		APIRequest request = new APIRequest();

		String response = request.loadDataFromApi(Constants.REQUEST_NUTRITIONDATALIST, params);
		NutritionDataList nutDataList = new Gson().fromJson(response, NutritionDataList.class);

		String id = nutDataList.getItems().getItemsList().get(0).getId();

		String queryName2[] = { "ndbno" };
		String queryValue2[] = { id };
		String params2[][] = { queryName2, queryValue2 };

		String response2 = request.loadDataFromApi(Constants.REQUEST_NUTRITIONDATA, params2);
		NutritionData item = new Gson().fromJson(response2, NutritionData.class);

		NutritionOutputModel output;
		if (!item.getNutritionData().getNutrients().isEmpty()) {
			output = new NutritionOutputModel(item.getNutritionData().getNutrients().get(0).getNutrients(),
					item.getNutritionData().getNutrients().get(0).getMeasure());

		} else {
			output = new NutritionOutputModel();
		}
		return Response.ok(output).build();

	}

}
