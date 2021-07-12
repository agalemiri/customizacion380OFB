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

/** Generated Interface for MED_SurgeryRequisition
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_MED_SurgeryRequisition 
{

    /** TableName=MED_SurgeryRequisition */
    public static final String Table_Name = "MED_SurgeryRequisition";

    /** AD_Table_ID=2000083 */
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

    /** Column name CC_Agreement_ID */
    public static final String COLUMNNAME_CC_Agreement_ID = "CC_Agreement_ID";

	/** Set CC_Agreement ID	  */
	public void setCC_Agreement_ID (int CC_Agreement_ID);

	/** Get CC_Agreement ID	  */
	public int getCC_Agreement_ID();

	public I_CC_Agreement getCC_Agreement() throws RuntimeException;

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

    /** Column name DateEnd */
    public static final String COLUMNNAME_DateEnd = "DateEnd";

	/** Set DateEnd	  */
	public void setDateEnd (Timestamp DateEnd);

	/** Get DateEnd	  */
	public Timestamp getDateEnd();

    /** Column name DateStart */
    public static final String COLUMNNAME_DateStart = "DateStart";

	/** Set Date Start.
	  * Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart);

	/** Get Date Start.
	  * Date Start for this Order
	  */
	public Timestamp getDateStart();

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

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

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

    /** Column name MED_SurgeryRequisition_ID */
    public static final String COLUMNNAME_MED_SurgeryRequisition_ID = "MED_SurgeryRequisition_ID";

	/** Set MED_SurgeryRequisition ID	  */
	public void setMED_SurgeryRequisition_ID (int MED_SurgeryRequisition_ID);

	/** Get MED_SurgeryRequisition ID	  */
	public int getMED_SurgeryRequisition_ID();

    /** Column name OfbButton */
    public static final String COLUMNNAME_OfbButton = "OfbButton";

	/** Set OfbButton	  */
	public void setOfbButton (String OfbButton);

	/** Get OfbButton	  */
	public String getOfbButton();

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
