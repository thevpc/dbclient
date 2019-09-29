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

package net.vpc.dbclient.api.viewmanager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 févr. 2007 03:37:13
 */
public class DBCComponentFormat implements Cloneable {
    public static final DBCComponentFormat DEFAULT_FORMAT = new DBCComponentFormat(null, null, null, null, null);
    private Color foreground;
    private Color background;
    private Integer style;
    private Integer size;
    private String fontName;
    private Font fontInstance;
    private Color foregroundInstance;
    private Color backgroundInstance;
    private DBCComponentFormat defaultFormat;

    public DBCComponentFormat() {

    }

    public DBCComponentFormat(Color foreground, Color background, String font, Integer size, Integer style) {
        this.background = background;
        this.foreground = foreground;
        this.fontName = font;
        this.size = size;
        this.style = style;
    }

    public void compile(Color background, Color foreground, Font otherFont) {
        if (otherFont != null) {
            if (fontName == null) {
                fontInstance = otherFont.deriveFont(
                        style == null ? otherFont.getStyle() : style,
                        size == null ? otherFont.getSize() : size
                );
            } else {
                fontInstance = new Font(fontName,
                        style == null ? otherFont.getStyle() : style,
                        size == null ? otherFont.getSize() : size
                );
            }
        } else {
            fontInstance = null;
        }
        this.foregroundInstance = (this.foreground != null) ? this.foreground : foreground;
        this.backgroundInstance = (this.background != null) ? this.background : background;
    }

    public void applyTo(JComponent component) {
        if (foregroundInstance != null) {
            component.setForeground(foregroundInstance);
        }
        if (backgroundInstance != null) {
            component.setOpaque(true);
            component.setBackground(backgroundInstance);
        } else {
            component.setOpaque(false);
        }
        if (fontInstance != null) {
            component.setFont(fontInstance);
        }
    }

    public Color getBackground() {
        return background == null && defaultFormat != null ? defaultFormat.background : background;
    }

    public String getFontName() {
        return fontName == null && defaultFormat != null ? defaultFormat.fontName : fontName;
    }

    public Color getForeground() {
        return foreground == null && defaultFormat != null ? defaultFormat.foreground : foreground;
    }

    public Integer getSize() {
        return size == null && defaultFormat != null ? defaultFormat.size : size;
    }

    public Integer getStyle() {
        return style == null && defaultFormat != null ? defaultFormat.style : style;
    }


    public DBCComponentFormat clone() {
        try {
            return (DBCComponentFormat) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }


    public void setBackground(Color background) {
        this.background = background;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public String toString() {
        return "ComponentFormat@" + System.identityHashCode(this) + "(fg=" + foreground + ",bg=" + background + ",font=" + fontName + ",size=" + size + ",style=" + style + ")";
    }

    public DBCComponentFormat getDefaultFormat() {
        return defaultFormat;
    }

    public void setDefaultFormat(DBCComponentFormat defaultFormat) {
        this.defaultFormat = defaultFormat;
    }
}
