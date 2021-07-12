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

/** Generated Interface for MED_Appointment
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_MED_Appointment 
{

    /** TableName=MED_Appointment */
    public static final String Table_Name = "MED_Appointment";

    /** AD_Table_ID=2000073 */
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

    /** Column name ArrivalTime */
    public static final String COLUMNNAME_ArrivalTime = "ArrivalTime";

	/** Set ArrivalTime	  */
	public void setArrivalTime (Timestamp ArrivalTime);

	/** Get ArrivalTime	  */
	public Timestamp getArrivalTime();

    /** Column name AttentionTime */
    public static final String COLUMNNAME_AttentionTime = "AttentionTime";

	/** Set AttentionTime	  */
	public void setAttentionTime (Timestamp AttentionTime);

	/** Get AttentionTime	  */
	public Timestamp getAttentionTime();

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

    /** Column name C_BPartnerMed_ID */
    public static final String COLUMNNAME_C_BPartnerMed_ID = "C_BPartnerMed_ID";

	/** Set C_BPartnerMed_ID	  */
	public void setC_BPartnerMed_ID (int C_BPartnerMed_ID);

	/** Get C_BPartnerMed_ID	  */
	public int getC_BPartnerMed_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerMed() throws RuntimeException;

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

    /** Column name Entry */
    public static final String COLUMNNAME_Entry = "Entry";

	/** Set Entry	  */
	public void setEntry (boolean Entry);

	/** Get Entry	  */
	public boolean isEntry();

    /** Column name ExitTime */
    public static final String COLUMNNAME_ExitTime = "ExitTime";

	/** Set ExitTime	  */
	public void setExitTime (Timestamp ExitTime);

	/** Get ExitTime	  */
	public Timestamp getExitTime();

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

    /** Column name IsArrived */
    public static final String COLUMNNAME_IsArrived = "IsArrived";

	/** Set IsArrived	  */
	public void setIsArrived (boolean IsArrived);

	/** Get IsArrived	  */
	public boolean isArrived();

    /** Column name IsExit */
    public static final String COLUMNNAME_IsExit = "IsExit";

	/** Set IsExit	  */
	public void setIsExit (boolean IsExit);

	/** Get IsExit	  */
	public boolean isExit();

    /** Column name MED_Appointment_ID */
    public static final String COLUMNNAME_MED_Appointment_ID = "MED_Appointment_ID";

	/** Set MED_Appointment ID	  */
	public void setMED_Appointment_ID (int MED_Appointment_ID);

	/** Get MED_Appointment ID	  */
	public int getMED_Appointment_ID();

    /** Column name MED_AttentionType_ID */
    public static final String COLUMNNAME_MED_AttentionType_ID = "MED_AttentionType_ID";

	/** Set MED_AttentionType ID	  */
	public void setMED_AttentionType_ID (int MED_AttentionType_ID);

	/** Get MED_AttentionType ID	  */
	public int getMED_AttentionType_ID();

	public I_MED_AttentionType getMED_AttentionType() throws RuntimeException;

    /** Column name MED_Specialty_ID */
    public static final String COLUMNNAME_MED_Specialty_ID = "MED_Specialty_ID";

	/** Set MED_Specialty ID	  */
	public void setMED_Specialty_ID (int MED_Specialty_ID);

	/** Get MED_Specialty ID	  */
	public int getMED_Specialty_ID();

	public I_MED_Specialty getMED_Specialty() throws RuntimeException;

    /** Column name ReservationDate */
    public static final String COLUMNNAME_ReservationDate = "ReservationDate";

	/** Set ReservationDate	  */
	public void setReservationDate (Timestamp ReservationDate);

	/** Get ReservationDate	  */
	public Timestamp getReservationDate();

    /** Column name State */
    public static final String COLUMNNAME_State = "State";

	/** Set State	  */
	public void setState (String State);

	/** Get State	  */
	public String getState();

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
}
