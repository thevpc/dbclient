package net.thevpc.dbclient.plugin.toolbox.settings.session.pluginsettings;

import net.thevpc.common.prs.factory.ExtensionDescriptor;

import javax.swing.table.AbstractTableModel;
import net.thevpc.common.prs.factory.ImplementationDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 26 août 2009
 * Time: 23:01:03
 * To change this template use File | Settings | File Templates.
 */
class DBCClassMappingModel extends AbstractTableModel {

    ExtensionDescriptor[] mappings;
    private DBCFactoryTablePanel dbcFactoryTablePanel;

    public DBCClassMappingModel(DBCFactoryTablePanel dbcFactoryTablePanel, ExtensionDescriptor[] map) {
        this.dbcFactoryTablePanel = dbcFactoryTablePanel;
        mappings = map;
    }

    public int getRowCount() {
        return mappings == null ? 0 : mappings.length;
    }

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return dbcFactoryTablePanel.session.getView().getMessageSet().get("Identifier");
            }
            case 1: {
                return dbcFactoryTablePanel.session.getView().getMessageSet().get("ComponentsMapping.Implementation");
            }
            case 2: {
                return dbcFactoryTablePanel.session.getView().getMessageSet().get("ComponentsMapping.DefaultImplementation");
            }
        }
        return super.getColumnName(columnIndex);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return mappings[rowIndex].getId().getName();
            }
            case 1: {
                ImplementationDescriptor validImpl = mappings[rowIndex].getValidImpl();
                return validImpl==null?null:validImpl.getImplementationType().getName();
            }
            case 2: {
                ImplementationDescriptor defaultImpl = mappings[rowIndex].getDefaultImpl();
                return defaultImpl == null ? null : defaultImpl.getImplementationType().getName();
            }
        }
        return null;
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}
