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

package net.thevpc.dbclient.plugin.toolbox.settings.application;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.configmanager.DBCApplicationSettingsComponent;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.factory.ExtensionDescriptor;
import net.thevpc.common.prs.factory.ImplementationDescriptor;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.api.viewmanager.DBCTable;
import net.thevpc.common.prs.plugin.PluginDescriptor;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 31 dec. 2006 00:31:42
 */
public class DBCApplicationFactorySettingsComponent extends DBCPluggablePanel implements DBCApplicationSettingsComponent {
    @Inject
    private DBCApplication application;
    private Map<String, JComboBox> combos = new HashMap<String, JComboBox>();
    private Map<String, JLabel> labels = new HashMap<String, JLabel>();
    private DBCTable mappingTable;
    private ExtensionDescriptor[] availableConfigurations;

    public DBCApplicationFactorySettingsComponent() {
        super(new BorderLayout());
    }


    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return getApplication().getView().getIconSet().getIconW("Action.FactoryMappingAction");
    }

    public String getId() {
        return getClass().getSimpleName();
    }

    public String getTitle() {
        return getApplication().getView().getMessageSet().get("Action.FactoryMappingAction");
    }

    public DBCApplication getApplication() {
        return application;
    }

    @Initializer
    private void init() {
        //available config does not change
        availableConfigurations = application.getFactory().getExtensions();

        mappingTable = application.getFactory().newInstance(DBCTable.class);
        JScrollPane mappingJsp = new JScrollPane(mappingTable.getComponent());
        mappingTable.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
            Color initialColor = getForeground();
//            Font initialFont=getFont();

            //            Font boldFont =initialFont.deriveFont(Font.BOLD);
            // implements javax.swing.pluginsTable.TableCellRenderer

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                int rowModel = mappingTable.convertRowIndexToModel(row);
                String c1 = (String) table.getModel().getValueAt(rowModel, 0);
                String c2 = (String) table.getModel().getValueAt(rowModel, 1);
                String c3 = (String) table.getModel().getValueAt(rowModel, 2);
                if (c2==c3 || (c2!=null && c2.equals(c3))) {
                    super.getTableCellRendererComponent(table, toSimple((String) value, true), isSelected, hasFocus, row, column);
                    setForeground(initialColor);
                } else {
                    super.getTableCellRendererComponent(table, toSimple((String) value, true), isSelected, hasFocus, row, column);
                    setForeground(Color.BLUE);
                }
//                if(column==0){
//                    setFont(boldFont);
//                }else{
//                    setFont(initialFont);
//                }
                return this;
            }

            private String toSimple(String s, boolean isSimple) {
                if (isSimple && s != null) {
                    int x = s.lastIndexOf('.');
                    return s.substring(x + 1);
                }
                return s;
            }
        });
        mappingJsp.setPreferredSize(new Dimension(600, 400));

        StringBuilder sb = new StringBuilder();
        //resort them, may it be, some change in labels of impls,
        Arrays.sort(availableConfigurations, new Comparator<ExtensionDescriptor>() {

            public int compare(ExtensionDescriptor o1, ExtensionDescriptor o2) {
                int i = o1.getGroup().compareTo(o2.getGroup());
                if (i != 0) {
                    return i;
                }
                i = o1.getId().getName().compareTo(o2.getId().getName());
                if (i != 0) {
                    return i;
                }
                return i;
            }
        });
        Map<String, Component> components = new HashMap<String, Component>();
        for (int i = 0; i < availableConfigurations.length; i++) {
            if (i == 0 || (i > 0 && !availableConfigurations[i].getGroup().equals(availableConfigurations[i - 1].getGroup()))) {
                sb.append("[^<-group").append(i).append(".]\n");
                JLabel grpLabel = new JLabel(availableConfigurations[i].getGroup() + " : ");
                components.put("group" + i, grpLabel);
            }

            final ExtensionDescriptor configuration = availableConfigurations[i];
            sb.append("[^<-label").append(i).append("][<-=combo").append(i).append(" : ]\n");

            JLabel jl = new JLabel(getLabel(configuration));
            labels.put(configuration.getId().getName(), jl);
            components.put("label" + i, jl);
            ImplementationDescriptor[] impls = configuration.getImplementations();
            ImplementationDescriptor[] impls2 = new ImplementationDescriptor[impls.length + 1];
            System.arraycopy(impls, 0, impls2, 1, impls.length);
            JComboBox v = new JComboBox(impls2);
            v.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    ImplementationDescriptor implementation = (ImplementationDescriptor) value;
                    String text = implementation == null ? "<<automatic>>" : getLabel(implementation);
                    if (implementation != null) {
                        if (implementation.getExtension().getDefaultImpl().equals(implementation)) {
                            text += " (*)";
                        }
                    }
                    super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                    return this;
                }
            });
            combos.put(configuration.getId().getName(), v);
            components.put("combo" + i, v);

        }
         /**/
        JPanel list = new JPanel(new DumbGridBagLayout(sb.toString()).setInsets(".*", new Insets(3, 3, 3, 3)));
        for (Map.Entry<String, Component> entry : components.entrySet()) {
            list.add(entry.getValue(), entry.getKey());
        }
        JScrollPane editPanel = new JScrollPane(list);


        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab(getApplication().getView().getMessageSet().get("View"), mappingJsp);
        jTabbedPane.addTab(getApplication().getView().getMessageSet().get("Edit"), editPanel);
        add(jTabbedPane);
    }

    private String getLabel(ExtensionDescriptor configuration) {
        for (ImplementationDescriptor implementation : configuration.getImplementations()) {
            PluginDescriptor pluginInfo = (PluginDescriptor) implementation.getOwner();
            DBCPlugin dbcPlugin = application.getPluginManager().getPlugin(pluginInfo.getId());
            String lab = dbcPlugin.getMessageSet().get(configuration.getId().getName(), false);
            if (lab != null) {
                return lab;
            }
        }
        getApplication().getLogger(DBCApplicationFactorySettingsComponent.class.getName()).log(Level.WARNING, "No Label for Configuration {0}", configuration.getId());
        return configuration.getId().getName();
    }

    private String getLabel(ImplementationDescriptor implementation) {
        PluginDescriptor pluginInfo = (PluginDescriptor) implementation.getOwner();
        DBCPlugin dbcPlugin = application.getPluginManager().getPlugin(pluginInfo.getId());
        String lab = dbcPlugin.getMessageSet().get(implementation.getImplementationType().getName(), false);
        if (lab != null) {
            return lab;
        }
        getApplication().getLogger(DBCApplicationFactorySettingsComponent.class.getName()).log(Level.WARNING, "No Label for Implementation {0}", implementation.getImplementationType().getName());
        return implementation.getImplementationType().getName();
    }

    public void loadConfig() {

        for (ExtensionDescriptor configuration : availableConfigurations) {
            JLabel jl = labels.get(configuration.getId().getName());
            JComboBox v = combos.get(configuration.getId().getName());
            ImplementationDescriptor[] all = configuration.getImplementations();
            ImplementationDescriptor[] all2 = new ImplementationDescriptor[all.length + 1];
            System.arraycopy(all, 0, all2, 1, all.length);
            v.setModel(new DefaultComboBoxModel(all2));
            if (configuration.getImpl() == null) {
                v.setSelectedIndex(0);
            } else {
                for (int i = 0; i < all.length; i++) {
                    if (all[i].equals(configuration.getImpl())) {
                        v.setSelectedIndex(i + 1);
                        break;
                    }
                }
            }
//            jl.setVisible(all.length > 1);
//            v.setVisible(all.length > 1);
        }
        mappingTable.setModel(new DBCApplicationFactorySettingsComponent.ClassMappingModel3(availableConfigurations));
    }

    public void saveConfig() {
        try {
            ExtensionDescriptor[] confs = application.getFactory().getExtensions();
            for (ExtensionDescriptor configuration : confs) {
                JComboBox v = combos.get(configuration.getId().getName());
                if (v.isVisible()) {
                    ImplementationDescriptor ii = (ImplementationDescriptor) v.getSelectedItem();
                    configuration.setImpl(ii);
                }
            }
        } catch (Exception e) {
            application.getLogger(DBCApplicationFactorySettingsComponent.class.getName()).log(Level.SEVERE,"Save config failed",e);
        }
    }

    class ClassMappingModel3 extends AbstractTableModel {
        ExtensionDescriptor[] mappings;

        public ClassMappingModel3(ExtensionDescriptor[] map) {
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
                    return getApplication().getView().getMessageSet().get("Identifier");
                }
                case 1: {
                    return getApplication().getView().getMessageSet().get("ComponentsMapping.Implementation");
                }
                case 2: {
                    return getApplication().getView().getMessageSet().get("ComponentsMapping.DefaultImplementation");
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
                    ImplementationDescriptor i = mappings[rowIndex].getValidImpl();
                    return i == null ? null : i.getImplementationType().getName();
                }
                case 2: {
                    ImplementationDescriptor impl = mappings[rowIndex].getDefaultImpl();
                    if(impl==null){
                        return null;
                    }
                    Class type = impl.getImplementationType();
                    return type==null?null:type.getName();
                }
            }
            return null;
        }


        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
    }

    public int getPosition() {
        return 0;
    }
}
