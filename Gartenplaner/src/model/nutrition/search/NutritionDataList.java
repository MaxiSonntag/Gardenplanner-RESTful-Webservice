package model.nutrition.search;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class NutritionDataList {
	
	@SerializedName("list")
	private NutritionDataListItems items;

	public NutritionDataListItems getItems() {
		return items;
	}

	public void setItems(NutritionDataListItems items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "NutritionDataList [items=" + items + "]";
	}
	
	

}
