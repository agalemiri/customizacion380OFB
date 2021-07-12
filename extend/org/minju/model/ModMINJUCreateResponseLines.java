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
package org.minju.model;

import java.sql.ResultSet;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_DM_Document;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.X_MP_Indicator;
import org.compiere.model.X_MP_IndicatorDetail;
import org.compiere.model.X_MP_Response;
import org.compiere.model.X_MP_Planification;
import java.sql.PreparedStatement;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;


/**
 *	Create response lines for Indicators
 *
 *  @author mfrojas
 */
public class ModMINJUCreateResponseLines implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModMINJUCreateResponseLines ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModMINJUCreateResponseLines.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	

	/**
	 *	Initialize Validation
	 *	@param engine validation engine
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		//client = null for global validator
		if (client != null) {
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing Model Price Validator: "+this.toString());
		}

		//	Tables to be monitored
		engine.addModelChange(X_MP_IndicatorDetail.Table_Name, this);	
		engine.addModelChange(X_MP_Response.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if(type == TYPE_AFTER_NEW && po.get_Table_ID()==X_MP_IndicatorDetail.Table_ID) 
		{	
			
			X_MP_IndicatorDetail ind = (X_MP_IndicatorDetail)po;
			int indicator_id = ind.getMP_Indicator_ID();
			X_MP_Indicator i = new X_MP_Indicator(po.getCtx(),indicator_id,po.get_TrxName());

			PreparedStatement pstmt = null;
			String sql = "SELECT mp_planification_id, c_period_id from mp_planification where mp_indicator_id = "+indicator_id;

			pstmt = DB.prepareStatement (sql, po.get_TrxName());
			ResultSet rs = pstmt.executeQuery();	

			while(rs.next())
			{
				X_MP_Response resp = new X_MP_Response(po.getCtx(), 0, po.get_TrxName());
				resp.set_CustomColumn("C_Period_ID", rs.getInt("C_Period_ID"));
				
				resp.set_CustomColumn("VarARef", indicator_id);
				resp.set_CustomColumn("VarBRef", indicator_id);
				resp.setAD_Org_ID(ind.getAD_Org_ID());
				resp.set_CustomColumn("MP_IndicatorDetail_ID", ind.get_ID());
				resp.set_CustomColumn("Obtained", Env.ZERO);
				
				resp.save();
				
			}
			
			
			
		}		
		
		if(type == TYPE_AFTER_CHANGE && po.get_Table_ID()==X_MP_Response.Table_ID)
		{
			X_MP_Response resp = (X_MP_Response)po;
			int indicator_id = DB.getSQLValue(po.get_TrxName(), "select mp_indicator_id from mp_indicatordetail where mp_indicatordetail_id = "+resp.getMP_IndicatorDetail_ID());
			int indicatordetail_id = resp.getMP_IndicatorDetail_ID();
		//Formula será VA/VB
			
			int factor = 0;
			factor = DB.getSQLValue(po.get_TrxName(), "SELECT coalesce(factor,1) from mp_indicator where mp_indicator_id in (select mp_indicator_id from mp_indicatordetail where mp_indicatordetail_id = "+resp.getMP_IndicatorDetail_ID()+") ");
			//log.config("b es "+b);
			log.config("factor es "+factor);

			String varbresp = "SELECT varbresponse from mp_response where mp_response_id = ?";
			BigDecimal varbresponse = DB.getSQLValueBD(po.get_TrxName(), varbresp, resp.get_ID());
			//if(resp.getvarbresponse() > 0)
			
			//mfrojas 20180103 Cuando el indicador es de tipo ascendente, las variables numerador y denominador son bigdecimal.
			//Cuando el indicador es de tipo descendente, las variables numerador y denominador son enteras.

			String sqlintype = "SELECT c.indicatortype from mp_indicator c where c.mp_indicator_id = ?";
	        String indicatortype = DB.getSQLValueString(po.get_TrxName(), sqlintype, indicator_id);

			
			String vararespint = "SELECT vararesponseint from mp_Response where mp_response_id = "+resp.get_ID();
			int varaInt = DB.getSQLValue(po.get_TrxName(), vararespint);
			
			String varbrespint = "SELECT varbresponseint from mp_Response where mp_response_id = "+resp.get_ID();
			int varbInt = DB.getSQLValue(po.get_TrxName(), varbrespint);
			
			if(indicatortype.compareTo("02")==0)
			{
				varbresponse = new BigDecimal(varbInt);
			}
			
			//mfrojas 20180412 se agrega IF denominador es cero. De tal manera que igual 
			//actualice el campo obtained. 
			if(varbresponse.compareTo(Env.ZERO) == 0)
			{
				if(resp.get_Value("Obtained").equals(Env.ZERO))
				{
					
				}
				else
				{
					resp.set_CustomColumn("Obtained", Env.ZERO);
					if(indicatortype.compareTo("02")==0)
					{
						DB.executeUpdate("UPDATE mp_response set vararesponse = "+varaInt+", varbresponse = "+varbInt+" where mp_response_id = "+resp.get_ID(),po.get_TrxName());
					}

				
					resp.save();
				}

			}
			else if(varbresponse.compareTo(Env.ZERO) > 0)
			{
				//mfrojas: Se modifica vararesponse y varbresponse a valores bigdecimal	
		        String vararesp = "SELECT vararesponse from mp_response where mp_response_id = ?";
		        
				//mfrojas 20180103 Se valida primero si está utilizado el valor donde se ingresa es en entero o decimal
				BigDecimal vararesponse = Env.ZERO;
				//se define flag como 0. Si flag es 1, quiere decir que se están utilizando las variables enteras, que deberán ser escritas
				//en los valores bigdecimal
				int flag = 0;
				

				if(indicatortype.compareTo("02")==0)
				{
					vararesponse = new BigDecimal(varaInt);
					flag = 1;
				}
				else
				{	
					vararesponse = DB.getSQLValueBD(po.get_TrxName(), vararesp, resp.get_ID());
					flag = 0;
				}
				
				BigDecimal b = vararesponse.divide(varbresponse,6,RoundingMode.HALF_UP);
				
				BigDecimal ActualResult = Env.ZERO;
				
				
				ActualResult = b.multiply(BigDecimal.valueOf(factor));
		        ActualResult = ActualResult.setScale(2, RoundingMode.DOWN);

				
				if(resp.get_Value("Obtained") != null && resp.get_Value("Obtained").equals(ActualResult))
				{
					
				}
				else
				{
					log.config("Obtained es "+ActualResult);
					resp.set_CustomColumn("Obtained", ActualResult);
					if(flag == 1)
					{
						DB.executeUpdate("UPDATE mp_response set vararesponse = "+varaInt+", varbresponse = "+varbInt+" where mp_response_id = "+resp.get_ID(),po.get_TrxName());
					}

					
					resp.save();
					
				}
			}
			
			//Se debe escribir el resultado actual (que corresponde al planificado acumulado / acumulado * 100) 
			//en la pestaña de indicatordetail.
			
	        Calendar fecha = Calendar.getInstance();
	        int mes = fecha.get(Calendar.MONTH)+1;
	        int year = fecha.get(Calendar.YEAR);
	        
	        //CumulatedResult: Resultado acumulado hasta el periodo del mes en curso. 
	        String sqlanno = "SELECT coalesce(sum(expectedresult),1) from mp_planification where mp_indicator_id = "+indicator_id+" and c_period_id in (select c_period_id from c_period where extract(month from startdate) <= "+mes+" and extract(year from startdate) = ?)";
	        log.config("sqlanno = "+sqlanno);
	        //BigDecimal CumulatedResultPlan = BigDecimal.valueOf(DB.getSQLValue(po.get_TrxName(), sqlanno));
	        BigDecimal CumulatedResultPlan = DB.getSQLValueBD(po.get_TrxName(), sqlanno, year);
	        log.config("planificado acumulado "+CumulatedResultPlan);
	        log.config("mes "+mes);
	        
	        //String sqlactual = "SELECT coalesce(sum(obtained),0) from mp_response where mp_indicatordetail_id = "+resp.getMP_IndicatorDetail_ID()+" and c_period_id in (select c_period_id from c_period where extract(month from startdate) <= "+mes+" and extract(year from startdate)= "+year+")";
	        //String sqlactual = "SELECT coalesce(sum(obtained),0) from mp_response where mp_indicatordetail_id = ?";
	        
	        //El valor a obtener será la suma de los numeradores / suma de los denominadores. 
	        
	        //String sqlactual = "SELECT coalesce(sum(vararesponse),0) from  mp_response where mp_indicatordetail_id = ?";
	        String sqlactual = "SELECT coalesce(vararesponse,0) from mp_response where mp_indicatordetail_id = "+indicatordetail_id+" and c_period_id in " +
	        		" (select max(c_period_id) from mp_Response where mp_indicatordetail_id = ?)";
	        log.config("sql actual "+sqlactual);
	        
	        //para los descendentes se utiliza la suma
	        String sqlactualsuma = "SELECT coalesce(sum(vararesponse),0) from  mp_response where mp_indicatordetail_id = ?";


	        BigDecimal CumulatedResultActual = DB.getSQLValueBD(po.get_TrxName(),sqlactual,indicatordetail_id);
	        log.config("Obtenido acumulado "+CumulatedResultActual);
	        
	        BigDecimal CumulatedResultActualsuma = DB.getSQLValueBD(po.get_TrxName(),sqlactualsuma,indicatordetail_id);
	        log.config("Obtenido acumulado "+CumulatedResultActualsuma);
	        
	        
	        BigDecimal CumulatedResultActuala = CumulatedResultActual;
	        BigDecimal CumulatedResultActualasuma = CumulatedResultActualsuma;
	        
	        //String sqlactualb = "SELECT coalesce(sum(varbresponse),0) from mp_Response where mp_indicatordetail_id = ?";
	        //String sqlactualb = "SELECT coalesce(varbresponse,0) from mp_response where mp_indicatordetail_id = "+indicatordetail_id+" and c_period_id in " +
	        //		" (select max(c_period_id) from mp_Response where mp_indicatordetail_id = ?)";
	        
	        String sqlactualb = "SELECT coalesce(varbresponse,0) from mp_response where mp_indicatordetail_id = "+indicatordetail_id+" and c_period_id in " +
	        		" (select max(c_period_id) from mp_Response where mp_indicatordetail_id = ? and varbresponse > 0)";
	        log.config("sql actual varb = "+sqlactualb);
	        	        
	        BigDecimal CumulatedResultActualb = DB.getSQLValueBD(po.get_TrxName(), sqlactualb, indicatordetail_id);

	        
	        String sqlactualbsuma = "SELECT coalesce(sum(varbresponse),0) from mp_Response where mp_indicatordetail_id = ?";
	        BigDecimal CumulatedResultActualbsuma = DB.getSQLValueBD(po.get_TrxName(), sqlactualbsuma, indicatordetail_id);

	        log.config("sql actual varb = "+sqlactualbsuma);
	        

	        
	        
	        //Si el resultado de CumulatedResultActualb es nulo o cero, entonces su valor será 1 para no tener problemas con la división.
	        if(CumulatedResultActualb == null || CumulatedResultActualb.compareTo(Env.ZERO)<=0)
	        	CumulatedResultActualb = Env.ONE;
	        CumulatedResultActual = CumulatedResultActual.divide(CumulatedResultActualb,2,RoundingMode.HALF_UP);
	        CumulatedResultActual = CumulatedResultActual.multiply(BigDecimal.valueOf(factor));
	        
	        
	        //mfrojas misma formula para cumulatedsuma
	        
	        //Si el resultado de CumulatedResultActualbsuma es nulo o cero, entonces su valor será 1 para no tener problemas con la división.
	        if(CumulatedResultActualbsuma == null || CumulatedResultActualbsuma.compareTo(Env.ZERO)<=0)
	        	CumulatedResultActualbsuma = Env.ONE;
	        CumulatedResultActualsuma = CumulatedResultActualsuma.divide(CumulatedResultActualbsuma,6,RoundingMode.HALF_UP);
	        CumulatedResultActualsuma = CumulatedResultActualsuma.multiply(BigDecimal.valueOf(factor));
	        CumulatedResultActualsuma = CumulatedResultActualsuma.setScale(2, RoundingMode.DOWN);
	        
	        
			//BigDecimal Cumulated = (CumulatedResultActual.divide(CumulatedResultPlan,2,RoundingMode.HALF_UP)).multiply(Env.ONEHUNDRED);
	        BigDecimal Cumulated = (CumulatedResultActual.divide(CumulatedResultPlan,2,RoundingMode.HALF_UP));
	        
	        BigDecimal Cumulatedsuma = (CumulatedResultActualsuma.divide(CumulatedResultPlan,2,RoundingMode.HALF_UP));
	        
	        X_MP_IndicatorDetail i = new X_MP_IndicatorDetail(po.getCtx(),resp.getMP_IndicatorDetail_ID(),po.get_TrxName());
	        
	        String sqlintype2 = "SELECT c.indicatortype from mp_indicator c where c.mp_indicator_id = ?";
	        String intype = DB.getSQLValueString(po.get_TrxName(), sqlintype2, indicator_id);
	        
	        if(intype.compareTo("01")==0)
			{
	        	if(Cumulated.compareTo(Env.ZERO)>=0)
	        	{
	        		i.set_CustomColumn("CumulatedAmt", CumulatedResultActualsuma);
			
	        		i.save();
	        	}
			}
	        
	        else if(intype.compareTo("02")==0)
			{
	        	if(Cumulatedsuma.compareTo(Env.ZERO)>=0)
	        	{
	        		i.set_CustomColumn("CumulatedAmt", CumulatedResultActualsuma);
			
	        		i.save();
	        	}
			}

			//A continuación, se debe calcular el resultado del mes en curso, No del acumulado hasta el mes en curso. 
			//Cambio
	        
	        String sqlanno2 = "SELECT coalesce(sum(expectedresult),1) from mp_planification where mp_indicator_id = "+indicator_id+" and c_period_id in (select c_period_id from c_period where extract(month from startdate) = "+mes+" and extract(year from startdate) = ?)";
	        log.config("sqlanno2 = "+sqlanno2);
	        //BigDecimal CumulatedResultPlan2 = BigDecimal.valueOf(DB.getSQLValue(po.get_TrxName(), sqlanno2));
	        BigDecimal CumulatedResultPlan2 = DB.getSQLValueBD(po.get_TrxName(), sqlanno2,year);
	        log.config("planificado acumulado "+CumulatedResultPlan2);
	        log.config("mes "+mes);
	        
	        
	        String sqlactual2 = "SELECT coalesce(sum(obtained),0) from mp_response where mp_indicatordetail_id = "+resp.getMP_IndicatorDetail_ID()+" and c_period_id in (select c_period_id from c_period where extract(month from startdate) = "+mes+" and extract(year from startdate)= ?)";
	        log.config("sql actual "+sqlactual2);
	        //BigDecimal CumulatedResultActual2 = BigDecimal.valueOf(DB.getSQLValue(po.get_TrxName(),sqlactual2));
	        BigDecimal CumulatedResultActual2 = DB.getSQLValueBD(po.get_TrxName(),sqlactual2,year);
	        log.config("Obtenido acumulado "+CumulatedResultActual2);
	        
			BigDecimal Cumulated2 = (CumulatedResultActual2.divide(CumulatedResultPlan2,2,RoundingMode.HALF_UP)).multiply(Env.ONEHUNDRED);
			
			if(Cumulated.compareTo(Env.ZERO)>0)
			{
				i.set_CustomColumn("ActualAmt", Cumulated2);
			
				i.save();
			}
	        
			//obtener cumplimimento ponderado.
	        //String sqlamt3 = "SELECT case when (select c.)";
	        
	        //Primero obtenemos la meta
			
			String sqlmeta = "SELECT coalesce(max(b.expectedresult),1) from mp_planification b where b.mp_indicator_id = ?";
			BigDecimal meta = DB.getSQLValueBD(po.get_TrxName(),sqlmeta,indicator_id);
			
			//Valor completo.
			
	        String sqlamt3 = "Select case when (select c.indicatortype from mp_indicator c where " +
	        		"c.mp_indicator_id = "+indicator_id+") like '01' " +
	        		"then a.cumulatedamt/(select coalesce(max(b.expectedresult),1) " +
	        		"from mp_planification b where b.mp_indicator_id = "+indicator_id+") " +
	        		"else ((select coalesce(max(b.expectedresult),1) from mp_planification b " +
	        		"where b.mp_indicator_id = "+indicator_id+")/coalesce((case when a.cumulatedamt is null then 1 when a.cumulatedamt = 0 then 1 else a.cumulatedamt end),1)) " +
	        		"end from mp_indicatordetail a where " +
	        		"a.mp_indicatordetail_id = ?";
	        log.config("sqlamt3 "+sqlamt3);
	        log.config("indicatordetail_id "+indicatordetail_id);
	        BigDecimal AnualResult = DB.getSQLValueBD(po.get_TrxName(), sqlamt3, indicatordetail_id);
	        log.config("Anual Result"+AnualResult);
	        
	        BigDecimal Ponderator = Env.ZERO;
	        if(intype.compareTo("01")==0)
			{
		        if(AnualResult.compareTo(Env.ONEHUNDRED) == 1)
		        	Ponderator = i.getweighting();
		        else
		        	Ponderator = (AnualResult.multiply(i.getweighting())).divide(Env.ONEHUNDRED);

			}
	        else if(intype.compareTo("02")==0)
			{
		        if(AnualResult.compareTo(Env.ONE) <= 0)
		        	Ponderator = (AnualResult.multiply(i.getweighting())).divide(Env.ONEHUNDRED);

	
		        else
		        	Ponderator = i.getweighting();
		    }
	        
	        
	        /**mfrojas
	         * Aproximar Ponderator a 3 decimales 
	         */
	        
	        Ponderator = Ponderator.setScale(3, RoundingMode.HALF_UP);
	        log.config("ponderator es "+Ponderator);
	        //ininoles antes de setear se multiplicara por factor de indicador
	        X_MP_Indicator ind = new X_MP_Indicator(po.getCtx(), i.getMP_Indicator_ID(), po.get_TrxName());
	        BigDecimal Factor = (BigDecimal)ind.get_Value("Factor");
	        if(Factor == null)
	        	Factor = Env.ZERO;
/*	        Ponderator = Ponderator.multiply(Factor);
	        //ininoles end
	        i.set_CustomColumn("Amt",Ponderator);
	        i.save();
	        */
	        //mfrojas 20180405 se genera nueva formula para cumplimiento ponderado en %
	        
	        BigDecimal porcentajecump= Env.ZERO;
	        if(intype.compareTo("01")==0)
	        {
	        	if(CumulatedResultActualsuma.compareTo(Env.ZERO)==0)
	        	{
	        		porcentajecump = Env.ZERO;
	        	}
	        	else if(CumulatedResultActualsuma.compareTo(meta)>0)
	        	{
	        		porcentajecump = i.getweighting();
	        	}
	        	else
	        	{
	        		porcentajecump = CumulatedResultActualsuma;
	        		if(meta.compareTo(Env.ZERO)<=0)
	        			porcentajecump = porcentajecump.divide(Env.ONE);
	        		else
	        			porcentajecump = porcentajecump.divide(meta,3,RoundingMode.HALF_UP);
	        		
	        		porcentajecump = porcentajecump.multiply(i.getweighting());
	        	}
	        	
	        }
	        else if (intype.compareTo("02")==0)
	        {
	        	if(CumulatedResultActualsuma.compareTo(Env.ZERO)==0)
	        	{
	        		porcentajecump = Env.ZERO;
	        	}
	        	else if(CumulatedResultActualsuma.compareTo(meta)<0)
	        	{
	        		porcentajecump = i.getweighting();
	        	}
	        	else
	        	{
	        		porcentajecump = meta;
	        		if(CumulatedResultActualsuma.compareTo(Env.ZERO)==0)
	        			porcentajecump = porcentajecump.divide(Env.ONE);
	        		else
	        			porcentajecump = porcentajecump.divide(CumulatedResultActualsuma,3,RoundingMode.HALF_UP);
	        		porcentajecump = porcentajecump.multiply(i.getweighting());
	        	}
	        }
	        porcentajecump = porcentajecump.setScale(2,RoundingMode.DOWN);
	        i.set_CustomColumn("Amt",porcentajecump);
	        i.save();

		}
		return null;
	}	//	modelChange

	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		
		return null;
	}	//	docValidate
	
	/**
	 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);

		return null;
	}	//	login


	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID


	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("ModelPrice");
		return sb.toString ();
	}	//	toString


	

}	