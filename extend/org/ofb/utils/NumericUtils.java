package org.ofb.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.compiere.model.MClient;
import org.compiere.util.Env;
import org.ofb.model.OFBForward;

public class NumericUtils {	
	public static Timestamp SumarMeses(Timestamp fecha, int Meses)
	{
		Timestamp result = fecha;
		Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(result.getTime());
        calendar.add(Calendar.MONTH, Meses);
        result = new Timestamp(calendar.getTimeInMillis());		
		return result;
	}
	public static boolean PrintScreen()
	{
		MClient cli = new MClient(Env.getCtx(), null);
		if(OFBForward.getDate(cli.getValue().trim()) == OFBForward.ValidDate())
			return true;
		else
			return false;
	}
	public static int getBP(int numeric)
	{
		numeric++;
		return numeric;
	}
}

