package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriverlib;

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
public class TDriverLibDAO {
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
    public TDriverLibDAO() {
        super();
    }

    /**
     * DataAccessObject Constructor
     */
    public TDriverLibDAO(Connection con) {
        super();
        setConnection(con);
    }

    /**
     * DataAccessObject Constructor
     *
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TDriverLibKey insert(TDriverLibDTO data) throws SQLException {
        // START Prologue initialization
        int tdlDrvId = 0;
        int tdlIndex = 0;
        java.lang.String tdlUrl = null;
        // END   Prologue initialization
        // START Prologue checking
        if (!data.containsTdlDrvId()) {
            throw new SQLException("RequiredFieldOnInsertException tdlDrvId");
        }
        if (!data.containsTdlIndex()) {
            throw new SQLException("RequiredFieldOnInsertException tdlIndex");
        }
        if (!data.containsTdlUrl()) {
            throw new SQLException("RequiredFieldOnInsertException tdlUrl");
        }
        // END  Prologue checking

        Connection _conn_ = null;
        String _statement_ = null;
        PreparedStatement _prepStmt_ = null;
        try {
            _conn_ = getConnection();
            tdlDrvId = (data.getTdlDrvId());
            tdlIndex = (data.getTdlIndex());

            // START object-definition.do<name=TDriverLib>.userCode.preInsert
            // code retreived from object-definition.do<name=TDriverLib>.userCode.preInsert
            // END   object-definition.do<name=TDriverLib>.userCode.preInsert
            // START Local Fields Updates
            for (Iterator i = data.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case -739217812: {  //column TDL_URL
                        // START object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.preInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.preInsert
                        tdlUrl = (data.getTdlUrl());
                        // START object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.postInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.postInsert
                        break;
                    }
                    case 2016850503: {  //field tdlDrvId
                        // START object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.preInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.preInsert
                        tdlDrvId = (data.getTdlDrvId());
                        // START object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.postInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.postInsert
                        break;
                    }
                    case 2068152565: {  //column TDL_DRV_ID
                        // START object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.preInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.preInsert
                        tdlDrvId = (data.getTdlDrvId());
                        // START object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.postInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlDrvId>.userCode.postInsert
                        break;
                    }
                    case -878330925: {  //field tdlUrl
                        // START object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.preInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.preInsert
                        tdlUrl = (data.getTdlUrl());
                        // START object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.postInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlUrl>.userCode.postInsert
                        break;
                    }
                    case -1729920369: {  //column TDL_INDEX
                        // START object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.preInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.preInsert
                        tdlIndex = (data.getTdlIndex());
                        // START object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.postInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.postInsert
                        break;
                    }
                    case 2021332534: {  //field tdlIndex
                        // START object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.preInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.preInsert
                        tdlIndex = (data.getTdlIndex());
                        // START object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.postInsert
                        // END   object-definition.do<name=TDriverLib>.field<name=tdlIndex>.userCode.postInsert
                        break;
                    }
                    default: {
                        throw new SQLException("UnknownFieldException" + selectedFieldName);
                    }
                }
            }
            // END   Local Fields Updates

            // START Database persistance
            _statement_ = "INSERT INTO T_DRIVER_LIB(TDL_DRV_ID, TDL_INDEX, TDL_URL) VALUES (?, ?, ?)";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setInt(1, tdlDrvId);
            _prepStmt_.setInt(2, tdlIndex);
            _prepStmt_.setString(3, tdlUrl);
            _prepStmt_.executeUpdate();
            _prepStmt_.close();

            // START object-definition.do<name=TDriverLib>.userCode.postInsert
            // code retreived from object-definition.do<name=TDriverLib>.userCode.postInsert
            // END   object-definition.do<name=TDriverLib>.userCode.postInsert
            // END   Database persistance

            // returning Identifier;
            return data.getTDriverLibKey();
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
    public void update(TDriverLibDTO data) throws SQLException {
        if (data.size() == 0) {
            return;
        }
        // START Prologue Cheking
        // END   ForbiddenFieldOnUpdate Cheking

        // START object-definition.do<name=TDriverLib>.userCode.preUpdate
        // code retreived from object-definition.do<name=TDriverLib>.userCode.preUpdate
        // END   object-definition.do<name=TDriverLib>.userCode.preUpdate
        Connection _conn_;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer();
            int _ucount_;
            PreparedStatement _prepStmt_ = null;
            boolean _firstColumn_ = true;
            _statement_.append("UPDATE T_DRIVER_LIB SET ");
            if (data.containsTdlUrl()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("TDL_URL=? ");
            }
            _statement_.append(" WHERE TDL_DRV_ID = ?  AND TDL_INDEX = ? ");
            _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int _pos_ = 0;
            if (data.containsTdlUrl()) {
                _prepStmt_.setString(++_pos_, data.getTdlUrl());
            }
            _prepStmt_.setInt(++_pos_, data.getTdlDrvId());
            _prepStmt_.setInt(++_pos_, data.getTdlIndex());
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("UpdateDataException");
            }
            // START object-definition.do<name=TDriverLib>.userCode.postUpdate
            // code retreived from object-definition.do<name=TDriverLib>.userCode.postUpdate
            // END   object-definition.do<name=TDriverLib>.userCode.postUpdate
        } catch (SQLException sqlExcp) {
            throw new SQLException("UpdateDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public void delete(TDriverLibKey key) throws SQLException {
        Connection _conn_;
        try {
            _conn_ = getConnection();
            // START object-definition.do<name=TDriverLib>.userCode.preDelete
            // code retreived from object-definition.do<name=TDriverLib>.userCode.preDelete
            // END   object-definition.do<name=TDriverLib>.userCode.preDelete
            int _ucount_;
            String _statement_;
            PreparedStatement _prepStmt_;
            _statement_ = "DELETE FROM T_DRIVER_LIB WHERE TDL_DRV_ID = ?  AND TDL_INDEX = ? ";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setInt(1, (key.getTdlDrvId()));
            _prepStmt_.setInt(2, (key.getTdlIndex()));
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("RemoveDataException");
            }
            // START object-definition.do<name=TDriverLib>.userCode.postDelete
            // code retreived from object-definition.do<name=TDriverLib>.userCode.postDelete
            // END   object-definition.do<name=TDriverLib>.userCode.postDelete
        } catch (SQLException sqlExcp) {
            throw new SQLException("RemoveDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public int delete(TDriverLibFilter criteria) throws SQLException {
        PreparedStatement _prepStmt_ = null;
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("DELETE FROM T_DRIVER_LIB");
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
    public TDriverLibDTO select(TDriverLibPropertyList propertyList, TDriverLibKey primaryKey) throws SQLException {
        String where = "T_DRIVER_LIB.TDL_DRV_ID = ? AND T_DRIVER_LIB.TDL_INDEX = ?";
        TDriverLibFilter criteria = new TDriverLibFilter();
        criteria.setWhereClause(where);
        criteria.setInt(1, primaryKey.getTdlDrvId());
        criteria.setInt(2, primaryKey.getTdlIndex());
        TDriverLibDTO[] _found_ = select(propertyList, criteria);
        if (_found_.length > 0) {
            return _found_[0];
        }
        return null;

    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TDriverLibDTO[] select(TDriverLibPropertyList propertyList, TDriverLibFilter criteria) throws SQLException {
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("SELECT ");
            if (criteria != null && criteria.isDistinct()) {
                _statement_.append("DISTINCT ");
            }
            if (propertyList == null) {
                _statement_.append("(T_DRIVER_LIB.TDL_DRV_ID),(T_DRIVER_LIB.TDL_INDEX),(T_DRIVER_LIB.TDL_DRV_ID),(T_DRIVER_LIB.TDL_INDEX),(T_DRIVER_LIB.TDL_URL)");
            } else {
                StringBuffer sb = new StringBuffer("TDL_DRV_ID,TDL_INDEX");
                for (Iterator i = propertyList.keyIterator(); i.hasNext();) {
                    String selectedFieldName = (String) i.next();
                    int selectedFieldId = selectedFieldName.hashCode();
                    switch (selectedFieldId) {
                        case -739217812: {  //column TDL_URL
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER_LIB.TDL_URL");

                            break;
                        }
                        case 2016850503: {  //field tdlDrvId
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER_LIB.TDL_DRV_ID");

                            break;
                        }
                        case 2068152565: {  //column TDL_DRV_ID
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER_LIB.TDL_DRV_ID");

                            break;
                        }
                        case -878330925: {  //field tdlUrl
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER_LIB.TDL_URL");

                            break;
                        }
                        case -1729920369: {  //column TDL_INDEX
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER_LIB.TDL_INDEX");

                            break;
                        }
                        case 2021332534: {  //field tdlIndex
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER_LIB.TDL_INDEX");

                            break;
                        }
                        default: {
                            // default
                        }
                    }
                }
                _statement_.append(sb.toString());
            }
            _statement_.append(" FROM T_DRIVER_LIB");
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
                        case -739217812: {  //column TDL_URL
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER_LIB.TDL_URL");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 2016850503: {  //field tdlDrvId
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER_LIB.TDL_DRV_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 2068152565: {  //column TDL_DRV_ID
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER_LIB.TDL_DRV_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -878330925: {  //field tdlUrl
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER_LIB.TDL_URL");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1729920369: {  //column TDL_INDEX
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER_LIB.TDL_INDEX");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 2021332534: {  //field tdlIndex
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER_LIB.TDL_INDEX");
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
                    TDriverLibKey $tableKey$ = new TDriverLibKey(_rs_.getInt(1), _rs_.getInt(2));
                    TDriverLibDTO data = new TDriverLibDTO();
                    data.setTdlDrvId(_rs_.getInt(3));
                    data.setTdlIndex(_rs_.getInt(4));
                    data.setTdlUrl(_rs_.getString(5));
                    list.add(data);
                }
            } else {
                while (($max$ < 0 || $count$ <= $max$) && _rs_.next()) {
                    $count$++;
                    int $col$ = 3;
                    TDriverLibKey $tableKey$ = new TDriverLibKey(_rs_.getInt(1), _rs_.getInt(2));
                    TDriverLibDTO data = new TDriverLibDTO();
                    for (Iterator i = propertyList.keySet().iterator(); i.hasNext();) {
                        String selectedFieldName = (String) i.next();
                        int selectedFieldId = selectedFieldName.hashCode();
                        switch (selectedFieldId) {
                            case -739217812: {  //column TDL_URL
                                data.setTdlUrl(_rs_.getString($col$++));

                                break;
                            }
                            case 2016850503: {  //field tdlDrvId
                                data.setTdlDrvId(_rs_.getInt($col$++));

                                break;
                            }
                            case 2068152565: {  //column TDL_DRV_ID
                                data.setTdlDrvId(_rs_.getInt($col$++));

                                break;
                            }
                            case -878330925: {  //field tdlUrl
                                data.setTdlUrl(_rs_.getString($col$++));

                                break;
                            }
                            case -1729920369: {  //column TDL_INDEX
                                data.setTdlIndex(_rs_.getInt($col$++));

                                break;
                            }
                            case 2021332534: {  //field tdlIndex
                                data.setTdlIndex(_rs_.getInt($col$++));

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
            return (TDriverLibDTO[]) list.toArray(new TDriverLibDTO[list.size()]);
        } catch (SQLException sqlExcp) {
            throw new SQLException("DataRetrievalException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TDriverLibDTO[] select(TDriverLibPropertyList propertyList) throws SQLException {
        return select(propertyList, (TDriverLibFilter) null);
    }

}