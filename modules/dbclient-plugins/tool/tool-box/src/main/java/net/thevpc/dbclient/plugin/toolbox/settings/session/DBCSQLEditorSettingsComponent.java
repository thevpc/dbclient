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

package net.thevpc.dbclient.plugin.toolbox.settings.session;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.api.sql.parser.SQLTokenGroup;
import net.thevpc.dbclient.api.sql.parser.TokenRichType;
import net.thevpc.dbclient.api.sql.parser.WordTokenResolver;
import net.thevpc.dbclient.api.viewmanager.DBCComponentFormat;
import net.thevpc.dbclient.api.viewmanager.DBCNamedFormat;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.system.viewmanager.ComponentFormatEditor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 févr. 2007 04:38:45
 */
public class DBCSQLEditorSettingsComponent extends DBCPluggablePanel implements DBCSessionSettingsComponent {
    public static final String SAMPLE_SQL =
            "/**\n" +
                    " * this is a bloc/multiline comment\n" +
                    " */\n" +
                    "select \n" +
                    "MAX(2 * NBR_COL), Convert(VARCHAR(255),PRICE_COL),COL_<%=1+2%>\n" +
                    "from MY_CATALOG.MY_SCHEMA.MY_TABLE \n" +
                    "Where \n" +
                    "COL_3 = 12 && 4 and COL_5='Some Text'\n" +
                    ";\n" +
                    "--this is a line comment\n" +
                    "call MY_PROCEDURE;";
    private static WordTokenResolver SAMPLE_TABLE_WORD_RESOLVER = new WordTokenResolver() {
        private TokenRichType RICHTYPE = TokenRichType.valueOf(SQLTokenGroup.TABLE);

        public TokenRichType resolve(String word, int supposedType, SQLParser parser) throws IOException {
            return "MY_TABLE".equals(word) ? RICHTYPE : null;
        }
    };

    private static WordTokenResolver SAMPLE_CATALOG_WORD_RESOLVER = new WordTokenResolver() {
        private TokenRichType RICHTYPE = TokenRichType.valueOf(SQLTokenGroup.CATALOG);

        public TokenRichType resolve(String word, int supposedType, SQLParser parser) throws IOException {
            return "MY_CATALOG".equals(word) ? RICHTYPE : null;
        }
    };

    private static WordTokenResolver SAMPLE_SCHEMA_WORD_RESOLVER = new WordTokenResolver() {
        private TokenRichType RICHTYPE = TokenRichType.valueOf(SQLTokenGroup.SCHEMA);

        public TokenRichType resolve(String word, int supposedType, SQLParser parser) throws IOException {
            return "MY_SCHEMA".equals(word) ? RICHTYPE : null;
        }
    };

    private ComponentFormatEditor editor;
    @Inject
    private DBCSession session;
    @Inject
    private DBCPluginSession pluginSession;
    private DBCNamedFormat[] namedFormats;
    private JList list;
    private DBCSQLEditor sqlEditor;

    public DBCSQLEditorSettingsComponent() {

    }


    public boolean acceptSession(DBCSession pluginSession) {
        return true;
    }


    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return session.getView().getIconSet().getIconR("Preferences");
    }

    public String getId() {
        return "SQLEditorConfig";
    }

    public String getTitle() {
        return "SQL Editor";
    }

    static final int[] ids = {
            -1,
            SQLTokenGroup.COMMENTS,
            SQLTokenGroup.OPERATOR,
            SQLTokenGroup.SEPARATOR,
            SQLTokenGroup.OPEN_PAR,
            SQLTokenGroup.CLOSE_PAR,
            SQLTokenGroup.KEYWORD,
            SQLTokenGroup.DATATYPE,
            SQLTokenGroup.FUNCTION,
            SQLTokenGroup.DATATYPE,
            SQLTokenGroup.STR,
            SQLTokenGroup.VAR,
            SQLTokenGroup.NUM,
            SQLTokenGroup.SCHEMA,
            SQLTokenGroup.TABLE,
            SQLTokenGroup.CATALOG,
            SQLTokenGroup.PROCEDURE,
            SQLTokenGroup.SCRIPT
    };

    @Initializer
    public void init() {
        editor = new ComponentFormatEditor(null);
        //editor.setEnableBackground(false);
        editor.setEnableFontSize(false);
        editor.setEnableFontName(false);
        ArrayList<DBCNamedFormat> all = new ArrayList<DBCNamedFormat>();
        for (int id : ids) {
            if (id == -1) {
                //first
                all.add(new DBCNamedFormat(id, "SQLEditorKit.$DEFAULT", "DEFAULT"));
            } else {
                all.add(new DBCNamedFormat(id, "SQLEditorKit." + SQLTokenGroup.typeToString(id), SQLTokenGroup.typeToString(id)));
            }
        }
        namedFormats = all.toArray(new DBCNamedFormat[all.size()]);
        list = new JList(
                namedFormats
        );
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                DBCNamedFormat f = (DBCNamedFormat) value;
                value = pluginSession.getMessageSet().get(f.getId());
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                return this;
            }
        });
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel cellsConfig = new JPanel();

        cellsConfig.setLayout(new DumbGridBagLayout(
                "[List+=][Editor]"
        ).setInsets(".*", new Insets(4, 4, 4, 4))
        );
        cellsConfig.add(new JScrollPane(list), "List");
        cellsConfig.add(editor, "Editor");
        editor.addPropertyChangeListener(ComponentFormatEditor.FORMAT_CHANGED, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                sqlEditor.setFormats(namedFormats);
                sqlEditor.setText(SAMPLE_SQL);
            }
        });
        cellsConfig.setBorder(BorderFactory.createTitledBorder("Editor Formats"));
        list.setSelectedIndex(0);
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = list.getSelectedIndex();
                    editor.setFormat(namedFormats[index].getComponentFormat());
                }
            }
        });
        cellsConfig.setBorder(BorderFactory.createTitledBorder("SQL Editor Formats"));

        JPanel other = new JPanel(new DumbGridBagLayout()
                .addLine("[<=$+sample]")
                .setInsets(".*", new Insets(2, 2, 2, 2))
        );
        try {
            sqlEditor = this.session.getFactory().newInstance(DBCSQLEditor.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sqlEditor.setText(SAMPLE_SQL);
        SQLParser parser = sqlEditor.getParser();
        parser.addWordTokenResolver(SAMPLE_TABLE_WORD_RESOLVER);
        parser.addWordTokenResolver(SAMPLE_CATALOG_WORD_RESOLVER);
        parser.addWordTokenResolver(SAMPLE_SCHEMA_WORD_RESOLVER);
        JScrollPane p1 = new JScrollPane(sqlEditor.getComponent());
        p1.setPreferredSize(new Dimension(400, 300));
        other.add(p1, "sample");

        other.setBorder(BorderFactory.createTitledBorder("Sample"));
        setLayout(new DumbGridBagLayout()
                .addLine("[<~+=cellsConfig]")
                .addLine("[<+$=other]")
                .setInsets("cellsConfig", new Insets(5, 5, 5, 5))
                .setInsets("other", new Insets(5, 5, 5, 5))
        );
        add(other, "other");
        add(cellsConfig, "cellsConfig");

    }

    public void loadConfig() {
        for (DBCNamedFormat namedFormat : namedFormats) {
            DBCComponentFormat componentFormat = session.getConfig().getComponentFormatProperty(namedFormat.getId(), null);
            namedFormat.setFormat(componentFormat);
        }
        editor.setFormat(namedFormats[list.getSelectedIndex()].getComponentFormat());
        sqlEditor.reloadFormats();
        sqlEditor.setText(SAMPLE_SQL);
    }

    public void saveConfig() {
        for (DBCNamedFormat namedFormat : namedFormats) {
            session.getConfig().setComponentFormatProperty(namedFormat.getId(), namedFormat.getComponentFormat());
        }
    }

    public int getPosition() {
        return 0;
    }

}
