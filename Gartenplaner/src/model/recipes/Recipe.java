package model.recipes;

import java.util.ArrayList;

import org.glassfish.jersey.server.Uri;

import com.google.gson.annotations.SerializedName;


public class Recipe {
	@SerializedName("publisher")
	private String publisher;
	@SerializedName("title")
	private String title;
	@SerializedName("ingredients")
	private ArrayList<String> ingredients;
	@SerializedName("image_url")
	private String image_url;
	@SerializedName("source_url")
	private String source_url;
	@SerializedName("social_rank")
	private Float social_rank;
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	public Float getSocial_rank() {
		return social_rank;
	}
	public void setSocial_rank(Float social_rank) {
		this.social_rank = social_rank;
	}
	public Recipe() {
		super();
	}
	
	
	
	

}
