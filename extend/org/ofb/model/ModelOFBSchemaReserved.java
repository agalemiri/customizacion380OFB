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
package org.ofb.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.I_M_InOutLineMA;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInOutLineMA;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_M_StorageReservation;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Validator default OFB
 *
 *  @author Italo Niñoles
 */
public class ModelOFBSchemaReserved implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelOFBSchemaReserved ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelOFBSchemaReserved.class);
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
			log.info("Initializing global validator: "+this.toString());
		}

		//	Tables to be monitored		
		//	Documents to be monitored
		engine.addDocValidate(MOrder.Table_Name, this);
		engine.addDocValidate(MInOut.Table_Name, this);
		

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public static final String DOCSTATUS_Drafted = "DR";
	public static final String DOCSTATUS_Completed = "CO";
	public static final String DOCSTATUS_InProgress = "IP";
	public static final String DOCSTATUS_Voided = "VO";
	
	
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
					
		return null;
	}	//	modelChange

	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt
     *	when you called addDocValidate for the table.
     *	Note that totals, etc. may not be correct.
	 *	@param po persistent object
	 *	@param timing see TIMING_ constants
     *	@return error message or null
	 */
	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		//if(timing == TIMING_BEFORE_PREPARE && po.get_Table_ID()==MOrder.Table_ID)
		//{
		//	MOrder order = (MOrder) po;
			/*MOrderLine[] lines = order.getLines();
			for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
			{
				MOrderLine oLine = lines[lineIndex];
				
				BigDecimal target = oLine.getQtyOrdered(); 
				BigDecimal difference = target
					.subtract(oLine.getQtyReserved())
					.subtract(oLine.getQtyDelivered()); 				
				BigDecimal ordered = order.isSOTrx()? Env.ZERO : difference;
				BigDecimal reserved = order.isSOTrx() ? difference : Env.ZERO;
				
				if(existReservationTable(po))
					OFBReservation(oLine.getM_Warehouse_ID(),oLine.getM_Product_ID(),ordered,reserved, po);
			}*/
		//	MOrderLine[] lines = order.getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		//	reserveStock(new MDocType(po.getCtx(),order.getC_DocTypeTarget_ID(),po.get_TrxName()), lines, po, order);
		//}
		if(timing == TIMING_BEFORE_COMPLETE && po.get_Table_ID()==MOrder.Table_ID)
		{
			MOrder order = (MOrder) po;			
			MOrderLine[] lines = order.getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
			//reserveStock(new MDocType(po.getCtx(),order.getC_DocTypeTarget_ID(),po.get_TrxName()), lines, po, order);
			boolean isSOTrx = order.isSOTrx();
			for (int i = 0; i < lines.length; i++)
			{
				MOrderLine line = lines[i];
				//	Binding
				BigDecimal difference = line.getQtyOrdered().subtract(line.getQtyDelivered());
				//	Check Product - Stocked and Item
				MProduct product = line.getProduct();
				if (product != null) 
				{
					if (product.isStocked())
					{
						BigDecimal ordered = isSOTrx ? Env.ZERO : difference;
						BigDecimal reserved = isSOTrx ? difference : Env.ZERO;					
						
						if(existReservationTable(po))//faaguilar OFB
							OFBReservation(line.getM_Warehouse_ID(),line.getM_Product_ID(),ordered,reserved, po);//faaguilar OFB
					}	//	stockec
				}	//	product
			}	//	reverse inventory
		}
		if(timing == TIMING_BEFORE_REACTIVATE && po.get_Table_ID()==MOrder.Table_ID)
		{
			MOrder order = (MOrder) po;			
			MOrderLine[] lines = order.getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
			//reserveStock(new MDocType(po.getCtx(),order.getC_DocTypeTarget_ID(),po.get_TrxName()), lines, po, order);
			boolean isSOTrx = order.isSOTrx();
			for (int i = 0; i < lines.length; i++)
			{
				MOrderLine line = lines[i];
				//	Binding
				BigDecimal difference = Env.ZERO
						.subtract(line.getQtyReserved());
				//	Check Product - Stocked and Item
				MProduct product = line.getProduct();
				if (product != null) 
				{
					if (product.isStocked())
					{
						BigDecimal ordered = isSOTrx ? Env.ZERO : difference;
						BigDecimal reserved = isSOTrx ? difference : Env.ZERO;					
						
						if(existReservationTable(po))//faaguilar OFB
							OFBReservation(line.getM_Warehouse_ID(),line.getM_Product_ID(),ordered,reserved, po);//faaguilar OFB
					}	//	stockec
				}	//	product
			}	//	reverse inventory
		}
		if(timing == TIMING_BEFORE_VOID && po.get_Table_ID()==MOrder.Table_ID)
		{
			MOrder order = (MOrder) po;
			int cant = DB.getSQLValue(po.get_TrxName(), "SELECT COUNT(1) FROM M_InOut WHERE IsActive = 'Y'" +
					" AND DocStatus IN ('CO','CL') AND C_Order_ID = "+order.get_ID());
			if(cant > 0 )
			{
				if(order.isSOTrx())
					return "ERROR: No es posible anular nota de venta. Debe anular despacho primero";
				else
					return "ERROR: No es posible anular OC. Debe anular recibo primero";
			}
			MOrderLine[] lines = order.getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
			//reserveStock(new MDocType(po.getCtx(),order.getC_DocTypeTarget_ID(),po.get_TrxName()), lines, po, order);
			boolean isSOTrx = order.isSOTrx();
			for (int i = 0; i < lines.length; i++)
			{
				MOrderLine line = lines[i];
				//	Binding
				BigDecimal difference = Env.ZERO
							.subtract(line.getQtyReserved());
				//	Check Product - Stocked and Item
				MProduct product = line.getProduct();
				if (product != null) 
				{
					if (product.isStocked())
					{
						BigDecimal ordered = isSOTrx ? Env.ZERO : difference;
						BigDecimal reserved = isSOTrx ? difference : Env.ZERO;					
						
						if(existReservationTable(po))//faaguilar OFB
							OFBReservation(line.getM_Warehouse_ID(),line.getM_Product_ID(),ordered,reserved, po);//faaguilar OFB
					}	//	stockec
				}	//	product
			}	//	reverse inventory
		}
		if(timing == TIMING_BEFORE_CLOSE && po.get_Table_ID()==MOrder.Table_ID)
		{
			MOrder order = (MOrder) po;			
			MOrderLine[] lines = order.getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
			//reserveStock(new MDocType(po.getCtx(),order.getC_DocTypeTarget_ID(),po.get_TrxName()), lines, po, order);
			boolean isSOTrx = order.isSOTrx();
			for (int i = 0; i < lines.length; i++)
			{
				MOrderLine line = lines[i];
				//	Binding
				BigDecimal difference = Env.ZERO
							.subtract(line.getQtyOrdered().subtract(line.getQtyDelivered()));
				//	Check Product - Stocked and Item
				MProduct product = line.getProduct();
				if (product != null) 
				{
					if (product.isStocked())
					{
						BigDecimal ordered = isSOTrx ? Env.ZERO : difference;
						BigDecimal reserved = isSOTrx ? difference : Env.ZERO;					
						
						if(existReservationTable(po))//faaguilar OFB
							OFBReservation(line.getM_Warehouse_ID(),line.getM_Product_ID(),ordered,reserved, po);//faaguilar OFB
					}	//	stockec
				}	//	product
			}	//	reverse inventory
		}
		if(timing == TIMING_AFTER_COMPLETE && po.get_Table_ID()==MInOut.Table_ID)
		{
			MInOut ship = (MInOut) po;
			//solo se desreserva si no proviene de un RMA
			if(ship.getM_RMA_ID() <= 0)
			{
				MInOutLine[] lines = ship.getLines(false);
				for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
				{
					MInOutLine sLine = lines[lineIndex];
					MProduct product = sLine.getProduct();				
					String MovementType = ship.getMovementType();
					BigDecimal Qty = sLine.getMovementQty();
					if (MovementType.charAt(1) == '-')	//	C- Customer Shipment - V- Vendor Return
						Qty = Qty.negate();
					BigDecimal QtySO = Env.ZERO;
					BigDecimal QtyPO = Env.ZERO;
	//				Update Order Line
					MOrderLine oLine = null;
					if (sLine.getC_OrderLine_ID() != 0)
					{
						oLine = new MOrderLine (po.getCtx(), sLine.getC_OrderLine_ID(), po.get_TrxName());
						log.fine("OrderLine - Reserved=" + oLine.getQtyReserved()
							+ ", Delivered=" + oLine.getQtyDelivered());
						if (ship.isSOTrx())
							QtySO = sLine.getMovementQty();
						else
							QtyPO = sLine.getMovementQty();
					}
					if (product != null
							&& product.isStocked() )
					{	
						//
						boolean sameWarehouse = true;
						if (oLine != null) {						
							sameWarehouse = oLine.getM_Warehouse_ID()==ship.getM_Warehouse_ID();
						}
						if (sLine.getM_AttributeSetInstance_ID() == 0)
						{
							MInOutLineMA mas[] = get(sLine.getM_InOutLine_ID(), po);
							for (int j = 0; j < mas.length; j++)
							{
								MInOutLineMA ma = mas[j];
								BigDecimal QtyMA = ma.getMovementQty();
								if (MovementType.charAt(1) == '-')	//	C- Customer Shipment - V- Vendor Return
									QtyMA = QtyMA.negate();
								BigDecimal reservedDiff = Env.ZERO;
								BigDecimal orderedDiff = Env.ZERO;
								if (sLine.getC_OrderLine_ID() != 0)
								{
									if (ship.isSOTrx())
										reservedDiff = ma.getMovementQty().negate();
									else
										orderedDiff = ma.getMovementQty().negate();
								}
								//faaguilar OFB fix retorno desde clientes begin
								if(ship.getMovementType().equals(MInOut.MOVEMENTTYPE_CustomerReturns))
									reservedDiff=Env.ZERO;
								
								if(!ship.isSOTrx())
									if(QtyPO.compareTo(orderedDiff)<0)//recibido mayor que comprado
											orderedDiff = QtyPO;
								//faaguilar OFB fix retorno desde clientes end
	
								if(existReservationTable(po))//faaguilar OFB
									OFBReservation(oLine.getM_Warehouse_ID(),sLine.getM_Product_ID(),orderedDiff,reservedDiff,po);//faaguilar OFB
								
								//	Update Storage - see also VMatch.createMatchRecord
							}
						}
						else
						{
							BigDecimal reservedDiff = sameWarehouse ? QtySO.negate() : Env.ZERO;
							BigDecimal orderedDiff = sameWarehouse ? QtyPO.negate(): Env.ZERO;
	
							if(existReservationTable(po) && oLine != null)//faaguilar OFB
								OFBReservation(oLine.getM_Warehouse_ID(),sLine.getM_Product_ID(),orderedDiff,reservedDiff, po);//faaguilar OFB
						}
					}
				}
			}	
		}
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
		StringBuffer sb = new StringBuffer ("QSS_Validator");
		return sb.toString ();
	}	//	toString
	public boolean existReservationTable(PO po)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean val = false;
		
		String mysql="SELECT count(1) from M_StorageReservation";
		
		if(!DB.isOracle())
			mysql = "select count(1) from AD_Table where tablename='M_StorageReservation'";
		try
		{
			pstmt = DB.prepareStatement(mysql,po.get_TrxName());
			rs = pstmt.executeQuery();
			if (rs.next())
				if(rs.getInt(1)>0)
					val = true;
			
		}
		catch (Exception e)
		{
			
			val = false;
		}
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return val;
	}
	public void OFBReservation(int M_WareHouse_ID, int M_Product_ID, BigDecimal ordered, BigDecimal reserved, PO po)
	{
		PreparedStatement pstmt = null;
		
		String mysql="SELECT * from M_StorageReservation where M_WareHouse_ID = ? and M_Product_ID = ?";
		try
		{
			pstmt = DB.prepareStatement(mysql, po.get_TrxName());
			pstmt.setInt(1, M_WareHouse_ID);
			pstmt.setInt(2, M_Product_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				X_M_StorageReservation storage = new X_M_StorageReservation (po.getCtx(),rs,po.get_TrxName() );
					storage.setQtyReserved(storage.getQtyReserved().add(reserved));
					storage.setQtyOrdered(storage.getQtyOrdered().add(ordered));
					
					if(storage.getQtyOrdered().signum()<0)
						storage.setQtyOrdered(Env.ZERO);
					if(storage.getQtyReserved().signum()<0)
						storage.setQtyReserved(Env.ZERO);						
					storage.save();
			}
			else{
				X_M_StorageReservation storage = new X_M_StorageReservation (po.getCtx(),0,po.get_TrxName() );
				storage.setQtyReserved(reserved);
				storage.setQtyOrdered(ordered);
				storage.setM_Product_ID(M_Product_ID);
				storage.setM_Warehouse_ID(M_WareHouse_ID);
				storage.save();
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		//	get
	}	
	public MInOutLineMA[] get (int M_InOutLine_ID, PO po)
	{
		Query query = MTable.get(po.getCtx(), MInOutLineMA.Table_Name)
							.createQuery(I_M_InOutLineMA.COLUMNNAME_M_InOutLine_ID+"=?", po.get_TrxName());
		query.setParameters(M_InOutLine_ID);
		List<MInOutLineMA> list = query.list();
		MInOutLineMA[] retValue = new MInOutLineMA[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	
	private boolean reserveStock (MDocType dt, MOrderLine[] lines, PO po, MOrder order)
	{
		if (dt == null)
			dt = MDocType.get(po.getCtx(), order.getC_DocType_ID());
		
		if(MDocType.DOCSUBTYPESO_ReturnMaterial.equals(dt.getDocSubTypeSO()))//faaguilar OFB customer return no reservan
			return true; //faaguilar OFB customer return no reservan
		
		//	Binding
		boolean binding = !dt.isProposal();
		//	Not binding - i.e. Target=0
		if (MOrder.DOCACTION_Void.equals(order.getDocAction())
			//	Closing Binding Quotation
			|| (MDocType.DOCSUBTYPESO_Quotation.equals(dt.getDocSubTypeSO()) 
				&& MOrder.DOCACTION_Close.equals(order.getDocAction())) 
			) // || isDropShip() )
			binding = false;
		boolean isSOTrx = order.isSOTrx();
		//	Always check and (un) Reserve Inventory		
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			//	Binding
			BigDecimal target = binding ? line.getQtyOrdered() : Env.ZERO; 
			BigDecimal difference = target
				//.subtract(line.getQtyReserved())
				.subtract(line.getQtyDelivered());
			if(MOrder.DOCACTION_Re_Activate.equals(order.getDocAction()))
					difference = difference.negate();
			//	Check Product - Stocked and Item
			MProduct product = line.getProduct();
			if (product != null) 
			{
				if (product.isStocked())
				{
					BigDecimal ordered = isSOTrx ? Env.ZERO : difference;
					BigDecimal reserved = isSOTrx ? difference : Env.ZERO;					
					
					if(existReservationTable(po))//faaguilar OFB
						OFBReservation(line.getM_Warehouse_ID(),line.getM_Product_ID(),ordered,reserved, po);//faaguilar OFB
				}	//	stockec
			}	//	product
		}	//	reverse inventory
		return true;
	}	//	reserveStock
}	