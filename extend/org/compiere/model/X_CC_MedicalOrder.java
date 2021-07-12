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

/** Generated Model for CC_MedicalOrder
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_MedicalOrder extends PO implements I_CC_MedicalOrder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_MedicalOrder (Properties ctx, int CC_MedicalOrder_ID, String trxName)
    {
      super (ctx, CC_MedicalOrder_ID, trxName);
      /** if (CC_MedicalOrder_ID == 0)
        {
			setCC_MedicalOrder_ID (0);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_CC_MedicalOrder (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_MedicalOrder[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User/Contact2.
		@param AD_User2_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User2_ID (int AD_User2_ID)
	{
		if (AD_User2_ID < 1) 
			set_Value (COLUMNNAME_AD_User2_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User2_ID, Integer.valueOf(AD_User2_ID));
	}

	/** Get User/Contact2.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set CC_CIE9_ID.
		@param CC_CIE9_ID CC_CIE9_ID	  */
	public void setCC_CIE9_ID (int CC_CIE9_ID)
	{
		if (CC_CIE9_ID < 1) 
			set_Value (COLUMNNAME_CC_CIE9_ID, null);
		else 
			set_Value (COLUMNNAME_CC_CIE9_ID, Integer.valueOf(CC_CIE9_ID));
	}

	/** Get CC_CIE9_ID.
		@return CC_CIE9_ID	  */
	public int getCC_CIE9_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_CIE9_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_Evaluation getCC_Evaluation() throws RuntimeException
    {
		return (I_CC_Evaluation)MTable.get(getCtx(), I_CC_Evaluation.Table_Name)
			.getPO(getCC_Evaluation_ID(), get_TrxName());	}

	/** Set CC_Evaluation ID.
		@param CC_Evaluation_ID CC_Evaluation ID	  */
	public void setCC_Evaluation_ID (int CC_Evaluation_ID)
	{
		if (CC_Evaluation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Evaluation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Evaluation_ID, Integer.valueOf(CC_Evaluation_ID));
	}

	/** Get CC_Evaluation ID.
		@return CC_Evaluation ID	  */
	public int getCC_Evaluation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Evaluation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_Hospitalization getCC_Hospitalization() throws RuntimeException
    {
		return (I_CC_Hospitalization)MTable.get(getCtx(), I_CC_Hospitalization.Table_Name)
			.getPO(getCC_Hospitalization_ID(), get_TrxName());	}

	/** Set CC_Hospitalization ID.
		@param CC_Hospitalization_ID CC_Hospitalization ID	  */
	public void setCC_Hospitalization_ID (int CC_Hospitalization_ID)
	{
		if (CC_Hospitalization_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Hospitalization_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Hospitalization_ID, Integer.valueOf(CC_Hospitalization_ID));
	}

	/** Get CC_Hospitalization ID.
		@return CC_Hospitalization ID	  */
	public int getCC_Hospitalization_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Hospitalization_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CC_MedicalOrder ID.
		@param CC_MedicalOrder_ID CC_MedicalOrder ID	  */
	public void setCC_MedicalOrder_ID (int CC_MedicalOrder_ID)
	{
		if (CC_MedicalOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalOrder_ID, Integer.valueOf(CC_MedicalOrder_ID));
	}

	/** Get CC_MedicalOrder ID.
		@return CC_MedicalOrder ID	  */
	public int getCC_MedicalOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_MedicalOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CC_MedicalOrder1_ID.
		@param CC_MedicalOrder1_ID CC_MedicalOrder1_ID	  */
	public void setCC_MedicalOrder1_ID (String CC_MedicalOrder1_ID)
	{
		set_Value (COLUMNNAME_CC_MedicalOrder1_ID, CC_MedicalOrder1_ID);
	}

	/** Get CC_MedicalOrder1_ID.
		@return CC_MedicalOrder1_ID	  */
	public String getCC_MedicalOrder1_ID () 
	{
		return (String)get_Value(COLUMNNAME_CC_MedicalOrder1_ID);
	}

	/** Set Decision.
		@param Decision Decision	  */
	public void setDecision (String Decision)
	{
		set_Value (COLUMNNAME_Decision, Decision);
	}

	/** Get Decision.
		@return Decision	  */
	public String getDecision () 
	{
		return (String)get_Value(COLUMNNAME_Decision);
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

	/** Set Description URL.
		@param DescriptionURL 
		URL for the description
	  */
	public void setDescriptionURL (String DescriptionURL)
	{
		throw new IllegalArgumentException ("DescriptionURL is virtual column");	}

	/** Get Description URL.
		@return URL for the description
	  */
	public String getDescriptionURL () 
	{
		return (String)get_Value(COLUMNNAME_DescriptionURL);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PrintFormatType3 AD_Reference_ID=255 */
	public static final int PRINTFORMATTYPE3_AD_Reference_ID=255;
	/** Field = F */
	public static final String PRINTFORMATTYPE3_Field = "F";
	/** Text = T */
	public static final String PRINTFORMATTYPE3_Text = "T";
	/** Print Format = P */
	public static final String PRINTFORMATTYPE3_PrintFormat = "P";
	/** Image = I */
	public static final String PRINTFORMATTYPE3_Image = "I";
	/** Rectangle = R */
	public static final String PRINTFORMATTYPE3_Rectangle = "R";
	/** Line = L */
	public static final String PRINTFORMATTYPE3_Line = "L";
	/** Set PrintFormatType3.
		@param PrintFormatType3 
		Print Format Type
	  */
	public void setPrintFormatType3 (String PrintFormatType3)
	{

		set_Value (COLUMNNAME_PrintFormatType3, PrintFormatType3);
	}

	/** Get PrintFormatType3.
		@return Print Format Type
	  */
	public String getPrintFormatType3 () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType3);
	}

	/** Set PrintFormatType4.
		@param PrintFormatType4 
		Print Format Type
	  */
	public void setPrintFormatType4 (String PrintFormatType4)
	{
		set_Value (COLUMNNAME_PrintFormatType4, PrintFormatType4);
	}

	/** Get PrintFormatType4.
		@return Print Format Type
	  */
	public String getPrintFormatType4 () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType4);
	}

	/** Set PrintFormatType5.
		@param PrintFormatType5 
		Print Format Type
	  */
	public void setPrintFormatType5 (String PrintFormatType5)
	{
		set_Value (COLUMNNAME_PrintFormatType5, PrintFormatType5);
	}

	/** Get PrintFormatType5.
		@return Print Format Type
	  */
	public String getPrintFormatType5 () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType5);
	}

	/** PrintFormatType6 AD_Reference_ID=255 */
	public static final int PRINTFORMATTYPE6_AD_Reference_ID=255;
	/** Field = F */
	public static final String PRINTFORMATTYPE6_Field = "F";
	/** Text = T */
	public static final String PRINTFORMATTYPE6_Text = "T";
	/** Print Format = P */
	public static final String PRINTFORMATTYPE6_PrintFormat = "P";
	/** Image = I */
	public static final String PRINTFORMATTYPE6_Image = "I";
	/** Rectangle = R */
	public static final String PRINTFORMATTYPE6_Rectangle = "R";
	/** Line = L */
	public static final String PRINTFORMATTYPE6_Line = "L";
	/** Set PrintFormatType6.
		@param PrintFormatType6 
		Print Format Type
	  */
	public void setPrintFormatType6 (String PrintFormatType6)
	{

		set_Value (COLUMNNAME_PrintFormatType6, PrintFormatType6);
	}

	/** Get PrintFormatType6.
		@return Print Format Type
	  */
	public String getPrintFormatType6 () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType6);
	}

	/** PrintFormatType7 AD_Reference_ID=255 */
	public static final int PRINTFORMATTYPE7_AD_Reference_ID=255;
	/** Field = F */
	public static final String PRINTFORMATTYPE7_Field = "F";
	/** Text = T */
	public static final String PRINTFORMATTYPE7_Text = "T";
	/** Print Format = P */
	public static final String PRINTFORMATTYPE7_PrintFormat = "P";
	/** Image = I */
	public static final String PRINTFORMATTYPE7_Image = "I";
	/** Rectangle = R */
	public static final String PRINTFORMATTYPE7_Rectangle = "R";
	/** Line = L */
	public static final String PRINTFORMATTYPE7_Line = "L";
	/** Set PrintFormatType7.
		@param PrintFormatType7 
		Print Format Type
	  */
	public void setPrintFormatType7 (String PrintFormatType7)
	{

		set_Value (COLUMNNAME_PrintFormatType7, PrintFormatType7);
	}

	/** Get PrintFormatType7.
		@return Print Format Type
	  */
	public String getPrintFormatType7 () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType7);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Schedules AD_Reference_ID=2000097 */
	public static final int SCHEDULES_AD_Reference_ID=2000097;
	/** horario = 01 */
	public static final String SCHEDULES_Horario = "01";
	/** Set Schedules.
		@param Schedules Schedules	  */
	public void setSchedules (String Schedules)
	{

		set_Value (COLUMNNAME_Schedules, Schedules);
	}

	/** Get Schedules.
		@return Schedules	  */
	public String getSchedules () 
	{
		return (String)get_Value(COLUMNNAME_Schedules);
	}

	/** ServiceUser AD_Reference_ID=2000094 */
	public static final int SERVICEUSER_AD_Reference_ID=2000094;
	/** CLINICA UNIVERSIDAD DE LOS ANDES = 01 */
	public static final String SERVICEUSER_CLINICAUNIVERSIDADDELOSANDES = "01";
	/** CLINICA BICENTENARIO = 02 */
	public static final String SERVICEUSER_CLINICABICENTENARIO = "02";
	/** LABORATORIO BLANCO = 03 */
	public static final String SERVICEUSER_LABORATORIOBLANCO = "03";
	/** CLIICA MADRE-HIJO BIOTREX = 04 */
	public static final String SERVICEUSER_CLIICAMADRE_HIJOBIOTREX = "04";
	/** LABORATORIO CLINICO BIONET = 05 */
	public static final String SERVICEUSER_LABORATORIOCLINICOBIONET = "05";
	/** ESTERILIZACION ISM = 06 */
	public static final String SERVICEUSER_ESTERILIZACIONISM = "06";
	/** LABORATORIO PROCELAB = 07 */
	public static final String SERVICEUSER_LABORATORIOPROCELAB = "07";
	/** Set ServiceUser.
		@param ServiceUser ServiceUser	  */
	public void setServiceUser (String ServiceUser)
	{

		set_Value (COLUMNNAME_ServiceUser, ServiceUser);
	}

	/** Get ServiceUser.
		@return ServiceUser	  */
	public String getServiceUser () 
	{
		return (String)get_Value(COLUMNNAME_ServiceUser);
	}

	/** Set Update1.
		@param Update1 Update1	  */
	public void setUpdate1 (Timestamp Update1)
	{
		set_Value (COLUMNNAME_Update1, Update1);
	}

	/** Get Update1.
		@return Update1	  */
	public Timestamp getUpdate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Update1);
	}

	/** Set Update2.
		@param Update2 Update2	  */
	public void setUpdate2 (Timestamp Update2)
	{
		set_Value (COLUMNNAME_Update2, Update2);
	}

	/** Get Update2.
		@return Update2	  */
	public Timestamp getUpdate2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Update2);
	}
}