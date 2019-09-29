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

package net.vpc.dbclient.plugin.system.viewmanager;

import net.vpc.dbclient.api.viewmanager.DBCComponentFormat;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.JButtonColorChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 8 févr. 2007 20:43:02
 */
public class ComponentFormatEditor extends JPanel {
    public static final String FORMAT_CHANGED = "ComponentFormatEditor.FORMAT_CHANGED";
    DBCComponentFormat format;
    JButtonColorChooser foregroundComponent;
    JButtonColorChooser backgroundComponent;
    JComboboxFontNames fontNames;
    JComboboxFontSizes fontSizes;
    JCheckBox styleBold;
    JCheckBox styleItalic;
    JCheckBox stylePlain;
    JCheckBox styleDefault;

    protected boolean enableForeground = true;
    protected boolean enableBackground = true;
    protected boolean enableFontName = true;
    protected boolean enableFontSize = true;
    protected boolean enableFontStyle = true;

    public ComponentFormatEditor(DBCComponentFormat _format) {
        this.format = _format;
        fontNames = new JComboboxFontNames(true);
        fontSizes = new JComboboxFontSizes(true);
        foregroundComponent = new JButtonColorChooser(null, true);
        backgroundComponent = new JButtonColorChooser(null, true);
        ButtonGroup buttonGroup = new ButtonGroup();
        styleBold = new JCheckBox("Bold", false);
        styleItalic = new JCheckBox("Italic", false);
        stylePlain = new JCheckBox("Plain", false);
        styleDefault = new JCheckBox("Default", true);
        buttonGroup.add(styleBold);
        buttonGroup.add(styleItalic);
        buttonGroup.add(stylePlain);
        buttonGroup.add(styleDefault);
//        styleBold.setEnabled(false);
//        stylePlain.setEnabled(false);
//        styleItalic.setEnabled(false);
        setLayout(new DumbGridBagLayout()
                .addLine("[<~ForegroundLabel] [<~Foreground] [<~BackgroundLabel] [<~Background]")
                .addLine("[<~FontLabel      ] [<~Font       :                   :            ]")
                .addLine("[<~SizeLabel      ] [<~Size      ]                                 ")
                .addLine("[<~Style**         :              :                   :            ]")
                .setInsets(".*", new Insets(3, 3, 3, 3))
        );
        JPanel style = new JPanel(new DumbGridBagLayout()
                .addLine("[<=-default][<=-plain][<=-bold][<=-italic]")
                .setInsets(".*", new Insets(3, 3, 3, 3))
        );
        style.add(stylePlain, "plain");
        style.add(styleDefault, "default");
        style.add(styleItalic, "italic");
        style.add(styleBold, "bold");
        style.setBorder(BorderFactory.createTitledBorder("Style"));

        add(new JLabel("Foreground"), "ForegroundLabel");
        add(new JLabel("Background"), "BackgroundLabel");
        add(new JLabel("Font"), "FontLabel");
//        add(new JLabel("Style"), "StyleLabel");
        add(new JLabel("Size"), "SizeLabel");
        add(foregroundComponent, "Foreground");
        add(backgroundComponent, "Background");
        add(fontNames, "Font");
        add(fontSizes, "Size");
        add(style, "Style");

        foregroundComponent.addPropertyChangeListener(JButtonColorChooser.PROPERTY_COLOR_CHANGED, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (format != null) {
                    format.setForeground((Color) evt.getNewValue());
                }
                ComponentFormatEditor.this.firePropertyChange(ComponentFormatEditor.FORMAT_CHANGED, null, format);
            }
        });

        backgroundComponent.addPropertyChangeListener(JButtonColorChooser.PROPERTY_COLOR_CHANGED, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (format != null) {
                    format.setBackground((Color) evt.getNewValue());
                }
                ComponentFormatEditor.this.firePropertyChange(ComponentFormatEditor.FORMAT_CHANGED, null, format);
            }
        });
        fontNames.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (format != null) {
                    Font item = (Font) fontNames.getSelectedItem();
                    format.setFontName(item == null ? null : item.getFamily());
                }
                ComponentFormatEditor.this.firePropertyChange(ComponentFormatEditor.FORMAT_CHANGED, null, format);
            }
        });
        fontSizes.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (format != null) {
                    String item = (String) fontSizes.getSelectedItem();
                    format.setSize(item == null ? null : Integer.parseInt(item));
                }
                ComponentFormatEditor.this.firePropertyChange(ComponentFormatEditor.FORMAT_CHANGED, null, format);
            }
        });

        styleDefault.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (!changingSytle) {
//                    styleBold.setEnabled(e.getStateChange() == ItemEvent.DESELECTED);
//                    styleItalic.setEnabled(e.getStateChange() == ItemEvent.DESELECTED);
//                    stylePlain.setEnabled(e.getStateChange() == ItemEvent.DESELECTED);
                    revalidateStyle();
                    ComponentFormatEditor.this.firePropertyChange(ComponentFormatEditor.FORMAT_CHANGED, null, format);
                }
            }
        });
        styleBold.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (!changingSytle) {
                    revalidateStyle();
                    ComponentFormatEditor.this.firePropertyChange(ComponentFormatEditor.FORMAT_CHANGED, null, format);
                }
            }
        });
        styleItalic.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (!changingSytle) {
                    revalidateStyle();
                    ComponentFormatEditor.this.firePropertyChange(ComponentFormatEditor.FORMAT_CHANGED, null, format);
                }
            }
        });
        stylePlain.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (!changingSytle) {
                    revalidateStyle();
                    ComponentFormatEditor.this.firePropertyChange(ComponentFormatEditor.FORMAT_CHANGED, null, format);
                }
            }
        });
    }

    private void revalidateStyle() {
        if (format != null) {
            format.setStyle(
                    styleBold.isSelected() ? (Integer) Font.BOLD :
                            styleItalic.isSelected() ? (Integer) Font.ITALIC :
                                    stylePlain.isSelected() ? (Integer) Font.PLAIN :
                                            null);
        }
    }

    private boolean changingSytle = false;

    public DBCComponentFormat getFormat() {
        return format;
    }

    public void setFormat(DBCComponentFormat format) {
        this.format = format;
        fontNames.setSelectedFontName(format == null ? null : format.getFontName());
        fontSizes.setSelectedItem(format == null ? null : format.getSize() == null ? null : String.valueOf(format.getSize()));
        foregroundComponent.setValue(format == null ? null : format.getForeground());
        backgroundComponent.setValue(format == null ? null : format.getBackground());
        if (!changingSytle) {
            changingSytle = true;
            try {
                styleBold.setSelected(format != null && format.getStyle() != null && (Font.BOLD & format.getStyle()) != 0);
                styleDefault.setSelected(format == null || format.getStyle() == null);
                styleItalic.setSelected(format != null && format.getStyle() != null && (Font.ITALIC & format.getStyle()) != 0);
                stylePlain.setSelected(format != null && format.getStyle() != null && format.getStyle() == 0);
            } finally {
                changingSytle = false;
            }
        }

    }

    public static class JComboboxFontSizes extends JComboBox {
        private boolean nullable;

        public JComboboxFontSizes(boolean nullable) {
            this.nullable = nullable;
            int x = nullable ? 1 : 0;
            String[] sizes = new String[70 + x];
            if (nullable) {
                sizes[0] = null;
            }
            for (int i = 0; i < sizes.length - x; i++) {
                sizes[i + x] = String.valueOf(i + 3);
            }
            setModel(new DefaultComboBoxModel(sizes));
            setRenderer(new DefaultListCellRenderer() {
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    return super.getListCellRendererComponent(list, value == null ? "Default" : value, index, isSelected, cellHasFocus);
                }
            });
        }

        public boolean isNullable() {
            return nullable;
        }
    }

    public static class JComboboxFontNames extends JComboBox {
        private boolean nullable;
        private Font[] values;

        public JComboboxFontNames(boolean nullable) {
            this.nullable = nullable;
            Font[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
            TreeSet<String> names = new TreeSet<String>();
            ArrayList<Font> fonts = new ArrayList<Font>();
            if (nullable) {
                fonts.add(null);
            }
            for (Font aFontList : fontList) {
                String fontName = aFontList.getFamily();
                if (!names.contains(fontName)) {
                    names.add(fontName);
                    fonts.add(aFontList);
                }
            }
            values = fonts.toArray(new Font[names.size()]);
            setModel(new DefaultComboBoxModel(values));
            setRenderer(new DefaultListCellRenderer() {
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Font f = (Font) value;
                    super.getListCellRendererComponent(list, f == null ? "Default" : f.getFamily(), index, isSelected, cellHasFocus);
                    if (f != null) {
                        setFont(f.deriveFont(f.getStyle(), 12));
                    }
                    return this;
                }
            });
        }

        public boolean isNullable() {
            return nullable;
        }

        public void setSelectedFontName(String fontName) {
            if (fontName == null && nullable) {
                setSelectedIndex(0);
            } else {
                for (int i = 0; i < values.length; i++) {
                    Font value = values[i];
                    if (value != null && value.getFamily().equals(fontName)) {
                        setSelectedIndex(i);
                        break;
                    }
                }
            }
        }

        public String getFontName() {
            Font font1 = (Font) getSelectedItem();
            return font1 == null ? null : font1.getFamily();
        }
    }


    public boolean isEnableBackground() {
        return enableBackground;
    }

    public void setEnableBackground(boolean enableBackground) {
        this.enableBackground = enableBackground;
        backgroundComponent.setEnabled(enableBackground);
    }

    public boolean isEnableFontName() {
        return enableFontName;
    }

    public void setEnableFontName(boolean enableFontName) {
        this.enableFontName = enableFontName;
        fontNames.setEnabled(enableFontName);
    }

    public boolean isEnableFontSize() {
        return enableFontSize;
    }

    public void setEnableFontSize(boolean enableFontSize) {
        this.enableFontSize = enableFontSize;
        fontSizes.setEnabled(enableFontSize);
    }

    public boolean isEnableFontStyle() {
        return enableFontStyle;
    }

    public void setEnableFontStyle(boolean enableFontStyle) {
        this.enableFontStyle = enableFontStyle;
    }

    public boolean isEnableForeground() {
        return enableForeground;
    }

    public void setEnableForeground(boolean enableForeground) {
        this.enableForeground = enableForeground;
        foregroundComponent.setEnabled(enableForeground);
    }
}
