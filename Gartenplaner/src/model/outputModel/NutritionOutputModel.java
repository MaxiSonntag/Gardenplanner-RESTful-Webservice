package model.outputModel;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import model.nutrition.data.NutrientItem;

public class NutritionOutputModel {
	
	@SerializedName("nutrients")
	ArrayList<NutrientItem> items;
	
	@SerializedName("measure")
	String measure;

	public NutritionOutputModel(ArrayList<NutrientItem> items, String measure) {
		super();
		this.items = items;
		this.measure = measure;
	}

	public NutritionOutputModel() {
		items = new ArrayList<>();
		measure = "";
	}

	public ArrayList<NutrientItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<NutrientItem> items) {
		this.items = items;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}
	
	

}
