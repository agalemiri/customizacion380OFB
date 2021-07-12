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

/** Generated Interface for MP_IndicatorDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_MP_IndicatorDetail 
{

    /** TableName=MP_IndicatorDetail */
    public static final String Table_Name = "MP_IndicatorDetail";

    /** AD_Table_ID=2000054 */
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

    /** Column name answers */
    public static final String COLUMNNAME_answers = "answers";

	/** Set answers	  */
	public void setanswers (String answers);

	/** Get answers	  */
	public String getanswers();

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

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

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

    /** Column name MP_IndicatorDetail_ID */
    public static final String COLUMNNAME_MP_IndicatorDetail_ID = "MP_IndicatorDetail_ID";

	/** Set MP_IndicatorDetail ID	  */
	public void setMP_IndicatorDetail_ID (int MP_IndicatorDetail_ID);

	/** Get MP_IndicatorDetail ID	  */
	public int getMP_IndicatorDetail_ID();

    /** Column name MP_Indicator_ID */
    public static final String COLUMNNAME_MP_Indicator_ID = "MP_Indicator_ID";

	/** Set MP_Indicator ID	  */
	public void setMP_Indicator_ID (int MP_Indicator_ID);

	/** Get MP_Indicator ID	  */
	public int getMP_Indicator_ID();

	public I_MP_Indicator getMP_Indicator() throws RuntimeException;

    /** Column name MP_Team_ID */
    public static final String COLUMNNAME_MP_Team_ID = "MP_Team_ID";

	/** Set MP_Team ID	  */
	public void setMP_Team_ID (int MP_Team_ID);

	/** Get MP_Team ID	  */
	public int getMP_Team_ID();

	public I_MP_Team getMP_Team() throws RuntimeException;

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

    /** Column name ponderator */
    public static final String COLUMNNAME_ponderator = "ponderator";

	/** Set ponderator	  */
	public void setponderator (BigDecimal ponderator);

	/** Get ponderator	  */
	public BigDecimal getponderator();

    /** Column name questions */
    public static final String COLUMNNAME_questions = "questions";

	/** Set questions	  */
	public void setquestions (String questions);

	/** Get questions	  */
	public String getquestions();

    /** Column name Result */
    public static final String COLUMNNAME_Result = "Result";

	/** Set Result.
	  * Result of the action taken
	  */
	public void setResult (String Result);

	/** Get Result.
	  * Result of the action taken
	  */
	public String getResult();

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

    /** Column name weighting */
    public static final String COLUMNNAME_weighting = "weighting";

	/** Set weighting	  */
	public void setweighting (BigDecimal weighting);

	/** Get weighting	  */
	public BigDecimal getweighting();
}
