package org.copesa.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class DateUtils {
	public static Timestamp today()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);						
		Timestamp instant = new Timestamp(calendar.getTimeInMillis());
		return instant;

	}

	public static Timestamp now()
	{
		Calendar calendar = Calendar.getInstance();
		Timestamp instant = new Timestamp(calendar.getTimeInMillis());
		return instant;
	}

	public static Timestamp nextDay( Timestamp _day )
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(_day.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);						
		calendar.add(Calendar.DATE, 1);							
		return new Timestamp(calendar.getTimeInMillis());
	}
	
	
	public static Timestamp veryDistantDate()
	{
		Calendar calendar = new GregorianCalendar(3022,0,1);
		return new Timestamp(calendar.getTimeInMillis());
	}

	
	public static long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	
}
