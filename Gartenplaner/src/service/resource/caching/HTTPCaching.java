package service.resource.caching;

import java.util.Calendar;
import java.util.Date;

public abstract class HTTPCaching {

	private static Date add(int amount, int type) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(type, amount);

		return c.getTime();
	}

	public static Date addMinutes(int amount) {
		return add(amount, Calendar.MINUTE);
	}

	public static Date addHours(int amount) {
		return add(amount, Calendar.HOUR);
	}

	public static Date addDays(int amount) {
		return add(amount, Calendar.DATE);
	}

}
