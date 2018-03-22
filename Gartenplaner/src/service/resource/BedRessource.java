package service.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.Hibernate;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import dbController.BedDAO;
import dbController.Beds;
import dbController.Crops;
import dbController.Location;
import dbController.LocationDAO;
import dbController.StoreData;
import dbController.User;
import dbController.Weather;
import service.resource.caching.ETagGenerator;

@Path("/user/{userId}")
public class BedRessource {

	@Context
	UriInfo uriInfo;

	@GET
	@Path("/beds")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListOfBedsForUser(@PathParam("userId") String email, @HeaderParam("token") String token, @Context UriInfo uriInfo1){

		if (!StoreData.getInstance().isTokenValid(token)) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		List<Beds> resultBeds = StoreData.getInstance().getBedDAO().queryBedsForUser(u);
		
		
		

		//#######################################
		
		resultBeds = resultBeds.stream().map(bed ->{
			bed = this.createHypermediaLinks(bed, bed.getUser());
			return bed;
		}).collect(Collectors.toList());
		
		//#######################################
		
		
		return Response.ok(resultBeds).build();

	}
	
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/beds")
	public Response createBedForUser(@PathParam("userId") String email, @HeaderParam("Token") String token, Beds bed) {

		if (!StoreData.getInstance().isTokenAndBedValid(token, bed)) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		bed.setUser(StoreData.getInstance().getUserDAO().getUser(u));
		boolean usesExistingLocation = false;

		List<Location> existingLocations = StoreData.getInstance().getLocationDAO().queryAllLocations();
		for (Location l : existingLocations) {
			if (l.getCity().equals(bed.getLocation().getCity()) && l.getCountry().equals(bed.getLocation().getCountry())
					&& l.getPostalCode().equals(bed.getLocation().getPostalCode())) {
				bed.setLocation(l);
				l.getBeds().add(bed);
				usesExistingLocation = true;
			}
		}
		
		ArrayList<Weather> weathers = new ArrayList<>();
		
		if (!usesExistingLocation) {
			Weather w = StoreData.getInstance().getLocationDAO().loadAndSaveWeatherDataForLocation(bed.getLocation());
			
			if (w.getSun_hours() != -1) {
				weathers.add(w);	
			}
			bed.getLocation().setWeather(weathers);
		}
		
		
		EntityTag eTag = new ETagGenerator().generateEtag(bed.hashCode() + bed.getId() + "");

		bed.seteTag(eTag.getValue());
		bed.setCrops(new ArrayList<Crops>());

		Beds result = (Beds) StoreData.getInstance().saveData(bed);

		//#############################################
		
		result = this.createHypermediaLinks(result, u);
		//#############################################
		
		
		CacheControl cc = new CacheControl();
		cc.setMaxAge(1000);

		ResponseBuilder rb = Response.created(result.getLinks().get(0).getHref());
		rb.entity(result);
		rb.cacheControl(cc);
		rb.tag(new EntityTag(result.geteTag()));

		return rb.build();

	}

	@GET
	@Path("/beds/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecificBed(@PathParam("userId") String email, @PathParam("id") String bedId,
			@HeaderParam("token") String token, @Context Request request) {

		if (!StoreData.getInstance().isTokenValid(token)) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		

		Beds bed = StoreData.getInstance().getBedDAO().queryBedById(u, bedId);

		if (!(bed.getId() == -1)) {
			if (!bed.getUser().getEmail().equals(email) || !bed.getUser().getToken().equals(token)) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		
		//#####################################################
		
		bed = this.createHypermediaLinks(bed, u);
		//#####################################################
		
		

		CacheControl cc = new CacheControl();
		cc.setMaxAge(1000);

		EntityTag eTag = new EntityTag(bed.geteTag());

		ResponseBuilder rb = request.evaluatePreconditions(eTag);
		if (rb != null) {
			return rb.cacheControl(cc).build();
		}

		rb = Response.ok(bed);
		rb.cacheControl(cc);
		rb.tag(new EntityTag(bed.geteTag()));

		return rb.build();

	}

	@PUT
	@Path("/beds/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSpecificBed(@PathParam("userId") String email, @PathParam("id") String bedId,
			@HeaderParam("token") String token, Beds bed, @Context Request request) {
		if (!StoreData.getInstance().isTokenAndBedValid(token, bed)) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}

		User u = new User();
		u.setEmail(email);
		u.setToken(token);

		if (!StoreData.getInstance().isUserValid(u)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		Beds dbBed = StoreData.getInstance().getBedDAO().queryBedById(u, bedId);

		if (!(dbBed.getId() == -1)) {
			if (!dbBed.getUser().getEmail().equals(email) || !dbBed.getUser().getToken().equals(token)) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		EntityTag eTag = new ETagGenerator().generateEtag(bed.hashCode() + bed.getId() + "");
		bed.seteTag(eTag.getValue());

		Beds bedNew = StoreData.getInstance().getBedDAO().updateBedById(bedId, bed);
		System.out.println(bedNew.toString());
		if(null == bedNew.getLocation().getWeather()){
			bedNew.getLocation().setWeather(new ArrayList<Weather>());
		}
		//################################
		
		bed = this.createHypermediaLinks(bedNew, u);
		//#################################

		CacheControl cc = new CacheControl();
		cc.setMaxAge(1000);

		ResponseBuilder rb = Response.ok(bed);
		rb.cacheControl(cc);
		rb.tag(eTag);

		return rb.build();

	}

	@DELETE
	@Path("/beds/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBed(@PathParam("userId") String email, @PathParam("id") String bedId,
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

		boolean successfull = StoreData.getInstance().getBedDAO().deleteBed(email, bedId);
		if (!successfull) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		

		return Response.ok().build();
	}

	// CROPS

	

	// WEATHER

	@GET
	@Path("/beds/{id}/weather")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWeatherForBed(@PathParam("userId") String email, @PathParam("id") String bedId,
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
		
		Beds bed = StoreData.getInstance().getBedDAO().queryBedById(u, bedId);
		ArrayList<Weather> weathers = new ArrayList<Weather>(bed.getLocation().getWeather());
		

		return Response.ok(weathers).build();

	}
	
	
	//Create JSON
	private URI createURI(Beds bed, String method, int parametersNeeded, boolean crop, User u) {
		UriBuilder uriBuilder;
		if(crop) {
			uriBuilder = uriInfo.getBaseUriBuilder().path(CropRessource.class).path(CropRessource.class, method);
		}else {
			uriBuilder = uriInfo.getBaseUriBuilder().path(BedRessource.class).path(BedRessource.class, method);
		}
		
		final URI classURI;
		switch(parametersNeeded) {
		case 2:{
			classURI = uriBuilder.build(u.getEmail(), bed.getId());
			break;
		}
		default:{
			classURI = uriBuilder.build(u.getEmail());
		}
		
		}
		return classURI;
	}
	
	//HypermediaLinks
	private Beds createHypermediaLinks(Beds bed, User u) {
		final URI selfUri = this.createURI(bed, "getSpecificBed", 2, false, u);
		bed.getLinks().add(new HypermediaLink("self", selfUri, "Bed representation", "GET"));
		
		final URI createCropUri = this.createURI(bed, "createCropForUser", 2, true, u);
		bed.getLinks().add(new HypermediaLink("next", createCropUri, "Create crop in bed", "POST"));
		
		final URI allBedsUri = this.createURI(bed, "getListOfBedsForUser", 1, false, u);
		bed.getLinks().add(new HypermediaLink("all", allBedsUri, "Show all beds for user", "GET"));
		
		final URI updateBedUri = this.createURI(bed, "updateSpecificBed", 2, false, u);
		bed.getLinks().add(new HypermediaLink("edit", updateBedUri, "Update this bed", "PUT"));
		
		final URI newBedUri = this.createURI(bed, "createBedForUser", 1, false, u);
		bed.getLinks().add(new HypermediaLink("all", newBedUri, "Create new bed", "POST"));

		final URI deleteBedUri = this.createURI(bed, "deleteBed", 2, false, u);
		bed.getLinks().add(new HypermediaLink("remove", deleteBedUri, "Delete this bed", "DELETE"));
		
		final URI showCropsUri = this.createURI(bed, "getCropsForUser", 2, true, u);
		bed.getLinks().add(new HypermediaLink("related", showCropsUri, "Show all crops in this bed", "GET"));
		
		return bed;
	}

	

	

}
