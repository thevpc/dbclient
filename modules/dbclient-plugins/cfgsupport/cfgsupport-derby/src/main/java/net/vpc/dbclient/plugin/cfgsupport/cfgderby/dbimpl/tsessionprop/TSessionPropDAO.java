package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsessionprop;

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
public class TSessionPropDAO {
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
    public TSessionPropDAO() {
        super();
    }

    /**
     * DataAccessObject Constructor
     */
    public TSessionPropDAO(Connection con) {
        super();
        setConnection(con);
    }

    /**
     * DataAccessObject Constructor
     *
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TSessionPropKey insert(TSessionPropDTO data) throws SQLException {
        // START Prologue initialization
        java.lang.String tspName = null;
        int tspSesId = 0;
        java.lang.String tspValue = null;
        // END   Prologue initialization
        // START Prologue checking
        if (!data.containsTspName()) {
            throw new SQLException("RequiredFieldOnInsertException tspName");
        }
        if (!data.containsTspSesId()) {
            throw new SQLException("RequiredFieldOnInsertException tspSesId");
        }
        // END  Prologue checking

        Connection _conn_ = null;
        String _statement_ = null;
        PreparedStatement _prepStmt_ = null;
        try {
            _conn_ = getConnection();
            tspName = (data.getTspName());
            tspSesId = (data.getTspSesId());

            // START object-definition.do<name=TSessionProp>.userCode.preInsert
            // code retreived from object-definition.do<name=TSessionProp>.userCode.preInsert
            // END   object-definition.do<name=TSessionProp>.userCode.preInsert
            // START Local Fields Updates
            for (Iterator i = data.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case -1722484213: {  //field tspSesId
                        // START object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.preInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.preInsert
                        tspSesId = (data.getTspSesId());
                        // START object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.postInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.postInsert
                        break;
                    }
                    case -1025548292: {  //field tspName
                        // START object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.preInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.preInsert
                        tspName = (data.getTspName());
                        // START object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.postInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.postInsert
                        break;
                    }
                    case -2090895037: {  //column TSP_VALUE
                        // START object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.preInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.preInsert
                        tspValue = (data.getTspValue());
                        // START object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.postInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.postInsert
                        break;
                    }
                    case -475211737: {  //column TSP_SES_ID
                        // START object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.preInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.preInsert
                        tspSesId = (data.getTspSesId());
                        // START object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.postInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspSesId>.userCode.postInsert
                        break;
                    }
                    case -898970535: {  //column TSP_NAME
                        // START object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.preInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.preInsert
                        tspName = (data.getTspName());
                        // START object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.postInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspName>.userCode.postInsert
                        break;
                    }
                    case -1719838176: {  //field tspValue
                        // START object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.preInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.preInsert
                        tspValue = (data.getTspValue());
                        // START object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.postInsert
                        // END   object-definition.do<name=TSessionProp>.field<name=tspValue>.userCode.postInsert
                        break;
                    }
                    default: {
                        throw new SQLException("UnknownFieldException" + selectedFieldName);
                    }
                }
            }
            // END   Local Fields Updates

            // START Database persistance
            _statement_ = "INSERT INTO T_SESSION_PROP(TSP_NAME, TSP_SES_ID, TSP_VALUE) VALUES (?, ?, ?)";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setString(1, tspName);
            _prepStmt_.setInt(2, tspSesId);
            _prepStmt_.setString(3, tspValue);
            _prepStmt_.executeUpdate();
            _prepStmt_.close();

            // START object-definition.do<name=TSessionProp>.userCode.postInsert
            // code retreived from object-definition.do<name=TSessionProp>.userCode.postInsert
            // END   object-definition.do<name=TSessionProp>.userCode.postInsert
            // END   Database persistance

            // returning Identifier;
            return data.getTSessionPropKey();
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
    public void update(TSessionPropDTO data) throws SQLException {
        if (data.size() == 0) {
            return;
        }
        // START Prologue Cheking
        // END   ForbiddenFieldOnUpdate Cheking

        // START object-definition.do<name=TSessionProp>.userCode.preUpdate
        // code retreived from object-definition.do<name=TSessionProp>.userCode.preUpdate
        // END   object-definition.do<name=TSessionProp>.userCode.preUpdate
        Connection _conn_;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer();
            int _ucount_;
            PreparedStatement _prepStmt_ = null;
            boolean _firstColumn_ = true;
            _statement_.append("UPDATE T_SESSION_PROP SET ");
            if (data.containsTspValue()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("TSP_VALUE=? ");
            }
            _statement_.append(" WHERE TSP_SES_ID = ?  AND TSP_NAME = ? ");
            _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int _pos_ = 0;
            if (data.containsTspValue()) {
                _prepStmt_.setString(++_pos_, data.getTspValue());
            }
            _prepStmt_.setInt(++_pos_, data.getTspSesId());
            _prepStmt_.setString(++_pos_, data.getTspName());
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("UpdateDataException");
            }
            // START object-definition.do<name=TSessionProp>.userCode.postUpdate
            // code retreived from object-definition.do<name=TSessionProp>.userCode.postUpdate
            // END   object-definition.do<name=TSessionProp>.userCode.postUpdate
        } catch (SQLException sqlExcp) {
            throw new SQLException("UpdateDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public void delete(TSessionPropKey key) throws SQLException {
        Connection _conn_;
        try {
            _conn_ = getConnection();
            // START object-definition.do<name=TSessionProp>.userCode.preDelete
            // code retreived from object-definition.do<name=TSessionProp>.userCode.preDelete
            // END   object-definition.do<name=TSessionProp>.userCode.preDelete
            int _ucount_;
            String _statement_;
            PreparedStatement _prepStmt_;
            _statement_ = "DELETE FROM T_SESSION_PROP WHERE TSP_SES_ID = ?  AND TSP_NAME = ? ";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setInt(1, (key.getTspSesId()));
            _prepStmt_.setString(2, (key.getTspName()));
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("RemoveDataException");
            }
            // START object-definition.do<name=TSessionProp>.userCode.postDelete
            // code retreived from object-definition.do<name=TSessionProp>.userCode.postDelete
            // END   object-definition.do<name=TSessionProp>.userCode.postDelete
        } catch (SQLException sqlExcp) {
            throw new SQLException("RemoveDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public int delete(TSessionPropFilter criteria) throws SQLException {
        PreparedStatement _prepStmt_ = null;
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("DELETE FROM T_SESSION_PROP");
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
    public TSessionPropDTO select(TSessionPropPropertyList propertyList, TSessionPropKey primaryKey) throws SQLException {
        String where = "T_SESSION_PROP.TSP_NAME = ? AND T_SESSION_PROP.TSP_SES_ID = ?";
        TSessionPropFilter criteria = new TSessionPropFilter();
        criteria.setWhereClause(where);
        criteria.setString(1, primaryKey.getTspName());
        criteria.setInt(2, primaryKey.getTspSesId());
        TSessionPropDTO[] _found_ = select(propertyList, criteria);
        if (_found_.length > 0) {
            return _found_[0];
        }
        return null;

    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TSessionPropDTO[] select(TSessionPropPropertyList propertyList, TSessionPropFilter criteria) throws SQLException {
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("SELECT ");
            if (criteria != null && criteria.isDistinct()) {
                _statement_.append("DISTINCT ");
            }
            if (propertyList == null) {
                _statement_.append("(T_SESSION_PROP.TSP_NAME),(T_SESSION_PROP.TSP_SES_ID),(T_SESSION_PROP.TSP_NAME),(T_SESSION_PROP.TSP_SES_ID),(T_SESSION_PROP.TSP_VALUE)");
            } else {
                StringBuffer sb = new StringBuffer("TSP_NAME,TSP_SES_ID");
                for (Iterator i = propertyList.keyIterator(); i.hasNext();) {
                    String selectedFieldName = (String) i.next();
                    int selectedFieldId = selectedFieldName.hashCode();
                    switch (selectedFieldId) {
                        case -1722484213: {  //field tspSesId
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION_PROP.TSP_SES_ID");

                            break;
                        }
                        case -1025548292: {  //field tspName
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION_PROP.TSP_NAME");

                            break;
                        }
                        case -2090895037: {  //column TSP_VALUE
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION_PROP.TSP_VALUE");

                            break;
                        }
                        case -475211737: {  //column TSP_SES_ID
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION_PROP.TSP_SES_ID");

                            break;
                        }
                        case -898970535: {  //column TSP_NAME
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION_PROP.TSP_NAME");

                            break;
                        }
                        case -1719838176: {  //field tspValue
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION_PROP.TSP_VALUE");

                            break;
                        }
                        default: {
                            // default
                        }
                    }
                }
                _statement_.append(sb.toString());
            }
            _statement_.append(" FROM T_SESSION_PROP");
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
                        case -1722484213: {  //field tspSesId
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION_PROP.TSP_SES_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1025548292: {  //field tspName
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION_PROP.TSP_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -2090895037: {  //column TSP_VALUE
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION_PROP.TSP_VALUE");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -475211737: {  //column TSP_SES_ID
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION_PROP.TSP_SES_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -898970535: {  //column TSP_NAME
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION_PROP.TSP_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1719838176: {  //field tspValue
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION_PROP.TSP_VALUE");
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
                    TSessionPropKey $tableKey$ = new TSessionPropKey(_rs_.getString(1), _rs_.getInt(2));
                    TSessionPropDTO data = new TSessionPropDTO();
                    data.setTspName(_rs_.getString(3));
                    data.setTspSesId(_rs_.getInt(4));
                    data.setTspValue(_rs_.getString(5));
                    list.add(data);
                }
            } else {
                while (($max$ < 0 || $count$ <= $max$) && _rs_.next()) {
                    $count$++;
                    int $col$ = 3;
                    TSessionPropKey $tableKey$ = new TSessionPropKey(_rs_.getString(1), _rs_.getInt(2));
                    TSessionPropDTO data = new TSessionPropDTO();
                    for (Iterator i = propertyList.keySet().iterator(); i.hasNext();) {
                        String selectedFieldName = (String) i.next();
                        int selectedFieldId = selectedFieldName.hashCode();
                        switch (selectedFieldId) {
                            case -1722484213: {  //field tspSesId
                                data.setTspSesId(_rs_.getInt($col$++));

                                break;
                            }
                            case -1025548292: {  //field tspName
                                data.setTspName(_rs_.getString($col$++));

                                break;
                            }
                            case -2090895037: {  //column TSP_VALUE
                                data.setTspValue(_rs_.getString($col$++));

                                break;
                            }
                            case -475211737: {  //column TSP_SES_ID
                                data.setTspSesId(_rs_.getInt($col$++));

                                break;
                            }
                            case -898970535: {  //column TSP_NAME
                                data.setTspName(_rs_.getString($col$++));

                                break;
                            }
                            case -1719838176: {  //field tspValue
                                data.setTspValue(_rs_.getString($col$++));

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
            return (TSessionPropDTO[]) list.toArray(new TSessionPropDTO[list.size()]);
        } catch (SQLException sqlExcp) {
            throw new SQLException("DataRetrievalException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TSessionPropDTO[] select(TSessionPropPropertyList propertyList) throws SQLException {
        return select(propertyList, (TSessionPropFilter) null);
    }

}