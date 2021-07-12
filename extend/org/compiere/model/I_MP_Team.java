/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for MP_Team
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_MP_Team 
{

    /** TableName=MP_Team */
    public static final String Table_Name = "MP_Team";

    /** AD_Table_ID=2000051 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_OrgRef_ID */
    public static final String COLUMNNAME_AD_OrgRef_ID = "AD_OrgRef_ID";

	/** Set AD_OrgRef_ID	  */
	public void setAD_OrgRef_ID (int AD_OrgRef_ID);

	/** Get AD_OrgRef_ID	  */
	public int getAD_OrgRef_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name descrip1 */
    public static final String COLUMNNAME_descrip1 = "descrip1";

	/** Set descrip1	  */
	public void setdescrip1 (String descrip1);

	/** Get descrip1	  */
	public String getdescrip1();

    /** Column name descrip2 */
    public static final String COLUMNNAME_descrip2 = "descrip2";

	/** Set descrip2	  */
	public void setdescrip2 (String descrip2);

	/** Get descrip2	  */
	public String getdescrip2();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name MP_Instrument_ID */
    public static final String COLUMNNAME_MP_Instrument_ID = "MP_Instrument_ID";

	/** Set MP_Instrument_ID	  */
	public void setMP_Instrument_ID (int MP_Instrument_ID);

	/** Get MP_Instrument_ID	  */
	public int getMP_Instrument_ID();

	public I_MP_Instrument getMP_Instrument() throws RuntimeException;

    /** Column name MP_Team_ID */
    public static final String COLUMNNAME_MP_Team_ID = "MP_Team_ID";

	/** Set MP_Team ID	  */
	public void setMP_Team_ID (int MP_Team_ID);

	/** Get MP_Team ID	  */
	public int getMP_Team_ID();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name team */
    public static final String COLUMNNAME_team = "team";

	/** Set team	  */
	public void setteam (String team);

	/** Get team	  */
	public String getteam();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
