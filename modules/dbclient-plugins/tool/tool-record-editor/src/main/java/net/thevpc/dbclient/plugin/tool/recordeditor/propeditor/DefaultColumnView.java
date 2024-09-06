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

package net.thevpc.dbclient.plugin.tool.recordeditor.propeditor;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.sql.objects.DBTableColumn;
import net.thevpc.dbclient.api.sql.util.SQLUtils;
import net.thevpc.dbclient.plugin.tool.recordeditor.ColumnProperties;
import net.thevpc.dbclient.plugin.tool.recordeditor.RecordEditorPluginSession;
import net.thevpc.dbclient.plugin.tool.recordeditor.JLabelPopupMenu;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 4 janv. 2006 12:25:09
 */
public class DefaultColumnView implements ColumnView {
    private static final String EDIT_COMPONENT_DELEGATE = "DefaultColumnView.EDIT_COMPONENT_DELEGATE";
    private static final String COLUMN_VIEW = "DefaultColumnView.COLUMN_VIEW";
    private TableView defaultTableView;
    private DBTableColumn column;
    private JComponent editComponent;
    private JComponent labelComponent;
    private Component[] suffixComponent;
    private RecordEditorPluginSession pluginSession;
    private boolean insertMode = false;
    private Dimension preferredDimension = new Dimension(1, 1);
//    protected JCheckBox doUpdate;
    protected boolean configurable;

    public DefaultColumnView() {
    }


    public JComponent getEditComponent() {
        if (editComponent == null) {
            editComponent = createEditComponent();
        }
        return editComponent;
    }

    protected void install(JComponent comp) {
        install(comp, comp);
    }

    protected void install(JComponent comp, JComponent delegate) {
        comp.putClientProperty(EDIT_COMPONENT_DELEGATE, delegate);
        comp.putClientProperty(COLUMN_VIEW, this);
    }

    protected JComponent createEditComponent() {
        JTextField _ec = new JTextField("");
        _ec.setColumns(15);
        install(_ec);
        return _ec;
    }

    public Component getEditComponentDelegate() {
        JComponent e = (getEditComponent());
        Component c = (Component) e.getClientProperty(EDIT_COMPONENT_DELEGATE);
        return c == null ? e : c;
    }

    public Component getLabelComponent() {
        if (labelComponent == null) {
            IconSet iconSet = getSession().getView().getIconSet();
            if (getColumn().isPk()) {
                labelComponent = new JLabel(pluginSession.getColumnTitle(getColumn()),
                        iconSet.getIconR((getColumn().isPk() ? "TypePK" : "NoIcon")),
                        SwingConstants.LEFT);
                labelComponent.setToolTipText("Primary Key");
            } else {
                labelComponent = new JCheckBox(pluginSession.getColumnTitle(getColumn()));
                labelComponent.setToolTipText("Column updatable when selected");
//            doUpdate.setEnabled(!getColumn().isPk());
                ((JCheckBox)labelComponent).setSelected(true);
                ((JCheckBox)labelComponent).addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
//                    JCheckBox s = (JCheckBox) e.getSource();
                        refreshComponentsStatuses();
//                    getEditComponent().setEnabled(s.isSelected());
                    }
                });
                //((JCheckBox)labelComponent).setSelected(!getColumn().isNullable());
            }
            JLabelPopupMenu popupMenu=new JLabelPopupMenu(labelComponent,this,getPluginSession());
            labelComponent.setComponentPopupMenu(popupMenu);
        }
        return labelComponent;
    }
    protected boolean isUpdatableImpl(){
        //doUpdate == null || doUpdate.isSelected()
        return (!(labelComponent instanceof JCheckBox)) || ((JCheckBox)labelComponent).isSelected();
    }

    public void configureLabel(){
        String value=pluginSession.getColumnTitle(getColumn());
        if(labelComponent instanceof JLabel){
            ((JLabel)labelComponent).setText(value);
        }else if(labelComponent instanceof AbstractButton){
            ((AbstractButton)labelComponent).setText(value);
        }
    }

    protected Component[] createSuffixComponents() {
        String suffixTooltip;
        String suffixText;
        String icon;
        switch (getColumn().getSqlType()) {
            case Types.BIGINT: {
                suffixTooltip = "big integer value";
                suffixText = "";
                icon = "TypeNumber";
                break;
            }
            case Types.BOOLEAN:
            case Types.BIT: {
                suffixTooltip = "";
                suffixText = "";
                icon = "TypeBoolean";
                break;
            }

            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.REAL: {
                suffixTooltip = "double value";
                suffixText = "";
                icon = "TypeNumber";
                break;
            }

            case Types.NUMERIC: {
                if (getColumn().getPrecision() > 0) {
                   suffixTooltip = "double value";
                    suffixText = "";
                    icon = "TypeNumber";
                } else {
                    suffixTooltip = "integer value";
                    suffixText = "";
                    icon = "TypeNumber";
                }
                break;
            }
            case Types.DATE: {
                suffixTooltip = "date value (format " + getSession().getConfig().getDateFormat().toPattern() + ")";
                suffixText = "";
                icon = "TypeDate";
                break;
            }
            case Types.TIME: {
                suffixTooltip = "time value (format " + getSession().getConfig().getTimeFormat().toPattern() + ")";
                suffixText = "";
                icon = "TypeTime";
                break;
            }
            case Types.TIMESTAMP: {
                suffixTooltip = "datetime value (format " + getSession().getConfig().getTimestampFormat().toPattern() + ")";
                suffixText = "";
                icon = "TypeTimestamp";
                break;
            }

            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER: {
                suffixTooltip = "integer value";
                suffixText = "";
                icon = "TypeNumber";
                break;
            }

            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BINARY:
            case Types.LONGVARCHAR:
            case Types.CHAR:
            case Types.VARCHAR: {
                suffixTooltip = "string value (max " + getColumn().getSize() + ")";
                suffixText = "";
                icon = "TypeVarchar";
                break;
            }

            case Types.ARRAY:
            case Types.STRUCT:
            case Types.NULL:
            case Types.OTHER:
            case Types.REF:
            case Types.JAVA_OBJECT:
            case Types.BLOB:
            case Types.CLOB:
            case Types.DISTINCT:
            case Types.DATALINK:
            default: {
                suffixTooltip = "";
                suffixText = "";
                icon = "TypeError";
                break;
            }
        }
        IconSet iconSet = getSession().getView().getIconSet();
        suffixTooltip += (" [" + SQLUtils.getSqlTypeName(getColumn().getSqlType(),getSession()) + "]");
        JLabel typeComponent = new JLabel(suffixText, iconSet.getIconR(icon), SwingConstants.LEFT);
        typeComponent.setToolTipText(suffixTooltip);
        JLabel nullableComponent = new JLabel(iconSet.getIconR((getColumn().isNullable() ? "NoIcon" : "NonNullable")));
        nullableComponent.setToolTipText((getColumn().isNullable() ? "" : "non nullable value"));
//        JComponent updatableComponent;
//        if (getColumn().isPk()) {
//            updatableComponent = new JLabel(" ",
//                    iconSet.getIconR((getColumn().isPk() ? "TypePK" : "NoIcon")),
//                    SwingConstants.LEFT);
//            updatableComponent.setToolTipText("Primary Key");
//            doUpdate = null;
//        } else {
//            doUpdate = new JCheckBox();
//            doUpdate.setToolTipText("Column updatable when selected");
////            doUpdate.setEnabled(!getColumn().isPk());
//            doUpdate.setSelected(true);
//            doUpdate.addItemListener(new ItemListener() {
//                public void itemStateChanged(ItemEvent e) {
////                    JCheckBox s = (JCheckBox) e.getSource();
//                    refreshComponentsStatuses();
////                    getEditComponent().setEnabled(s.isSelected());
//                }
//            });
//            doUpdate.setSelected(!getColumn().isNullable());
//            updatableComponent = doUpdate;
//        }
        return new JComponent[]{/*updatableComponent, */nullableComponent, typeComponent};
    }

    public Component[] getSuffixComponents() {
        if (suffixComponent == null) {
            suffixComponent = createSuffixComponents();
        }
        return suffixComponent;
    }

    public void setValue(Object value) {
        ((JTextComponent) getEditComponentDelegate()).setText(value == null ? "" : (String) value);
    }

//    public int loadValue(ResultSet resultSet, int index) throws SQLException {
//        return 0;
//    }
//
//    public int saveValue(PreparedStatement ps, int index) throws SQLException {
//        return 0;
//    }

    public String getSQLLiteral() throws SQLException {
        return getSession().getConnection().getSQLLiteral(getValue(), getColumn().getSqlType());
    }

    public Object getValue() {
        return (((JTextComponent) getEditComponentDelegate()).getText());
    }

    public TableView getTableView() {
        return defaultTableView;
    }

    public void setTableView(TableView defaultTableView) {
        this.defaultTableView = defaultTableView;
    }

    public DBCSession getSession() {
        return pluginSession.getSession();
    }

    public RecordEditorPluginSession getPluginSession() {
        return pluginSession;
    }

    public void setInsertMode(boolean insertMode) {
        this.insertMode = insertMode;
        refreshComponentsStatuses();
    }

    protected void refreshComponentsStatuses() {
        //TableColumn column = getColumn();
        getEditComponentDelegate().setEnabled(
                (isUpdatableImpl())
                        && ((insertMode && column.isInsertable()) || !column.isPk()));
    }

    public Dimension getPreferredGridDimension() {
        if (editComponent == null) {
            getEditComponent();//
        }
        return preferredDimension;
    }

    public DBTableColumn getColumn() {
        return column;
    }

    public Dimension getPreferredDimension() {
        return preferredDimension;
    }

    public void setPreferredDimension(Dimension preferredDimension) {
        this.preferredDimension = preferredDimension;
    }

    public void init(RecordEditorPluginSession session) {
        this.pluginSession = session;
    }

    public void setColumn(DBTableColumn column) {
        this.column = column;
    }

    public void configure() {

    }

    public boolean accept(DBTableColumn node) {
        return true;
    }

    public boolean isConfigurable() {
        return configurable;
    }

    public void setConfigurable(boolean configurable) {
        this.configurable = configurable;
    }

    public void showConfig() {
        if (isConfigurable()) {
            ConfigPanel p = createConfigPanel();
            p.load();
            while (true) {
                int r = JOptionPane.showConfirmDialog(null, p, "Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION);
                if (r == JOptionPane.OK_OPTION) {
                    try {
                        p.store();
                        return;
                    } catch (Throwable th) {
                        getSession().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, th);
                    }
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalStateException();
        }
    }

    protected ConfigPanel createConfigPanel() {
        throw new IllegalStateException();
    }

    public boolean isUpdatable() {
        return getColumn().isPk() || isUpdatableImpl();
    }

    public boolean isInsertable() {
        return getColumn().isInsertable() && isUpdatableImpl();
    }

    protected void setConfigValue(String code, String value) {
        getPluginSession().getConfig().setPathValue(getColumn().getStringPath(), "ColumnView." + code, value);
    }

    protected String getConfigValue(String code) {
        return getPluginSession().getConfig().getPathValue(getColumn().getStringPath(), "ColumnView." + code);
    }

    protected void resetConfig() {
        getPluginSession().getConfig().clearPathsValues(getColumn().getStringPath(), "ColumnView.%");
    }

    protected abstract class ConfigPanel extends JPanel {
        public ConfigPanel() {
        }

        public ConfigPanel(LayoutManager layout) {
            super(layout);
        }

        public abstract void load();

        public abstract void store();

    }

}
