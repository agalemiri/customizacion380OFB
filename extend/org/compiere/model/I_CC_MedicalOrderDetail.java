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

/** Generated Interface for CC_MedicalOrderDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_CC_MedicalOrderDetail 
{

    /** TableName=CC_MedicalOrderDetail */
    public static final String Table_Name = "CC_MedicalOrderDetail";

    /** AD_Table_ID=2000026 */
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

    /** Column name CC_MedicalOrder_ID */
    public static final String COLUMNNAME_CC_MedicalOrder_ID = "CC_MedicalOrder_ID";

	/** Set CC_MedicalOrder ID	  */
	public void setCC_MedicalOrder_ID (int CC_MedicalOrder_ID);

	/** Get CC_MedicalOrder ID	  */
	public int getCC_MedicalOrder_ID();

	public I_CC_MedicalOrder getCC_MedicalOrder() throws RuntimeException;

    /** Column name CC_MedicalOrderDetail_ID */
    public static final String COLUMNNAME_CC_MedicalOrderDetail_ID = "CC_MedicalOrderDetail_ID";

	/** Set CC_MedicalOrderDetail ID	  */
	public void setCC_MedicalOrderDetail_ID (int CC_MedicalOrderDetail_ID);

	/** Get CC_MedicalOrderDetail ID	  */
	public int getCC_MedicalOrderDetail_ID();

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

    /** Column name CycleName */
    public static final String COLUMNNAME_CycleName = "CycleName";

	/** Set Cycle Name.
	  * Name of the Project Cycle
	  */
	public void setCycleName (String CycleName);

	/** Get Cycle Name.
	  * Name of the Project Cycle
	  */
	public String getCycleName();

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name Option1 */
    public static final String COLUMNNAME_Option1 = "Option1";

	/** Set Option1	  */
	public void setOption1 (String Option1);

	/** Get Option1	  */
	public String getOption1();

    /** Column name Option2 */
    public static final String COLUMNNAME_Option2 = "Option2";

	/** Set Option2	  */
	public void setOption2 (boolean Option2);

	/** Get Option2	  */
	public boolean isOption2();

    /** Column name Option3 */
    public static final String COLUMNNAME_Option3 = "Option3";

	/** Set Option3	  */
	public void setOption3 (String Option3);

	/** Get Option3	  */
	public String getOption3();

    /** Column name Option4 */
    public static final String COLUMNNAME_Option4 = "Option4";

	/** Set Option4	  */
	public void setOption4 (boolean Option4);

	/** Get Option4	  */
	public boolean isOption4();

    /** Column name Option5 */
    public static final String COLUMNNAME_Option5 = "Option5";

	/** Set Option5	  */
	public void setOption5 (boolean Option5);

	/** Get Option5	  */
	public boolean isOption5();

    /** Column name Option6 */
    public static final String COLUMNNAME_Option6 = "Option6";

	/** Set Option6	  */
	public void setOption6 (boolean Option6);

	/** Get Option6	  */
	public boolean isOption6();

    /** Column name Option7 */
    public static final String COLUMNNAME_Option7 = "Option7";

	/** Set Option7	  */
	public void setOption7 (boolean Option7);

	/** Get Option7	  */
	public boolean isOption7();

    /** Column name Option8 */
    public static final String COLUMNNAME_Option8 = "Option8";

	/** Set Option8	  */
	public void setOption8 (boolean Option8);

	/** Get Option8	  */
	public boolean isOption8();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (String Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public String getQty();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

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
