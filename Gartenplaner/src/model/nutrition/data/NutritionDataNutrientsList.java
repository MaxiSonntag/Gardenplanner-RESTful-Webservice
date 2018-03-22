package model.nutrition.data;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class NutritionDataNutrientsList {
	@SerializedName("measure")
	private String measure;
	@SerializedName("name")
	private String name;
	@SerializedName("nutrients")
	private ArrayList<NutrientItem> nutrients;
	
	
	
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<NutrientItem> getNutrients() {
		return nutrients;
	}
	public void setNutrients(ArrayList<NutrientItem> nutrients) {
		this.nutrients = nutrients;
	}
	@Override
	public String toString() {
		return "NutritionDataFoodList [measure=" + measure + ", name=" + name + ", nutrients=" + nutrients + "]";
	}
	
	

}
