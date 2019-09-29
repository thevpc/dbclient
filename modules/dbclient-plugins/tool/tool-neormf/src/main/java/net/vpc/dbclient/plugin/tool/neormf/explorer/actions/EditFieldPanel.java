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

package net.vpc.dbclient.plugin.tool.neormf.explorer.actions;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.vpc.dbclient.plugin.tool.neormf.NUtils;
import net.vpc.dbclient.plugin.tool.neormf.explorer.NNode;
import org.vpc.neormf.commons.sql.DAOFieldKind;
import org.vpc.neormf.jbgen.config.ConfigNode;
import org.vpc.neormf.jbgen.util.FieldFormulaType;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.sql.Types;
import java.util.TreeSet;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 22 avr. 2007 18:33:44
 */
public class EditFieldPanel extends JPanel {
    DBCSQLEditor sqlQuery = null;
    DBCSQLEditor sqlView = null;
    DBCSQLEditor sqlCall = null;
    DBCSQLEditor sqlFunction = null;
    JTextArea code = null;
    JTextField search = new JTextField();
    JTextField detailTable = new JTextField();
    JTextField detailPrimaryFields = new JTextField();
    JTextField foreignFields = new JTextField();
    JComboBox kind = new JComboBox();
    JComboBox fieldDataType = new JComboBox();
    JComboBox fieldConverter = new JComboBox();
    JComboBox fieldImplementation = new JComboBox();
    JTextField fieldTitle = new JTextField();
    JComboBox fieldSqlNullable = new JComboBox();
    JComboBox fieldSqlType = new JComboBox();
    JTextField fieldSqlSize = new JTextField();
    JTextField fieldSqlBufferLength = new JTextField();
    JTextField fieldSqlDecimalDigits = new JTextField();
    JTextField fieldSqlNumPrecRadix = new JTextField();
    JTextField fieldMin = new JTextField();
    JTextField fieldMax = new JTextField();
    JTextField fieldValues = new JTextField();
    JComboBox fieldSqlMultiLine = new JComboBox();
    JComboBox forbiddenOnInsert = new JComboBox();
    JComboBox forbiddenOnUpdate = new JComboBox();
    JComboBox requiredOnInsert = new JComboBox();
    JComboBox forbiddenOnSearch = new JComboBox();
    JTextField oName = new JTextField();
    JTextField doName = new JTextField();
    JTextArea preInsert = new JTextArea();
    JTextArea postInsert = new JTextArea();
    JTextArea preUpdate = new JTextArea();
    JTextArea postUpdate = new JTextArea();
    NNode node;
    ConfigNode parent;
    DBCSession session;
    private boolean generic;

    public EditFieldPanel(NNode node, String doNameString, ConfigNode parent, DBCSession session, boolean generic) {
        super(new BorderLayout());
        this.session = session;
        this.parent = parent;
        this.generic = generic;
        this.node = node;
        for (DAOFieldKind fieldKind : DAOFieldKind.values()) {
            kind.addItem(fieldKind.toString());
        }
        doName.setText(doNameString);
        doName.setEditable(generic);
        this.generic = generic;
//        kind.setRenderer(new DefaultListCellRenderer() {
//            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                if (value.equals("true")) {
//                    value = "live-field";
//                }
//                if (value.equals("false")) {
//                    value = "regular-existant";
//                }
//                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);    //To change body of overridden methods use File | Settings | File Templates.
//            }
//        });

        fieldSqlMultiLine.addItem("");
        fieldSqlMultiLine.addItem("true");
        fieldSqlMultiLine.addItem("false");
        fieldSqlMultiLine.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value.equals("true")) {
                    value = "Multi Line";
                }
                if (value.equals("false")) {
                    value = "Single Line";
                }
                if (value.equals("")) {
                    value = " ";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });

        fieldDataType.addItem("");
        fieldDataType.addItem("new StringType()");
        fieldDataType.addItem("new StringType(boolean nullable)");
        fieldDataType.addItem("new StringType(boolean nullable, int minLength, int maxLength,boolean multiline)");
        fieldDataType.addItem("new DoubleType()");
        fieldDataType.addItem("new DoubleType(boolean nullable)");
        fieldDataType.addItem("new DoubleType(boolean nullable, double limit, boolean isSuperiorLimit)");
        fieldDataType.addItem("new DoubleType(boolean nullable, double minValue, double maxValue)");
        fieldDataType.addItem("new BooleanType()");
        fieldDataType.addItem("new BooleanType(boolean nullable)");
        fieldDataType.addItem("new DateType()");
        fieldDataType.addItem("new DateType(boolean nullable)");
        fieldDataType.addItem("new DateType(boolean nullable, java.util.Date min, java.util.Date max)");
        fieldDataType.addItem("new DateMonthType()");
        fieldDataType.addItem("new DateMonthType(boolean nullable)");
        fieldDataType.addItem("new DateMonthType(boolean nullable, Date min, Date max)");

        fieldDataType.addItem("new IntType()");
        fieldDataType.addItem("new IntType(boolean nullable)");
        fieldDataType.addItem("new IntType(boolean nullable, int limit, boolean isSuperiorLimit)");
        fieldDataType.addItem("new IntType(boolean nullable, int minValue, int maxValue)");
        fieldDataType.addItem("new ListChoiceType(boolean nullable, Object[] elements, Object[] renderers,DataType elementType)");
        fieldDataType.addItem("new ListChoiceType(boolean nullable, Object[] elements,DataType elementType)");
        fieldDataType.addItem("new DataContentChoiceTypeForClientConnector(boolean nullable, DataContent dataPrototype)");
        fieldDataType.addItem("new AnyType()");
        fieldDataType.addItem("new IntType()");
        fieldDataType.addItem("new TimeType()");
        fieldDataType.addItem("new TimeType(boolean nullable)");
        fieldDataType.addItem("new TimeType(boolean nullable, java.util.Date min, java.util.Date max)");
        fieldDataType.addItem("new TimeDayType()");
        fieldDataType.addItem("new TimeDayType(boolean nullable)");
        fieldDataType.addItem("new TimeDayType(boolean nullable, Timestamp min, Timestamp max)");
        fieldDataType.addItem("new DateTimeType()");
        fieldDataType.addItem("new DateTimeType(boolean nullable)");
        fieldDataType.addItem("new DateTimeType(boolean nullable, java.sql.Timestamp min, java.sql.Timestamp max)");
        fieldSqlNullable.addItem("");
        fieldSqlNullable.addItem("true");
        fieldSqlNullable.addItem("false");
        DefaultListCellRenderer nullableRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value.equals("true")) {
                    value = "Nullable";
                }
                if (value.equals("false")) {
                    value = "Not Nullable";
                }
                if (value.equals("")) {
                    value = " ";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);    //To change body of overridden methods use File | Settings | File Templates.
            }
        };
        DefaultListCellRenderer trueFalseRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value.equals("true")) {
                    value = "Yes";
                }
                if (value.equals("false")) {
                    value = "No";
                }
                if (value.equals("")) {
                    value = " ";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);    //To change body of overridden methods use File | Settings | File Templates.
            }
        };
        fieldSqlNullable.setRenderer(nullableRenderer);
        fieldConverter.addItem("");
        fieldConverter.addItem("Converters.INTEGER_TO_BOOLEAN");
        fieldConverter.addItem("Converters.STRING1_YN_TO_BOOLEAN");
        fieldConverter.addItem("new String1ToBoolean('Y', 'N',false)");
        fieldConverter.setEditable(true);

        requiredOnInsert.addItem("");
        requiredOnInsert.addItem("true");
        requiredOnInsert.addItem("false");
        requiredOnInsert.setRenderer(trueFalseRenderer);

        forbiddenOnInsert.addItem("");
        forbiddenOnInsert.addItem("true");
        forbiddenOnInsert.addItem("false");
        forbiddenOnInsert.setRenderer(trueFalseRenderer);

        forbiddenOnUpdate.addItem("");
        forbiddenOnUpdate.addItem("true");
        forbiddenOnUpdate.addItem("false");
        forbiddenOnUpdate.setRenderer(trueFalseRenderer);

        forbiddenOnSearch.addItem("");
        forbiddenOnSearch.addItem("true");
        forbiddenOnSearch.addItem("false");
        forbiddenOnSearch.setRenderer(trueFalseRenderer);

        fieldSqlType.addItem("");
        Field[] f = Types.class.getFields();
        TreeSet<String> allf = new TreeSet<String>();//to sort types
        for (int i = 0; i < f.length; i++) {
            allf.add(f[i].getName());
        }
        for (String s : allf) {
            fieldSqlType.addItem(s);
        }

        JTabbedPane pane = new JTabbedPane();
//            pane.setTabPlacement(JTabbedPane.LEFT);
        Dimension jspd = new Dimension(400, 100);
        Insets insets = new Insets(3, 3, 3, 3);

        JPanel props = new JPanel(new DumbGridBagLayout()
                .addLine("[<~nameL][<=-name][<~doL][<=-do]")
                .addLine("[<=titleL][<=-title][<=actionL][<=-action]")
                .addLine("[<=dtypeL][<=-dtype:: ]")
                .setInsets(".*", insets)
        );
        oName.setEditable(node == null);
        oName.setText(node == null ? "" : node.getName());
        fieldDataType.setEditable(true);
        props.add(new JLabel("Title"), "titleL");
        props.add(fieldTitle, "title");
        props.add(new JLabel("Kind"), "actionL");
        props.add(kind, "action");
        props.add(new JLabel("Datatype"), "dtypeL");
        props.add(fieldDataType, "dtype");
        props.add(new JLabel("Name"), "nameL");
        props.add(oName, "name");
        props.add(new JLabel("DO Name"), "doL");
        props.add(doName, "do");
        pane.addTab("Properties", new JScrollPane(props));

        JPanel constraints = new JPanel(new DumbGridBagLayout()
                .addLine("[<~nullL][<=-null][<=multilineL][<=-multiline]")
                .addLine("[<=minL][<=-min][<=maxL][<=-max]")
                .addLine("[<=valuesL :::]")
                .addLine("[<=-values :::]")
                .setInsets(".*", insets)
        );
        constraints.add(new JLabel("Min"), "minL");
        constraints.add(fieldMin, "min");
        constraints.add(new JLabel("Max"), "maxL");
        constraints.add(fieldMax, "max");
        constraints.add(new JLabel("Multiline (if string)"), "multilineL");
        constraints.add(fieldSqlMultiLine, "multiline");
        constraints.add(new JLabel("Values (';' separated)"), "valuesL");
        constraints.add(fieldValues, "values");
        constraints.add(new JLabel("Nullable"), "nullL");
        constraints.add(fieldSqlNullable, "null");

        pane.addTab("Constraints", new JScrollPane(constraints));

        JPanel sql = new JPanel(new DumbGridBagLayout()
                .addLine("[<=typeL][<=-type]")
                .addLine("[<=sizeL][<=-size]")
                .addLine("[<=lenL][<=-len]")
                .addLine("[<=decmL][<=-decm]")
                .addLine("[<=radixL][<=-radix]")
                .addLine("[<=convL][<=-conv]")
                .setInsets(".*", insets)
        );
        fieldSqlType.setEditable(true);
        sql.add(new JLabel("SQL Type"), "typeL");
        sql.add(fieldSqlType, "type");
        sql.add(new JLabel("Sql Size"), "sizeL");
        sql.add(fieldSqlSize, "size");
        sql.add(new JLabel("Sql Buffer Length"), "lenL");
        sql.add(fieldSqlBufferLength, "len");
        sql.add(new JLabel("Sql Decimal Digits"), "decmL");
        sql.add(fieldSqlDecimalDigits, "decm");
        sql.add(new JLabel("Sql NumPrecRadix"), "radixL");
        sql.add(fieldSqlNumPrecRadix, "radix");
        sql.add(new JLabel("Converter"), "convL");
        sql.add(fieldConverter, "conv");

        pane.addTab("SQL Type", new JScrollPane(sql));

        pane.addTab("Impl", createComponentForFieldImpl());


        JPanel insert = new JPanel(new DumbGridBagLayout()
                .addLine("[<=f1L][<=-f1][<=f2L][<=-f2]")
                .addLine("[<L1 :::]")
                .addLine("[<$+=C1 :::]")
                .addLine("[<L2 :::]")
                .addLine("[<$+=C2 :::]")
                .setInsets(".*", insets)
        );
        JScrollPane jsp;
        jsp = new JScrollPane(preInsert);
        jsp.setPreferredSize(jspd);
        insert.add(new JLabel("PreInsert Code"), "L1");
        insert.add(jsp, "C1");
        jsp = new JScrollPane(postInsert);
        jsp.setPreferredSize(jspd);
        insert.add(new JLabel("PostInsert Code"), "L2");
        insert.add(jsp, "C2");
        insert.add(requiredOnInsert, "f1");
        insert.add(forbiddenOnInsert, "f2");
        insert.add(new JLabel("Required On Insert"), "f1L");
        insert.add(new JLabel("Forbidden On Insert"), "f2L");
        pane.addTab("Insert", new JScrollPane(insert));

        JPanel update = new JPanel(new DumbGridBagLayout()
                .addLine("[<=f3L][<=-f3]")
                .addLine("[<L1 :]")
                .addLine("[<$+=C1 :]")
                .addLine("[<L2 :]")
                .addLine("[<$+=C2 :]")
                .setInsets(".*", insets)
        );
        jsp = new JScrollPane(preUpdate);
        jsp.setPreferredSize(jspd);
        update.add(new JLabel("Forbidden On Update"), "f3L");
        update.add(forbiddenOnUpdate, "f3");
        update.add(new JLabel("PreUpdate Code"), "L1");
        update.add(jsp, "C1");
        jsp = new JScrollPane(postUpdate);
        jsp.setPreferredSize(jspd);
        update.add(new JLabel("PostUpdate Code"), "L2");
        update.add(jsp, "C2");
        pane.addTab("Update", new JScrollPane(update));

        JPanel find = new JPanel(new DumbGridBagLayout()
                .addLine("[<-f4L ][<=-f4]")
                .addLine("[<=-searchL : ]")
                .addLine("[<=-search  : ]")
                .setInsets(".*", insets)
        );
        find.add(new JLabel("Forbidden On Find"), "f4L");
        find.add(forbiddenOnSearch, "f4");
        find.add(new JLabel("Find Expression (default {columnName} = {columnValue})"), "searchL");
        find.add(search, "search");
        pane.addTab("Find", new JScrollPane(find));

        this.add(pane, BorderLayout.CENTER);
    }

    public JComponent createComponentForFieldImpl() {
        final JPanel all = new JPanel();
        final CardLayout fieldImplLayout = new CardLayout();
        all.setLayout(fieldImplLayout);
//        fieldImplementation.addItem("");
        for (FieldFormulaType fieldFormulaType : FieldFormulaType.values()) {
            fieldImplementation.addItem(fieldFormulaType.toString());
        }
        fieldImplementation.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String s = (String) e.getItem();
                fieldImplLayout.show(all, s);
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        all.add(new JLabel(""), FieldFormulaType.none.toString());

        JPanel sqlQueryPanel = new JPanel(new DumbGridBagLayout()
                .addLine("[<+~$=comp]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        sqlQuery = session.getFactory().newInstance(DBCSQLEditor.class);
        {
            JScrollPane pane = new JScrollPane(sqlQuery.getComponent());
            pane.setPreferredSize(new Dimension(400, 100));
            sqlQueryPanel.add(pane, "comp");
            all.add(sqlQueryPanel, FieldFormulaType.sqlQuery.toString());
        }
        JPanel sqlViewPanel = new JPanel(new DumbGridBagLayout()
                .addLine("[<+~$=comp]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        {
            sqlView = session.getFactory().newInstance(DBCSQLEditor.class);
            JScrollPane pane = new JScrollPane(sqlView.getComponent());
            pane.setPreferredSize(new Dimension(400, 100));
            sqlViewPanel.add(pane, "comp");
            all.add(sqlViewPanel, FieldFormulaType.sqlView.toString());
        }

        JPanel sqlCallPanel = new JPanel(new DumbGridBagLayout()
                .addLine("[<+~$=comp]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        {
            sqlCall = session.getFactory().newInstance(DBCSQLEditor.class);
            JScrollPane pane = new JScrollPane(sqlCall.getComponent());
            pane.setPreferredSize(new Dimension(400, 100));
            sqlCallPanel.add(pane, "comp");
            all.add(sqlCallPanel, FieldFormulaType.sqlCall.toString());
        }

        JPanel sqlFunctionPanel = new JPanel(new DumbGridBagLayout()
                .addLine("[<~+$=comp]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        {
            sqlFunction = session.getFactory().newInstance(DBCSQLEditor.class);

            JScrollPane pane = new JScrollPane(sqlFunction.getComponent());
            pane.setPreferredSize(new Dimension(400, 100));
            sqlFunctionPanel.add(pane, "comp");
            all.add(sqlFunctionPanel, FieldFormulaType.sqlFunction.toString());
        }

        JPanel codePanel = new JPanel(new DumbGridBagLayout()
                .addLine("[<~+$=comp]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        try {
            code = new JTextArea();
            JScrollPane pane = new JScrollPane(code);
            pane.setPreferredSize(new Dimension(400, 100));
            codePanel.add(pane, "comp");
            all.add(codePanel, FieldFormulaType.code.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel relPanel = new JPanel(new DumbGridBagLayout()
                .addLine("[<~tabL][<-=tab]")
                .addLine("[<f1L][<-=f1]")
                .addLine("[<f2L][<-=f2]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        try {
            relPanel.add(new JLabel("Detail Table"), "tabL");
            relPanel.add(detailTable, "tab");
            relPanel.add(new JLabel("Detail Primary Fields"), "f1L");
            relPanel.add(detailPrimaryFields, "f1");
            relPanel.add(new JLabel("Foreign Fields"), "f2L");
            relPanel.add(foreignFields, "f2");
            all.add(relPanel, FieldFormulaType.sqlMasterDetail.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel ret = new JPanel(new DumbGridBagLayout()
                .addLine("[~<-=selL][~<-=sel]")
                .addLine("[<$$==+val : ]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        ret.add(new JLabel("Get/Set Implementation"), "selL");
        ret.add(fieldImplementation, "sel");
        ret.add(all, "val");
        return ret;
    }

    public void load() {
        ConfigNode[] configNodes = parent.getChildren(("field<name=" + oName.getText() + ">"));
        ConfigNode c = null;
        if (configNodes.length > 0) {
            c = configNodes[0];
        }
        NUtils.loadAttr(c, fieldMin, "min", "");
        NUtils.loadAttr(c, fieldMax, "max", "");
        NUtils.loadAttr(c, fieldSqlBufferLength, "bufferlength", "");
        NUtils.loadAttr(c, fieldSqlDecimalDigits, "decimaldegits", "");
        NUtils.loadAttr(c, fieldSqlMultiLine, "multiline", "");
        NUtils.loadAttr(c, fieldSqlNullable, "nullable", "");
        NUtils.loadAttr(c, fieldSqlNumPrecRadix, "numrecradix", "");
        NUtils.loadAttr(c, fieldSqlSize, "columnsize", "");
        NUtils.loadAttr(c, fieldSqlType, "typename", "");
        NUtils.loadAttr(c, fieldValues, "values", "");
        NUtils.loadAttr(c, fieldTitle, "title", "");
        NUtils.loadAttr(c, requiredOnInsert, "isRequiredOnInsert", "");
        NUtils.loadAttr(c, forbiddenOnInsert, "isForbiddenOnInsert", "");
        NUtils.loadAttr(c, forbiddenOnUpdate, "isForbiddenOnUpdate", "");
        NUtils.loadAttr(c, forbiddenOnSearch, "isForbiddenOnSearch", "");
        NUtils.loadAttr(c, kind, "kind", "regular");

        sqlCall.setText("");
        sqlFunction.setText("");
        sqlQuery.setText("");
        detailPrimaryFields.setText("");
        foreignFields.setText("");
        detailTable.setText("");
        sqlView.setText("");
        code.setText("");
        if (c != null) {
            fieldDataType.setSelectedItem(c.getChildOrCreateIt("datatype").getValue());
            fieldConverter.setSelectedItem(c.getChildOrCreateIt("converter").getValue());
            search.setText(c.getChildOrCreateIt("search").getValue());
            for (ConfigNode configNode : c.getChildren(("userCode.preInsert"))) {
                preInsert.setText(configNode.getValue());
                break;
            }
            for (ConfigNode configNode : c.getChildren(("userCode.postInsert"))) {
                postInsert.setText(configNode.getValue());
                break;
            }
            for (ConfigNode configNode : c.getChildren(("userCode.preUpdate"))) {
                preUpdate.setText(configNode.getValue());
                break;
            }
            for (ConfigNode configNode : c.getChildren(("userCode.postUpdate"))) {
                postUpdate.setText(configNode.getValue());
                break;
            }

            ConfigNode cc = c.getChild(FieldFormulaType.sqlQuery + "FieldExpression", false);
            if (cc != null) {
                fieldImplementation.setSelectedItem(FieldFormulaType.sqlQuery.toString());
                sqlQuery.setText(cc.getValue());
            }

            cc = c.getChild(FieldFormulaType.sqlView + "FieldExpression", false);
            if (cc != null) {
                fieldImplementation.setSelectedItem(FieldFormulaType.sqlView.toString());
                sqlView.setText(cc.getValue());
            }

            cc = c.getChild(FieldFormulaType.sqlFunction + "FieldExpression", false);
            if (cc != null) {
                fieldImplementation.setSelectedItem(FieldFormulaType.sqlFunction.toString());
                sqlFunction.setText(cc.getValue());
            }

            cc = c.getChild(FieldFormulaType.code + "FieldExpression", false);
            if (cc != null) {
                fieldImplementation.setSelectedItem(FieldFormulaType.code.toString());
                code.setText(cc.getValue());
            }


            if (cc == null) {
                cc = c.getChild(FieldFormulaType.sqlMasterDetail + "FieldExpression", false);
                if (cc != null) {
                    fieldImplementation.setSelectedItem(FieldFormulaType.sqlMasterDetail.toString());
                    detailTable.setText(cc.getAttribute("detailTable"));
                    detailPrimaryFields.setText(cc.getAttribute("detailPrimaryFields"));
                    foreignFields.setText(cc.getAttribute("foreignFields"));
                }
            }
        }
    }

    public void store() {
        ConfigNode[] configNodes = parent.getChildren(("field<name=\"" + oName.getText() + "\">"));
        ConfigNode c;
        String theName = oName.getText().trim();
        if (theName.length() <= 0) {
            return;
        }
        if (configNodes.length > 0) {
            c = configNodes[0];
        } else {
            c = new ConfigNode("field");
            c.setName(theName);
            parent.add(c);
        }
        NUtils.storeAttr(c, fieldMin, "min", "");
        NUtils.storeAttr(c, fieldMax, "max", "");
        NUtils.storeAttr(c, fieldSqlBufferLength, "bufferlength", "");
        NUtils.storeAttr(c, fieldSqlDecimalDigits, "decimaldegits", "");
        NUtils.storeAttr(c, fieldSqlMultiLine, "multiline", "");
        NUtils.storeAttr(c, fieldSqlNullable, "nullable", "");
        NUtils.storeAttr(c, fieldSqlNumPrecRadix, "numrecradix", "");
        NUtils.storeAttr(c, fieldSqlSize, "columnsize", "");
        NUtils.storeAttr(c, fieldSqlType, "typename", "");
        NUtils.storeAttr(c, fieldValues, "values", "");
        NUtils.storeAttr(c, fieldTitle, "title", "");
        NUtils.storeAttr(c, requiredOnInsert, "isRequiredOnInsert", "");
        NUtils.storeAttr(c, forbiddenOnInsert, "isForbiddenOnInsert", "");
        NUtils.storeAttr(c, forbiddenOnUpdate, "isForbiddenOnUpdate", "");
        NUtils.storeAttr(c, forbiddenOnSearch, "isForbiddenOnSearch", "");
        NUtils.storeAttr(c, kind, "kind", "regular");
        if (c != null) {
            String valueFatatype = (String) fieldDataType.getSelectedItem();
            if (valueFatatype == null) {
                c.getChildOrCreateIt("datatype").remove();
            } else {
                c.getChildOrCreateIt("datatype").setValue(valueFatatype);
            }

            String valueConverter = (String) fieldConverter.getSelectedItem();
            if (valueConverter == null) {
                c.getChildOrCreateIt("converter").remove();
            } else {
                c.getChildOrCreateIt("converter").setValue(valueConverter);
            }
            String valueSearch = search.getText();
            if (valueSearch == null || valueSearch.length() == 0) {
                c.getChildOrCreateIt("search").remove();
            } else {
                c.getChildOrCreateIt("search").setValue(valueSearch);
            }

            String valueUserCode = preInsert.getText();
            if (valueUserCode == null) {
                c.getChildOrCreateIt("userCode").getChildOrCreateIt("preInsert").remove();
            } else {
                c.getChildOrCreateIt("userCode").getChildOrCreateIt("preInsert").setValue(valueUserCode);
            }

            valueUserCode = postInsert.getText();
            if (valueUserCode == null) {
                c.getChildOrCreateIt("userCode").getChildOrCreateIt("postInsert").remove();
            } else {
                c.getChildOrCreateIt("userCode").getChildOrCreateIt("postInsert").setValue(valueUserCode);
            }

            valueUserCode = preUpdate.getText();
            if (valueUserCode == null) {
                c.getChildOrCreateIt("userCode").getChildOrCreateIt("preUpdate").remove();
            } else {
                c.getChildOrCreateIt("userCode").getChildOrCreateIt("preUpdate").setValue(valueUserCode);
            }

            valueUserCode = postUpdate.getText();
            if (valueUserCode == null) {
                c.getChildOrCreateIt("userCode").getChildOrCreateIt("postUpdate").remove();
            } else {
                c.getChildOrCreateIt("userCode").getChildOrCreateIt("postUpdate").setValue(valueUserCode);
            }

            ConfigNode userCodeNode = c.getChildOrCreateIt("userCode");
            if (userCodeNode.size() == 0) {
                userCodeNode.remove();
            }

            ConfigNode[] children = c.getChildren("*FieldExpression", false, false);
            for (ConfigNode aChildren : children) {
                c.remove(aChildren);
            }
            Object i = fieldImplementation.getSelectedItem();
            if (FieldFormulaType.sqlQuery.toString().equals(i) && sqlQuery.getText().trim().length() > 0) {
                c.getChildOrCreateIt(i + "FieldExpression").setValue(sqlQuery.getText());
            }
            if (FieldFormulaType.sqlCall.toString().equals(i) && sqlCall.getText().trim().length() > 0) {
                c.getChildOrCreateIt(i + "FieldExpression").setValue(sqlCall.getText());
            }
            if (FieldFormulaType.sqlFunction.toString().equals(i) && sqlFunction.getText().trim().length() > 0) {
                c.getChildOrCreateIt(i + "FieldExpression").setValue(sqlFunction.getText());
            }
            if (FieldFormulaType.sqlView.toString().equals(i) && sqlView.getText().trim().length() > 0) {
                c.getChildOrCreateIt(i + "FieldExpression").setValue(sqlView.getText());
            }
            if (FieldFormulaType.sqlMasterDetail.toString().equals(i)
                    && detailTable.getText().trim().length() > 0
                    ) {
                c.getChildOrCreateIt(i + "FieldExpression").setAttribute("detailTable", detailTable.getText());
                c.getChildOrCreateIt(i + "FieldExpression").setAttribute("detailPrimaryFields", detailPrimaryFields.getText());
                c.getChildOrCreateIt(i + "FieldExpression").setAttribute("foreignFields", foreignFields.getText());
            }
            if (FieldFormulaType.code.toString().equals(i) && code.getText().trim().length() > 0) {
                c.getChildOrCreateIt(i + "FieldExpression").setValue(code.getText());
            }
        }
    }

}
