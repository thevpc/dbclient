package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tcnxproperty;

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
public class TCnxPropertyDAO {
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
    public TCnxPropertyDAO() {
        super();
    }

    /**
     * DataAccessObject Constructor
     */
    public TCnxPropertyDAO(Connection con) {
        super();
        setConnection(con);
    }

    /**
     * DataAccessObject Constructor
     *
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TCnxPropertyKey insert(TCnxPropertyDTO data) throws SQLException {
        // START Prologue initialization
        java.lang.String cprName = null;
        int cprSesId = 0;
        java.lang.String cprValue = null;
        // END   Prologue initialization
        // START Prologue checking
        if (!data.containsCprName()) {
            throw new SQLException("RequiredFieldOnInsertException cprName");
        }
        if (!data.containsCprSesId()) {
            throw new SQLException("RequiredFieldOnInsertException cprSesId");
        }
        // END  Prologue checking

        Connection _conn_ = null;
        String _statement_ = null;
        PreparedStatement _prepStmt_ = null;
        try {
            _conn_ = getConnection();
            cprName = (data.getCprName());
            cprSesId = (data.getCprSesId());

            // START object-definition.do<name=TCnxProperty>.userCode.preInsert
            // code retreived from object-definition.do<name=TCnxProperty>.userCode.preInsert
            // END   object-definition.do<name=TCnxProperty>.userCode.preInsert
            // START Local Fields Updates
            for (Iterator i = data.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case -587396585: {  //column CPR_VALUE
                        // START object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.preInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.preInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.preInsert
                        cprValue = (data.getCprValue());
                        // START object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.postInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.postInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.postInsert
                        break;
                    }
                    case 404225719: {  //field cprSesId
                        // START object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.preInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.preInsert
                        cprSesId = (data.getCprSesId());
                        // START object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.postInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.postInsert
                        break;
                    }
                    case 1227739397: {  //column CPR_NAME
                        // START object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.preInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.preInsert
                        cprName = (data.getCprName());
                        // START object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.postInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.postInsert
                        break;
                    }
                    case 406871756: {  //field cprValue
                        // START object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.preInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.preInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.preInsert
                        cprValue = (data.getCprValue());
                        // START object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.postInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.postInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprValue>.userCode.postInsert
                        break;
                    }
                    case -1111399981: {  //column CPR_SES_ID
                        // START object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.preInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.preInsert
                        cprSesId = (data.getCprSesId());
                        // START object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.postInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprSesId>.userCode.postInsert
                        break;
                    }
                    case 982717904: {  //field cprName
                        // START object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.preInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.preInsert
                        cprName = (data.getCprName());
                        // START object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.postInsert
                        // END   object-definition.do<name=TCnxProperty>.field<name=cprName>.userCode.postInsert
                        break;
                    }
                    default: {
                        throw new SQLException("UnknownFieldException" + selectedFieldName);
                    }
                }
            }
            // END   Local Fields Updates

            // START Database persistance
            _statement_ = "INSERT INTO T_CNX_PROPERTY(CPR_NAME, CPR_SES_ID, CPR_VALUE) VALUES (?, ?, ?)";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setString(1, cprName);
            _prepStmt_.setInt(2, cprSesId);
            _prepStmt_.setString(3, cprValue);
            _prepStmt_.executeUpdate();
            _prepStmt_.close();

            // START object-definition.do<name=TCnxProperty>.userCode.postInsert
            // code retreived from object-definition.do<name=TCnxProperty>.userCode.postInsert
            // END   object-definition.do<name=TCnxProperty>.userCode.postInsert
            // END   Database persistance

            // returning Identifier;
            return data.getTCnxPropertyKey();
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
    public void update(TCnxPropertyDTO data) throws SQLException {
        if (data.size() == 0) {
            return;
        }
        // START Prologue Cheking
        // END   ForbiddenFieldOnUpdate Cheking

        // START object-definition.do<name=TCnxProperty>.userCode.preUpdate
        // code retreived from object-definition.do<name=TCnxProperty>.userCode.preUpdate
        // END   object-definition.do<name=TCnxProperty>.userCode.preUpdate
        Connection _conn_;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer();
            int _ucount_;
            PreparedStatement _prepStmt_ = null;
            boolean _firstColumn_ = true;
            _statement_.append("UPDATE T_CNX_PROPERTY SET ");
            if (data.containsCprValue()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("CPR_VALUE=? ");
            }
            _statement_.append(" WHERE CPR_SES_ID = ?  AND CPR_NAME = ? ");
            _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int _pos_ = 0;
            if (data.containsCprValue()) {
                _prepStmt_.setString(++_pos_, data.getCprValue());
            }
            _prepStmt_.setInt(++_pos_, data.getCprSesId());
            _prepStmt_.setString(++_pos_, data.getCprName());
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("UpdateDataException");
            }
            // START object-definition.do<name=TCnxProperty>.userCode.postUpdate
            // code retreived from object-definition.do<name=TCnxProperty>.userCode.postUpdate
            // END   object-definition.do<name=TCnxProperty>.userCode.postUpdate
        } catch (SQLException sqlExcp) {
            throw new SQLException("UpdateDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public void delete(TCnxPropertyKey key) throws SQLException {
        Connection _conn_;
        try {
            _conn_ = getConnection();
            // START object-definition.do<name=TCnxProperty>.userCode.preDelete
            // code retreived from object-definition.do<name=TCnxProperty>.userCode.preDelete
            // END   object-definition.do<name=TCnxProperty>.userCode.preDelete
            int _ucount_;
            String _statement_;
            PreparedStatement _prepStmt_;
            _statement_ = "DELETE FROM T_CNX_PROPERTY WHERE CPR_SES_ID = ?  AND CPR_NAME = ? ";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setInt(1, (key.getCprSesId()));
            _prepStmt_.setString(2, (key.getCprName()));
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("RemoveDataException");
            }
            // START object-definition.do<name=TCnxProperty>.userCode.postDelete
            // code retreived from object-definition.do<name=TCnxProperty>.userCode.postDelete
            // END   object-definition.do<name=TCnxProperty>.userCode.postDelete
        } catch (SQLException sqlExcp) {
            throw new SQLException("RemoveDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public int delete(TCnxPropertyFilter criteria) throws SQLException {
        PreparedStatement _prepStmt_ = null;
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("DELETE FROM T_CNX_PROPERTY");
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
    public TCnxPropertyDTO select(TCnxPropertyPropertyList propertyList, TCnxPropertyKey primaryKey) throws SQLException {
        String where = "T_CNX_PROPERTY.CPR_NAME = ? AND T_CNX_PROPERTY.CPR_SES_ID = ?";
        TCnxPropertyFilter criteria = new TCnxPropertyFilter();
        criteria.setWhereClause(where);
        criteria.setString(1, primaryKey.getCprName());
        criteria.setInt(2, primaryKey.getCprSesId());
        TCnxPropertyDTO[] _found_ = select(propertyList, criteria);
        if (_found_.length > 0) {
            return _found_[0];
        }
        return null;

    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TCnxPropertyDTO[] select(TCnxPropertyPropertyList propertyList, TCnxPropertyFilter criteria) throws SQLException {
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("SELECT ");
            if (criteria != null && criteria.isDistinct()) {
                _statement_.append("DISTINCT ");
            }
            if (propertyList == null) {
                _statement_.append("(T_CNX_PROPERTY.CPR_NAME),(T_CNX_PROPERTY.CPR_SES_ID),(T_CNX_PROPERTY.CPR_NAME),(T_CNX_PROPERTY.CPR_SES_ID),(T_CNX_PROPERTY.CPR_VALUE)");
            } else {
                StringBuffer sb = new StringBuffer("CPR_NAME,CPR_SES_ID");
                for (Iterator i = propertyList.keyIterator(); i.hasNext();) {
                    String selectedFieldName = (String) i.next();
                    int selectedFieldId = selectedFieldName.hashCode();
                    switch (selectedFieldId) {
                        case -587396585: {  //column CPR_VALUE
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CNX_PROPERTY.CPR_VALUE");

                            break;
                        }
                        case 404225719: {  //field cprSesId
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CNX_PROPERTY.CPR_SES_ID");

                            break;
                        }
                        case 1227739397: {  //column CPR_NAME
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CNX_PROPERTY.CPR_NAME");

                            break;
                        }
                        case 406871756: {  //field cprValue
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CNX_PROPERTY.CPR_VALUE");

                            break;
                        }
                        case -1111399981: {  //column CPR_SES_ID
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CNX_PROPERTY.CPR_SES_ID");

                            break;
                        }
                        case 982717904: {  //field cprName
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_CNX_PROPERTY.CPR_NAME");

                            break;
                        }
                        default: {
                            // default
                        }
                    }
                }
                _statement_.append(sb.toString());
            }
            _statement_.append(" FROM T_CNX_PROPERTY");
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
                        case -587396585: {  //column CPR_VALUE
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CNX_PROPERTY.CPR_VALUE");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 404225719: {  //field cprSesId
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CNX_PROPERTY.CPR_SES_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1227739397: {  //column CPR_NAME
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CNX_PROPERTY.CPR_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 406871756: {  //field cprValue
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CNX_PROPERTY.CPR_VALUE");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1111399981: {  //column CPR_SES_ID
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CNX_PROPERTY.CPR_SES_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 982717904: {  //field cprName
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_CNX_PROPERTY.CPR_NAME");
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
                    TCnxPropertyKey $tableKey$ = new TCnxPropertyKey(_rs_.getString(1), _rs_.getInt(2));
                    TCnxPropertyDTO data = new TCnxPropertyDTO();
                    data.setCprName(_rs_.getString(3));
                    data.setCprSesId(_rs_.getInt(4));
                    data.setCprValue(_rs_.getString(5));
                    list.add(data);
                }
            } else {
                while (($max$ < 0 || $count$ <= $max$) && _rs_.next()) {
                    $count$++;
                    int $col$ = 3;
                    TCnxPropertyKey $tableKey$ = new TCnxPropertyKey(_rs_.getString(1), _rs_.getInt(2));
                    TCnxPropertyDTO data = new TCnxPropertyDTO();
                    for (Iterator i = propertyList.keySet().iterator(); i.hasNext();) {
                        String selectedFieldName = (String) i.next();
                        int selectedFieldId = selectedFieldName.hashCode();
                        switch (selectedFieldId) {
                            case -587396585: {  //column CPR_VALUE
                                data.setCprValue(_rs_.getString($col$++));

                                break;
                            }
                            case 404225719: {  //field cprSesId
                                data.setCprSesId(_rs_.getInt($col$++));

                                break;
                            }
                            case 1227739397: {  //column CPR_NAME
                                data.setCprName(_rs_.getString($col$++));

                                break;
                            }
                            case 406871756: {  //field cprValue
                                data.setCprValue(_rs_.getString($col$++));

                                break;
                            }
                            case -1111399981: {  //column CPR_SES_ID
                                data.setCprSesId(_rs_.getInt($col$++));

                                break;
                            }
                            case 982717904: {  //field cprName
                                data.setCprName(_rs_.getString($col$++));

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
            return (TCnxPropertyDTO[]) list.toArray(new TCnxPropertyDTO[list.size()]);
        } catch (SQLException sqlExcp) {
            throw new SQLException("DataRetrievalException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TCnxPropertyDTO[] select(TCnxPropertyPropertyList propertyList) throws SQLException {
        return select(propertyList, (TCnxPropertyFilter) null);
    }

}