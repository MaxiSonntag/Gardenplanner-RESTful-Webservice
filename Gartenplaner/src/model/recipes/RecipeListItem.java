package model.recipes;

import org.glassfish.jersey.server.Uri;

import com.google.gson.annotations.SerializedName;

public class RecipeListItem {

	public RecipeListItem() {
		super();
	}


	@SerializedName("recipe_id")
	private String recipe_id;
	@SerializedName("title")
	private String title;
	@SerializedName("image_url")
	private String image_url;
	@SerializedName("social_rank")
	private Float social_rank;
	
	
	public RecipeListItem(String recipeId, String title, String imgURL, Float socialRank) {
		super();
		this.recipe_id = recipeId;
		this.title = title;
		this.image_url = imgURL;
		this.social_rank = socialRank;
	}
	


	public String getRecipe_id() {
		return recipe_id;
	}



	public void setRecipe_id(String recipe_id) {
		this.recipe_id = recipe_id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getImage_url() {
		return image_url;
	}



	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}



	public Float getSocial_rank() {
		return social_rank;
	}



	public void setSocial_rank(Float social_rank) {
		this.social_rank = social_rank;
	}



	@Override
	public String toString() {
		return "RecipeListItem [recipeId=" + recipe_id + ", title=" + title + ", imgURL=" + image_url + ", socialRank="
				+ social_rank + "]";
	}
	
	
	
	
}
