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

/** Generated Interface for CC_Evolution_Kine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_CC_Evolution_Kine 
{

    /** TableName=CC_Evolution_Kine */
    public static final String Table_Name = "CC_Evolution_Kine";

    /** AD_Table_ID=2000062 */
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

    /** Column name CC_Evolution_Kine_ID */
    public static final String COLUMNNAME_CC_Evolution_Kine_ID = "CC_Evolution_Kine_ID";

	/** Set CC_Evolution_Kine ID	  */
	public void setCC_Evolution_Kine_ID (int CC_Evolution_Kine_ID);

	/** Get CC_Evolution_Kine ID	  */
	public int getCC_Evolution_Kine_ID();

    /** Column name CC_Hospitalization_ID */
    public static final String COLUMNNAME_CC_Hospitalization_ID = "CC_Hospitalization_ID";

	/** Set CC_Hospitalization ID	  */
	public void setCC_Hospitalization_ID (int CC_Hospitalization_ID);

	/** Get CC_Hospitalization ID	  */
	public int getCC_Hospitalization_ID();

	public I_CC_Hospitalization getCC_Hospitalization() throws RuntimeException;

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

	/** Set Date.
	  * Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1);

	/** Get Date.
	  * Date when business is not conducted
	  */
	public Timestamp getDate1();

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

    /** Column name Description2 */
    public static final String COLUMNNAME_Description2 = "Description2";

	/** Set Description2.
	  * Optional short description of the record
	  */
	public void setDescription2 (String Description2);

	/** Get Description2.
	  * Optional short description of the record
	  */
	public String getDescription2();

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

    /** Column name KineDorsiFlexTobilloD */
    public static final String COLUMNNAME_KineDorsiFlexTobilloD = "KineDorsiFlexTobilloD";

	/** Set KineDorsiFlexTobilloD	  */
	public void setKineDorsiFlexTobilloD (BigDecimal KineDorsiFlexTobilloD);

	/** Get KineDorsiFlexTobilloD	  */
	public BigDecimal getKineDorsiFlexTobilloD();

    /** Column name KineExtMunecaD */
    public static final String COLUMNNAME_KineExtMunecaD = "KineExtMunecaD";

	/** Set KineExtMunecaD	  */
	public void setKineExtMunecaD (BigDecimal KineExtMunecaD);

	/** Get KineExtMunecaD	  */
	public BigDecimal getKineExtMunecaD();

    /** Column name KineExtMunecaI */
    public static final String COLUMNNAME_KineExtMunecaI = "KineExtMunecaI";

	/** Set KineExtMunecaI	  */
	public void setKineExtMunecaI (BigDecimal KineExtMunecaI);

	/** Get KineExtMunecaI	  */
	public BigDecimal getKineExtMunecaI();

    /** Column name KineExtRodillaD */
    public static final String COLUMNNAME_KineExtRodillaD = "KineExtRodillaD";

	/** Set KineExtRodillaD	  */
	public void setKineExtRodillaD (BigDecimal KineExtRodillaD);

	/** Get KineExtRodillaD	  */
	public BigDecimal getKineExtRodillaD();

    /** Column name KineExtRodillaI */
    public static final String COLUMNNAME_KineExtRodillaI = "KineExtRodillaI";

	/** Set KineExtRodillaI	  */
	public void setKineExtRodillaI (BigDecimal KineExtRodillaI);

	/** Get KineExtRodillaI	  */
	public BigDecimal getKineExtRodillaI();

    /** Column name KineFlexCaderaD */
    public static final String COLUMNNAME_KineFlexCaderaD = "KineFlexCaderaD";

	/** Set KineFlexCaderaD	  */
	public void setKineFlexCaderaD (BigDecimal KineFlexCaderaD);

	/** Get KineFlexCaderaD	  */
	public BigDecimal getKineFlexCaderaD();

    /** Column name KineFlexCaderai */
    public static final String COLUMNNAME_KineFlexCaderai = "KineFlexCaderai";

	/** Set KineFlexCaderaI	  */
	public void setKineFlexCaderai (BigDecimal KineFlexCaderai);

	/** Get KineFlexCaderaI	  */
	public BigDecimal getKineFlexCaderai();

    /** Column name KineFlexCodoD */
    public static final String COLUMNNAME_KineFlexCodoD = "KineFlexCodoD";

	/** Set KineFlexCodoD	  */
	public void setKineFlexCodoD (BigDecimal KineFlexCodoD);

	/** Get KineFlexCodoD	  */
	public BigDecimal getKineFlexCodoD();

    /** Column name KineFlexCodoI */
    public static final String COLUMNNAME_KineFlexCodoI = "KineFlexCodoI";

	/** Set KineFlexCodoI	  */
	public void setKineFlexCodoI (BigDecimal KineFlexCodoI);

	/** Get KineFlexCodoI	  */
	public BigDecimal getKineFlexCodoI();

    /** Column name KineFlexHombroD */
    public static final String COLUMNNAME_KineFlexHombroD = "KineFlexHombroD";

	/** Set KineFlexHombroD	  */
	public void setKineFlexHombroD (BigDecimal KineFlexHombroD);

	/** Get KineFlexHombroD	  */
	public BigDecimal getKineFlexHombroD();

    /** Column name KineFlexHombroI */
    public static final String COLUMNNAME_KineFlexHombroI = "KineFlexHombroI";

	/** Set KineFlexHombroI	  */
	public void setKineFlexHombroI (BigDecimal KineFlexHombroI);

	/** Get KineFlexHombroI	  */
	public BigDecimal getKineFlexHombroI();

    /** Column name KineMRCSS */
    public static final String COLUMNNAME_KineMRCSS = "KineMRCSS";

	/** Set KineMRCSS	  */
	public void setKineMRCSS (BigDecimal KineMRCSS);

	/** Get KineMRCSS	  */
	public BigDecimal getKineMRCSS();

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
