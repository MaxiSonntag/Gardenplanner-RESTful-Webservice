package model.weather;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class CurrentCondition {
	
	@SerializedName("weatherCode")
	private String weatherCode;

	@SerializedName("weatherDesc")
	private ArrayList<WeatherDesc> weatherDesc ;
	
	@SerializedName("windspeedMiles")
	private String windspeedMiles;
	
	@SerializedName("windspeedKmph")
	private String windspeedKmph;
	
	@SerializedName("winddir16Point")
	private String winddir16Point;
	
	@SerializedName("precipMM")
	private String rainfall;
	
	@SerializedName("humidity")
	private String humidity;
	
	@SerializedName("pressure")
	private String pressure;
	
	@SerializedName("cloudcover")
	private String cloudcover;
	
	@SerializedName("temp_C")
	private String temperature;

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getWeatherCode() {
		return weatherCode;
	}

	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}

	public ArrayList<WeatherDesc> getWeatherDesc() {
		return weatherDesc;
	}

	public void setWeatherDesc(ArrayList<WeatherDesc> weatherDesc) {
		this.weatherDesc = weatherDesc;
	}

	public String getWindspeedMiles() {
		return windspeedMiles;
	}

	public void setWindspeedMiles(String windspeedMiles) {
		this.windspeedMiles = windspeedMiles;
	}

	public String getWindspeedKmph() {
		return windspeedKmph;
	}

	public void setWindspeedKmph(String windspeedKmph) {
		this.windspeedKmph = windspeedKmph;
	}

	public String getWinddir16Point() {
		return winddir16Point;
	}

	public void setWinddir16Point(String winddir16Point) {
		this.winddir16Point = winddir16Point;
	}

	public String getRainfall() {
		return rainfall;
	}

	public void setRainfall(String rainfall) {
		this.rainfall = rainfall;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getCloudcover() {
		return cloudcover;
	}

	public void setCloudcover(String cloudcover) {
		this.cloudcover = cloudcover;
	}

	@Override
	public String toString() {
		return "CurrentCondition [weatherCode=" + weatherCode + ", weatherDesc=" + weatherDesc + ", windspeedMiles="
				+ windspeedMiles + ", windspeedKmph=" + windspeedKmph + ", winddir16Point=" + winddir16Point
				+ ", rainfall=" + rainfall + ", humidity=" + humidity + ", pressure=" + pressure + ", cloudcover="
				+ cloudcover + ", temperature=" + temperature + "]";
	}

	
	
	

	
}
