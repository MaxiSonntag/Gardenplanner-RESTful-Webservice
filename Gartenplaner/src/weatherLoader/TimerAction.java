package weatherLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import dbController.BedDAO;
import dbController.Beds;
import dbController.Location;
import dbController.StoreData;
import dbController.User;
import dbController.UserDAO;
import dbController.WeatherDAO;

public class TimerAction extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("TIMER CALLED");
		List<User> users = StoreData.getInstance().getUserDAO().queryAllUsers();
		
		List<Beds> beds = new ArrayList<Beds>();
		for(User u : users) {
			List<Beds> userBeds = StoreData.getInstance().getBedDAO().queryBedsForUser(u);
			for(Beds b : userBeds) {
				beds.add(b);
			}
		}
		
		List<Location> updatedLocations = new ArrayList<Location>();
		boolean couldLoadWeatherData = false;
		for(Beds b : beds) {
			if(!updatedLocations.contains(b.getLocation())) {
				couldLoadWeatherData=StoreData.getInstance().getWeatherDAO().updateWeatherForBed(b);
				if(couldLoadWeatherData) {
					updatedLocations.add(b.getLocation());
				}
				
			}
		}
	}

}
