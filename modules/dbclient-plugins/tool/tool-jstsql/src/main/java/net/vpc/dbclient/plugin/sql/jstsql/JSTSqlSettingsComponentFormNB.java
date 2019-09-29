/*
 * JSTSqlSettingsComponentFormNB.java
 *
 * Created on 4 janv. 2009, 14:33:57
 */
package net.vpc.dbclient.plugin.sql.jstsql;

import net.vpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.vpc.swingext.PRSManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author vpc
 */
public class JSTSqlSettingsComponentFormNB extends javax.swing.JPanel {

    private JSTSqlPluginSession pluginSession;
    private JSTSqlTemplateInfo currentInfo;

    /**
     * Creates new form JSTSqlSettingsComponentFormNB
     */
    public JSTSqlSettingsComponentFormNB(JSTSqlPluginSession pluginSession) {
        this.pluginSession = pluginSession;
        initComponents();
        list.setModel(new DefaultListModel());
        list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int i = e.getFirstIndex();
                    if (i >= 0) {
                        setCurrent((JSTSqlTemplateInfo) list.getSelectedValue());
                    }
                }
            }
        });
        idText.setEditable(false);
        idText.setEditable(true);
        ButtonGroup group = new ButtonGroup();
        group.add(typePlainTemplate);
        group.add(typeProcessedTemplate);
        validateVisibility();
        confirmCheck.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                validateVisibility();
            }
        });
        listToolbar.setFloatable(false);
        idText.setEnabled(false);
        idText.setEditable(true);
        loadTemplates();
        PRSManager.addSupport(upButton, "Up");
        PRSManager.addSupport(downButton, "Down");
        PRSManager.addSupport(addButton, "Add");
        PRSManager.addSupport(removeButton, "Remove");
        PRSManager.update(this, pluginSession.getMessageSet(), pluginSession.getIconSet());
    }

    public void validateVisibility() {
        confirmText.setEnabled(confirmCheck.isSelected());

    }

    public void saveTemplates() {
        updateCurrent();
        pluginSession.storeTemplates(getTemplates());
    }

    public void loadTemplates() {
        updateCurrent();
        DefaultListModel model = (DefaultListModel) list.getModel();
        model.clear();
        JSTSqlTemplateInfo[] all = pluginSession.loadTemplates();
        Arrays.sort(all, new Comparator<JSTSqlTemplateInfo>() {
            public int compare(JSTSqlTemplateInfo o1, JSTSqlTemplateInfo o2) {
                return o1.getSstIndex() - o2.getSstIndex();
            }
        });
        for (JSTSqlTemplateInfo jSTSqlTemplateInfo : all) {
            model.addElement(jSTSqlTemplateInfo);
        }
        if (all.length > 0) {
            list.setSelectedIndex(0);
        } else {
            setCurrent(null);
        }
    }

    public JSTSqlTemplateInfo[] getTemplates() {
        updateCurrent();
        DefaultListModel model = (DefaultListModel) list.getModel();
        return Arrays.asList(model.toArray()).toArray(new JSTSqlTemplateInfo[model.getSize()]);
    }

    private boolean getBoolean(Boolean b) {
        return b == null ? false : b;
    }

    public void updateCurrent() {
        if (currentInfo == null) {
            return;
        }
        //idText.setText(info.getSstId()==null?"":String.valueOf(info.getSstId()));
        currentInfo.setSstEnabled(enabledCheck.isSelected());
        currentInfo.setSstConfirmEnabled(confirmCheck.isSelected());
        currentInfo.setSstIsSeparator(prefixWithSeparatorCheck.isSelected());
        currentInfo.setSstPreferTemplate(typePlainTemplate.isSelected());
        currentInfo.setSstConfirmMsg(confirmText.getText());
        currentInfo.setSstName(nameText.getText());
        currentInfo.setSstSql(sqlArea.getText());
        StringBuilder filter = new StringBuilder();
        if (filterTable.isSelected()) {
            if (filter.length() > 0) {
                filter.append("|");
            }
            filter.append("table");
        }
        if (filterColumn.isSelected()) {
            if (filter.length() > 0) {
                filter.append("|");
            }
            filter.append("column");
        }
        if (filterProcedure.isSelected()) {
            if (filter.length() > 0) {
                filter.append("|");
            }
            filter.append("procedure");
        }
        if (filterView.isSelected()) {
            if (filter.length() > 0) {
                filter.append("|");
            }
            filter.append("view");
        }
        if (filterSystemTable.isSelected()) {
            if (filter.length() > 0) {
                filter.append("|");
            }
            filter.append("system table");
        }
        currentInfo.setSstNodeFilter(filter.toString());
        currentInfo.setSstGroupIndex(
                (groupIndexToolbarCheck.isSelected() ? JSTSqlPluginSession.TOOLBAR : 0)
                        | (groupIndexExecCheck.isSelected() ? JSTSqlPluginSession.MENU_EXEC : 0)
                        | (groupIndexInsertCheck.isSelected() ? JSTSqlPluginSession.MENU_INSERT : 0)
                        | (groupIndexSaveCheck.isSelected() ? JSTSqlPluginSession.MENU_SAVE : 0)
        );
    }

    public void setCurrent(JSTSqlTemplateInfo info) {
        if (info != currentInfo) {
            updateCurrent();
        }
        currentInfo = info;
        if (info == null) {
            idText.setText("");
            enabledCheck.setSelected(false);
            confirmCheck.setSelected(false);
            confirmText.setText("");
            nameText.setText("");
            sqlArea.setText("");

            typePlainTemplate.setSelected(false);
            typeProcessedTemplate.setSelected(true);
            filterTable.setSelected(false);
            filterProcedure.setSelected(false);
            filterSystemTable.setSelected(false);
            filterView.setSelected(false);
            filterColumn.setSelected(false);
            groupIndexToolbarCheck.setSelected(false);
            groupIndexExecCheck.setSelected(false);
            groupIndexInsertCheck.setSelected(false);
            groupIndexSaveCheck.setSelected(false);
            prefixWithSeparatorCheck.setSelected(false);
        } else {
            idText.setText(info.getSstId() == null ? "" : String.valueOf(info.getSstId()));
            enabledCheck.setSelected(getBoolean(info.getSstEnabled()));
            confirmCheck.setSelected(getBoolean(info.getSstConfirmEnabled()));
            confirmText.setText(info.getSstConfirmMsg());
            nameText.setText(info.getSstName());
            sqlArea.setText(info.getSstSql());
            prefixWithSeparatorCheck.setSelected(getBoolean(info.getSstIsSeparator()));
            if (info.getSstPreferTemplate()) {
                typePlainTemplate.setSelected(true);
            } else {
                typeProcessedTemplate.setSelected(true);
            }
            filterTable.setSelected(false);
            filterProcedure.setSelected(false);
            filterSystemTable.setSelected(false);
            filterView.setSelected(false);
            filterColumn.setSelected(false);
            for (String string : info.getSstNodeFilter().split("\\|")) {
                if ("table".equals(string)) {
                    filterTable.setSelected(true);
                } else if ("view".equals(string)) {
                    filterView.setSelected(true);
                } else if ("system table".equals(string)) {
                    filterSystemTable.setSelected(true);
                } else if ("procedure".equals(string)) {
                    filterProcedure.setSelected(true);
                } else if ("column".equals(string)) {
                    filterColumn.setSelected(true);
                }
            }
            groupIndexToolbarCheck.setSelected((info.getSstGroupIndex() & JSTSqlPluginSession.TOOLBAR) != 0);
            groupIndexExecCheck.setSelected((info.getSstGroupIndex() & JSTSqlPluginSession.MENU_EXEC) != 0);
            groupIndexInsertCheck.setSelected((info.getSstGroupIndex() & JSTSqlPluginSession.MENU_INSERT) != 0);
            groupIndexSaveCheck.setSelected((info.getSstGroupIndex() & JSTSqlPluginSession.MENU_SAVE) != 0);
        }
    }

    private JEditorPane createSQLArea() {
        DBCSQLEditor ep;
        ep = pluginSession.getSession().getFactory().newInstance(DBCSQLEditor.class);
        return (JEditorPane)ep;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        nameText = new javax.swing.JTextField();
        filterLabel = new javax.swing.JLabel();
        enabledCheck = new javax.swing.JCheckBox();
        sqlAreaScroll = new javax.swing.JScrollPane();
        sqlArea = createSQLArea();
        filterPanel = new javax.swing.JPanel();
        filterTable = new javax.swing.JCheckBox();
        filterSystemTable = new javax.swing.JCheckBox();
        filterView = new javax.swing.JCheckBox();
        filterColumn = new javax.swing.JCheckBox();
        filterProcedure = new javax.swing.JCheckBox();
        sqlAreaLabel = new javax.swing.JLabel();
        optionPanel = new javax.swing.JPanel();
        confirmText = new javax.swing.JTextField();
        typeLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        typePanel = new javax.swing.JPanel();
        typeProcessedTemplate = new javax.swing.JRadioButton();
        typePlainTemplate = new javax.swing.JRadioButton();
        idText = new javax.swing.JTextField();
        groupIndexPanel = new javax.swing.JPanel();
        groupIndexInsertCheck = new javax.swing.JCheckBox();
        groupIndexSaveCheck = new javax.swing.JCheckBox();
        groupIndexExecCheck = new javax.swing.JCheckBox();
        groupIndexToolbarCheck = new javax.swing.JCheckBox();
        groupIndexText = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        confirmCheck = new javax.swing.JCheckBox();
        prefixWithSeparatorCheck = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        listScroll = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        listToolbar = new javax.swing.JToolBar();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Script Definition"));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        nameText.setText("jTextField2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 10);
        jPanel1.add(nameText, gridBagConstraints);

        filterLabel.setText("Filter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel1.add(filterLabel, gridBagConstraints);

        enabledCheck.setText("Enabled");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(enabledCheck, gridBagConstraints);

        sqlAreaScroll.setViewportView(sqlArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 3.0;
        gridBagConstraints.weighty = 3.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 10, 7, 10);
        jPanel1.add(sqlAreaScroll, gridBagConstraints);

        filterPanel.setLayout(new javax.swing.BoxLayout(filterPanel, javax.swing.BoxLayout.LINE_AXIS));

        filterTable.setText("Table");
        filterPanel.add(filterTable);

        filterSystemTable.setText("System Tabe");
        filterPanel.add(filterSystemTable);

        filterView.setText("View");
        filterPanel.add(filterView);

        filterColumn.setText("Column");
        filterPanel.add(filterColumn);

        filterProcedure.setText("Procedure");
        filterPanel.add(filterProcedure);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(filterPanel, gridBagConstraints);

        sqlAreaLabel.setText("SQL Template");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel1.add(sqlAreaLabel, gridBagConstraints);

        optionPanel.setLayout(new javax.swing.BoxLayout(optionPanel, javax.swing.BoxLayout.LINE_AXIS));

        confirmText.setText("jTextField1");
        optionPanel.add(confirmText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 10);
        jPanel1.add(optionPanel, gridBagConstraints);

        typeLabel.setText("Type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel1.add(typeLabel, gridBagConstraints);

        nameLabel.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel1.add(nameLabel, gridBagConstraints);

        typePanel.setLayout(new javax.swing.BoxLayout(typePanel, javax.swing.BoxLayout.LINE_AXIS));

        typeProcessedTemplate.setText("Processed Template");
        typePanel.add(typeProcessedTemplate);

        typePlainTemplate.setText("Plain Template");
        typePanel.add(typePlainTemplate);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(typePanel, gridBagConstraints);

        idText.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 10);
        jPanel1.add(idText, gridBagConstraints);

        groupIndexPanel.setLayout(new javax.swing.BoxLayout(groupIndexPanel, javax.swing.BoxLayout.LINE_AXIS));

        groupIndexInsertCheck.setText("Insert Menu");
        groupIndexPanel.add(groupIndexInsertCheck);

        groupIndexSaveCheck.setText("Save Menu");
        groupIndexPanel.add(groupIndexSaveCheck);

        groupIndexExecCheck.setText("Exec Menu");
        groupIndexPanel.add(groupIndexExecCheck);

        groupIndexToolbarCheck.setText("Toolbar");
        groupIndexPanel.add(groupIndexToolbarCheck);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(groupIndexPanel, gridBagConstraints);

        groupIndexText.setText("Integration Location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel1.add(groupIndexText, gridBagConstraints);

        idLabel.setText("Id");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel1.add(idLabel, gridBagConstraints);

        confirmCheck.setText("Confirm Message");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel1.add(confirmCheck, gridBagConstraints);

        prefixWithSeparatorCheck.setText("Prefix With Separator");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel1.add(prefixWithSeparatorCheck, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.weighty = 3.0;
        add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("All Scripts"));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        list.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listScroll.setViewportView(list);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 3.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(listScroll, gridBagConstraints);

        listToolbar.setRollover(true);

        addButton.setText("+");
        addButton.setFocusable(false);
        addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        listToolbar.add(addButton);

        removeButton.setText("-");
        removeButton.setFocusable(false);
        removeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        listToolbar.add(removeButton);

        upButton.setText("up");
        upButton.setFocusable(false);
        upButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        upButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });
        listToolbar.add(upButton);

        downButton.setText("down");
        downButton.setFocusable(false);
        downButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        downButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });
        listToolbar.add(downButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(listToolbar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        updateCurrent();
        int index = list.getSelectedIndex();
        if (index > 0) {
            DefaultListModel model = (DefaultListModel) list.getModel();
            model.remove(index);
            if (index < model.size()) {
                list.setSelectedIndex(index);
            } else if (index > 0) {
                list.setSelectedIndex(index - 1);
            }
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
        updateCurrent();
        int index = list.getSelectedIndex();
        if (index > 0) {
            DefaultListModel model = (DefaultListModel) list.getModel();
            JSTSqlTemplateInfo x = (JSTSqlTemplateInfo) model.getElementAt(index);
            JSTSqlTemplateInfo y = (JSTSqlTemplateInfo) model.getElementAt(index - 1);
            x.setSstIndex(index - 1);
            y.setSstIndex(index);
            model.setElementAt(x, x.getSstIndex());
            model.setElementAt(y, y.getSstIndex());
            list.setSelectedIndex(index - 1);
        }
    }//GEN-LAST:event_upButtonActionPerformed

    private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
        updateCurrent();
        int index = list.getSelectedIndex();
        if (index < (list.getModel().getSize() - 1)) {
            DefaultListModel model = (DefaultListModel) list.getModel();
            JSTSqlTemplateInfo x = (JSTSqlTemplateInfo) model.getElementAt(index);
            JSTSqlTemplateInfo y = (JSTSqlTemplateInfo) model.getElementAt(index + 1);
            x.setSstIndex(index + 1);
            y.setSstIndex(index);
            model.setElementAt(x, x.getSstIndex());
            model.setElementAt(y, y.getSstIndex());
            list.setSelectedIndex(index + 1);
        }
    }//GEN-LAST:event_downButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        updateCurrent();
        JSTSqlTemplateInfo newItem = new JSTSqlTemplateInfo();
        newItem.setSstName("NO_NAME");
        DefaultListModel model = (DefaultListModel) list.getModel();
        newItem.setSstIndex(model.size());
        model.addElement(newItem);
        list.setSelectedIndex(newItem.getSstIndex());
    }//GEN-LAST:event_addButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JCheckBox confirmCheck;
    private javax.swing.JTextField confirmText;
    private javax.swing.JButton downButton;
    private javax.swing.JCheckBox enabledCheck;
    private javax.swing.JCheckBox filterColumn;
    private javax.swing.JLabel filterLabel;
    private javax.swing.JPanel filterPanel;
    private javax.swing.JCheckBox filterProcedure;
    private javax.swing.JCheckBox filterSystemTable;
    private javax.swing.JCheckBox filterTable;
    private javax.swing.JCheckBox filterView;
    private javax.swing.JCheckBox groupIndexExecCheck;
    private javax.swing.JCheckBox groupIndexInsertCheck;
    private javax.swing.JPanel groupIndexPanel;
    private javax.swing.JCheckBox groupIndexSaveCheck;
    private javax.swing.JLabel groupIndexText;
    private javax.swing.JCheckBox groupIndexToolbarCheck;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField idText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JList list;
    private javax.swing.JScrollPane listScroll;
    private javax.swing.JToolBar listToolbar;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameText;
    private javax.swing.JPanel optionPanel;
    private javax.swing.JCheckBox prefixWithSeparatorCheck;
    private javax.swing.JButton removeButton;
    private javax.swing.JEditorPane sqlArea;
    private javax.swing.JLabel sqlAreaLabel;
    private javax.swing.JScrollPane sqlAreaScroll;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JPanel typePanel;
    private javax.swing.JRadioButton typePlainTemplate;
    private javax.swing.JRadioButton typeProcessedTemplate;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables
}
