package model.weather;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Weather {
	
	@SerializedName("date")
	private String date;

	@SerializedName("maxtempC")
	private String maxtempC;
	
	@SerializedName("maxtempF")
	private String maxtempF;
	
	@SerializedName("mintempC")
	private String mintempC;
	
	@SerializedName("mintempF")
	private String mintempF;
	
	@SerializedName("totalSnow_cm")
	private String totalSnowCm;
	
	@SerializedName("sunHour")
	private String sunHour;
	
	@SerializedName("uvIndex")
	private String uvIndex;

	@Override
	public String toString() {
		return "Weather [date=" + date + ", maxtempC=" + maxtempC + ", maxtempF=" + maxtempF + ", mintempC=" + mintempC
				+ ", mintempF=" + mintempF + ", totalSnowCm=" + totalSnowCm + ", sunHour=" + sunHour + ", uvIndex="
				+ uvIndex + "]";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMaxtempC() {
		return maxtempC;
	}

	public void setMaxtempC(String maxtempC) {
		this.maxtempC = maxtempC;
	}

	public String getMaxtempF() {
		return maxtempF;
	}

	public void setMaxtempF(String maxtempF) {
		this.maxtempF = maxtempF;
	}

	public String getMintempC() {
		return mintempC;
	}

	public void setMintempC(String mintempC) {
		this.mintempC = mintempC;
	}

	public String getMintempF() {
		return mintempF;
	}

	public void setMintempF(String mintempF) {
		this.mintempF = mintempF;
	}

	public String getTotalSnowCm() {
		return totalSnowCm;
	}

	public void setTotalSnowCm(String totalSnowCm) {
		this.totalSnowCm = totalSnowCm;
	}

	public String getSunHour() {
		return sunHour;
	}

	public void setSunHour(String sunHour) {
		this.sunHour = sunHour;
	}

	public String getUvIndex() {
		return uvIndex;
	}

	public void setUvIndex(String uvIndex) {
		this.uvIndex = uvIndex;
	}
}
