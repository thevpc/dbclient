package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tconfigprop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TConfigPropDAO {
    private Connection connection;
    private String callerPrincipalName;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection value) {
        this.connection = value;
    }

    public String getCallerPrincipalName() {
        return callerPrincipalName;
    }

    public void setCallerPrincipalName(String value) {
        this.callerPrincipalName = value;
    }

    /**
     * DataAccessObject Constructor
     */
    public TConfigPropDAO() {
        super();
    }

    /**
     * DataAccessObject Constructor
     */
    public TConfigPropDAO(Connection con) {
        super();
        setConnection(con);
    }

    /**
     * DataAccessObject Constructor
     *
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TConfigPropKey insert(TConfigPropDTO data) throws SQLException {
        // START Prologue initialization
        java.lang.String tcpName = null;
        java.lang.String tcpValue = null;
        // END   Prologue initialization
        // START Prologue checking
        if (!data.containsTcpName()) {
            throw new SQLException("RequiredFieldOnInsertException tcpName");
        }
        // END  Prologue checking

        Connection _conn_ = null;
        String _statement_ = null;
        PreparedStatement _prepStmt_ = null;
        try {
            _conn_ = getConnection();
            tcpName = (data.getTcpName());

            // START object-definition.do<name=TConfigProp>.userCode.preInsert
            // code retreived from object-definition.do<name=TConfigProp>.userCode.preInsert
            // END   object-definition.do<name=TConfigProp>.userCode.preInsert
            // START Local Fields Updates
            for (Iterator i = data.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case -1483614708: {  //field tcpName
                        // START object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.preInsert
                        // END   object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.preInsert
                        tcpName = (data.getTcpName());
                        // START object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.postInsert
                        // END   object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.postInsert
                        break;
                    }
                    case 88910675: {  //column TCP_VALUE
                        // START object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.preInsert
                        // code retreived from object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.preInsert
                        // END   object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.preInsert
                        tcpValue = (data.getTcpValue());
                        // START object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.postInsert
                        // code retreived from object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.postInsert
                        // END   object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.postInsert
                        break;
                    }
                    case 2080839753: {  //column TCP_NAME
                        // START object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.preInsert
                        // END   object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.preInsert
                        tcpName = (data.getTcpName());
                        // START object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.postInsert
                        // END   object-definition.do<name=TConfigProp>.field<name=tcpName>.userCode.postInsert
                        break;
                    }
                    case 1259972112: {  //field tcpValue
                        // START object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.preInsert
                        // code retreived from object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.preInsert
                        // END   object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.preInsert
                        tcpValue = (data.getTcpValue());
                        // START object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.postInsert
                        // code retreived from object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.postInsert
                        // END   object-definition.do<name=TConfigProp>.field<name=tcpValue>.userCode.postInsert
                        break;
                    }
                    default: {
                        throw new SQLException("UnknownFieldException" + selectedFieldName);
                    }
                }
            }
            // END   Local Fields Updates

            // START Database persistance
            _statement_ = "INSERT INTO T_CONFIG_PROP(TCP_NAME, TCP_VALUE) VALUES (?, ?)";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setString(1, tcpName);
            _prepStmt_.setString(2, tcpValue);
            _prepStmt_.executeUpdate();
            _prepStmt_.close();

            // START object-definition.do<name=TConfigProp>.userCode.postInsert
            // code retreived from object-definition.do<name=TConfigProp>.userCode.postInsert
            // END   object-definition.do<name=TConfigProp>.userCode.postInsert
            // END   Database persistance

            // returning Identifier;
            return data.getTConfigPropKey();
        } catch (SQLException sqlExcp) {
            sqlExcp.printStackTrace();
            throw new SQLException("CreateDataException " + sqlExcp);
        } catch (RuntimeException rtmExcp) {
            rtmExcp.printStackTrace();
            throw rtmExcp;
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public void update(TConfigPropDTO data) throws SQLException {
        if (data.size() == 0) {
            return;
        }
        // START Prologue Cheking
        // END   ForbiddenFieldOnUpdate Cheking

        // START object-definition.do<name=TConfigProp>.userCode.preUpdate
        // code retreived from object-definition.do<name=TConfigProp>.userCode.preUpdate
        // END   object-definition.do<name=TConfigProp>.userCode.preUpdate
        Connection _conn_;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer();
            int _ucount_;
            PreparedStatement _prepStmt_ = null;
            boolean _firstColumn_ = true;
            _statement_.append("UPDATE T_CONFIG_PROP SET ");
            if (data.containsTcpValue()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("TCP_VALUE=? ");
            }
            _statement_.append(" WHERE TCP_NAME = ? ");
            _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int _pos_ = 0;
            if (data.containsTcpValue()) {
                _prepStmt_.setString(++_pos_, data.getTcpValue());
            }
            _prepStmt_.setString(++_pos_, data.getTcpName());
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("UpdateDataException");
            }
            // START object-definition.do<name=TConfigProp>.userCode.postUpdate
            // code retreived from object-definition.do<name=TConfigProp>.userCode.postUpdate
            // END   object-definition.do<name=TConfigProp>.userCode.postUpdate
        } catch (SQLException sqlExcp) {
            throw new SQLException("UpdateDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public void delete(TConfigPropKey key) throws SQLException {
        Connection _conn_;
        try {
            _conn_ = getConnection();
            // START object-definition.do<name=TConfigProp>.userCode.preDelete
            // code retreived from object-definition.do<name=TConfigProp>.userCode.preDelete
            // END   object-definition.do<name=TConfigProp>.userCode.preDelete
            int _ucount_;
            String _statement_;
            PreparedStatement _prepStmt_;
            _statement_ = "DELETE FROM T_CONFIG_PROP WHERE TCP_NAME = ? ";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setString(1, (key.getTcpName()));
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("RemoveDataException");
            }
            // START object-definition.do<name=TConfigProp>.userCode.postDelete
            // code retreived from object-definition.do<name=TConfigProp>.userCode.postDelete
            // END   object-definition.do<name=TConfigProp>.userCode.postDelete
        } catch (SQLException sqlExcp) {
            throw new SQLException("RemoveDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public int delete(TConfigPropFilter criteria) throws SQLException {
        PreparedStatement _prepStmt_ = null;
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("DELETE FROM T_CONFIG_PROP");
            if (criteria != null && criteria.getJoins() != null) {
                _statement_.append(" ");
                _statement_.append(criteria.getJoins());
            }
            if (criteria != null && criteria.getWhereClause() != null) {
                _statement_.append(" WHERE ");
                _statement_.append(criteria.getWhereClause());
            }
            ArrayList list = new ArrayList();
            _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int $statementParamPos$ = 1;
            if (criteria != null) {
                criteria.populateStatement(_prepStmt_, $statementParamPos$);
            }
            return _prepStmt_.executeUpdate();
        } catch (SQLException sqlExcp) {
            throw new SQLException("DataRetrievalException " + sqlExcp);
        } finally {
            if (_prepStmt_ != null) {
                _prepStmt_.close();
            }
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TConfigPropDTO select(TConfigPropPropertyList propertyList, TConfigPropKey primaryKey) throws SQLException {
        String where = "T_CONFIG_PROP.TCP_NAME = ?";
        TConfigPropFilter criteria = new TConfigPropFilter();
        criteria.setWhereClause(where);
        criteria.setString(1, primaryKey.getTcpName());
        TConfigPropDTO[] _found_ = select(propertyList, criteria);
        if (_found_.length > 0) {
            return _found_[0];
        }
        return null;

    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TConfigPropDTO[] select(TConfigPropPropertyList propertyList, TConfigPropFilter criteria) throws SQLException {
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("SELECT ");
            if (criteria != null && criteria.isDistinct()) {
                _statement_.append("DISTINCT ");
            }
            if (propertyList == null) {
                _statement_.append("(T_CONFIG_PROP.TCP_NAME),(T_CONFIG_PROP.TCP_NAME),(T_CONFIG_PROP.TCP_VALUE)");
            } else {
                StringBuffer sb = new StringBuffer("TCP_NAME");
                for (Iterator i = propertyList.keyIterator(); i.hasNext();) {
                    String selectedFieldName = (String) i.next();
                    int selectedFieldId = selectedFieldName.hashCode();
                    switch (selectedFieldId) {
                        case -1483614708: {  //field tcpName
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CONFIG_PROP.TCP_NAME");

                            break;
                        }
                        case 88910675: {  //column TCP_VALUE
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CONFIG_PROP.TCP_VALUE");

                            break;
                        }
                        case 2080839753: {  //column TCP_NAME
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CONFIG_PROP.TCP_NAME");

                            break;
                        }
                        case 1259972112: {  //field tcpValue
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CONFIG_PROP.TCP_VALUE");

                            break;
                        }
                        default: {
                            // default
                        }
                    }
                }
                _statement_.append(sb.toString());
            }
            _statement_.append(" FROM T_CONFIG_PROP");
            if (criteria != null && criteria.getJoins() != null) {
                _statement_.append(" ");
                _statement_.append(criteria.getJoins());
            }
            if (criteria != null && criteria.getWhereClause() != null) {
                _statement_.append(" WHERE ");
                _statement_.append(criteria.getWhereClause());
            }
            if (criteria != null && criteria.getOrderCount() > 0) {
                _statement_.append(" ORDER BY ");
                boolean orderFirst = true;
                int orderIteratorIndex = 0;
                for (Iterator i = criteria.getOrderIterator(); i.hasNext();) {
                    boolean asc = criteria.isOrderAscendent(orderIteratorIndex++);
                    String selectedFieldName = (String) i.next();
                    int selectedFieldId = selectedFieldName.hashCode();
                    switch (selectedFieldId) {
                        case -1483614708: {  //field tcpName
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CONFIG_PROP.TCP_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 88910675: {  //column TCP_VALUE
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CONFIG_PROP.TCP_VALUE");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 2080839753: {  //column TCP_NAME
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CONFIG_PROP.TCP_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1259972112: {  //field tcpValue
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CONFIG_PROP.TCP_VALUE");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        default: {
                            //WHEN UNKNOWN FIELD PASSED AS IS TO SQL
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append(selectedFieldName);
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                    }
                }
            }
            ArrayList list = new ArrayList();
            PreparedStatement _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int $min$ = -1;
            int $max$ = -1;
            int $statementParamPos$ = 1;
            if (criteria != null) {
                criteria.populateStatement(_prepStmt_, $statementParamPos$);
                $min$ = criteria.getMinRowIndex();
                $max$ = criteria.getMaxRowIndex();
            }
            ResultSet _rs_ = _prepStmt_.executeQuery();
            int $count$ = 0;
            while ($count$ < $min$ && _rs_.next()) {
                $count$++;
            }
            if (propertyList == null) {
                while (($max$ < 0 || $count$ <= $max$) && _rs_.next()) {
                    $count$++;
                    TConfigPropKey $tableKey$ = new TConfigPropKey(_rs_.getString(1));
                    TConfigPropDTO data = new TConfigPropDTO();
                    data.setTcpName(_rs_.getString(2));
                    data.setTcpValue(_rs_.getString(3));
                    list.add(data);
                }
            } else {
                while (($max$ < 0 || $count$ <= $max$) && _rs_.next()) {
                    $count$++;
                    int $col$ = 2;
                    TConfigPropKey $tableKey$ = new TConfigPropKey(_rs_.getString(1));
                    TConfigPropDTO data = new TConfigPropDTO();
                    for (Iterator i = propertyList.keySet().iterator(); i.hasNext();) {
                        String selectedFieldName = (String) i.next();
                        int selectedFieldId = selectedFieldName.hashCode();
                        switch (selectedFieldId) {
                            case -1483614708: {  //field tcpName
                                data.setTcpName(_rs_.getString($col$++));

                                break;
                            }
                            case 88910675: {  //column TCP_VALUE
                                data.setTcpValue(_rs_.getString($col$++));

                                break;
                            }
                            case 2080839753: {  //column TCP_NAME
                                data.setTcpName(_rs_.getString($col$++));

                                break;
                            }
                            case 1259972112: {  //field tcpValue
                                data.setTcpValue(_rs_.getString($col$++));

                                break;
                            }
                            default: {
                                // default
                            }
                        }
                    }
                    list.add(data);
                }
            }
            _rs_.close();
            _prepStmt_.close();
            return (TConfigPropDTO[]) list.toArray(new TConfigPropDTO[list.size()]);
        } catch (SQLException sqlExcp) {
            throw new SQLException("DataRetrievalException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TConfigPropDTO[] select(TConfigPropPropertyList propertyList) throws SQLException {
        return select(propertyList, (TConfigPropFilter) null);
    }

}