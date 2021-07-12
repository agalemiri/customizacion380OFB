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
package org.copesa.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MUser;
import org.compiere.model.X_I_OrderMasive;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Import Order from I_Order
 *  @author Oscar Gomez
 * 			<li>BF [ 2936629 ] Error when creating bpartner in the importation order
 * 			<li>https://sourceforge.net/tracker/?func=detail&aid=2936629&group_id=176962&atid=879332
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ImportOrder.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ImportOrder extends SvrProcess
{
	/**	Client to be imported to		*/
	private int				m_AD_Client_ID = 0;
	/**	Organization to be imported to		*/
	private int				m_AD_Org_ID = 0;
	/**	Delete old Imported				*/
	private boolean			m_deleteOldImported = false;
	/**	Document Action					*/
	private String			m_docAction = MOrder.DOCACTION_Prepare;


	/** Effective						*/
	private Timestamp		m_DateValue = null;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Client_ID"))
				m_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("AD_Org_ID"))
				m_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DeleteOldImported"))
				m_deleteOldImported = "Y".equals(para[i].getParameter());
			else if (name.equals("DocAction"))
				m_docAction = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		if (m_DateValue == null)
			m_DateValue = new Timestamp (System.currentTimeMillis());
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;

		//	****	Prepare	****

		//	Delete Old Imported
		if (m_deleteOldImported)
		{
			sql = new StringBuffer ("DELETE I_OrderMasive "
				  + "WHERE I_IsImported='Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.fine("Delete Old Impored =" + no);
		}

		//	Set Client, Org, IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_OrderMasive "
			  + "SET AD_Client_ID = COALESCE (AD_Client_ID,").append (m_AD_Client_ID).append ("),"
			  + " AD_Org_ID = COALESCE (AD_Org_ID,").append (m_AD_Org_ID).append ("),"
			  + " IsActive = COALESCE (IsActive, 'Y'),"
			  + " Created = COALESCE (Created, SysDate),"
			  + " CreatedBy = COALESCE (CreatedBy, 0),"
			  + " Updated = COALESCE (Updated, SysDate),"
			  + " UpdatedBy = COALESCE (UpdatedBy, 0),"
			  + " I_ErrorMsg = ' ',"
			  + " I_IsImported = 'N' "
			  + "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info ("Reset=" + no);

		sql = new StringBuffer ("UPDATE I_OrderMasive o "
			+ "SET I_IsImported='N', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Org, '"
			+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0"
			+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Org=" + no);

		//	Price List x Name
		sql = new StringBuffer ("UPDATE I_OrderMasive o SET M_PriceList_ID = " +
				" (SELECT MAX(M_PriceList_ID) FROM M_PriceList p WHERE name LIKE 'Lista Precio Base' AND p.IsSOPriceList='Y' AND o.AD_Client_ID=p.AD_Client_ID)" +
				" WHERE M_PriceList_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck); 
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set Default Currency PriceList=" + no);
		
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
			  + "SET M_PriceList_ID=(SELECT MAX(M_PriceList_ID) FROM M_PriceList p WHERE p.IsDefault='Y'"
			  + " AND p.IsSOPriceList='Y' AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_PriceList_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());		
		log.fine("Set Currency PriceList=" + no);
		
		//
		sql = new StringBuffer ("UPDATE I_OrderMasive "
			  + "SET I_IsImported='N', I_ErrorMsg=I_ErrorMsg||'ERR=No PriceList, ' "
			  + "WHERE M_PriceList_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No PriceList=" + no);
				
		//	Payment Term
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
			  + "SET C_PaymentTerm_ID=(SELECT C_PaymentTerm_ID FROM C_PaymentTerm p"
			  + " WHERE o.C_PaymentTermName =p.Value AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE C_PaymentTerm_ID IS NULL AND C_PaymentTermName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set PaymentTerm=" + no);
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
			  + "SET C_PaymentTerm_ID=(SELECT MAX(C_PaymentTerm_ID) FROM C_PaymentTerm p"
			  + " WHERE p.IsDefault='Y' AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE C_PaymentTerm_ID IS NULL AND o.C_PaymentTermName IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set Default PaymentTerm=" + no);
		//
		sql = new StringBuffer ("UPDATE I_OrderMasive "
			  + "SET I_IsImported='N', I_ErrorMsg=I_ErrorMsg||'ERR=No PaymentTerm, ' "
			  + "WHERE C_PaymentTerm_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("No PaymentTerm=" + no);

		//	se
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
			  + "SET M_Warehouse_ID=(SELECT MAX(M_Warehouse_ID) FROM M_Warehouse w"
			  + " WHERE o.AD_Client_ID=w.AD_Client_ID AND  w.name = o.M_WareHouseName) "
			  + "WHERE M_Warehouse_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());	//	Warehouse for Org
		if (no != 0)
			log.fine("Set Warehouse=" + no);
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
			  + "SET M_Warehouse_ID=(SELECT M_Warehouse_ID FROM M_Warehouse w"
			  + " WHERE o.AD_Client_ID=w.AD_Client_ID) "
			  + "WHERE M_Warehouse_ID IS NULL"
			  + " AND EXISTS (SELECT AD_Client_ID FROM M_Warehouse w WHERE w.AD_Client_ID=o.AD_Client_ID GROUP BY AD_Client_ID HAVING COUNT(*)=1)"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.fine("Set Only Client Warehouse=" + no);
		//
		sql = new StringBuffer ("UPDATE I_OrderMasive "
			  + "SET I_IsImported='N', I_ErrorMsg=I_ErrorMsg||'ERR=No Warehouse, ' "
			  + "WHERE M_Warehouse_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("No Warehouse=" + no);
		//locator
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
				  + "SET M_Locator_ID=(SELECT MAX(M_Locator_ID) FROM M_Locator w"
				  + " WHERE o.AD_Client_ID=w.AD_Client_ID AND  w.Value = o.M_LocatorName) "
				  + "WHERE M_Locator_ID IS NULL AND M_LocatorName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());	//	Warehouse for Org
			if (no != 0)
				log.fine("Set Warehouse=" + no);
		
		//	BP from Value
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
			  + "SET C_BPartner_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
			  + " WHERE o.Value=bp.Value AND o.AD_Client_ID=bp.AD_Client_ID) "
			  + "WHERE C_BPartner_ID IS NULL AND Value IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set BP from Value=" + no);
		
		//ubicacion sera tratada de forma especial para validar campos nativos de copesa
		
		//	Product
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
			  + "SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p"
			  + " WHERE o.M_product_Name=p.Name AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_Product_ID IS NULL AND M_product_Name IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set Product from Value=" + no);
		
		sql = new StringBuffer ("UPDATE I_OrderMasive "
			  + "SET I_IsImported='N', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Product, ' "
			  + "WHERE M_Product_ID IS NULL AND (M_product_Name IS NOT NULL)"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Product=" + no);
		//	Tax
		/*sql = new StringBuffer ("UPDATE I_OrderMasive o "
			  + "SET C_Tax_ID=(SELECT MAX(C_Tax_ID) FROM C_Tax t"
			  + " WHERE 'IVA 19%' = t.name AND o.AD_Client_ID=t.AD_Client_ID) "
			  + " WHERE C_Tax_ID IS NULL "
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set Tax=" + no);
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Tax, ' "
			  + "WHERE C_Tax_ID IS NULL AND TaxIndicator IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Tax=" + no);*/
		
		//channel
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
				  + "SET C_Channel_ID=(SELECT MAX(C_Channel_ID) FROM C_Channel t"
				  + " WHERE o.C_ChannelName = t.name AND o.AD_Client_ID=t.AD_Client_ID) "
				  + "WHERE C_Channel_ID IS NULL AND C_ChannelName IS NOT NULL"
				  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set channel=" + no);		
		
		//campos de direcci�n
		//ciudad
		sql = new StringBuffer ("UPDATE I_OrderMasive o " +
				" SET C_City_ID=(SELECT MAX(C_City_ID) FROM C_City t " +
				" WHERE o.C_CityName = t.name)" +
				" WHERE C_City_ID IS NULL AND C_CityName IS NOT NULL " +
				" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set City=" + no);		
		
		//provincia
		sql = new StringBuffer ("UPDATE I_OrderMasive o " +
				" SET C_Province_ID=(SELECT MAX(C_Province_ID) FROM C_Province t " +
				" WHERE o.C_ProvinceName = t.name )" +
				" WHERE C_Province_ID IS NULL AND C_ProvinceName IS NOT NULL " +
				" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set province=" + no);
		
		//region
		sql = new StringBuffer ("UPDATE I_OrderMasive o " +
				" SET C_Region_ID=(SELECT MAX(C_Region_ID) FROM C_Region t " +
				" WHERE o.C_RegionName = t.name )" +
				" WHERE C_Region_ID IS NULL AND C_RegionName IS NOT NULL " +
				" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set region=" + no);

		//street
		sql = new StringBuffer ("UPDATE I_OrderMasive o " +
				" SET C_Street_ID=(SELECT MAX(C_Street_ID) FROM C_Street t " +
				" WHERE o.C_StreetName = t.name)" +
				" WHERE C_Street_ID IS NULL AND C_StreetName IS NOT NULL " +
				" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set region=" + no);
		
		//bl line
		sql = new StringBuffer ("UPDATE I_OrderMasive o "
			  + "SET C_BPartnerLine_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
			  + " WHERE o.ValueL=bp.Value AND o.AD_Client_ID=bp.AD_Client_ID) "
			  + "WHERE C_BPartnerLine_ID IS NULL AND ValueL IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set BP from Value=" + no);
		
		//campos de direcci�n linea
		//ciudad
		sql = new StringBuffer ("UPDATE I_OrderMasive o " +
				" SET C_CityL_ID=(SELECT MAX(C_City_ID) FROM C_City t " +
				" WHERE o.C_CityNameL = t.name )" +
				" WHERE C_CityL_ID IS NULL AND C_CityNameL IS NOT NULL " +
				" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set CityL=" + no);		
		
		//provincia
		sql = new StringBuffer ("UPDATE I_OrderMasive o " +
				" SET C_ProvinceL_ID=(SELECT MAX(C_Province_ID) FROM C_Province t " +
				" WHERE o.C_ProvinceNameL = t.name)" +
				" WHERE C_ProvinceL_ID IS NULL AND C_ProvinceNameL IS NOT NULL " +
				" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set provinceL=" + no);
		
		//region
		sql = new StringBuffer ("UPDATE I_OrderMasive o " +
				" SET C_RegionL_ID=(SELECT MAX(C_Region_ID) FROM C_Region t " +
				" WHERE o.C_RegionNameL = t.name)" +
				" WHERE C_RegionL_ID IS NULL AND C_RegionNameL IS NOT NULL " +
				" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set regionL=" + no);

		//street
		sql = new StringBuffer ("UPDATE I_OrderMasive o " +
				" SET C_StreetL_ID=(SELECT MAX(C_Street_ID) FROM C_Street t " +
				" WHERE o.C_StreetNameL = t.name)" +
				" WHERE C_StreetL_ID IS NULL AND C_StreetNameL IS NOT NULL " +
				" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set streetL=" + no);
		
		//copesa calendar
		sql = new StringBuffer ("UPDATE I_OrderMasive o " +
				" SET C_CalendarCOPESA_ID=(SELECT MAX(C_CalendarCOPESA_ID) FROM C_CalendarCOPESA t " +
				" WHERE o.C_CalendarCOPESAName = t.name AND o.AD_Client_ID=t.AD_Client_ID)" +
				" WHERE C_CalendarCOPESA_ID IS NULL AND C_CalendarCOPESAName IS NOT NULL " +
				" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set copesa calendar=" + no);
		
		commitEx();
		
		//	-- New BPartner Head ---------------------------------------------

		//	Go through Order Records w/o C_BPartner_ID
		sql = new StringBuffer ("SELECT * FROM I_OrderMasive "
			  + "WHERE I_IsImported='N' AND (C_BPartner_ID IS NULL OR C_BPartner_Location_ID IS NULL)").append (clientCheck);
		try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				X_I_OrderMasive imp = new X_I_OrderMasive (getCtx (), rs, get_TrxName());
				//si no trae rut no se crea BP
				if (imp.getValue() == null)
					continue;
				//	crear nuevo BPartner
				MBPartner bp = MBPartner.get (getCtx(), imp.getValue());
				if (bp == null)
				{
					bp = new MBPartner (getCtx (), -1, get_TrxName());
					bp.setClientOrg (imp.getAD_Client_ID (), imp.getAD_Org_ID ());
					bp.setValue (imp.getValue ());
					bp.setName((imp.getName1()+" "+imp.getName5()+" "+imp.getName2()+" "+imp.getName3()+" "+imp.getName4()).trim());
					if(imp.getName1() != null)
						bp.set_CustomColumn("Name1", imp.getName1());
					if(imp.getName2() != null)
						bp.set_CustomColumn("Name2", imp.getName2());
					if(imp.getName3() != null)
						bp.set_CustomColumn("Name3", imp.getName3());
					if(imp.getName4() != null)
						bp.set_CustomColumn("Name4", imp.getName4());
					if(imp.getName5() != null)
						bp.set_CustomColumn("Name5", imp.getName5());
					if(imp.getDigito() != null)
						bp.set_CustomColumn("Digito", imp.getDigito());
					//tipo
					if(imp.getPartnerType() != null)
					{
						if(getValueRef(2000001, imp.getPartnerType()) != null)
							bp.set_CustomColumn("PartnerType", getValueRef(2000001, imp.getPartnerType()));
					}
					if(imp.getVIPClient() != null)
					{
						if(getValueRef(2000002, imp.getVIPClient()) != null)
							bp.set_CustomColumn("VIPClient", getValueRef(2000002, imp.getVIPClient()));
					}
					if(imp.getBirthday() != null)
						bp.set_CustomColumn("Birthday", imp.getBirthday());
					if(imp.getMaritalStatus() != null)
					{
						if(getValueRef(53614, imp.getMaritalStatus()) != null)
							bp.set_CustomColumn("MaritalStatus", getValueRef(53614, imp.getMaritalStatus()));
					}
					if(imp.getChildren() > 0)
						bp.set_CustomColumn("Children", imp.getChildren());
					if(imp.getTwitter() != null)
						bp.set_CustomColumn("Twitter", imp.getTwitter());
					if(imp.getFacebook() != null)
						bp.set_CustomColumn("Facebook", imp.getFacebook());
					if(imp.getGender() != null)
					{
						if(getValueRef(53612, imp.getGender()) != null)
							bp.set_CustomColumn("Gender", getValueRef(53612, imp.getGender()));
					}
					if (!bp.save())
						continue;
				}
				imp.setC_BPartner_ID (bp.getC_BPartner_ID ());
				imp.save();
				//creacion de direcciones
//				BP Location
				MBPartnerLocation bpl = null; 
				if(!existLocator(imp, imp.getC_BPartner_ID()))
				{
					bpl = new MBPartnerLocation (bp);
					bpl.setAD_Org_ID(bp.getAD_Org_ID());
					bpl.setC_Location_ID (2000011);
					if(imp.getHomeType() != null)
					{	
						if(getValueRef(2000003, imp.getHomeType()) != null)
							bpl.set_CustomColumn("HomeType", getValueRef(2000003, imp.getHomeType()));
					}
					if(imp.getAddressType() != null)
					{	
						if(getValueRef(2000004, imp.getAddressType()) != null)
							bpl.set_CustomColumn("AddressType", getValueRef(2000004, imp.getAddressType()));
					}
					//seteamos campos ID
					if(imp.getC_City_ID() > 0)
						bpl.set_CustomColumn("C_City_ID", imp.getC_City_ID());
					if(imp.getC_Province_ID() > 0)
						bpl.set_CustomColumn("C_Province_ID", imp.getC_Province_ID());
					if(imp.getC_Region_ID() > 0)
						bpl.set_CustomColumn("C_Region_ID", imp.getC_Region_ID());
					if(imp.getC_Street_ID() > 0)
						bpl.set_CustomColumn("C_Street_ID", imp.getC_Street_ID());
					//
					if(imp.getAddressNumber().compareTo(Env.ZERO) > 0)
						bpl.set_CustomColumn("AddressNumber", imp.getAddressNumber().toString());
					if(imp.getHomeNumber().compareTo(Env.ZERO) > 0)
						bpl.set_CustomColumn("HomeNumber", Integer.toString(imp.getHomeNumber().intValue()));
					if(imp.getBlock() != null)
						bpl.set_CustomColumn("Block", imp.getBlock());
					if(imp.getVilla() != null)
						bpl.set_CustomColumn("Villa", imp.getVilla());
					if(imp.getStreetName() != null)
						bpl.set_CustomColumn("StreetName", imp.getStreetName());
					if(imp.getZipCode() != null)
						bpl.set_CustomColumn("ZipCode", imp.getZipCode().toString());
					if(imp.getZone() != null)
						bpl.set_CustomColumn("Zone", imp.getZone().toString());
					if(imp.getSector() != null)
						bpl.set_CustomColumn("Sector", imp.getSector().toString());
					if(imp.getLocator_Description() != null)
						bpl.set_CustomColumn("Description", imp.getLocator_Description());
					if (!bpl.save ())
						continue;
				}
				else
				{
					bpl = new MBPartnerLocation(getCtx(), getLocator(imp, imp.getC_BPartner_ID()), get_TrxName());
				}
				if(bpl.get_ID() > 0)
				{
					imp.set_CustomColumn("C_BPartner_Location_ID",bpl.get_ID());
					imp.save();
				}
				//creacion de contactos
				if(!existContact(imp.getDescription(), imp.getC_BPartner_ID()))
				{
					MUser user = new MUser(getCtx(), 0, get_TrxName());
					user.setC_BPartner_ID(imp.getC_BPartner_ID());
					if(imp.getType() != null)
					{
						if(getValueRef(2000021, imp.getType()) != null)
							user.set_CustomColumn("Type", getValueRef(2000021, imp.getType()));
					}
					if(imp.getIsDefaultCOPESA() != null)
					{
						if(getValueRef(2000024, imp.getIsDefaultCOPESA()) != null)
							user.set_CustomColumn("IsDefaultCOPESA", getValueRef(2000024, imp.getIsDefaultCOPESA()));
					}
					if(imp.getDescription() != null)
						user.setDescription(imp.getDescription());
					if(imp.getEMail() != null)
						user.setEMail(imp.getEMail());
					if(imp.getPhone() != null)
						user.setPhone(imp.getPhone());
					if(imp.getIsWhatsapp() != null)
					{
						if(imp.getIsWhatsapp().compareToIgnoreCase("SI") == 0)
							user.set_CustomColumn("IsWhatsapp", true);
						else
							user.set_CustomColumn("IsWhatsapp", false);
					}
					if(imp.getIsSMS() != null)
					{
						if(imp.getIsSMS().compareToIgnoreCase("SI") == 0)
							user.set_CustomColumn("IsSMS", true);
						else
							user.set_CustomColumn("IsSMS", false);
					}
					if(imp.getPhoneType() != null)
					{
						if(getValueRef(2000033, imp.getPhoneType()) != null)
							user.set_CustomColumn("PhoneType", getValueRef(2000033, imp.getPhoneType()));
					}
					if(imp.getPhoneUse() != null)
					{
						if(getValueRef(2000034, imp.getPhoneUse()) != null)
							user.set_CustomColumn("PhoneUse", getValueRef(2000034, imp.getPhoneUse()));
					}
					user.saveEx(get_TrxName());
				}
			}
			rs.close ();
			pstmt.close ();
			//
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "BP - " + sql.toString(), e);
		}
		//creacion de socios y direcciones de linea
		sql = new StringBuffer ("SELECT * FROM I_OrderMasive "
				  + "WHERE I_IsImported='N' AND (C_BPartnerLine_ID IS NULL OR C_Bpartner_LocationLine_ID IS NULL)").append (clientCheck);
		try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				X_I_OrderMasive imp = new X_I_OrderMasive (getCtx (), rs, get_TrxName());
				//si no trae rut no se crea BP
				if (imp.getValueL() == null)
					continue;
				//	crear nuevo BPartner
				MBPartner bp = MBPartner.get (getCtx(), imp.getValueL());
				if (bp == null)
				{
					bp = new MBPartner (getCtx (), -1, get_TrxName());
					bp.setClientOrg(imp.getAD_Client_ID (), imp.getAD_Org_ID ());
					bp.setValue (imp.getValueL());
					bp.setName((imp.getName1L()+" "+imp.getName5L()+" "+imp.getName2L()+" "+imp.getName3L()+" "+imp.getName4L()).trim());
					if(imp.getName1L() != null)
						bp.set_CustomColumn("Name1", imp.getName1L());
					if(imp.getName2L() != null)
						bp.set_CustomColumn("Name2", imp.getName2L());
					if(imp.getName3L() != null)
						bp.set_CustomColumn("Name3", imp.getName3L());
					if(imp.getName4L() != null)
						bp.set_CustomColumn("Name4", imp.getName4L());
					if(imp.getName5L() != null)
						bp.set_CustomColumn("Name5", imp.getName5L());
					if(imp.getDigitoL() != null)
						bp.set_CustomColumn("Digito", imp.getDigitoL());
					//tipo
					if(imp.getPartnerTypeL() != null)
					{
						if(getValueRef(2000001, imp.getPartnerTypeL()) != null)
							bp.set_CustomColumn("PartnerType", getValueRef(2000001, imp.getPartnerTypeL()));
					}
					if(imp.getVipClientL() != null)
					{
						if(getValueRef(2000002, imp.getVIPClient()) != null)
							bp.set_CustomColumn("VIPClient", getValueRef(2000002, imp.getVipClientL()));
					}
					if(imp.getBirthdayL() != null)
						bp.set_CustomColumn("Birthday", imp.getBirthdayL());
					if(imp.getMaritalStatusL() != null)
					{
						if(getValueRef(53614, imp.getMaritalStatusL()) != null)
							bp.set_CustomColumn("MaritalStatus", getValueRef(53614, imp.getMaritalStatusL()));
					}
					if(imp.getChildrenL() > 0)
						bp.set_CustomColumn("Children", imp.getChildrenL());
					if(imp.getTwitterL() != null)
						bp.set_CustomColumn("Twitter", imp.getTwitterL());
					if(imp.getFacebookL() != null)
						bp.set_CustomColumn("Facebook", imp.getFacebookL());
					if(imp.getGenderL() != null)
					{
						if(getValueRef(53612, imp.getGenderL()) != null)
							bp.set_CustomColumn("Gender", getValueRef(53612, imp.getGenderL()));
					}
					if (!bp.save())
						continue;
				}
				imp.setC_BPartnerLine_ID(bp.getC_BPartner_ID ());
				//creacion de direcciones
//				BP Location
				MBPartnerLocation bpl = null; 
				if(!existLocator(imp, imp.getC_BPartnerLine_ID()))
				{
					bpl = new MBPartnerLocation (bp);
					bpl.setC_Location_ID (2000011);
					if(imp.getHomeTypeL() != null)
					{	
						if(getValueRef(2000003, imp.getHomeTypeL()) != null)
							bpl.set_CustomColumn("HomeType", getValueRef(2000003, imp.getHomeTypeL()));
					}
					if(imp.getAddressTypeL() != null)
					{	
						if(getValueRef(2000004, imp.getAddressTypeL()) != null)
							bpl.set_CustomColumn("AddressType", getValueRef(2000004, imp.getAddressTypeL()));
					}
					
					//seteamos campos ID
					if(imp.getC_CityL_ID() > 0)
						bpl.set_CustomColumn("C_City_ID", imp.getC_CityL_ID());
					if(imp.getC_ProvinceL_ID() > 0)
						bpl.set_CustomColumn("C_Province_ID", imp.getC_ProvinceL_ID());
					if(imp.getC_RegionL_ID() > 0)
						bpl.set_CustomColumn("C_Region_ID", imp.getC_RegionL_ID());
					if(imp.getC_StreetL_ID() > 0)
						bpl.set_CustomColumn("C_Street_ID", imp.getC_StreetL_ID());
					//
					if(imp.getAddressNumberL().compareTo(Env.ZERO) > 0)
						bpl.set_CustomColumn("AddressNumber", imp.getAddressNumberL());
										
					if(imp.getHomeNumberL().compareTo(Env.ZERO) > 0)
						bpl.set_CustomColumn("HomeNumber", Integer.toString(imp.getHomeNumberL().intValue()));
					if(imp.getBlockL() != null)
						bpl.set_CustomColumn("Block", imp.getBlockL());
					if(imp.getVillaL() != null)
						bpl.set_CustomColumn("Villa", imp.getVillaL());
					if(imp.getStreetNameL() != null)
						bpl.set_CustomColumn("StreetName", imp.getStreetNameL());
					if(imp.getZipCodeL() != null)
						bpl.set_CustomColumn("ZipCode", imp.getZipCodeL().toString());
					if(imp.getZoneL() != null)
						bpl.set_CustomColumn("Zone", imp.getZoneL().toString());
					if(imp.getSectorL() != null)
						bpl.set_CustomColumn("Sector", imp.getSectorL().toString());
					if(imp.getLocatorDescriptionL() != null)
						bpl.set_CustomColumn("Description", imp.getLocatorDescriptionL());
					if (!bpl.save ())
						continue;
				}
				imp.set_CustomColumn("C_BPartner_LocationLine_ID",bpl.getC_BPartner_Location_ID());
				imp.save();
			}
			rs.close ();
			pstmt.close ();
			//
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "BP - " + sql.toString(), e);
		}
		
		//
		sql = new StringBuffer ("UPDATE I_OrderMasive "
				  + "SET I_IsImported='N', I_ErrorMsg=I_ErrorMsg||'ERR=No BPartner, ',IsError = 'Y'  "
				  + "WHERE C_BPartner_ID IS NULL"
				  + " AND I_IsImported<>'Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("No BPartner=" + no);
		
		//generacion de codigo que valida que todos los campos obligatorios esten llenos.		 
		
		sql = new StringBuffer ("SELECT ColumnName FROM validationMandatory");
		String condicion = " WHERE I_OrderMasive_ID > 0 ";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				condicion = condicion+", AND "+rs.getString("ColumnName")+" IS NULL";
			}
			DB.executeUpdate("UPDATE I_OrderMasive SET IsError = 'Y', ERROR ", get_TrxName());
			commitEx();
			rs.close ();
			pstmt.close ();
			//
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "BP - " + sql.toString(), e);
		}
		
		//
		/*sql = new StringBuffer ("UPDATE I_Order "
				  + "SET I_IsImported='N', I_ErrorMsg=I_ErrorMsg||'ERR=No BPartner, ',IsError = 'Y'  "
				  + "WHERE C_BPartner_ID IS NULL"
				  + " AND I_IsImported<>'Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("No BPartner=" + no);
			*/		
		
		//	-- New Orders -----------------------------------------------------

		int noInsert = 0;
		int noInsertLine = 0;

		//	Go through Order Records w/o
		sql = new StringBuffer ("SELECT * FROM I_OrderMasive "
			  + "WHERE I_IsImported='N' AND IsError = 'N'").append (clientCheck)
			.append(" ORDER BY C_BPartner_ID, C_BPartner_Location_ID, I_OrderMasive_ID");
		try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			//
			int oldC_BPartner_ID = 0;
			int oldC_BPartner_Location_ID = 0;
			String oldDocumentNo = "";
			//
			MOrder order = null;
			int lineNo = 0;
			while (rs.next ())
			{
				X_I_OrderMasive imp = new X_I_OrderMasive (getCtx (), rs.getInt("I_OrderMasive_ID"), get_TrxName());
				String cmpDocumentNo = imp.getDocumentNo();
				if (cmpDocumentNo == null)
					cmpDocumentNo = "";
				//	New Order
				if (oldC_BPartner_ID != imp.getC_BPartner_ID() 
					|| oldC_BPartner_Location_ID != imp.getC_BPartner_Location_ID()
					|| !oldDocumentNo.equals(cmpDocumentNo))
				{
					if (order != null)
					{
						if (m_docAction != null && m_docAction.length() > 0)
						{
							order.setDocAction(m_docAction);
							order.processIt (m_docAction);
						}
						order.saveEx();
					}
					oldC_BPartner_ID = imp.getC_BPartner_ID();
					oldC_BPartner_Location_ID = imp.getC_BPartner_Location_ID();
					oldDocumentNo = imp.getDocumentNo();
					if (oldDocumentNo == null)
						oldDocumentNo = "";
					//
					order = new MOrder (getCtx(), 0, get_TrxName());
					order.setClientOrg (imp.getAD_Client_ID(), m_AD_Org_ID);
					/*if(imp.getAD_Org_ID() <= 0)
						order.setAD_Org_ID(m_AD_Org_ID);*/
					order.setC_DocTypeTarget_ID(MDocType.getDocType("SOO", imp.getAD_Org_ID()));
					order.setIsSOTrx(true);
					order.setDeliveryRule("A");
					if (imp.getDocumentNo() != null)
						order.setDocumentNo(imp.getDocumentNo());
					//	Ship Partner
					order.setC_BPartner_ID(imp.getC_BPartner_ID());
					order.setC_BPartner_Location_ID(imp.getC_BPartner_Location_ID());
					order.setAD_User_ID(Env.getAD_User_ID(getCtx()));
					//	Bill Partner
					order.setBill_BPartner_ID(imp.getC_BPartner_ID());
					order.setBill_Location_ID(imp.getC_BPartner_Location_ID());
					//
					if (imp.getDescription() != null)
						order.setDescription(imp.getDescription());
					if(imp.getC_PaymentTerm_ID() > 0)
						order.setC_PaymentTerm_ID(imp.getC_PaymentTerm_ID());
					else
						order.setC_PaymentTerm_ID(imp.getC_PaymentTerm_ID());
					order.setM_PriceList_ID(imp.getM_PriceList_ID());
					order.setM_Warehouse_ID(imp.getM_Warehouse_ID());
					order.setSalesRep_ID(getAD_User_ID());
					//
					order.setAD_OrgTrx_ID(imp.getAD_Org_ID());
					order.setDateOrdered(imp.getDateCompleted());
					order.setDatePromised(imp.getDateCompleted());
					//order.set_CustomColumn("DateCompleted",imp.getDateCompleted());
					order.setDateAcct(imp.getDateCompleted());
					if(getValueRef(195, imp.getPaymentRule()) != null);
						order.setPaymentRule(getValueRef(195, imp.getPaymentRule()));
					if(getValueRef(2000036, imp.getTypePaymentRule()) != null);
						order.set_CustomColumn("TypePaymentRule",getValueRef(2000036, imp.getTypePaymentRule()));
					if(imp.getPOReference() != null)
						order.set_CustomColumn("POReference2",imp.getPOReference());
					//
					order.saveEx();
					noInsert++;
					lineNo = 10;
				}
				imp.setC_Order_ID(order.getC_Order_ID());
				//	New OrderLine
				MOrderLine line = new MOrderLine (order);
				line.setLine(lineNo);
				lineNo += 10;
				if (imp.getM_Product_ID() != 0)
					line.setM_Product_ID(imp.getM_Product_ID(), true);
				//se agrega cargo para ser cargado en archivo
				else if(imp.get_ValueAsInt("C_Charge_ID") > 0)
					line.setC_Charge_ID(imp.get_ValueAsInt("C_Charge_ID"));
				line.setQty(imp.getQtyEntered());
				line.setPrice(imp.getPriceActual());
				if(imp.getC_BPartnerLine_ID() > 0)
					line.set_CustomColumn("C_BpartnerRef_ID", imp.getC_BPartnerLine_ID());
				if(imp.getC_Bpartner_LocationLine_ID() > 0)
					line.set_CustomColumn("C_Bpartner_Location_ID", imp.getC_Bpartner_LocationLine_ID());
				if(imp.getDatePromised2() != null)
					line.set_CustomColumn("DatePromised2",imp.getDatePromised2());
				if(imp.getDatePromised3() != null)
					line.set_CustomColumn("DatePromised3",imp.getDatePromised3());
				if(imp.getM_Locator_ID() > 0)
					line.set_CustomColumn("M_Locator_ID",imp.getM_Locator_ID());
				if(imp.getLineNetAmt().compareTo(Env.ZERO) > 0)
					line.setLineNetAmt(imp.getLineNetAmt());
				if(imp.getC_CalendarCOPESA_ID() > 0)
					line.set_CustomColumn("C_CalendarCOPESA_ID",imp.getC_CalendarCOPESA_ID());
				if(imp.getMonthlyAmount() != null 
						&& imp.getMonthlyAmount().compareTo(Env.ZERO) >= 0)
					line.set_CustomColumn("MonthlyAmount", imp.getMonthlyAmount());
				if(imp.getLevelNo() > 0)
					line.set_CustomColumn("LevelNo", imp.getLevelNo());
				
				line.setC_Tax_ID(2000002);//siempre es IVA				
				line.saveEx();
				imp.setC_OrderLine_ID(line.getC_OrderLine_ID());
				imp.setI_IsImported(true);
				imp.setProcessed(true);
				//
				if (imp.save())
					noInsertLine++;
			}
			if (order != null)
			{
				if (m_docAction != null && m_docAction.length() > 0)
				{
					order.setDocAction(m_docAction);
					order.processIt (m_docAction);
					order.saveEx();
				}
			}
			rs.close();
			pstmt.close();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Order - " + sql.toString(), e);
		}

		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_Order "
			+ "SET I_IsImported='N', Updated=SysDate "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		//addLog (0, null, new BigDecimal (no), "@Errors@");
		//
		addLog (0, null, new BigDecimal (noInsert), "@C_Order_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noInsertLine), "@C_OrderLine_ID@: @Inserted@");
		return "#" + noInsert + "/" + noInsertLine;
	}	//	doIt
	private Boolean existContact(String desc, int ID_Bpartner)
	{
		int cant = 0;
		if(ID_Bpartner > 0 && desc != null)
		{
			cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_User WHERE C_Bpartner_ID ="+ID_Bpartner+
				" AND lower(Description) = '"+desc.toLowerCase()+"'");
		}
		if(cant > 0)
			return true;
		else
			return false;
	}
	private Boolean existLocator(X_I_OrderMasive iOrder, int ID_Bpartner)
	{
		int cant = 0;
		if(iOrder.getC_StreetL_ID() > 0 || iOrder.getStreetNameL() != null)
		{
			cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_BPartner_Location " +
					" WHERE C_Bpartner_ID = "+ID_Bpartner+" AND AddressNumber = '"+iOrder.getAddressNumberL()+"' AND HomeNumber = '"+iOrder.getHomeNumberL()+"' " +
					" AND (C_Street_ID = "+iOrder.getC_StreetL_ID()+" OR StreetName = '"+iOrder.getStreetNameL()+"')");
		}
		if(cant > 0)
			return true;
		else
			return false;
	}
	private int getLocator(X_I_OrderMasive iOrder, int ID_Bpartner)
	{
		int cant = 0;
		if(iOrder.getC_StreetL_ID() > 0 || iOrder.getStreetNameL() != null)
		{
			cant = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_BPartner_Location_ID) FROM C_BPartner_Location " +
					" WHERE C_Bpartner_ID = "+ID_Bpartner+" AND AddressNumber = '"+iOrder.getAddressNumberL()+"' AND HomeNumber = '"+iOrder.getHomeNumberL()+"' " +
					" AND (C_Street_ID = "+iOrder.getC_StreetL_ID()+" OR StreetName = '"+iOrder.getStreetNameL()+"')");
		}
		return cant;
	}
	private String getValueRef(int ID_Reference, String name)
	{
		String valueRef = DB.getSQLValueString(get_TrxName(), "SELECT VALUE FROM AD_Ref_List " +
				" WHERE AD_Reference_ID="+ID_Reference+" AND Name = '"+name+"'");
		return valueRef;
	}

}	//	ImportOrder
