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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for HR_AttendanceHours
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_HR_AttendanceHours 
{

    /** TableName=HR_AttendanceHours */
    public static final String Table_Name = "HR_AttendanceHours";

    /** AD_Table_ID=2000115 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name CalcExit */
    public static final String COLUMNNAME_CalcExit = "CalcExit";

	/** Set CalcExit	  */
	public void setCalcExit (Timestamp CalcExit);

	/** Get CalcExit	  */
	public Timestamp getCalcExit();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name CompensatedTime */
    public static final String COLUMNNAME_CompensatedTime = "CompensatedTime";

	/** Set CompensatedTime	  */
	public void setCompensatedTime (Timestamp CompensatedTime);

	/** Get CompensatedTime	  */
	public Timestamp getCompensatedTime();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name DayHoursCalc */
    public static final String COLUMNNAME_DayHoursCalc = "DayHoursCalc";

	/** Set DayHoursCalc	  */
	public void setDayHoursCalc (Timestamp DayHoursCalc);

	/** Get DayHoursCalc	  */
	public Timestamp getDayHoursCalc();

    /** Column name DayOfWeek */
    public static final String COLUMNNAME_DayOfWeek = "DayOfWeek";

	/** Set DayOfWeek	  */
	public void setDayOfWeek (String DayOfWeek);

	/** Get DayOfWeek	  */
	public String getDayOfWeek();

    /** Column name DayTimePay */
    public static final String COLUMNNAME_DayTimePay = "DayTimePay";

	/** Set DayTimePay	  */
	public void setDayTimePay (Timestamp DayTimePay);

	/** Get DayTimePay	  */
	public Timestamp getDayTimePay();

    /** Column name DayTimePlanification */
    public static final String COLUMNNAME_DayTimePlanification = "DayTimePlanification";

	/** Set DayTimePlanification	  */
	public void setDayTimePlanification (Timestamp DayTimePlanification);

	/** Get DayTimePlanification	  */
	public Timestamp getDayTimePlanification();

    /** Column name DelayBase */
    public static final String COLUMNNAME_DelayBase = "DelayBase";

	/** Set DelayBase	  */
	public void setDelayBase (Timestamp DelayBase);

	/** Get DelayBase	  */
	public Timestamp getDelayBase();

    /** Column name DelayBegin */
    public static final String COLUMNNAME_DelayBegin = "DelayBegin";

	/** Set DelayBegin	  */
	public void setDelayBegin (Timestamp DelayBegin);

	/** Get DelayBegin	  */
	public Timestamp getDelayBegin();

    /** Column name DelayCalc */
    public static final String COLUMNNAME_DelayCalc = "DelayCalc";

	/** Set DelayCalc	  */
	public void setDelayCalc (Timestamp DelayCalc);

	/** Get DelayCalc	  */
	public Timestamp getDelayCalc();

    /** Column name DelayEnd */
    public static final String COLUMNNAME_DelayEnd = "DelayEnd";

	/** Set DelayEnd	  */
	public void setDelayEnd (Timestamp DelayEnd);

	/** Get DelayEnd	  */
	public Timestamp getDelayEnd();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DifferenceHours */
    public static final String COLUMNNAME_DifferenceHours = "DifferenceHours";

	/** Set DifferenceHours	  */
	public void setDifferenceHours (Timestamp DifferenceHours);

	/** Get DifferenceHours	  */
	public Timestamp getDifferenceHours();

    /** Column name EarlyExit */
    public static final String COLUMNNAME_EarlyExit = "EarlyExit";

	/** Set EarlyExit	  */
	public void setEarlyExit (Timestamp EarlyExit);

	/** Get EarlyExit	  */
	public Timestamp getEarlyExit();

    /** Column name EarlyExitBase */
    public static final String COLUMNNAME_EarlyExitBase = "EarlyExitBase";

	/** Set EarlyExitBase	  */
	public void setEarlyExitBase (Timestamp EarlyExitBase);

	/** Get EarlyExitBase	  */
	public Timestamp getEarlyExitBase();

    /** Column name EndMidMorning */
    public static final String COLUMNNAME_EndMidMorning = "EndMidMorning";

	/** Set EndMidMorning	  */
	public void setEndMidMorning (Timestamp EndMidMorning);

	/** Get EndMidMorning	  */
	public Timestamp getEndMidMorning();

    /** Column name EntryCut */
    public static final String COLUMNNAME_EntryCut = "EntryCut";

	/** Set EntryCut	  */
	public void setEntryCut (Timestamp EntryCut);

	/** Get EntryCut	  */
	public Timestamp getEntryCut();

    /** Column name EntryHour */
    public static final String COLUMNNAME_EntryHour = "EntryHour";

	/** Set EntryHour	  */
	public void setEntryHour (Timestamp EntryHour);

	/** Get EntryHour	  */
	public Timestamp getEntryHour();

    /** Column name ExitCalc */
    public static final String COLUMNNAME_ExitCalc = "ExitCalc";

	/** Set ExitCalc	  */
	public void setExitCalc (Timestamp ExitCalc);

	/** Get ExitCalc	  */
	public Timestamp getExitCalc();

    /** Column name ExitFormat */
    public static final String COLUMNNAME_ExitFormat = "ExitFormat";

	/** Set ExitFormat	  */
	public void setExitFormat (Timestamp ExitFormat);

	/** Get ExitFormat	  */
	public Timestamp getExitFormat();

    /** Column name ExitHour */
    public static final String COLUMNNAME_ExitHour = "ExitHour";

	/** Set ExitHour	  */
	public void setExitHour (Timestamp ExitHour);

	/** Get ExitHour	  */
	public Timestamp getExitHour();

    /** Column name HR_AdministrativeRequests_ID */
    public static final String COLUMNNAME_HR_AdministrativeRequests_ID = "HR_AdministrativeRequests_ID";

	/** Set HR_AdministrativeRequests	  */
	public void setHR_AdministrativeRequests_ID (int HR_AdministrativeRequests_ID);

	/** Get HR_AdministrativeRequests	  */
	public int getHR_AdministrativeRequests_ID();

	public I_HR_AdministrativeRequests getHR_AdministrativeRequests() throws RuntimeException;

    /** Column name HR_AttendanceHours_ID */
    public static final String COLUMNNAME_HR_AttendanceHours_ID = "HR_AttendanceHours_ID";

	/** Set HR_AttendanceHours ID	  */
	public void setHR_AttendanceHours_ID (int HR_AttendanceHours_ID);

	/** Get HR_AttendanceHours ID	  */
	public int getHR_AttendanceHours_ID();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name MaxDayTime */
    public static final String COLUMNNAME_MaxDayTime = "MaxDayTime";

	/** Set MaxDayTime	  */
	public void setMaxDayTime (Timestamp MaxDayTime);

	/** Get MaxDayTime	  */
	public Timestamp getMaxDayTime();

    /** Column name MaxEarlyExit */
    public static final String COLUMNNAME_MaxEarlyExit = "MaxEarlyExit";

	/** Set MaxEarlyExit	  */
	public void setMaxEarlyExit (Timestamp MaxEarlyExit);

	/** Get MaxEarlyExit	  */
	public Timestamp getMaxEarlyExit();

    /** Column name MidMorningHours */
    public static final String COLUMNNAME_MidMorningHours = "MidMorningHours";

	/** Set MidMorningHours	  */
	public void setMidMorningHours (Timestamp MidMorningHours);

	/** Get MidMorningHours	  */
	public Timestamp getMidMorningHours();

    /** Column name MinuteCalc */
    public static final String COLUMNNAME_MinuteCalc = "MinuteCalc";

	/** Set MinuteCalc	  */
	public void setMinuteCalc (int MinuteCalc);

	/** Get MinuteCalc	  */
	public int getMinuteCalc();

    /** Column name NightHoursCalc */
    public static final String COLUMNNAME_NightHoursCalc = "NightHoursCalc";

	/** Set NightHoursCalc	  */
	public void setNightHoursCalc (Timestamp NightHoursCalc);

	/** Get NightHoursCalc	  */
	public Timestamp getNightHoursCalc();

    /** Column name NightHoursEnd */
    public static final String COLUMNNAME_NightHoursEnd = "NightHoursEnd";

	/** Set NightHoursEnd	  */
	public void setNightHoursEnd (Timestamp NightHoursEnd);

	/** Get NightHoursEnd	  */
	public Timestamp getNightHoursEnd();

    /** Column name NightHoursPay */
    public static final String COLUMNNAME_NightHoursPay = "NightHoursPay";

	/** Set NightHoursPay	  */
	public void setNightHoursPay (Timestamp NightHoursPay);

	/** Get NightHoursPay	  */
	public Timestamp getNightHoursPay();

    /** Column name NightHoursPlanification */
    public static final String COLUMNNAME_NightHoursPlanification = "NightHoursPlanification";

	/** Set NightHoursPlanification	  */
	public void setNightHoursPlanification (Timestamp NightHoursPlanification);

	/** Get NightHoursPlanification	  */
	public Timestamp getNightHoursPlanification();

    /** Column name NightHoursStart */
    public static final String COLUMNNAME_NightHoursStart = "NightHoursStart";

	/** Set NightHoursStart	  */
	public void setNightHoursStart (Timestamp NightHoursStart);

	/** Get NightHoursStart	  */
	public Timestamp getNightHoursStart();

    /** Column name OldExitCalc */
    public static final String COLUMNNAME_OldExitCalc = "OldExitCalc";

	/** Set OldExitCalc	  */
	public void setOldExitCalc (Timestamp OldExitCalc);

	/** Get OldExitCalc	  */
	public Timestamp getOldExitCalc();

    /** Column name OverTime */
    public static final String COLUMNNAME_OverTime = "OverTime";

	/** Set OverTime	  */
	public void setOverTime (Timestamp OverTime);

	/** Get OverTime	  */
	public Timestamp getOverTime();

    /** Column name PreCalcExit */
    public static final String COLUMNNAME_PreCalcExit = "PreCalcExit";

	/** Set PreCalcExit	  */
	public void setPreCalcExit (Timestamp PreCalcExit);

	/** Get PreCalcExit	  */
	public Timestamp getPreCalcExit();

    /** Column name PreCalcExit1 */
    public static final String COLUMNNAME_PreCalcExit1 = "PreCalcExit1";

	/** Set PreCalcExit1	  */
	public void setPreCalcExit1 (Timestamp PreCalcExit1);

	/** Get PreCalcExit1	  */
	public Timestamp getPreCalcExit1();

    /** Column name PreCalcExit2 */
    public static final String COLUMNNAME_PreCalcExit2 = "PreCalcExit2";

	/** Set PreCalcExit2	  */
	public void setPreCalcExit2 (Timestamp PreCalcExit2);

	/** Get PreCalcExit2	  */
	public Timestamp getPreCalcExit2();

    /** Column name PreCalcOvertime1 */
    public static final String COLUMNNAME_PreCalcOvertime1 = "PreCalcOvertime1";

	/** Set PreCalcOvertime1	  */
	public void setPreCalcOvertime1 (Timestamp PreCalcOvertime1);

	/** Get PreCalcOvertime1	  */
	public Timestamp getPreCalcOvertime1();

    /** Column name PreCalcOvertime2 */
    public static final String COLUMNNAME_PreCalcOvertime2 = "PreCalcOvertime2";

	/** Set PreCalcOvertime2	  */
	public void setPreCalcOvertime2 (Timestamp PreCalcOvertime2);

	/** Get PreCalcOvertime2	  */
	public Timestamp getPreCalcOvertime2();

    /** Column name RuleVerification */
    public static final String COLUMNNAME_RuleVerification = "RuleVerification";

	/** Set RuleVerification	  */
	public void setRuleVerification (Timestamp RuleVerification);

	/** Get RuleVerification	  */
	public Timestamp getRuleVerification();

    /** Column name SpecialDayEntry */
    public static final String COLUMNNAME_SpecialDayEntry = "SpecialDayEntry";

	/** Set SpecialDayEntry	  */
	public void setSpecialDayEntry (Timestamp SpecialDayEntry);

	/** Get SpecialDayEntry	  */
	public Timestamp getSpecialDayEntry();

    /** Column name SpecialDayExit */
    public static final String COLUMNNAME_SpecialDayExit = "SpecialDayExit";

	/** Set SpecialDayExit	  */
	public void setSpecialDayExit (Timestamp SpecialDayExit);

	/** Get SpecialDayExit	  */
	public Timestamp getSpecialDayExit();

    /** Column name SumDelayEarlyWorked */
    public static final String COLUMNNAME_SumDelayEarlyWorked = "SumDelayEarlyWorked";

	/** Set SumDelayEarlyWorked	  */
	public void setSumDelayEarlyWorked (Timestamp SumDelayEarlyWorked);

	/** Get SumDelayEarlyWorked	  */
	public Timestamp getSumDelayEarlyWorked();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name WorkedHoursCalc */
    public static final String COLUMNNAME_WorkedHoursCalc = "WorkedHoursCalc";

	/** Set WorkedHoursCalc	  */
	public void setWorkedHoursCalc (Timestamp WorkedHoursCalc);

	/** Get WorkedHoursCalc	  */
	public Timestamp getWorkedHoursCalc();

    /** Column name WorkedHoursForCalc */
    public static final String COLUMNNAME_WorkedHoursForCalc = "WorkedHoursForCalc";

	/** Set WorkedHoursForCalc	  */
	public void setWorkedHoursForCalc (Timestamp WorkedHoursForCalc);

	/** Get WorkedHoursForCalc	  */
	public Timestamp getWorkedHoursForCalc();

    /** Column name WorkingHours */
    public static final String COLUMNNAME_WorkingHours = "WorkingHours";

	/** Set WorkingHours	  */
	public void setWorkingHours (Timestamp WorkingHours);

	/** Get WorkingHours	  */
	public Timestamp getWorkingHours();
}
