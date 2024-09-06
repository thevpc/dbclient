package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tmessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TMessageFilter {
    private String whereClause;
    private String joins;
    private boolean distinct;
    private int minRowIndex = -1;
    private int maxRowIndex = -1;
    private ArrayList orderFields = new ArrayList();
    private ArrayList orderAsc = new ArrayList();
    private HashMap params = new HashMap();

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String value) {
        this.whereClause = value;
    }

    public String getJoins() {
        return joins;
    }

    public void setJoins(String value) {
        this.joins = value;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean value) {
        this.distinct = value;
    }

    public int getMinRowIndex() {
        return minRowIndex;
    }

    public void setMinRowIndex(int value) {
        this.minRowIndex = value;
    }

    public int getMaxRowIndex() {
        return maxRowIndex;
    }

    public void setMaxRowIndex(int value) {
        this.maxRowIndex = value;
    }

    public Iterator getOrderIterator() {
        return orderFields.iterator();
    }

    public int getOrderCount() {
        return orderFields.size();
    }

    public boolean isOrderAscendent(int index) {
        return ((Boolean) orderAsc.get(index)).booleanValue();
    }

    public TMessageFilter setString(int pos, String value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.VARCHAR);
        params.put(new Integer(pos), p);
        return this;
    }

    public TMessageFilter setDouble(int pos, double value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.DOUBLE);
        params.put(new Integer(pos), p);
        return this;
    }

    public TMessageFilter setInt(int pos, int value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.INTEGER);
        params.put(new Integer(pos), p);
        return this;
    }

    public TMessageFilter setBoolean(int pos, boolean value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.BOOLEAN);
        params.put(new Integer(pos), p);
        return this;
    }

    public TMessageFilter setFloat(int pos, float value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.FLOAT);
        params.put(new Integer(pos), p);
        return this;
    }

    public TMessageFilter setDate(int pos, java.sql.Date value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.DATE);
        params.put(new Integer(pos), p);
        return this;
    }

    public TMessageFilter setTime(int pos, java.sql.Time value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.TIME);
        params.put(new Integer(pos), p);
        return this;
    }

    public TMessageFilter setTimestamp(int pos, java.sql.Timestamp value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.TIMESTAMP);
        params.put(new Integer(pos), p);
        return this;
    }

    public TMessageFilter setLong(int pos, long value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.BIGINT);
        params.put(new Integer(pos), p);
        return this;
    }

    public int populateStatement(PreparedStatement ps, int startPosition) throws SQLException {
        int count = 0;
        for (Iterator i = params.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            count += ((SqlParam) entry.getValue()).populateStatement(ps, startPosition);
        }
        return count;
    }

    public TMessageFilter merge(TMessageFilter other) {
        if (other == null) {
            return this;
        }
        TMessageFilter newCriteria = new TMessageFilter();

        StringBuffer sb = new StringBuffer();
        if (whereClause != null) {
            sb.append(whereClause);
        }
        if (other.whereClause != null) {
            if (sb.length() > 0) {
                sb.append(" AND ");
            }
            sb.append(other.whereClause);
        }
        newCriteria.whereClause = sb.length() == 0 ? null : sb.toString();

        sb = new StringBuffer();
        if (joins != null) {
            sb.append(joins);
        }
        if (other.joins != null) {
            sb.append(" ");
            sb.append(other.joins);
        }
        newCriteria.joins = sb.length() == 0 ? null : sb.toString();
        int pos = 1;
        for (int i = 0; i < params.size(); i++) {
            newCriteria.params.put(new Integer(pos), params.get(new Integer(i + 1)));
            pos++;
        }
        for (int i = 0; i < other.params.size(); i++) {
            SqlParam p = (SqlParam) other.params.get(new Integer(i + 1));
            p.setPos(pos);
            newCriteria.params.put(new Integer(pos), p);
            pos++;
        }
        if (minRowIndex >= 0) {
            newCriteria.minRowIndex = minRowIndex;
        }
        if (other.minRowIndex >= 0) {
            newCriteria.minRowIndex = other.minRowIndex;
        }
        if (maxRowIndex >= 0) {
            newCriteria.maxRowIndex = maxRowIndex;
        }
        if (other.maxRowIndex >= 0) {
            newCriteria.maxRowIndex = other.maxRowIndex;
        }
        if (distinct || other.isDistinct()) {
            newCriteria.distinct = other.distinct;
        }
        return newCriteria;
    }

    public TMessageFilter orderBy(String orderColumn, boolean asc) {
        orderFields.add(orderColumn);
        orderAsc.add(asc ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public TMessageFilter orderByMsgId(boolean asc) {
        orderBy(TMessagePropertyList.MSG_ID, asc);
        return this;
    }

    public TMessageFilter orderByMsgLocName(boolean asc) {
        orderBy(TMessagePropertyList.MSG_LOC_NAME, asc);
        return this;
    }

    public TMessageFilter orderByMsgData(boolean asc) {
        orderBy(TMessagePropertyList.MSG_DATA, asc);
        return this;
    }

    public TMessageFilter setAll(TMessageDTO prototype) throws SQLException {
        // building criteria
        if (prototype != null && prototype.size() > 0) {
            StringBuffer whereClause = new StringBuffer();
            int pos = params.size() + 1;
            for (Iterator i = prototype.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case -520642774: {  //field msgLocName
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getMsgLocName();
                        whereClause.append("T_MESSAGE.MSG_LOC_NAME = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -471701048: {  //column MSG_DATA
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getMsgData();
                        whereClause.append("T_MESSAGE.MSG_DATA = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 1343251147: {  //field msgData
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getMsgData();
                        whereClause.append("T_MESSAGE.MSG_DATA = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -34489496: {  //column MSG_LOC_NAME
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getMsgLocName();
                        whereClause.append("T_MESSAGE.MSG_LOC_NAME = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -2011661639: {  //column MSG_ID
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getMsgId();
                        whereClause.append("T_MESSAGE.MSG_ID = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 104191100: {  //field msgId
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getMsgId();
                        whereClause.append("T_MESSAGE.MSG_ID = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    default: {
                        throw new SQLException("UnknownFieldException " + selectedFieldName);

                    }
                }
            }
            setWhereClause(whereClause.toString());
        }
        // START object-definition.do<name=TMessage>.userCode.findAll
        // code retreived from object-definition.do<name=TMessage>.userCode.findAll
        // END   object-definition.do<name=TMessage>.userCode.findAll

        return this;

    }

    private static class SqlParam {
        private int pos;
        private String name;
        private Object value;
        private int sqlType;

        public int getPos() {
            return pos;
        }

        public void setPos(int value) {
            this.pos = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public int getSqlType() {
            return sqlType;
        }

        public void setSqlType(int value) {
            this.sqlType = value;
        }

        public int populateStatement(PreparedStatement ps, int startPosition) throws SQLException {
            int rpos = pos + startPosition - 1;
            if (value == null) {
                ps.setNull(rpos, sqlType);
                return 1;
            }
            switch (sqlType) {
                case Types.VARCHAR: {
                    ps.setString(rpos, (String) value);
                    break;
                }
                case Types.INTEGER: {
                    ps.setInt(rpos, ((Integer) value).intValue());
                    break;
                }
                case Types.DOUBLE: {
                    ps.setDouble(rpos, ((Double) value).doubleValue());
                    break;
                }
                case Types.SMALLINT: {
                    ps.setShort(rpos, ((Short) value).shortValue());
                    break;
                }
                case Types.TIME: {
                    ps.setTime(rpos, ((Time) value));
                    break;
                }
                case Types.TIMESTAMP: {
                    ps.setTimestamp(rpos, ((Timestamp) value));
                    break;
                }
                case Types.BIGINT: {
                    ps.setLong(rpos, ((Long) value).longValue());
                    break;
                }
                case Types.FLOAT: {
                    ps.setFloat(rpos, ((Float) value).floatValue());
                    break;
                }
                case Types.DATE: {
                    ps.setDate(rpos, ((java.sql.Date) value));
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unhandled sql type " + sqlType);
                }
            }
            return 1;
        }

    }
}