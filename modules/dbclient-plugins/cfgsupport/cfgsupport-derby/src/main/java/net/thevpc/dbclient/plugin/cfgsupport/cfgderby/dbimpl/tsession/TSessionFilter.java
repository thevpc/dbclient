package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsession;

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
public class TSessionFilter {
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

    public TSessionFilter setString(int pos, String value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.VARCHAR);
        params.put(new Integer(pos), p);
        return this;
    }

    public TSessionFilter setDouble(int pos, double value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.DOUBLE);
        params.put(new Integer(pos), p);
        return this;
    }

    public TSessionFilter setInt(int pos, int value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.INTEGER);
        params.put(new Integer(pos), p);
        return this;
    }

    public TSessionFilter setBoolean(int pos, boolean value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.BOOLEAN);
        params.put(new Integer(pos), p);
        return this;
    }

    public TSessionFilter setFloat(int pos, float value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.FLOAT);
        params.put(new Integer(pos), p);
        return this;
    }

    public TSessionFilter setDate(int pos, java.sql.Date value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.DATE);
        params.put(new Integer(pos), p);
        return this;
    }

    public TSessionFilter setTime(int pos, java.sql.Time value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.TIME);
        params.put(new Integer(pos), p);
        return this;
    }

    public TSessionFilter setTimestamp(int pos, java.sql.Timestamp value) {
        SqlParam p = new SqlParam();
        p.setPos(pos);
        p.setValue(value);
        p.setSqlType(Types.TIMESTAMP);
        params.put(new Integer(pos), p);
        return this;
    }

    public TSessionFilter setLong(int pos, long value) {
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

    public TSessionFilter merge(TSessionFilter other) {
        if (other == null) {
            return this;
        }
        TSessionFilter newCriteria = new TSessionFilter();

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

    public TSessionFilter orderBy(String orderColumn, boolean asc) {
        orderFields.add(orderColumn);
        orderAsc.add(asc ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public TSessionFilter orderBySesId(boolean asc) {
        orderBy(TSessionPropertyList.SES_ID, asc);
        return this;
    }

    public TSessionFilter orderBySesName(boolean asc) {
        orderBy(TSessionPropertyList.SES_NAME, asc);
        return this;
    }

    public TSessionFilter orderBySesPath(boolean asc) {
        orderBy(TSessionPropertyList.SES_PATH, asc);
        return this;
    }

    public TSessionFilter orderBySesIndex(boolean asc) {
        orderBy(TSessionPropertyList.SES_INDEX, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxUrl(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_URL, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxDriver(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_DRIVER, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxLogin(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_LOGIN, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxPassword(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_PASSWORD, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxAutocommit(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_AUTOCOMMIT, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxHoldability(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_HOLDABILITY, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxTransIsolation(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_TRANS_ISOLATION, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxReadOnly(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_READ_ONLY, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxOpenScript(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_OPEN_SCRIPT, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxCloseScript(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_CLOSE_SCRIPT, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxCreated(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_CREATED, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxLastUpdated(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_LAST_UPDATED, asc);
        return this;
    }

    public TSessionFilter orderBySesCnxFactoryName(boolean asc) {
        orderBy(TSessionPropertyList.SES_CNX_FACTORY_NAME, asc);
        return this;
    }

    public TSessionFilter orderBySesAutoConnect(boolean asc) {
        orderBy(TSessionPropertyList.SES_AUTO_CONNECT, asc);
        return this;
    }

    public TSessionFilter orderBySesDesc(boolean asc) {
        orderBy(TSessionPropertyList.SES_DESC, asc);
        return this;
    }

    public TSessionFilter setAll(TSessionDTO prototype) throws SQLException {
        // building criteria
        if (prototype != null && prototype.size() > 0) {
            StringBuffer whereClause = new StringBuffer();
            int pos = params.size() + 1;
            for (Iterator i = prototype.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case -1703376515: {  //field sesCnxLogin
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxLogin();
                        whereClause.append("T_SESSION.SES_CNX_LOGIN = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 1983877772: {  //field sesName
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesName();
                        whereClause.append("T_SESSION.SES_NAME = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 1578327522: {  //column SES_CNX_LAST_UPDATED
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.sql.Date columnValue = prototype.getSesCnxLastUpdated();
                        whereClause.append("T_SESSION.SES_CNX_LAST_UPDATED = ?");
                        setDate(pos++, columnValue);
                        break;

                    }
                    case -97993273: {  //column SES_CNX_TRANS_ISOLATION
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesCnxTransIsolation();
                        whereClause.append("T_SESSION.SES_CNX_TRANS_ISOLATION = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case -1569337598: {  //column SES_CNX_CLOSE_SCRIPT
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxCloseScript();
                        whereClause.append("T_SESSION.SES_CNX_CLOSE_SCRIPT = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -1832342439: {  //field sesCnxLastUpdated
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.sql.Date columnValue = prototype.getSesCnxLastUpdated();
                        whereClause.append("T_SESSION.SES_CNX_LAST_UPDATED = ?");
                        setDate(pos++, columnValue);
                        break;

                    }
                    case 1920628383: {  //field sesCnxHoldability
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesCnxHoldability();
                        whereClause.append("T_SESSION.SES_CNX_HOLDABILITY = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case -851990416: {  //column SES_CNX_OPEN_SCRIPT
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxOpenScript();
                        whereClause.append("T_SESSION.SES_CNX_OPEN_SCRIPT = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -2096043063: {  //column SES_NAME
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesName();
                        whereClause.append("T_SESSION.SES_NAME = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 123921404: {  //field sesCnxCreated
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.sql.Date columnValue = prototype.getSesCnxCreated();
                        whereClause.append("T_SESSION.SES_CNX_CREATED = ?");
                        setDate(pos++, columnValue);
                        break;

                    }
                    case -2067964200: {  //column SES_CNX_CREATED
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.sql.Date columnValue = prototype.getSesCnxCreated();
                        whereClause.append("T_SESSION.SES_CNX_CREATED = ?");
                        setDate(pos++, columnValue);
                        break;

                    }
                    case -426140475: {  //column SES_CNX_READ_ONLY
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesCnxReadOnly();
                        whereClause.append("T_SESSION.SES_CNX_READ_ONLY = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case 521217375: {  //column SES_CNX_URL
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxUrl();
                        whereClause.append("T_SESSION.SES_CNX_URL = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 1153462222: {  //field sesCnxReadOnly
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesCnxReadOnly();
                        whereClause.append("T_SESSION.SES_CNX_READ_ONLY = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case -546658640: {  //column SES_CNX_FACTORY_NAME
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxFactoryName();
                        whereClause.append("T_SESSION.SES_CNX_FACTORY_NAME = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -1009572458: {  //column SES_CNX_AUTOCOMMIT
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesCnxAutocommit();
                        whereClause.append("T_SESSION.SES_CNX_AUTOCOMMIT = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case -1852458535: {  //column SES_ID
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesId();
                        whereClause.append("T_SESSION.SES_ID = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case 970193911: {  //field sesCnxCloseScript
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxCloseScript();
                        whereClause.append("T_SESSION.SES_CNX_CLOSE_SCRIPT = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -1491255148: {  //field sesCnxDriver
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxDriver();
                        whereClause.append("T_SESSION.SES_CNX_DRIVER = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 1983583890: {  //field sesDesc
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesDesc();
                        whereClause.append("T_SESSION.SES_DESC = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 1983937574: {  //field sesPath
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesPath();
                        whereClause.append("T_SESSION.SES_PATH = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -285812597: {  //column SES_CNX_PASSWORD
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxPassword();
                        whereClause.append("T_SESSION.SES_CNX_PASSWORD = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 1430764920: {  //column SES_AUTO_CONNECT
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesAutoConnect();
                        whereClause.append("T_SESSION.SES_AUTO_CONNECT = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case -2096336945: {  //column SES_DESC
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesDesc();
                        whereClause.append("T_SESSION.SES_DESC = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 793343512: {  //column SES_CNX_DRIVER
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxDriver();
                        whereClause.append("T_SESSION.SES_CNX_DRIVER = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -2095983261: {  //column SES_PATH
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesPath();
                        whereClause.append("T_SESSION.SES_PATH = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 1314149658: {  //field sesAutoConnect
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesAutoConnect();
                        whereClause.append("T_SESSION.SES_AUTO_CONNECT = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case 1328944673: {  //field sesCnxOpenScript
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxOpenScript();
                        whereClause.append("T_SESSION.SES_CNX_OPEN_SCRIPT = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -557064396: {  //column SES_INDEX
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesIndex();
                        whereClause.append("T_SESSION.SES_INDEX = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case -761539453: {  //field sesCnxUrl
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxUrl();
                        whereClause.append("T_SESSION.SES_CNX_URL = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 1981094779: {  //column SES_CNX_HOLDABILITY
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesCnxHoldability();
                        whereClause.append("T_SESSION.SES_CNX_HOLDABILITY = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case 997795186: {  //field sesCnxTransIsolation
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesCnxTransIsolation();
                        whereClause.append("T_SESSION.SES_CNX_TRANS_ISOLATION = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case 890258889: {  //field sesCnxFactoryName
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxFactoryName();
                        whereClause.append("T_SESSION.SES_CNX_FACTORY_NAME = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case 109326684: {  //field sesId
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesId();
                        whereClause.append("T_SESSION.SES_ID = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case 1066686994: {  //field sesCnxAutocommit
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesCnxAutocommit();
                        whereClause.append("T_SESSION.SES_CNX_AUTOCOMMIT = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case 1366429937: {  //field sesIndex
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        int columnValue = prototype.getSesIndex();
                        whereClause.append("T_SESSION.SES_INDEX = ?");
                        setInt(pos++, columnValue);
                        break;

                    }
                    case -1056835577: {  //field sesCnxPassword
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxPassword();
                        whereClause.append("T_SESSION.SES_CNX_PASSWORD = ?");
                        setString(pos++, columnValue);
                        break;

                    }
                    case -1629679783: {  //column SES_CNX_LOGIN
                        if (whereClause.length() > 1) {
                            whereClause.append(" AND ");
                        }
                        java.lang.String columnValue = prototype.getSesCnxLogin();
                        whereClause.append("T_SESSION.SES_CNX_LOGIN = ?");
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
        // START object-definition.do<name=TSession>.userCode.findAll
        // code retreived from object-definition.do<name=TSession>.userCode.findAll
        // END   object-definition.do<name=TSession>.userCode.findAll

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