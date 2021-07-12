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
package org.minju.process;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 *  @author Italo Niñoles ininoles
 *  @version $Id: MailBoletaGarantia.java $
 */
public class MailBoletaGarantia extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{

	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		int cant=0;
		String sql = "SELECT * FROM DM_Document WHERE SecondDateReminder = ? OR FirstDateReminder = ?";
		try
		{
			//calculo de fechas parametro
			Calendar calendario = Calendar.getInstance();
	        calendario.add(Calendar.DATE, -30);
	        Timestamp date30 = new Timestamp(calendario.getTimeInMillis());
	        calendario.add(Calendar.DATE, -30);
	        Timestamp date60 = new Timestamp(calendario.getTimeInMillis());
	        
			PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setTimestamp(1, date30);
			pstmt.setTimestamp(2, date60);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				//envio de correo
				log.config("Correo Enviado ID boleta: "+rs.getInt("DM_Document_ID"));
				//mandacorreo
				CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
				log.config("id "+rs.getInt("DM_Document_ID"));
				cst.setInt(1, Env.getAD_Client_ID(getCtx()));
				cst.setInt(2, 23);//cambiar por el que corresponda ININOLES // Cambiado mfrojas
				cst.setString(3, "-");
				cst.setInt(4,rs.getInt("DM_Document_ID"));
				cst.execute();
				cant++;
			}
		}catch (Exception e) {
			log.config("Error enviando Correo");
		}
		return "Se han enviado "+cant+ " correos";
	}	//	doIt
}
