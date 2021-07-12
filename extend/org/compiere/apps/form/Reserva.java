/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps.form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
//import org.compiere.grid.ed.VComboBox;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MPayment;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.model.X_MED_Schedule;
import org.compiere.model.X_MED_Appointment;
import org.compiere.model.X_MED_ScheduleDay;
import org.compiere.model.X_MED_ScheduleTime;
import org.compiere.model.X_CC_Hospitalization;
/*import org.pdi.jesan.model.X_JES_Cita;
import org.pdi.jesan.model.X_JES_CitaHora;
import org.pdi.jesan.model.X_JES_Hora;
import org.pdi.jesan.model.X_JES_HorarioDia;
*/
public class Reserva
{
	public DecimalFormat format = DisplayType.getNumberFormat(DisplayType.Amount);

	/**	Logger			*/
	public static CLogger log = CLogger.getCLogger(Reserva.class);

	public int         	m_C_Currency_ID = 0;
	public int         	m_C_BPartner_ID = 0;
	private int         m_noInvoices = 0;
	private int         m_noPayments = 0;
	public BigDecimal	totalInv = new BigDecimal(0.0);
	public BigDecimal 	totalPay = new BigDecimal(0.0);
	public BigDecimal	totalDiff = new BigDecimal(0.0);
	
	public Timestamp allocDate = null;

	//  Index	changed if multi-currency
	private int         i_payment = 7;
	//
	private int         i_open = 6;
	private int         i_discount = 7;
	private int         i_writeOff = 8; 
	private int         i_applied = 9;
	public int         	m_AD_Org_ID = 0;

	public void dynInit() throws Exception
	{
		m_C_Currency_ID = Env.getContextAsInt(Env.getCtx(), "$C_Currency_ID");   //  default
		//
		log.info("Currency=" + m_C_Currency_ID);
		
		m_AD_Org_ID = Env.getAD_Org_ID(Env.getCtx());
	}
	
	
	public Vector<Vector<Object>> gethorasData(Object tratante, Object date, Object especialidad, IMiniTable paymentTable)
	{		
		int flag = 0;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuffer sql = new StringBuffer("SELECT hh.timerequested,hh.med_specialty_id, h.c_bpartnermed_id ,esp.name, " +
				" tr.name," +
				//" tr.apellidopaterno,tr.apellidomaterno, " +
				" h.type, hh.med_scheduletime_id, hh.state, hh.Description   " +
				" from  MED_ScheduleTime hh " +
				" Inner Join MED_ScheduleDay p on (hh.MED_ScheduleDay_ID=p.MED_ScheduleDay_ID) " +
				" Inner join MED_Schedule h on (p.MED_Schedule_ID=h.MED_Schedule_ID) " +
				" Inner Join MED_Specialty esp on (h.MED_Specialty_ID=esp.MED_Specialty_ID)" +
				" Inner join C_BPartner tr on (h.C_BPartnerMed_ID=tr.C_BPartner_ID)" +
				//" WHERE (p.DateTrx = ? or p.DateTrx - cast('1 day' as interval) = ?)" +
				" WHERE (p.DateTrx = ? or p.DateTrx between ? and (?::date + cast('10 day' as interval)))" +
				" and h.MED_Specialty_ID=? " +
				" and h.C_BPartnerMed_ID=? " +
				" AND hh.state='DI'" +
				" order by hh.timerequested ");
		if(tratante == null)
		{
			sql = new StringBuffer("SELECT hh.timerequested,hh.med_specialty_id, h.c_bpartnermed_id ,esp.name, " +
					" tr.name," +
					//" tr.apellidopaterno,tr.apellidomaterno, " +
					" h.type, hh.med_scheduletime_id, hh.state, hh.Description   " +
					" from  MED_ScheduleTime hh " +
					" Inner Join MED_ScheduleDay p on (hh.MED_ScheduleDay_ID=p.MED_ScheduleDay_ID) " +
					" Inner join MED_Schedule h on (p.MED_Schedule_ID=h.MED_Schedule_ID) " +
					" Inner Join MED_Specialty esp on (h.MED_Specialty_ID=esp.MED_Specialty_ID)" +
					" Inner join C_BPartner tr on (h.C_BPartnerMed_ID=tr.C_BPartner_ID)" +
					//" WHERE (p.DateTrx = ? or p.DateTrx - cast('1 day' as interval) = ?)" +
					" WHERE (p.DateTrx = ? or p.DateTrx between ? and (?::date + cast('10 day' as interval)))" +
					" and h.MED_Specialty_ID=? " +
//					" and h.C_BPartnerMed_ID=? " +
					" AND hh.state='DI'" +
					" order by hh.timerequested ");
			flag = 1; //flag 1 es que no hay tratante.
		}
		if(especialidad == null)
		{
			sql = new StringBuffer("SELECT hh.timerequested,hh.med_specialty_id, h.c_bpartnermed_id ,esp.name, " +
					" tr.name," +
					//" tr.apellidopaterno,tr.apellidomaterno, " +
					" h.type, hh.med_scheduletime_id, hh.state, hh.Description   " +
					" from  MED_ScheduleTime hh " +
					" Inner Join MED_ScheduleDay p on (hh.MED_ScheduleDay_ID=p.MED_ScheduleDay_ID) " +
					" Inner join MED_Schedule h on (p.MED_Schedule_ID=h.MED_Schedule_ID) " +
					" Inner Join MED_Specialty esp on (h.MED_Specialty_ID=esp.MED_Specialty_ID)" +
					" Inner join C_BPartner tr on (h.C_BPartnerMed_ID=tr.C_BPartner_ID)" +
					//" WHERE (p.DateTrx = ? or p.DateTrx - cast('1 day' as interval) = ?)" +
					" WHERE (p.DateTrx = ? or p.DateTrx between ? and (?::date + cast('10 day' as interval)))" +
	//				" and h.MED_Specialty_ID=? " +
					" and h.C_BPartnerMed_ID=? " +
					" AND hh.state='DI'" +
					" order by hh.timerequested ");
			flag = 2; //flag 2 es que no hay especialidad
		}
		// role security
		//sql = new StringBuffer( MRole.getDefault(Env.getCtx(), false).addAccessSQL( sql.toString(), "p", MRole.SQL_FULLYQUALIFIED, MRole.SQL_RO ) );
		
		log.fine("SQL=" + sql.toString());
		log.fine("parameters: " +TimeUtil.getDay(((Timestamp)date).getTime()) + "-"  +especialidad +"-"+ tratante);
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			
			pstmt.setTimestamp(1, TimeUtil.getDay(((Timestamp)date).getTime()));
			pstmt.setTimestamp(2, TimeUtil.getDay(((Timestamp)date).getTime()));
			pstmt.setTimestamp(3, TimeUtil.getDay(((Timestamp)date).getTime()));

			if(flag == 0)
			{
				//pstmt.setInt(3, (Integer)especialidad);
				//pstmt.setInt(4, (Integer)tratante);
				pstmt.setInt(4, (Integer)especialidad);
				pstmt.setInt(5, (Integer)tratante);
			}
			else if (flag == 1)
				//pstmt.setInt(3, (Integer)especialidad);
				pstmt.setInt(4, (Integer)especialidad);
			else if (flag == 2)
				//pstmt.setInt(3, (Integer)tratante);
				pstmt.setInt(4, (Integer)tratante);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));       //  0-Selection
				KeyNamePair pp = new KeyNamePair(rs.getInt(7), rs.getString(1));
				line.add(pp); // -1 hora
				//ininoles nuevo orden cantidad de reservas y despues el tipo
				
				//int reservas = DB.getSQLValue(null, "Select Count(1) from Jes_CitaHora ch left join Jes_Cita c on (ch.Jes_Cita_ID = c.Jes_Cita_ID) " +
				//		" where Jes_hora_ID ="+rs.getInt(9)+" and c.estado <> 'AN' ");
				//line.add(reservas<0?0:reservas);
				
				//ininoles se mostrara nombre de referencia
				//line.add(rs.getString(8).equals("C")?"Consulta":"Procedimiento" ); // -2 Tipo
				line.add(1);
				String txtRef = DB.getSQLValueString(null, "SELECT Name FROM AD_Ref_List WHERE AD_Reference_ID = 1000073"+
						" AND value = '"+rs.getString(8)+"'");
				line.add(txtRef==null?"Consulta":txtRef); // -2 Tipo				
				//ininoles nuevo campo estado
				String NameValue = DB.getSQLValueString(null, "SELECT name FROM AD_Ref_List where AD_Reference_ID = 2000195 "+ //o 191 data local
						" and value like '"+rs.getString(8)+"'");
				line.add(NameValue==null?"Desconocido":NameValue);
				line.add(rs.getString(9)==null?"Desconocido":rs.getString(9));
				//especialidad y tratante
				line.add(rs.getString(4)==null?"-":rs.getString(4));
				line.add(rs.getString(5)==null?"-":rs.getString(5));
				//ininoles end
				//
				data.add(line);
				log.fine(" line added");
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		
		return data;
	}
	
	public Vector<String> gethorasColumnNames()
	{	
		//  Header Info
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "Date"));
		//ininoles nuevo orden de campos
		columnNames.add(Msg.getMsg(Env.getCtx(), "N. Reservas"));
		columnNames.add("Tipo");
		//ininoles nueva columna
		columnNames.add("Estado");
		columnNames.add("Desc. Estado");
		columnNames.add("Especialidad");
		columnNames.add("Tratante");
		//ininoles end
		
		
		return columnNames;
	}
	
	public void sethorasColumnClass(IMiniTable horasTable)
	{
		int i = 0;
		horasTable.setColumnClass(i++, Boolean.class, false);         //  0-Selection
		horasTable.setColumnClass(i++, String.class, true);        //  1-TrxDate		
		//ininoles nuevo orden de campos
		horasTable.setColumnClass(i++, Integer.class, true);        //  3-Cantidad de Reservas
		horasTable.setColumnClass(i++, String.class, true);        //  2-Tipo
		//end ininoles
		//ininoles nuevo campo estado
		horasTable.setColumnClass(i++, String.class, true);        //  4-Tipo
		horasTable.setColumnClass(i++, String.class, true);        //  5-desc estado
		
		//  Table UI
		horasTable.autoSize();
	}
	
	//public Vector<Vector<Object>> getReservaData(IMiniTable horasTable)
	public Vector<Vector<Object>> getReservaData(Object tratante, Object date, Object especialidad, IMiniTable paymentTable)
	{
		log.config("-getReservaData");
/*		int hRows = horasTable.getRowCount();
		String where="(0";
		for (int i = 0; i < hRows; i++)
		{
			if (((Boolean)horasTable.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)horasTable.getValueAt(i, 1);
				where +=","+ pp.getKey();
			}
		}
		where +=")";
		*/
		int flag = 0;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuffer sql = new StringBuffer("SELECT c.MED_Appointment_id, c.MED_AttentionType_ID, ti.name, " +
				" c.State, c.description" +
				" , p.name  as paciente, " +
				" c.c_bpartner_id, c.AttentionTime " +
				//" h.Description " +
				" from MED_Appointment c" +
				" inner join MED_AttentionType ti on (c.MED_AttentionType_ID=ti.MED_AttentionType_ID) " +
				" left outer join c_bpartner p on (c.c_bpartner_id=p.c_bpartner_id) "+
				//" left outer join Jes_paciente t on (t.jes_paciente_id=c.jes_titular_id) " +
				//" inner join Jes_CitaHora ch on (c.Jes_Cita_id=ch.Jes_Cita_ID) " +
				" inner join MED_scheduletime h on (c.MED_ScheduleTime_id=h.MED_ScheduleTime_ID) " +
				" inner join MED_ScheduleDay sd on (sd.MED_ScheduleDay_ID = h.MED_ScheduleDay_ID)"+
				//" where c.MED_ScheduleTime_ID IN "+where+" order by c.reservationdate");
				" WHERE (sd.DateTrx::date = ?::date  or (sd.DateTrx::date - cast('1 day' as interval)) = ?::date) and c.MED_Specialty_ID=? and c.C_BPartnerMed_ID=? AND c.state!='DI'");

		if(tratante == null)
		{
			sql = new StringBuffer("SELECT c.MED_Appointment_id, c.MED_AttentionType_ID, ti.name, " +
					" c.State, c.description" +
					" , p.name  as paciente, " +
					" c.c_bpartner_id, c.AttentionTime " +
					//" h.Description " +
					" from MED_Appointment c" +
					" inner join MED_AttentionType ti on (c.MED_AttentionType_ID=ti.MED_AttentionType_ID) " +
					" left outer join c_bpartner p on (c.c_bpartner_id=p.c_bpartner_id) "+
					//" left outer join Jes_paciente t on (t.jes_paciente_id=c.jes_titular_id) " +
					//" inner join Jes_CitaHora ch on (c.Jes_Cita_id=ch.Jes_Cita_ID) " +
					" inner join MED_scheduletime h on (c.MED_ScheduleTime_id=h.MED_ScheduleTime_ID) " +
					" inner join MED_ScheduleDay sd on (sd.MED_ScheduleDay_ID = h.MED_ScheduleDay_ID)"+
					//" where c.MED_ScheduleTime_ID IN "+where+" order by c.reservationdate");
					" WHERE (sd.DateTrx::date = ?::date or (sd.DateTrx::date - cast('1 day' as interval))  = ?::date)" +
					" and c.MED_Specialty_ID=? " +
					//" and c.C_BPartnerMed_ID=? " +
					" AND c.state!='DI'");
			flag = 1; //tratante es nulo
		}
		if(especialidad == null)
		{
			sql = new StringBuffer("SELECT c.MED_Appointment_id, c.MED_AttentionType_ID, ti.name, " +
					" c.State, c.description" +
					" , p.name  as paciente, " +
					" c.c_bpartner_id, c.AttentionTime " +
					//" h.Description " +
					" from MED_Appointment c" +
					" inner join MED_AttentionType ti on (c.MED_AttentionType_ID=ti.MED_AttentionType_ID) " +
					" left outer join c_bpartner p on (c.c_bpartner_id=p.c_bpartner_id) "+
					//" left outer join Jes_paciente t on (t.jes_paciente_id=c.jes_titular_id) " +
					//" inner join Jes_CitaHora ch on (c.Jes_Cita_id=ch.Jes_Cita_ID) " +
					" inner join MED_scheduletime h on (c.MED_ScheduleTime_id=h.MED_ScheduleTime_ID) " +
					" inner join MED_ScheduleDay sd on (sd.MED_ScheduleDay_ID = h.MED_ScheduleDay_ID)"+
					//" where c.MED_ScheduleTime_ID IN "+where+" order by c.reservationdate");
					" WHERE (sd.DateTrx::date = ?::date or (sd.DateTrx::date - cast('1 day' as interval)) = ?::date)" +
					//" and c.MED_Specialty_ID=? " +
					" and c.C_BPartnerMed_ID=? " +
					" AND c.state!='DI'");
			flag = 2; // especialidad nula

		}
		log.fine("SQL=" + sql.toString());
		log.config("parameter : "+date);
		log.config("parameter : "+especialidad);
		log.config("parameter : "+tratante);
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setTimestamp(1, TimeUtil.getDay(((Timestamp)date).getTime()));
			pstmt.setTimestamp(2, TimeUtil.getDay(((Timestamp)date).getTime()));

			if(flag == 0)
			{
				pstmt.setInt(3, (Integer)especialidad);
				pstmt.setInt(4, (Integer)tratante);
			}
			else if(flag == 1)
				pstmt.setInt(3, (Integer)especialidad);
			else if(flag == 2)
				pstmt.setInt(3, (Integer)tratante);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));       //  0-Selection
				
				//KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(8));
				
				line.add(pp); // 1- fecha reserva ID
				
				//ininoles nuevo orden de campos
				String tipo="";
				if(rs.getString("State").equals("AN"))
					tipo="Anulada";
				else if (rs.getString("State").equals("RC"))
					tipo="Reservada / Confirmada";
				else if (rs.getString("State").equals("RN"))
					tipo="Reservada / No Confirmada";
				else if(rs.getString("State").equals("RL"))
					tipo="Recepcionado";
				line.add(tipo); //5- Estado
				
				line.add(rs.getString("paciente")); //2- Paciente
				
				line.add(rs.getString("name")); //4- Tipo Atención
							
/*				if(rs.getString("c_bpartner_id") == null)
					line.add(rs.getString("paciente")); //3- Titular
				else
					line.add(rs.getString("paciente")); //3- Titular
	*/			
				line.add(rs.getString("description")); //6- Descripcion
				
				//ininoles nuevo campo descripcion estado
				line.add(rs.getString("Description")); //7- EstadoDesc
				//ininoles end
				
				data.add(line);
				log.fine("--line added");
				
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		
		return data;
	}

	public Vector<String> getReservaColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "Fecha Reserva"));
		
		//ininoles nuevo orden de campos
		columnNames.add(Msg.getMsg(Env.getCtx(), "Estado"));		
		columnNames.add(Msg.translate(Env.getCtx(), "Paciente"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Tipo Atencion"));		
		columnNames.add(Msg.getMsg(Env.getCtx(), ""));
		columnNames.add(Msg.getMsg(Env.getCtx(), ""));//ininoles se cambia nombre campo descripcion por contacto movil
		columnNames.add(Msg.getMsg(Env.getCtx(), ""));//ininoles se agrega nuevo campo descripcion estado
		//ininoles end
		
		return columnNames;
	}
	
	public void setReservaColumnClass(IMiniTable reservaTable)
	{
		int i = 0;
		reservaTable.setColumnClass(i++, Boolean.class, false);         //  0-Selection
		reservaTable.setColumnClass(i++, String.class, true);        // 1- fecha reserva ID
		
		//ininoles nuevo orden de campos
		reservaTable.setColumnClass(i++, String.class, true);  //5- Estado
		reservaTable.setColumnClass(i++, String.class, true);           //  2- Paciente
		reservaTable.setColumnClass(i++, String.class, true);  //4- Tipo Atención
		reservaTable.setColumnClass(i++, String.class, true);  //3- Titular
		//reservaTable.setColumnClass(i++, String.class, true);   //6- Descripcion
		//reservaTable.setColumnClass(i++, String.class, true);   //7- Descripcion estado
		//ininoles end
		
		reservaTable.autoSize();
	}
	
	
	/**************************************************************************
	 *  Save Data
	 */
	public String saveData(int m_WindowNo, Object date, IMiniTable payment, IMiniTable invoice, String trxName)
	{
		if (m_noInvoices + m_noPayments == 0)
			return "";

		//  fixed fields
		int AD_Client_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "AD_Client_ID");
		int AD_Org_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "AD_Org_ID");
		int C_BPartner_ID = m_C_BPartner_ID;
		int C_Order_ID = 0;
		int C_CashLine_ID = 0;
		Timestamp DateTrx = (Timestamp)date;
		int C_Currency_ID = m_C_Currency_ID;	//	the allocation currency
		//
		if (AD_Org_ID == 0)
		{
			//ADialog.error(m_WindowNo, this, "Org0NotAllowed", null);
			throw new AdempiereException("@Org0NotAllowed@");
		}
		//
		log.config("Client=" + AD_Client_ID + ", Org=" + AD_Org_ID
			+ ", BPartner=" + C_BPartner_ID + ", Date=" + DateTrx);

		//  Payment - Loop and add them to paymentList/amountList
		int pRows = payment.getRowCount();
		ArrayList<Integer> paymentList = new ArrayList<Integer>(pRows);
		ArrayList<BigDecimal> amountList = new ArrayList<BigDecimal>(pRows);
		BigDecimal paymentAppliedAmt = Env.ZERO;
		for (int i = 0; i < pRows; i++)
		{
			//  Payment line is selected
			if (((Boolean)payment.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)payment.getValueAt(i, 2);   //  Value
				//  Payment variables
				int C_Payment_ID = pp.getKey();
				paymentList.add(new Integer(C_Payment_ID));
				//
				BigDecimal PaymentAmt = (BigDecimal)payment.getValueAt(i, i_payment);  //  Applied Payment
				amountList.add(PaymentAmt);
				//
				paymentAppliedAmt = paymentAppliedAmt.add(PaymentAmt);
				//
				log.fine("C_Payment_ID=" + C_Payment_ID 
					+ " - PaymentAmt=" + PaymentAmt); // + " * " + Multiplier + " = " + PaymentAmtAbs);
			}
		}
		log.config("Number of Payments=" + paymentList.size() + " - Total=" + paymentAppliedAmt);

		//  Invoices - Loop and generate allocations
		int iRows = invoice.getRowCount();
		
		//	Create Allocation
		MAllocationHdr alloc = new MAllocationHdr (Env.getCtx(), true,	//	manual
			DateTrx, C_Currency_ID, Env.getContext(Env.getCtx(), "#AD_User_Name"), trxName);
		alloc.setAD_Org_ID(AD_Org_ID);
		alloc.saveEx();
		BigDecimal unmatchedApplied = Env.ZERO;
		for (int i = 0; i < iRows; i++)
		{
			//  Invoice line is selected
			if (((Boolean)invoice.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)invoice.getValueAt(i, 2);    //  Value
				//  Invoice variables
				int C_Invoice_ID = pp.getKey();
				BigDecimal AppliedAmt = (BigDecimal)invoice.getValueAt(i, i_applied);
				//  semi-fixed fields (reset after first invoice)
				BigDecimal DiscountAmt = (BigDecimal)invoice.getValueAt(i, i_discount);
				BigDecimal WriteOffAmt = (BigDecimal)invoice.getValueAt(i, i_writeOff);
				//	OverUnderAmt needs to be in Allocation Currency
				BigDecimal OverUnderAmt = ((BigDecimal)invoice.getValueAt(i, i_open))
					.subtract(AppliedAmt).subtract(DiscountAmt).subtract(WriteOffAmt);
				
				log.config("Invoice #" + i + " - AppliedAmt=" + AppliedAmt);// + " -> " + AppliedAbs);
				//  loop through all payments until invoice applied
				
				for (int j = 0; j < paymentList.size() && AppliedAmt.signum() != 0; j++)
				{
					int C_Payment_ID = ((Integer)paymentList.get(j)).intValue();
					BigDecimal PaymentAmt = (BigDecimal)amountList.get(j);
					if (PaymentAmt.signum() == AppliedAmt.signum())	// only match same sign (otherwise appliedAmt increases)
					{												// and not zero (appliedAmt was checked earlier)
						log.config(".. with payment #" + j + ", Amt=" + PaymentAmt);
						
						BigDecimal amount = AppliedAmt;
						if (amount.abs().compareTo(PaymentAmt.abs()) > 0)  // if there's more open on the invoice
							amount = PaymentAmt;							// than left in the payment
						
						//	Allocation Line
						MAllocationLine aLine = new MAllocationLine (alloc, amount, 
							DiscountAmt, WriteOffAmt, OverUnderAmt);
						aLine.setDocInfo(C_BPartner_ID, C_Order_ID, C_Invoice_ID);
						aLine.setPaymentInfo(C_Payment_ID, C_CashLine_ID);
						aLine.saveEx();

						//  Apply Discounts and WriteOff only first time
						DiscountAmt = Env.ZERO;
						WriteOffAmt = Env.ZERO;
						//  subtract amount from Payment/Invoice
						AppliedAmt = AppliedAmt.subtract(amount);
						PaymentAmt = PaymentAmt.subtract(amount);
						log.fine("Allocation Amount=" + amount + " - Remaining  Applied=" + AppliedAmt + ", Payment=" + PaymentAmt);
						amountList.set(j, PaymentAmt);  //  update
					}	//	for all applied amounts
				}	//	loop through payments for invoice
				
				if ( AppliedAmt.signum() == 0 && DiscountAmt.signum() == 0 && WriteOffAmt.signum() == 0)
					continue;
				else {			// remainder will need to match against other invoices
					int C_Payment_ID = 0;
					
					//	Allocation Line
					MAllocationLine aLine = new MAllocationLine (alloc, AppliedAmt, 
						DiscountAmt, WriteOffAmt, OverUnderAmt);
					aLine.setDocInfo(C_BPartner_ID, C_Order_ID, C_Invoice_ID);
					aLine.setPaymentInfo(C_Payment_ID, C_CashLine_ID);
					aLine.saveEx();
					log.fine("Allocation Amount=" + AppliedAmt);
					unmatchedApplied = unmatchedApplied.add(AppliedAmt);
				}
			}   //  invoice selected
		}   //  invoice loop

		// check for unapplied payment amounts (eg from payment reversals)
		for (int i = 0; i < paymentList.size(); i++)	{
			BigDecimal payAmt = (BigDecimal) amountList.get(i);
			if ( payAmt.signum() == 0 )
					continue;
			int C_Payment_ID = ((Integer)paymentList.get(i)).intValue();
			log.fine("Payment=" + C_Payment_ID  
					+ ", Amount=" + payAmt);

			//	Allocation Line
			MAllocationLine aLine = new MAllocationLine (alloc, payAmt, 
				Env.ZERO, Env.ZERO, Env.ZERO);
			aLine.setDocInfo(C_BPartner_ID, 0, 0);
			aLine.setPaymentInfo(C_Payment_ID, 0);
			aLine.saveEx();
			unmatchedApplied = unmatchedApplied.subtract(payAmt);
		}		
		
		if ( unmatchedApplied.signum() != 0 )
			log.log(Level.SEVERE, "Allocation not balanced -- out by " + unmatchedApplied );

		//	Should start WF
		if (alloc.get_ID() != 0)
		{
			alloc.processIt(DocAction.ACTION_Complete);
			alloc.saveEx();
		}
		
		//  Test/Set IsPaid for Invoice - requires that allocation is posted
		for (int i = 0; i < iRows; i++)
		{
			//  Invoice line is selected
			if (((Boolean)invoice.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)invoice.getValueAt(i, 2);    //  Value
				//  Invoice variables
				int C_Invoice_ID = pp.getKey();
				String sql = "SELECT invoiceOpen(C_Invoice_ID, 0) "
					+ "FROM C_Invoice WHERE C_Invoice_ID=?";
				BigDecimal open = DB.getSQLValueBD(trxName, sql, C_Invoice_ID);
				if (open != null && open.signum() == 0)	{
					sql = "UPDATE C_Invoice SET IsPaid='Y' "
						+ "WHERE C_Invoice_ID=" + C_Invoice_ID;
					int no = DB.executeUpdate(sql, trxName);
					log.config("Invoice #" + i + " is paid - updated=" + no);
				} else
					log.config("Invoice #" + i + " is not paid - " + open);
			}
		}
		//  Test/Set Payment is fully allocated
		for (int i = 0; i < paymentList.size(); i++)
		{
			int C_Payment_ID = ((Integer)paymentList.get(i)).intValue();
			MPayment pay = new MPayment (Env.getCtx(), C_Payment_ID, trxName);
			if (pay.testAllocation())
				pay.saveEx();
			log.config("Payment #" + i + (pay.isAllocated() ? " not" : " is") 
					+ " fully allocated");
		}
		paymentList.clear();
		amountList.clear();
		
		return alloc.getDocumentNo();
	}   //  saveData
	
	
	/**************************************************************************
	 *  Save Reserva
	 */
	public int saveReserva(int m_WindowNo, IMiniTable horas,String pacienteDesc,Object dateField,Object especialidadPick,Object pacienteSearch,Object tipoHoraPick, String trxName,Object curso,Object acargo,Object academia,Object tratante)
	{
		log.config("--saveReserva");
		//  fixed fields
		int AD_Org_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "AD_Org_ID");
		//
		if (AD_Org_ID == 0)
		{
			throw new AdempiereException("@Org0NotAllowed@");
		}		
		//ciclo de validacion de horas que esten disponibles atencion medica - procedimiento.
		int validacionHora = 0;
		if((Integer)tipoHoraPick==1000000 || (Integer)tipoHoraPick==1000001)
		{
			int pRows2 = horas.getRowCount();
			for (int i2 = 0; i2 < pRows2; i2++)
			{
				if (((Boolean)horas.getValueAt(i2, 0)).booleanValue())
				{
					KeyNamePair pp = (KeyNamePair)horas.getValueAt(i2, 1);    //  Value
					X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(), pp.getKey(),trxName);
					if (!hora.getState().equalsIgnoreCase("DI") && !hora.getState().equalsIgnoreCase("AN"))
					{
						validacionHora = 1;
					}
				}
			}			
		}		
		if (validacionHora > 0)
			throw new AdempiereException("Hora no Disponible, No se puede agendar cita.");
		int pRowsValid = horas.getRowCount();
		int cantHorasTomadas = 0;
		for (int iValid = 0; iValid < pRowsValid; iValid++)
		{
			if (((Boolean)horas.getValueAt(iValid, 0)).booleanValue())
				cantHorasTomadas++;
		}		
		if(cantHorasTomadas > 1)
			throw new AdempiereException("Solo se puede tomar 1 hora a a vez.");
		//
		X_MED_Appointment cita = null;
		int pRows = horas.getRowCount();
		//ininoles validaci�n no pueden seleccionar mas de 1 hora
		
		for (int i = 0; i < pRows; i++)
		{
			//  Payment line is selected
			if (((Boolean)horas.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)horas.getValueAt(i, 1);    //  Value
				X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(),pp.getKey(),trxName);	
				
				//validacion de fecha (no puede ser menor a la fecha actual).
				
				if(hora.getTimeRequested().compareTo(Env.getContextAsDate(Env.getCtx(), trxName)) < 0)
					return 0;
				
				//fin  validacion fecha
				
				if(cita==null)
				{
					cita = new X_MED_Appointment(Env.getCtx(), 0 ,trxName);
					cita.setAD_Org_ID(AD_Org_ID);
					cita.setState("RN");
					cita.setDescription(pacienteDesc);
					//cita.setFECHARESERVA((Timestamp)dateField);
					//ininoles se cambia porque fecha de ventana java es modiifcable y crea horas erroneas 
					cita.setReservationDate(Env.getContextAsDate(Env.getCtx(), trxName));
					cita.setMED_Specialty_ID((Integer)especialidadPick);
					/*cita.set_CustomColumn("Academia", (String)academia );
					cita.set_CustomColumn("ACargo", (String)acargo );
					cita.set_CustomColumn("Curso", (String)curso);
					cita.set_CustomColumn("JES_Tratante_ID", (Integer)tratante);
					*/
					//if((Integer)tipoHoraPick==1000000 || (Integer)tipoHoraPick==1000001 || (Integer)tipoHoraPick==1000006 || (Integer)tipoHoraPick==1000007 || (Integer)tipoHoraPick==1000008)
					cita.setC_BPartner_ID((Integer)pacienteSearch);
					cita.setMED_AttentionType_ID((Integer)tipoHoraPick);					
					cita.save();
					// aca update hora														
				}
				
				/*X_JES_CitaHora ch = new X_JES_CitaHora(Env.getCtx(), 0 ,trxName);
				ch.setJES_Hora_ID(pp.getKey());
				ch.setJES_Cita_ID(cita.getJES_Cita_ID());
				ch.save();
				*/
				/*DB.executeUpdate("update adempiere.med_Scheduletime set state='RN' where " +
						"med_scheduletime_id IN (select med_Scheduletime_id from " +
						"adempiere.jes_citahora where jes_cita_id=" +cita.getMED_Appointment_ID()+")", trxName);
					*/			
				//actualizamos hora de cita en nuevo campo horareserva ininoles		
				//cita.set_CustomColumn("HORARESERVA", (Timestamp)hora.getTimeRequested());
				cita.set_CustomColumn("MED_ScheduleTime_ID", hora.getMED_ScheduleTime_ID());
				cita.setAttentionTime((Timestamp)hora.getTimeRequested());
				log.config("1.Tratante ID = "+(Integer)tratante);
				cita.set_CustomColumn("C_BPartnerMED_ID", (Integer)tratante);
				
				cita.save();
				hora.setState("RN");
				hora.save();
				
				//end ininoles
			}
		}
		return cita.getMED_Appointment_ID();

	}   //  saveData
	
	/**************************************************************************
	 *  Save Reserva
	 */
	public String updateCita(int m_WindowNo, IMiniTable reservas,Object nuevoEstadoBox, String trxName)
	{
		log.config("--updateCita");
		//  fixed fields
		//int AD_Client_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "AD_Client_ID");
		int AD_Org_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "AD_Org_ID");
		//
		if (AD_Org_ID == 0)
		{
			//ADialog.error(m_WindowNo, this, "Org0NotAllowed", null);
			throw new AdempiereException("@Org0NotAllowed@");
		}
		//
		int pRows = reservas.getRowCount();
		for (int i = 0; i < pRows; i++)
		{
			//  Payment line is selected
			if (((Boolean)reservas.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)reservas.getValueAt(i, 1);    //  Value			
				
				String nEstado = (String)nuevoEstadoBox;
				//ininoles nueva funcionalidad para borrar citas tomadas
				if(nEstado.compareTo("DI") == 0)					
				{
					log.config("Dentro de if DI. Ininoles");
					//DB.executeUpdate("update adempiere.med_scheduletime set state='"+(String)nuevoEstadoBox+"' where jes_hora_id=(select jes_hora_id from adempiere.jes_citahora where jes_cita_id=" +pp.getKey()+")", trxName);
					//DB.executeUpdate("DELETE adempiere.jes_citahora WHERE jes_cita_ID = "+pp.getKey(), trxName);
					//DB.executeUpdate("DELETE adempiere.jes_cita WHERE jes_cita_ID = "+pp.getKey(), trxName);
					
				}else
				{
					X_MED_Appointment cita = new X_MED_Appointment(Env.getCtx(), pp.getKey(),trxName);
					if(nEstado.compareTo("Reserva Confirmada") == 0)
						nEstado = "RC";
					if(nEstado.compareTo("Anular") == 0)
						nEstado = "AN";
					if(nEstado.compareTo("Recepcionado") == 0)
						nEstado = "RL";
/*					nuevoEstadoBox.addItem("Reserva Confirmada");//confirmar
					//nuevoEstadoBox.addItem("AN");//anular
					nuevoEstadoBox.addItem("Anular");//anular
					//nuevoEstadoBox.addItem("RL");//paciente llego
					nuevoEstadoBox.addItem("Paciente Llego");//paciente llego*/
					cita.setState(nEstado);
					//DB.executeUpdate("update adempiere.jes_hora set estado='"+(String)nuevoEstadoBox+"' where " +
					//		"jes_hora_id=(select jes_hora_id from adempiere.jes_citahora where " +
					//		"jes_cita_id=" +cita.getMED_Appointment_ID()+")", trxName);
					cita.save();
					
					//Si el paciente ya llego, se debe crear un ingreso administrativo ambulatorio
					if(nEstado.compareTo("RL") == 0)
					{
						X_CC_Hospitalization hosp = new X_CC_Hospitalization(Env.getCtx(),0,trxName);
						hosp.setC_BPartner_ID(cita.getC_BPartner_ID());
						hosp.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
						int scheduletime = cita.get_ValueAsInt("MED_ScheduleTime_ID");
						int locator = DB.getSQLValue(trxName, "SELECT coalesce(m_locator_id, 0) from med_scheduletime where med_scheduletime_id = "+scheduletime);
						hosp.set_CustomColumn("M_Locator1_ID",locator);
						hosp.set_CustomColumn("IsPayable","N");
						hosp.set_CustomColumn("Payamt", new BigDecimal(50000));
						hosp.set_CustomColumn("MED_Appointment_ID", cita.getMED_Appointment_ID());
						
						//Se deben agregar los camops de isapre y convenio. Se deben obtener desde socio de negocio. 
						int isapre = DB.getSQLValue(trxName, "SELECT coalesce(cc_isapre_id,0) from c_bpartner " +
								" WHERE c_bpartner_id = "+cita.getC_BPartner_ID());
						if(isapre > 0)
							hosp.setCC_Isapre_ID(isapre);
						int convenio = DB.getSQLValue(trxName, "SELECT coalesce(cc_agreement_id,0) FROM c_bpartner " +
								" WHERE c_bpartner_id = "+cita.getC_BPartner_ID());
						if(convenio > 0)
							hosp.setCC_Agreement_ID(convenio);
						String nivel = DB.getSQLValueString(trxName, "SELECT coalesce(ISAPRE,  '-') FROM c_bpartner " +
								" WHERE c_bpartner_id = ?",cita.getC_BPartner_ID());
						/*if(nivel.compareTo("-") != 0)
							hosp.set_CustomColumn("ISAPRE", nivel);*/
						
						//monto correcto segun convenio.
						int productoasociado = DB.getSQLValue(trxName, "SELECT coalesce(m_product_id, 0) FROM MED_Specialty " +
								" WHERE MED_Specialty_ID = "+cita.getMED_Specialty_ID());
						int pricelistversion = DB.getSQLValue(trxName, "SELECT coalesce(m_pricelist_version_id,0) FROM cc_agreement " +
								" WHERE cc_Agreement_id in (select cc_agreement_id from c_bpartner " +
								" where c_bpartner_id = "+cita.getC_BPartner_ID()+" )");
						if(productoasociado > 0 && pricelistversion > 0)
						{
							BigDecimal montocorrecto = DB.getSQLValueBD(trxName, "SELECT coalesce(pricelist,0) from m_productprice " +
								" WHERE m_product_id = ? and m_pricelist_version_id = "+pricelistversion, productoasociado);
							if(montocorrecto != null && montocorrecto.compareTo(Env.ZERO) > 0)
								hosp.set_CustomColumn("Payamt", montocorrecto);
						}
						
						//se agrega el producto
						hosp.set_CustomColumn("M_Product_ID", productoasociado);
						//se agrega especialidad
						hosp.set_CustomColumn("MED_Specialty_ID", cita.getMED_Specialty_ID());
						hosp.save();

					}
					//Si la cita es anulada, el horario debe volver a estar disponible. 
					if(nEstado.compareTo("AN") == 0)
					{
						X_MED_ScheduleTime sched = new X_MED_ScheduleTime(Env.getCtx(),cita.get_ValueAsInt("MED_ScheduleTime_ID"),trxName);
						sched.setState("DI");
						sched.save();
					}
				}
			}
		}	
		return "";
	}   //  saveData
	//ininoles metodos customizados de datos
	//grilla 1 carga de dias 
	public Vector<Vector<Object>> getdiasData(Object especialidad, Object date, Object tratante, Object tipo)
	{		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuffer sql = new StringBuffer("SELECT p.datetrx,p.med_scheduleday_ID,p.dia, ref.name as dianombre," +
				" (SELECT COUNT(1)FROM med_scheduletime WHERE state = 'DI' AND med_Scheduleday_ID = p.med_Scheduleday_ID) as cant " +
				" FROM  med_Scheduleday p " +
				"INNER JOIN med_schedule h ON (p.med_schedule_ID=h.med_schedule_ID) " +
				"INNER JOIN AD_Ref_List ref ON (AD_Reference_ID=2000193 AND ref.value = p.dia) " +
				"WHERE p.fecha BETWEEN ? AND  ? " +
				//"AND h.IsInternet = 'Y' " +
				" AND p.IsActive = 'Y' AND h.IsActive = 'Y' " +
				"AND h.med_specialty_ID = ? AND h.c_bpartnermed_ID = ? AND h.type = '"+tipo.toString()+"' " +
				"ORDER BY p.datetrx");
				
		log.fine("SQL=" + sql.toString());
		//calculo de fecha final
		Timestamp fechaInicio = (Timestamp)date;
		Calendar calCalendario = Calendar.getInstance();
        calCalendario.setTimeInMillis(fechaInicio.getTime());
        calCalendario.add(Calendar.DATE, 2);
        fechaInicio = new Timestamp(calCalendario.getTimeInMillis());
        calCalendario.add(Calendar.DATE, 30);
        Timestamp fechaFin = new Timestamp(calCalendario.getTimeInMillis());        
		
		log.fine("parameters: " +fechaInicio+":"+fechaFin+"-"+especialidad +"-"+ tratante);
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setTimestamp(1,fechaInicio);
			pstmt.setTimestamp(2,fechaFin);
			pstmt.setInt(3, (Integer)especialidad);
			pstmt.setInt(4, Integer.valueOf(tratante.toString()));			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));       //  0-Selection
				KeyNamePair pp = new KeyNamePair(rs.getInt(2), rs.getString(1).substring(0,10));
				line.add(pp); // 1-fecha
				line.add(rs.getString(4)==null?"Desconocido":rs.getString(4));
				line.add(rs.getInt(5));
				data.add(line);
				log.fine(" line added");
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		return data;
	}
	public Vector<String> getdiasColumnNames()
	{	
		//  Header Info
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "Date"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Dia"));
		columnNames.add("Horas Disponibles");
		return columnNames;
	}
	
	public void setdiasColumnClass(IMiniTable horasTable)
	{
		int i = 0;
		horasTable.setColumnClass(i++, Boolean.class, false);         //  0-Selection
		horasTable.setColumnClass(i++, String.class, true);        //  1-TrxDate
		horasTable.setColumnClass(i++, String.class, true);        //  2-Nombre del dia
		horasTable.setColumnClass(i++, Integer.class, true);        //  3-Cantidad de horas disponibles
		//  Table UI
		horasTable.autoSize();
	}
	//grilla 2 carga de horas
	public Vector<Vector<Object>> gethorasDataExterno(IMiniTable diasTable)
	{		
		log.config("-getHorasData");
		int hRows = diasTable.getRowCount();
		String where="(0";
		for (int i = 0; i < hRows; i++)
		{
			if (((Boolean)diasTable.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)diasTable.getValueAt(i, 1);
				where +=","+ pp.getKey();
			}
		}
		where +=")";
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuffer sql = new StringBuffer("SELECT hh.timerequested,hh.med_scheduletime_id,refEstado.name as estado, " +
				" refTipo.name as tipo " +
				" FROM  med_scheduletime hh " +
				" INNER JOIN med_scheduleday p on (hh.med_Scheduleday_ID=p.med_Scheduleday_ID) " +
				" INNER JOIN med_schedule h on (p.med_schedule_ID=h.med_Schedule_ID) " +
				" LEFT JOIN AD_Ref_List refTipo ON (refTipo.AD_Reference_ID=1000073 AND refTipo.value = h.tipo) " +
				" LEFT JOIN AD_Ref_List refEstado ON (refEstado.AD_Reference_ID=1000110 " +
				" AND refEstado.value = hh.estado) " +
				" WHERE hh.med_Scheduleday_ID IN "+where+" AND hh.IsActive = 'Y' AND hh.state = 'DI' " +
				" order by hh.timerequested ");

		log.fine("SQL=" + sql.toString());
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));       //  0-Selection
				KeyNamePair pp = new KeyNamePair(rs.getInt(2), rs.getString(1).substring(11));
				line.add(pp); // 1 - hora
				line.add(rs.getString(3)==null?"Desconocido":rs.getString(3));
				line.add(rs.getString(4)==null?"Desconocido":rs.getString(4));
				data.add(line);
				log.fine(" line added");
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		return data;
	}
	
	public Vector<String> gethorasExternoColumnNames()
	{	
		//  Header Info
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "Date"));
		columnNames.add("Estado");
		columnNames.add("Tipo");
		return columnNames;
	}
	
	public void sethorasExternoColumnClass(IMiniTable horasTable)
	{
		int i = 0;
		horasTable.setColumnClass(i++, Boolean.class, false);         //  0-Selection
		horasTable.setColumnClass(i++, String.class, true);        //  1-TrxDate
		horasTable.setColumnClass(i++, String.class, true);        //  2-Estado
		horasTable.setColumnClass(i++, String.class, true);        //  3-Tipo
		//  Table UI
		horasTable.autoSize();
	}
	public int saveReservaExterno(int m_WindowNo, IMiniTable horas,String pacienteDesc,Object especialidadPick,Object pacienteSearch,String trxName,Object curso,Object acargo,Object academia,Object tratante)
	{
		log.config("--saveReserva");
		//  fixed fields
		//int AD_Client_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "AD_Client_ID");
		int AD_Org_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "AD_Org_ID");
		//
		if (AD_Org_ID == 0)
		{
			//ADialog.error(m_WindowNo, this, "Org0NotAllowed", null);
			throw new AdempiereException("@Org0NotAllowed@");
		}		
		//ciclo de validacion de horas que esten disponibles atencion medica - procedimiento.
		int validacionHora = 0;

		int pRows2 = horas.getRowCount();
		int cantHorasTomadas = 0;
		for (int i2 = 0; i2 < pRows2; i2++)
		{
			if (((Boolean)horas.getValueAt(i2, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)horas.getValueAt(i2, 1);    //  Value
				X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(), pp.getKey(),trxName);
				if (!hora.getState().equalsIgnoreCase("DI") && !hora.getState().equalsIgnoreCase("AN"))
				{
					validacionHora = 1;
				}
				cantHorasTomadas++;
			}
		}
		if(cantHorasTomadas > 1)
			throw new AdempiereException("Solo se puede tomar 1 hora a a vez.");
		if (validacionHora > 0)
			throw new AdempiereException("Hora no Disponible, No se puede agendar cita.");
		//
		X_MED_Appointment cita = null;
		int pRows = horas.getRowCount();
		for (int i = 0; i < pRows; i++)
		{
			if (((Boolean)horas.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)horas.getValueAt(i, 1);    //  Value
				X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(),pp.getKey(),trxName);
				X_MED_ScheduleDay horaDia = new X_MED_ScheduleDay(Env.getCtx(),hora.getMED_ScheduleDay_ID(),trxName) ;
				if(cita==null)
				{
					cita = new X_MED_Appointment(Env.getCtx(), 0 ,trxName);
					cita.setAD_Org_ID(AD_Org_ID);
					cita.setState("RN");
					cita.setDescription(pacienteDesc);
					//cita.setFECHARESERVA((Timestamp)dateField);
					cita.setReservationDate(horaDia.getDateTrx());
					//cita.setJES_Especialidad_ID(Integer.valueOf(especialidadPick.toString()));
					cita.setMED_Specialty_ID((Integer)especialidadPick);
					/*cita.set_CustomColumn("Academia", (String)academia );
					cita.set_CustomColumn("ACargo", (String)acargo );
					cita.set_CustomColumn("Curso", (String)curso);
					//log.config("1.Tratante ID = "+(Integer)tratante);
					cita.set_CustomColumn("JES_Tratante_ID", Integer.valueOf(tratante.toString()));
					*/
					cita.setC_BPartner_ID((Integer)pacienteSearch);
					//cita.setJES_TipoAtencion_ID((Integer)tipoHoraPick);
					cita.save();

					cita.setAttentionTime((Timestamp)hora.getTimeRequested());
					//log.config("1.Tratante ID = "+(Integer)tratante);
					//cita.set_CustomColumn("JES_Tratante_ID", (Integer)tratante);
					cita.save();
					// aca update hora														
				}				
				/*X_JES_CitaHora ch = new X_JES_CitaHora(Env.getCtx(), 0 ,trxName);
				ch.setJES_Hora_ID(pp.getKey());
				ch.setJES_Cita_ID(cita.getJES_Cita_ID());
				ch.save();
				*/
				/*DB.executeUpdate("update adempiere.med_scheduletime set estado='RN' " +
						" where jes_hora_id=(select jes_hora_id from adempiere.jes_citahora " +
						" where jes_cita_id=" +cita.getMED_Appointment_ID()+")", trxName);
				//end ininoles*/
			}
		}
		return cita.getMED_Appointment_ID();
	}   //  saveData
	//lista de tratantes
	public static List<KeyNamePair> getEspecialidad(int ID_Tratante)
	{
		List<KeyNamePair> list = new ArrayList<KeyNamePair>();
			
		KeyNamePair pp = new KeyNamePair(0, "");
		list.add(pp);
		String sql = "SELECT MED_Specialty_ID, name " +
				" FROM MED_Specialty WHERE IsActive = 'Y' ";
		/*if(ID_Tratante > 0)
			sql =  "SELECT es.MED_Specialty_ID, es.name FROM MED_Specialty es " +
				" INNER JOIN JES_TratanteEspecialidad te ON (es.JES_Especialidad_ID = te.JES_Especialidad_ID) " +
				" WHERE es.IsActive = 'Y' AND te.IsActive = 'Y' AND JES_Tratante_ID = "+ID_Tratante;
		*/
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			list.add(new KeyNamePair(0,"--Seleccione--"));
			while (rs.next())
			{
				pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				list.add(pp);
			}
			return list;
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return list;
		}
		finally
		{
			DB.close(rs, pstmt);
		    rs = null; pstmt = null;
		}		
	} //getAssetTSM
	public static List<KeyNamePair> getTratante(int ID_Especialidad)
	{
		List<KeyNamePair> list = new ArrayList<KeyNamePair>();
			
		KeyNamePair pp = new KeyNamePair(0, "");
		list.add(pp);
		String sql = "SELECT C_BPartner_ID, apellidopaterno||' '||apellidomaterno||' '||nombres as name" +
				" FROM C_BPartner WHERE IsActive = 'Y' ";
		/*if(ID_Especialidad > 0)
			sql =  "SELECT es.JES_Tratante_ID, es.apellidopaterno||' '||es.apellidomaterno||' '||es.nombres as name " +
				" FROM JES_Tratante es " +
				" INNER JOIN JES_TratanteEspecialidad te ON (es.JES_Tratante_ID = te.JES_Tratante_ID) " +
				" WHERE es.IsActive = 'Y' AND te.IsActive = 'Y' AND te.JES_Especialidad_ID = "+ID_Especialidad+
				" ORDER BY apellidopaterno ";
		*/
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			list.add(new KeyNamePair(0,"--Seleccione--"));
			while (rs.next())
			{
				pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				list.add(pp);
			}
			return list;
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return list;
		}
		finally
		{
			DB.close(rs, pstmt);
		    rs = null; pstmt = null;
		}		
	} //lista de tratantes
}
