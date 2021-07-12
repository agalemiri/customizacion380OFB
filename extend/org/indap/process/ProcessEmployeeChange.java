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
package org.indap.process;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import org.compiere.model.X_HR_EmployeeBackup;
import org.compiere.model.X_HR_EmployeeChange;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.X_HR_Employee;
/**
 *	
 *	
 *  @author Italo Niñoles ininoles
 *  @version $Id: ProcessNomina.java $
 */
public class ProcessEmployeeChange extends SvrProcess
{
	private int				p_HR_EmployeeChange = 0; 
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{	
		p_HR_EmployeeChange=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_HR_EmployeeChange > 0)
		{
			X_HR_EmployeeChange change = new X_HR_EmployeeChange(getCtx(), p_HR_EmployeeChange, get_TrxName());
			//se busca registro a modificar
			int ID_Employee = DB.getSQLValue(get_TrxName(), "SELECT MAX(HR_Employee_ID) FROM HR_Employee" +
					" WHERE IsActive = 'Y' AND C_Bpartner_ID = "+change.getC_BPartner_ID());
			if(ID_Employee > 0)
			{
				X_HR_Employee emp = new X_HR_Employee(getCtx(), ID_Employee, get_TrxName());
				String bOld = emp.get_ValueAsString("benio").toString();
				//cambio de grado
				if(change.getgrade() != null && change.getgrade().trim().length()>0)
				{
					//cuando se cambia de grado se calcula la absorcion de bienios.
					//sueldo base grado antiguo

					String bienioValue = "";
					BigDecimal MontoAProteger = Env.ZERO;
					MontoAProteger = DB.getSQLValueBD(get_TrxName(), "SELECT MAX(MinValue) FROM HR_Attribute " +
							" WHERE HR_Concept_ID=2000101 AND grade = '"+emp.get_ValueAsString("grade")+"' AND IsActive = 'Y'");
					bienioValue = DB.getSQLValueString(get_TrxName(), "SELECT Description FROM AD_Ref_List " +
							" WHERE AD_Reference_ID=2000157 AND Value = '"+emp.get_ValueAsString("benio")+"'");
					int bienioAux = Integer.parseInt(bienioValue);
					bienioAux++;						
					MontoAProteger = MontoAProteger.add(MontoAProteger.multiply(new BigDecimal("0.02").multiply(new BigDecimal(bienioAux))));
					MontoAProteger = MontoAProteger.setScale(0, RoundingMode.HALF_EVEN);
					
					BigDecimal nuevoSueldo = DB.getSQLValueBD(get_TrxName(), "SELECT MAX(MinValue) FROM HR_Attribute " +
							" WHERE HR_Concept_ID=2000101 AND grade = '"+change.getgrade()+"' AND IsActive = 'Y'");
					BigDecimal dif = MontoAProteger.subtract(nuevoSueldo);
					change.setgradeOld(emp.get_ValueAsString("grade"));
					//SOLO SE REALIZA CALCULO SI SE SUBE DE GRADO
					int gradoOld = Integer.parseInt(DB.getSQLValueString(get_TrxName(), "SELECT name FROM AD_Ref_List " +
							" WHERE AD_Reference_ID=2000138 AND Value = '"+change.getgradeOld()+"'"));
					int gradoNew = Integer.parseInt(DB.getSQLValueString(get_TrxName(), "SELECT name FROM AD_Ref_List " +
							" WHERE AD_Reference_ID=2000138 AND Value = '"+change.getgrade()+"'"));
					if(gradoOld > gradoNew)
					{
						
						if(dif.compareTo(Env.ZERO) > 0) // si es positivo se calcula absorcion
						{
							BigDecimal bienioFinal = dif.divide(nuevoSueldo.multiply(new BigDecimal("0.02")),3,RoundingMode.HALF_EVEN);
							bienioFinal = bienioFinal.setScale(0,BigDecimal.ROUND_UP);		
							emp.set_CustomColumn("benio", DB.getSQLValueString(get_TrxName(), "SELECT Value FROM AD_Ref_List " +
									" WHERE AD_Reference_ID=2000157 AND description = '"+bienioFinal+"'"));
						}
						else //si es negativo o cero se queda en 0 bienios
						{
							emp.set_CustomColumn("benio", DB.getSQLValueString(get_TrxName(), "SELECT Value FROM AD_Ref_List " +
									" WHERE AD_Reference_ID=2000157 AND description = '0'"));
						}
					}
					emp.set_CustomColumn("grade", change.getgrade());	
				}
				//cambio de estamento
				if(change.getHR_Job_ID() > 0)
				{
					change.setHR_JobOld_ID(emp.getHR_Job_ID());
					emp.setHR_Job_ID(change.getHR_Job_ID());
				}
				//cambio de region
				if(change.getC_Region_ID() > 0)
				{
					change.setC_RegionOld_ID(emp.get_ValueAsInt("C_Region_ID"));
					emp.set_CustomColumn("C_Region_ID", change.getC_Region_ID());
				}
				//cambio "es profesional"
				if(change.isProfessional())
				{
					change.setIsProfessionalOld(false);
					emp.set_CustomColumn("isProfessional", true);					
				}	
				/*else
				{
					change.setIsProfessionalOld(true);
					emp.set_CustomColumn("isProfessional", false);			
				}*/
				//cambio de benio
				if(change.get_ValueAsString("benio") != null && change.get_ValueAsString("benio").trim().length()>0)
				{
					change.set_CustomColumn("benioOld", emp.get_ValueAsString("benio"));
					emp.set_CustomColumn("benio", change.get_ValueAsString("benio"));
				}
				emp.saveEx(get_TrxName());
				//cambio asignacion de responsabilidad
				if(change.get_ValueAsBoolean("IsAResponsibility"))
				{
					change.set_CustomColumn("IsAResponsibilityOld", false);
					emp.set_CustomColumn("IsAResponsibility", true);					
				}
				if(change.get_ValueAsBoolean("IsBienestar"))
				{
					change.set_CustomColumn("IsBienestarOld", false);
					emp.set_CustomColumn("IsBienestar", true);					
				}
				if(change.get_ValueAsBoolean("IsCritical"))
				{
					change.set_CustomColumn("IsCriticalOld", false);
					emp.set_CustomColumn("IsCritical", true);					
				}	
				/*else
				{
					change.set_CustomColumn("IsAResponsibilityOld", true);
					emp.set_CustomColumn("IsAResponsibility", false);			
				}*/
				//ininoles se genera registro de respaldo de estado
				X_HR_EmployeeBackup back = new X_HR_EmployeeBackup(getCtx(), 0, get_TrxName());
				back.setC_BPartner_ID(change.getC_BPartner_ID());
				back.setHR_Employee_ID(ID_Employee);
				back.setAD_Org_ID(emp.getAD_Org_ID());
				back.setAFP_Porcent((BigDecimal)emp.get_Value("AFP_Porcent"));
				if(emp.get_ValueAsString("Benio") != null && emp.get_ValueAsString("Benio").trim().length() > 0)
					back.setBenio(emp.get_ValueAsString("Benio"));
				back.setDateStart((Timestamp)change.get_Value("DateAcct"));
				back.setgrade(emp.get_ValueAsString("grade"));
				back.setISAPRE_Porcent((BigDecimal)emp.get_Value("ISAPRE_Porcent"));
				back.setISAPRE_UF((BigDecimal)emp.get_Value("ISAPRE_UF"));
				back.setIsAResponsibility(emp.get_ValueAsBoolean("IsAResponsibility"));
				back.setIsINP(emp.get_ValueAsBoolean("IsINP"));
				back.setIsProfessional(emp.get_ValueAsBoolean("IsProfessional"));
				back.setMaxAmount((BigDecimal)emp.get_Value("MaxAmount"));
				back.setHR_Job_ID(emp.get_ValueAsInt("HR_Job_ID"));
				back.setC_Region_ID(emp.get_ValueAsInt("C_Region_ID"));
				back.setTopeIsapre_UF((BigDecimal)emp.get_Value("TopeIsapre_UF"));
				back.setTypeUniqueTax(emp.get_ValueAsString("TypeUniqueTax"));
				back.setUseUniqueTax(emp.get_ValueAsBoolean("UseUniqueTax"));
				back.saveEx(get_TrxName());
				
				//se actualizan bienios en change en caso de que se haya hecho absorción
				//para usarlos en parcialidad
				if(bOld.compareTo(emp.get_ValueAsString("benio")) != 0)
				{
					change.set_CustomColumn("benioOld", bOld);
					change.set_CustomColumn("benio", emp.get_ValueAsString("benio"));
				}
				
			}
			change.setProcessed(true);
			change.saveEx(get_TrxName());
		}
		return "Procesado";
	}
}
