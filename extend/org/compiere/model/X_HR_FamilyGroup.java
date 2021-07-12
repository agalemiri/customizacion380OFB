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

/** Generated Model for HR_FamilyGroup
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_FamilyGroup extends PO implements I_HR_FamilyGroup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181029L;

    /** Standard Constructor */
    public X_HR_FamilyGroup (Properties ctx, int HR_FamilyGroup_ID, String trxName)
    {
      super (ctx, HR_FamilyGroup_ID, trxName);
      /** if (HR_FamilyGroup_ID == 0)
        {
			setHR_FamilyGroup_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_FamilyGroup (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_FamilyGroup[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Age.
		@param Age 
		Age of a person
	  */
	public void setAge (String Age)
	{
		set_Value (COLUMNNAME_Age, Age);
	}

	/** Get Age.
		@return Age of a person
	  */
	public String getAge () 
	{
		return (String)get_Value(COLUMNNAME_Age);
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

	/** Set Date1.
		@param Date1 
		Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Date1.
		@return Date when business is not conducted
	  */
	public Timestamp getDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date1);
	}

	/** Set Digito.
		@param Digito 
		Digit for verify RUT information
	  */
	public void setDigito (String Digito)
	{
		set_Value (COLUMNNAME_Digito, Digito);
	}

	/** Get Digito.
		@return Digit for verify RUT information
	  */
	public String getDigito () 
	{
		return (String)get_Value(COLUMNNAME_Digito);
	}

	/** Set Hire_Date.
		@param Hire_Date Hire_Date	  */
	public void setHire_Date (Timestamp Hire_Date)
	{
		set_Value (COLUMNNAME_Hire_Date, Hire_Date);
	}

	/** Get Hire_Date.
		@return Hire_Date	  */
	public Timestamp getHire_Date () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Hire_Date);
	}

	/** Set HR_FamilyGroup ID.
		@param HR_FamilyGroup_ID HR_FamilyGroup ID	  */
	public void setHR_FamilyGroup_ID (int HR_FamilyGroup_ID)
	{
		if (HR_FamilyGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_FamilyGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_FamilyGroup_ID, Integer.valueOf(HR_FamilyGroup_ID));
	}

	/** Get HR_FamilyGroup ID.
		@return HR_FamilyGroup ID	  */
	public int getHR_FamilyGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_FamilyGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LegalBurden.
		@param LegalBurden LegalBurden	  */
	public void setLegalBurden (boolean LegalBurden)
	{
		set_Value (COLUMNNAME_LegalBurden, Boolean.valueOf(LegalBurden));
	}

	/** Get LegalBurden.
		@return LegalBurden	  */
	public boolean isLegalBurden () 
	{
		Object oo = get_Value(COLUMNNAME_LegalBurden);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name1.
		@param Name1 Name1	  */
	public void setName1 (String Name1)
	{
		set_Value (COLUMNNAME_Name1, Name1);
	}

	/** Get Name1.
		@return Name1	  */
	public String getName1 () 
	{
		return (String)get_Value(COLUMNNAME_Name1);
	}

	/** Set Name 2.
		@param Name2 
		Additional Name
	  */
	public void setName2 (String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Name 3.
		@param Name3 
		Additional Name 3
	  */
	public void setName3 (String Name3)
	{
		set_Value (COLUMNNAME_Name3, Name3);
	}

	/** Get Name 3.
		@return Additional Name 3
	  */
	public String getName3 () 
	{
		return (String)get_Value(COLUMNNAME_Name3);
	}

	/** Relationship AD_Reference_ID=2000221 */
	public static final int RELATIONSHIP_AD_Reference_ID=2000221;
	/** Padre = 01 */
	public static final String RELATIONSHIP_Padre = "01";
	/** Madre = 02 */
	public static final String RELATIONSHIP_Madre = "02";
	/** Conyuge = 03 */
	public static final String RELATIONSHIP_Conyuge = "03";
	/** Hijo/a = 04 */
	public static final String RELATIONSHIP_HijoA = "04";
	/** Nieto/a = 05 */
	public static final String RELATIONSHIP_NietoA = "05";
	/** Hermano/a = 06 */
	public static final String RELATIONSHIP_HermanoA = "06";
	/** Sobrino/a = 07 */
	public static final String RELATIONSHIP_SobrinoA = "07";
	/** Suegro/a = 08 */
	public static final String RELATIONSHIP_SuegroA = "08";
	/** Nuera = 09 */
	public static final String RELATIONSHIP_Nuera = "09";
	/** Yerno = 10 */
	public static final String RELATIONSHIP_Yerno = "10";
	/** Cuñado/a = 11 */
	public static final String RELATIONSHIP_CunadoA = "11";
	/** Abuelo/a = 12 */
	public static final String RELATIONSHIP_AbueloA = "12";
	/** Tío/a = 13 */
	public static final String RELATIONSHIP_TioA = "13";
	/** Primo/a = 14 */
	public static final String RELATIONSHIP_PrimoA = "14";
	/** Set Relationship.
		@param Relationship Relationship	  */
	public void setRelationship (String Relationship)
	{

		set_Value (COLUMNNAME_Relationship, Relationship);
	}

	/** Get Relationship.
		@return Relationship	  */
	public String getRelationship () 
	{
		return (String)get_Value(COLUMNNAME_Relationship);
	}

	/** Scholarship AD_Reference_ID=2000222 */
	public static final int SCHOLARSHIP_AD_Reference_ID=2000222;
	/** Basica Incompleta = 01 */
	public static final String SCHOLARSHIP_BasicaIncompleta = "01";
	/** Basica Completa = 02 */
	public static final String SCHOLARSHIP_BasicaCompleta = "02";
	/** Licencia Media Completa = 03 */
	public static final String SCHOLARSHIP_LicenciaMediaCompleta = "03";
	/** Licencia Media Incompleta = 04 */
	public static final String SCHOLARSHIP_LicenciaMediaIncompleta = "04";
	/** Tecnico Profesional Incompleto = 05 */
	public static final String SCHOLARSHIP_TecnicoProfesionalIncompleto = "05";
	/** Tecnico Profesional Completo = 06 */
	public static final String SCHOLARSHIP_TecnicoProfesionalCompleto = "06";
	/** Profesional Incompleto = 07 */
	public static final String SCHOLARSHIP_ProfesionalIncompleto = "07";
	/** Profesional Egresado = 08 */
	public static final String SCHOLARSHIP_ProfesionalEgresado = "08";
	/** Profesional Titulado = 09 */
	public static final String SCHOLARSHIP_ProfesionalTitulado = "09";
	/** Set Scholarship.
		@param Scholarship Scholarship	  */
	public void setScholarship (String Scholarship)
	{

		set_Value (COLUMNNAME_Scholarship, Scholarship);
	}

	/** Get Scholarship.
		@return Scholarship	  */
	public String getScholarship () 
	{
		return (String)get_Value(COLUMNNAME_Scholarship);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}