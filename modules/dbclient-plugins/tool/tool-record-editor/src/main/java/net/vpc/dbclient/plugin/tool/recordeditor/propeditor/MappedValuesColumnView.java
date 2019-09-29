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

package net.vpc.dbclient.plugin.tool.recordeditor.propeditor;

import net.vpc.dbclient.api.viewmanager.DBCTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 aout 2006 20:09:00
 */
public class MappedValuesColumnView extends ColumnViewWrapper {
    private LinkedHashMap<Object, String> data = new LinkedHashMap<Object, String>();

    public MappedValuesColumnView(ColumnView base) {
        super(base);
    }

    public LinkedHashMap<Object, String> getData() {
        return data;
    }

    protected String configToString(Object o) {
        return o == null ? "" : String.valueOf(o);
    }

    protected Object configFromString(String o) {
        return o;
    }

    protected void fillDefaultData() {
    }

    public void configure() {
        super.configure();
        String pathValue = getConfigValue("Count");
        if (pathValue == null) {
            fillDefaultData();
        } else {
            int count = Integer.parseInt(pathValue);
            for (int i = 0; i < count; i++) {
                String s = getConfigValue("Model." + i);
                Object val = null;
                if (s == null || s.length() == 0) {
                    val = configFromString(null);
                } else {
                    val = configFromString(s);
                }
                String name = getConfigValue("View." + i);
                data.put(val, name == null ? configToString(val) : name);
            }
        }
    }

    @Override
    protected ConfigPanel createConfigPanel() {
        return new MapConfigPanel();
    }

    public class MapConfigPanel extends ConfigPanel {
        DBCTable table;
        String[][] data;
        boolean[] yes;

        public MapConfigPanel() {
            this(100);
        }

        public MapConfigPanel(int size) {
            super(new BorderLayout());
            data = new String[size][2];
            yes = new boolean[data.length];

            table = getSession().getFactory().newInstance(DBCTable.class);
            table.setModel(new DefaultTableModel() {
                @Override
                public int getRowCount() {
                    return data == null ? 0 : data.length;
                }

                @Override
                public int getColumnCount() {
                    return data == null ? 0 : 3;
                }

                @Override
                public String getColumnName(int columnIndex) {
                    switch (columnIndex) {
                        case 0: {
                            return "Selected";
                        }
                        case 1: {
                            return "Key";
                        }
                        case 2: {
                            return "Value";
                        }
                    }
                    return null;
                }

                @Override
                public Class getColumnClass(int columnIndex) {
                    switch (columnIndex) {
                        case 0: {
                            return Boolean.class;
                        }
                        case 1: {
                            return String.class;
                        }
                        case 2: {
                            return String.class;
                        }
                    }
                    return String.class;
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return true;
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    if (columnIndex > 0) {
                        return data[rowIndex][columnIndex - 1];
                    } else {
                        return yes[rowIndex];
                    }
                }

                @Override
                public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                    if (columnIndex > 0) {
                        data[rowIndex][columnIndex - 1] = (String) aValue;
                        yes[rowIndex] = (aValue == null || ((String) aValue).length() > 0);
                    } else {
                        yes[rowIndex] = (Boolean) aValue;
                    }
                }
            });
            JScrollPane p = new JScrollPane(table.getComponent());
            p.setPreferredSize(new Dimension(500, 400));
            add(p);
        }

        public LinkedHashMap<String, String> getMap() {
            LinkedHashMap<String, String> m = new LinkedHashMap<String, String>();
            for (int i = 0; i < data.length; i++) {
                String[] strings = data[i];
                if (yes[i]) {
                    m.put(strings[0], strings[1]);
                }
            }
            return m;
        }

        public void setMap(Map<String, String> m) {
            for (int i = 0; i < data.length; i++) {
                data[i][0] = "";
                data[i][1] = "";
                yes[i] = false;
            }
            if (m != null) {
                int x = 0;
                for (Map.Entry<String, String> entry1 : m.entrySet()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) entry1;
                    data[x][0] = entry.getKey();
                    data[x][1] = entry.getValue();
                    yes[x] = true;//(data[x][0] == null || data[x][0].length() == 0) || (data[x][1] == null || data[x][1].length() == 1);
                    x++;
                }
            }
        }

        public void load() {
            LinkedHashMap<String, String> x = new LinkedHashMap<String, String>();
            for (Map.Entry<Object, String> entry : getData().entrySet()) {
                x.put(configToString(entry.getKey()), entry.getValue());
            }
            setMap(x);
        }

        public void store() {
            LinkedHashMap<String, String> x = getMap();
            LinkedHashMap<Object, String> data0 = null;
            data0 = new LinkedHashMap<Object, String>();
            for (Map.Entry<String, String> entry : x.entrySet()) {
                data0.put(configFromString(entry.getKey()), entry.getValue());
            }
            getData().clear();
            getData().putAll(data0);
            resetConfig();
            setConfigValue("Count", String.valueOf(getData().size()));
            int index = 0;
            for (Map.Entry<String, String> entry : x.entrySet()) {
                setConfigValue("Model." + index, entry.getKey());
                setConfigValue("View." + index, entry.getValue());
                index++;
            }
        }
    }

}
