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
package net.vpc.dbclient.plugin.system.sessionmanager.table;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.actionmanager.DBCActionFilter;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.vpc.dbclient.api.actionmanager.DBClientAction;
import net.vpc.prs.plugin.Initializer;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sessionmanager.DBCResultTable;
import net.vpc.dbclient.api.sessionmanager.DBCResultTableWrapper;
import net.vpc.dbclient.api.sessionmanager.DBCSQLNodeProvider;
import net.vpc.dbclient.api.sessionmanager.DBCTableModel;
import net.vpc.dbclient.api.sql.SQLRecord;
import net.vpc.dbclient.api.sql.objects.DBTableColumn;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.dbclient.api.viewmanager.DBCTable;
import net.vpc.dbclient.plugin.system.sql.SystemSQLUtils;
import net.vpc.swingext.ComponentResourcesUpdater;
import net.vpc.swingext.PRSManager;
import net.vpc.prs.iconset.IconSet;
import net.vpc.prs.messageset.MessageSet;
import net.vpc.swingext.JDropDownButton;
import net.vpc.swingext.dialog.MessageDialogType;
import net.vpc.swingext.table.DefaultTableCellFilter;
import net.vpc.swingext.table.TableFilterModel;
import net.vpc.swingext.table.TableRowFilterByCell;
import net.vpc.util.Chronometer;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 15 dec. 2006 00:14:36
 */
public class DBCResultTableImpl extends DBCPluggablePanel implements DBCResultTable {

    DBCTable table;

    public DBCResultTableImpl() {
    }

    public static final String EVENT_CHANGED = "EVENT_CHANGED";
    @Inject
    private DBCSession session;

    //    private int selectedRow;
    private Vector<Action> actionsList = new Vector<Action>();
    private JTableSupport support;
    //    private TLog log;
    private DBCFieldRenderer Renderer_Null;
    private DBCFieldRenderer Renderer_Bool;
    private DBCFieldRenderer Renderer_Default;
    private DBCFieldRenderer Renderer_Time;
    private DBCFieldRenderer Renderer_Date;
    private DBCFieldRenderer Renderer_DateTime;
    private DBCFieldRenderer Renderer_Timestamp;
    private MyTableCellRenderer myTableCellRenderer = new MyTableCellRenderer();
    private DBCSQLNodeProvider sqlNodeProvider;
    private Timer autoRefreshTimer;
    private int autoRefreshDelay = 1000;


    private class MyTableCellRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value == null) {
                Renderer_Null.setPk(false);
                return Renderer_Null.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);
            }
            DBTableColumn tableColumnNode = null;
            try {
                tableColumnNode = getModel().getFieldNode(column);
            } catch (SQLException e) {
                //e.printStackTrace();
            }
            boolean pk = false;
            if (tableColumnNode != null) {
                if (tableColumnNode.isPk()) {
//                        String f = sessionInfo.getKeyRenderer();
//                        if (f != null && f.length() > 0) {
//                        }
                    pk = true;
                }
            }
            if (value instanceof java.sql.Time) {
                Renderer_Time.setPk(pk);
                return Renderer_Time.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            } else if (value instanceof java.sql.Date) {
                Renderer_Date.setPk(pk);
                return Renderer_Date.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            } else if (value instanceof java.sql.Timestamp) {
                Renderer_Timestamp.setPk(pk);
                return Renderer_Timestamp.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            } else if (value instanceof java.util.Date) {
                Renderer_DateTime.setPk(pk);
                return Renderer_DateTime.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            } else if (value instanceof Boolean) {
                Renderer_Bool.setPk(pk);
                return Renderer_Bool.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            } else if (value instanceof byte[]) {
                Renderer_Default.setPk(pk);
                return Renderer_Default.getTableCellRendererComponent(table, new String((byte[]) value), isSelected, hasFocus, row, column);
            } else if (value instanceof char[]) {
                Renderer_Default.setPk(pk);
                return Renderer_Default.getTableCellRendererComponent(table, new String((char[]) value), isSelected, hasFocus, row, column);
            } else {
                Renderer_Default.setPk(pk);
                return Renderer_Default.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        }
    }

    @Initializer
    private void init() {
        DBCTableModel model = session.getFactory().newInstance(DBCTableModel.class);
        model.addPropertyChangeListener("lastQuery", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                table.setToolTipText("<HTML>" + SystemSQLUtils.sqlToHtml((String) evt.getNewValue()) + "</HTML>");
            }
        });
        //model
        setLayout(new BorderLayout());
        table = session.getFactory().newInstance(DBCTable.class);
        table.setModel(new TableFilterModel(model));
        support = new JTableSupport(this);
        Renderer_Null = new StaticRenderer(session.getConfig().getStringProperty("ui.format.null", null));
        Renderer_Bool = new net.vpc.dbclient.plugin.system.sessionmanager.table.DBCFieldBooleanRenderer();
        Renderer_Default = new DBCFieldRendererImpl();
        Renderer_Time = new net.vpc.dbclient.plugin.system.sessionmanager.table.DBCFieldDateRenderer(session.getConfig().getTimeFormat());
        Renderer_Date = new net.vpc.dbclient.plugin.system.sessionmanager.table.DBCFieldDateRenderer(session.getConfig().getDateFormat());
        Renderer_DateTime = new net.vpc.dbclient.plugin.system.sessionmanager.table.DBCFieldDateRenderer(session.getConfig().getTimestampFormat());
        Renderer_Timestamp = new net.vpc.dbclient.plugin.system.sessionmanager.table.DBCFieldDateRenderer(session.getConfig().getTimestampFormat());
//        table.setComponentPopupMenu(support.getPopup());

        table.setDefaultRenderer(Object.class, myTableCellRenderer);
        table.setDefaultRenderer(java.util.Date.class, myTableCellRenderer);
        table.setDefaultRenderer(java.sql.Date.class, myTableCellRenderer);
        table.setDefaultRenderer(java.sql.Time.class, myTableCellRenderer);
        table.setDefaultRenderer(java.sql.Timestamp.class, myTableCellRenderer);
        table.setDefaultRenderer(Number.class, myTableCellRenderer);
        table.setDefaultRenderer(Double.class, myTableCellRenderer);
        table.setDefaultRenderer(Float.class, myTableCellRenderer);
        table.setDefaultRenderer(Integer.class, myTableCellRenderer);
        table.setDefaultRenderer(BigInteger.class, myTableCellRenderer);
        table.setDefaultRenderer(String.class, myTableCellRenderer);
        table.setDefaultRenderer(Boolean.class, myTableCellRenderer);

        table.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(byte[].class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return super.getTableCellRendererComponent(table, new String((byte[]) value), isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(char[].class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return super.getTableCellRendererComponent(table, new String((char[]) value), isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(Number.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(Double.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(Float.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(java.sql.Date.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return Renderer_Date.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(java.sql.Time.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return Renderer_Time.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(java.sql.Timestamp.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                return Renderer_Timestamp.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null) {
                    return Renderer_Null.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                if (value instanceof char[]) {
                    value = new String((char[]) value);
                }
                if (value instanceof byte[]) {
                    value = new String((byte[]) value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

//        for (DBCResultTableAction dbcResultTableAction : getSession().getApplication().getPluginManager().createResultTableActions(this)) {
//            dbcResultTableAction.setResultTable(this);
//            addAction(dbcResultTableAction);
//        }
//        getSupport().getPopup().addSeparator();
        //actionsList.add(new GenerateSqlForRecordDataJTableAction(this));
        //actionsList.add(new GenerateSqlForAllDataJTableAction(this));

        //for (Action action : actionsList) {
        //    getSupport().getPopup().add(action);
        //}
        //actionsList.addAll(Arrays.asList(getSupport().getDefaultActions()));
        class MyDBCActionFilter implements DBCActionFilter {
            private ArrayList<DBClientAction> list = new ArrayList<DBClientAction>();

            public boolean accept(DBClientAction action) {
                if (action instanceof DBCResultTableAction) {
                    list.add(action);
                    return true;
                }
                return false;
            }
        }
        final MyDBCActionFilter instanceTableFilter = new MyDBCActionFilter();
        try {
            getSession().getView().getActionManager().fillComponent(getSupport().getPopup(), DBCActionLocation.POPUP, instanceTableFilter);
        } catch (Throwable e) {
            getSession().getView().getDialogManager().showMessage(null, "Unable to create Popup", MessageDialogType.ERROR, null, e);
        }
        getSupport().getPopup().addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                for (DBClientAction o : instanceTableFilter.list) {
                    if (o instanceof DBCResultTableAction) {
                        ((DBCResultTableAction) o).setResultTable(DBCResultTableImpl.this);
                    }
                }
                PRSManager.update(getSupport().getPopup(), DBCResultTableImpl.this.session.getView());
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        if (table.getComponent() instanceof JComponent) {
            PRSManager.addSupport(((JComponent) table.getComponent()), "JTableDB", new ComponentResourcesUpdater() {

                public void update(JComponent comp, String id, MessageSet messageSet, IconSet iconSet) {
                    PRSManager.update(actionsList, getSupport().getPopup(), messageSet, iconSet);
                }
            });
        }
        PRSManager.update(getSupport().getPopup(), session.getView());
        table.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                JList list = (JList) table.getClientProperty("JList");
                if (list != null) {
//                    list.setFixedCellWidth(getRowCount() == 0 ? 5 : -1);
                }
//                firePropertyChange(EVENT_CHANGED, false, true);
//                invalidate();
//                JScrollPane pane = getEnclosingScrollPane();
                JScrollPane pane = null;
                if (pane != null) {
                    pane.invalidate();
//                pane.setRowHeaderView(null);
//                pane.setRowHeaderView(list);
                    JViewport header = pane.getRowHeader();
                    if (header != null) {
                        JComponent view = (JComponent) header.getView();
                        if (view != null) {
                            view.revalidate();
                            view.repaint();
                        }
                    }
                }
//                pane.setRowHeaderView(null);
//                pane.setRowHeaderView(list);
            }
        });

        getSupport().install();
        statusBar = new StatusBar();
//        jdbTable.setPreferredScrollableViewportSize(new Dimension(jdbTable.getColumnCount()*100, 400));
//        jdbTable.setPreferredSize(new Dimension(jdbTable.getColumnCount()*100, 400));
//        jdbTable.setMinimumSize(new Dimension(jdbTable.getColumnCount()*100, 400));
//        JPanel p =new JPanel(new BorderLayout());
        JScrollPane comp = new JScrollPane(getTableComponent().getComponent());
        comp.setBorder(null);
        comp.setPreferredSize(new Dimension(table.getModel().getColumnCount() * 100, 10));
        comp.setMinimumSize(new Dimension(table.getModel().getColumnCount() * 100, 10));
//        p.add(comp);
        JScrollPane comp2 = new JScrollPane(comp);
        comp2.setBorder(null);
        JPanel main = new JPanel(new BorderLayout());
//        main.add(BorderLayout.PAGE_START,toolBar);
        main.add(BorderLayout.PAGE_START, statusBar);
        main.add(BorderLayout.CENTER, comp2);
        //TODO?
//        setTitle(table.getToolTipText());

//        jdbTable.addPropertyChangeListener(JDBTable.EVENT_CHANGED, new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                statusBar.refresh();
//            }
//        });
        statusBar.refreshStatusBar();
        getTableComponent().getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                statusBar.refreshStatusBar();
            }
        });
        this.add(main);
    }

    public void doRefreshTable() {

    }

    public void addAction(Action action) {
        actionsList.add(action);
        //actionsList.add(new GenerateSqlForAllDataJTableAction(this));
        getSupport().getPopup().add(action);
    }

//    protected JScrollPane getEnclosingScrollPane() {
//        Container p = getParent();
//        if (p instanceof JViewport) {
//            Container gp = p.getParent();
//            if (gp instanceof JScrollPane) {
//                return (JScrollPane) gp;
//            }
//        }
//        return null;
//    }
//    protected void configureEnclosingScrollPane() {
//        super.configureEnclosingScrollPane();
//        if (true/*getSession().getSessionInfo().isShowRowNumber()*/) {
//            Container p = getParent();
//            if (p instanceof JViewport) {
//                Container gp = p.getParent();
//                if (gp instanceof JScrollPane) {
//                    JScrollPane scrollPane = (JScrollPane) gp;
//                    // Make certain we are the viewPort's view and not, for
//                    // example, the rowHeaderView of the scrollPane -
//                    // an implementor of fixed columns might do this.
//                    JViewport viewport = scrollPane.getViewport();
//                    if (viewport == null || viewport.getView() != this) {
//                        return;
//                    }
//                    //                scrollPane.setColumnHeaderView(getTableHeader());
//                    JList list = (JList) getClientProperty("JList");
//                    if (list != null) {
//                        //already configured
//                        return;
//                    }
//                    list = new JList(new AbstractListModel() {
//                        public int getSize() {
//                            return JDBTable.this.getRowCount();
//                        }
//
//                        public Object getElementAt(int index) {
//                            return (index + 1);
//                        }
//                    });
//                    table.putClientProperty("JList", list);
////                    list.setFixedCellWidth(getRowCount() == 0 ? 5 : -1);
//                    //list.setFixedCellWidth(50);
//                    list.setMinimumSize(new Dimension(30, 5));
//                    list.setMaximumSize(new Dimension(60, 1000));
//                    list.setFixedCellHeight(this.getRowHeight());
//                    list.setEnabled(false);
//                    list.setCellRenderer(new SimpleRowHeaderRenderer(this));
//                    list.setBackground(table.getTableHeader().getBackground());
//                    scrollPane.setRowHeaderView(list);
//                }
//            }
//        }
//        getSupport().install();

    //    }

    public TableFilterModel getTableFilterModel() {
        return (TableFilterModel) table.getModel();
    }

    public DBCTableModel getModel() {
        return (DBCTableModel) getTableFilterModel().getBaseModel();
    }

    public SQLRecord getRecord(int row) {
        Object[] arr = getModel().getRow(getTableFilterModel().getBaseRowIndex(row));
        SQLRecord rec = new SQLRecord();
        for (int i = 0; i < arr.length; i++) {
            rec.put(getModel().getDefaultColumnName(i), arr[i]);
        }
        return rec;
    }

    public void close()
            throws SQLException {
        getModel().close();
    }

    public void setModel(ResultSet rs, DBCResultTableWrapper w)
            throws SQLException {
        getModel().displayQuery(rs, w);
    }

//    private void fireChanges() {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                JList list = (JList) table.getClientProperty("JList");
//                if (list != null) {
//                    list.setFixedCellWidth(table.getRowCount() == 0 ? 5 : -1);
//                }
//                table.firePropertyChange(EVENT_CHANGED, false, true);
//                table.invalidate();
//                JScrollPane pane = getEnclosingScrollPane();
//                if (pane != null) {
//                    pane.invalidate();
//                }
//            }
//        });
////        repaint();

    //    }

    public JTableSupport getSupport() {
        return support;
    }

    public DBCSQLNodeProvider getSqlNodeProvider() {
        return sqlNodeProvider;
    }

    public void setSqlNodeProvider(DBCSQLNodeProvider sqlNodeProvider) {
        this.sqlNodeProvider = sqlNodeProvider;
    }

    public DBCTable getTableComponent() {
        return table;
    }

    public DefaultTableCellFilter getTableCellFilter() {
        try {
            TableRowFilterByCell tableRowFilter = (TableRowFilterByCell) getTableFilterModel().getTableRowFilter();
            return tableRowFilter == null ? null : (DefaultTableCellFilter) tableRowFilter.getCellFilter();
        } catch (ClassCastException e) {
            return null;
        }
    }

    public DBCSession getSession() {
        return session;
    }


    private DBCResultTableAction refreshAction;
    private StatusBar statusBar;

    public Component getStatusBar() {
        return statusBar;
    }

    public void refresh() {
        if (refreshAction != null) {
            refreshAction.actionPerformed(new ActionEvent(this, -1, "refresh"));
        } else {
            try {
                getModel().reexecuteQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void startAutoRefreshTimer() {
        if (autoRefreshTimer == null) {
            if (autoRefreshDelay > 0) {
                autoRefreshTimer = new Timer(autoRefreshDelay, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        refresh();
                    }
                });
            }
        } else {
            autoRefreshTimer.stop();
            if (autoRefreshDelay > 0) {
                autoRefreshTimer.setDelay(autoRefreshDelay);
                autoRefreshTimer.start();
            }
        }
    }

    private void stopAutoRefreshTimer() {
        if (autoRefreshTimer != null) {
            autoRefreshTimer.stop();
        }
    }

    private static DecimalFormat DFORMAT = new DecimalFormat("00000.00");

    private void configureAutoRefreshTimer() {
        final JSlider s = new JSlider(JSlider.HORIZONTAL, 1000, 5 * 60000, autoRefreshDelay);
//        s.setPaintLabels(true);
        //s.setPaintTicks(true);
        s.setPaintTrack(true);
        s.setMajorTickSpacing(30000);
        s.setMinorTickSpacing(1000);
        s.setSnapToTicks(true);
        final JLabel lab = new JLabel();
        lab.setText(Chronometer.formatPeriodFixed((long) s.getValue(), Chronometer.DatePart.s, Chronometer.DatePart.mn));
        s.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                lab.setText(Chronometer.formatPeriodFixed((long) s.getValue(), Chronometer.DatePart.s, Chronometer.DatePart.mn));
            }
        });
        Box b = Box.createHorizontalBox();
        b.add(Box.createHorizontalStrut(10));
        b.add(s);
        b.add(Box.createHorizontalStrut(10));
        b.add(lab);
        b.add(Box.createHorizontalStrut(10));
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, b, "Auto Refresh Delay (mn:s)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)) {
            setAutoRefreshDelay(s.getValue());
        }
    }

    public int getAutoRefreshDelay() {
        return autoRefreshDelay;
    }

    public void setAutoRefreshDelay(int autoRefreshDelay) {
        if (autoRefreshDelay <= 0) {
            throw new IllegalArgumentException("Invalid delay " + autoRefreshDelay);
        }
        int old = this.autoRefreshDelay;
        this.autoRefreshDelay = autoRefreshDelay;
        firePropertyChange("autoRefreshDelay", old, autoRefreshDelay);
    }

    public DBCResultTableAction getRefreshAction() {
        return refreshAction;
    }

    public void setRefreshAction(DBCResultTableAction refreshAction) {
        this.refreshAction = refreshAction;
        if (this.refreshAction != null) {
            refreshAction.setResultTable(this);
        }
    }


    public class StatusBar extends JToolBar {
        private JLabel rowsValue = new JLabel("");
        private JLabel colsValue = new JLabel("");
        private JDropDownButton refresh;

        //TODO be careful!!! what count?

        public void refreshStatusBar() {
            try {
                rowsValue.setText((getModel().isMoreRows() ? ">" : "") + String.valueOf(getTableComponent().getModel().getRowCount()));
                colsValue.setText(String.valueOf(getTableComponent().getModel().getColumnCount()));
            } catch (Exception e) {
                //do nothing..
            }
        }


        public StatusBar() {
            super();
            PRSManager.addSupport(this, "JDBTablePane.Header",
                    new ComponentResourcesUpdater() {
                        public void update(JComponent comp, String id, MessageSet messageSet, IconSet iconSet) {
                            if (iconSet != null) {
                                rowsValue.setIcon(iconSet.getIconR("RowsCount"));
                                colsValue.setIcon(iconSet.getIconR("ColumnsCount"));
                            }

                        }
                    }
            );

            refresh = new JDropDownButton();
            refresh.setQuickActionDelay(200);
            PRSManager.addSupport(refresh, "Refresh");

            JMenuItem startTimerMenuItem = PRSManager.createMenuItem("RefreshStartTimer");
            JMenuItem stopTimerMenuItem = PRSManager.createMenuItem("RefreshStopTimer");
            JMenuItem configureTimerMenuItem = PRSManager.createMenuItem("RefreshConfigureTimer");
            JMenuItem refreshMenuItem = PRSManager.createMenuItem("Refresh");

            refresh.add(refreshMenuItem);
            refresh.addSeparator();
            refresh.add(startTimerMenuItem);
            refresh.add(stopTimerMenuItem);
            refresh.addSeparator();
            refresh.add(configureTimerMenuItem);

            refresh.addQuickActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    DBCResultTableImpl.this.refresh();
                }
            });
            startTimerMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    startAutoRefreshTimer();
                }
            });
            stopTimerMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    stopAutoRefreshTimer();
                }
            });
            configureTimerMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    configureAutoRefreshTimer();
                }
            });
//            Dimension d = new Dimension(40, colsValue.getSize().height);
//            colsValue.setPreferredSize(d);
//            colsValue.setMinimumSize(d);
//            colsValue.setMaximumSize(d);
            add(Box.createHorizontalGlue());
            addSeparator();
            add(new DBCResultTableQuickSearchTextField(DBCResultTableImpl.this, DBCResultTableQuickSearchTextField.Mode.Filter), "filterValue");
            addSeparator();
            add(refresh);
            addSeparator();
            add(rowsValue, "rowsValue");
            addSeparator();
            add(colsValue, "colsValue");
            setFloatable(false);
        }

    }


    public Component getComponent() {
        return this;
    }

    public int getSelectedColumn() {
        return getSupport().getColumn();
    }

    public int getSelectedRow() {
        return getSupport().getRow();
    }

}
