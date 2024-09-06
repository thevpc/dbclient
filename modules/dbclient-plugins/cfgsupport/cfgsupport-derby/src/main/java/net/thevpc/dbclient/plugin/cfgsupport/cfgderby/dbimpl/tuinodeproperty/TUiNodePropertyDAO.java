package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tuinodeproperty;

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
public class TUiNodePropertyDAO {
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
    public TUiNodePropertyDAO() {
        super();
    }

    /**
     * DataAccessObject Constructor
     */
    public TUiNodePropertyDAO(Connection con) {
        super();
        setConnection(con);
    }

    /**
     * DataAccessObject Constructor
     *
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TUiNodePropertyKey insert(TUiNodePropertyDTO data) throws SQLException {
        // START Prologue initialization
        java.lang.String unpCode = null;
        java.lang.String unpPath = null;
        int unpSesId = 0;
        java.lang.String unpValue = null;
        // END   Prologue initialization
        // START Prologue checking
        if (!data.containsUnpCode()) {
            throw new SQLException("RequiredFieldOnInsertException unpCode");
        }
        if (!data.containsUnpPath()) {
            throw new SQLException("RequiredFieldOnInsertException unpPath");
        }
        if (!data.containsUnpSesId()) {
            throw new SQLException("RequiredFieldOnInsertException unpSesId");
        }
        // END  Prologue checking

        Connection _conn_ = null;
        String _statement_ = null;
        PreparedStatement _prepStmt_ = null;
        try {
            _conn_ = getConnection();
            unpCode = (data.getUnpCode());
            unpPath = (data.getUnpPath());
            unpSesId = (data.getUnpSesId());

            // START object-definition.do<name=TUiNodeProperty>.userCode.preInsert
            // code retreived from object-definition.do<name=TUiNodeProperty>.userCode.preInsert
            // END   object-definition.do<name=TUiNodeProperty>.userCode.preInsert
            // START Local Fields Updates
            for (Iterator i = data.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case 701348493: {  //column UNP_PATH
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.preInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.preInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.preInsert
                        unpPath = (data.getUnpPath());
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.postInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.postInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.postInsert
                        break;
                    }
                    case -224387519: {  //column UNP_SES_ID
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.preInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.preInsert
                        unpSesId = (data.getUnpSesId());
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.postInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.postInsert
                        break;
                    }
                    case -122224987: {  //field unpSesId
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.preInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.preInsert
                        unpSesId = (data.getUnpSesId());
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.postInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpSesId>.userCode.postInsert
                        break;
                    }
                    case -119578950: {  //field unpValue
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.preInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.preInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.preInsert
                        unpValue = (data.getUnpValue());
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.postInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.postInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.postInsert
                        break;
                    }
                    case -281504892: {  //field unpCode
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.preInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.preInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.preInsert
                        unpCode = (data.getUnpCode());
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.postInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.postInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.postInsert
                        break;
                    }
                    case -281130564: {  //field unpPath
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.preInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.preInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.preInsert
                        unpPath = (data.getUnpPath());
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.postInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.postInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpPath>.userCode.postInsert
                        break;
                    }
                    case 272500713: {  //column UNP_VALUE
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.preInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.preInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.preInsert
                        unpValue = (data.getUnpValue());
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.postInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.postInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpValue>.userCode.postInsert
                        break;
                    }
                    case 700974165: {  //column UNP_CODE
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.preInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.preInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.preInsert
                        unpCode = (data.getUnpCode());
                        // START object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.postInsert
                        // code retreived from object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.postInsert
                        // END   object-definition.do<name=TUiNodeProperty>.field<name=unpCode>.userCode.postInsert
                        break;
                    }
                    default: {
                        throw new SQLException("UnknownFieldException" + selectedFieldName);
                    }
                }
            }
            // END   Local Fields Updates

            // START Database persistance
            _statement_ = "INSERT INTO T_UI_NODE_PROPERTY(UNP_CODE, UNP_PATH, UNP_SES_ID, UNP_VALUE) VALUES (?, ?, ?, ?)";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setString(1, unpCode);
            _prepStmt_.setString(2, unpPath);
            _prepStmt_.setInt(3, unpSesId);
            _prepStmt_.setString(4, unpValue);
            _prepStmt_.executeUpdate();
            _prepStmt_.close();

            // START object-definition.do<name=TUiNodeProperty>.userCode.postInsert
            // code retreived from object-definition.do<name=TUiNodeProperty>.userCode.postInsert
            // END   object-definition.do<name=TUiNodeProperty>.userCode.postInsert
            // END   Database persistance

            // returning Identifier;
            return data.getTUiNodePropertyKey();
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
    public void update(TUiNodePropertyDTO data) throws SQLException {
        if (data.size() == 0) {
            return;
        }
        // START Prologue Cheking
        // END   ForbiddenFieldOnUpdate Cheking

        // START object-definition.do<name=TUiNodeProperty>.userCode.preUpdate
        // code retreived from object-definition.do<name=TUiNodeProperty>.userCode.preUpdate
        // END   object-definition.do<name=TUiNodeProperty>.userCode.preUpdate
        Connection _conn_;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer();
            int _ucount_;
            PreparedStatement _prepStmt_ = null;
            boolean _firstColumn_ = true;
            _statement_.append("UPDATE T_UI_NODE_PROPERTY SET ");
            if (data.containsUnpValue()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("UNP_VALUE=? ");
            }
            _statement_.append(" WHERE UNP_SES_ID = ?  AND UNP_CODE = ?  AND UNP_PATH = ? ");
            _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int _pos_ = 0;
            if (data.containsUnpValue()) {
                _prepStmt_.setString(++_pos_, data.getUnpValue());
            }
            _prepStmt_.setInt(++_pos_, data.getUnpSesId());
            _prepStmt_.setString(++_pos_, data.getUnpCode());
            _prepStmt_.setString(++_pos_, data.getUnpPath());
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("UpdateDataException");
            }
            // START object-definition.do<name=TUiNodeProperty>.userCode.postUpdate
            // code retreived from object-definition.do<name=TUiNodeProperty>.userCode.postUpdate
            // END   object-definition.do<name=TUiNodeProperty>.userCode.postUpdate
        } catch (SQLException sqlExcp) {
            throw new SQLException("UpdateDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public void delete(TUiNodePropertyKey key) throws SQLException {
        Connection _conn_;
        try {
            _conn_ = getConnection();
            // START object-definition.do<name=TUiNodeProperty>.userCode.preDelete
            // code retreived from object-definition.do<name=TUiNodeProperty>.userCode.preDelete
            // END   object-definition.do<name=TUiNodeProperty>.userCode.preDelete
            int _ucount_;
            String _statement_;
            PreparedStatement _prepStmt_;
            _statement_ = "DELETE FROM T_UI_NODE_PROPERTY WHERE UNP_SES_ID = ?  AND UNP_CODE = ?  AND UNP_PATH = ? ";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setInt(1, (key.getUnpSesId()));
            _prepStmt_.setString(2, (key.getUnpCode()));
            _prepStmt_.setString(3, (key.getUnpPath()));
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("RemoveDataException");
            }
            // START object-definition.do<name=TUiNodeProperty>.userCode.postDelete
            // code retreived from object-definition.do<name=TUiNodeProperty>.userCode.postDelete
            // END   object-definition.do<name=TUiNodeProperty>.userCode.postDelete
        } catch (SQLException sqlExcp) {
            throw new SQLException("RemoveDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public int delete(TUiNodePropertyFilter criteria) throws SQLException {
        PreparedStatement _prepStmt_ = null;
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("DELETE FROM T_UI_NODE_PROPERTY");
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
    public TUiNodePropertyDTO select(TUiNodePropertyPropertyList propertyList, TUiNodePropertyKey primaryKey) throws SQLException {
        String where = "T_UI_NODE_PROPERTY.UNP_CODE = ? AND T_UI_NODE_PROPERTY.UNP_PATH = ? AND T_UI_NODE_PROPERTY.UNP_SES_ID = ?";
        TUiNodePropertyFilter criteria = new TUiNodePropertyFilter();
        criteria.setWhereClause(where);
        criteria.setString(1, primaryKey.getUnpCode());
        criteria.setString(2, primaryKey.getUnpPath());
        criteria.setInt(3, primaryKey.getUnpSesId());
        TUiNodePropertyDTO[] _found_ = select(propertyList, criteria);
        if (_found_.length > 0) {
            return _found_[0];
        }
        return null;

    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TUiNodePropertyDTO[] select(TUiNodePropertyPropertyList propertyList, TUiNodePropertyFilter criteria) throws SQLException {
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("SELECT ");
            if (criteria != null && criteria.isDistinct()) {
                _statement_.append("DISTINCT ");
            }
            if (propertyList == null) {
                _statement_.append("(T_UI_NODE_PROPERTY.UNP_CODE),(T_UI_NODE_PROPERTY.UNP_PATH),(T_UI_NODE_PROPERTY.UNP_SES_ID),(T_UI_NODE_PROPERTY.UNP_CODE),(T_UI_NODE_PROPERTY.UNP_PATH),(T_UI_NODE_PROPERTY.UNP_SES_ID),(T_UI_NODE_PROPERTY.UNP_VALUE)");
            } else {
                StringBuffer sb = new StringBuffer("UNP_CODE,UNP_PATH,UNP_SES_ID");
                for (Iterator i = propertyList.keyIterator(); i.hasNext();) {
                    String selectedFieldName = (String) i.next();
                    int selectedFieldId = selectedFieldName.hashCode();
                    switch (selectedFieldId) {
                        case 701348493: {  //column UNP_PATH
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_UI_NODE_PROPERTY.UNP_PATH");

                            break;
                        }
                        case -224387519: {  //column UNP_SES_ID
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_UI_NODE_PROPERTY.UNP_SES_ID");

                            break;
                        }
                        case -122224987: {  //field unpSesId
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_UI_NODE_PROPERTY.UNP_SES_ID");

                            break;
                        }
                        case -119578950: {  //field unpValue
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_UI_NODE_PROPERTY.UNP_VALUE");

                            break;
                        }
                        case -281504892: {  //field unpCode
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_UI_NODE_PROPERTY.UNP_CODE");

                            break;
                        }
                        case -281130564: {  //field unpPath
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_UI_NODE_PROPERTY.UNP_PATH");

                            break;
                        }
                        case 272500713: {  //column UNP_VALUE
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_UI_NODE_PROPERTY.UNP_VALUE");

                            break;
                        }
                        case 700974165: {  //column UNP_CODE
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_UI_NODE_PROPERTY.UNP_CODE");

                            break;
                        }
                        default: {
                            // default
                        }
                    }
                }
                _statement_.append(sb.toString());
            }
            _statement_.append(" FROM T_UI_NODE_PROPERTY");
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
                        case 701348493: {  //column UNP_PATH
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_UI_NODE_PROPERTY.UNP_PATH");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -224387519: {  //column UNP_SES_ID
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_UI_NODE_PROPERTY.UNP_SES_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -122224987: {  //field unpSesId
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_UI_NODE_PROPERTY.UNP_SES_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -119578950: {  //field unpValue
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_UI_NODE_PROPERTY.UNP_VALUE");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -281504892: {  //field unpCode
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_UI_NODE_PROPERTY.UNP_CODE");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -281130564: {  //field unpPath
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_UI_NODE_PROPERTY.UNP_PATH");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 272500713: {  //column UNP_VALUE
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_UI_NODE_PROPERTY.UNP_VALUE");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 700974165: {  //column UNP_CODE
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_UI_NODE_PROPERTY.UNP_CODE");
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
                    TUiNodePropertyKey $tableKey$ = new TUiNodePropertyKey(_rs_.getString(1), _rs_.getString(2), _rs_.getInt(3));
                    TUiNodePropertyDTO data = new TUiNodePropertyDTO();
                    data.setUnpCode(_rs_.getString(4));
                    data.setUnpPath(_rs_.getString(5));
                    data.setUnpSesId(_rs_.getInt(6));
                    data.setUnpValue(_rs_.getString(7));
                    list.add(data);
                }
            } else {
                while (($max$ < 0 || $count$ <= $max$) && _rs_.next()) {
                    $count$++;
                    int $col$ = 4;
                    TUiNodePropertyKey $tableKey$ = new TUiNodePropertyKey(_rs_.getString(1), _rs_.getString(2), _rs_.getInt(3));
                    TUiNodePropertyDTO data = new TUiNodePropertyDTO();
                    for (Iterator i = propertyList.keySet().iterator(); i.hasNext();) {
                        String selectedFieldName = (String) i.next();
                        int selectedFieldId = selectedFieldName.hashCode();
                        switch (selectedFieldId) {
                            case 701348493: {  //column UNP_PATH
                                data.setUnpPath(_rs_.getString($col$++));

                                break;
                            }
                            case -224387519: {  //column UNP_SES_ID
                                data.setUnpSesId(_rs_.getInt($col$++));

                                break;
                            }
                            case -122224987: {  //field unpSesId
                                data.setUnpSesId(_rs_.getInt($col$++));

                                break;
                            }
                            case -119578950: {  //field unpValue
                                data.setUnpValue(_rs_.getString($col$++));

                                break;
                            }
                            case -281504892: {  //field unpCode
                                data.setUnpCode(_rs_.getString($col$++));

                                break;
                            }
                            case -281130564: {  //field unpPath
                                data.setUnpPath(_rs_.getString($col$++));

                                break;
                            }
                            case 272500713: {  //column UNP_VALUE
                                data.setUnpValue(_rs_.getString($col$++));

                                break;
                            }
                            case 700974165: {  //column UNP_CODE
                                data.setUnpCode(_rs_.getString($col$++));

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
            return (TUiNodePropertyDTO[]) list.toArray(new TUiNodePropertyDTO[list.size()]);
        } catch (SQLException sqlExcp) {
            throw new SQLException("DataRetrievalException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TUiNodePropertyDTO[] select(TUiNodePropertyPropertyList propertyList) throws SQLException {
        return select(propertyList, (TUiNodePropertyFilter) null);
    }

}