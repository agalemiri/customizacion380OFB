/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
package org.cencosud.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: ProcessVendorCenco.java,v 1.2 2011/06/12 00:51:01  $
 *  
 */
public class ProcessVendorCenco extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private int 			p_ID_Vendor;
	
	protected void prepare()
	{	
		p_ID_Vendor = getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		BigDecimal sum = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(Percentage) FROM M_VendorCencosudLine" +
				" WHERE IsActive = 'Y' AND M_VendorCencosud_ID = "+p_ID_Vendor);
		String msg = "";
		if(sum != null && sum.compareTo(Env.ONEHUNDRED) == 0)
		{
			DB.executeUpdate("UPDATE M_VendorCencosud SET IsVAlid = 'Y' WHERE M_VendorCencosud_ID="+p_ID_Vendor, get_TrxName());
			msg = "Validado";
		}
		else
		{
			DB.executeUpdate("UPDATE M_VendorCencosud SET IsVAlid = 'N' WHERE M_VendorCencosud_ID="+p_ID_Vendor, get_TrxName());
			msg = "ERROR: No se puede validar. Porcentaje Asignado="+sum+"%";
			throw new AdempiereException("ERROR: No se puede validar. Porcentaje Asignado="+sum.intValue()+"%");
		}
		
		return msg;		
	}	//	doIt	
}	//	

