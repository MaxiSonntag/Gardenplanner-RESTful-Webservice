package model.nutrition.data;

import com.google.gson.annotations.SerializedName;

public class NutritionData {
	
	@SerializedName("report")
	private NutritionDataFoodList nutritionData;
	
	

	public NutritionDataFoodList getNutritionData() {
		return nutritionData;
	}

	public void setNutritionData(NutritionDataFoodList nutritionData) {
		this.nutritionData = nutritionData;
	}

	@Override
	public String toString() {
		return "NutritionData [nutritionData=" + nutritionData + "]";
	}

}
