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

/** Generated Interface for MP_Indicator
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_MP_Indicator 
{

    /** TableName=MP_Indicator */
    public static final String Table_Name = "MP_Indicator";

    /** AD_Table_ID=2000053 */
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

    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/** Set Role.
	  * Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID);

	/** Get Role.
	  * Responsibility Role
	  */
	public int getAD_Role_ID();

	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException;

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

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

    /** Column name currentresult */
    public static final String COLUMNNAME_currentresult = "currentresult";

	/** Set currentresult	  */
	public void setcurrentresult (String currentresult);

	/** Get currentresult	  */
	public String getcurrentresult();

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

    /** Column name finish */
    public static final String COLUMNNAME_finish = "finish";

	/** Set finish	  */
	public void setfinish (String finish);

	/** Get finish	  */
	public String getfinish();

    /** Column name formula */
    public static final String COLUMNNAME_formula = "formula";

	/** Set formula	  */
	public void setformula (String formula);

	/** Get formula	  */
	public String getformula();

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

    /** Column name MP_IndicatorGroup_ID */
    public static final String COLUMNNAME_MP_IndicatorGroup_ID = "MP_IndicatorGroup_ID";

	/** Set MP_IndicatorGroup ID	  */
	public void setMP_IndicatorGroup_ID (int MP_IndicatorGroup_ID);

	/** Get MP_IndicatorGroup ID	  */
	public int getMP_IndicatorGroup_ID();

	public I_MP_IndicatorGroup getMP_IndicatorGroup() throws RuntimeException;

    /** Column name MP_Indicator_ID */
    public static final String COLUMNNAME_MP_Indicator_ID = "MP_Indicator_ID";

	/** Set MP_Indicator ID	  */
	public void setMP_Indicator_ID (int MP_Indicator_ID);

	/** Get MP_Indicator ID	  */
	public int getMP_Indicator_ID();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Note.
	  * Optional additional user defined information
	  */
	public void setNote (String Note);

	/** Get Note.
	  * Optional additional user defined information
	  */
	public String getNote();

    /** Column name recurrence */
    public static final String COLUMNNAME_recurrence = "recurrence";

	/** Set recurrence	  */
	public void setrecurrence (boolean recurrence);

	/** Get recurrence	  */
	public boolean isrecurrence();

    /** Column name rules */
    public static final String COLUMNNAME_rules = "rules";

	/** Set rules	  */
	public void setrules (String rules);

	/** Get rules	  */
	public String getrules();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name VarA */
    public static final String COLUMNNAME_VarA = "VarA";

	/** Set VarA	  */
	public void setVarA (String VarA);

	/** Get VarA	  */
	public String getVarA();

    /** Column name VarB */
    public static final String COLUMNNAME_VarB = "VarB";

	/** Set VarB	  */
	public void setVarB (String VarB);

	/** Get VarB	  */
	public String getVarB();

    /** Column name verification */
    public static final String COLUMNNAME_verification = "verification";

	/** Set verification	  */
	public void setverification (String verification);

	/** Get verification	  */
	public String getverification();
}
