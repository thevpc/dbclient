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

package net.vpc.dbclient.plugin.tool.recordeditor;

import net.vpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
import net.vpc.prs.plugin.PluginInfo;
import net.vpc.dbclient.plugin.tool.recordeditor.propeditor.*;
import net.vpc.dbclient.api.sql.objects.DBTableColumn;

import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 nov. 2006 23:34:20
 */
@PluginInfo(
    messageSet = "net.vpc.dbclient.plugin.tool.recordeditor.messageset.Plugin",
    iconSet = "net.vpc.dbclient.plugin.tool.recordeditor.iconset.Plugin"
)
public class RecordEditorPlugin extends DBCAbstractPlugin {
    protected Vector<ColumnView> defaultColumnViews = new Vector<ColumnView>();

    public RecordEditorPlugin() {
    }

    @Override
    public void applicationOpening() {
        registerDefaultColumnViews();
    }

    public void registerDefaultColumnViews() {
        registerColumnView(new BigIntegerColumnView());

        registerColumnView(new BooleanColumnView());
        registerColumnView(new BooleanAsComboBoxColumnView());
        registerColumnView(new BooleanAsComboBoxTriStateColumnView());

        registerColumnView(new DateColumnView());
        registerColumnView(new DoubleColumnView());

        registerColumnView(new IntegerColumnView());
        registerColumnView(new IntegerAsCheckBoxColumnView());
        registerColumnView(new IntegerAsComboBoxColumnView());
        registerColumnView(new IntegerAsRadioListColumnView());
        registerColumnView(new IntegerAsCheckListColumnView());
        registerColumnView(new IntegerBitMaskColumnView());

        registerColumnView(new StringColumnView());
        registerColumnView(new SQLStringColumnView());
        registerColumnView(new StringAsCheckBoxColumnView());
        registerColumnView(new StringAsComboBoxColumnView());

        registerColumnView(new TimeColumnView());
        registerColumnView(new TimestampColumnView());
    }

    public ColumnView[] getValidColumnView(DBTableColumn node) {
        if (node == null) {
            return defaultColumnViews.toArray(new ColumnView[defaultColumnViews.size()]);
        }
        ArrayList<ColumnView> all = new ArrayList<ColumnView>();
        for (ColumnView columnView : defaultColumnViews) {
            if (columnView.accept(node)) {
                all.add(columnView);
            }
        }
        return all.toArray(new ColumnView[all.size()]);
    }

    public void registerColumnView(ColumnView cv) {
        defaultColumnViews.add(cv);
    }


    public ColumnView createDefaultColumnView(DBTableColumn node) {
        for (ColumnView columnView : defaultColumnViews) {
            if (columnView.accept(node)) {
                try {
                    return getApplication().getFactory().newInstance(null,columnView.getClass(), getDescriptor());
                } catch (Throwable e) {
                    //
                }
            }
        }
        return new DefaultColumnView();
    }


}
