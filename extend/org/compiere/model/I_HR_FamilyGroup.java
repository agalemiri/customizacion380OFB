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

/** Generated Interface for HR_FamilyGroup
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_HR_FamilyGroup 
{

    /** TableName=HR_FamilyGroup */
    public static final String Table_Name = "HR_FamilyGroup";

    /** AD_Table_ID=2000122 */
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

    /** Column name Age */
    public static final String COLUMNNAME_Age = "Age";

	/** Set Age.
	  * Age of a person
	  */
	public void setAge (String Age);

	/** Get Age.
	  * Age of a person
	  */
	public String getAge();

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

    /** Column name Date1 */
    public static final String COLUMNNAME_Date1 = "Date1";

	/** Set Date1.
	  * Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1);

	/** Get Date1.
	  * Date when business is not conducted
	  */
	public Timestamp getDate1();

    /** Column name Digito */
    public static final String COLUMNNAME_Digito = "Digito";

	/** Set Digito.
	  * Digit for verify RUT information
	  */
	public void setDigito (String Digito);

	/** Get Digito.
	  * Digit for verify RUT information
	  */
	public String getDigito();

    /** Column name Hire_Date */
    public static final String COLUMNNAME_Hire_Date = "Hire_Date";

	/** Set Hire_Date	  */
	public void setHire_Date (Timestamp Hire_Date);

	/** Get Hire_Date	  */
	public Timestamp getHire_Date();

    /** Column name HR_FamilyGroup_ID */
    public static final String COLUMNNAME_HR_FamilyGroup_ID = "HR_FamilyGroup_ID";

	/** Set HR_FamilyGroup ID	  */
	public void setHR_FamilyGroup_ID (int HR_FamilyGroup_ID);

	/** Get HR_FamilyGroup ID	  */
	public int getHR_FamilyGroup_ID();

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

    /** Column name LegalBurden */
    public static final String COLUMNNAME_LegalBurden = "LegalBurden";

	/** Set LegalBurden	  */
	public void setLegalBurden (boolean LegalBurden);

	/** Get LegalBurden	  */
	public boolean isLegalBurden();

    /** Column name Name1 */
    public static final String COLUMNNAME_Name1 = "Name1";

	/** Set Name1	  */
	public void setName1 (String Name1);

	/** Get Name1	  */
	public String getName1();

    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/** Set Name 2.
	  * Additional Name
	  */
	public void setName2 (String Name2);

	/** Get Name 2.
	  * Additional Name
	  */
	public String getName2();

    /** Column name Name3 */
    public static final String COLUMNNAME_Name3 = "Name3";

	/** Set Name 3.
	  * Additional Name 3
	  */
	public void setName3 (String Name3);

	/** Get Name 3.
	  * Additional Name 3
	  */
	public String getName3();

    /** Column name Relationship */
    public static final String COLUMNNAME_Relationship = "Relationship";

	/** Set Relationship	  */
	public void setRelationship (String Relationship);

	/** Get Relationship	  */
	public String getRelationship();

    /** Column name Scholarship */
    public static final String COLUMNNAME_Scholarship = "Scholarship";

	/** Set Scholarship	  */
	public void setScholarship (String Scholarship);

	/** Get Scholarship	  */
	public String getScholarship();

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
}
