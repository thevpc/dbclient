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
package net.thevpc.dbclient.api.configmanager;

import net.thevpc.dbclient.api.viewmanager.DBCComponentFormat;

import java.awt.*;

/**
 * @author vpc
 */
public interface DBCConfig {
    public Boolean getBooleanProperty(String name, Boolean defaultValue);

    public void setBooleanProperty(String name, Boolean value);

    public Color getColorProperty(String name, Color defaultValue);

    public void setColorProperty(String name, Color value);

    public String getStringProperty(String name, String defaultValue);

    public void setStringProperty(String name, String value);

    public Integer getIntegerProperty(String name, Integer defaultValue);

    public void setIntegerProperty(String name, Integer value);

    public Double getDoubleProperty(String name, Double defaultValue);

    public void setDoubleProperty(String name, Double value);

    public Long getLongProperty(String name, Long defaultValue);

    public void setLongProperty(String name, Long value);

    public DBCComponentFormat getComponentFormatProperty(String name, DBCComponentFormat defaultValue);

    public void setComponentFormatProperty(String name, DBCComponentFormat value);

    public void clearProperties(String pathPattern);

    public String getPathValue(String path, String code);

    public void clearPathsValues(String path, String code);

    public void setPathValue(String path, String code, String value);
}
