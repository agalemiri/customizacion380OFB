/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is Compiere ERP & CRM Smart Business Solution. The Initial
 * Developer of the Original Code is Jorg Janke. Portions created by Jorg Janke
 * are Copyright (C) 1999-2005 Jorg Janke.
 * All parts are Copyright (C) 1999-2005 ComPiere, Inc.  All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.ofb.model;

import java.sql.Timestamp;
import java.util.*;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MUser;

import org.compiere.util.Env;

/**
 *	Requisition Callout
 *	
 *  @author mfrojas
 *  @version $Id: CalloutRHAdministrativeRequests.java,v 2.0 2012/12/03  Exp $
 */
public class CalloutAuthorizations extends CalloutEngine
{

	 
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
			 mTab.setValue("UpdatedSignature1", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature1", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature1", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature1", usr.getC_BPartner().getName());


		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature1", null);
			 mTab.setValue("UpdatedBySignature1", null);

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
			 mTab.setValue("UpdatedSignature2", new Timestamp(System.currentTimeMillis()));
//			 mTab.setValue("UpdatedBySignature2", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature2", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature2", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature2", null);
			 mTab.setValue("UpdatedBySignature2", null);

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
			 mTab.setValue("UpdatedSignature3", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature3", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature3", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature3", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature3", null);
			 mTab.setValue("UpdatedBySignature3", null);

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
			 mTab.setValue("UpdatedSignature4", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature4", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature4", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature4", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature4", null);
			 mTab.setValue("UpdatedBySignature4", null);

		 }
		
		return "";
	}
	
	public String Signature5 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature5", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature5", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature5", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature5", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature5", null);
			 mTab.setValue("UpdatedBySignature5", null);

		 }

		 return "";
	}	
	
	public String Signature6 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature6", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature6", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature6", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature6", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature6", null);
			 mTab.setValue("UpdatedBySignature6", null);

		 }
		
		return "";
	}	
	
	public String Signature7 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature7", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature7", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature7", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature7", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature7",null);
			 mTab.setValue("UpdatedBySignature7", null);

		 }
		
		return "";
	}
	
	public String Signature8 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature8", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature8", Env.getAD_User_ID(Env.getCtx()));
			 
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature8", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature8", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature8", null);
			 mTab.setValue("UpdatedBySignature8", null);

		 }

		 return "";
	}	
	
	public String Signature9 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature9", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature9", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature9", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature9", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature9", null);
			 mTab.setValue("UpdatedBySignature9", null);

		 }
		
		return "";
	}	
	
	public String Signature10 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature10", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature10", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature10", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature10", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature10", null);
			 mTab.setValue("UpdatedBySignature10", null);

		 }
		
		return "";
	}
	public String Signature11 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature11", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature11", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature11", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature11", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature11", null);
			 mTab.setValue("UpdatedBySignature11", null);

		 }

		 
		 return "";
	}	
	
	public String Signature12 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature12", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature12", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature12", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature12", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature12", null);
			 mTab.setValue("UpdatedBySignature12", null);

		 }
		
		return "";
	}	
	
	public String Signature13 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature13", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature13", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature13", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature13", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature13",null);
			 mTab.setValue("UpdatedBySignature13",null);

		 }
		
		return "";
	}
	public String Signature14 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature14", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature14", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature14", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature14", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature14", null);
			 mTab.setValue("UpdatedBySignature14", null);

		 }

		 return "";
	}	
	
	public String Signature15 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
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
			 mTab.setValue("UpdatedSignature15", new Timestamp(System.currentTimeMillis()));
			 //mTab.setValue("UpdatedBySignature15", Env.getAD_User_ID(Env.getCtx()));
			 MUser usr = new  MUser(ctx,Env.getAD_User_ID(Env.getCtx()),null );
			 if(Env.getAD_User_ID(Env.getCtx())==100)
				 mTab.setValue("UpdatedBySignature15", "SuperUser");
			 else
				 mTab.setValue("UpdatedBySignature15", usr.getC_BPartner().getName());

		 }
		 else
		 {
			 mTab.setValue("UpdatedSignature15", null);
			 mTab.setValue("UpdatedBySignature15", null);

		 }
		
		return "";
	}	
		

}


