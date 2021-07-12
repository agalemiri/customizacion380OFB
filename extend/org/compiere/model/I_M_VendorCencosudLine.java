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

/** Generated Interface for M_VendorCencosudLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_M_VendorCencosudLine 
{

    /** TableName=M_VendorCencosudLine */
    public static final String Table_Name = "M_VendorCencosudLine";

    /** AD_Table_ID=2000034 */
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

    /** Column name M_VendorCencosud_ID */
    public static final String COLUMNNAME_M_VendorCencosud_ID = "M_VendorCencosud_ID";

	/** Set M_VendorCencosud ID	  */
	public void setM_VendorCencosud_ID (int M_VendorCencosud_ID);

	/** Get M_VendorCencosud ID	  */
	public int getM_VendorCencosud_ID();

	public I_M_VendorCencosud getM_VendorCencosud() throws RuntimeException;

    /** Column name M_VendorCencosudLine_ID */
    public static final String COLUMNNAME_M_VendorCencosudLine_ID = "M_VendorCencosudLine_ID";

	/** Set M_VendorCencosudLine ID	  */
	public void setM_VendorCencosudLine_ID (int M_VendorCencosudLine_ID);

	/** Get M_VendorCencosudLine ID	  */
	public int getM_VendorCencosudLine_ID();

    /** Column name Percentage */
    public static final String COLUMNNAME_Percentage = "Percentage";

	/** Set Percentage.
	  * Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage);

	/** Get Percentage.
	  * Percent of the entire amount
	  */
	public BigDecimal getPercentage();

    /** Column name RutOC */
    public static final String COLUMNNAME_RutOC = "RutOC";

	/** Set RutOC	  */
	public void setRutOC (String RutOC);

	/** Get RutOC	  */
	public String getRutOC();

    /** Column name RutOT */
    public static final String COLUMNNAME_RutOT = "RutOT";

	/** Set RutOT	  */
	public void setRutOT (String RutOT);

	/** Get RutOT	  */
	public String getRutOT();

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

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public BigDecimal getWeight();

    /** Column name WeightedAmt */
    public static final String COLUMNNAME_WeightedAmt = "WeightedAmt";

	/** Set Weighted Amount.
	  * The amount adjusted by the probability.
	  */
	public void setWeightedAmt (BigDecimal WeightedAmt);

	/** Get Weighted Amount.
	  * The amount adjusted by the probability.
	  */
	public BigDecimal getWeightedAmt();
}
