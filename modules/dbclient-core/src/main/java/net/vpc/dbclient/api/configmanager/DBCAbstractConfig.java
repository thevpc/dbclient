/**
 * ====================================================================
 *             DBCLient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
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
package net.vpc.dbclient.api.configmanager;

import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.vpc.dbclient.api.viewmanager.DBCComponentFormat;

import java.awt.*;

/**
 * @author vpc
 */
public abstract class DBCAbstractConfig extends DBCAbstractPluggable implements DBCConfig {
    public void setBooleanProperty(String name, Boolean value) {
        setStringProperty(name, value == null ? null : String.valueOf(value));
    }

    public Boolean getBooleanProperty(String name, Boolean defaultValue) {
        String s = getStringProperty(name, (defaultValue == null ? null : defaultValue.toString()));
        if (s == null) {
            return defaultValue;
        } else {
            try {
                return Boolean.parseBoolean(s);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }

    public void setDoubleProperty(String name, Double value) {
        setStringProperty(name, value == null ? null : String.valueOf(value));
    }

    public Double getDoubleProperty(String name, Double defaultValue) {
        String s = getStringProperty(name, null);
        if (s == null) {
            return defaultValue;
        } else {
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }

    public void setIntegerProperty(String name, Integer value) {
        setStringProperty(name, value == null ? null : String.valueOf(value));
    }

    public Integer getIntegerProperty(String name, Integer defaultValue) {
        String s = getStringProperty(name, null);
        if (s == null) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }

    public void setLongProperty(String name, Long value) {
        setStringProperty(name, value == null ? null : String.valueOf(value));
    }

    public Long getLongProperty(String name, Long defaultValue) {
        String s = getStringProperty(name, null);
        if (s == null) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }

    public Color getColorProperty(String name, Color defaultValue) {
        String rgb = getStringProperty(name, null);
        if (rgb == null || rgb.trim().length() == 0) {
            return defaultValue;
        }
        String[] strings = rgb.trim().split(",");
        return new Color(
                Integer.parseInt(strings[0].trim()),
                Integer.parseInt(strings[1].trim()),
                Integer.parseInt(strings[2].trim()));
    }

    public void setColorProperty(String name, Color color) {
        setStringProperty(name, color == null ? null : (color.getRed() + "," + color.getGreen() + "," + color.getBlue()));
    }

    public DBCComponentFormat getComponentFormatProperty(String name, DBCComponentFormat defaultValue) {
        Color f = getColorProperty(name + ".foreground", null);
        Color g = getColorProperty(name + ".background", null);
        Integer style = getIntegerProperty(name + ".style", null);
        Integer size = getIntegerProperty(name + ".size", null);
        String fotName = getStringProperty(name + ".fontname", null);
        if (f == null && g == null && size == null && style == null && fotName == null) {
            return defaultValue;
        }
        return new DBCComponentFormat(f, g, fotName, size, style);
    }

    public void setComponentFormatProperty(String name, DBCComponentFormat format) {
        setColorProperty(name + ".foreground", format != null ? format.getForeground() : null);
        setColorProperty(name + ".background", format != null ? format.getBackground() : null);
        setIntegerProperty(name + ".style", format != null ? format.getStyle() : null);
        setIntegerProperty(name + ".size", format != null ? format.getSize() : null);
        setStringProperty(name + ".fontname", format != null ? format.getFontName() : null);
    }
}
