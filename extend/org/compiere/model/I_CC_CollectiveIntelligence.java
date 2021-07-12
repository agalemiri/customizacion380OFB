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

/** Generated Interface for CC_CollectiveIntelligence
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_CC_CollectiveIntelligence 
{

    /** TableName=CC_CollectiveIntelligence */
    public static final String Table_Name = "CC_CollectiveIntelligence";

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

    /** Column name CC_CI01 */
    public static final String COLUMNNAME_CC_CI01 = "CC_CI01";

	/** Set CC_CI01	  */
	public void setCC_CI01 (boolean CC_CI01);

	/** Get CC_CI01	  */
	public boolean isCC_CI01();

    /** Column name CC_CI02 */
    public static final String COLUMNNAME_CC_CI02 = "CC_CI02";

	/** Set CC_CI02	  */
	public void setCC_CI02 (boolean CC_CI02);

	/** Get CC_CI02	  */
	public boolean isCC_CI02();

    /** Column name CC_CI03 */
    public static final String COLUMNNAME_CC_CI03 = "CC_CI03";

	/** Set CC_CI03	  */
	public void setCC_CI03 (boolean CC_CI03);

	/** Get CC_CI03	  */
	public boolean isCC_CI03();

    /** Column name CC_CI04 */
    public static final String COLUMNNAME_CC_CI04 = "CC_CI04";

	/** Set CC_CI04	  */
	public void setCC_CI04 (boolean CC_CI04);

	/** Get CC_CI04	  */
	public boolean isCC_CI04();

    /** Column name CC_CI05 */
    public static final String COLUMNNAME_CC_CI05 = "CC_CI05";

	/** Set CC_CI05	  */
	public void setCC_CI05 (boolean CC_CI05);

	/** Get CC_CI05	  */
	public boolean isCC_CI05();

    /** Column name CC_CI06 */
    public static final String COLUMNNAME_CC_CI06 = "CC_CI06";

	/** Set CC_CI06	  */
	public void setCC_CI06 (boolean CC_CI06);

	/** Get CC_CI06	  */
	public boolean isCC_CI06();

    /** Column name CC_CI07 */
    public static final String COLUMNNAME_CC_CI07 = "CC_CI07";

	/** Set CC_CI07	  */
	public void setCC_CI07 (boolean CC_CI07);

	/** Get CC_CI07	  */
	public boolean isCC_CI07();

    /** Column name CC_CI08 */
    public static final String COLUMNNAME_CC_CI08 = "CC_CI08";

	/** Set CC_CI08	  */
	public void setCC_CI08 (boolean CC_CI08);

	/** Get CC_CI08	  */
	public boolean isCC_CI08();

    /** Column name CC_CI09 */
    public static final String COLUMNNAME_CC_CI09 = "CC_CI09";

	/** Set CC_CI09	  */
	public void setCC_CI09 (boolean CC_CI09);

	/** Get CC_CI09	  */
	public boolean isCC_CI09();

    /** Column name CC_CI10 */
    public static final String COLUMNNAME_CC_CI10 = "CC_CI10";

	/** Set CC_CI10	  */
	public void setCC_CI10 (boolean CC_CI10);

	/** Get CC_CI10	  */
	public boolean isCC_CI10();

    /** Column name CC_CI11 */
    public static final String COLUMNNAME_CC_CI11 = "CC_CI11";

	/** Set CC_CI11	  */
	public void setCC_CI11 (boolean CC_CI11);

	/** Get CC_CI11	  */
	public boolean isCC_CI11();

    /** Column name CC_CI12 */
    public static final String COLUMNNAME_CC_CI12 = "CC_CI12";

	/** Set CC_CI12	  */
	public void setCC_CI12 (boolean CC_CI12);

	/** Get CC_CI12	  */
	public boolean isCC_CI12();

    /** Column name CC_CI13 */
    public static final String COLUMNNAME_CC_CI13 = "CC_CI13";

	/** Set CC_CI13	  */
	public void setCC_CI13 (boolean CC_CI13);

	/** Get CC_CI13	  */
	public boolean isCC_CI13();

    /** Column name CC_CI14 */
    public static final String COLUMNNAME_CC_CI14 = "CC_CI14";

	/** Set CC_CI14	  */
	public void setCC_CI14 (boolean CC_CI14);

	/** Get CC_CI14	  */
	public boolean isCC_CI14();

    /** Column name CC_CI15 */
    public static final String COLUMNNAME_CC_CI15 = "CC_CI15";

	/** Set CC_CI15	  */
	public void setCC_CI15 (boolean CC_CI15);

	/** Get CC_CI15	  */
	public boolean isCC_CI15();

    /** Column name CC_CI16 */
    public static final String COLUMNNAME_CC_CI16 = "CC_CI16";

	/** Set CC_CI16	  */
	public void setCC_CI16 (boolean CC_CI16);

	/** Get CC_CI16	  */
	public boolean isCC_CI16();

    /** Column name CC_CI17 */
    public static final String COLUMNNAME_CC_CI17 = "CC_CI17";

	/** Set CC_CI17	  */
	public void setCC_CI17 (boolean CC_CI17);

	/** Get CC_CI17	  */
	public boolean isCC_CI17();

    /** Column name CC_CI18 */
    public static final String COLUMNNAME_CC_CI18 = "CC_CI18";

	/** Set CC_CI18	  */
	public void setCC_CI18 (boolean CC_CI18);

	/** Get CC_CI18	  */
	public boolean isCC_CI18();

    /** Column name CC_CI19 */
    public static final String COLUMNNAME_CC_CI19 = "CC_CI19";

	/** Set CC_CI19	  */
	public void setCC_CI19 (boolean CC_CI19);

	/** Get CC_CI19	  */
	public boolean isCC_CI19();

    /** Column name CC_CI20 */
    public static final String COLUMNNAME_CC_CI20 = "CC_CI20";

	/** Set CC_CI20	  */
	public void setCC_CI20 (boolean CC_CI20);

	/** Get CC_CI20	  */
	public boolean isCC_CI20();

    /** Column name CC_CI21 */
    public static final String COLUMNNAME_CC_CI21 = "CC_CI21";

	/** Set CC_CI21	  */
	public void setCC_CI21 (boolean CC_CI21);

	/** Get CC_CI21	  */
	public boolean isCC_CI21();

    /** Column name CC_CI22 */
    public static final String COLUMNNAME_CC_CI22 = "CC_CI22";

	/** Set CC_CI22	  */
	public void setCC_CI22 (boolean CC_CI22);

	/** Get CC_CI22	  */
	public boolean isCC_CI22();

    /** Column name CC_CI23 */
    public static final String COLUMNNAME_CC_CI23 = "CC_CI23";

	/** Set CC_CI23	  */
	public void setCC_CI23 (boolean CC_CI23);

	/** Get CC_CI23	  */
	public boolean isCC_CI23();

    /** Column name CC_CI24 */
    public static final String COLUMNNAME_CC_CI24 = "CC_CI24";

	/** Set CC_CI24	  */
	public void setCC_CI24 (boolean CC_CI24);

	/** Get CC_CI24	  */
	public boolean isCC_CI24();

    /** Column name CC_CI25 */
    public static final String COLUMNNAME_CC_CI25 = "CC_CI25";

	/** Set CC_CI25	  */
	public void setCC_CI25 (boolean CC_CI25);

	/** Get CC_CI25	  */
	public boolean isCC_CI25();

    /** Column name CC_CI26 */
    public static final String COLUMNNAME_CC_CI26 = "CC_CI26";

	/** Set CC_CI26	  */
	public void setCC_CI26 (boolean CC_CI26);

	/** Get CC_CI26	  */
	public boolean isCC_CI26();

    /** Column name CC_CI27 */
    public static final String COLUMNNAME_CC_CI27 = "CC_CI27";

	/** Set CC_CI27	  */
	public void setCC_CI27 (boolean CC_CI27);

	/** Get CC_CI27	  */
	public boolean isCC_CI27();

    /** Column name CC_CI28 */
    public static final String COLUMNNAME_CC_CI28 = "CC_CI28";

	/** Set CC_CI28	  */
	public void setCC_CI28 (boolean CC_CI28);

	/** Get CC_CI28	  */
	public boolean isCC_CI28();

    /** Column name CC_CI29 */
    public static final String COLUMNNAME_CC_CI29 = "CC_CI29";

	/** Set CC_CI29	  */
	public void setCC_CI29 (boolean CC_CI29);

	/** Get CC_CI29	  */
	public boolean isCC_CI29();

    /** Column name CC_CI30 */
    public static final String COLUMNNAME_CC_CI30 = "CC_CI30";

	/** Set CC_CI30	  */
	public void setCC_CI30 (boolean CC_CI30);

	/** Get CC_CI30	  */
	public boolean isCC_CI30();

    /** Column name CC_CI31 */
    public static final String COLUMNNAME_CC_CI31 = "CC_CI31";

	/** Set CC_CI31	  */
	public void setCC_CI31 (boolean CC_CI31);

	/** Get CC_CI31	  */
	public boolean isCC_CI31();

    /** Column name CC_CI32 */
    public static final String COLUMNNAME_CC_CI32 = "CC_CI32";

	/** Set CC_CI32	  */
	public void setCC_CI32 (boolean CC_CI32);

	/** Get CC_CI32	  */
	public boolean isCC_CI32();

    /** Column name CC_CI33 */
    public static final String COLUMNNAME_CC_CI33 = "CC_CI33";

	/** Set CC_CI33	  */
	public void setCC_CI33 (boolean CC_CI33);

	/** Get CC_CI33	  */
	public boolean isCC_CI33();

    /** Column name CC_CI34 */
    public static final String COLUMNNAME_CC_CI34 = "CC_CI34";

	/** Set CC_CI34	  */
	public void setCC_CI34 (boolean CC_CI34);

	/** Get CC_CI34	  */
	public boolean isCC_CI34();

    /** Column name CC_CI35 */
    public static final String COLUMNNAME_CC_CI35 = "CC_CI35";

	/** Set CC_CI35	  */
	public void setCC_CI35 (boolean CC_CI35);

	/** Get CC_CI35	  */
	public boolean isCC_CI35();

    /** Column name CC_CI36 */
    public static final String COLUMNNAME_CC_CI36 = "CC_CI36";

	/** Set CC_CI36	  */
	public void setCC_CI36 (boolean CC_CI36);

	/** Get CC_CI36	  */
	public boolean isCC_CI36();

    /** Column name CC_CI37 */
    public static final String COLUMNNAME_CC_CI37 = "CC_CI37";

	/** Set CC_CI37	  */
	public void setCC_CI37 (boolean CC_CI37);

	/** Get CC_CI37	  */
	public boolean isCC_CI37();

    /** Column name CC_CI38 */
    public static final String COLUMNNAME_CC_CI38 = "CC_CI38";

	/** Set CC_CI38	  */
	public void setCC_CI38 (boolean CC_CI38);

	/** Get CC_CI38	  */
	public boolean isCC_CI38();

    /** Column name CC_CI39 */
    public static final String COLUMNNAME_CC_CI39 = "CC_CI39";

	/** Set CC_CI39	  */
	public void setCC_CI39 (boolean CC_CI39);

	/** Get CC_CI39	  */
	public boolean isCC_CI39();

    /** Column name CC_CI40 */
    public static final String COLUMNNAME_CC_CI40 = "CC_CI40";

	/** Set CC_CI40	  */
	public void setCC_CI40 (boolean CC_CI40);

	/** Get CC_CI40	  */
	public boolean isCC_CI40();

    /** Column name CC_CI41 */
    public static final String COLUMNNAME_CC_CI41 = "CC_CI41";

	/** Set CC_CI41	  */
	public void setCC_CI41 (boolean CC_CI41);

	/** Get CC_CI41	  */
	public boolean isCC_CI41();

    /** Column name CC_CI42 */
    public static final String COLUMNNAME_CC_CI42 = "CC_CI42";

	/** Set CC_CI42	  */
	public void setCC_CI42 (boolean CC_CI42);

	/** Get CC_CI42	  */
	public boolean isCC_CI42();

    /** Column name CC_CI43 */
    public static final String COLUMNNAME_CC_CI43 = "CC_CI43";

	/** Set CC_CI43	  */
	public void setCC_CI43 (boolean CC_CI43);

	/** Get CC_CI43	  */
	public boolean isCC_CI43();

    /** Column name CC_CI44 */
    public static final String COLUMNNAME_CC_CI44 = "CC_CI44";

	/** Set CC_CI44	  */
	public void setCC_CI44 (boolean CC_CI44);

	/** Get CC_CI44	  */
	public boolean isCC_CI44();

    /** Column name CC_CI45 */
    public static final String COLUMNNAME_CC_CI45 = "CC_CI45";

	/** Set CC_CI45	  */
	public void setCC_CI45 (boolean CC_CI45);

	/** Get CC_CI45	  */
	public boolean isCC_CI45();

    /** Column name CC_CI46 */
    public static final String COLUMNNAME_CC_CI46 = "CC_CI46";

	/** Set CC_CI46	  */
	public void setCC_CI46 (boolean CC_CI46);

	/** Get CC_CI46	  */
	public boolean isCC_CI46();

    /** Column name CC_CI47 */
    public static final String COLUMNNAME_CC_CI47 = "CC_CI47";

	/** Set CC_CI47	  */
	public void setCC_CI47 (boolean CC_CI47);

	/** Get CC_CI47	  */
	public boolean isCC_CI47();

    /** Column name CC_CI48 */
    public static final String COLUMNNAME_CC_CI48 = "CC_CI48";

	/** Set CC_CI48	  */
	public void setCC_CI48 (boolean CC_CI48);

	/** Get CC_CI48	  */
	public boolean isCC_CI48();

    /** Column name CC_CI49 */
    public static final String COLUMNNAME_CC_CI49 = "CC_CI49";

	/** Set CC_CI49	  */
	public void setCC_CI49 (boolean CC_CI49);

	/** Get CC_CI49	  */
	public boolean isCC_CI49();

    /** Column name CC_CI50 */
    public static final String COLUMNNAME_CC_CI50 = "CC_CI50";

	/** Set CC_CI50	  */
	public void setCC_CI50 (boolean CC_CI50);

	/** Get CC_CI50	  */
	public boolean isCC_CI50();

    /** Column name CC_CI51 */
    public static final String COLUMNNAME_CC_CI51 = "CC_CI51";

	/** Set CC_CI51	  */
	public void setCC_CI51 (boolean CC_CI51);

	/** Get CC_CI51	  */
	public boolean isCC_CI51();

    /** Column name CC_CI52 */
    public static final String COLUMNNAME_CC_CI52 = "CC_CI52";

	/** Set CC_CI52	  */
	public void setCC_CI52 (boolean CC_CI52);

	/** Get CC_CI52	  */
	public boolean isCC_CI52();

    /** Column name CC_CI53 */
    public static final String COLUMNNAME_CC_CI53 = "CC_CI53";

	/** Set CC_CI53	  */
	public void setCC_CI53 (boolean CC_CI53);

	/** Get CC_CI53	  */
	public boolean isCC_CI53();

    /** Column name CC_CI54 */
    public static final String COLUMNNAME_CC_CI54 = "CC_CI54";

	/** Set CC_CI54	  */
	public void setCC_CI54 (boolean CC_CI54);

	/** Get CC_CI54	  */
	public boolean isCC_CI54();

    /** Column name CC_CI55 */
    public static final String COLUMNNAME_CC_CI55 = "CC_CI55";

	/** Set CC_CI55	  */
	public void setCC_CI55 (boolean CC_CI55);

	/** Get CC_CI55	  */
	public boolean isCC_CI55();

    /** Column name CC_CI56 */
    public static final String COLUMNNAME_CC_CI56 = "CC_CI56";

	/** Set CC_CI56	  */
	public void setCC_CI56 (boolean CC_CI56);

	/** Get CC_CI56	  */
	public boolean isCC_CI56();

    /** Column name CC_CI57 */
    public static final String COLUMNNAME_CC_CI57 = "CC_CI57";

	/** Set CC_CI57	  */
	public void setCC_CI57 (boolean CC_CI57);

	/** Get CC_CI57	  */
	public boolean isCC_CI57();

    /** Column name CC_CI58 */
    public static final String COLUMNNAME_CC_CI58 = "CC_CI58";

	/** Set CC_CI58	  */
	public void setCC_CI58 (boolean CC_CI58);

	/** Get CC_CI58	  */
	public boolean isCC_CI58();

    /** Column name CC_CI59 */
    public static final String COLUMNNAME_CC_CI59 = "CC_CI59";

	/** Set CC_CI59	  */
	public void setCC_CI59 (boolean CC_CI59);

	/** Get CC_CI59	  */
	public boolean isCC_CI59();

    /** Column name CC_CI60 */
    public static final String COLUMNNAME_CC_CI60 = "CC_CI60";

	/** Set CC_CI60	  */
	public void setCC_CI60 (boolean CC_CI60);

	/** Get CC_CI60	  */
	public boolean isCC_CI60();

    /** Column name CC_CollectiveIntelligence_ID */
    public static final String COLUMNNAME_CC_CollectiveIntelligence_ID = "CC_CollectiveIntelligence_ID";

	/** Set CC_CollectiveIntelligence ID	  */
	public void setCC_CollectiveIntelligence_ID (int CC_CollectiveIntelligence_ID);

	/** Get CC_CollectiveIntelligence ID	  */
	public int getCC_CollectiveIntelligence_ID();

    /** Column name CC_Hospitalization_ID */
    public static final String COLUMNNAME_CC_Hospitalization_ID = "CC_Hospitalization_ID";

	/** Set CC_Hospitalization ID	  */
	public void setCC_Hospitalization_ID (int CC_Hospitalization_ID);

	/** Get CC_Hospitalization ID	  */
	public int getCC_Hospitalization_ID();

	public I_CC_Hospitalization getCC_Hospitalization() throws RuntimeException;

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

    /** Column name Date1 */
    public static final String COLUMNNAME_Date1 = "Date1";

	/** Set Date.
	  * Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1);

	/** Get Date.
	  * Date when business is not conducted
	  */
	public Timestamp getDate1();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

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
