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
package org.compiere.apps.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;



import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.Combobox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.panel.StatusBarPanel;
import org.adempiere.webui.window.FDialog;
import org.compiere.apps.form.Reserva;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MQuery;
import org.compiere.model.MUser;
import org.compiere.model.PrintInfo;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.print.ReportViewerProvider;
import org.compiere.process.ProcessInfo;
import org.compiere.swing.CPanel;
import org.compiere.util.ASyncProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
//import org.pdi.jesan.model.X_JES_Paciente;
//import org.pdi.process.CXFConnector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.East;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Separator;

/**
 * Allocation Form
 *
 * @author  Jorg Janke
 * @version $Id: VAllocation.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * 
 * Contributor : Fabian Aguilar - OFBConsulting - Multiallocation
 */
public class CopyOfReservaHora extends Reserva
	implements IFormController, EventListener, WTableModelListener, ValueChangeListener, ASyncProcess, FormPanel, ActionListener, TableModelListener, VetoableChangeListener
{
	private CPanel panel = new CPanel();
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						
	private CustomForm form = new CustomForm();

	public String tipoEmpresa = "";
	public int tipoEmp = -1;
	public int monto = 0;
	public int Paciente_ID = -1;
	public String logError = "";
	public int imprimirID_Cita = 0;
	public String codContratoStr = "";
	public int codContrato = 1;
	
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	public CopyOfReservaHora()
	{
		Env.setContext(Env.getCtx(), form.getWindowNo(), "IsSOTrx", "Y");   //  defaults to no
		try
		{
			super.dynInit();
			dynInit();
			zkInit();
			inicioTablas();
			southPanel.appendChild(new Separator());
			southPanel.appendChild(statusBar);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
	}	//	init
	
	//
	private Borderlayout mainLayout = new Borderlayout();
	private Panel parameterPanel = new Panel();
	private Panel allocationPanel = new Panel();
	private Grid parameterLayout = GridFactory.newGridLayout();
	private Label tratanteLabel = new Label();
	private WSearchEditor tratanteSearch = null;
	private WListbox reservasTable = ListboxFactory.newDataTable();
	private WListbox horasTable = ListboxFactory.newDataTable();
	private Borderlayout infoPanel = new Borderlayout();
	private Panel horasPanel = new Panel();
	private Label horaLabel = new Label();
	
	private Panel reservasPanel = new Panel();
	private Label horasLabel = new Label();
	private Label reservasLabel = new Label();
	private Borderlayout horasLayout = new Borderlayout();
	private Borderlayout reservasLayout = new Borderlayout();
	private Label horasInfo = new Label();
	private Label reservasInfo = new Label();
	private Grid allocationLayout = GridFactory.newGridLayout();
	private Button searchButton = new Button();
	private StatusBarPanel statusBar = new StatusBarPanel();
	private Label dateLabel = new Label();
	private Label signacionLabel = new Label();
	private WDateEditor dateField = new WDateEditor();
	private Label especialidadLabel = new Label();
	private WTableDirEditor especialidadPick;
	
	private Panel asignarPanel = new Panel();
	private Grid asignarLayout = GridFactory.newGridLayout();
	private Label pacienteLabel = new Label();
	private Label rutValidarLabel = new Label();
	private Label rutDigitoLabel = new Label();
	private WSearchEditor pacienteSearch = null;
	private Label pacienteDescLabel = new Label();
	private Textbox  pacienteDesc = new Textbox();
	private Textbox  rutCompleto = new Textbox();
	private Textbox  digito = new Textbox();
	private Label tipoHoraLabel = new Label();
	private WTableDirEditor tipoHoraPick;
	//ininoles nuevoa campos para recien nacido
	private Label rNacidoLabel = new Label();
	private Checkbox rNacido = new Checkbox();
	private Label  nameRNLabel = new Label();
	private Textbox nameRN = new Textbox();
	//ininoles end
	private Button reservaButton = new Button();
	private Button validarButton = new Button();
	private Button imprimirButton = new Button();
	
	private Panel estadoPanel = new Panel();
	private Grid estadoLayout = GridFactory.newGridLayout();
	private Label actualizacionLabel = new Label();
	private Label nuevoEstadoLabel = new Label();	
	private Combobox nuevoEstadoBox = new Combobox();
	private Button citaButton = new Button();
	
	private Label academiaLabel = new Label();
	private Textbox  academia = new Textbox();
	private Label cursoLabel = new Label();
	private Textbox  curso = new Textbox();
	private Label acargoLabel = new Label();
	private Textbox  acargo = new Textbox();
	
	
	
	
	private Panel southPanel = new Panel();

	/**
	 *  Static Init
	 *  @throws Exception
	 */
	private void zkInit() throws Exception
	{
		//
		form.appendChild(mainLayout);
		mainLayout.setWidth("99%");
		mainLayout.setHeight("100%");
		dateLabel.setText(Msg.getMsg(Env.getCtx(), "Date"));
		//
		parameterPanel.appendChild(parameterLayout);
		allocationPanel.appendChild(allocationLayout);
		asignarPanel.appendChild(asignarLayout);
		estadoPanel.appendChild(estadoLayout);
		
		signacionLabel.setText("Reserva Hora Paciente");
		tratanteLabel.setText("Profesional");
		horasLabel.setText("Lista de Horas");
		horaLabel.setText("Reserva de hora");
		reservasLabel.setText("Lista de Citas");
		academiaLabel.setText("Academia");
		cursoLabel.setText("Curso");
		acargoLabel.setText("A Cargo");
		horasPanel.appendChild(horasLayout);
		reservasPanel.appendChild(reservasLayout);
		reservasInfo.setText(".");
		horasInfo.setText(".");
		
		
		rutValidarLabel.setText("Rut");
		rutDigitoLabel.setText("-");
		pacienteLabel.setText("Paciente");		
		pacienteDescLabel.setText("Contacto (Movil)");
		tipoHoraLabel.setText("Tipo Reserva");
		rNacidoLabel.setText("Recien Nacido");
		nameRNLabel.setText("Nombre Recien Nacido");
		reservaButton.setLabel("Realizar Reserva");
		reservaButton.addActionListener(this);
		
		actualizacionLabel.setText("Actualizacion de Cita");
		nuevoEstadoLabel.setText("Nuevo Estado");
		citaButton.setLabel("Confirmar Estado");
		citaButton.addActionListener(this);
		
		searchButton.setLabel(Msg.getMsg(Env.getCtx(), "Buscar"));
		searchButton.addActionListener(this);
		
		validarButton.setLabel(Msg.getMsg(Env.getCtx(), "Validar"));
		validarButton.addActionListener(this);
		
		imprimirButton.setLabel(Msg.getMsg(Env.getCtx(), "Imprimir Cita"));
		imprimirButton.addActionListener(this);
		
		//ininoles nuevos campos
		/*rNacido.setChecked(false);
		rNacido.addActionListener(this);*/
		
		
		especialidadLabel.setText("Especialidad");
		
		North north = new North();
		north.setStyle("border: none");
		mainLayout.appendChild(north);
		north.appendChild(parameterPanel);
		
		Rows rows = null;
		Row row = null;
		
		parameterLayout.setWidth("800px");
		rows = parameterLayout.newRows();
		row = rows.newRow();
		row.appendChild(tratanteLabel.rightAlign());
		row.appendChild(tratanteSearch.getComponent());
		row.appendChild(dateLabel.rightAlign());
		row.appendChild(dateField.getComponent());
		row.appendChild(especialidadLabel.rightAlign());
		row.appendChild(especialidadPick.getComponent());
		row.appendChild(searchButton);
		
		South south = new South();
		south.setStyle("border: none");
		mainLayout.appendChild(south);
		south.appendChild(southPanel);
		southPanel.appendChild(allocationPanel);
		allocationPanel.appendChild(allocationLayout);
		allocationLayout.setWidth("400px");
		
		
		horasPanel.appendChild(horasLayout);
		horasPanel.setWidth("100%");
		horasPanel.setHeight("100%");
		horasLayout.setWidth("100%");
		horasLayout.setHeight("100%");
		horasLayout.setStyle("border: none");
		
		reservasPanel.appendChild(reservasLayout);
		reservasPanel.setWidth("100%");
		reservasPanel.setHeight("100%");
		reservasLayout.setWidth("100%");
		reservasLayout.setHeight("100%");
		reservasLayout.setStyle("border: none");
		
		north = new North();
		north.setStyle("border: none");
		horasLayout.appendChild(north);
		north.appendChild(horasLabel);
		south = new South();
		south.setStyle("border: none");
		horasLayout.appendChild(south);
		south.appendChild(horasInfo.rightAlign());
		Center center = new Center();
		horasLayout.appendChild(center);
		center.appendChild(horasTable);
		horasTable.setWidth("70%");
		horasTable.setHeight("99%");
		center.setStyle("border: none");
		
		//faaguilar
		East east= new East();
		east.setStyle("border: none");
		horasLayout.appendChild(east);
		east.appendChild(asignarPanel);
		rows = asignarLayout.newRows();
		row = rows.newRow();
		row.appendChild(signacionLabel);
		row = rows.newRow();
		row.appendChild(rutValidarLabel);
		row.appendChild(rutCompleto);
		row.appendChild(rutDigitoLabel);
		row.appendChild(digito);
		row = rows.newRow();
		row.appendChild(validarButton);
		row = rows.newRow();
		row.appendChild(pacienteLabel);
		row.appendChild(pacienteSearch.getComponent());
		row = rows.newRow();
		row.appendChild(pacienteDescLabel);
		row.appendChild(pacienteDesc);
		row = rows.newRow();
		row.appendChild(tipoHoraLabel);
		row.appendChild(tipoHoraPick.getComponent());
		row = rows.newRow();
		row.appendChild(academiaLabel);
		row.appendChild(academia);
		row = rows.newRow();
		row.appendChild(cursoLabel);
		row.appendChild(curso);
		row = rows.newRow();
		row.appendChild(acargoLabel);
		row.appendChild(acargo);
		row = rows.newRow();
		row.appendChild(rNacidoLabel);
		row.appendChild(rNacido);
		row = rows.newRow();
		row.appendChild(nameRNLabel);
		row.appendChild(nameRN);
		row = rows.newRow();
		row.appendChild(reservaButton);
		row.appendChild(imprimirButton);
		
		north = new North();
		north.setStyle("border: none");
		reservasLayout.appendChild(north);
		north.appendChild(reservasLabel);
		south = new South();
		south.setStyle("border: none");
		reservasLayout.appendChild(south);
		south.appendChild(reservasInfo.rightAlign());
		center = new Center();
		reservasLayout.appendChild(center);
		center.appendChild(reservasTable);
		reservasTable.setWidth("80%");
		reservasTable.setHeight("99%");
		center.setStyle("border: none");
		
		//faaguilar
		east= new East();
		east.setStyle("border: none");
		reservasLayout.appendChild(east);
		east.appendChild(estadoPanel);
		rows = estadoLayout.newRows();
		row = rows.newRow();
		row.appendChild(actualizacionLabel);
		row = rows.newRow();
		row.appendChild(nuevoEstadoLabel);
		row.appendChild(nuevoEstadoBox);
		row = rows.newRow();
		row.appendChild(citaButton);
		
		//
		center = new Center();
		center.setFlex(true);
		mainLayout.appendChild(center);
		center.appendChild(infoPanel);
		
		infoPanel.setStyle("border: none");
		infoPanel.setWidth("100%");
		infoPanel.setHeight("100%");
		
		north = new North();
		north.setStyle("border: none");
		north.setHeight("49%");
		infoPanel.appendChild(north);
		north.appendChild(horasPanel);
		north.setSplittable(true);
		center = new Center();
		center.setStyle("border: none");
		center.setFlex(true);
		infoPanel.appendChild(center);
		center.appendChild(reservasPanel);
		
		
		
		
	}   //  jbInit

	/**
	 *  Dynamic Init (prepare dynamic fields)
	 *  @throws Exception if Lookups cannot be initialized
	 */
	public void dynInit() throws Exception
	{
		
		// Organization filter selection
		int AD_Column_ID = 1002805; //C_Period.AD_Org_ID (needed to allow org 0)
		//ininoles se hace referencia a otro campo para poder filtrar
		//int AD_Column_ID = 1000252; 
		MLookup lookupOrg = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), 0, AD_Column_ID, DisplayType.Table);
		especialidadPick = new WTableDirEditor("JES_Especialidad_ID", true, false, true, lookupOrg);
		
		//especialidadPick.addValueChangeListener(this);
		AD_Column_ID = 1000016;        
		MLookup lookupHH = MLookupFactory.get (Env.getCtx(), form.getWindowNo(), 0, AD_Column_ID, DisplayType.Table);
		tipoHoraPick = new WTableDirEditor("JES_TipoAtencion_ID", true, false, true, lookupHH);
		tipoHoraPick.addValueChangeListener(this);
		
		
		//  BPartner
		AD_Column_ID = 1000251;        
		MLookup lookupBP = MLookupFactory.get (Env.getCtx(), form.getWindowNo(), 0, AD_Column_ID, DisplayType.Search);
		tratanteSearch = new WSearchEditor("JES_Tratante_ID", true, false, true, lookupBP);
		tratanteSearch.addValueChangeListener(this);//ininoles 
		
		AD_Column_ID = 1000010;        
		MLookup lookupPa = MLookupFactory.get (Env.getCtx(), form.getWindowNo(), 0, AD_Column_ID, DisplayType.Search);
		pacienteSearch = new WSearchEditor("JES_Paciente_ID", true, false, true, lookupPa);
		//pacienteSearch.addValueChangeListener(this);
		
		nuevoEstadoBox.appendItem("Confirmar", "RC");
		nuevoEstadoBox.appendItem("Anular", "AN");
		//ininoles se agrega nuevo estado para volver a reserva no confirmada
		if(Env.getAD_Role_ID(Env.getCtx()) == 1000000 || Env.getAD_Role_ID(Env.getCtx()) == 1000012)
			nuevoEstadoBox.appendItem("Borrar Reservas(Crítico)", "DI");
		
		//ininoles nuevo campos
		rNacido.setChecked(false);
		rNacido.addActionListener(this);
		
		//Translation
		cursoLabel.setVisible(false);
		curso.setVisible(false);
		academiaLabel.setVisible(false);
		academia.setVisible(false);
		acargoLabel.setVisible(false);
		acargo.setVisible(false);
		nameRNLabel.setVisible(false);
		nameRN.setVisible(false);

		//  Date set to Login Date
		dateField.setValue(Env.getContextAsDate(Env.getCtx(), "#Date"));
		//set especialidad
				
		dateField.addValueChangeListener(this);		
	}   //  dynInit
	
	/**************************************************************************
	 *  Action Listener.
	 *  - MultiCurrency
	 *  - Allocate
	 *  @param e event
	 */
	public void onEvent(Event e)
	{
		log.config("");
		if (e.getTarget().equals(searchButton))
		{
			if(tratanteSearch.getValue()==null || dateField.getValue() == null || especialidadPick.getValue()==null)
			{
				FDialog.error(form.getWindowNo(), " Debe indicar todos los parametros de busqueda");
				return;
			}					
			loadHoras();
			limparReservastable();
		}
		else if (e.getTarget().equals(reservaButton))
		{	
			if(tipoHoraPick.getValue() == null)
			{
				FDialog.error(form.getWindowNo(), " Debe indicar el Tipo de Reserva");
				return;
			}
			if(especialidadPick.getValue() == null)
			{
				FDialog.error(form.getWindowNo(), " Debe indicar la Especialidad");
				return;
			}
			if(pacienteSearch.getValue() == null || Integer.parseInt(pacienteSearch.getValue().toString()) < 1)
			{
				log.config("valor paciente search: "+pacienteSearch);
				log.config("valor paciente search to string: "+pacienteSearch.toString());
				log.config("valor paciente entero: "+Integer.parseInt(pacienteSearch.getValue().toString()));
				FDialog.error(form.getWindowNo(), " Debe indicar paciente válido");
				return;
			}
			try
			{
				//	ininoles nueva validacion para usuarios que se dan solo sus propias horas
				log.config("Inicio validacion");
				int cantError = 0;
				int id_tratanteEnv =(Integer) tratanteSearch.getValue();
				log.config("cantError = "+cantError+" id_tratante = "+id_tratanteEnv);
				if (id_tratanteEnv > 0)
				{								
					log.config("Dentro de if(id_tratanteEnv > 0)");
					try 
					{
						log.config("Busca User en entorno = "+Env.getAD_User_ID(Env.getCtx()));
						MUser userEnv = new MUser(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()),null);
						log.config("ID Usuario: "+userEnv.get_ID());
						if (userEnv.get_ValueAsBoolean("isAllAtention") == false)
						{	
							String sqlEnvValid = "SELECT COUNT(1) FROM JES_Tratante WHERE AD_User_ID = "+userEnv.get_ID()+ 
									" AND JES_Tratante_ID = "+id_tratanteEnv;
							int cantValid = DB.getSQLValue(null, sqlEnvValid);						
							if (cantValid == 0)
							{
								cantError = 1;
							}
						}
						else
						{
							cantError = 0;
						}
					}
					catch(Exception ex)
					{
						cantError = 1;
					}					
					if (cantError > 0)
					{
						FDialog.error(form.getWindowNo(), "Accion No permitida por este Rol, Solo Puede Agendar Horas de Especialistas Asociados");
						return;
					}
				}
				log.config("FIN validacion");
			}
			catch(Exception ex)
			{
				;
			}
			
			if((Integer)tipoHoraPick.getValue()==1000000 || (Integer)tipoHoraPick.getValue()==1000001 || (Integer)tipoHoraPick.getValue()==1000006 || (Integer)tipoHoraPick.getValue()==1000007 || (Integer)tipoHoraPick.getValue()==1000008)//consulta - procedimiento - sobrecupo
			{
				if(pacienteSearch.getValue() == null)
				{
					FDialog.error(form.getWindowNo(), " Debe indicar el Paciente");
					return;
				}

				Trx trx = Trx.get(Trx.createTrxName("AL"), true);
				
				//llamarWS(pacienteSearch.getValue(),trx);  //faaguilar nuevo
				
				log.config("Valor Tratante antes de crear cita: "+tratanteSearch.getValue());
				int cita_id=saveReserva(form.getWindowNo(),horasTable,pacienteDesc.getText(),dateField.getValue(),especialidadPick.getValue(),pacienteSearch.getValue(),tipoHoraPick.getValue() ,trx.getTrxName(), null, null, null,tratanteSearch.getValue());
				log.config("Valor Tratante despues de crear cita: "+tratanteSearch.getValue());
				
				//ininoles actualizar movil con datos de la hora tomada		
				if (pacienteDesc.getText().length() > 0 && pacienteDesc.getText().trim() != " ")
				{
					String sqlUpdateMovil = "UPDATE JES_Paciente SET movil = '"+pacienteDesc.getText()+"' WHERE  JES_Paciente_ID = "+(Integer)pacienteSearch.getValue();
					DB.executeUpdate(sqlUpdateMovil,null);
				}
				//ininoles nuevo campo
				
				imprimirID_Cita = cita_id; 
				//end ininoles
				
				//imprimircita(cita_id); //faaguilar nuevo				
				trx.commit();
				trx.close();
				
				if(rNacido.isChecked())
				{
					String sqlUpdateMovil = "UPDATE JES_Cita SET RNacido='Y', nombreRNacido='"+nameRN.getText()+"'  WHERE  JES_Cita_ID="+cita_id;
					DB.executeUpdate(sqlUpdateMovil,null);
				}
				
				trx.commit();
				trx.close();
				
			}
			else if((Integer)tipoHoraPick.getValue()==1000004)// postulantes
			{
				if(curso.getValue() == null || acargo.getValue()== null ||  academia.getValue()== null)
				{
					FDialog.error(form.getWindowNo(), " Los campos de Curso - Academia y A cargo son Obligatorios");
					return;
				}
				if(pacienteDesc.getValue() == null)
				{
					FDialog.error(form.getWindowNo(), " La Descripcion es Obligatoria");
					return;
				}
				
				Trx trx = Trx.get(Trx.createTrxName("AL"), true);
				
				saveReserva(form.getWindowNo(),horasTable,pacienteDesc.getText(),dateField.getValue(),especialidadPick.getValue(),pacienteSearch.getValue(),tipoHoraPick.getValue() ,trx.getTrxName(), curso.getValue(), acargo.getValue(), academia.getValue(),tratanteSearch.getValue());
						
				trx.commit();
				trx.close();
			}
			else // motivo
			{
				if(pacienteDesc.getValue() == null)
				{
					FDialog.error(form.getWindowNo(), " La Descripcion es Obligatoria");
					return;
				}
				Trx trx = Trx.get(Trx.createTrxName("AL"), true);
				
				saveReserva(form.getWindowNo(),horasTable,pacienteDesc.getText(),dateField.getValue(),especialidadPick.getValue(),pacienteSearch.getValue(),tipoHoraPick.getValue() ,trx.getTrxName(), null, null, null,tratanteSearch.getValue());
						
				trx.commit();
				trx.close();
			}
			loadHoras();
			
			pacienteSearch.setValue(0);
			pacienteDesc.setValue(" ");
			//tipoHoraPick.setValue(0);		
			//imprimircita(imprimirID_Cita); //faaguilar nuevo			
		}
		else if (e.getTarget().equals(citaButton))
		{
			if(nuevoEstadoBox.getSelectedIndex()==-1)
			{
				FDialog.error(form.getWindowNo(), " Debe indicar un nuevo Estado para la Hora");
				return;
			}
			Trx trx = Trx.get(Trx.createTrxName("AL"), true);
			
			updateCita(form.getWindowNo(),reservasTable,nuevoEstadoBox.getSelectedItem().getValue(),trx.getTrxName());
						
			trx.commit();
			trx.close();
			loadCitas ();
			loadHoras();//ininoles se agrega actualizacion de horas dadas al confirmar hora
		}
		else  if (e.getTarget().equals(validarButton))
		{
			Trx trx = Trx.get(Trx.createTrxName("AL"), true);
			
			if(rutCompleto.getValue() == null)
			{
				FDialog.error(form.getWindowNo(), "Debe ingresar un rut a validar");
				return;
			}		
			
			String digitoValid = generarDigito(rutCompleto.getText());
			/*
			if (digitoValid.equalsIgnoreCase(digito.getText()))
			{
				FDialog.error(form.getWindowNo(), "Digito Invalido");
				return;				
			}*/
			//seteamos variable log
			logError = "";
			
			String rutCompletoCalc = rutCompleto.getText();
			//ininoles llamada para conocer tipo empresa
			//reseteo variable tipo empresa
			tipoEmpresa = "";
			//reseteo variable tipo contrato
			codContratoStr = "";
			//seteamos paciente ID
			Paciente_ID = 0;
			//seteamos monto
			monto = 0;
	
			
			//seteo de variables de fecha
			Timestamp fnacValid = new Timestamp (System.currentTimeMillis());
			Calendar fValid = Calendar.getInstance();
			fValid.setTimeInMillis(fnacValid.getTime()); 
			
			int p_day = fValid.get(Calendar.DAY_OF_MONTH);
			log.config("Dia : "+p_day);
			int p_mes = fValid.get(Calendar.MONTH)+1;
			log.config("Mes : "+p_mes);
			
			int p_year = fValid.get(Calendar.YEAR);
			
			if(p_day <= 10)
			{
				p_mes = p_mes - 2;
			}
			else
			{
				p_mes = p_mes - 1;
			}
			if(p_mes == 0)
			{
				p_mes = 12;
				p_year = p_year-1;
			}
			if(p_mes == -1)
			{
				p_mes = 11;
				p_year = p_year-1;
			}
			
			if (tipoEmpresa != null && tipoEmpresa != "")
			{
				if (tipoEmpresa.length()>0)
				{
					tipoEmp = Integer.parseInt(tipoEmpresa);
					String codigoJes = "D0000520";
					//ininoles nueva validacion cod contrato
					if(codContratoStr != null && codContratoStr != "")
					{
						if (codContratoStr.length()>0)
						{
							codContrato = Integer.parseInt(codContratoStr);
							if(codContrato == 2)
							{
								codigoJes = "D0000352";
							}else if (codContrato == 1) 
							{
								codigoJes = "D0000520";
							}else if (codContrato == 3)
							{
								codigoJes = "D0000352";								
							}else
							{
								codigoJes = "D0000352";								
							}	
						}
					}	
					
					if (monto > 0)
					{				
						int TempPaciente_ID = DB.getSQLValue(trx.getTrxName(), "Select Jes_Paciente_ID from Jes_Paciente where RutSD = '"+rutCompletoCalc+"'");
						log.config("TempPaciente despues de if:"+Paciente_ID);
						if(TempPaciente_ID <= 0)
						{
							TempPaciente_ID = 0;
						}
						
						//llamada para actualizacion de informacion en la DB						
						
						trx.commit();
						
						if (Paciente_ID < 1)
						{
							Paciente_ID = DB.getSQLValue(trx.getTrxName(), "Select Jes_Paciente_ID from Jes_Paciente where RutSD = '"+rutCompletoCalc+"' and Estado = 'Y' ");
						}						
						//ininoles se copia valor de paciente a campo de busqueda 
						if(Paciente_ID > 0)
						{
							pacienteSearch.setValue(Paciente_ID);
						}
					}
					else
					{
						//ininoles si es funcionario sin aporte se pregunta si es carga para agotar todas las posibilidades
						int TempPaciente2_ID = DB.getSQLValue(trx.getTrxName(), "Select Jes_Paciente_ID from Jes_Paciente where RutSD = '"+rutCompletoCalc+"'");
						log.config("2 TempPaciente despues de if:"+Paciente_ID);
						if(TempPaciente2_ID <= 0)
							TempPaciente2_ID = 0;
						if(Paciente_ID <= 0)
							logError = "Sin Aporte Realizado ";
					}
				}
				else
				{
					logError = logError + " Rut No Existe WS ";
				}
			}
			else if (tipoEmpresa == null || tipoEmpresa == "")
			{
				if (Paciente_ID < 1)
				{
					try 
					{
						int TempPaciente2_ID = DB.getSQLValue(trx.getTrxName(), "Select Jes_Paciente_ID from Jes_Paciente where RutSD = '"+rutCompletoCalc+"'");
						if(TempPaciente2_ID <= 0)
						{
							TempPaciente2_ID = 0;
						}						
						log.config("Antes de LLamado de WSCarga");
						log.config("ID Paciente Temporal: "+TempPaciente2_ID);

						log.config("Despues de LLamado WSCarga");
						trx.commit();
					}catch(Exception ex)
					{
						Paciente_ID = 0;
						ex.printStackTrace();
						log.log(Level.SEVERE,"",ex);
						log.config(ex.getMessage());
					}					
					//Paciente_ID = DB.getSQLValue(trx.getTrxName(), "Select Jes_Paciente_ID from Jes_Paciente where RutSD = '"+rutCompletoCalc+"' and Estado = 'Y' ");
				}
				if(Paciente_ID > 0)
				{
					pacienteSearch.setValue(Paciente_ID);
				}
				else
				{
					logError = logError + " - Rut No es Carga con Aporte";
				}					
			}
			if (Paciente_ID < 1)
			{					
				if (logError.length() < 5)
				{
					logError = "Rut No Existe WS";
				}
				int PacienteEspecial_ID = 0;
				
				String RutCalculado = rutCompletoCalc + "-"+generarDigito(rutCompletoCalc);
				PacienteEspecial_ID = DB.getSQLValue(trx.getTrxName(), "Select Jes_Paciente_ID from Jes_Paciente where Rut = '"+RutCalculado+"' and IsManual = 'Y'");

				if (PacienteEspecial_ID > 0)
				{
					logError = "";
					pacienteSearch.setValue(PacienteEspecial_ID);					
				}
				else
				{	
					logError = logError + " - Rut No Existe DB Local";
				}
			}			
			if (logError.length()>3)
			{
				pacienteSearch.setValue(-1);
				FDialog.error(form.getWindowNo(), logError);
				return;
			}			
			trx.commit();
			trx.close();
		}
		else if (e.getTarget().equals(imprimirButton))
		{
			//imprimircita(imprimirID_Cita); //faaguilar nuevo
		}
		else if(e.getTarget().equals(rNacido))
		{
			if (rNacido.isChecked())
			{
				nameRNLabel.setVisible(true);
				nameRN.setVisible(true);
			}
		}
	}   //  actionPerformed

	
	/**
	 *  Table Model Listener.
	 *  - Recalculate Totals
	 *  @param e event
	 */
	public void tableChanged(WTableModelEvent e)
	{
		log.config("--tableChanged");
		boolean isUpdate = (e.getType() == WTableModelEvent.CONTENTS_CHANGED);
		
		if(isUpdate && e.getModel().equals(horasTable.getModel()))
		{	
			log.fine("--horasTable Changed");
			calculate();
		}
	}   //  tableChanged
	
	/**
	 *  Vetoable Change Listener.
	 *  - Business Partner
	 *  - Currency
	 * 	- Date
	 *  @param e event
	 */
	public void valueChange (ValueChangeEvent e)
	{
		String name = e.getPropertyName();
		Object value = e.getNewValue();
		log.config(name + "=" + value);
		if (value == null)
			return;
		
		if (e.getPropertyName().equals("JES_TipoAtencion_ID"))
		{
			int tipoAtencion = (Integer) e.getNewValue();
			if(tipoAtencion==1000004)
			{
				cursoLabel.setVisible(true);
				curso.setVisible(true);
				academiaLabel.setVisible(true);
				academia.setVisible(true);
				acargoLabel.setVisible(true);
				acargo.setVisible(true);
			}
			else
			{
				cursoLabel.setVisible(false);
				curso.setVisible(false);
				academiaLabel.setVisible(false);
				academia.setVisible(false);
				acargoLabel.setVisible(false);
				acargo.setVisible(false);
			}
		}
		//ininoles actualizacion de especialidad.
		if (e.getPropertyName().equals("JES_Tratante_ID"))
		{				
			int id_Tratante = (Integer) e.getNewValue();
			String sqlDefault = "select MAX(JES_Especialidad_ID) FROM JES_TratanteEspecialidad JES " +
					"WHERE  JES.JES_Tratante_ID=?";
			int espDefault = DB.getSQLValue(null, sqlDefault, id_Tratante);
			
			if(id_Tratante > 0 && espDefault > 0)
			{
				especialidadPick.setValue(espDefault);
			}
		}//end ininoles
		//ininoles nuevo codigo para refrescar al cambiar de fecha
		if (e.getPropertyName().equals(dateField.getColumnName()))
		{
			loadHoras();
			loadCitas();
		}
		
	}   //  vetoableChange
	
	
	private void loadHoras ()
	{
		log.config("-loadData horas");
		Vector<Vector<Object>> data = gethorasData(tratanteSearch.getValue(), dateField.getValue(),especialidadPick.getValue(), horasTable);
		Vector<String> columnNames = gethorasColumnNames();
		
		horasTable.clear();
		
		//  Remove previous listeners
		horasTable.getModel().removeTableModelListener(this);
		
		//  Set Model
		ListModelTable modelP = new ListModelTable(data);
		modelP.addTableModelListener(this);
		horasTable.setData(modelP, columnNames);
		sethorasColumnClass(horasTable);
		
	}   //  loadHoras
	
	private void loadCitas ()
	{
		log.config("-loadData citas");
		
		//Vector<Vector<Object>> data = getReservaData(horasTable);
		Vector<String> columnNames = getReservaColumnNames();
		
		reservasTable.clear();
		
		//  Remove previous listeners
		reservasTable.getModel().removeTableModelListener(this);
		
		//  Set Model
		//ListModelTable modelR = new ListModelTable(data);
		//modelR.addTableModelListener(this);
		//reservasTable.setData(modelR, columnNames);
		setReservaColumnClass(reservasTable);
		
	}   //  loadCitas
	
	public void inicioTablas()
	{
		log.config("-inicioTablas");
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<String> columnNames = gethorasColumnNames();
		
		horasTable.clear();
		
		//  Remove previous listeners
		horasTable.getModel().removeTableModelListener(this);
		
		//  Set Model
		ListModelTable modelP = new ListModelTable(data);
		modelP.addTableModelListener(this);
		horasTable.setData(modelP, columnNames);
		sethorasColumnClass(horasTable);
		
		data = new Vector<Vector<Object>>();
		columnNames = getReservaColumnNames();
		
		reservasTable.clear();
		
		//  Remove previous listeners
		reservasTable.getModel().removeTableModelListener(this);
		
		//  Set Model
		ListModelTable modelR = new ListModelTable(data);
		modelR.addTableModelListener(this);
		reservasTable.setData(modelR, columnNames);
		setReservaColumnClass(reservasTable);
	}
	
	public void limparReservastable()
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<String> columnNames = gethorasColumnNames();
		
		reservasTable.clear();
		
		//  Remove previous listeners
		reservasTable.getModel().removeTableModelListener(this);
		
		//  Set Model
		ListModelTable modelR = new ListModelTable(data);
		modelR.addTableModelListener(this);
		reservasTable.setData(modelR, columnNames);
		setReservaColumnClass(reservasTable);
	}
	
	public void calculate()
	{
		log.config("-calculate");
		/*int hRows = horasTable.getRowCount();
		int count = 0;
		for (int i = 0; i < hRows; i++)
		{
			//  Payment line is selected
			if (((Boolean)horasTable.getValueAt(i, 0)).booleanValue())
			{
				count++;
			}
		}
		if(count>1)
		{
			FDialog.error(form.getWindowNo(), " Solo puede Seleccionar 1 Hora");
			return;
		}*/
		
		loadCitas ();
		
	}
	
	/**************************************************************************
	 *  Save Data
	 */
	public void saveData()
	{	
		if (m_AD_Org_ID > 0)
			Env.setContext(Env.getCtx(), form.getWindowNo(), "AD_Org_ID", m_AD_Org_ID);
		else
			Env.setContext(Env.getCtx(), form.getWindowNo(), "AD_Org_ID", "");
		Trx trx = Trx.get(Trx.createTrxName("AL"), true);
		statusBar.setStatusLine(saveData(form.getWindowNo(), dateField.getValue(), horasTable, reservasTable, trx.getTrxName()));
		trx.commit();
		trx.close();
	}   //  saveData
	
	/**
	 * Called by org.adempiere.webui.panel.ADForm.openForm(int)
	 * @return
	 */
	public ADForm getForm()
	{
		return form;
	}
	
	/*private void llamarWS(String rutCompleto, Trx trx) throws Exception
	{
		
		final String request = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema/\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
				 "<soap:Body>"+
				 "<BuscarFuncionarioPorRut xmlns=\"http://tempuri.org/\">"+
				 "<rut>"+rutCompleto+"</rut>"+
		         "</BuscarFuncionarioPorRut>"+
				  "</soap:Body>"+
				 "</soap:Envelope>";

				Source response = null;
					
				try
				{				
					final CXFConnector wsc = new CXFConnector();					
					wsc.setSoapAction("http://tempuri.org/BuscarFuncionarioPorRut");
					wsc.setRequest(request);
					wsc.setBinding(SOAPBinding.SOAP11HTTP_BINDING);
					wsc.setEndpointAddress("http://10.134.1.251:8033/ServicioWebSIA.asmx");
					wsc.setServiceName("ServicioWebSIA");
					wsc.setPortName("ServicioWebSIAPortType");
					wsc.setTargetNS("urn:ServicioWebSIA");
					wsc.executeConnector();
					response = wsc.getResponse(); 
					
				}
				catch(Exception e)
				{
					throw new Exception("No se ha podido establecer conexion con el Servicio SIA");
				}
				
				
				try
				{
					//comienza la lectura del xml recibido
					DocumentBuilderFactory.newInstance().newDocumentBuilder();
					SAXSource output = (SAXSource) response;
					Transformer tf = TransformerFactory.newInstance().newTransformer();
				
					DOMResult result = new DOMResult();
					tf.transform(output, result);
					Document doc = (Document) result.getNode();
					
					Node datos = findReturnBP(doc.getChildNodes().item(0)); // aca esta todo
										
					if(datos!=null){
						NodeList att = datos.getChildNodes(); // trae los hijos del BuscarFuncionarioPorRutResult
						for(int x=0;x<att.getLength();x++)
						{
							
							log.config(att.item(x).getLocalName());
							
							if(att.item(x).getLocalName().equals("TipoEmpresa") || att.item(x).getNodeName().equals("TipoEmpresa"))
								tipoEmpresa=att.item(x).getFirstChild().getNodeValue();
							if(att.item(x).getLocalName().equals("CodContrato") || att.item(x).getNodeName().equals("CodContrato"))
								codContratoStr=att.item(x).getFirstChild().getNodeValue();
						} // fin for return
					}//FIN DATOS
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
	}
	*/
	/*private void llamarWSValidation(String rutCompleto,int tipoEmp, int p_year,int p_mounth, 
				String codigo,Trx trx) throws Exception
	{
		final String request = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema/\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
				 "<soap:Body>"+
				 "<BuscarDescuento xmlns=\"http://tempuri.org/\">"+
				 "<Rut>"+rutCompleto+"</Rut>"+
			     "<TipoEmpresa>"+tipoEmp+"</TipoEmpresa>"+
			     "<año>"+p_year+"</año>"+
			     "<mes>"+p_mounth+"</mes>"+
			     "<codigo>"+codigo+"</codigo>"+
		         "</BuscarDescuento>"+
				  "</soap:Body>"+
				 "</soap:Envelope>";
		
		Source response = null;
		
		try 
		{
			final CXFConnector wsc = new CXFConnector();					
			wsc.setSoapAction("http://tempuri.org/BuscarDescuento");
			wsc.setRequest(request);
			wsc.setBinding(SOAPBinding.SOAP11HTTP_BINDING);
			wsc.setEndpointAddress("http://10.134.1.251:8033/ServicioWebSIA.asmx");
			wsc.setServiceName("ServicioWebSIA");
			wsc.setPortName("ServicioWebSIAPortType");
			wsc.setTargetNS("urn:ServicioWebSIA");
			wsc.executeConnector();
			response = wsc.getResponse(); 
			
		}
		catch(Exception e)
		{
			throw new Exception("No se ha podido establecer conexion con el Servicio SIA");
		}
		
		try
		{
			//comienza la lectura del xml recibido
			String montoStr = null;			
			
			DocumentBuilderFactory.newInstance().newDocumentBuilder();
			SAXSource output = (SAXSource) response;
			Transformer tf = TransformerFactory.newInstance().newTransformer();
		
			DOMResult result = new DOMResult();
			tf.transform(output, result);
			Document doc = (Document) result.getNode();
			
			Node datos = findReturn(doc.getChildNodes().item(0)); // aca esta todo
			log.config("Antes de if(datos!=null) ");
								
			if(datos!=null)
			{
				NodeList att = datos.getChildNodes(); // trae los hijos del BuscarDescuento
				for(int x=0;x<att.getLength();x++)
				{					
					log.config(att.item(x).getLocalName());
					if(att.item(x).getLocalName().equals("Monto") || att.item(x).getNodeName().equals("Monto"))
						montoStr = att.item(x).getFirstChild().getNodeValue();
				}				
				log.config("Antes de montoStr.length()>0. variable monto="+montoStr);
				if (montoStr.length()>0)
				{
					try
					{
						monto = Integer.parseInt(montoStr);						
					}
					catch(Exception ee)
					{
						ee.printStackTrace();
					}
				}				
			}//FIN DATOS
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	*/
	
	/*
	private void imprimircita(int cita_ID)
	{
		try{
			
			//int AD_PrintFormat_ID = 1000040;// versiones antes de 01-08-2016
			int AD_PrintFormat_ID = 1000124;
			int copies = 1;
			MPrintFormat format = MPrintFormat.get (Env.getCtx(), AD_PrintFormat_ID, false);
			MQuery query = new MQuery("rvjf_tickethora");
			query.addRestriction("JES_Cita_ID", MQuery.EQUAL,cita_ID);
			PrintInfo info = new PrintInfo(
					"Confirmacion Reserva",
					1000128,
					cita_ID,
					1000000);
				info.setCopies(copies);
				info.setDocumentCopy(false);		//	true prints "Copy" on second
				info.setPrinterName(format.getPrinterName());
			ReportEngine re = new ReportEngine(Env.getCtx(), format, query, info, null);
			//re.print(); se saca impresion directa, ya que se hacia en el servidor.						
			ReportViewerProvider provider = ReportCtl.getReportViewerProvider();			
			provider.openViewer(re);			
		}
		catch (Exception e)
		{
			FDialog.error(form.getWindowNo(), "No se Encuetra bien definido el formato de impresion");
		}
	}
	*/
	//Validar RUT
    private String generarDigito(String rut)
    {
    	String digito ="";
        try
        {
    
            if(!"".equals(rut))
            {
                if(rut.length() >= 7)
                {
                    String codigo;
                    int multiplo = 2;
                    int cont = 0;

                    for(int x = 0;x < rut.length();x++)
                    {
                        cont = cont+(Integer.parseInt(rut.substring(rut.length()-x-1, rut.length()-x))*multiplo);
                        multiplo++;
                        if(multiplo == 8)
                        {
                            multiplo = 2;
                        }
                    }

                    cont=11-(cont%11);

                    if(cont <= 9)
                    {
                        codigo = ""+cont;                
                    }
                    else if(cont == 11)
                    {
                        codigo = "0";
                    }else
                    {
                        codigo = "K";
                    }

                    if(codigo != null)
                    {
                       digito = codigo; 
                    }                    
                }
                else
                {
                	log.config("EL RUT DEBE CONTENER A LO MENOS 7 DIGITOS");
                }
            }else
            {
            	log.config("EL RUT NO DEBE SER NULO");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return digito;
    }  
	
	
	 
    public Node findReturnBP(Node node) {
			Node value = null;
			NodeList list = node.getChildNodes();
			for (int i=0; i<list.getLength(); i++) {
				// Get child node
				Node childNode = list.item(i);
				if(childNode.getNodeName().equals(("BuscarFuncionarioPorRutResult")))
				{
					value = childNode;
					break;
				}

				value=findReturnBP(childNode);

			}
			return value;
	}
	 
	public Node findReturn(Node node) {
			Node value = null;
			NodeList list = node.getChildNodes();
			for (int i=0; i<list.getLength(); i++) {
				// Get child node
				Node childNode = list.item(i);
				if(childNode.getNodeName().equals(("BuscarDescuentoResult")))
				{
					value = childNode;
					break;
				}

				value=findReturn(childNode);

			}
			return value;
	}
	
	public Node findReturnCarga(Node node) {
		Node value = null;
		NodeList list = node.getChildNodes();
		for (int i=0; i<list.getLength(); i++) {
			// Get child node
			Node childNode = list.item(i);
			log.config("Dentro del findreturn: "+childNode.getNodeName());
			if(childNode.getNodeName().equals("BuscarDescuentoCargaResult"))
			{
				log.config("Dentro de if antes de break: "+childNode.getNodeName());
				value = childNode;
				break;				
			}
			
			log.config("Asignacion de value: "+childNode.getNodeName());
			value=findReturnCarga(childNode);
		}
		log.config("Return devuelto: "+value);
		return value;
}

	@Override
	public void lockUI(ProcessInfo pi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockUI(ProcessInfo pi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUILocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void executeASync(ProcessInfo pi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vetoableChange(PropertyChangeEvent arg0)
			throws PropertyVetoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(int WindowNo, FormFrame frame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}   //  VAllocation
