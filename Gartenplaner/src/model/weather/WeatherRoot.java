package model.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherRoot {
	
	@SerializedName("data")
	private WeatherData data;

	public WeatherData getData() {
		return data;
	}

	public void setData(WeatherData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "WeatherRoot [data=" + data + "]";
	}
	
	

}
