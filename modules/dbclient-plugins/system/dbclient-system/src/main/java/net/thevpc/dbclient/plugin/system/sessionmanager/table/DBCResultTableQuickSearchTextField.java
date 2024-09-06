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

package net.thevpc.dbclient.plugin.system.sessionmanager.table;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.dbclient.api.sessionmanager.DBCResultTable;
import net.thevpc.dbclient.api.viewmanager.DBCTable;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.swing.messageset.ComponentMessageSetUpdater;
import net.thevpc.common.prs.messageset.MessageSet;
import net.thevpc.common.swing.table.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 6 mai 2006 18:47:41
 */
public class DBCResultTableQuickSearchTextField extends JPanel {
    public static enum Mode {
        Selection, Filter
    }

    private JTableQuickSearchSupport searchSupport;
    private JCheckBoxMenuItem config_caseSensitive;
    private JRadioButtonMenuItem config_regexp;
    private JRadioButtonMenuItem config_startsWith;
    private JRadioButtonMenuItem config_contains;
    private JRadioButtonMenuItem config_endsWith;
    private JDropDownButton configButton;
    private JTextField filterTextField;
    private JMenuItem goFilter;
    private DBCResultTable table;
    private Mode mode;
//    private DBCSession session;

    public DBCResultTableQuickSearchTextField(DBCResultTable tableInstance, Mode mode) {
        this.table = tableInstance;
        this.mode = mode;
        PRSManager.addMessageSetSupport(this, "FilterTextField", new ComponentMessageSetUpdater() {
            public void updateMessageSet(JComponent comp, String id, MessageSet messageSet) {
                configButton.setToolTipText(messageSet.get("FilterTextField.ConfigButton.toolTipText"));
                config_caseSensitive.setText(messageSet.get("FilterTextField.ConfigButton.CaseSensitive"));
                config_startsWith.setText(messageSet.get("FilterTextField.ConfigButton.StartWith"));
                config_contains.setText(messageSet.get("FilterTextField.ConfigButton.Contains"));
                config_endsWith.setText(messageSet.get("FilterTextField.ConfigButton.EndWith"));
                config_regexp.setText(messageSet.get("FilterTextField.ConfigButton.RegExp"));
                filterTextField.setToolTipText(messageSet.get("FilterTextField.FilterText.toolTipText"));
                goFilter.setText(messageSet.get("FilterTextField.ConfigButton.ApplyFilter"));
            }

            public void install(JComponent comp, String id) {
            }
        });
        searchSupport = new JTableQuickSearchSupport(tableInstance.getTableComponent());
        goFilter = PRSManager.createMenuItem("ApplyFilter");
        configButton = new JDropDownButton(" ");
        configButton.setQuickActionDelay(0);
        configButton.setPopupOrientation(SwingConstants.RIGHT);
        configButton.setPopupOrientation(SwingConstants.RIGHT);
        configButton.setMargin(new Insets(1, 1, 1, 1));
        goFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goNext();
            }
        });
        configButton.add(goFilter);
        configButton.addSeparator();
        configButton.add(config_caseSensitive = new JCheckBoxMenuItem());
        config_caseSensitive.setSelected(false);
        configButton.addSeparator();
        ButtonGroup bg = new ButtonGroup();
        configButton.add(config_startsWith = new JRadioButtonMenuItem());
        config_startsWith.setSelected(true);
        configButton.add(config_contains = new JRadioButtonMenuItem());
        configButton.add(config_endsWith = new JRadioButtonMenuItem());
        configButton.add(config_regexp = new JRadioButtonMenuItem());
        bg.add(config_startsWith);
        bg.add(config_contains);
        bg.add(config_endsWith);
        bg.add(config_regexp);
        filterTextField = new JTextField("");
        filterTextField.addKeyListener(
                new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (e.getKeyChar() == ' ' && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                            goNext();
                        }
                    }
                }
        );
        filterTextField.setColumns(20);
        config_caseSensitive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfigCaseSensitive();
                updateCellLookupStrategy();
            }

        });
        ActionListener lookupStrategy = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfigLookupStrategy();
                updateCellLookupStrategy();
            }

        };
        filterTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                go();
            }

            public void insertUpdate(DocumentEvent e) {
                go();
            }

            public void removeUpdate(DocumentEvent e) {
                go();
            }

        });
        config_endsWith.addActionListener(lookupStrategy);
        config_contains.addActionListener(lookupStrategy);
        config_startsWith.addActionListener(lookupStrategy);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(filterTextField);
        add(configButton);
        loadConfigCaseSensitive();
        loadConfigLookupStrategy();
        updateCellLookupStrategy();
        PRSManager.update(this, table.getSession().getView());
        setBorder(BorderFactory.createEtchedBorder());
    }

    public DBCTable getTableComponent() {
        return table.getTableComponent();
    }

    public void goNext() {
        updateCellLookupStrategy();
    }

    public void go() {
        updateCellLookupStrategy();
    }

    public JTableQuickSearchSupport getSearchSupport() {
        return searchSupport;
    }


    public JTextField getFilterTextField() {
        return filterTextField;
    }

    private void loadConfigCaseSensitive() {
        try {
            config_caseSensitive.setSelected(table.getSession().getConfig().getBooleanProperty("ui.table.filter.lookup_case_sensitive", false));
        } catch (Exception e) {
            //
        }
    }

    private void loadConfigLookupStrategy() {
        String lookup_strategyString = table.getSession().getConfig().getStringProperty("ui.table.filter.lookup_strategy", null);
        if (lookup_strategyString == null) {
            lookup_strategyString = "portion";
        }
        config_startsWith.setSelected("prefix".equalsIgnoreCase(lookup_strategyString));
        config_endsWith.setSelected("suffix".equalsIgnoreCase(lookup_strategyString));
        config_contains.setSelected("portion".equalsIgnoreCase(lookup_strategyString));
        config_regexp.setSelected("regexp".equalsIgnoreCase(lookup_strategyString));
        if (!config_startsWith.isSelected()
                && !config_endsWith.isSelected()
                && !config_contains.isSelected()
                && !config_regexp.isSelected()
                ) {
            config_startsWith.setSelected(true);
        }
    }

    private void saveConfigCaseSensitive() {
        table.getSession().getConfig().setBooleanProperty("ui.table.filter.lookup_case_sensitive", config_caseSensitive.isSelected());
    }

    private void saveConfigLookupStrategy() {
        if (config_endsWith.isSelected()) {
            table.getSession().getConfig().setStringProperty("ui.table.filter.lookup_strategy", "suffix");
        } else if (config_contains.isSelected()) {
            table.getSession().getConfig().setStringProperty("ui.table.filter.lookup_strategy", "portion");
        } else if (config_startsWith.isSelected()) {
            table.getSession().getConfig().setStringProperty("ui.table.filter.lookup_strategy", "prefix");
        } else if (config_regexp.isSelected()) {
            table.getSession().getConfig().setStringProperty("ui.table.filter.lookup_strategy", "regexp");
        }
    }

    private void updateCellLookupStrategy() {
        switch (mode) {
            case Filter: {
                updateCellLookupStrategyFilter();
                break;
            }
            case Selection: {
                updateCellLookupStrategySelection();
                break;
            }
        }
    }

    private void updateCellLookupStrategyFilter() {
        DefaultTableCellFilter tableCellFilter = null;
        if (filterTextField.getText().length() != 0) {
            if (config_endsWith.isSelected()) {
                tableCellFilter = new EndsWithTextTableCellFilter(filterTextField.getText(), !config_caseSensitive.isSelected());
            } else if (config_contains.isSelected()) {
                tableCellFilter = new ContainsTextTableCellFilter(filterTextField.getText(), !config_caseSensitive.isSelected());
            } else if (config_startsWith.isSelected()) {
                tableCellFilter = new StartsWithTextTableCellFilter(filterTextField.getText(), !config_caseSensitive.isSelected());
            } else if (config_regexp.isSelected()) {
                tableCellFilter = new RegExpTableCellFilter(filterTextField.getText(), !config_caseSensitive.isSelected());
            }
        }
        ((TableFilterModel) (table.getTableComponent().getModel())).setTableRowFilter(tableCellFilter == null ? null : new TableRowFilterByCell(tableCellFilter));
        if (table.getModel().getRowCount() == 0) {
            filterTextField.setForeground(Color.red);
        } else {
            filterTextField.setForeground(Color.black);
        }
    }

    private void updateCellLookupStrategySelection() {
        searchSupport.setCellLookupCaseSensitive(config_caseSensitive.isSelected());
        if (config_endsWith.isSelected()) {
            searchSupport.setCellLookupStrategy(JTableQuickSearchSupport.LOOK_UP_CELL_BY_SUFFIX);
        } else if (config_contains.isSelected()) {
            searchSupport.setCellLookupStrategy(JTableQuickSearchSupport.LOOK_UP_CELL_BY_PORTION);
        } else if (config_startsWith.isSelected()) {
            searchSupport.setCellLookupStrategy(JTableQuickSearchSupport.LOOK_UP_CELL_BY_PREFIX);
        } else {
            searchSupport.setCellLookupStrategy(JTableQuickSearchSupport.LOOK_UP_CELL_BY_REGEXP);
        }
        int ind = searchSupport.setFirstSelectedByFilter(filterTextField.getText());
        if (ind >= 0)
            filterTextField.setForeground(Color.black);
        else
            filterTextField.setForeground(Color.red);
    }

    public Mode getMode() {
        return mode;
    }
}
