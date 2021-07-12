/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.clinicacolonial.model;

import java.sql.Timestamp;
import java.util.Properties;

import oracle.Column;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MColumn;
import org.compiere.model.X_CC_CollectiveIntelligence;
import org.compiere.model.X_CC_Hospitalization;
import org.compiere.model.X_CC_MedicalOrder;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	CColonial Callouts business partner.
 *	
 *  @author Italo Niñoles
 *  @version $Id: CalloutCOPESACreditCard.java,v 1.5 2016/04/25 
 *  
 */
public class CalloutCCBPartner extends CalloutEngine
{
	/**
	 *
	 */
	public String Signature1 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null)
			return "";
		
		boolean Signature;		
		if (value instanceof Boolean) 
			Signature = ((Boolean)value).booleanValue(); 
		else
			Signature = "Y".equals(value);
		 
		if(Signature)
		{
			 int UserID = Env.getAD_User_ID(Env.getCtx());
			 if (UserID > 0)
				 mTab.setValue("UpdatedByS1_ID", Env.getAD_User_ID(Env.getCtx()));
			 mTab.setValue("UpdatedSignature1", new Timestamp(System.currentTimeMillis()));			 
		}		
		return "";
	}	
	public String Signature2 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null)
			return "";
		
		boolean Signature;		
		if (value instanceof Boolean) 
			Signature = ((Boolean)value).booleanValue(); 
		else
			Signature = "Y".equals(value);
		 
		if(Signature)
		{
			 int UserID = Env.getAD_User_ID(Env.getCtx());
			 if (UserID > 0)
				 mTab.setValue("UpdatedByS2_ID", Env.getAD_User_ID(Env.getCtx()));
			 mTab.setValue("UpdatedSignature2", new Timestamp(System.currentTimeMillis()));			 
		}		
		return "";
	}	
	public String Signature3 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null)
			return "";
		
		boolean Signature;		
		if (value instanceof Boolean) 
			Signature = ((Boolean)value).booleanValue(); 
		else
			Signature = "Y".equals(value);
		 
		if(Signature)
		{
			 int UserID = Env.getAD_User_ID(Env.getCtx());
			 if (UserID > 0)
				 mTab.setValue("UpdatedByS3_ID", Env.getAD_User_ID(Env.getCtx()));
			 mTab.setValue("UpdatedSignature3", new Timestamp(System.currentTimeMillis()));			 
		}		
		return "";
	}
	public String Signature4 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null)
			return "";
		
		boolean Signature;		
		if (value instanceof Boolean) 
			Signature = ((Boolean)value).booleanValue(); 
		else
			Signature = "Y".equals(value);
		 
		if(Signature)
		{
			 int UserID = Env.getAD_User_ID(Env.getCtx());
			 if (UserID > 0)
				 mTab.setValue("UpdatedByS4_ID", Env.getAD_User_ID(Env.getCtx()));
			 mTab.setValue("UpdatedSignature4", new Timestamp(System.currentTimeMillis()));			 
		}		
		return "";
	}	
	public String CollectiveIntelligence(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null)
			return "";
		
		String fieldsCI[]= {"CC_CI01","CC_CI02","CC_CI03","CC_CI04","CC_CI05","CC_CI06","CC_CI07","CC_CI08","CC_CI09","CC_CI10",
				"CC_CI11","CC_CI12","CC_CI13","CC_CI14","CC_CI15","CC_CI16","CC_CI17","CC_CI18","CC_CI19","CC_CI20",
				"CC_CI21","CC_CI22","CC_CI23","CC_CI24","CC_CI25","CC_CI26","CC_CI27","CC_CI28","CC_CI29","CC_CI30",
				"CC_CI31","CC_CI32","CC_CI33","CC_CI34","CC_CI35","CC_CI36","CC_CI37","CC_CI38","CC_CI39","CC_CI40",
				"CC_CI41","CC_CI42","CC_CI43","CC_CI44","CC_CI45","CC_CI46","CC_CI47","CC_CI48","CC_CI49","CC_CI50",
				"CC_CI51","CC_CI52","CC_CI53","CC_CI54","CC_CI55","CC_CI56","CC_CI57","CC_CI58","CC_CI59","CC_CI60"};
		StringBuilder Desc = new StringBuilder();
		for(int a=0; a<fieldsCI.length; a++)
		{	
			if(mTab.getValueAsBoolean(fieldsCI[a]))
			{
				int ID_Column = DB.getSQLValue(null, "SELECT AD_Column_ID FROM " +
						" AD_Column WHERE AD_Table_ID = "+X_CC_CollectiveIntelligence.Table_ID+
						" AND ColumnName = '"+fieldsCI[a]+"'");
				if(ID_Column > 0)
				{
					MColumn colum = new MColumn(Env.getCtx(),ID_Column,null);
					String textAux = "";			
					if(colum.getDescription() != null && !Desc.toString().contains(colum.getDescription()))
						textAux = textAux+colum.getDescription()+":"+"\n";
					if(colum.getHelp() != null)
						textAux=textAux+colum.getHelp();
					if(textAux != null && textAux.trim().length() > 1)
					{
						Desc.append(textAux);
						Desc.append(" - ");
						Desc.append("\n");
					}
				}
			}	
		}
		if(Desc != null && Desc.toString().trim().length() > 1)
			mTab.setValue("Description", Desc.toString());
		return "";
	}
	
	public String MedicalOrder(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null)
			return "";
		
		String fieldsCI[]= {"Signature1","Description1","Signature2","Description2","Signature3","Description3","Signature4","Description4",
				"Signature5","Description5",
				"Signature6","Description6","Signature7","Description7","Signature8","Description8","Signature9","Description9","Signature10","Description10",
				"Signature11","Description11","Signature12","Description12","Signature13","Description13",
				"Signature14","Description14","Signature15","Description15"};
		StringBuilder Desc = new StringBuilder();
		for(int a=0; a<fieldsCI.length; a++)
		{	
			if(fieldsCI[a].contains("Signature"))
			{	
				if(mTab.getValueAsBoolean(fieldsCI[a]))
				{
					int ID_Column = DB.getSQLValue(null, "SELECT AD_Column_ID FROM " +
							" AD_Column WHERE AD_Table_ID = "+X_CC_MedicalOrder.Table_ID+
							" AND ColumnName = '"+fieldsCI[a]+"'");
					if(ID_Column > 0)
					{
						MColumn colum = new MColumn(Env.getCtx(),ID_Column,null);
						String textAux = "";			
						if(colum.getDescription() != null && !Desc.toString().contains(colum.getDescription()))
							textAux = textAux+colum.getDescription()+":"+"\n";
						if(colum.getHelp() != null)
							textAux=textAux+colum.getHelp();
						if(textAux != null && textAux.trim().length() > 1)
						{
							Desc.append(textAux);
							Desc.append(" - ");
							Desc.append("\n");
						}
					}
				}	
			}
			else if(fieldsCI[a].contains("Description"))
			{
				if(mTab.getValue(fieldsCI[a]) != null)
				{
					int ID_Column = DB.getSQLValue(null, "SELECT AD_Column_ID FROM " +
							" AD_Column WHERE AD_Table_ID = "+X_CC_MedicalOrder.Table_ID+
							" AND ColumnName = '"+fieldsCI[a]+"'");
					if(ID_Column > 0)
					{
						MColumn colum = new MColumn(Env.getCtx(),ID_Column,null);
						String textAux = "";			
						if(colum.getDescription() != null && !Desc.toString().contains(colum.getDescription()))
							textAux = textAux+colum.getDescription()+":"+"\n";
						textAux = textAux+mTab.getValue(fieldsCI[a].toString());
						if(textAux != null && textAux.trim().length() > 1)
						{
							Desc.append(textAux);
							Desc.append(" - ");
							Desc.append("\n");
						}
					}
				}
			}
			
		}
		if(Desc != null && Desc.toString().trim().length() > 1)
			mTab.setValue("Description", Desc.toString());
		return "";
	}	

}	//	

