package model.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherRequest {
	
	@SerializedName("type")
	private String type;
	
	@SerializedName("query")
	private String query;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return "WeatherRequest [type=" + type + ", query=" + query + "]";
	}
	
	

}
