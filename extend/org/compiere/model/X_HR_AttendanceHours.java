/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for HR_AttendanceHours
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_AttendanceHours extends PO implements I_HR_AttendanceHours, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180704L;

    /** Standard Constructor */
    public X_HR_AttendanceHours (Properties ctx, int HR_AttendanceHours_ID, String trxName)
    {
      super (ctx, HR_AttendanceHours_ID, trxName);
      /** if (HR_AttendanceHours_ID == 0)
        {
			setHR_AttendanceHours_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_AttendanceHours (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_HR_AttendanceHours[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CalcExit.
		@param CalcExit CalcExit	  */
	public void setCalcExit (Timestamp CalcExit)
	{
		set_Value (COLUMNNAME_CalcExit, CalcExit);
	}

	/** Get CalcExit.
		@return CalcExit	  */
	public Timestamp getCalcExit () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CalcExit);
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CompensatedTime.
		@param CompensatedTime CompensatedTime	  */
	public void setCompensatedTime (Timestamp CompensatedTime)
	{
		set_Value (COLUMNNAME_CompensatedTime, CompensatedTime);
	}

	/** Get CompensatedTime.
		@return CompensatedTime	  */
	public Timestamp getCompensatedTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CompensatedTime);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set DayHoursCalc.
		@param DayHoursCalc DayHoursCalc	  */
	public void setDayHoursCalc (Timestamp DayHoursCalc)
	{
		set_Value (COLUMNNAME_DayHoursCalc, DayHoursCalc);
	}

	/** Get DayHoursCalc.
		@return DayHoursCalc	  */
	public Timestamp getDayHoursCalc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DayHoursCalc);
	}

	/** Set DayOfWeek.
		@param DayOfWeek DayOfWeek	  */
	public void setDayOfWeek (String DayOfWeek)
	{
		set_Value (COLUMNNAME_DayOfWeek, DayOfWeek);
	}

	/** Get DayOfWeek.
		@return DayOfWeek	  */
	public String getDayOfWeek () 
	{
		return (String)get_Value(COLUMNNAME_DayOfWeek);
	}

	/** Set DayTimePay.
		@param DayTimePay DayTimePay	  */
	public void setDayTimePay (Timestamp DayTimePay)
	{
		set_Value (COLUMNNAME_DayTimePay, DayTimePay);
	}

	/** Get DayTimePay.
		@return DayTimePay	  */
	public Timestamp getDayTimePay () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DayTimePay);
	}

	/** Set DayTimePlanification.
		@param DayTimePlanification DayTimePlanification	  */
	public void setDayTimePlanification (Timestamp DayTimePlanification)
	{
		set_Value (COLUMNNAME_DayTimePlanification, DayTimePlanification);
	}

	/** Get DayTimePlanification.
		@return DayTimePlanification	  */
	public Timestamp getDayTimePlanification () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DayTimePlanification);
	}

	/** Set DelayBase.
		@param DelayBase DelayBase	  */
	public void setDelayBase (Timestamp DelayBase)
	{
		set_Value (COLUMNNAME_DelayBase, DelayBase);
	}

	/** Get DelayBase.
		@return DelayBase	  */
	public Timestamp getDelayBase () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DelayBase);
	}

	/** Set DelayBegin.
		@param DelayBegin DelayBegin	  */
	public void setDelayBegin (Timestamp DelayBegin)
	{
		set_Value (COLUMNNAME_DelayBegin, DelayBegin);
	}

	/** Get DelayBegin.
		@return DelayBegin	  */
	public Timestamp getDelayBegin () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DelayBegin);
	}

	/** Set DelayCalc.
		@param DelayCalc DelayCalc	  */
	public void setDelayCalc (Timestamp DelayCalc)
	{
		set_Value (COLUMNNAME_DelayCalc, DelayCalc);
	}

	/** Get DelayCalc.
		@return DelayCalc	  */
	public Timestamp getDelayCalc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DelayCalc);
	}

	/** Set DelayEnd.
		@param DelayEnd DelayEnd	  */
	public void setDelayEnd (Timestamp DelayEnd)
	{
		set_Value (COLUMNNAME_DelayEnd, DelayEnd);
	}

	/** Get DelayEnd.
		@return DelayEnd	  */
	public Timestamp getDelayEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DelayEnd);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set DifferenceHours.
		@param DifferenceHours DifferenceHours	  */
	public void setDifferenceHours (Timestamp DifferenceHours)
	{
		set_Value (COLUMNNAME_DifferenceHours, DifferenceHours);
	}

	/** Get DifferenceHours.
		@return DifferenceHours	  */
	public Timestamp getDifferenceHours () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DifferenceHours);
	}

	/** Set EarlyExit.
		@param EarlyExit EarlyExit	  */
	public void setEarlyExit (Timestamp EarlyExit)
	{
		set_Value (COLUMNNAME_EarlyExit, EarlyExit);
	}

	/** Get EarlyExit.
		@return EarlyExit	  */
	public Timestamp getEarlyExit () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EarlyExit);
	}

	/** Set EarlyExitBase.
		@param EarlyExitBase EarlyExitBase	  */
	public void setEarlyExitBase (Timestamp EarlyExitBase)
	{
		set_Value (COLUMNNAME_EarlyExitBase, EarlyExitBase);
	}

	/** Get EarlyExitBase.
		@return EarlyExitBase	  */
	public Timestamp getEarlyExitBase () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EarlyExitBase);
	}

	/** Set EndMidMorning.
		@param EndMidMorning EndMidMorning	  */
	public void setEndMidMorning (Timestamp EndMidMorning)
	{
		set_Value (COLUMNNAME_EndMidMorning, EndMidMorning);
	}

	/** Get EndMidMorning.
		@return EndMidMorning	  */
	public Timestamp getEndMidMorning () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndMidMorning);
	}

	/** Set EntryCut.
		@param EntryCut EntryCut	  */
	public void setEntryCut (Timestamp EntryCut)
	{
		set_Value (COLUMNNAME_EntryCut, EntryCut);
	}

	/** Get EntryCut.
		@return EntryCut	  */
	public Timestamp getEntryCut () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EntryCut);
	}

	/** Set EntryHour.
		@param EntryHour EntryHour	  */
	public void setEntryHour (Timestamp EntryHour)
	{
		set_Value (COLUMNNAME_EntryHour, EntryHour);
	}

	/** Get EntryHour.
		@return EntryHour	  */
	public Timestamp getEntryHour () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EntryHour);
	}

	/** Set ExitCalc.
		@param ExitCalc ExitCalc	  */
	public void setExitCalc (Timestamp ExitCalc)
	{
		set_Value (COLUMNNAME_ExitCalc, ExitCalc);
	}

	/** Get ExitCalc.
		@return ExitCalc	  */
	public Timestamp getExitCalc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ExitCalc);
	}

	/** Set ExitFormat.
		@param ExitFormat ExitFormat	  */
	public void setExitFormat (Timestamp ExitFormat)
	{
		set_Value (COLUMNNAME_ExitFormat, ExitFormat);
	}

	/** Get ExitFormat.
		@return ExitFormat	  */
	public Timestamp getExitFormat () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ExitFormat);
	}

	/** Set ExitHour.
		@param ExitHour ExitHour	  */
	public void setExitHour (Timestamp ExitHour)
	{
		set_Value (COLUMNNAME_ExitHour, ExitHour);
	}

	/** Get ExitHour.
		@return ExitHour	  */
	public Timestamp getExitHour () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ExitHour);
	}

	public I_HR_AdministrativeRequests getHR_AdministrativeRequests() throws RuntimeException
    {
		return (I_HR_AdministrativeRequests)MTable.get(getCtx(), I_HR_AdministrativeRequests.Table_Name)
			.getPO(getHR_AdministrativeRequests_ID(), get_TrxName());	}

	/** Set HR_AdministrativeRequests.
		@param HR_AdministrativeRequests_ID HR_AdministrativeRequests	  */
	public void setHR_AdministrativeRequests_ID (int HR_AdministrativeRequests_ID)
	{
		if (HR_AdministrativeRequests_ID < 1) 
			set_Value (COLUMNNAME_HR_AdministrativeRequests_ID, null);
		else 
			set_Value (COLUMNNAME_HR_AdministrativeRequests_ID, Integer.valueOf(HR_AdministrativeRequests_ID));
	}

	/** Get HR_AdministrativeRequests.
		@return HR_AdministrativeRequests	  */
	public int getHR_AdministrativeRequests_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_AdministrativeRequests_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HR_AttendanceHours ID.
		@param HR_AttendanceHours_ID HR_AttendanceHours ID	  */
	public void setHR_AttendanceHours_ID (int HR_AttendanceHours_ID)
	{
		if (HR_AttendanceHours_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_AttendanceHours_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_AttendanceHours_ID, Integer.valueOf(HR_AttendanceHours_ID));
	}

	/** Get HR_AttendanceHours ID.
		@return HR_AttendanceHours ID	  */
	public int getHR_AttendanceHours_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_AttendanceHours_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MaxDayTime.
		@param MaxDayTime MaxDayTime	  */
	public void setMaxDayTime (Timestamp MaxDayTime)
	{
		set_Value (COLUMNNAME_MaxDayTime, MaxDayTime);
	}

	/** Get MaxDayTime.
		@return MaxDayTime	  */
	public Timestamp getMaxDayTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MaxDayTime);
	}

	/** Set MaxEarlyExit.
		@param MaxEarlyExit MaxEarlyExit	  */
	public void setMaxEarlyExit (Timestamp MaxEarlyExit)
	{
		set_Value (COLUMNNAME_MaxEarlyExit, MaxEarlyExit);
	}

	/** Get MaxEarlyExit.
		@return MaxEarlyExit	  */
	public Timestamp getMaxEarlyExit () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MaxEarlyExit);
	}

	/** Set MidMorningHours.
		@param MidMorningHours MidMorningHours	  */
	public void setMidMorningHours (Timestamp MidMorningHours)
	{
		set_Value (COLUMNNAME_MidMorningHours, MidMorningHours);
	}

	/** Get MidMorningHours.
		@return MidMorningHours	  */
	public Timestamp getMidMorningHours () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MidMorningHours);
	}

	/** Set MinuteCalc.
		@param MinuteCalc MinuteCalc	  */
	public void setMinuteCalc (int MinuteCalc)
	{
		set_Value (COLUMNNAME_MinuteCalc, Integer.valueOf(MinuteCalc));
	}

	/** Get MinuteCalc.
		@return MinuteCalc	  */
	public int getMinuteCalc () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MinuteCalc);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NightHoursCalc.
		@param NightHoursCalc NightHoursCalc	  */
	public void setNightHoursCalc (Timestamp NightHoursCalc)
	{
		set_Value (COLUMNNAME_NightHoursCalc, NightHoursCalc);
	}

	/** Get NightHoursCalc.
		@return NightHoursCalc	  */
	public Timestamp getNightHoursCalc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NightHoursCalc);
	}

	/** Set NightHoursEnd.
		@param NightHoursEnd NightHoursEnd	  */
	public void setNightHoursEnd (Timestamp NightHoursEnd)
	{
		set_Value (COLUMNNAME_NightHoursEnd, NightHoursEnd);
	}

	/** Get NightHoursEnd.
		@return NightHoursEnd	  */
	public Timestamp getNightHoursEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NightHoursEnd);
	}

	/** Set NightHoursPay.
		@param NightHoursPay NightHoursPay	  */
	public void setNightHoursPay (Timestamp NightHoursPay)
	{
		set_Value (COLUMNNAME_NightHoursPay, NightHoursPay);
	}

	/** Get NightHoursPay.
		@return NightHoursPay	  */
	public Timestamp getNightHoursPay () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NightHoursPay);
	}

	/** Set NightHoursPlanification.
		@param NightHoursPlanification NightHoursPlanification	  */
	public void setNightHoursPlanification (Timestamp NightHoursPlanification)
	{
		set_Value (COLUMNNAME_NightHoursPlanification, NightHoursPlanification);
	}

	/** Get NightHoursPlanification.
		@return NightHoursPlanification	  */
	public Timestamp getNightHoursPlanification () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NightHoursPlanification);
	}

	/** Set NightHoursStart.
		@param NightHoursStart NightHoursStart	  */
	public void setNightHoursStart (Timestamp NightHoursStart)
	{
		set_Value (COLUMNNAME_NightHoursStart, NightHoursStart);
	}

	/** Get NightHoursStart.
		@return NightHoursStart	  */
	public Timestamp getNightHoursStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NightHoursStart);
	}

	/** Set OldExitCalc.
		@param OldExitCalc OldExitCalc	  */
	public void setOldExitCalc (Timestamp OldExitCalc)
	{
		set_Value (COLUMNNAME_OldExitCalc, OldExitCalc);
	}

	/** Get OldExitCalc.
		@return OldExitCalc	  */
	public Timestamp getOldExitCalc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_OldExitCalc);
	}

	/** Set OverTime.
		@param OverTime OverTime	  */
	public void setOverTime (Timestamp OverTime)
	{
		set_Value (COLUMNNAME_OverTime, OverTime);
	}

	/** Get OverTime.
		@return OverTime	  */
	public Timestamp getOverTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_OverTime);
	}

	/** Set PreCalcExit.
		@param PreCalcExit PreCalcExit	  */
	public void setPreCalcExit (Timestamp PreCalcExit)
	{
		set_Value (COLUMNNAME_PreCalcExit, PreCalcExit);
	}

	/** Get PreCalcExit.
		@return PreCalcExit	  */
	public Timestamp getPreCalcExit () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PreCalcExit);
	}

	/** Set PreCalcExit1.
		@param PreCalcExit1 PreCalcExit1	  */
	public void setPreCalcExit1 (Timestamp PreCalcExit1)
	{
		set_Value (COLUMNNAME_PreCalcExit1, PreCalcExit1);
	}

	/** Get PreCalcExit1.
		@return PreCalcExit1	  */
	public Timestamp getPreCalcExit1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PreCalcExit1);
	}

	/** Set PreCalcExit2.
		@param PreCalcExit2 PreCalcExit2	  */
	public void setPreCalcExit2 (Timestamp PreCalcExit2)
	{
		set_Value (COLUMNNAME_PreCalcExit2, PreCalcExit2);
	}

	/** Get PreCalcExit2.
		@return PreCalcExit2	  */
	public Timestamp getPreCalcExit2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PreCalcExit2);
	}

	/** Set PreCalcOvertime1.
		@param PreCalcOvertime1 PreCalcOvertime1	  */
	public void setPreCalcOvertime1 (Timestamp PreCalcOvertime1)
	{
		set_Value (COLUMNNAME_PreCalcOvertime1, PreCalcOvertime1);
	}

	/** Get PreCalcOvertime1.
		@return PreCalcOvertime1	  */
	public Timestamp getPreCalcOvertime1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PreCalcOvertime1);
	}

	/** Set PreCalcOvertime2.
		@param PreCalcOvertime2 PreCalcOvertime2	  */
	public void setPreCalcOvertime2 (Timestamp PreCalcOvertime2)
	{
		set_Value (COLUMNNAME_PreCalcOvertime2, PreCalcOvertime2);
	}

	/** Get PreCalcOvertime2.
		@return PreCalcOvertime2	  */
	public Timestamp getPreCalcOvertime2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PreCalcOvertime2);
	}

	/** Set RuleVerification.
		@param RuleVerification RuleVerification	  */
	public void setRuleVerification (Timestamp RuleVerification)
	{
		set_Value (COLUMNNAME_RuleVerification, RuleVerification);
	}

	/** Get RuleVerification.
		@return RuleVerification	  */
	public Timestamp getRuleVerification () 
	{
		return (Timestamp)get_Value(COLUMNNAME_RuleVerification);
	}

	/** Set SpecialDayEntry.
		@param SpecialDayEntry SpecialDayEntry	  */
	public void setSpecialDayEntry (Timestamp SpecialDayEntry)
	{
		set_Value (COLUMNNAME_SpecialDayEntry, SpecialDayEntry);
	}

	/** Get SpecialDayEntry.
		@return SpecialDayEntry	  */
	public Timestamp getSpecialDayEntry () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SpecialDayEntry);
	}

	/** Set SpecialDayExit.
		@param SpecialDayExit SpecialDayExit	  */
	public void setSpecialDayExit (Timestamp SpecialDayExit)
	{
		set_Value (COLUMNNAME_SpecialDayExit, SpecialDayExit);
	}

	/** Get SpecialDayExit.
		@return SpecialDayExit	  */
	public Timestamp getSpecialDayExit () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SpecialDayExit);
	}

	/** Set SumDelayEarlyWorked.
		@param SumDelayEarlyWorked SumDelayEarlyWorked	  */
	public void setSumDelayEarlyWorked (Timestamp SumDelayEarlyWorked)
	{
		set_Value (COLUMNNAME_SumDelayEarlyWorked, SumDelayEarlyWorked);
	}

	/** Get SumDelayEarlyWorked.
		@return SumDelayEarlyWorked	  */
	public Timestamp getSumDelayEarlyWorked () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SumDelayEarlyWorked);
	}

	/** Set WorkedHoursCalc.
		@param WorkedHoursCalc WorkedHoursCalc	  */
	public void setWorkedHoursCalc (Timestamp WorkedHoursCalc)
	{
		set_Value (COLUMNNAME_WorkedHoursCalc, WorkedHoursCalc);
	}

	/** Get WorkedHoursCalc.
		@return WorkedHoursCalc	  */
	public Timestamp getWorkedHoursCalc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_WorkedHoursCalc);
	}

	/** Set WorkedHoursForCalc.
		@param WorkedHoursForCalc WorkedHoursForCalc	  */
	public void setWorkedHoursForCalc (Timestamp WorkedHoursForCalc)
	{
		set_Value (COLUMNNAME_WorkedHoursForCalc, WorkedHoursForCalc);
	}

	/** Get WorkedHoursForCalc.
		@return WorkedHoursForCalc	  */
	public Timestamp getWorkedHoursForCalc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_WorkedHoursForCalc);
	}

	/** Set WorkingHours.
		@param WorkingHours WorkingHours	  */
	public void setWorkingHours (Timestamp WorkingHours)
	{
		set_Value (COLUMNNAME_WorkingHours, WorkingHours);
	}

	/** Get WorkingHours.
		@return WorkingHours	  */
	public Timestamp getWorkingHours () 
	{
		return (Timestamp)get_Value(COLUMNNAME_WorkingHours);
	}
}