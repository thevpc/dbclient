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

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public class IntegerBitMaskColumnView extends MappedValuesColumnView {

    private static class CheckList extends JPanel {
        private LinkedHashMap<Object, JCheckBox> btns = new LinkedHashMap<Object, JCheckBox>();

        public CheckList() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        }

        protected void addItem(Object o, String name) {
            JCheckBox c = new JCheckBox(name);
            c.putClientProperty("index", btns.size());
            c.putClientProperty("object", o);
            btns.put(o, c);
            add(c);
        }

        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            for (JCheckBox jRadioButton : btns.values()) {
                jRadioButton.setEnabled(enabled);
            }
        }

        public void setValue(Object value) {
            for (JCheckBox jRadioButton : btns.values()) {
                Integer o = (Integer) jRadioButton.getClientProperty("object");
                int a=0;
                int b=0;
                if (o != null && value!=null) {
                    a=(Integer) o;
                    b=(Integer) value;
                }
                jRadioButton.setSelected((a&b)!=0);
            }
        }

        public Object getValue() {
            int x=0;
            for (JCheckBox jRadioButton : btns.values()) {
                if (jRadioButton.isSelected()) {
                    Integer o = (Integer) jRadioButton.getClientProperty("object");
                    if(o!=null){
                        x+=o;
                    }
                }
            }
            return x;
        }

    }

    public IntegerBitMaskColumnView() {
        super(new IntegerColumnView());
        setConfigurable(true);
    }

    protected void fillDefaultData() {
        getData().put(1,"value 1");
        getData().put(2,"value 2");
        getData().put(4,"value 4");
        getData().put(8,"value 8");
        getData().put(16,"value 16");
    }

    protected Object configFromString(String o) {
        int x= Integer.parseInt(o);
        if(x<=2){
            return x;
        }
        int y=x;
        while(y!=0 && y!=2){
            if(y==1){
                throw new RuntimeException("Invalid Bit Mask Value : "+x);
            }
            y=y/2;
        }
        return x;
    }

    protected JComponent createEditComponent() {
        IntegerBitMaskColumnView.CheckList editComponent = new IntegerBitMaskColumnView.CheckList();
        for (Map.Entry<Object, String> entry : getData().entrySet()) {
            editComponent.addItem(entry.getKey(), entry.getValue());
        }
        install(editComponent);
        return editComponent;
    }

    public void setValue(Object value) {
        IntegerBitMaskColumnView.CheckList com = ((IntegerBitMaskColumnView.CheckList) getEditComponentDelegate());
        com.setValue(value);
    }

    public Object getValue() {
        IntegerBitMaskColumnView.CheckList com = ((IntegerBitMaskColumnView.CheckList) getEditComponentDelegate());
        return com.getValue();
    }

}
