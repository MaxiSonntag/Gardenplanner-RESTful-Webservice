package model.recipes;

import com.google.gson.annotations.SerializedName;

public class RecipeRoot {
	@SerializedName("recipe")
	Recipe recipe;

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public RecipeRoot() {
		super();
	}
	
	

}
