/**
 * 
====================================================================
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
 * 
====================================================================
 */

package net.vpc.dbclient.plugin.tool.searchengine;

import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.viewmanager.DBCTable;
import net.vpc.swingext.PRSManager;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.SwingUtilities3;
import net.vpc.swingext.table.JQuickSearchTextField;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2006/11/07
 */
public class SearchPanel extends JPanel implements SearchListener {
    JCheckBox contextContent;
    JCheckBox contextDef;
    JTextField catalogFilter;
    JTextField schemaFilter;
    JTextField tableFilter;
    JTextField procFilter;
    JButton startSearch;
    JButton stopSearch;
    JQuickSearchTextField pattern;
    DBCTable table;
    JTextArea errors;
    JProgressBar info;
    JLabel count;
    MyModel myModel;
    boolean searchCancelled;
    private SearchEnginePluginSession plugin;

    public SearchPanel(String catalog, String schema, SearchEnginePluginSession plugin) {
        super(new DumbGridBagLayout(
                "[<label   ]  :[<text+=   :              ]\n" +
                        "[criteria+=  :           :              ]\n" +
                        "[ glue       :          ]:[     buttons>]\n" +
                        "[ +=$$$table :           :              ]\n" +
                        "[ +===info   :          ]:[       count>]\n" +
                        "[ +=$log     :           :              ]\n"
        )
                .setInsets("label|text", new Insets(4, 4, 4, 4))
                .setInsets("buttons", new Insets(4, 4, 4, 4))
                .setInsets("info|count", new Insets(4, 4, 4, 4))
        );
        this.plugin = plugin;

        JPanel criteria = new JPanel(new DumbGridBagLayout(
                "[           ]:[ ^>ctxdata]:[<ctxdef   ]:[           ]\n" +
                        "[<catalogL  ]:[<-catalog=]:[<schemaL  ]:[<-schema=  ]\n" +
                        "[<tableL    ]:[<-table  =]:[<procL    ]:[<-proc  =  ]\n"
        )
                .setInsets(".*", new Insets(2, 2, 2, 2))
                .setInsets("cs", new Insets(5, 5, 2, 2))
                .setInsets("catalogL|tableL", new Insets(2, 5, 2, 2))
//                .setInsets("filter|schema|proc",new Insets(2,2,2,2))
        );


        PRSManager.setComponentMessageSet(this, plugin.getMessageSet());
        startSearch = PRSManager.createButton("Start");
        stopSearch = PRSManager.createButton("Stop");
        stopSearch.setEnabled(false);
        pattern = new JQuickSearchTextField();
        pattern.setStrategy(JQuickSearchTextField.Strategy.CONTAINS);
        pattern.setCaseSensitive(false);
        pattern.setGoFilterEnabled(false);
        myModel = new MyModel();
        count = new JLabel();
        info = new JProgressBar(JProgressBar.HORIZONTAL);
        info.setStringPainted(true);
        table = (DBCTable) plugin.getSession().getFactory().newInstance(DBCTable.class);
        table.setModel(myModel);
        TableColumnModel model = table.getColumnModel();
        model.getColumn(0).setPreferredWidth(100);
        model.getColumn(1).setPreferredWidth(10);
        model.getColumn(2).setPreferredWidth(10);
        model.getColumn(3).setPreferredWidth(10);
        model.getColumn(4).setPreferredWidth(100);
        tableFilter = new JTextField("");
        catalogFilter = new JTextField(catalog);
        schemaFilter = new JTextField(schema);
        procFilter = new JTextField("");

        contextContent = PRSManager.createCheck("SearchEngine.LookInContent", true);

        contextDef = PRSManager.createCheck("SearchEngine.LookInDefinition", true);
        contextDef.setSelected(true);

        criteria.add("ctxdata", contextContent);
        criteria.add("ctxdef", contextDef);
        criteria.add("catalogL", PRSManager.createLabel("SearchEngine.Catalog"));
        criteria.add("schemaL", PRSManager.createLabel("SearchEngine.Schema"));
        criteria.add("tableL", PRSManager.createLabel("SearchEngine.Table"));
        criteria.add("procL", PRSManager.createLabel("SearchEngine.Procedure"));
        criteria.add("catalog", catalogFilter);
        criteria.add("schema", schemaFilter);
        criteria.add("table", tableFilter);
        criteria.add("proc", procFilter);


        JToolBar tb = new JToolBar(JToolBar.HORIZONTAL);
        tb.setFloatable(false);
        tb.add(startSearch);
        tb.add(stopSearch);

        JScrollPane jsp = new JScrollPane(table.getComponent());
        errors = new JTextArea();
        errors.setEditable(false);
        JScrollPane jsplog = new JScrollPane(errors);
        add(PRSManager.createLabel("SearchEngine.Text"), "label");
        add(Box.createHorizontalGlue(), "glue");
        add(criteria, "criteria");
        add(pattern, "text");
        add(tb, "buttons");
        add(count, "count");
        add(jsp, "table");
        add(jsplog, "log");
        add(info, "info");
        startSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        stopSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchCancelled = true;
            }
        });
        PRSManager.update(this, plugin);
    }

    public void requestFocusOnTextField() {
        pattern.requestFocus();
    }

    public void start() {
        if (pattern.getPattern().length() > 0) {
            searchCancelled = false;
            SwingWorker sw = new SwingWorker() {
                protected Object doInBackground() throws Exception {
                    DBCConnection con = null;
                    try {
                        SwingUtilities3.invokeLater(new Runnable() {
                            public void run() {
                                myModel.reset();
                                count.setText("");
                                info.setIndeterminate(true);
                                startSearch.setEnabled(false);
                                stopSearch.setEnabled(true);
                            }
                        });
                        con = plugin.getSession().createConnection();
                        SearchOptions options = new SearchOptions();
                        options.setCatalog(catalogFilter.getText().length() == 0 ? null : catalogFilter.getText());
                        options.setSchema(schemaFilter.getText().length() == 0 ? null : schemaFilter.getText());
                        options.setTable(tableFilter.getText().length() == 0 ? null : tableFilter.getText());
                        options.setProcedure(procFilter.getText().length() == 0 ? null : procFilter.getText());
                        options.setLookInData(contextContent.isSelected());
                        options.setLookInDefinition(contextDef.isSelected());
                        SearchEngine se = new SearchEngine(plugin.getSession(), con, options, new DefaultSearchFilter(pattern.getStringFilter()));
                        se.addSearchListener(SearchPanel.this);
                        SwingUtilities3.invokeLater(
                                new Runnable() {
                                    public void run() {
                                        info.setString("");
                                        errors.setText("");
                                    }
                                }
                        );
                        se.search();
                        if (isSearchCancelled()) {
                            log("info", plugin.getMessageSet().get("SearchPanel.UserCancelledMessage"));
                        } else {
                            log("info", plugin.getMessageSet().get("SearchPanel.SuccessMessage"));
                        }
                    } catch (Throwable e) {
                        log("error", e.toString());
                    } finally {
                        if (con != null) {
                            con.close();
                        }
                        try {
                            SwingUtilities3.invokeLater(new Runnable() {
                                public void run() {
                                    stopSearch.setEnabled(false);
                                    startSearch.setEnabled(true);
                                    info.setIndeterminate(false);
                                }
                            });
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        searchCancelled = false;
                    }
                    return null;
                }
            };
            sw.execute();
        }

    }


    public void log(final String type, final String message) {
        SwingUtilities3.invokeLater(
                new Runnable() {
                    public void run() {
                        if (type.equals("info")) {
                            info.setString("[INFO ] " + message);
                            errors.append("[INFO ] " + message + "\n");
                        } else {
                            errors.append("[ERROR] " + message + "\n");
                        }
                    }
                }
        );
    }


    public void itemFound(final SearchResult result1) {
        SwingUtilities3.invokeLater(
                new Runnable() {
                    public void run() {
                        myModel.addRow(result1);
                        count.setText(myModel.model.size() + " objects...");
                    }
                }
        );
    }


    public boolean isSearchCancelled() {
        return searchCancelled;
    }

    class MyModel extends AbstractTableModel {
        ArrayList<SearchResult> model = new ArrayList<SearchResult>();


        public MyModel() {
        }

        public void reset() {
            model.clear();
            fireTableDataChanged();
        }

        public int getRowCount() {
            return model == null ? 0 : model.size();
        }

        public int getColumnCount() {
            return 5;
        }


        public String getColumnName(int column) {
            switch (column) {
                case 0: {
                    return "Location";
                }
                case 1: {
                    return "Type";
                }
                case 2: {
                    return "Context";
                }
                case 3: {
                    return "Column";
                }
                case 4: {
                    return "Value";
                }
                case 5: {
                    return "Row";
                }
            }
            return "?";
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            SearchResult r = model.get(rowIndex);
            switch (columnIndex) {
                case 0: {
                    return r.getObjectName();
                }
                case 1: {
                    return r.getObjectType();
                }
                case 2: {
                    return r.getContext();
                }
                case 3: {
                    return r.getColumnName();
                }
                case 4: {
                    return r.getValue();
                }
                case 5: {
                    return r.getRow();
                }
            }
            return "?";
        }

        public void addRow(SearchResult m) {
            model.add(m);
            fireTableRowsInserted(model.size() - 1, model.size() - 1);
        }
    }
}