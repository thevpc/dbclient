package net.thevpc.dbclient.plugin.system.configmanager.xmlconfig;

import net.thevpc.dbclient.api.configmanager.DBCDriverInfo;
import net.thevpc.dbclient.api.configmanager.DBCMessageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 11 sept. 2007 20:50:14
 */
public class DBCXMLConfigApplicationModel implements Serializable {
    ArrayList<Integer> sessions = new ArrayList<Integer>();
    HashMap<Integer, DBCDriverInfo> drivers = new HashMap<Integer, DBCDriverInfo>();
    HashMap<String, String> props = new HashMap<String, String>();
    HashMap<String, DBCMessageInfo> messages = new HashMap<String, DBCMessageInfo>();

}
