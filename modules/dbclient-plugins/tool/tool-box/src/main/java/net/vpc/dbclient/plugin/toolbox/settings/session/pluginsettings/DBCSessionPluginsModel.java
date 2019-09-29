package net.vpc.dbclient.plugin.toolbox.settings.session.pluginsettings;

import net.vpc.dbclient.api.pluginmanager.DBCPlugin;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 26 août 2009
 * Time: 23:02:24
 * To change this template use File | Settings | File Templates.
 */
class DBCSessionPluginsModel extends AbstractTableModel {
    DBCPlugin[] plugins;
    private DBCPluginsActivationPanel dbcPluginsActivationPanel;

    public DBCSessionPluginsModel(DBCPluginsActivationPanel dbcPluginsActivationPanel) {
        this.dbcPluginsActivationPanel = dbcPluginsActivationPanel;
        DBCPlugin[] dbcPlugins = dbcPluginsActivationPanel.session.getApplication().getPluginManager().getEnabledPlugins();
        ArrayList<DBCPlugin> all = new ArrayList<DBCPlugin>();
        for (DBCPlugin plugin : dbcPlugins) {
            int level = -1;
            try {
                level = plugin.getConnectionSupportLevel(dbcPluginsActivationPanel.session.getConnection());
            } catch (SQLException e) {
                //ignore plugin not added
            }
            if (level >= 0 && !plugin.getDescriptor().isSystem()) {
                all.add(plugin);
                dbcPluginsActivationPanel.enabledPlugins.put(plugin.getId(), dbcPluginsActivationPanel.session.isPluginEnabled(plugin.getId()));
            }
        }


        this.plugins = all.toArray(new DBCPlugin[all.size()]);
    }

    public int getRowCount() {
        return plugins == null ? 0 : plugins.length;
    }

    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return "Title";
            }
            case 1: {
                return "Category";
            }
            case 2: {
                return "Enabled";
            }
        }
        return super.getColumnName(columnIndex);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return plugins[rowIndex].getDescriptor().getTitle();
            }
            case 1: {
                return plugins[rowIndex].getDescriptor().getCategory();
            }
            case 2: {
                Boolean b = dbcPluginsActivationPanel.enabledPlugins.get(plugins[rowIndex].getId());
                return b == null ? Boolean.TRUE : b;
            }
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 2: {
                return Boolean.class;
            }
            default: {
                return String.class;
            }
        }
    }
}
