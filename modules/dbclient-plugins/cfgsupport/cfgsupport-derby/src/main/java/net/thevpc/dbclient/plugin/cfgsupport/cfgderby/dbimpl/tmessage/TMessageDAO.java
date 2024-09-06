package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tmessage;

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
public class TMessageDAO {
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
    public TMessageDAO() {
        super();
    }

    /**
     * DataAccessObject Constructor
     */
    public TMessageDAO(Connection con) {
        super();
        setConnection(con);
    }

    /**
     * DataAccessObject Constructor
     *
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TMessageKey insert(TMessageDTO data) throws SQLException {
        // START Prologue initialization
        java.lang.String msgId = null;
        java.lang.String msgLocName = null;
        java.lang.String msgData = null;
        // END   Prologue initialization
        // START Prologue checking
        if (!data.containsMsgId()) {
            throw new SQLException("RequiredFieldOnInsertException msgId");
        }
        if (!data.containsMsgLocName()) {
            throw new SQLException("RequiredFieldOnInsertException msgLocName");
        }
        if (!data.containsMsgData()) {
            throw new SQLException("RequiredFieldOnInsertException msgData");
        }
        // END  Prologue checking

        Connection _conn_ = null;
        String _statement_ = null;
        PreparedStatement _prepStmt_ = null;
        try {
            _conn_ = getConnection();
            msgId = (data.getMsgId());
            msgLocName = (data.getMsgLocName());

            // START object-definition.do<name=TMessage>.userCode.preInsert
            // code retreived from object-definition.do<name=TMessage>.userCode.preInsert
            // END   object-definition.do<name=TMessage>.userCode.preInsert
            // START Local Fields Updates
            for (Iterator i = data.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case -520642774: {  //field msgLocName
                        // START object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.preInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.preInsert
                        msgLocName = (data.getMsgLocName());
                        // START object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.postInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.postInsert
                        break;
                    }
                    case -471701048: {  //column MSG_DATA
                        // START object-definition.do<name=TMessage>.field<name=msgData>.userCode.preInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgData>.userCode.preInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgData>.userCode.preInsert
                        msgData = (data.getMsgData());
                        // START object-definition.do<name=TMessage>.field<name=msgData>.userCode.postInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgData>.userCode.postInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgData>.userCode.postInsert
                        break;
                    }
                    case 1343251147: {  //field msgData
                        // START object-definition.do<name=TMessage>.field<name=msgData>.userCode.preInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgData>.userCode.preInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgData>.userCode.preInsert
                        msgData = (data.getMsgData());
                        // START object-definition.do<name=TMessage>.field<name=msgData>.userCode.postInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgData>.userCode.postInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgData>.userCode.postInsert
                        break;
                    }
                    case -34489496: {  //column MSG_LOC_NAME
                        // START object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.preInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.preInsert
                        msgLocName = (data.getMsgLocName());
                        // START object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.postInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgLocName>.userCode.postInsert
                        break;
                    }
                    case -2011661639: {  //column MSG_ID
                        // START object-definition.do<name=TMessage>.field<name=msgId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgId>.userCode.preInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgId>.userCode.preInsert
                        msgId = (data.getMsgId());
                        // START object-definition.do<name=TMessage>.field<name=msgId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgId>.userCode.postInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgId>.userCode.postInsert
                        break;
                    }
                    case 104191100: {  //field msgId
                        // START object-definition.do<name=TMessage>.field<name=msgId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgId>.userCode.preInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgId>.userCode.preInsert
                        msgId = (data.getMsgId());
                        // START object-definition.do<name=TMessage>.field<name=msgId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TMessage>.field<name=msgId>.userCode.postInsert
                        // END   object-definition.do<name=TMessage>.field<name=msgId>.userCode.postInsert
                        break;
                    }
                    default: {
                        throw new SQLException("UnknownFieldException" + selectedFieldName);
                    }
                }
            }
            // END   Local Fields Updates

            // START Database persistance
            _statement_ = "INSERT INTO T_MESSAGE(MSG_ID, MSG_LOC_NAME, MSG_DATA) VALUES (?, ?, ?)";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setString(1, msgId);
            _prepStmt_.setString(2, msgLocName);
            _prepStmt_.setString(3, msgData);
            _prepStmt_.executeUpdate();
            _prepStmt_.close();

            // START object-definition.do<name=TMessage>.userCode.postInsert
            // code retreived from object-definition.do<name=TMessage>.userCode.postInsert
            // END   object-definition.do<name=TMessage>.userCode.postInsert
            // END   Database persistance

            // returning Identifier;
            return data.getTMessageKey();
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
    public void update(TMessageDTO data) throws SQLException {
        if (data.size() == 0) {
            return;
        }
        // START Prologue Cheking
        // END   ForbiddenFieldOnUpdate Cheking

        // START object-definition.do<name=TMessage>.userCode.preUpdate
        // code retreived from object-definition.do<name=TMessage>.userCode.preUpdate
        // END   object-definition.do<name=TMessage>.userCode.preUpdate
        Connection _conn_;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer();
            int _ucount_;
            PreparedStatement _prepStmt_ = null;
            boolean _firstColumn_ = true;
            _statement_.append("UPDATE T_MESSAGE SET ");
            if (data.containsMsgData()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("MSG_DATA=? ");
            }
            _statement_.append(" WHERE MSG_LOC_NAME = ?  AND MSG_ID = ? ");
            _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int _pos_ = 0;
            if (data.containsMsgData()) {
                _prepStmt_.setString(++_pos_, data.getMsgData());
            }
            _prepStmt_.setString(++_pos_, data.getMsgLocName());
            _prepStmt_.setString(++_pos_, data.getMsgId());
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("UpdateDataException");
            }
            // START object-definition.do<name=TMessage>.userCode.postUpdate
            // code retreived from object-definition.do<name=TMessage>.userCode.postUpdate
            // END   object-definition.do<name=TMessage>.userCode.postUpdate
        } catch (SQLException sqlExcp) {
            throw new SQLException("UpdateDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public void delete(TMessageKey key) throws SQLException {
        Connection _conn_;
        try {
            _conn_ = getConnection();
            // START object-definition.do<name=TMessage>.userCode.preDelete
            // code retreived from object-definition.do<name=TMessage>.userCode.preDelete
            // END   object-definition.do<name=TMessage>.userCode.preDelete
            int _ucount_;
            String _statement_;
            PreparedStatement _prepStmt_;
            _statement_ = "DELETE FROM T_MESSAGE WHERE MSG_LOC_NAME = ?  AND MSG_ID = ? ";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setString(1, (key.getMsgLocName()));
            _prepStmt_.setString(2, (key.getMsgId()));
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("RemoveDataException");
            }
            // START object-definition.do<name=TMessage>.userCode.postDelete
            // code retreived from object-definition.do<name=TMessage>.userCode.postDelete
            // END   object-definition.do<name=TMessage>.userCode.postDelete
        } catch (SQLException sqlExcp) {
            throw new SQLException("RemoveDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public int delete(TMessageFilter criteria) throws SQLException {
        PreparedStatement _prepStmt_ = null;
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("DELETE FROM T_MESSAGE");
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
    public TMessageDTO select(TMessagePropertyList propertyList, TMessageKey primaryKey) throws SQLException {
        String where = "T_MESSAGE.MSG_ID = ? AND T_MESSAGE.MSG_LOC_NAME = ?";
        TMessageFilter criteria = new TMessageFilter();
        criteria.setWhereClause(where);
        criteria.setString(1, primaryKey.getMsgId());
        criteria.setString(2, primaryKey.getMsgLocName());
        TMessageDTO[] _found_ = select(propertyList, criteria);
        if (_found_.length > 0) {
            return _found_[0];
        }
        return null;

    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TMessageDTO[] select(TMessagePropertyList propertyList, TMessageFilter criteria) throws SQLException {
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("SELECT ");
            if (criteria != null && criteria.isDistinct()) {
                _statement_.append("DISTINCT ");
            }
            if (propertyList == null) {
                _statement_.append("(T_MESSAGE.MSG_ID),(T_MESSAGE.MSG_LOC_NAME),(T_MESSAGE.MSG_ID),(T_MESSAGE.MSG_LOC_NAME),(T_MESSAGE.MSG_DATA)");
            } else {
                StringBuffer sb = new StringBuffer("MSG_ID,MSG_LOC_NAME");
                for (Iterator i = propertyList.keyIterator(); i.hasNext();) {
                    String selectedFieldName = (String) i.next();
                    int selectedFieldId = selectedFieldName.hashCode();
                    switch (selectedFieldId) {
                        case -520642774: {  //field msgLocName
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_MESSAGE.MSG_LOC_NAME");

                            break;
                        }
                        case -471701048: {  //column MSG_DATA
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_MESSAGE.MSG_DATA");

                            break;
                        }
                        case 1343251147: {  //field msgData
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_MESSAGE.MSG_DATA");

                            break;
                        }
                        case -34489496: {  //column MSG_LOC_NAME
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_MESSAGE.MSG_LOC_NAME");

                            break;
                        }
                        case -2011661639: {  //column MSG_ID
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_MESSAGE.MSG_ID");

                            break;
                        }
                        case 104191100: {  //field msgId
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_MESSAGE.MSG_ID");

                            break;
                        }
                        default: {
                            // default
                        }
                    }
                }
                _statement_.append(sb.toString());
            }
            _statement_.append(" FROM T_MESSAGE");
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
                        case -520642774: {  //field msgLocName
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_MESSAGE.MSG_LOC_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -471701048: {  //column MSG_DATA
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_MESSAGE.MSG_DATA");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1343251147: {  //field msgData
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_MESSAGE.MSG_DATA");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -34489496: {  //column MSG_LOC_NAME
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_MESSAGE.MSG_LOC_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -2011661639: {  //column MSG_ID
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_MESSAGE.MSG_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 104191100: {  //field msgId
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_MESSAGE.MSG_ID");
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
                    TMessageKey $tableKey$ = new TMessageKey(_rs_.getString(1), _rs_.getString(2));
                    TMessageDTO data = new TMessageDTO();
                    data.setMsgId(_rs_.getString(3));
                    data.setMsgLocName(_rs_.getString(4));
                    data.setMsgData(_rs_.getString(5));
                    list.add(data);
                }
            } else {
                while (($max$ < 0 || $count$ <= $max$) && _rs_.next()) {
                    $count$++;
                    int $col$ = 3;
                    TMessageKey $tableKey$ = new TMessageKey(_rs_.getString(1), _rs_.getString(2));
                    TMessageDTO data = new TMessageDTO();
                    for (Iterator i = propertyList.keySet().iterator(); i.hasNext();) {
                        String selectedFieldName = (String) i.next();
                        int selectedFieldId = selectedFieldName.hashCode();
                        switch (selectedFieldId) {
                            case -520642774: {  //field msgLocName
                                data.setMsgLocName(_rs_.getString($col$++));

                                break;
                            }
                            case -471701048: {  //column MSG_DATA
                                data.setMsgData(_rs_.getString($col$++));

                                break;
                            }
                            case 1343251147: {  //field msgData
                                data.setMsgData(_rs_.getString($col$++));

                                break;
                            }
                            case -34489496: {  //column MSG_LOC_NAME
                                data.setMsgLocName(_rs_.getString($col$++));

                                break;
                            }
                            case -2011661639: {  //column MSG_ID
                                data.setMsgId(_rs_.getString($col$++));

                                break;
                            }
                            case 104191100: {  //field msgId
                                data.setMsgId(_rs_.getString($col$++));

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
            return (TMessageDTO[]) list.toArray(new TMessageDTO[list.size()]);
        } catch (SQLException sqlExcp) {
            throw new SQLException("DataRetrievalException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TMessageDTO[] select(TMessagePropertyList propertyList) throws SQLException {
        return select(propertyList, (TMessageFilter) null);
    }

}