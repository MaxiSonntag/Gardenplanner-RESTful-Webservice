package model.recipes;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class RecipeList {
	@SerializedName("recipes")
	private ArrayList<RecipeListItem> recipeList = new ArrayList<RecipeListItem>();

	public ArrayList<RecipeListItem> getRecipeList() {
		return recipeList;
	}

	public void setRecipeList(ArrayList<RecipeListItem> recipeList) {
		this.recipeList = recipeList;
	}

	@Override
	public String toString() {
		return "RecipeList [recipeList=" + recipeList + "]";
	}

	public RecipeList() {
		super();
	}
	
	

}
