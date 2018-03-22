package weatherLoader;

import java.util.List;

import dbController.Beds;
import dbController.Location;
import dbController.StoreData;
import dbController.User;
import dbController.Weather;

public class WeatherData {
	
	Weather w;
	
	public WeatherData() {
		
	}

	public Weather getW() {
		return w;
	}

	public void setW(Weather w) {
		this.w = w;
	}
	
	
	/*public void loadCurrentWeatherForAllLocations() {
		List<Location> locations = StoreData.getInstance().queryLocations();
		System.out.println("Locations Count: "+locations.size());
		Weather w = new Weather();
		for(Location l : locations) {
			w = StoreData.getInstance().loadAndSaveWeatherDataForLocation(l);
		}
		User u = new User();
		u.setEmail("maxi@sonntags.net");
		u.setToken("Test123");
		Beds b = StoreData.getInstance().queryBedById(u, "2");
		System.out.println("BED 2 WEATHER: "+b.getLocation().getWeather().size());
	}*/

}
