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

/** Generated Model for CC_CollectiveIntelligence
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_CollectiveIntelligence extends PO implements I_CC_CollectiveIntelligence, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_CollectiveIntelligence (Properties ctx, int CC_CollectiveIntelligence_ID, String trxName)
    {
      super (ctx, CC_CollectiveIntelligence_ID, trxName);
      /** if (CC_CollectiveIntelligence_ID == 0)
        {
			setCC_CI01 (false);
// N
			setCC_CI02 (false);
// N
			setCC_CI03 (false);
// N
			setCC_CI04 (false);
// N
			setCC_CI05 (false);
// N
			setCC_CI06 (false);
// N
			setCC_CI07 (false);
// N
			setCC_CI08 (false);
// N
			setCC_CI09 (false);
// N
			setCC_CI10 (false);
// N
			setCC_CI11 (false);
// N
			setCC_CI12 (false);
// N
			setCC_CI13 (false);
// N
			setCC_CI14 (false);
// N
			setCC_CI15 (false);
// N
			setCC_CI16 (false);
// N
			setCC_CI17 (false);
// N
			setCC_CI18 (false);
// N
			setCC_CI19 (false);
// N
			setCC_CI20 (false);
// N
			setCC_CI21 (false);
// N
			setCC_CI22 (false);
// N
			setCC_CI23 (false);
// N
			setCC_CI24 (false);
// N
			setCC_CI25 (false);
// N
			setCC_CI26 (false);
// N
			setCC_CI27 (false);
// N
			setCC_CI28 (false);
// N
			setCC_CI29 (false);
// N
			setCC_CI30 (false);
// N
			setCC_CI31 (false);
// N
			setCC_CI32 (false);
// N
			setCC_CI33 (false);
// N
			setCC_CI34 (false);
// N
			setCC_CI35 (false);
// N
			setCC_CI36 (false);
// N
			setCC_CI37 (false);
// N
			setCC_CI38 (false);
// N
			setCC_CI39 (false);
// N
			setCC_CI40 (false);
// N
			setCC_CI41 (false);
// N
			setCC_CI42 (false);
// N
			setCC_CI43 (false);
// N
			setCC_CI44 (false);
// N
			setCC_CI45 (false);
// N
			setCC_CI46 (false);
// N
			setCC_CI47 (false);
// N
			setCC_CI48 (false);
// N
			setCC_CI49 (false);
// N
			setCC_CI50 (false);
// N
			setCC_CI51 (false);
// N
			setCC_CI52 (false);
// N
			setCC_CI53 (false);
// N
			setCC_CI54 (false);
// N
			setCC_CI55 (false);
// N
			setCC_CI56 (false);
// N
			setCC_CI57 (false);
// N
			setCC_CI58 (false);
// N
			setCC_CI59 (false);
// N
			setCC_CI60 (false);
// N
			setCC_CollectiveIntelligence_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CC_CollectiveIntelligence (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_CollectiveIntelligence[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CC_CI01.
		@param CC_CI01 CC_CI01	  */
	public void setCC_CI01 (boolean CC_CI01)
	{
		set_Value (COLUMNNAME_CC_CI01, Boolean.valueOf(CC_CI01));
	}

	/** Get CC_CI01.
		@return CC_CI01	  */
	public boolean isCC_CI01 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI01);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI02.
		@param CC_CI02 CC_CI02	  */
	public void setCC_CI02 (boolean CC_CI02)
	{
		set_Value (COLUMNNAME_CC_CI02, Boolean.valueOf(CC_CI02));
	}

	/** Get CC_CI02.
		@return CC_CI02	  */
	public boolean isCC_CI02 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI02);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI03.
		@param CC_CI03 CC_CI03	  */
	public void setCC_CI03 (boolean CC_CI03)
	{
		set_Value (COLUMNNAME_CC_CI03, Boolean.valueOf(CC_CI03));
	}

	/** Get CC_CI03.
		@return CC_CI03	  */
	public boolean isCC_CI03 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI03);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI04.
		@param CC_CI04 CC_CI04	  */
	public void setCC_CI04 (boolean CC_CI04)
	{
		set_Value (COLUMNNAME_CC_CI04, Boolean.valueOf(CC_CI04));
	}

	/** Get CC_CI04.
		@return CC_CI04	  */
	public boolean isCC_CI04 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI04);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI05.
		@param CC_CI05 CC_CI05	  */
	public void setCC_CI05 (boolean CC_CI05)
	{
		set_Value (COLUMNNAME_CC_CI05, Boolean.valueOf(CC_CI05));
	}

	/** Get CC_CI05.
		@return CC_CI05	  */
	public boolean isCC_CI05 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI05);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI06.
		@param CC_CI06 CC_CI06	  */
	public void setCC_CI06 (boolean CC_CI06)
	{
		set_Value (COLUMNNAME_CC_CI06, Boolean.valueOf(CC_CI06));
	}

	/** Get CC_CI06.
		@return CC_CI06	  */
	public boolean isCC_CI06 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI06);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI07.
		@param CC_CI07 CC_CI07	  */
	public void setCC_CI07 (boolean CC_CI07)
	{
		set_Value (COLUMNNAME_CC_CI07, Boolean.valueOf(CC_CI07));
	}

	/** Get CC_CI07.
		@return CC_CI07	  */
	public boolean isCC_CI07 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI07);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI08.
		@param CC_CI08 CC_CI08	  */
	public void setCC_CI08 (boolean CC_CI08)
	{
		set_Value (COLUMNNAME_CC_CI08, Boolean.valueOf(CC_CI08));
	}

	/** Get CC_CI08.
		@return CC_CI08	  */
	public boolean isCC_CI08 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI08);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI09.
		@param CC_CI09 CC_CI09	  */
	public void setCC_CI09 (boolean CC_CI09)
	{
		set_Value (COLUMNNAME_CC_CI09, Boolean.valueOf(CC_CI09));
	}

	/** Get CC_CI09.
		@return CC_CI09	  */
	public boolean isCC_CI09 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI09);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI10.
		@param CC_CI10 CC_CI10	  */
	public void setCC_CI10 (boolean CC_CI10)
	{
		set_Value (COLUMNNAME_CC_CI10, Boolean.valueOf(CC_CI10));
	}

	/** Get CC_CI10.
		@return CC_CI10	  */
	public boolean isCC_CI10 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI10);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI11.
		@param CC_CI11 CC_CI11	  */
	public void setCC_CI11 (boolean CC_CI11)
	{
		set_Value (COLUMNNAME_CC_CI11, Boolean.valueOf(CC_CI11));
	}

	/** Get CC_CI11.
		@return CC_CI11	  */
	public boolean isCC_CI11 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI11);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI12.
		@param CC_CI12 CC_CI12	  */
	public void setCC_CI12 (boolean CC_CI12)
	{
		set_Value (COLUMNNAME_CC_CI12, Boolean.valueOf(CC_CI12));
	}

	/** Get CC_CI12.
		@return CC_CI12	  */
	public boolean isCC_CI12 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI12);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI13.
		@param CC_CI13 CC_CI13	  */
	public void setCC_CI13 (boolean CC_CI13)
	{
		set_Value (COLUMNNAME_CC_CI13, Boolean.valueOf(CC_CI13));
	}

	/** Get CC_CI13.
		@return CC_CI13	  */
	public boolean isCC_CI13 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI13);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI14.
		@param CC_CI14 CC_CI14	  */
	public void setCC_CI14 (boolean CC_CI14)
	{
		set_Value (COLUMNNAME_CC_CI14, Boolean.valueOf(CC_CI14));
	}

	/** Get CC_CI14.
		@return CC_CI14	  */
	public boolean isCC_CI14 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI14);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI15.
		@param CC_CI15 CC_CI15	  */
	public void setCC_CI15 (boolean CC_CI15)
	{
		set_Value (COLUMNNAME_CC_CI15, Boolean.valueOf(CC_CI15));
	}

	/** Get CC_CI15.
		@return CC_CI15	  */
	public boolean isCC_CI15 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI15);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI16.
		@param CC_CI16 CC_CI16	  */
	public void setCC_CI16 (boolean CC_CI16)
	{
		set_Value (COLUMNNAME_CC_CI16, Boolean.valueOf(CC_CI16));
	}

	/** Get CC_CI16.
		@return CC_CI16	  */
	public boolean isCC_CI16 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI16);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI17.
		@param CC_CI17 CC_CI17	  */
	public void setCC_CI17 (boolean CC_CI17)
	{
		set_Value (COLUMNNAME_CC_CI17, Boolean.valueOf(CC_CI17));
	}

	/** Get CC_CI17.
		@return CC_CI17	  */
	public boolean isCC_CI17 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI17);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI18.
		@param CC_CI18 CC_CI18	  */
	public void setCC_CI18 (boolean CC_CI18)
	{
		set_Value (COLUMNNAME_CC_CI18, Boolean.valueOf(CC_CI18));
	}

	/** Get CC_CI18.
		@return CC_CI18	  */
	public boolean isCC_CI18 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI18);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI19.
		@param CC_CI19 CC_CI19	  */
	public void setCC_CI19 (boolean CC_CI19)
	{
		set_Value (COLUMNNAME_CC_CI19, Boolean.valueOf(CC_CI19));
	}

	/** Get CC_CI19.
		@return CC_CI19	  */
	public boolean isCC_CI19 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI19);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI20.
		@param CC_CI20 CC_CI20	  */
	public void setCC_CI20 (boolean CC_CI20)
	{
		set_Value (COLUMNNAME_CC_CI20, Boolean.valueOf(CC_CI20));
	}

	/** Get CC_CI20.
		@return CC_CI20	  */
	public boolean isCC_CI20 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI20);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI21.
		@param CC_CI21 CC_CI21	  */
	public void setCC_CI21 (boolean CC_CI21)
	{
		set_Value (COLUMNNAME_CC_CI21, Boolean.valueOf(CC_CI21));
	}

	/** Get CC_CI21.
		@return CC_CI21	  */
	public boolean isCC_CI21 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI21);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI22.
		@param CC_CI22 CC_CI22	  */
	public void setCC_CI22 (boolean CC_CI22)
	{
		set_Value (COLUMNNAME_CC_CI22, Boolean.valueOf(CC_CI22));
	}

	/** Get CC_CI22.
		@return CC_CI22	  */
	public boolean isCC_CI22 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI22);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI23.
		@param CC_CI23 CC_CI23	  */
	public void setCC_CI23 (boolean CC_CI23)
	{
		set_Value (COLUMNNAME_CC_CI23, Boolean.valueOf(CC_CI23));
	}

	/** Get CC_CI23.
		@return CC_CI23	  */
	public boolean isCC_CI23 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI23);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI24.
		@param CC_CI24 CC_CI24	  */
	public void setCC_CI24 (boolean CC_CI24)
	{
		set_Value (COLUMNNAME_CC_CI24, Boolean.valueOf(CC_CI24));
	}

	/** Get CC_CI24.
		@return CC_CI24	  */
	public boolean isCC_CI24 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI24);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI25.
		@param CC_CI25 CC_CI25	  */
	public void setCC_CI25 (boolean CC_CI25)
	{
		set_Value (COLUMNNAME_CC_CI25, Boolean.valueOf(CC_CI25));
	}

	/** Get CC_CI25.
		@return CC_CI25	  */
	public boolean isCC_CI25 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI25);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI26.
		@param CC_CI26 CC_CI26	  */
	public void setCC_CI26 (boolean CC_CI26)
	{
		set_Value (COLUMNNAME_CC_CI26, Boolean.valueOf(CC_CI26));
	}

	/** Get CC_CI26.
		@return CC_CI26	  */
	public boolean isCC_CI26 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI26);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI27.
		@param CC_CI27 CC_CI27	  */
	public void setCC_CI27 (boolean CC_CI27)
	{
		set_Value (COLUMNNAME_CC_CI27, Boolean.valueOf(CC_CI27));
	}

	/** Get CC_CI27.
		@return CC_CI27	  */
	public boolean isCC_CI27 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI27);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI28.
		@param CC_CI28 CC_CI28	  */
	public void setCC_CI28 (boolean CC_CI28)
	{
		set_Value (COLUMNNAME_CC_CI28, Boolean.valueOf(CC_CI28));
	}

	/** Get CC_CI28.
		@return CC_CI28	  */
	public boolean isCC_CI28 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI28);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI29.
		@param CC_CI29 CC_CI29	  */
	public void setCC_CI29 (boolean CC_CI29)
	{
		set_Value (COLUMNNAME_CC_CI29, Boolean.valueOf(CC_CI29));
	}

	/** Get CC_CI29.
		@return CC_CI29	  */
	public boolean isCC_CI29 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI29);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI30.
		@param CC_CI30 CC_CI30	  */
	public void setCC_CI30 (boolean CC_CI30)
	{
		set_Value (COLUMNNAME_CC_CI30, Boolean.valueOf(CC_CI30));
	}

	/** Get CC_CI30.
		@return CC_CI30	  */
	public boolean isCC_CI30 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI30);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI31.
		@param CC_CI31 CC_CI31	  */
	public void setCC_CI31 (boolean CC_CI31)
	{
		set_Value (COLUMNNAME_CC_CI31, Boolean.valueOf(CC_CI31));
	}

	/** Get CC_CI31.
		@return CC_CI31	  */
	public boolean isCC_CI31 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI31);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI32.
		@param CC_CI32 CC_CI32	  */
	public void setCC_CI32 (boolean CC_CI32)
	{
		set_Value (COLUMNNAME_CC_CI32, Boolean.valueOf(CC_CI32));
	}

	/** Get CC_CI32.
		@return CC_CI32	  */
	public boolean isCC_CI32 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI32);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI33.
		@param CC_CI33 CC_CI33	  */
	public void setCC_CI33 (boolean CC_CI33)
	{
		set_Value (COLUMNNAME_CC_CI33, Boolean.valueOf(CC_CI33));
	}

	/** Get CC_CI33.
		@return CC_CI33	  */
	public boolean isCC_CI33 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI33);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI34.
		@param CC_CI34 CC_CI34	  */
	public void setCC_CI34 (boolean CC_CI34)
	{
		set_Value (COLUMNNAME_CC_CI34, Boolean.valueOf(CC_CI34));
	}

	/** Get CC_CI34.
		@return CC_CI34	  */
	public boolean isCC_CI34 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI34);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI35.
		@param CC_CI35 CC_CI35	  */
	public void setCC_CI35 (boolean CC_CI35)
	{
		set_Value (COLUMNNAME_CC_CI35, Boolean.valueOf(CC_CI35));
	}

	/** Get CC_CI35.
		@return CC_CI35	  */
	public boolean isCC_CI35 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI35);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI36.
		@param CC_CI36 CC_CI36	  */
	public void setCC_CI36 (boolean CC_CI36)
	{
		set_Value (COLUMNNAME_CC_CI36, Boolean.valueOf(CC_CI36));
	}

	/** Get CC_CI36.
		@return CC_CI36	  */
	public boolean isCC_CI36 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI36);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI37.
		@param CC_CI37 CC_CI37	  */
	public void setCC_CI37 (boolean CC_CI37)
	{
		set_Value (COLUMNNAME_CC_CI37, Boolean.valueOf(CC_CI37));
	}

	/** Get CC_CI37.
		@return CC_CI37	  */
	public boolean isCC_CI37 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI37);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI38.
		@param CC_CI38 CC_CI38	  */
	public void setCC_CI38 (boolean CC_CI38)
	{
		set_Value (COLUMNNAME_CC_CI38, Boolean.valueOf(CC_CI38));
	}

	/** Get CC_CI38.
		@return CC_CI38	  */
	public boolean isCC_CI38 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI38);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI39.
		@param CC_CI39 CC_CI39	  */
	public void setCC_CI39 (boolean CC_CI39)
	{
		set_Value (COLUMNNAME_CC_CI39, Boolean.valueOf(CC_CI39));
	}

	/** Get CC_CI39.
		@return CC_CI39	  */
	public boolean isCC_CI39 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI39);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI40.
		@param CC_CI40 CC_CI40	  */
	public void setCC_CI40 (boolean CC_CI40)
	{
		set_Value (COLUMNNAME_CC_CI40, Boolean.valueOf(CC_CI40));
	}

	/** Get CC_CI40.
		@return CC_CI40	  */
	public boolean isCC_CI40 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI40);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI41.
		@param CC_CI41 CC_CI41	  */
	public void setCC_CI41 (boolean CC_CI41)
	{
		set_Value (COLUMNNAME_CC_CI41, Boolean.valueOf(CC_CI41));
	}

	/** Get CC_CI41.
		@return CC_CI41	  */
	public boolean isCC_CI41 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI41);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI42.
		@param CC_CI42 CC_CI42	  */
	public void setCC_CI42 (boolean CC_CI42)
	{
		set_Value (COLUMNNAME_CC_CI42, Boolean.valueOf(CC_CI42));
	}

	/** Get CC_CI42.
		@return CC_CI42	  */
	public boolean isCC_CI42 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI42);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI43.
		@param CC_CI43 CC_CI43	  */
	public void setCC_CI43 (boolean CC_CI43)
	{
		set_Value (COLUMNNAME_CC_CI43, Boolean.valueOf(CC_CI43));
	}

	/** Get CC_CI43.
		@return CC_CI43	  */
	public boolean isCC_CI43 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI43);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI44.
		@param CC_CI44 CC_CI44	  */
	public void setCC_CI44 (boolean CC_CI44)
	{
		set_Value (COLUMNNAME_CC_CI44, Boolean.valueOf(CC_CI44));
	}

	/** Get CC_CI44.
		@return CC_CI44	  */
	public boolean isCC_CI44 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI44);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI45.
		@param CC_CI45 CC_CI45	  */
	public void setCC_CI45 (boolean CC_CI45)
	{
		set_Value (COLUMNNAME_CC_CI45, Boolean.valueOf(CC_CI45));
	}

	/** Get CC_CI45.
		@return CC_CI45	  */
	public boolean isCC_CI45 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI45);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI46.
		@param CC_CI46 CC_CI46	  */
	public void setCC_CI46 (boolean CC_CI46)
	{
		set_Value (COLUMNNAME_CC_CI46, Boolean.valueOf(CC_CI46));
	}

	/** Get CC_CI46.
		@return CC_CI46	  */
	public boolean isCC_CI46 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI46);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI47.
		@param CC_CI47 CC_CI47	  */
	public void setCC_CI47 (boolean CC_CI47)
	{
		set_Value (COLUMNNAME_CC_CI47, Boolean.valueOf(CC_CI47));
	}

	/** Get CC_CI47.
		@return CC_CI47	  */
	public boolean isCC_CI47 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI47);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI48.
		@param CC_CI48 CC_CI48	  */
	public void setCC_CI48 (boolean CC_CI48)
	{
		set_Value (COLUMNNAME_CC_CI48, Boolean.valueOf(CC_CI48));
	}

	/** Get CC_CI48.
		@return CC_CI48	  */
	public boolean isCC_CI48 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI48);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI49.
		@param CC_CI49 CC_CI49	  */
	public void setCC_CI49 (boolean CC_CI49)
	{
		set_Value (COLUMNNAME_CC_CI49, Boolean.valueOf(CC_CI49));
	}

	/** Get CC_CI49.
		@return CC_CI49	  */
	public boolean isCC_CI49 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI49);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI50.
		@param CC_CI50 CC_CI50	  */
	public void setCC_CI50 (boolean CC_CI50)
	{
		set_Value (COLUMNNAME_CC_CI50, Boolean.valueOf(CC_CI50));
	}

	/** Get CC_CI50.
		@return CC_CI50	  */
	public boolean isCC_CI50 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI50);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI51.
		@param CC_CI51 CC_CI51	  */
	public void setCC_CI51 (boolean CC_CI51)
	{
		set_Value (COLUMNNAME_CC_CI51, Boolean.valueOf(CC_CI51));
	}

	/** Get CC_CI51.
		@return CC_CI51	  */
	public boolean isCC_CI51 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI51);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI52.
		@param CC_CI52 CC_CI52	  */
	public void setCC_CI52 (boolean CC_CI52)
	{
		set_Value (COLUMNNAME_CC_CI52, Boolean.valueOf(CC_CI52));
	}

	/** Get CC_CI52.
		@return CC_CI52	  */
	public boolean isCC_CI52 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI52);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI53.
		@param CC_CI53 CC_CI53	  */
	public void setCC_CI53 (boolean CC_CI53)
	{
		set_Value (COLUMNNAME_CC_CI53, Boolean.valueOf(CC_CI53));
	}

	/** Get CC_CI53.
		@return CC_CI53	  */
	public boolean isCC_CI53 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI53);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI54.
		@param CC_CI54 CC_CI54	  */
	public void setCC_CI54 (boolean CC_CI54)
	{
		set_Value (COLUMNNAME_CC_CI54, Boolean.valueOf(CC_CI54));
	}

	/** Get CC_CI54.
		@return CC_CI54	  */
	public boolean isCC_CI54 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI54);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI55.
		@param CC_CI55 CC_CI55	  */
	public void setCC_CI55 (boolean CC_CI55)
	{
		set_Value (COLUMNNAME_CC_CI55, Boolean.valueOf(CC_CI55));
	}

	/** Get CC_CI55.
		@return CC_CI55	  */
	public boolean isCC_CI55 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI55);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI56.
		@param CC_CI56 CC_CI56	  */
	public void setCC_CI56 (boolean CC_CI56)
	{
		set_Value (COLUMNNAME_CC_CI56, Boolean.valueOf(CC_CI56));
	}

	/** Get CC_CI56.
		@return CC_CI56	  */
	public boolean isCC_CI56 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI56);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI57.
		@param CC_CI57 CC_CI57	  */
	public void setCC_CI57 (boolean CC_CI57)
	{
		set_Value (COLUMNNAME_CC_CI57, Boolean.valueOf(CC_CI57));
	}

	/** Get CC_CI57.
		@return CC_CI57	  */
	public boolean isCC_CI57 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI57);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI58.
		@param CC_CI58 CC_CI58	  */
	public void setCC_CI58 (boolean CC_CI58)
	{
		set_Value (COLUMNNAME_CC_CI58, Boolean.valueOf(CC_CI58));
	}

	/** Get CC_CI58.
		@return CC_CI58	  */
	public boolean isCC_CI58 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI58);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI59.
		@param CC_CI59 CC_CI59	  */
	public void setCC_CI59 (boolean CC_CI59)
	{
		set_Value (COLUMNNAME_CC_CI59, Boolean.valueOf(CC_CI59));
	}

	/** Get CC_CI59.
		@return CC_CI59	  */
	public boolean isCC_CI59 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI59);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CI60.
		@param CC_CI60 CC_CI60	  */
	public void setCC_CI60 (boolean CC_CI60)
	{
		set_Value (COLUMNNAME_CC_CI60, Boolean.valueOf(CC_CI60));
	}

	/** Get CC_CI60.
		@return CC_CI60	  */
	public boolean isCC_CI60 () 
	{
		Object oo = get_Value(COLUMNNAME_CC_CI60);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CC_CollectiveIntelligence ID.
		@param CC_CollectiveIntelligence_ID CC_CollectiveIntelligence ID	  */
	public void setCC_CollectiveIntelligence_ID (int CC_CollectiveIntelligence_ID)
	{
		if (CC_CollectiveIntelligence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_CollectiveIntelligence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_CollectiveIntelligence_ID, Integer.valueOf(CC_CollectiveIntelligence_ID));
	}

	/** Get CC_CollectiveIntelligence ID.
		@return CC_CollectiveIntelligence ID	  */
	public int getCC_CollectiveIntelligence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_CollectiveIntelligence_ID);
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

	/** Set Date.
		@param Date1 
		Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Date.
		@return Date when business is not conducted
	  */
	public Timestamp getDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date1);
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
}