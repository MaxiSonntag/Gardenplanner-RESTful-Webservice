package model.nutrition.search;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class NutritionDataListItems {
	
	@SerializedName("item")
	private ArrayList<NutritionDataListItem> itemsList;
	
	public ArrayList<NutritionDataListItem> getItemsList() {
		return itemsList;
	}

	public void setItemsList(ArrayList<NutritionDataListItem> itemsList) {
		this.itemsList = itemsList;
	}

	@Override
	public String toString() {
		return "NutritionDataListItems [itemsList=" + itemsList + "]";
	}
	
	
	

}
