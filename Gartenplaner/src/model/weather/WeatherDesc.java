package model.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherDesc {
	
	@SerializedName("value")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "WeatherDesc [value=" + value + "]";
	}
	
	

}
