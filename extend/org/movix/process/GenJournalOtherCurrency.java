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
package org.movix.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MGLCategory;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalBatch;
import org.compiere.model.MJournalLine;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.ofb.utils.DateUtils;
/**
 *	
 *	
 *  @author Italo Niñoles ininoles 
 *  @version $Id: GenJournalOtherCurrency,java $
 */
public class GenJournalOtherCurrency extends SvrProcess
{
	private Timestamp				p_dateAcct; 
	private Timestamp				p_dateAcctTo;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("DateAcct"))
			{
				p_dateAcct = para[i].getParameterAsTimestamp();
				p_dateAcctTo = para[i].getParameterToAsTimestamp();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		String sqlJournal = "SELECT C_Currency_ID,TotalDR,DateAcct,AD_Org_ID FROM GL_Journal " +
				" WHERE DocStatus IN ('CO','CL') AND AD_Client_ID = "+Env.getAD_Client_ID(getCtx())+" AND C_Currency_ID <> 228 AND Description NOT LIKE '%Diferencia moneda%' AND DateAcct BETWEEN ? AND ? ";
		log.config("sqljournal "+sqlJournal);
		PreparedStatement pstmt = DB.prepareStatement(sqlJournal, get_TrxName());
		pstmt.setTimestamp(1, p_dateAcct);
		pstmt.setTimestamp(2, p_dateAcctTo);		
		ResultSet rs = pstmt.executeQuery ();
		BigDecimal amtDif = Env.ZERO;		
		BigDecimal amtAux = Env.ZERO;
		BigDecimal amtAcum = Env.ZERO;
		BigDecimal MRate = Env.ZERO; 
		BigDecimal MRateToday = Env.ZERO;
		while (rs.next())
		{
			MRate = MConversionRate.getRate(rs.getInt("C_Currency_ID"),228,rs.getTimestamp("DateAcct"), 
					114,Env.getAD_Client_ID(Env.getCtx()),rs.getInt("AD_Org_ID"));
			if(MRate == null)
				MRate = Env.ZERO;
			amtDif = rs.getBigDecimal("TotalDR").multiply(MRate);						
			MRateToday = MConversionRate.getRate(rs.getInt("C_Currency_ID"),228,DateUtils.today(), 
					114,Env.getAD_Client_ID(Env.getCtx()),rs.getInt("AD_Org_ID"));
			if(MRateToday == null)
				MRateToday = Env.ZERO;
			amtAux = rs.getBigDecimal("TotalDR").multiply(MRateToday);			
			amtDif = amtDif.subtract(amtAux);
			amtAcum = amtAcum.add(amtDif);
		}
		if(amtAcum.compareTo(Env.ZERO) != 0)
		{
			//se crea journal batch
			MJournalBatch batch = new MJournalBatch(getCtx(),0,get_TrxName());
			batch.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
			batch.setDescription("Diferencia moneda : "+DateUtils.today());
			batch.setPostingType("A");
			batch.setC_DocType_ID(MDocType.getOfDocBaseType(getCtx(), "GLJ")[0].getC_DocType_ID());
			batch.setGL_Category_ID(MGLCategory.getDefault(getCtx(), MGLCategory.CATEGORYTYPE_Manual).getGL_Category_ID());
			batch.setDateAcct(DateUtils.today());
			batch.setDateDoc(DateUtils.today());
			batch.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(),DateUtils.today(),Env.getAD_Org_ID(getCtx())));
			MClient client= MClient.get(getCtx(), Env.getAD_Client_ID(getCtx()));
			batch.setC_Currency_ID(client.getC_Currency_ID());		
			batch.save(get_TrxName());
			//se genera journal
			MJournal journal = new MJournal(batch);					
			journal.setDescription("Diferencia moneda : "+DateUtils.today());
			journal.setC_AcctSchema_ID(MAcctSchema.getClientAcctSchema(getCtx(), Env.getAD_Client_ID(getCtx()))[0].getC_AcctSchema_ID() );
			journal.setGL_Category_ID(MGLCategory.getDefault(getCtx(), MGLCategory.CATEGORYTYPE_Document).getGL_Category_ID() );
			journal.setC_ConversionType_ID(114);
			journal.save(get_TrxName());
			int ID_Debito= DB.getSQLValue(get_TrxName(), "SELECT PJ_Asset_Acct FROM C_AcctSchema_Default WHERE AD_Client_ID = "+Env.getAD_Client_ID(getCtx()));
			int ID_Credito= DB.getSQLValue(get_TrxName(), "SELECT PJ_WIP_Acct FROM C_AcctSchema_Default WHERE AD_Client_ID ="+Env.getAD_Client_ID(getCtx()));
			
			//linea debito
			MJournalLine line1= new MJournalLine(journal);
			line1.setAmtSourceDr(amtAcum.abs());
			line1.setAmtSourceCr(Env.ZERO);
			line1.setAmtAcct(amtAcum.abs(), Env.ZERO);
			line1.setC_ValidCombination_ID(ID_Debito);
			line1.save();
			
			//linea Credito
			MJournalLine line2= new MJournalLine(journal);
			line2.setAmtSourceDr(Env.ZERO);
			line2.setAmtSourceCr(amtAcum.abs());
			line2.setAmtAcct(Env.ZERO,amtAcum.abs());
			line2.setC_ValidCombination_ID(ID_Credito);
			line2.save();
		}
		return "Procesado";
	}	//	doIt
}
