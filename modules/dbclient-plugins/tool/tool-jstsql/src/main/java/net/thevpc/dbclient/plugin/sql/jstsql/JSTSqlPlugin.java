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

package net.thevpc.dbclient.plugin.sql.jstsql;

import net.thevpc.common.prs.xml.XmlUtils;
import net.thevpc.dbclient.api.configmanager.DBCDriverInfo;
import net.thevpc.dbclient.api.configmanager.DBCMessageInfo;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
import net.thevpc.common.prs.plugin.PluginInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlEvaluator;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 3 dec. 2006 18:57:40
 */
@PluginInfo(
        messageSet = "net.thevpc.dbclient.plugin.sql.jstsql.messageset.Plugin"
)
public class JSTSqlPlugin extends DBCAbstractPlugin {
    public static final String ID = "tool-jstsql";

    public JSTSqlPlugin() {
    }

    @Override
    public void pluginInstalled() {
        try {
            URL resource = getClass().getResource("install-defaults.xml");
            InputStream inputStream;
            inputStream = resource.openStream();
            List v = (List) XmlUtils.xmlToObject(inputStream,null,null);
            for (Object o : v) {
                if (o instanceof DBCDriverInfo) {
                    DBCDriverInfo d = (DBCDriverInfo) o;
//                    d.setApplication(getApplication());
                    getApplication().getConfig().addDriver(d);
                } else if (o instanceof DBCMessageInfo) {
                    DBCMessageInfo d = (DBCMessageInfo) o;
                    getApplication().getConfig().addMessage(d);
                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static JSTSqlEvaluator resolveSqlEvaluator(DBCPluginSession session){
        synchronized(session){
            JSTSqlEvaluator v = (JSTSqlEvaluator)session.getUserObject(JSTSqlEvaluator.class.getName());
            if(v==null){
                v=new JSTSqlEvaluator(session);
                session.setUserObject(JSTSqlEvaluator.class.getName(),v);
            }
            return v;
        }
    }
}
