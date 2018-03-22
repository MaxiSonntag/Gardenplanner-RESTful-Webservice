package model.weather;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
	
	@SerializedName("request")
	private ArrayList<WeatherRequest> request;
	
	@SerializedName("current_condition")
	private ArrayList<CurrentCondition> currentCondition;
	
	@SerializedName("weather")
	private ArrayList<Weather> weather;

	public ArrayList<WeatherRequest> getRequest() {
		return request;
	}

	public void setRequest(ArrayList<WeatherRequest> request) {
		this.request = request;
	}

	public ArrayList<CurrentCondition> getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(ArrayList<CurrentCondition> currentCondition) {
		this.currentCondition = currentCondition;
	}

	public ArrayList<Weather> getWeather() {
		return weather;
	}

	public void setWeather(ArrayList<Weather> weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "WeatherData [request=" + request + ", currentCondition=" + currentCondition + ", weather=" + weather
				+ "]";
	}
	
	public boolean isValid() {
		if(this.getCurrentCondition() != null && this.weather != null && !this.getCurrentCondition().isEmpty() && !this.getWeather().isEmpty()) {
			if(null != this.getCurrentCondition().get(0).getTemperature() && !this.getCurrentCondition().get(0).getTemperature().isEmpty()) {
				if(null != this.getWeather().get(0) && null != this.getWeather().get(0).getSunHour() && !this.getWeather().get(0).getSunHour().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}
	
	

}
