package model.nutrition.data;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class NutritionDataFoodList {
	
	@SerializedName("foods")
	private ArrayList<NutritionDataNutrientsList> nutrients;

	public ArrayList<NutritionDataNutrientsList> getNutrients() {
		return nutrients;
	}

	public void setNutrients(ArrayList<NutritionDataNutrientsList> nutrients) {
		this.nutrients = nutrients;
	}

	@Override
	public String toString() {
		return "NutritionDataFoodList [nutrients=" + nutrients + "]";
	}
	
	
	
	
	

}
