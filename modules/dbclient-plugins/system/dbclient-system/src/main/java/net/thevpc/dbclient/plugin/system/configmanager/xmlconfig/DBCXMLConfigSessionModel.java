package net.thevpc.dbclient.plugin.system.configmanager.xmlconfig;

import net.thevpc.dbclient.api.configmanager.DBCSessionInfo;
import net.thevpc.dbclient.api.configmanager.DBCUiNodePropertyInfo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 11 sept. 2007 20:50:14
 */
public class DBCXMLConfigSessionModel implements Serializable {

    DBCSessionInfo sessionConfig = new DBCSessionInfo();
    HashMap<String, DBCUiNodePropertyInfo> uiNodeProperties = new HashMap<String, DBCUiNodePropertyInfo>();
    HashMap<String, String> sessionProps = new HashMap<String, String>();

    public DBCXMLConfigSessionModel() {
    }
}