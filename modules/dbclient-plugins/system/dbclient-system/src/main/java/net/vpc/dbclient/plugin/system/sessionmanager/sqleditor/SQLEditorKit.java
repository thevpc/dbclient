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

package net.vpc.dbclient.plugin.system.sessionmanager.sqleditor;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sql.parser.SQLParser;
import net.vpc.dbclient.api.sql.parser.SQLToken;
import net.vpc.dbclient.api.sql.parser.SQLTokenGroup;
import net.vpc.dbclient.api.viewmanager.DBCComponentFormat;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.ViewFactory;
import java.awt.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SQLEditorKit extends DefaultEditorKit {


    private static final Map<Integer, DBCComponentFormat> tokenStyles0;
    private static DBCComponentFormat tokenStyleForUnknown0 = new DBCComponentFormat(Color.black, null, null, null, Font.PLAIN);

    static {
        tokenStyles0 = new HashMap<Integer, DBCComponentFormat>();
        tokenStyles0.put(SQLTokenGroup.COMMENTS, new DBCComponentFormat(Color.lightGray, null, null, null, Font.ITALIC));
        tokenStyles0.put(SQLTokenGroup.OPERATOR, new DBCComponentFormat(Color.pink.darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.SEPARATOR, new DBCComponentFormat(Color.red, null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.OPEN_PAR, new DBCComponentFormat(Color.red, null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.CLOSE_PAR, new DBCComponentFormat(Color.red, null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.KEYWORD, new DBCComponentFormat(Color.blue.darker(), null, null, null, Font.BOLD));
        tokenStyles0.put(SQLTokenGroup.DATATYPE, new DBCComponentFormat(Color.cyan.darker().darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.FUNCTION, new DBCComponentFormat(Color.cyan.darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.DATATYPE, new DBCComponentFormat(Color.yellow.darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.STR, new DBCComponentFormat(Color.green.darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.VAR, new DBCComponentFormat(Color.black.darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.NUM, new DBCComponentFormat(Color.red.darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.SCHEMA, new DBCComponentFormat(Color.ORANGE.darker().darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.TABLE, new DBCComponentFormat(Color.ORANGE.darker().darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.CATALOG, new DBCComponentFormat(Color.ORANGE.darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.PROCEDURE, new DBCComponentFormat(Color.blue.darker().darker(), null, null, null, Font.PLAIN));
        tokenStyles0.put(SQLTokenGroup.SCRIPT, new DBCComponentFormat(Color.orange, null, null, null, Font.BOLD));
    }

    protected Map<Integer, DBCComponentFormat> tokenStyles;
    protected DBCComponentFormat tokenStyleForUnknown;
    protected SQLParser parser;
    protected DBCSession session;
    protected SQLViewFactory viewFactory;

    public SQLEditorKit(SQLParser parser, DBCSession session) {
        this.session = session;
        tokenStyles = new HashMap<Integer, DBCComponentFormat>(tokenStyles0);

        tokenStyles.put(SQLTokenGroup.COMMENTS, new DBCComponentFormat(Color.lightGray, null, null, null, Font.ITALIC));
        tokenStyles.put(SQLTokenGroup.OPERATOR, new DBCComponentFormat(Color.pink.darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.SEPARATOR, new DBCComponentFormat(Color.red, null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.OPEN_PAR, new DBCComponentFormat(Color.red, null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.CLOSE_PAR, new DBCComponentFormat(Color.red, null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.KEYWORD, new DBCComponentFormat(Color.blue.darker(), null, null, null, Font.BOLD));
        tokenStyles.put(SQLTokenGroup.DATATYPE, new DBCComponentFormat(Color.cyan.darker().darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.FUNCTION, new DBCComponentFormat(Color.cyan.darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.DATATYPE, new DBCComponentFormat(Color.yellow.darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.STR, new DBCComponentFormat(Color.green.darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.VAR, new DBCComponentFormat(Color.black.darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.NUM, new DBCComponentFormat(Color.red.darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.SCHEMA, new DBCComponentFormat(Color.ORANGE.darker().darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.TABLE, new DBCComponentFormat(Color.ORANGE.darker().darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.CATALOG, new DBCComponentFormat(Color.ORANGE.darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.PROCEDURE, new DBCComponentFormat(Color.blue.darker().darker(), null, null, null, Font.PLAIN));
        tokenStyles.put(SQLTokenGroup.SCRIPT, new DBCComponentFormat(Color.orange, null, null, null, Font.BOLD));
        tokenStyleForUnknown = tokenStyleForUnknown0;
        reloadConfig();
        this.parser = parser;
        if (session != null) {
            try {
                this.parser = parser == null ? session.getConnection().createParser() : parser;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        viewFactory = new SQLViewFactory(this);
    }

    public void setDefaultFormat(DBCComponentFormat componentFormat) {
        if (componentFormat == null) {
            tokenStyleForUnknown = tokenStyleForUnknown0;
        } else {
            tokenStyleForUnknown = componentFormat.clone();
            tokenStyleForUnknown.setDefaultFormat(tokenStyleForUnknown0);
        }
    }

    public void setFormat(int tokenType, DBCComponentFormat componentFormat) {
        if (componentFormat == null) {
            tokenStyles.put(tokenType, tokenStyles0.get(tokenType).clone());
        } else {
            DBCComponentFormat tokenStyle = componentFormat.clone();
            tokenStyle.setDefaultFormat(tokenStyles0.get(tokenType));
            tokenStyles.put(tokenType, tokenStyle);
        }
    }

    public void reloadConfig() {
        int[] ids = {
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
        if (session != null) {
            for (int id : ids) {
                DBCComponentFormat componentFormat = session.getConfig().getComponentFormatProperty("SQLEditorKit." + SQLTokenGroup.typeToString(id), null);
                setFormat(id, componentFormat);
            }
            DBCComponentFormat componentFormat = session.getConfig().getComponentFormatProperty("SQLEditorKit.$DEFAULT", null);
            setDefaultFormat(componentFormat);
        }
    }

    public String getContentType() {
        return "text/sql";
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DBCComponentFormat getCharFormat(int type) {
        DBCComponentFormat tokenStyle = tokenStyles.get(type);
        return tokenStyle == null ? tokenStyleForUnknown : tokenStyle;
    }

    public Collection<SQLToken> scan(Document document, int i, int j) {
        return ((SQLDocument) document).getTokens(i, j, true);
    }

    public SQLParser getParser() {
        return parser;
    }

    public Document createDefaultDocument() {
        return new SQLDocument(getParser());
    }
}

