/**
 * ====================================================================
 *             DBClient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */
package net.thevpc.dbclient.plugin.system;

import net.thevpc.common.prs.xml.XmlUtils;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.dbclient.api.DBCApplicationView;
import net.thevpc.dbclient.api.configmanager.*;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
import net.thevpc.dbclient.api.pluginmanager.DBCPluggableResourcesSupport;
import net.thevpc.common.prs.artset.ArtSet;
import net.thevpc.common.swing.plaf.UIManager2;
import net.thevpc.common.prs.plugin.PluginInfo;
import net.thevpc.common.swing.dialog.MessageDialogType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CancellationException;
import java.util.logging.Level;

import net.thevpc.common.prs.iconset.IconSetManager;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 16 nov. 2006 00:00:32
 */
@PluginInfo(
        iconSet = "net.thevpc.dbclient.plugin.system.viewmanager.iconset.dbclient-iconset-default"
)
public class DBCDefaultSystemPlugin extends DBCAbstractPlugin {
    public DBCDefaultSystemPlugin() {
    }

    /**
     * initialize Factory and MessageSet
     * <pre>
     *  getApplication().setDefaultFactoryConfigurations(createFactoryConfigurations(getMessageSet()));
     *  getApplication().getView().getMessageSet().addBundle(new MessageSetResourceBundleWrapper("net.thevpc.dbclient.plugin.system.viewmanager.messageset.DBClient", getApplication().getLocale(),getClass().getClassLoader()));
     * </pre>
     */
    @Override
    public void applicationInitializing() {
        //initialize URL Variables
        Map<String, Object> variables = getApplication().getVariables();
        variables.put("user.dir", System.getProperty("user.dir"));
        variables.put("user.home", System.getProperty("user.home"));
        variables.put("user.name", System.getProperty("user.name"));
        variables.put("app.dir", new Object() {

            @Override
            public String toString() {
                return getApplication().getWorkingDir().getPath();
            }
        });

        getApplication().getView().getMessageSet().addBundle("net.thevpc.dbclient.plugin.system.viewmanager.messageset.DBClient");
        //getApplication().setDefaultFactoryConfigurations(createFactoryConfigurations(getMessageSet()));
        IconSetManager.setClassLoaderForIconSetImports("net.thevpc.dbclient.plugin.system.viewmanager.iconset.dbclient-iconset-imports", getClass().getClassLoader());

    }

    @Override
    public void applicationConfiguring() {
        //now config is ok. add config MessageSetBundle to view
        getApplication().getView().getMessageSet().addBundle(getApplication().getConfig().getMessageSetBundle());
    }
    
    

    @Override
    public void pluginInstalled() {
        try {
            DBCApplicationConfig config = getApplication().getConfig();
            URL resource = getClass().getClassLoader().getResource("net/thevpc/dbclient/plugin/system/configmanager/install-defaults.xml");
            InputStream inputStream;
            inputStream = resource.openStream();
            Vector v = (Vector) XmlUtils.xmlToObject(inputStream,null,null);
            config.updateAll(v, DBCIfFoundAction.FAIL, DBCIfNotFoundAction.ADD);

//            resource = getClass().getClassLoader().getResource("net/vpc/dbclient/plugin/system/configmanager/install-driver-defaults.xml");
//            inputStream = resource.openStream();
//            v = (Vector) XmlUtils.xmlToObject(inputStream,null,null);
            config.updateAll(createDrivers(), DBCIfFoundAction.FAIL, DBCIfNotFoundAction.ADD);
        } catch (IOException ex) {
            getApplication().getLogger(DBCDefaultSystemPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private Vector createDrivers(){
        Vector<DBCDriverInfo> v=new Vector<DBCDriverInfo>();
        v.add(new DBCDriverInfo(v.size(),v.size(),"JDBC/ODBC",true,"sun.jdbc.odbc.JdbcOdbcDriver","jdbc:odbc:{database}","","",new URL[0], "jdbc:odbc:<i>databaseName</i><br><u>exemple :</u><br>jdbc:odbc:test<br>"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"Oracle Thin",true,"oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@{server}:1521:{database}","system","manager",new URL[0], "jdbc:oracle:thin:@<i>serverAddress</i>:<i>serverPort</i>:<i>databaseName</i><br><u>exemple :</u><br>jdbc:oracle:thin:@localhost:1521:orclParams :<br>user = system<br>password = manager ; serverPort = 1521<br>"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"Sybase",true,"connect.sybase.SybaseDriver","jdbc:sybase://{server}:{port}/{database}","guest","trustworthy",new URL[0], "jdbc:sybase://<i>serverAddress</i>[:<i>serverPort</i>]/pubs2<br><u>exemple :</u><br>jdbc:sybase://dbtest/pubs2<br><u>Params :</u><br>user = guest<br>password = trustworthy<br>serverPort = 1455<br>"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"DB2",true,"COM.ibm.db2.jdbc.app.DB2Driver","jdbc:db2:{database}","db2admin","db2admin",new URL[0], "jdbc:db2:<i>databaseName</i>\n" +
                "        <br><u>exemple :</u>\n" +
                "        <br>jdbc:db2:test\n" +
                "        <br><u>Params :</u>\n" +
                "        <br>user = db2admin\n" +
                "        <br>password = db2admin<br>"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"InstantDB",true,"com.lutris.instantdb.jdbc.idbDriver","jdbc:idb={database}","","",new URL[0], "jdbc:idb=<i>databaseName</i>\n" +
                "        <br><u>exemple :</u>\n" +
                "        <br>jdbc:db2=D:/einstein/work/runtime/resources/db/sgDb.prp"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"Interbase",true,"interbase.interclient.Driver","jdbc:interbase://{server]/{database}","SYSDBA","masterkey",new URL[0], "jdbc:interbase://<i>serverAddress</i>/<i>databaseName</i>\n" +
                "                    <br><u>exemple :</u>\n" +
                "                    <br>jdbc:interbase://localhost/D:/examples/employee.gdb\n" +
                "                    <br><u>Params :</u>\n" +
                "                    <br>user = SYSDBA\n" +
                "                    <br>password = masterkey"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"MSSQLServer/Avenir",true,"net.avenir.jdbc2.Driver","jdbc:AvenirDriver://{server}:{port}/{database}","sa","",new URL[0], "jdbc:AvenirDriver://<i>serverAddress</i>[:<i>serverPort</i>]/<i>databaseName</i>\n" +
                "                    <br><u>exemple :</u>\n" +
                "                    <br>jdbc:AvenirDriver://localhost:1433/master\n" +
                "                    <br><u>Params :</u>\n" +
                "                    <br>user = sa<br>password =\n" +
                "                    <br>serverPort = 1433"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"MSSQLServer/SequeLink",true,"com.merant.sequelink.jdbc.SequeLinkDriver","jdbc:sequelink://{server}:{port}:/databaseName={database}","sa","",new URL[0], "jdbc:sequelink://<i>serverAddress</i>[:<i>serverPort</i>]:/databaseName=<i>databaseName</i>\n" +
                "                    <br><u>exemple :</u>\n" +
                "                    <br>jdbc:sequelink://localhost:99996:/databaseName=master\n" +
                "                    <br><u>Params :</u><br>user = sa\n" +
                "                    <br>password = <br>serverPort = 99996"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"MSSQLServer",true,"com.microsoft.jdbc.sqlserver.SQLServerDriver","jdbc:microsoft:sqlserver://{server}:{port};databaseName={database}","sa","",new URL[0], "jdbc:microsoft:sqlserver://<i>serverAddress</i>:<i>serverPort</i>;databaseName=<i>databaseName</i>\n" +
                "                    <br><u>exemple :</u>\n" +
                "                    <br>jdbc:sequelink://localhost:99996:/databaseName=master\n" +
                "                    <br><u>Params :</u>\n" +
                "                    <br>user = sa\n" +
                "                    <br>password =\n" +
                "                    <br>serverPort = 99996<br>"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"MySQL",true,"org.gjt.mm.mysql.Driver","jdbc:mysql://{server}/{database}","root","",new URL[0], "jdbc:mysql://<i>serverAddress</i>/<i>databaseName</i>\n" +
                "                    <br><u>exemple :</u>\n" +
                "                    <br>jdbc:mysql://localhost/test\n" +
                "                    <br><u>Params :</u>\n" +
                "                    <br>user = root<br>password ="));
        v.add(new DBCDriverInfo(v.size(),v.size(),"McKoi",true,"com.mckoi.JDBCDriver","jdbc:mckoi://{server}:{port}/{database}/","","",new URL[0], "jdbc:mckoi://host[:<i>port</i>][/<i>schema</i>]/\n" +
                "                    <br><u>exemple :</u>\n" +
                "                    <br>jdbc:mckoi://mydatabase.mydomain.org/TOBY/\n" +
                "                    <br><u>Params :</u><br>user = root\n" +
                "                    <br>password = <br>port=9157\n" +
                "                    <br>schema=APP\n" +
                "                    <br><br>or\n" +
                "                    <br><br>\n" +
                "                    jdbc:mckoi:local://<i>path_to_database_config</i>[/<i>schema</i>][?<i>var1</i>=<i>value1</i>&<i>var2</i>=<i>value2</i>&...]\n" +
                "                    <br><u>exemple :</u>\n" +
                "                    <br>jdbc:mckoi:local://c:/mydb/myconfig.conf?read_only=enabled&log_path=c:/mynewlogdir/\n" +
                "                    <br><u>to create a database :</u>\n" +
                "                    <br>jdbc:mckoi:local://c:/mydb/myconfig.conf?create=true"));

        v.add(new DBCDriverInfo(v.size(),v.size(),"Derby/Embedded",true,"org.apache.derby.jdbc.EmbeddedDriver","jdbc:derby:{database};create=true","","",new URL[0], "jdbc:derby:<i>dbname</i>;create=true\n" +
                "                    <br><u>exemple :</u>\n" +
                "                    <br>jdbc:derby:test;create=true\n" +
                "                    <br><u>Params :</u>\n" +
                "                    <br>user = root\n" +
                "                    <br>password = root"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"MSAccess/hxtt",true,"com.hxtt.sql.access.AccessDriver","jdbc:access:{database}","","",new URL[0], "jdbc:access:////home/vpc/example"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"PostgreSQL",true,"org.postgresql.Driver","jdbc:postgresql://{server}/{database}","postgres","postgres",new URL[0], "jdbc:postgresql://localhost/postgres"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"Derby Network Server",true,"org.apache.derby.jdbc.ClientDriver","jdbc:derby://{server}:{port}/{database};create=true","","",new URL[0], "jdbc:derby://localhost:1527/MyDbTest;create=true\n" +
                "                    <br><u>exemple :</u>\n" +
                "                    <br>jdbc:derby:test;create=true\n" +
                "                    <br><u>Params :</u>\n" +
                "                    <br>user = root\n" +
                "                    <br>password = root"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"HSql",true,"org.hsqldb.jdbcDriver","jdbc:hsqldb:{protocol}:{subUrl}","","",new URL[0], "examples of urls\n" +
                "                    <ul>\n" +
                "                    <li>jdbc:hsqldb:file:/path/to/testdb</li>\n" +
                "                    <li>jdbc:hsqldb:mem:testdb</li>\n" +
                "                    <li>jdbc:hsqldb:hsql://localhost/testdb</li>\n" +
                "                    </ul>"));
        v.add(new DBCDriverInfo(v.size(),v.size(),"Sybase-Tds",true,"com.sybase.jdbc3.jdbc.SybDriver","jdbc:sybase:Tds:{server}:{port}/{database}","sa","",new URL[0], "jdbc:sybase:Tds:<i>serverAddress</i>[:<i>serverPort</i>]/XP<br><u>exemple :</u><br>jdbc:sybase:Tds:localhost:5000<br><u>Params :</u><br>user = sa<br>password = <br>serverPort = 5000<br>"));



        return v;
    }

    @Override
    public void applicationOpening() {
        DBCApplicationView appView = getApplication().getView();
        try {
            appView.getLookAndFeelManager().start();//just to initialize it
            getApplication().getFactory().newInstance(DBCPluggableResourcesSupport.class);
            try {
                appView.setPlaf(UIManager2.getPlafItem(getApplication().getConfig().getStringProperty(DBCApplicationView.PROPERTY_PLAF, null)));
            } catch (Exception e) {
                getApplication().getLogger(getClass().getName()).log(Level.SEVERE,"Plaf settings failed",e);
            }
            String iconSetName = getApplication().getConfig().getStringProperty(DBCApplicationView.PROPERTY_ICONSET, null);
            if (iconSetName != null && iconSetName.length() > 0) {
                try {
                    appView.setIconSet(PRSManager.getIconSet(iconSetName));
                } catch (Exception e) {
                    appView.getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
                }
            }
            appView.setLocale(loadLocale());
            String artSetConfig = getApplication().getConfig().getStringProperty(DBCApplicationView.PROPERTY_ARTSET, null);
            appView.setArtSet(PRSManager.getArtSet(artSetConfig));
            if (artSetConfig==null) {
                //if no artset found lookup for defaut artset : dbclient-artset-blues
                //if not found use the last one
                final String defaultArtSet="dbclient-artset-blues";
                ArtSet[] all = PRSManager.getArtSets();
                ArtSet ok=null;
                for (ArtSet artSet : all) {
                    if(artSet.getId().equals(defaultArtSet)){
                        ok=artSet;
                        break;
                    }
                }
                if(ok==null && all.length>0){
                    ok=all[all.length - 1];
                }
                appView.setArtSet(ok);
            }
        } catch (Exception e) {
            appView.getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
        }
    }

    @Override
    public void applicationReady() {
        if (getApplication().getConfig().getBooleanProperty("init.autoConnect", true)) {
            DBCSessionInfo[] sessions1 = getApplication().getConfig().getSessions();
            for (DBCSessionInfo dbcSessionInfo : sessions1) {
                if (dbcSessionInfo.isAutoConnect()) {
                    try {
                        getApplication().getView().getSplashScreen().hide();
                        getApplication().openSession(dbcSessionInfo.getId());
                    } catch (CancellationException e) {
                        //do nothing
                    } catch (Throwable e) {
                        getApplication().getView().getDialogManager().showMessage(null, "Unable to start Session " + dbcSessionInfo.getName(), MessageDialogType.ERROR, null, e);
                    }
                }
            }
        }
    }

    protected Locale loadLocale() {
        String loc = getApplication().getConfig().getStringProperty(DBCApplicationView.PROPERTY_LOCALE, null);
        if (loc != null && loc.length() > 0) {
            String[] parts = loc.split("_");
            String lang = parts.length > 0 ? parts[0] : "";
            String count = parts.length > 1 ? parts[1] : "";
            String vari = parts.length > 2 ? parts[2] : "";
            return new Locale(lang, count, vari);
        }
        return Locale.getDefault();
    }
}