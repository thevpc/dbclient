/**
 * ====================================================================
 *             DBClient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */

package net.vpc.dbclient.plugin.sql.jstsql;

import net.vpc.dbclient.api.configmanager.DBCAbstractInfo;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class JSTSqlTemplateInfo extends DBCAbstractInfo implements Cloneable {
    public static final String SST_ID = "sstId";
    public static final String SST_VERSION = "sstVersion";
    public static final String SST_SFC_NAME = "sstSfcName";
    public static final String SST_INDEX = "sstIndex";
    public static final String SST_GROUP_INDEX = "sstGroupIndex";
    public static final String SST_NAME = "sstName";
    public static final String SST_IS_SEPARATOR = "sstIsSeparator";
    public static final String SST_SQL = "sstSql";
    public static final String SST_NODE_FILTER = "sstNodeFilter";
    public static final String SST_PREFER_TEMPLATE = "sstPreferTemplate";
    public static final String SST_CONFIRM_ENABLED = "sstConfirmEnabled";
    public static final String SST_CONFIRM_MSG = "sstConfirmMsg";
    public static final String SST_ENABLED = "sstEnabled";

    /**
     * Constructor
     */
    public JSTSqlTemplateInfo() {
    }

    /**
     * getter for sstId
     */
    public Integer getSstId() {
        return ((Integer) map.get(SST_ID));
    }

    /**
     * setter for sstId
     */
    public void setSstId(Integer value) {
        map.put(SST_ID, value);
    }

    /**
     * true if record contains the field sstId
     */
    public boolean containsSstId() {
        return map.containsKey(SST_ID);
    }

    /**
     * remove the field sstId
     */
    public JSTSqlTemplateInfo unsetSstId() {
        map.remove(SST_ID);
        return this;
    }

    /**
     * getter for sstVersion
     */
    public Integer getSstVersion() {
        return ((Integer) map.get(SST_VERSION));
    }

    /**
     * setter for sstVersion
     */
    public void setSstVersion(Integer value) {
        map.put(SST_VERSION, value);
    }

    /**
     * true if record contains the field sstVersion
     */
    public boolean containsSstVersion() {
        return map.containsKey(SST_VERSION);
    }

    /**
     * remove the field sstVersion
     */
    public JSTSqlTemplateInfo unsetSstVersion() {
        map.remove(SST_VERSION);
        return this;
    }

    /**
     * getter for sstSfcName
     */
    public String getSstSfcName() {
        return ((String) map.get(SST_SFC_NAME));
    }

    /**
     * setter for sstSfcName
     */
    public void setSstSfcName(String value) {
        map.put(SST_SFC_NAME, value);
    }

    /**
     * true if record contains the field sstSfcName
     */
    public boolean containsSstSfcName() {
        return map.containsKey(SST_SFC_NAME);
    }

    /**
     * remove the field sstSfcName
     */
    public JSTSqlTemplateInfo unsetSstSfcName() {
        map.remove(SST_SFC_NAME);
        return this;
    }

    /**
     * getter for sstIndex
     */
    public Integer getSstIndex() {
        return ((Integer) map.get(SST_INDEX));
    }

    /**
     * setter for sstIndex
     */
    public void setSstIndex(Integer value) {
        map.put(SST_INDEX, value);
    }

    /**
     * true if record contains the field sstIndex
     */
    public boolean containsSstIndex() {
        return map.containsKey(SST_INDEX);
    }

    /**
     * remove the field sstIndex
     */
    public JSTSqlTemplateInfo unsetSstIndex() {
        map.remove(SST_INDEX);
        return this;
    }

    /**
     * getter for sstGroupIndex
     */
    public Integer getSstGroupIndex() {
        return ((Integer) map.get(SST_GROUP_INDEX));
    }

    /**
     * setter for sstGroupIndex
     */
    public void setSstGroupIndex(Integer value) {
        map.put(SST_GROUP_INDEX, value);
    }

    /**
     * true if record contains the field sstGroupIndex
     */
    public boolean containsSstGroupIndex() {
        return map.containsKey(SST_GROUP_INDEX);
    }

    /**
     * remove the field sstGroupIndex
     */
    public JSTSqlTemplateInfo unsetSstGroupIndex() {
        map.remove(SST_GROUP_INDEX);
        return this;
    }

    /**
     * getter for sstName
     */
    public String getSstName() {
        return ((String) map.get(SST_NAME));
    }

    /**
     * setter for sstName
     */
    public void setSstName(String value) {
        map.put(SST_NAME, value);
    }

    /**
     * true if record contains the field sstName
     */
    public boolean containsSstName() {
        return map.containsKey(SST_NAME);
    }

    /**
     * remove the field sstName
     */
    public JSTSqlTemplateInfo unsetSstName() {
        map.remove(SST_NAME);
        return this;
    }

    /**
     * getter for sstIsSeparator
     */
    public Boolean getSstIsSeparator() {
        return ((Boolean) map.get(SST_IS_SEPARATOR));
    }

    /**
     * setter for sstIsSeparator
     */
    public void setSstIsSeparator(Boolean value) {
        map.put(SST_IS_SEPARATOR, value);
    }

    /**
     * true if record contains the field sstIsSeparator
     */
    public boolean containsSstIsSeparator() {
        return map.containsKey(SST_IS_SEPARATOR);
    }

    /**
     * remove the field sstIsSeparator
     */
    public JSTSqlTemplateInfo unsetSstIsSeparator() {
        map.remove(SST_IS_SEPARATOR);
        return this;
    }

    /**
     * getter for sstSql
     */
    public String getSstSql() {
        return ((String) map.get(SST_SQL));
    }

    /**
     * setter for sstSql
     */
    public void setSstSql(String value) {
        map.put(SST_SQL, value);
    }

    /**
     * true if record contains the field sstSql
     */
    public boolean containsSstSql() {
        return map.containsKey(SST_SQL);
    }

    /**
     * remove the field sstSql
     */
    public JSTSqlTemplateInfo unsetSstSql() {
        map.remove(SST_SQL);
        return this;
    }

    /**
     * getter for sstNodeFilter
     */
    public String getSstNodeFilter() {
        return ((String) map.get(SST_NODE_FILTER));
    }

    /**
     * setter for sstNodeFilter
     */
    public void setSstNodeFilter(String value) {
        map.put(SST_NODE_FILTER, value);
    }

    /**
     * true if record contains the field sstNodeFilter
     */
    public boolean containsSstNodeFilter() {
        return map.containsKey(SST_NODE_FILTER);
    }

    /**
     * remove the field sstNodeFilter
     */
    public JSTSqlTemplateInfo unsetSstNodeFilter() {
        map.remove(SST_NODE_FILTER);
        return this;
    }

    /**
     * getter for sstPreferTemplate
     */
    public Boolean getSstPreferTemplate() {
        return ((Boolean) map.get(SST_PREFER_TEMPLATE));
    }

    /**
     * setter for sstPreferTemplate
     */
    public JSTSqlTemplateInfo setSstPreferTemplate(Boolean value) {
        map.put(SST_PREFER_TEMPLATE, value);
        return this;
    }

    /**
     * true if record contains the field sstPreferTemplate
     */
    public boolean containsSstPreferTemplate() {
        return map.containsKey(SST_PREFER_TEMPLATE);
    }

    /**
     * remove the field sstPreferTemplate
     */
    public JSTSqlTemplateInfo unsetSstPreferTemplate() {
        map.remove(SST_PREFER_TEMPLATE);
        return this;
    }

    /**
     * getter for sstConfirmEnabled
     */
    public Boolean getSstConfirmEnabled() {
        return ((Boolean) map.get(SST_CONFIRM_ENABLED));
    }

    /**
     * setter for sstConfirmEnabled
     */
    public JSTSqlTemplateInfo setSstConfirmEnabled(Boolean value) {
        map.put(SST_CONFIRM_ENABLED, value);
        return this;
    }

    /**
     * true if record contains the field sstConfirmEnabled
     */
    public boolean containsSstConfirmEnabled() {
        return map.containsKey(SST_CONFIRM_ENABLED);
    }

    /**
     * remove the field sstConfirmEnabled
     */
    public JSTSqlTemplateInfo unsetSstConfirmEnabled() {
        map.remove(SST_CONFIRM_ENABLED);
        return this;
    }

    /**
     * getter for sstConfirmMsg
     */
    public String getSstConfirmMsg() {
        return ((String) map.get(SST_CONFIRM_MSG));
    }

    /**
     * setter for sstConfirmMsg
     */
    public JSTSqlTemplateInfo setSstConfirmMsg(String value) {
        map.put(SST_CONFIRM_MSG, value);
        return this;
    }

    /**
     * true if record contains the field sstConfirmMsg
     */
    public boolean containsSstConfirmMsg() {
        return map.containsKey(SST_CONFIRM_MSG);
    }

    /**
     * remove the field sstConfirmMsg
     */
    public JSTSqlTemplateInfo unsetSstConfirmMsg() {
        map.remove(SST_CONFIRM_MSG);
        return this;
    }

    /**
     * getter for sstEnabled
     */
    public Boolean getSstEnabled() {
        return ((Boolean) map.get(SST_ENABLED));
    }

    /**
     * setter for sstEnabled
     */
    public JSTSqlTemplateInfo setSstEnabled(Boolean value) {
        map.put(SST_ENABLED, value);
        return this;
    }

    /**
     * true if record contains the field sstEnabled
     */
    public boolean containsSstEnabled() {
        return map.containsKey(SST_ENABLED);
    }

    /**
     * remove the field sstEnabled
     */
    public JSTSqlTemplateInfo unsetSstEnabled() {
        map.remove(SST_ENABLED);
        return this;
    }

    public boolean isSstGroupIndex(int flag) {
        Integer i = getSstGroupIndex();
        return i != null && ((i & flag) == flag);
    }

    @Override
    public String toString() {
        String n = getSstName();
        if (n != null) {
            return n;
        }
        return super.toString();
    }
}