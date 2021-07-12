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

/** Generated Model for CC_Evaluation
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_Evaluation extends PO implements I_CC_Evaluation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_Evaluation (Properties ctx, int CC_Evaluation_ID, String trxName)
    {
      super (ctx, CC_Evaluation_ID, trxName);
      /** if (CC_Evaluation_ID == 0)
        {
			setCC_Evaluation_ID (0);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_CC_Evaluation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_Evaluation[")
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
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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

	/** Set AD_User1_ID.
		@param AD_User1_ID AD_User1_ID	  */
	public void setAD_User1_ID (int AD_User1_ID)
	{
		if (AD_User1_ID < 1) 
			set_Value (COLUMNNAME_AD_User1_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User1_ID, Integer.valueOf(AD_User1_ID));
	}

	/** Get AD_User1_ID.
		@return AD_User1_ID	  */
	public int getAD_User1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User1_ID);
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

	/** Set Boton1.
		@param Boton1 Boton1	  */
	public void setBoton1 (String Boton1)
	{
		set_Value (COLUMNNAME_Boton1, Boton1);
	}

	/** Get Boton1.
		@return Boton1	  */
	public String getBoton1 () 
	{
		return (String)get_Value(COLUMNNAME_Boton1);
	}

	/** Set BtnGenerateMove.
		@param BtnGenerateMove BtnGenerateMove	  */
	public void setBtnGenerateMove (String BtnGenerateMove)
	{
		set_ValueNoCheck (COLUMNNAME_BtnGenerateMove, BtnGenerateMove);
	}

	/** Get BtnGenerateMove.
		@return BtnGenerateMove	  */
	public String getBtnGenerateMove () 
	{
		return (String)get_Value(COLUMNNAME_BtnGenerateMove);
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_BPartner getC_BPartnerRef() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerRef_ID(), get_TrxName());	}

	/** Set C_BPartnerRef_ID.
		@param C_BPartnerRef_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartnerRef_ID (int C_BPartnerRef_ID)
	{
		if (C_BPartnerRef_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRef_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRef_ID, Integer.valueOf(C_BPartnerRef_ID));
	}

	/** Get C_BPartnerRef_ID.
		@return Identifies a Business Partner
	  */
	public int getC_BPartnerRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_CIE10 getCC_CIE10() throws RuntimeException
    {
		return (I_CC_CIE10)MTable.get(getCtx(), I_CC_CIE10.Table_Name)
			.getPO(getCC_CIE10_ID(), get_TrxName());	}

	/** Set CC_CIE10_ID.
		@param CC_CIE10_ID CC_CIE10_ID	  */
	public void setCC_CIE10_ID (int CC_CIE10_ID)
	{
		if (CC_CIE10_ID < 1) 
			set_Value (COLUMNNAME_CC_CIE10_ID, null);
		else 
			set_Value (COLUMNNAME_CC_CIE10_ID, Integer.valueOf(CC_CIE10_ID));
	}

	/** Get CC_CIE10_ID.
		@return CC_CIE10_ID	  */
	public int getCC_CIE10_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_CIE10_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		throw new IllegalArgumentException ("DateTrx is virtual column");	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** DietFood AD_Reference_ID=2000070 */
	public static final int DIETFOOD_AD_Reference_ID=2000070;
	/** Regimen Liviano = R01 */
	public static final String DIETFOOD_RegimenLiviano = "R01";
	/** Liviano Hiposodico Diabetico = R02 */
	public static final String DIETFOOD_LivianoHiposodicoDiabetico = "R02";
	/** Liviano Hiposodico Papilla = R03 */
	public static final String DIETFOOD_LivianoHiposodicoPapilla = "R03";
	/** Liviano Normosodico Diabetico = R04 */
	public static final String DIETFOOD_LivianoNormosodicoDiabetico = "R04";
	/** Normal = R05 */
	public static final String DIETFOOD_Normal = "R05";
	/** Set DietFood.
		@param DietFood DietFood	  */
	public void setDietFood (String DietFood)
	{

		set_Value (COLUMNNAME_DietFood, DietFood);
	}

	/** Get DietFood.
		@return DietFood	  */
	public String getDietFood () 
	{
		return (String)get_Value(COLUMNNAME_DietFood);
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set IsReadOnly1.
		@param IsReadOnly1 IsReadOnly1	  */
	public void setIsReadOnly1 (boolean IsReadOnly1)
	{
		set_Value (COLUMNNAME_IsReadOnly1, Boolean.valueOf(IsReadOnly1));
	}

	/** Get IsReadOnly1.
		@return IsReadOnly1	  */
	public boolean isReadOnly1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException
    {
		return (org.compiere.model.I_M_Movement)MTable.get(getCtx(), org.compiere.model.I_M_Movement.Table_Name)
			.getPO(getM_Movement_ID(), get_TrxName());	}

	/** Set Inventory Move.
		@param M_Movement_ID 
		Movement of Inventory
	  */
	public void setM_Movement_ID (int M_Movement_ID)
	{
		if (M_Movement_ID < 1) 
			set_Value (COLUMNNAME_M_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
	}

	/** Get Inventory Move.
		@return Movement of Inventory
	  */
	public int getM_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Movement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Planning Horizon.
		@param PlanningHorizon 
		The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	  */
	public void setPlanningHorizon (String PlanningHorizon)
	{
		set_Value (COLUMNNAME_PlanningHorizon, PlanningHorizon);
	}

	/** Get Planning Horizon.
		@return The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	  */
	public String getPlanningHorizon () 
	{
		return (String)get_Value(COLUMNNAME_PlanningHorizon);
	}

	/** Set Format Type.
		@param PrintFormatType 
		Print Format Type
	  */
	public void setPrintFormatType (String PrintFormatType)
	{
		set_Value (COLUMNNAME_PrintFormatType, PrintFormatType);
	}

	/** Get Format Type.
		@return Print Format Type
	  */
	public String getPrintFormatType () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType);
	}

	/** Set Procedure.
		@param ProcedureName 
		Name of the Database Procedure
	  */
	public void setProcedureName (String ProcedureName)
	{
		set_Value (COLUMNNAME_ProcedureName, ProcedureName);
	}

	/** Get Procedure.
		@return Name of the Database Procedure
	  */
	public String getProcedureName () 
	{
		return (String)get_Value(COLUMNNAME_ProcedureName);
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

	/** Repose AD_Reference_ID=2000035 */
	public static final int REPOSE_AD_Reference_ID=2000035;
	/** Absoluto = 01 */
	public static final String REPOSE_Absoluto = "01";
	/** Relativo = 02 */
	public static final String REPOSE_Relativo = "02";
	/** Levantar a Sillon = 03 */
	public static final String REPOSE_LevantarASillon = "03";
	/** Set Repose.
		@param Repose Repose	  */
	public void setRepose (String Repose)
	{

		set_Value (COLUMNNAME_Repose, Repose);
	}

	/** Get Repose.
		@return Repose	  */
	public String getRepose () 
	{
		return (String)get_Value(COLUMNNAME_Repose);
	}
}