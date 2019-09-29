package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsession;

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
public class TSessionDAO {
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
    public TSessionDAO() {
        super();
    }

    /**
     * DataAccessObject Constructor
     */
    public TSessionDAO(Connection con) {
        super();
        setConnection(con);
    }

    /**
     * DataAccessObject Constructor
     *
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TSessionKey insert(TSessionDTO data) throws SQLException {
        // START Prologue initialization
        int sesId = 0;
        java.lang.String sesName = null;
        java.lang.String sesPath = null;
        int sesIndex = 0;
        java.lang.String sesCnxUrl = null;
        java.lang.String sesCnxDriver = null;
        java.lang.String sesCnxLogin = null;
        java.lang.String sesCnxPassword = null;
        int sesCnxAutocommit = 0;
        int sesCnxHoldability = 0;
        int sesCnxTransIsolation = 0;
        int sesCnxReadOnly = 0;
        java.lang.String sesCnxOpenScript = null;
        java.lang.String sesCnxCloseScript = null;
        java.sql.Date sesCnxCreated = null;
        java.sql.Date sesCnxLastUpdated = null;
        java.lang.String sesCnxFactoryName = null;
        int sesAutoConnect = 0;
        java.lang.String sesDesc = null;
        boolean sesAskForPassword = false;
        // END   Prologue initialization
        // START Prologue checking
        if (data.containsSesId()) {
            throw new SQLException("ForbiddenFieldOnInsertException sesId");
        }
        if (!data.containsSesName()) {
            throw new SQLException("RequiredFieldOnInsertException sesName");
        }
        if (!data.containsSesIndex()) {
            throw new SQLException("RequiredFieldOnInsertException sesIndex");
        }
        if (!data.containsSesCnxUrl()) {
            throw new SQLException("RequiredFieldOnInsertException sesCnxUrl");
        }
        if (!data.containsSesCnxDriver()) {
            throw new SQLException("RequiredFieldOnInsertException sesCnxDriver");
        }
        // END  Prologue checking

        Connection _conn_ = null;
        String _statement_ = null;
        PreparedStatement _prepStmt_ = null;
        try {
            _conn_ = getConnection();

            // START object-definition.do<name=TSession>.userCode.preInsert
            // code retreived from object-definition.do<name=TSession>.userCode.preInsert
            // END   object-definition.do<name=TSession>.userCode.preInsert
            // START Local Fields Updates
            for (Iterator i = data.keySet().iterator(); i.hasNext();) {
                String selectedFieldName = (String) i.next();
                int selectedFieldId = selectedFieldName.hashCode();
                switch (selectedFieldId) {
                    case -1703376515: {  //field sesCnxLogin
                        // START object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.preInsert
                        sesCnxLogin = (data.getSesCnxLogin());
                        // START object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.postInsert
                        break;
                    }
                    case 1983877772: {  //field sesName
                        // START object-definition.do<name=TSession>.field<name=sesName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesName>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesName>.userCode.preInsert
                        sesName = (data.getSesName());
                        // START object-definition.do<name=TSession>.field<name=sesName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesName>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesName>.userCode.postInsert
                        break;
                    }
                    case 1578327522: {  //column SES_CNX_LAST_UPDATED
                        // START object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.preInsert
                        sesCnxLastUpdated = (data.getSesCnxLastUpdated());
                        // START object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.postInsert
                        break;
                    }
                    case -97993273: {  //column SES_CNX_TRANS_ISOLATION
                        // START object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.preInsert
                        sesCnxTransIsolation = (data.getSesCnxTransIsolation());
                        // START object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.postInsert
                        break;
                    }
                    case -1569337598: {  //column SES_CNX_CLOSE_SCRIPT
                        // START object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.preInsert
                        sesCnxCloseScript = (data.getSesCnxCloseScript());
                        // START object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.postInsert
                        break;
                    }
                    case -1832342439: {  //field sesCnxLastUpdated
                        // START object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.preInsert
                        sesCnxLastUpdated = (data.getSesCnxLastUpdated());
                        // START object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLastUpdated>.userCode.postInsert
                        break;
                    }
                    case 1920628383: {  //field sesCnxHoldability
                        // START object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.preInsert
                        sesCnxHoldability = (data.getSesCnxHoldability());
                        // START object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.postInsert
                        break;
                    }
                    case -851990416: {  //column SES_CNX_OPEN_SCRIPT
                        // START object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.preInsert
                        sesCnxOpenScript = (data.getSesCnxOpenScript());
                        // START object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.postInsert
                        break;
                    }
                    case -2096043063: {  //column SES_NAME
                        // START object-definition.do<name=TSession>.field<name=sesName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesName>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesName>.userCode.preInsert
                        sesName = (data.getSesName());
                        // START object-definition.do<name=TSession>.field<name=sesName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesName>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesName>.userCode.postInsert
                        break;
                    }
                    case 123921404: {  //field sesCnxCreated
                        // START object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.preInsert
                        sesCnxCreated = (data.getSesCnxCreated());
                        // START object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.postInsert
                        break;
                    }
                    case -2067964200: {  //column SES_CNX_CREATED
                        // START object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.preInsert
                        sesCnxCreated = (data.getSesCnxCreated());
                        // START object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxCreated>.userCode.postInsert
                        break;
                    }
                    case -426140475: {  //column SES_CNX_READ_ONLY
                        // START object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.preInsert
                        sesCnxReadOnly = (data.getSesCnxReadOnly());
                        // START object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.postInsert
                        break;
                    }
                    case 521217375: {  //column SES_CNX_URL
                        // START object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.preInsert
                        sesCnxUrl = (data.getSesCnxUrl());
                        // START object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.postInsert
                        break;
                    }
                    case 1153462222: {  //field sesCnxReadOnly
                        // START object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.preInsert
                        sesCnxReadOnly = (data.getSesCnxReadOnly());
                        // START object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxReadOnly>.userCode.postInsert
                        break;
                    }
                    case -546658640: {  //column SES_CNX_FACTORY_NAME
                        // START object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.preInsert
                        sesCnxFactoryName = (data.getSesCnxFactoryName());
                        // START object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.postInsert
                        break;
                    }
                    case -1009572458: {  //column SES_CNX_AUTOCOMMIT
                        // START object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.preInsert
                        sesCnxAutocommit = (data.getSesCnxAutocommit());
                        // START object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.postInsert
                        break;
                    }
                    case -1852458535: {  //column SES_ID
                        // START object-definition.do<name=TSession>.field<name=sesId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesId>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesId>.userCode.preInsert
                        sesId = (data.getSesId());
                        // START object-definition.do<name=TSession>.field<name=sesId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesId>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesId>.userCode.postInsert
                        break;
                    }
                    case 970193911: {  //field sesCnxCloseScript
                        // START object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.preInsert
                        sesCnxCloseScript = (data.getSesCnxCloseScript());
                        // START object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxCloseScript>.userCode.postInsert
                        break;
                    }
                    case -1491255148: {  //field sesCnxDriver
                        // START object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.preInsert
                        sesCnxDriver = (data.getSesCnxDriver());
                        // START object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.postInsert
                        break;
                    }
                    case 1983583890: {  //field sesDesc
                        // START object-definition.do<name=TSession>.field<name=sesDesc>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesDesc>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesDesc>.userCode.preInsert
                        sesDesc = (data.getSesDesc());
                        // START object-definition.do<name=TSession>.field<name=sesDesc>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesDesc>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesDesc>.userCode.postInsert
                        break;
                    }
                    case 1983937574: {  //field sesPath
                        // START object-definition.do<name=TSession>.field<name=sesPath>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesPath>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesPath>.userCode.preInsert
                        sesPath = (data.getSesPath());
                        // START object-definition.do<name=TSession>.field<name=sesPath>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesPath>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesPath>.userCode.postInsert
                        break;
                    }
                    case -285812597: {  //column SES_CNX_PASSWORD
                        // START object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.preInsert
                        sesCnxPassword = (data.getSesCnxPassword());
                        // START object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.postInsert
                        break;
                    }
                    case 1430764920: {  //column SES_AUTO_CONNECT
                        // START object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.preInsert
                        sesAutoConnect = (data.getSesAutoConnect());
                        // START object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.postInsert
                        break;
                    }
                    case -2096336945: {  //column SES_DESC
                        // START object-definition.do<name=TSession>.field<name=sesDesc>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesDesc>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesDesc>.userCode.preInsert
                        sesDesc = (data.getSesDesc());
                        // START object-definition.do<name=TSession>.field<name=sesDesc>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesDesc>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesDesc>.userCode.postInsert
                        break;
                    }
                    case 793343512: {  //column SES_CNX_DRIVER
                        // START object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.preInsert
                        sesCnxDriver = (data.getSesCnxDriver());
                        // START object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxDriver>.userCode.postInsert
                        break;
                    }
                    case -2095983261: {  //column SES_PATH
                        // START object-definition.do<name=TSession>.field<name=sesPath>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesPath>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesPath>.userCode.preInsert
                        sesPath = (data.getSesPath());
                        // START object-definition.do<name=TSession>.field<name=sesPath>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesPath>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesPath>.userCode.postInsert
                        break;
                    }
                    case 1314149658: {  //field sesAutoConnect
                        // START object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.preInsert
                        sesAutoConnect = (data.getSesAutoConnect());
                        // START object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesAutoConnect>.userCode.postInsert
                        break;
                    }
                    case 1328944673: {  //field sesCnxOpenScript
                        // START object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.preInsert
                        sesCnxOpenScript = (data.getSesCnxOpenScript());
                        // START object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxOpenScript>.userCode.postInsert
                        break;
                    }
                    case -557064396: {  //column SES_INDEX
                        // START object-definition.do<name=TSession>.field<name=sesIndex>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesIndex>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesIndex>.userCode.preInsert
                        sesIndex = (data.getSesIndex());
                        // START object-definition.do<name=TSession>.field<name=sesIndex>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesIndex>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesIndex>.userCode.postInsert
                        break;
                    }
                    case -761539453: {  //field sesCnxUrl
                        // START object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.preInsert
                        sesCnxUrl = (data.getSesCnxUrl());
                        // START object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxUrl>.userCode.postInsert
                        break;
                    }
                    case 1981094779: {  //column SES_CNX_HOLDABILITY
                        // START object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.preInsert
                        sesCnxHoldability = (data.getSesCnxHoldability());
                        // START object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxHoldability>.userCode.postInsert
                        break;
                    }
                    case 997795186: {  //field sesCnxTransIsolation
                        // START object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.preInsert
                        sesCnxTransIsolation = (data.getSesCnxTransIsolation());
                        // START object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxTransIsolation>.userCode.postInsert
                        break;
                    }
                    case 890258889: {  //field sesCnxFactoryName
                        // START object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.preInsert
                        sesCnxFactoryName = (data.getSesCnxFactoryName());
                        // START object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxFactoryName>.userCode.postInsert
                        break;
                    }
                    case 109326684: {  //field sesId
                        // START object-definition.do<name=TSession>.field<name=sesId>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesId>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesId>.userCode.preInsert
                        sesId = (data.getSesId());
                        // START object-definition.do<name=TSession>.field<name=sesId>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesId>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesId>.userCode.postInsert
                        break;
                    }
                    case 1066686994: {  //field sesCnxAutocommit
                        // START object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.preInsert
                        sesCnxAutocommit = (data.getSesCnxAutocommit());
                        // START object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxAutocommit>.userCode.postInsert
                        break;
                    }
                    case 1366429937: {  //field sesIndex
                        // START object-definition.do<name=TSession>.field<name=sesIndex>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesIndex>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesIndex>.userCode.preInsert
                        sesIndex = (data.getSesIndex());
                        // START object-definition.do<name=TSession>.field<name=sesIndex>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesIndex>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesIndex>.userCode.postInsert
                        break;
                    }
                    case -1056835577: {  //field sesCnxPassword
                        // START object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.preInsert
                        sesCnxPassword = (data.getSesCnxPassword());
                        // START object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxPassword>.userCode.postInsert
                        break;
                    }
                    case -1629679783: {  //column SES_CNX_LOGIN
                        // START object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.preInsert
                        sesCnxLogin = (data.getSesCnxLogin());
                        // START object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.postInsert
                        break;
                    }
                    case 1125561612:
                    case 395042028:
                    {  //column SES_CNX_LOGIN
                        // START object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.preInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.preInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.preInsert
                        sesAskForPassword = (data.isAskForPassword());
                        // START object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.postInsert
                        // code retreived from object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.postInsert
                        // END   object-definition.do<name=TSession>.field<name=sesCnxLogin>.userCode.postInsert
                        break;
                    }
                    default: {
                        throw new SQLException("UnknownFieldException " + selectedFieldName);
                    }
                }
            }
            // END   Local Fields Updates

            // START Database persistance
            _statement_ = "INSERT INTO T_SESSION(SES_NAME, SES_PATH, SES_INDEX, SES_CNX_URL, SES_CNX_DRIVER, SES_CNX_LOGIN, SES_CNX_PASSWORD, SES_CNX_AUTOCOMMIT, SES_CNX_HOLDABILITY, SES_CNX_TRANS_ISOLATION, SES_CNX_READ_ONLY, SES_CNX_OPEN_SCRIPT, SES_CNX_CLOSE_SCRIPT, SES_CNX_CREATED, SES_CNX_LAST_UPDATED, SES_CNX_FACTORY_NAME, SES_AUTO_CONNECT, SES_DESC, SES_ASK_FOR_PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setString(1, sesName);
            _prepStmt_.setString(2, sesPath);
            _prepStmt_.setInt(3, sesIndex);
            _prepStmt_.setString(4, sesCnxUrl);
            _prepStmt_.setString(5, sesCnxDriver);
            _prepStmt_.setString(6, sesCnxLogin);
            _prepStmt_.setString(7, sesCnxPassword);
            _prepStmt_.setInt(8, sesCnxAutocommit);
            _prepStmt_.setInt(9, sesCnxHoldability);
            _prepStmt_.setInt(10, sesCnxTransIsolation);
            _prepStmt_.setInt(11, sesCnxReadOnly);
            _prepStmt_.setString(12, sesCnxOpenScript);
            _prepStmt_.setString(13, sesCnxCloseScript);
            _prepStmt_.setDate(14, sesCnxCreated);
            _prepStmt_.setDate(15, sesCnxLastUpdated);
            _prepStmt_.setString(16, sesCnxFactoryName);
            _prepStmt_.setInt(17, sesAutoConnect);
            _prepStmt_.setString(18, sesDesc);
            _prepStmt_.setInt(19, sesAskForPassword?1:0);
            _prepStmt_.executeUpdate();
            _prepStmt_.close();


            // START Sequence Handling
            String _selectNewIdStatement_ = "Select w.AUTOINCREMENTVALUE-1   From SYS.SYSCOLUMNS w  inner join SYS.SYSTABLES t on REFERENCEID=t.TABLEID where  w.COLUMNNAME='SES_ID' and t.TABLENAME='T_SESSION'";
            _prepStmt_ = _conn_.prepareStatement(_selectNewIdStatement_);
            ResultSet _rs_ = _prepStmt_.executeQuery();
            if (_rs_.next()) {
                data.setSesId(_rs_.getInt(1));
            }
            _rs_.close();
            _prepStmt_.close();
            // END Sequence Handling
            // START object-definition.do<name=TSession>.userCode.postInsert
            // code retreived from object-definition.do<name=TSession>.userCode.postInsert
            // END   object-definition.do<name=TSession>.userCode.postInsert
            // END   Database persistance

            // returning Identifier;
            return data.getTSessionKey();
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
    public void update(TSessionDTO data) throws SQLException {
        if (data.size() == 0) {
            return;
        }
        // START Prologue Cheking
        // END   ForbiddenFieldOnUpdate Cheking

        // START object-definition.do<name=TSession>.userCode.preUpdate
        // code retreived from object-definition.do<name=TSession>.userCode.preUpdate
        // END   object-definition.do<name=TSession>.userCode.preUpdate
        Connection _conn_;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer();
            int _ucount_;
            PreparedStatement _prepStmt_ = null;
            boolean _firstColumn_ = true;
            _statement_.append("UPDATE T_SESSION SET ");
            if (data.containsSesName()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_NAME=? ");
            }
            if (data.containsSesPath()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_PATH=? ");
            }
            if (data.containsSesIndex()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_INDEX=? ");
            }
            if (data.containsSesCnxUrl()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_URL=? ");
            }
            if (data.containsSesCnxDriver()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_DRIVER=? ");
            }
            if (data.containsSesCnxLogin()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_LOGIN=? ");
            }
            if (data.containsSesCnxPassword()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_PASSWORD=? ");
            }
            if (data.containsSesCnxAutocommit()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_AUTOCOMMIT=? ");
            }
            if (data.containsSesCnxHoldability()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_HOLDABILITY=? ");
            }
            if (data.containsSesCnxTransIsolation()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_TRANS_ISOLATION=? ");
            }
            if (data.containsSesCnxReadOnly()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_READ_ONLY=? ");
            }
            if (data.containsSesCnxOpenScript()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_OPEN_SCRIPT=? ");
            }
            if (data.containsSesCnxCloseScript()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_CLOSE_SCRIPT=? ");
            }
            if (data.containsSesCnxCreated()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_CREATED=? ");
            }
            if (data.containsSesCnxLastUpdated()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_LAST_UPDATED=? ");
            }
            if (data.containsSesCnxFactoryName()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_CNX_FACTORY_NAME=? ");
            }
            if (data.containsSesAutoConnect()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_AUTO_CONNECT=? ");
            }
            if (data.containsSesDesc()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_DESC=? ");
            }
            if (data.containsAskForPassword()) {
                if (_firstColumn_) {
                    _firstColumn_ = false;
                } else {
                    _statement_.append(", ");
                }
                _statement_.append("SES_ASK_FOR_PASSWORD=? ");
            }
            _statement_.append(" WHERE SES_ID = ? ");
            _prepStmt_ = _conn_.prepareStatement(_statement_.toString());
            int _pos_ = 0;
            if (data.containsSesName()) {
                _prepStmt_.setString(++_pos_, data.getSesName());
            }
            if (data.containsSesPath()) {
                _prepStmt_.setString(++_pos_, data.getSesPath());
            }
            if (data.containsSesIndex()) {
                _prepStmt_.setInt(++_pos_, data.getSesIndex());
            }
            if (data.containsSesCnxUrl()) {
                _prepStmt_.setString(++_pos_, data.getSesCnxUrl());
            }
            if (data.containsSesCnxDriver()) {
                _prepStmt_.setString(++_pos_, data.getSesCnxDriver());
            }
            if (data.containsSesCnxLogin()) {
                _prepStmt_.setString(++_pos_, data.getSesCnxLogin());
            }
            if (data.containsSesCnxPassword()) {
                _prepStmt_.setString(++_pos_, data.getSesCnxPassword());
            }
            if (data.containsSesCnxAutocommit()) {
                _prepStmt_.setInt(++_pos_, data.getSesCnxAutocommit());
            }
            if (data.containsSesCnxHoldability()) {
                _prepStmt_.setInt(++_pos_, data.getSesCnxHoldability());
            }
            if (data.containsSesCnxTransIsolation()) {
                _prepStmt_.setInt(++_pos_, data.getSesCnxTransIsolation());
            }
            if (data.containsSesCnxReadOnly()) {
                _prepStmt_.setInt(++_pos_, data.getSesCnxReadOnly());
            }
            if (data.containsSesCnxOpenScript()) {
                _prepStmt_.setString(++_pos_, data.getSesCnxOpenScript());
            }
            if (data.containsSesCnxCloseScript()) {
                _prepStmt_.setString(++_pos_, data.getSesCnxCloseScript());
            }
            if (data.containsSesCnxCreated()) {
                _prepStmt_.setDate(++_pos_, data.getSesCnxCreated());
            }
            if (data.containsSesCnxLastUpdated()) {
                _prepStmt_.setDate(++_pos_, data.getSesCnxLastUpdated());
            }
            if (data.containsSesCnxFactoryName()) {
                _prepStmt_.setString(++_pos_, data.getSesCnxFactoryName());
            }
            if (data.containsSesAutoConnect()) {
                _prepStmt_.setInt(++_pos_, data.getSesAutoConnect());
            }
            if (data.containsSesDesc()) {
                _prepStmt_.setString(++_pos_, data.getSesDesc());
            }
            if (data.containsAskForPassword()) {
                _prepStmt_.setInt(++_pos_, data.isAskForPassword()?1:0);
            }
            _prepStmt_.setInt(++_pos_, data.getSesId());
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("UpdateDataException");
            }
            // START object-definition.do<name=TSession>.userCode.postUpdate
            // code retreived from object-definition.do<name=TSession>.userCode.postUpdate
            // END   object-definition.do<name=TSession>.userCode.postUpdate
        } catch (SQLException sqlExcp) {
            throw new SQLException("UpdateDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public void delete(TSessionKey key) throws SQLException {
        Connection _conn_;
        try {
            _conn_ = getConnection();
            // START object-definition.do<name=TSession>.userCode.preDelete
            // code retreived from object-definition.do<name=TSession>.userCode.preDelete
            // END   object-definition.do<name=TSession>.userCode.preDelete
            int _ucount_;
            String _statement_;
            PreparedStatement _prepStmt_;
            _statement_ = "DELETE FROM T_SESSION WHERE SES_ID = ? ";
            _prepStmt_ = _conn_.prepareStatement(_statement_);
            _prepStmt_.setInt(1, (key.getSesId()));
            _ucount_ = _prepStmt_.executeUpdate();
            _prepStmt_.close();
            if (_ucount_ <= 0) {
                throw new SQLException("RemoveDataException");
            }
            // START object-definition.do<name=TSession>.userCode.postDelete
            // code retreived from object-definition.do<name=TSession>.userCode.postDelete
            // END   object-definition.do<name=TSession>.userCode.postDelete
        } catch (SQLException sqlExcp) {
            throw new SQLException("RemoveDataException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public int delete(TSessionFilter criteria) throws SQLException {
        PreparedStatement _prepStmt_ = null;
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("DELETE FROM T_SESSION");
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
    public TSessionDTO select(TSessionPropertyList propertyList, TSessionKey primaryKey) throws SQLException {
        String where = "T_SESSION.SES_ID = ?";
        TSessionFilter criteria = new TSessionFilter();
        criteria.setWhereClause(where);
        criteria.setInt(1, primaryKey.getSesId());
        TSessionDTO[] _found_ = select(propertyList, criteria);
        if (_found_.length > 0) {
            return _found_[0];
        }
        return null;

    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TSessionDTO[] select(TSessionPropertyList propertyList, TSessionFilter criteria) throws SQLException {
        Connection _conn_ = null;
        try {
            _conn_ = getConnection();
            StringBuffer _statement_ = new StringBuffer("SELECT ");
            if (criteria != null && criteria.isDistinct()) {
                _statement_.append("DISTINCT ");
            }
            if (propertyList == null) {
                _statement_.append("(T_SESSION.SES_ID),(T_SESSION.SES_ID),(T_SESSION.SES_NAME),(T_SESSION.SES_PATH),(T_SESSION.SES_INDEX),(T_SESSION.SES_CNX_URL),(T_SESSION.SES_CNX_DRIVER),(T_SESSION.SES_CNX_LOGIN),(T_SESSION.SES_CNX_PASSWORD),(T_SESSION.SES_CNX_AUTOCOMMIT),(T_SESSION.SES_CNX_HOLDABILITY),(T_SESSION.SES_CNX_TRANS_ISOLATION),(T_SESSION.SES_CNX_READ_ONLY),(T_SESSION.SES_CNX_OPEN_SCRIPT),(T_SESSION.SES_CNX_CLOSE_SCRIPT),(T_SESSION.SES_CNX_CREATED),(T_SESSION.SES_CNX_LAST_UPDATED),(T_SESSION.SES_CNX_FACTORY_NAME),(T_SESSION.SES_AUTO_CONNECT),(T_SESSION.SES_DESC),SES_ASK_FOR_PASSWORD");
            } else {
                StringBuffer sb = new StringBuffer("SES_ID");
                for (Iterator i = propertyList.keyIterator(); i.hasNext();) {
                    String selectedFieldName = (String) i.next();
                    int selectedFieldId = selectedFieldName.hashCode();
                    switch (selectedFieldId) {
                        case -1703376515: {  //field sesCnxLogin
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_LOGIN");

                            break;
                        }
                        case 1983877772: {  //field sesName
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_NAME");

                            break;
                        }
                        case 1578327522: {  //column SES_CNX_LAST_UPDATED
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_LAST_UPDATED");

                            break;
                        }
                        case -97993273: {  //column SES_CNX_TRANS_ISOLATION
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_TRANS_ISOLATION");

                            break;
                        }
                        case -1569337598: {  //column SES_CNX_CLOSE_SCRIPT
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_CLOSE_SCRIPT");

                            break;
                        }
                        case -1832342439: {  //field sesCnxLastUpdated
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_LAST_UPDATED");

                            break;
                        }
                        case 1920628383: {  //field sesCnxHoldability
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_HOLDABILITY");

                            break;
                        }
                        case -851990416: {  //column SES_CNX_OPEN_SCRIPT
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_OPEN_SCRIPT");

                            break;
                        }
                        case -2096043063: {  //column SES_NAME
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_NAME");

                            break;
                        }
                        case 123921404: {  //field sesCnxCreated
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_CREATED");

                            break;
                        }
                        case -2067964200: {  //column SES_CNX_CREATED
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_CREATED");

                            break;
                        }
                        case -426140475: {  //column SES_CNX_READ_ONLY
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_READ_ONLY");

                            break;
                        }
                        case 521217375: {  //column SES_CNX_URL
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_URL");

                            break;
                        }
                        case 1153462222: {  //field sesCnxReadOnly
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_READ_ONLY");

                            break;
                        }
                        case -546658640: {  //column SES_CNX_FACTORY_NAME
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_FACTORY_NAME");

                            break;
                        }
                        case -1009572458: {  //column SES_CNX_AUTOCOMMIT
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_AUTOCOMMIT");

                            break;
                        }
                        case -1852458535: {  //column SES_ID
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_ID");

                            break;
                        }
                        case 970193911: {  //field sesCnxCloseScript
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_CLOSE_SCRIPT");

                            break;
                        }
                        case -1491255148: {  //field sesCnxDriver
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_DRIVER");

                            break;
                        }
                        case 1983583890: {  //field sesDesc
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_DESC");

                            break;
                        }
                        case 1983937574: {  //field sesPath
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_PATH");

                            break;
                        }
                        case -285812597: {  //column SES_CNX_PASSWORD
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_PASSWORD");

                            break;
                        }
                        case 1430764920: {  //column SES_AUTO_CONNECT
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_AUTO_CONNECT");

                            break;
                        }
                        case -2096336945: {  //column SES_DESC
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_DESC");

                            break;
                        }
                        case 793343512: {  //column SES_CNX_DRIVER
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_DRIVER");

                            break;
                        }
                        case -2095983261: {  //column SES_PATH
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_PATH");

                            break;
                        }
                        case 1314149658: {  //field sesAutoConnect
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_AUTO_CONNECT");

                            break;
                        }
                        case 1328944673: {  //field sesCnxOpenScript
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_OPEN_SCRIPT");

                            break;
                        }
                        case -557064396: {  //column SES_INDEX
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_INDEX");

                            break;
                        }
                        case -761539453: {  //field sesCnxUrl
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_URL");

                            break;
                        }
                        case 1981094779: {  //column SES_CNX_HOLDABILITY
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_HOLDABILITY");

                            break;
                        }
                        case 997795186: {  //field sesCnxTransIsolation
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_TRANS_ISOLATION");

                            break;
                        }
                        case 890258889: {  //field sesCnxFactoryName
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_FACTORY_NAME");

                            break;
                        }
                        case 109326684: {  //field sesId
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_ID");

                            break;
                        }
                        case 1066686994: {  //field sesCnxAutocommit
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_AUTOCOMMIT");

                            break;
                        }
                        case 1366429937: {  //field sesIndex
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_INDEX");

                            break;
                        }
                        case -1056835577: {  //field sesCnxPassword
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_PASSWORD");

                            break;
                        }
                        case -1629679783: {  //column SES_CNX_LOGIN
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_CNX_LOGIN");

                            break;
                        }
                        case 314722773:
                        case -449117835:
                        case 1125561612:
                        case 395042028:
                        {  //column SES_ASK_FOR_PASSWORD
                            if (sb.length() > 0) {
                                sb.append(" , ");
                            }
                            sb.append("T_SESSION.SES_ASK_FOR_PASSWORD");

                            break;
                        }
                        default: {
                            // default
                        }
                    }
                }
                _statement_.append(sb.toString());
            }
            _statement_.append(" FROM T_SESSION");
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
                        case -1703376515: {  //field sesCnxLogin
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_LOGIN");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1983877772: {  //field sesName
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1578327522: {  //column SES_CNX_LAST_UPDATED
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_LAST_UPDATED");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -97993273: {  //column SES_CNX_TRANS_ISOLATION
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_TRANS_ISOLATION");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1569337598: {  //column SES_CNX_CLOSE_SCRIPT
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_CLOSE_SCRIPT");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1832342439: {  //field sesCnxLastUpdated
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_LAST_UPDATED");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1920628383: {  //field sesCnxHoldability
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_HOLDABILITY");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -851990416: {  //column SES_CNX_OPEN_SCRIPT
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_OPEN_SCRIPT");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -2096043063: {  //column SES_NAME
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 123921404: {  //field sesCnxCreated
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_CREATED");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -2067964200: {  //column SES_CNX_CREATED
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_CREATED");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -426140475: {  //column SES_CNX_READ_ONLY
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_READ_ONLY");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 521217375: {  //column SES_CNX_URL
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_URL");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1153462222: {  //field sesCnxReadOnly
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_READ_ONLY");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -546658640: {  //column SES_CNX_FACTORY_NAME
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_FACTORY_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1009572458: {  //column SES_CNX_AUTOCOMMIT
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_AUTOCOMMIT");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1852458535: {  //column SES_ID
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 970193911: {  //field sesCnxCloseScript
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_CLOSE_SCRIPT");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1491255148: {  //field sesCnxDriver
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_DRIVER");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1983583890: {  //field sesDesc
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_DESC");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1983937574: {  //field sesPath
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_PATH");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -285812597: {  //column SES_CNX_PASSWORD
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_PASSWORD");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1430764920: {  //column SES_AUTO_CONNECT
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_AUTO_CONNECT");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -2096336945: {  //column SES_DESC
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_DESC");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 793343512: {  //column SES_CNX_DRIVER
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_DRIVER");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -2095983261: {  //column SES_PATH
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_PATH");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1314149658: {  //field sesAutoConnect
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_AUTO_CONNECT");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1328944673: {  //field sesCnxOpenScript
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_OPEN_SCRIPT");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -557064396: {  //column SES_INDEX
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_INDEX");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -761539453: {  //field sesCnxUrl
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_URL");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1981094779: {  //column SES_CNX_HOLDABILITY
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_HOLDABILITY");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 997795186: {  //field sesCnxTransIsolation
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_TRANS_ISOLATION");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 890258889: {  //field sesCnxFactoryName
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_FACTORY_NAME");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 109326684: {  //field sesId
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_ID");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1066686994: {  //field sesCnxAutocommit
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_AUTOCOMMIT");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1366429937: {  //field sesIndex
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_INDEX");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1056835577: {  //field sesCnxPassword
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_PASSWORD");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case -1629679783: {  //column SES_CNX_LOGIN
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_CNX_LOGIN");
                            _statement_.append(" ").append(asc ? "ASC" : "DESC");
                            break;
                        }
                        case 1125561612:
                        case 395042028:
                        case 314722773:
                        case -449117835:
                        {  //column SES_ASK_FOR_PASSWORD
                            if (orderFirst) {
                                orderFirst = false;
                            } else {
                                _statement_.append(" , ");
                            }
                            _statement_.append("T_SESSION.SES_ASK_FOR_PASSWORD");
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
                    TSessionKey $tableKey$ = new TSessionKey(_rs_.getInt(1));
                    TSessionDTO data = new TSessionDTO();
                    data.setSesId(_rs_.getInt(2));
                    data.setSesName(_rs_.getString(3));
                    data.setSesPath(_rs_.getString(4));
                    data.setSesIndex(_rs_.getInt(5));
                    data.setSesCnxUrl(_rs_.getString(6));
                    data.setSesCnxDriver(_rs_.getString(7));
                    data.setSesCnxLogin(_rs_.getString(8));
                    data.setSesCnxPassword(_rs_.getString(9));
                    data.setSesCnxAutocommit(_rs_.getInt(10));
                    data.setSesCnxHoldability(_rs_.getInt(11));
                    data.setSesCnxTransIsolation(_rs_.getInt(12));
                    data.setSesCnxReadOnly(_rs_.getInt(13));
                    data.setSesCnxOpenScript(_rs_.getString(14));
                    data.setSesCnxCloseScript(_rs_.getString(15));
                    data.setSesCnxCreated(_rs_.getDate(16));
                    data.setSesCnxLastUpdated(_rs_.getDate(17));
                    data.setSesCnxFactoryName(_rs_.getString(18));
                    data.setSesAutoConnect(_rs_.getInt(19));
                    data.setSesDesc(_rs_.getString(20));
                    data.setAskForPassword(_rs_.getInt(21)!=0);
                    list.add(data);
                }
            } else {
                while (($max$ < 0 || $count$ <= $max$) && _rs_.next()) {
                    $count$++;
                    int $col$ = 2;
                    TSessionKey $tableKey$ = new TSessionKey(_rs_.getInt(1));
                    TSessionDTO data = new TSessionDTO();
                    for (Iterator i = propertyList.keySet().iterator(); i.hasNext();) {
                        String selectedFieldName = (String) i.next();
                        int selectedFieldId = selectedFieldName.hashCode();
                        switch (selectedFieldId) {
                            case -1703376515: {  //field sesCnxLogin
                                data.setSesCnxLogin(_rs_.getString($col$++));

                                break;
                            }
                            case 1983877772: {  //field sesName
                                data.setSesName(_rs_.getString($col$++));

                                break;
                            }
                            case 1578327522: {  //column SES_CNX_LAST_UPDATED
                                data.setSesCnxLastUpdated(_rs_.getDate($col$++));

                                break;
                            }
                            case -97993273: {  //column SES_CNX_TRANS_ISOLATION
                                data.setSesCnxTransIsolation(_rs_.getInt($col$++));

                                break;
                            }
                            case -1569337598: {  //column SES_CNX_CLOSE_SCRIPT
                                data.setSesCnxCloseScript(_rs_.getString($col$++));

                                break;
                            }
                            case -1832342439: {  //field sesCnxLastUpdated
                                data.setSesCnxLastUpdated(_rs_.getDate($col$++));

                                break;
                            }
                            case 1920628383: {  //field sesCnxHoldability
                                data.setSesCnxHoldability(_rs_.getInt($col$++));

                                break;
                            }
                            case -851990416: {  //column SES_CNX_OPEN_SCRIPT
                                data.setSesCnxOpenScript(_rs_.getString($col$++));

                                break;
                            }
                            case -2096043063: {  //column SES_NAME
                                data.setSesName(_rs_.getString($col$++));

                                break;
                            }
                            case 123921404: {  //field sesCnxCreated
                                data.setSesCnxCreated(_rs_.getDate($col$++));

                                break;
                            }
                            case -2067964200: {  //column SES_CNX_CREATED
                                data.setSesCnxCreated(_rs_.getDate($col$++));

                                break;
                            }
                            case -426140475: {  //column SES_CNX_READ_ONLY
                                data.setSesCnxReadOnly(_rs_.getInt($col$++));

                                break;
                            }
                            case 521217375: {  //column SES_CNX_URL
                                data.setSesCnxUrl(_rs_.getString($col$++));

                                break;
                            }
                            case 1153462222: {  //field sesCnxReadOnly
                                data.setSesCnxReadOnly(_rs_.getInt($col$++));

                                break;
                            }
                            case -546658640: {  //column SES_CNX_FACTORY_NAME
                                data.setSesCnxFactoryName(_rs_.getString($col$++));

                                break;
                            }
                            case -1009572458: {  //column SES_CNX_AUTOCOMMIT
                                data.setSesCnxAutocommit(_rs_.getInt($col$++));

                                break;
                            }
                            case -1852458535: {  //column SES_ID
                                data.setSesId(_rs_.getInt($col$++));

                                break;
                            }
                            case 970193911: {  //field sesCnxCloseScript
                                data.setSesCnxCloseScript(_rs_.getString($col$++));

                                break;
                            }
                            case -1491255148: {  //field sesCnxDriver
                                data.setSesCnxDriver(_rs_.getString($col$++));

                                break;
                            }
                            case 1983583890: {  //field sesDesc
                                data.setSesDesc(_rs_.getString($col$++));

                                break;
                            }
                            case 1983937574: {  //field sesPath
                                data.setSesPath(_rs_.getString($col$++));

                                break;
                            }
                            case -285812597: {  //column SES_CNX_PASSWORD
                                data.setSesCnxPassword(_rs_.getString($col$++));

                                break;
                            }
                            case 1430764920: {  //column SES_AUTO_CONNECT
                                data.setSesAutoConnect(_rs_.getInt($col$++));

                                break;
                            }
                            case -2096336945: {  //column SES_DESC
                                data.setSesDesc(_rs_.getString($col$++));

                                break;
                            }
                            case 793343512: {  //column SES_CNX_DRIVER
                                data.setSesCnxDriver(_rs_.getString($col$++));

                                break;
                            }
                            case -2095983261: {  //column SES_PATH
                                data.setSesPath(_rs_.getString($col$++));

                                break;
                            }
                            case 1314149658: {  //field sesAutoConnect
                                data.setSesAutoConnect(_rs_.getInt($col$++));

                                break;
                            }
                            case 1328944673: {  //field sesCnxOpenScript
                                data.setSesCnxOpenScript(_rs_.getString($col$++));

                                break;
                            }
                            case -557064396: {  //column SES_INDEX
                                data.setSesIndex(_rs_.getInt($col$++));

                                break;
                            }
                            case -761539453: {  //field sesCnxUrl
                                data.setSesCnxUrl(_rs_.getString($col$++));

                                break;
                            }
                            case 1981094779: {  //column SES_CNX_HOLDABILITY
                                data.setSesCnxHoldability(_rs_.getInt($col$++));

                                break;
                            }
                            case 997795186: {  //field sesCnxTransIsolation
                                data.setSesCnxTransIsolation(_rs_.getInt($col$++));

                                break;
                            }
                            case 890258889: {  //field sesCnxFactoryName
                                data.setSesCnxFactoryName(_rs_.getString($col$++));

                                break;
                            }
                            case 109326684: {  //field sesId
                                data.setSesId(_rs_.getInt($col$++));

                                break;
                            }
                            case 1066686994: {  //field sesCnxAutocommit
                                data.setSesCnxAutocommit(_rs_.getInt($col$++));

                                break;
                            }
                            case 1366429937: {  //field sesIndex
                                data.setSesIndex(_rs_.getInt($col$++));

                                break;
                            }
                            case -1056835577: {  //field sesCnxPassword
                                data.setSesCnxPassword(_rs_.getString($col$++));

                                break;
                            }
                            case -1629679783: {  //column SES_CNX_LOGIN
                                data.setSesCnxLogin(_rs_.getString($col$++));

                                break;
                            }
                            case 1125561612:
                            case 395042028:
                            {  //column SES_ASK_FOR_PASSWORD
                                data.setAskForPassword(_rs_.getInt($col$++)!=0);

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
            return (TSessionDTO[]) list.toArray(new TSessionDTO[list.size()]);
        } catch (SQLException sqlExcp) {
            throw new SQLException("DataRetrievalException " + sqlExcp);
        }
    }

    /**
     * @class:generator JBGen
     * @ejb:visibility client
     */
    public TSessionDTO[] select(TSessionPropertyList propertyList) throws SQLException {
        return select(propertyList, (TSessionFilter) null);
    }

}