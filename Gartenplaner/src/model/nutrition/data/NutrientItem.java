package model.nutrition.data;

import com.google.gson.annotations.SerializedName;

public class NutrientItem {
	
	@SerializedName("nutrient")
	private String nutrient;
	
	@SerializedName("unit")
	private String unit;
	
	@SerializedName("value")
	private String value;
	
	

	public String getNutrient() {
		return nutrient;
	}

	public void setNutrient(String nutrient) {
		this.nutrient = nutrient;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "NutrientItem [nutrient=" + nutrient + ", unit=" + unit + ", value=" + value + "]";
	}
	
	
	
	

}
