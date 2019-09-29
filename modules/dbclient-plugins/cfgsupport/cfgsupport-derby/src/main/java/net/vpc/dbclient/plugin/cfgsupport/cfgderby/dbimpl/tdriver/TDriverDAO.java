package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriver;

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
public class TDriverDAO {
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
    public TDriverDAO() {
        super();
    }

    /**
     * DataAccessObject Constructor
     */
    public TDriverDAO(Connection con) {
        super();
        setConnection(con);
    }

    /**
     * DataAccessObject Constructor
     *
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TDriverKey insert(TDriverDTO data) throws SQLException {
        // START Prologue initialization
        int drvId = 0;
        java.lang.String drvName = null;
        int drvIndex = 0;
        java.lang.String drvClassName = null;
        java.lang.String drvDefaultUrl = null;
        java.lang.String drvDefaultLogin = null;
        java.lang.String drvDefaultPassword = null;
        java.lang.String drvDefaultDesc = null;
        int drvEnabled = 0;
        // END   Prologue initialization
        // START Prologue checking
        if (data.containsDrvId()) {
            throw new SQLException("ForbiddenFieldOnInsertException drvId");
        }
        if (!data.containsDrvName()) {
            throw new SQLException("RequiredFieldOnInsertException drvName");
        }
        if (!data.containsDrvIndex()) {
            throw new SQLException("RequiredFieldOnInsertException drvIndex");
        }
        if (!data.containsDrvClassName()) {
            throw new SQLException("RequiredFieldOnInsertException drvClassName");
        }
        if (!data.containsDrvDefaultUrl()) {
            throw new SQLException("RequiredFieldOnInsertException drvDefaultUrl");
        }
        if (!data.containsDrvDefaultLogin()) {
            throw new SQLException("RequiredFieldOnInsertException drvDefaultLogin");
        }
        if (!data.containsDrvDefaultPassword()) {
            throw new SQLException("RequiredFieldOnInsertException drvDefaultPassword");
        }
        if (!data.containsDrvDefaultDesc()) {
            throw new SQLException("RequiredFieldOnInsertException drvDefaultDesc");
        }
        if (!data.containsDrvEnabled()) {
            throw new SQLException("RequiredFieldOnInsertException drvEnabled");
        }
        // END  Prologue checking

        Connection _conn_ = null;
        String _statement_ = null;
        PreparedStatement _prepStmt_ = null;
        try {
            _conn_ = getConnection();

            // START object-definition.do<name=TDriver>.userCode.preInsert
            // code retreived from object-definition.do<name=TDriver>.userCode.preInsert
            // END   object-definition.do<name=TDriver>.userCode.preInsert
            // START Local Fields Updates
            for (Iterator i = data.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case 2025166642: {  //column DRV_ID
                        // START object-definition.do<name=TDriver>.field<name=drvId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvId>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvId>.userCode.preInsert
                        drvId = (data.getDrvId());
                        // START object-definition.do<name=TDriver>.field<name=drvId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvId>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvId>.userCode.postInsert
                        break;
                    }
                    case 1609667419: {  //field drvClassName
                        // START object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.preInsert
                        drvClassName = (data.getDrvClassName());
                        // START object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.postInsert
                        break;
                    }
                    case 334190395: {  //column DRV_INDEX
                        // START object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.preInsert
                        drvIndex = (data.getDrvIndex());
                        // START object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.postInsert
                        break;
                    }
                    case 1931173971: {  //field drvName
                        // START object-definition.do<name=TDriver>.field<name=drvName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvName>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvName>.userCode.preInsert
                        drvName = (data.getDrvName());
                        // START object-definition.do<name=TDriver>.field<name=drvName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvName>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvName>.userCode.postInsert
                        break;
                    }
                    case -2133227664: {  //field drvDefaultLogin
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.preInsert
                        drvDefaultLogin = (data.getDrvDefaultLogin());
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.postInsert
                        break;
                    }
                    case 989259984: {  //column DRV_DEFAULT_PASSWORD
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.preInsert
                        drvDefaultPassword = (data.getDrvDefaultPassword());
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.postInsert
                        break;
                    }
                    case -223494294: {  //column DRV_ENABLED
                        // START object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.preInsert
                        drvEnabled = (data.getDrvEnabled());
                        // START object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.postInsert
                        break;
                    }
                    case 2067830598: {  //column DRV_DEFAULT_DESC
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.preInsert
                        drvDefaultDesc = (data.getDrvDefaultDesc());
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.postInsert
                        break;
                    }
                    case -314086092: {  //column DRV_DEFAULT_LOGIN
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.preInsert
                        drvDefaultLogin = (data.getDrvDefaultLogin());
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultLogin>.userCode.postInsert
                        break;
                    }
                    case -1734394374: {  //column DRV_DEFAULT_URL
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.preInsert
                        drvDefaultUrl = (data.getDrvDefaultUrl());
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.postInsert
                        break;
                    }
                    case 565106402: {  //column DRV_NAME
                        // START object-definition.do<name=TDriver>.field<name=drvName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvName>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvName>.userCode.preInsert
                        drvName = (data.getDrvName());
                        // START object-definition.do<name=TDriver>.field<name=drvName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvName>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvName>.userCode.postInsert
                        break;
                    }
                    case 1480355961: {  //field drvEnabled
                        // START object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.preInsert
                        drvEnabled = (data.getDrvEnabled());
                        // START object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvEnabled>.userCode.postInsert
                        break;
                    }
                    case 1177864618: {  //field drvDefaultDesc
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.preInsert
                        drvDefaultDesc = (data.getDrvDefaultDesc());
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultDesc>.userCode.postInsert
                        break;
                    }
                    case 95864035: {  //field drvId
                        // START object-definition.do<name=TDriver>.field<name=drvId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvId>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvId>.userCode.preInsert
                        drvId = (data.getDrvId());
                        // START object-definition.do<name=TDriver>.field<name=drvId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvId>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvId>.userCode.postInsert
                        break;
                    }
                    case 840061236: {  //field drvDefaultPassword
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.preInsert
                        drvDefaultPassword = (data.getDrvDefaultPassword());
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultPassword>.userCode.postInsert
                        break;
                    }
                    case -267387894: {  //field drvIndex
                        // START object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.preInsert
                        drvIndex = (data.getDrvIndex());
                        // START object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvIndex>.userCode.postInsert
                        break;
                    }
                    case -654724298: {  //field drvDefaultUrl
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.preInsert
                        drvDefaultUrl = (data.getDrvDefaultUrl());
                        // START object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvDefaultUrl>.userCode.postInsert
                        break;
                    }
                    case 1221204009: {  //column DRV_CLASS_NAME
                        // START object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.preInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.preInsert
                        drvClassName = (data.getDrvClassName());
                        // START object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.postInsert
                        // END   object-definition.do<name=TDriver>.field<name=drvClassName>.userCode.postInsert
                        break;
                    }
                    default: {
                        throw new SQLException("UnknownFieldException" + selectedFieldName);
                    }
                }
            }
            // END   Local Fields Updates

            // START Database persistance
            _statement_ = "INSERT INTO T_DRIVER(DRV_NAME, DRV_INDEX, DRV_CLASS_NAME, DRV_DEFAULT_URL, DRV_DEFAULT_LOGIN, DRV_DEFAULT_PASSWORD, DRV_DEFAULT_DESC, DRV_ENABLED) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setString(1, drvName);
            _prepStmt_.setInt(2, drvIndex);
            _prepStmt_.setString(3, drvClassName);
            _prepStmt_.setString(4, drvDefaultUrl);
            _prepStmt_.setString(5, drvDefaultLogin);
            _prepStmt_.setString(6, drvDefaultPassword);
            _prepStmt_.setString(7, drvDefaultDesc);
            _prepStmt_.setInt(8, drvEnabled);
            _prepStmt_.executeUpdate();
            _prepStmt_.close();


            // START Sequence Handling
            String _selectNewIdStatement_ = "Select w.AUTOINCREMENTVALUE-1   From SYS.SYSCOLUMNS w  inner join SYS.SYSTABLES t on REFERENCEID=t.TABLEID where  w.COLUMNNAME='DRV_ID' and t.TABLENAME='T_DRIVER'";
            _prepStmt_ = _conn_.prepareStatement(_selectNewIdStatement_);
            ResultSet _rs_ = _prepStmt_.executeQuery();
            if (_rs_.next()) {
                data.setDrvId(_rs_.getInt(1));
            }
            _rs_.close();
            _prepStmt_.close();
            // END Sequence Handling
            // START object-definition.do<name=TDriver>.userCode.postInsert
            // code retreived from object-definition.do<name=TDriver>.userCode.postInsert
            // END   object-definition.do<name=TDriver>.userCode.postInsert
            // END   Database persistance

            // returning Identifier;
            return data.getTDriverKey();
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
    public void update(TDriverDTO data) throws SQLException {
        if (data.size() == 0) {
            return;
        }
        // START Prologue Cheking
        // END   ForbiddenFieldOnUpdate Cheking

        // START object-definition.do<name=TDriver>.userCode.preUpdate
        // code retreived from object-definition.do<name=TDriver>.userCode.preUpdate
        // END   object-definition.do<name=TDriver>.userCode.preUpdate
        Connection _conn_;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer();
            int _ucount_;
            PreparedStatement _prepStmt_ = null;
            boolean _firstColumn_ = true;
            _statement_.append("UPDATE T_DRIVER SET ");
            if (data.containsDrvName()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("DRV_NAME=? ");
            }
            if (data.containsDrvIndex()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("DRV_INDEX=? ");
            }
            if (data.containsDrvClassName()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("DRV_CLASS_NAME=? ");
            }
            if (data.containsDrvDefaultUrl()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("DRV_DEFAULT_URL=? ");
            }
            if (data.containsDrvDefaultLogin()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("DRV_DEFAULT_LOGIN=? ");
            }
            if (data.containsDrvDefaultPassword()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("DRV_DEFAULT_PASSWORD=? ");
            }
            if (data.containsDrvDefaultDesc()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("DRV_DEFAULT_DESC=? ");
            }
            if (data.containsDrvEnabled()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("DRV_ENABLED=? ");
            }
            _statement_.append(" WHERE DRV_ID = ? ");
            _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int _pos_ = 0;
            if (data.containsDrvName()) {
                _prepStmt_.setString(++_pos_, data.getDrvName());
            }
            if (data.containsDrvIndex()) {
                _prepStmt_.setInt(++_pos_, data.getDrvIndex());
            }
            if (data.containsDrvClassName()) {
                _prepStmt_.setString(++_pos_, data.getDrvClassName());
            }
            if (data.containsDrvDefaultUrl()) {
                _prepStmt_.setString(++_pos_, data.getDrvDefaultUrl());
            }
            if (data.containsDrvDefaultLogin()) {
                _prepStmt_.setString(++_pos_, data.getDrvDefaultLogin());
            }
            if (data.containsDrvDefaultPassword()) {
                _prepStmt_.setString(++_pos_, data.getDrvDefaultPassword());
            }
            if (data.containsDrvDefaultDesc()) {
                _prepStmt_.setString(++_pos_, data.getDrvDefaultDesc());
            }
            if (data.containsDrvEnabled()) {
                _prepStmt_.setInt(++_pos_, data.getDrvEnabled());
            }
            _prepStmt_.setInt(++_pos_, data.getDrvId());
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("UpdateDataException");
            }
            // START object-definition.do<name=TDriver>.userCode.postUpdate
            // code retreived from object-definition.do<name=TDriver>.userCode.postUpdate
            // END   object-definition.do<name=TDriver>.userCode.postUpdate
        } catch (SQLException sqlExcp) {
            throw new SQLException("UpdateDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public void delete(TDriverKey key) throws SQLException {
        Connection _conn_;
        try {
            _conn_ = getConnection();
            // START object-definition.do<name=TDriver>.userCode.preDelete
            // code retreived from object-definition.do<name=TDriver>.userCode.preDelete
            // END   object-definition.do<name=TDriver>.userCode.preDelete
            int _ucount_;
            String _statement_;
            PreparedStatement _prepStmt_;
            _statement_ = "DELETE FROM T_DRIVER WHERE DRV_ID = ? ";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setInt(1, (key.getDrvId()));
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("RemoveDataException");
            }
            // START object-definition.do<name=TDriver>.userCode.postDelete
            // code retreived from object-definition.do<name=TDriver>.userCode.postDelete
            // END   object-definition.do<name=TDriver>.userCode.postDelete
        } catch (SQLException sqlExcp) {
            throw new SQLException("RemoveDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public int delete(TDriverFilter criteria) throws SQLException {
        PreparedStatement _prepStmt_ = null;
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("DELETE FROM T_DRIVER");
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
    public TDriverDTO select(TDriverPropertyList propertyList, TDriverKey primaryKey) throws SQLException {
        String where = "T_DRIVER.DRV_ID = ?";
        TDriverFilter criteria = new TDriverFilter();
        criteria.setWhereClause(where);
        criteria.setInt(1, primaryKey.getDrvId());
        TDriverDTO[] _found_ = select(propertyList, criteria);
        if (_found_.length > 0) {
            return _found_[0];
        }
        return null;

    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TDriverDTO[] select(TDriverPropertyList propertyList, TDriverFilter criteria) throws SQLException {
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("SELECT ");
            if (criteria != null && criteria.isDistinct()) {
                _statement_.append("DISTINCT ");
            }
            if (propertyList == null) {
                _statement_.append("(T_DRIVER.DRV_ID),(T_DRIVER.DRV_ID),(T_DRIVER.DRV_NAME),(T_DRIVER.DRV_INDEX),(T_DRIVER.DRV_CLASS_NAME),(T_DRIVER.DRV_DEFAULT_URL),(T_DRIVER.DRV_DEFAULT_LOGIN),(T_DRIVER.DRV_DEFAULT_PASSWORD),(T_DRIVER.DRV_DEFAULT_DESC),(T_DRIVER.DRV_ENABLED)");
            } else {
                StringBuffer sb = new StringBuffer("DRV_ID");
                for (Iterator i = propertyList.keyIterator(); i.hasNext();) {
                    String selectedFieldName = (String) i.next();
                    int selectedFieldId = selectedFieldName.hashCode();
                    switch (selectedFieldId) {
                        case 2025166642: {  //column DRV_ID
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_ID");

                            break;
                        }
                        case 1609667419: {  //field drvClassName
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_CLASS_NAME");

                            break;
                        }
                        case 334190395: {  //column DRV_INDEX
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_INDEX");

                            break;
                        }
                        case 1931173971: {  //field drvName
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_NAME");

                            break;
                        }
                        case -2133227664: {  //field drvDefaultLogin
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_DEFAULT_LOGIN");

                            break;
                        }
                        case 989259984: {  //column DRV_DEFAULT_PASSWORD
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_DEFAULT_PASSWORD");

                            break;
                        }
                        case -223494294: {  //column DRV_ENABLED
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_ENABLED");

                            break;
                        }
                        case 2067830598: {  //column DRV_DEFAULT_DESC
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_DEFAULT_DESC");

                            break;
                        }
                        case -314086092: {  //column DRV_DEFAULT_LOGIN
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_DEFAULT_LOGIN");

                            break;
                        }
                        case -1734394374: {  //column DRV_DEFAULT_URL
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_DEFAULT_URL");

                            break;
                        }
                        case 565106402: {  //column DRV_NAME
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_NAME");

                            break;
                        }
                        case 1480355961: {  //field drvEnabled
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_ENABLED");

                            break;
                        }
                        case 1177864618: {  //field drvDefaultDesc
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_DEFAULT_DESC");

                            break;
                        }
                        case 95864035: {  //field drvId
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_ID");

                            break;
                        }
                        case 840061236: {  //field drvDefaultPassword
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_DEFAULT_PASSWORD");

                            break;
                        }
                        case -267387894: {  //field drvIndex
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_INDEX");

                            break;
                        }
                        case -654724298: {  //field drvDefaultUrl
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_DEFAULT_URL");

                            break;
                        }
                        case 1221204009: {  //column DRV_CLASS_NAME
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_DRIVER.DRV_CLASS_NAME");

                            break;
                        }
                        default: {
                            // default
                        }
                    }
                }
                _statement_.append(sb.toString());
            }
            _statement_.append(" FROM T_DRIVER");
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
                        case 2025166642: {  //column DRV_ID
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1609667419: {  //field drvClassName
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_CLASS_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 334190395: {  //column DRV_INDEX
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_INDEX");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1931173971: {  //field drvName
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -2133227664: {  //field drvDefaultLogin
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_DEFAULT_LOGIN");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 989259984: {  //column DRV_DEFAULT_PASSWORD
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_DEFAULT_PASSWORD");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -223494294: {  //column DRV_ENABLED
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_ENABLED");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 2067830598: {  //column DRV_DEFAULT_DESC
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_DEFAULT_DESC");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -314086092: {  //column DRV_DEFAULT_LOGIN
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_DEFAULT_LOGIN");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1734394374: {  //column DRV_DEFAULT_URL
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_DEFAULT_URL");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 565106402: {  //column DRV_NAME
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1480355961: {  //field drvEnabled
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_ENABLED");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1177864618: {  //field drvDefaultDesc
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_DEFAULT_DESC");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 95864035: {  //field drvId
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 840061236: {  //field drvDefaultPassword
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_DEFAULT_PASSWORD");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -267387894: {  //field drvIndex
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_INDEX");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -654724298: {  //field drvDefaultUrl
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_DEFAULT_URL");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1221204009: {  //column DRV_CLASS_NAME
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_DRIVER.DRV_CLASS_NAME");
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
                    TDriverKey $tableKey$ = new TDriverKey(_rs_.getInt(1));
                    TDriverDTO data = new TDriverDTO();
                    data.setDrvId(_rs_.getInt(2));
                    data.setDrvName(_rs_.getString(3));
                    data.setDrvIndex(_rs_.getInt(4));
                    data.setDrvClassName(_rs_.getString(5));
                    data.setDrvDefaultUrl(_rs_.getString(6));
                    data.setDrvDefaultLogin(_rs_.getString(7));
                    data.setDrvDefaultPassword(_rs_.getString(8));
                    data.setDrvDefaultDesc(_rs_.getString(9));
                    data.setDrvEnabled(_rs_.getInt(10));
                    list.add(data);
                }
            } else {
                while (($max$ < 0 || $count$ <= $max$) && _rs_.next()) {
                    $count$++;
                    int $col$ = 2;
                    TDriverKey $tableKey$ = new TDriverKey(_rs_.getInt(1));
                    TDriverDTO data = new TDriverDTO();
                    for (Iterator i = propertyList.keySet().iterator(); i.hasNext();) {
                        String selectedFieldName = (String) i.next();
                        int selectedFieldId = selectedFieldName.hashCode();
                        switch (selectedFieldId) {
                            case 2025166642: {  //column DRV_ID
                                data.setDrvId(_rs_.getInt($col$++));

                                break;
                            }
                            case 1609667419: {  //field drvClassName
                                data.setDrvClassName(_rs_.getString($col$++));

                                break;
                            }
                            case 334190395: {  //column DRV_INDEX
                                data.setDrvIndex(_rs_.getInt($col$++));

                                break;
                            }
                            case 1931173971: {  //field drvName
                                data.setDrvName(_rs_.getString($col$++));

                                break;
                            }
                            case -2133227664: {  //field drvDefaultLogin
                                data.setDrvDefaultLogin(_rs_.getString($col$++));

                                break;
                            }
                            case 989259984: {  //column DRV_DEFAULT_PASSWORD
                                data.setDrvDefaultPassword(_rs_.getString($col$++));

                                break;
                            }
                            case -223494294: {  //column DRV_ENABLED
                                data.setDrvEnabled(_rs_.getInt($col$++));

                                break;
                            }
                            case 2067830598: {  //column DRV_DEFAULT_DESC
                                data.setDrvDefaultDesc(_rs_.getString($col$++));

                                break;
                            }
                            case -314086092: {  //column DRV_DEFAULT_LOGIN
                                data.setDrvDefaultLogin(_rs_.getString($col$++));

                                break;
                            }
                            case -1734394374: {  //column DRV_DEFAULT_URL
                                data.setDrvDefaultUrl(_rs_.getString($col$++));

                                break;
                            }
                            case 565106402: {  //column DRV_NAME
                                data.setDrvName(_rs_.getString($col$++));

                                break;
                            }
                            case 1480355961: {  //field drvEnabled
                                data.setDrvEnabled(_rs_.getInt($col$++));

                                break;
                            }
                            case 1177864618: {  //field drvDefaultDesc
                                data.setDrvDefaultDesc(_rs_.getString($col$++));

                                break;
                            }
                            case 95864035: {  //field drvId
                                data.setDrvId(_rs_.getInt($col$++));

                                break;
                            }
                            case 840061236: {  //field drvDefaultPassword
                                data.setDrvDefaultPassword(_rs_.getString($col$++));

                                break;
                            }
                            case -267387894: {  //field drvIndex
                                data.setDrvIndex(_rs_.getInt($col$++));

                                break;
                            }
                            case -654724298: {  //field drvDefaultUrl
                                data.setDrvDefaultUrl(_rs_.getString($col$++));

                                break;
                            }
                            case 1221204009: {  //column DRV_CLASS_NAME
                                data.setDrvClassName(_rs_.getString($col$++));

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
            return (TDriverDTO[]) list.toArray(new TDriverDTO[list.size()]);
        } catch (SQLException sqlExcp) {
            throw new SQLException("DataRetrievalException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TDriverDTO[] select(TDriverPropertyList propertyList) throws SQLException {
        return select(propertyList, (TDriverFilter) null);
    }

}