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
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBClientActionTreeBuilder;
import net.vpc.dbclient.api.actionmanager.DBClientActionType;
import net.vpc.prs.plugin.Initializer;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sessionmanager.*;
import net.vpc.reflect.ClassFilter;
import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.FindMonitor;
import net.vpc.dbclient.api.sql.objects.*;
import net.vpc.dbclient.api.sql.parser.*;
import net.vpc.dbclient.api.sql.util.SQLUtils;
import net.vpc.dbclient.api.viewmanager.DBCNamedFormat;
import net.vpc.dbclient.plugin.system.sessionmanager.sqleditor.action.UndoRedoSupport;
import net.vpc.dbclient.plugin.system.sessionmanager.tree.ExtendedBasicTransferable;
import net.vpc.dbclient.plugin.system.sql.SystemSQLUtils;
import net.vpc.dbclient.plugin.system.sql.parser.DefaultSQLParser;
import net.vpc.swingext.ComponentResourcesUpdater;
import net.vpc.swingext.PRSManager;
import net.vpc.prs.iconset.IconSet;
import net.vpc.prs.messageset.MessageSet;
import net.vpc.swingext.JFindReplaceDialog;
import net.vpc.swingext.TextManipSupport;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.UIResource;
import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.im.InputContext;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import net.vpc.log.TLog;

public class DBCSQLEditorImpl extends JEditorPane implements DBCAutoCompleteListener, DBCSQLEditor {

    @Inject
    private DBCSession session;
    private File file;
    private DBCAutoCompleteComponent contextualPopupMenu;
    //    private JFileChooser chooser;
    private JFindReplaceDialog findReplaceDialog;
    private FindMonitor currentFindMonitor;
    private List<DBCSQLEditorAction> actionsList = new ArrayList<DBCSQLEditorAction>();
    private Map<String, String> wizz = new HashMap<String, String>();
    private int caretRow = 0;
    private int caretColumn = 0;
    private List<DBCCaretListener> caretListenerList = new ArrayList<DBCCaretListener>(3);
    private HashSet<Integer> lineBreakPoints;
    private boolean singleStatement;

    protected CaretListener caretListener = new MyCaretListener();
    /**
     * Listener for the edits on the current document.
     */

    /**
     * UndoManager that we add edits to.
     */
    protected UndoRedoSupport undoRedoSupport = new UndoRedoSupport();

    private TextManipSupport manipSupport;
    private TLog log;
    private LineNumberedBorder numberedBorder;
    private boolean selectEvenSingleOption;

    private static class CustomHighlighter extends DefaultHighlighter.DefaultHighlightPainter {
        private CustomHighlighter(Color c) {
            super(c);
        }
    }

    private CustomHighlighter errorHighlighter = new CustomHighlighter(Color.red.brighter());


    public DBCSQLEditorImpl() {
    }

//    public DBCSQLEditorPaneImpl(DBCSession session) throws SQLException {
//        this(session, session.getLog());
//    }
//    
//    public DBCSQLEditorPaneImpl(DBCSession session, TLog log) throws SQLException {
//        init(session, log);
//    }


    @Initializer
    private void init() throws SQLException {
        contextualPopupMenu = session == null ?
                //new JPopupMenuAutoCompleteComponent()
                new JListAutoCompleteComponent()
                : session.getFactory().newInstance(DBCAutoCompleteComponent.class);
//        contextualPopupMenu = new JListAutoCompleteComponent();
        contextualPopupMenu.init(this);
        contextualPopupMenu.addAutoCompleteListener(this);
//        this.log = session == null ? TLog.NULL : session.getLog();
        manipSupport = new TextManipSupport(this);
        manipSupport.setDateFormat(SQLUtils.DATE_FORMAT);
        manipSupport.setTimeFormat(SQLUtils.TIME_FORMAT);
        manipSupport.setDateTimeFormat(SQLUtils.TIMESTAMP_FORMAT);
        PRSManager.addSupport(this, "SQLEditorPane",
                new ComponentResourcesUpdater() {
                    public void update(JComponent comp, String id, MessageSet messageSet, IconSet iconSet) {
                        PRSManager.update(manipSupport.getActions(), messageSet, iconSet);
                        PRSManager.update(contextualPopupMenu.getComponent(), messageSet, iconSet);
                    }
                });
//        setEditorKit(new SQLEditorKit1(new SessionSQLParser(session)));
        if (session == null) {
            setEditorKit(new SQLEditorKit(new DefaultSQLParser(), session));
        } else {
            setEditorKit(new SQLEditorKit(new DefaultSQLParser(), session));
            getSession().getTaskManager().run(getName(),null,new Runnable() {
                @Override
                public void run() {
                    try{
                        setEditorKit(new SQLEditorKit(session.getConnection().createParser(), session));
                    }catch(Exception ex){
                        getLog().error(ex);
                    }
                }
                
            });
        }
//        setEditorKit(new SQLKit(this));
        setEditable(true);
        setDragEnabled(true);
        setTransferHandler(new ExtendedTransferHandler());
//        setSelectionColor(Color.yellow);
        refreshSyntaxColoring();
        //aaaaaaaa
        //iiiiiiii
//        getKeymap().removeKeyStrokeBinding(KeyStroke.getKeyStrokes(88, 2));
//        getKeymap().removeKeyStrokeBinding(KeyStroke.getKeyStrokes(67, 2));
//        getKeymap().removeKeyStrokeBinding(KeyStroke.getKeyStrokes(86, 2));

        this.setFont(new Font("Monospaced", 0, 12));

        getDocument().addUndoableEditListener(undoRedoSupport.getUndoableEditListener());
        InputMap inputMap = getInputMap(WHEN_FOCUSED);

        if (inputMap != null) {
            ActionMap actionMap = getActionMap();
            DBCSession dbcSession = getSession();
            DBClientActionTreeBuilder builder = new DBClientActionTreeBuilder();
            for (DBCSQLEditorAction ii : dbcSession.getFactory().createImplementations(DBCSQLEditorAction.class)) {
                DBCSQLEditorAction a = null;
                try {
                    a = ii;
                    if (a.acceptSession(session)) {
                        a.init(this);
                        actionsList.add(a);
                        builder.register(a, DBCActionLocation.POPUP);
                    }
                } catch (Exception e) {
                    //ognore
                }
            }

            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), undoRedoSupport.getUndoAction());
            if (actionMap != null) {
                actionMap.put(undoRedoSupport.getUndoAction(), undoRedoSupport.getUndoAction());
            }

            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK), undoRedoSupport.getRedoAction());
            if (actionMap != null) {
                actionMap.put(undoRedoSupport.getRedoAction(), undoRedoSupport.getRedoAction());
            }
            manipSupport.getPopup().addSeparator();

            builder.fillComponent(manipSupport.getPopup(), null);

            for (DBCSQLEditorAction action : actionsList) {
                if (action.getActionType() == DBClientActionType.ACTION) {
                    KeyStroke[] keyStrokes = action.getKeyStrokes();
                    for (KeyStroke keyStroke : keyStrokes) {
                        inputMap.put(keyStroke, action);
                    }
                    if (actionMap != null) {
                        actionMap.put(action, action);
                    }
                }
            }
        }

        manipSupport.getPopup().addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                PRSManager.update(actionsList, null, getSession().getView());
                PRSManager.update(manipSupport.getPopup(), getSession().getView());
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        findReplaceDialog = new JFindReplaceDialog(null, this);
        findReplaceDialog.installKeyBindings();
        //TODO add to some config table
        wizz.put("order", "order by");
        wizz.put("group", "group by");
        wizz.put("full", "full join");
        wizz.put("right", "right join");
        wizz.put("left", "left join");
        wizz.put("outer", "outer join");
        wizz.put("inner", "inner join");
        wizz.put("cross", "cross join");
        wizz.put("primary", "primary key");
        wizz.put("foreign", "foreign key");
        numberedBorder = new LineNumberedBorder(LineNumberedBorder.LEFT_SIDE, LineNumberedBorder.RIGHT_JUSTIFY, this);
        setBorder(numberedBorder);
//        addCaretListener(new DBCCaretListener() {
//            public void caretUpdate(DBCCaretEvent event) {
//                repaint();
//            }
//        });
        addCaretListener(caretListener);
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                int row = getCaretRow();
                if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e) && row >= 0 && e.getPoint().x < numberedBorder.getLineNumberMargin()) {
                    if (isLineBreakPoint(row)) {
                        removeLineBreakPoint(row);
                    } else {
                        addLineBreakPoint(row);
                    }
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void setLineImage(int index, String name, Image image) {
        numberedBorder.setLineImage(index, name, image);
        repaint();
    }

    public void revalidateEditorKit() {
        try {
            setEditorKit(new SQLEditorKit(session.getConnection().createParser(), session));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refreshSyntaxColoring();
    }

    public SQLParser getParser() {
        return getSQLEditorKit().getParser();
    }

    public void setParser(SQLParser parser) {
        setEditorKit(new SQLEditorKit(parser, session));
    }

    public SQLEditorKit getSQLEditorKit() {
        SQLEditorKit k = (SQLEditorKit) super.getEditorKit();
        k.getParser().setSingleStatement(isSingleStatement());
        return k;
    }

    public TLog getLog() {
        return log;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setText(String t) {
        String old = getText();
        if (t == null) {
            t = "";
        }
        super.setText(t);
        firePropertyChange("text", old, t);
        clearBreakPoints();
    }


    public void loadFile(File file) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            setText(sql.toString().trim() + "\n");
            setFile(file);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public void setText(Reader reader0) throws IOException {
        BufferedReader reader = null;
        try {
            reader = (reader0 instanceof BufferedReader) ? (BufferedReader) reader0 : new BufferedReader(reader0);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            setText(sb.toString().trim() + "\n");
            clearBreakPoints();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

//    protected EditorKit createDefaultEditorKit() {
//        if(connectionWrapper==null){
//            return super.createDefaultEditorKit();
//        }
//        return new SQLEditorKit(connectionWrapper);
//    }
//

    protected Document createDefaultModel() {
        return new PlainDocument();
    }


    public int getNumberOfLines() {
        Element element = getDocument().getDefaultRootElement();
        return element.getElementCount();
    }

    public boolean getScrollableTrackViewportHeight() {
        return false;
    }

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public void refreshSyntaxColoring() {
        revalidate();
    }

    public void actionSelectLine() {
//        queryTextArea.
        int i0 = this.getSelectionStart();
        int i1 = this.getSelectionEnd();
        if (i0 < 0) {
            i0 = this.getCaretPosition();
            i1 = this.getCaretPosition();
        }
        try {
            if (this.getText(i0, 1).equals("\n")) {
                i0--;
            }
            while (i0 >= 0 && !this.getText(i0, 1).equals("\n")) {
                i0--;
            }
            i0++;
            while (i1 < this.getText().length() && !this.getText(i1, 1).equals("\n")) {
                i1++;
            }
            if (i1 < i0) {
                i1 = i0;
            }
            this.select(i0, i1);
        } catch (BadLocationException e) {
            //ignore
        }
//        if(i0>=0 && i0>=0){
//            if(i0==0 || queryTextArea.getText(i0-1,1).equals('\'))
//        }
    }


    public String getSelectedSQL() {
        String txt = getSelectedText();
        if (txt == null || txt.length() == 0) {
            txt = getText();
        }
        return txt.trim();
    }

    public void highlightErrorStatement(SQLStatement q) {
        SQLStatement q2 = q.trim(true);
        if (q2 != null) {
            try {
                if (q2.getTokens().length > 0) {
                    int i = q2.getCharStartIndex();
                    int j = q2.getCharEndIndex();
                    if (i >= 0 && j < getText().length()) {
                        getHighlighter().addHighlight(i, j, errorHighlighter);
                    }
                }
            } catch (BadLocationException e) {
                //ignore
            }
        }
    }


    public String getSQL() {
        return getText();
    }

    public void setSQL(String text) {
        setText(text);
    }


    public void actionCancelAutoComplete() {
        if (currentFindMonitor != null) {
            currentFindMonitor.setStopped(true);
        }
    }

    public void actionAutoComplete() {
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            int initialCaretPosition = this.getCaretPosition();
            SQLDocument doc = (SQLDocument) getDocument();
            SQLDocument.ElementLocation elementLocation = doc.locate(initialCaretPosition - 1);
            if (elementLocation == null) {
                return;
            }
            SQLDocument.ElementLocation nextElementLocation = doc.locate(initialCaretPosition);

            SQLName name = SQLUtils.leftExpandName(initialCaretPosition - 1, elementLocation.sqlStatement.getTokens(), true);
            if (name == null || name.getTokens().length == 0
                    ) {
                return;
//                name=new SQLName(new SQLToken[0]);
            }
            SQLName aliasType = null;
            if (name.getAlias() != null) {
                SQLDeclaration tokenDeclaration = elementLocation.sqlStatement.getTokenDeclaration(name.getAlias());
                SQLToken[] aliasType0 = tokenDeclaration == null ? null : tokenDeclaration.getType();
                if (aliasType0 != null) {
                    aliasType = new SQLName(aliasType0);
                }
            }
            SQLName newName = name.getNewName(aliasType);
            ArrayList<DBObject> valid = new ArrayList<DBObject>();
            currentFindMonitor = new FindMonitor();
            ArrayList<DBObject> contextParents = new ArrayList<DBObject>();
            contextParents.add(null);
            SQLDeclaration[] tokenDeclarations = elementLocation.sqlStatement.getQueryTables();
            DBCSession currentSession = getSession();
            DBCConnection currentConnexion = currentSession.getConnection();
            ClassFilter classFilter = new ClassFilter() {
                public boolean accept(Class clz) {
                    return DBSchema.class.isAssignableFrom(clz) || DBCatalog.class.isAssignableFrom(clz) || DBTable.class.isAssignableFrom(clz);
                }
            };
            for (SQLDeclaration tokenDeclaration : tokenDeclarations) {
                DBObject[] dbObjects = currentConnexion.find(
                        null, null, null, SystemSQLUtils.maskWildChars(new SQLName(tokenDeclaration.getType()).toSQL()),
                        null,
                        classFilter,
                        currentFindMonitor);
                contextParents.addAll(Arrays.asList(dbObjects));
            }
            valid.addAll(Arrays.asList(getSession().getConnection().find(
                    null, null, null, SystemSQLUtils.maskWildChars(newName.toSQL()) + "%",
                    contextParents.toArray(new DBObject[contextParents.size()]),
                    null,
                    currentFindMonitor)));
            SQLToken tocompleteWord = elementLocation.token;
            String tocompleteString = tocompleteWord.getValue();
            boolean changeWordOnAutoComplete = true;
            int preferredCaretPosition = initialCaretPosition;
            if (changeWordOnAutoComplete) {
                preferredCaretPosition = elementLocation.token.getCharEndIndex();
            } else {
                if (initialCaretPosition < elementLocation.token.getCharEndIndex()) {
                    tocompleteString = tocompleteString.substring(0, initialCaretPosition - elementLocation.token.getCharStartIndex());
                }
            }
            DBCAutoCompleteInfo[] alll = new DBCAutoCompleteInfo[valid.size()];
            for (int i = 0; i < valid.size(); i++) {
                DBObject obj = valid.get(i);
                String sql = getInsertSQL(obj, nextElementLocation == null ? null : nextElementLocation.token);
                alll[i] = new DBCAutoCompleteInfoImpl(preferredCaretPosition,
                        sql,
                        obj.getSQL(),
                        tocompleteString, obj);
            }
            if (alll.length == 1 && !selectEvenSingleOption) {
                autoComplete(alll[0]);
            } else {
                contextualPopupMenu.setData(alll);
                contextualPopupMenu.showAutoComplete(this);
            }
        } catch (Throwable e) {
            session.getLogger(getClass().getName()).log(Level.SEVERE,"Autocomplete failed",e);
        } finally {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            currentFindMonitor = null;
        }
    }


    //TODO
    //must be dialect sensitive!!!!

    public String getInsertSQL(DBObject node, SQLToken nextToken) {
        String sql = node.getSQL();
        if (sql == null) {
            return null;
        }
        boolean nextIsWhite = nextToken != null && nextToken.getGroup() != SQLTokenGroup.WHITE;
        boolean noNext = nextToken == null;
        String s = wizz.get(sql.toLowerCase());
        if (s != null) {
            String suffix = (noNext || (!nextIsWhite && !sql.endsWith("."))) ? " " : "";
            return s + suffix;
        }
        if (node instanceof DBCatalog || node instanceof DBSchema) {
            String suffix = (noNext || (!nextIsWhite && !sql.endsWith("."))) ? "." : "";
            return sql + suffix;
        }
        if (node instanceof DBFunction) {
            String suffix = (noNext || (!nextIsWhite && !sql.endsWith("(") && !sql.endsWith(")"))) ? "()" : "";
            return sql + suffix;
        }

        String suffix = (noNext || (!nextIsWhite && !sql.endsWith("."))) ? " " : "";
        return sql + suffix;
    }


    public DBCSession getSession() {
        return session;
    }

    public TextManipSupport getManipSupport() {
        return manipSupport;
    }

    public SQLDocument getSQLDocument() {
        return (SQLDocument) getDocument();
    }

    public void updateUI() {
        super.updateUI();
        if (manipSupport != null) {
            manipSupport.updateUI();
        }
        if (contextualPopupMenu != null) {
            Component c = contextualPopupMenu.getComponent();
            if (c instanceof JComponent) {
                ((JComponent) c).updateUI();
            }
        }
        if (numberedBorder != null) {
            numberedBorder.updateUI();
        }
//        if (chooser != null) {
//            chooser.updateUI();
//        }
        if (findReplaceDialog != null) {
            findReplaceDialog.updateUI();
        }
    }


    public void autoComplete(DBCAutoCompleteInfo info) {
        try {
            String tocompleteWord = info.getWordToReplace();
            int stringPosition = info.getIndex();
            String w = info.getValue();
            if (tocompleteWord.equals(".")) {
                getDocument().insertString(stringPosition, w, null);
                setCaretPosition(stringPosition + w.length());
            } else {
                getDocument().remove(stringPosition - tocompleteWord.length(), tocompleteWord.length());
                getDocument().insertString(stringPosition - tocompleteWord.length(), w, null);
                setCaretPosition(stringPosition - tocompleteWord.length() + w.length());
            }
            repaint();
        } catch (BadLocationException e) {
            //ignore
        }
    }

    public JEditorPane getComponent() {
        return this;
    }

    public void setFormats(DBCNamedFormat[] namedFormats) {
        SQLEditorKit kit = this.getSQLEditorKit();
        for (DBCNamedFormat namedFormat : namedFormats) {
            if (namedFormat.getCode() == -1) {
                kit.setDefaultFormat(namedFormat.getComponentFormat());
            } else {
                kit.setFormat(namedFormat.getCode(), namedFormat.getComponentFormat());
            }
        }
        this.refreshSyntaxColoring();
    }

    public void reloadFormats() {
        getSQLEditorKit().reloadConfig();
        this.refreshSyntaxColoring();
    }

    public void setLog(TLog log) {
        if (log == null) {
            log = TLog.NULL;
        }
        this.log = log;
    }


    /**
     * will handle also Files
     */
    static class ExtendedTransferHandler extends TransferHandler implements
            UIResource {
        public void exportToClipboard(JComponent comp, Clipboard clipboard,
                                      int action) throws IllegalStateException {
            if (comp instanceof JTextComponent) {
                JTextComponent text = (JTextComponent) comp;
                int p0 = text.getSelectionStart();
                int p1 = text.getSelectionEnd();
                if (p0 != p1) {
                    try {
                        Document doc = text.getDocument();
                        String srcData = doc.getText(p0, p1 - p0);
                        StringSelection contents = new StringSelection(srcData);

                        // this may throw an IllegalStateException,
                        // but it will be caught and handled in the
                        // action that invoked this method
                        clipboard.setContents(contents, null);

                        if (action == TransferHandler.MOVE) {
                            doc.remove(p0, p1 - p0);
                        }
                    } catch (BadLocationException ble) {
                        //ignore
                    }
                }
            }
        }

        public boolean importDataString(DBCSQLEditor comp, Transferable t, DataFlavor flavor, String stringValue) {
            try {
                //treat urls wisely
                URL url = new URL(stringValue);
                if ("file".equals(url.getProtocol())) {
                    Reader reader = null;
                    try {
                        File fileObj = new File(url.getFile());
                        reader = new FileReader(fileObj);
                        comp.setText(reader);
                        comp.setFile(fileObj);
                    } catch (Exception e1) {
                        ((JTextComponent) comp).replaceSelection(stringValue);
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } else {
                    Reader reader = null;
                    try {
                        reader = new InputStreamReader(url.openStream());
                        comp.setText(reader);
                    } catch (Exception e1) {
                        ((JTextComponent) comp).replaceSelection(stringValue);
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            } catch (MalformedURLException mue) {
                ((JTextComponent) comp).replaceSelection(stringValue);
            }
            return true;
        }

        public boolean importDataObject(DBCSQLEditor comp, Transferable t, DataFlavor flavor, Object objectValue) {
            if (objectValue == null) {
                return true;
            } else if (objectValue instanceof String) {
                return importDataString(comp, t, flavor, (String) objectValue);
            } else if (objectValue instanceof File) {
                try {
                    return importDataString(comp, t, flavor, ((File) objectValue).toURI().toURL().toString());
                } catch (MalformedURLException e) {
                    return false;
                }
            } else if (objectValue instanceof java.util.List) {
                return importDataList(comp, t, flavor, ((java.util.List) objectValue));
            } else if (objectValue instanceof DBObject) {
                String value = ((DBObject) objectValue).getDropSQL();
                if (value == null) {
                    return true;
                }
                return importDataString(comp, t, flavor, value);
            } else {
                return importDataString(comp, t, flavor, objectValue.toString());
            }
        }

        public boolean importDataList(DBCSQLEditor comp, Transferable t, DataFlavor flavor, java.util.List objectValue) {
            if (objectValue == null) {
                return true;
            } else if (objectValue.size() > 0) {
                return importDataObject(comp, t, flavor, objectValue.get(0));
            }
            return false;
        }

        public boolean importData(JComponent comp, Transferable t) {
            if (comp instanceof DBCSQLEditor) {
                DataFlavor flavor = null;
                flavor = getFileListFlavor(t.getTransferDataFlavors());
                if (flavor != null) {
                    InputContext ic = comp.getInputContext();
                    if (ic != null) {
                        ic.endComposition();
                    }
                    try {
                        java.util.List data = (java.util.List) t.getTransferData(flavor);
                        boolean ret = importDataList((DBCSQLEditor) comp, t, flavor, data);
                        if (ret) {
                            return true;
                        }
                    } catch (UnsupportedFlavorException ufe) {
                    } catch (IOException ioe) {
                    }
                }
                flavor = getObjectFlavor(t.getTransferDataFlavors());
                if (flavor != null) {
                    InputContext ic = comp.getInputContext();
                    if (ic != null) {
                        ic.endComposition();
                    }
                    try {
                        Object data = t.getTransferData(flavor);
                        boolean ret = importDataObject((DBCSQLEditor) comp, t, flavor, data);
                        if (ret) {
                            return true;
                        }
                    } catch (UnsupportedFlavorException ufe) {
                    } catch (IOException ioe) {
                    }
                }
                flavor = getStringFlavor(t.getTransferDataFlavors());
                if (flavor != null) {
                    InputContext ic = comp.getInputContext();
                    if (ic != null) {
                        ic.endComposition();
                    }
                    try {
                        String data = (String) t.getTransferData(flavor);
                        boolean ret = importDataString((DBCSQLEditor) comp, t, flavor, data);
                        if (ret) {
                            return true;
                        }
                    } catch (UnsupportedFlavorException ufe) {
                    } catch (IOException ioe) {
                    }
                }
            }
            return false;
        }

        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            JTextComponent c = (JTextComponent) comp;
            if (!(c.isEditable() && c.isEnabled())) {
                return false;
            }
            return (getFlavor(transferFlavors) != null);
        }

        public int getSourceActions(JComponent c) {
            return NONE;
        }

        private DataFlavor getFlavor(DataFlavor[] flavors) {
            if (flavors != null) {
                DataFlavor f = null;
                f = getFileListFlavor(flavors);
                if (f != null) {
                    return f;
                }
                f = getObjectFlavor(flavors);
                if (f != null) {
                    return f;
                }
                f = getStringFlavor(flavors);
                if (f != null) {
                    return f;
                }
            }
            return null;
        }

        private DataFlavor getStringFlavor(DataFlavor[] flavors) {
            if (flavors != null) {
                for (int counter = 0; counter < flavors.length; counter++) {
//                    System.out.println("counter = " + flavors[counter].toString());
                    if (
                            flavors[counter].equals(DataFlavor.stringFlavor)
                            ) {
                        return flavors[counter];
                    }
                }
            }
            return null;
        }

        private DataFlavor getObjectFlavor(DataFlavor[] flavors) {
            if (flavors != null) {
                for (int counter = 0; counter < flavors.length; counter++) {
//                    System.out.println("counter = " + flavors[counter].toString());
                    if (
                            flavors[counter].equals(ExtendedBasicTransferable.javaObjetRefFlavor)
                            ) {
                        return flavors[counter];
                    }
                }
            }
            return null;
        }

        private DataFlavor getFileListFlavor(DataFlavor[] flavors) {
            if (flavors != null) {
                for (DataFlavor flavor : flavors) {
//                    System.out.println("counter = " + flavors[counter].toString());
                    if (
                            flavor.equals(DataFlavor.javaFileListFlavor)
                            ) {
                        return flavor;
                    }
                }
            }
            return null;
        }
    }

    private class MyDBCCaretEvent extends DBCCaretEvent {
        private int dot;
        private int mark;
        private int row;
        private int column;

        private MyDBCCaretEvent(Object source) {
            super(source);
        }

        public int getDot() {
            return dot;
        }

        public int getMark() {
            return mark;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

    private class MyCaretListener implements CaretListener {
        public void caretUpdate(CaretEvent e) {
//            Object source = e.getSource();
            int pos = e.getDot();

            String s = getText();
            int line = 1;
            int row = 1;

            char[] chars = s.toCharArray();
            int len = chars.length;
            int lastBackSlashN = -1;
            for (int i = 0; i < len; i++) {
                char cc = chars[i];
                if (cc == '\n') {
                    line++;
                    lastBackSlashN = i;
                }
                if (pos == i) {
                    break;
                }
            }
            caretColumn = lastBackSlashN == -1 ? (pos + 1) : (pos == lastBackSlashN) ? 1 : (pos - lastBackSlashN);
            caretRow = line;
            repaint();
            MyDBCCaretEvent evt = null;
            for (DBCCaretListener dbcCaretListener : caretListenerList) {
                if (evt == null) {
                    evt = new MyDBCCaretEvent(e.getSource());
                    evt.column = caretColumn;
                    evt.row = caretRow;
                    evt.dot = e.getDot();
                    evt.mark = e.getMark();
                }
                dbcCaretListener.caretUpdate(evt);
            }
        }
    }


    public int getCaretRow() {
        return caretRow;
    }

    public int getCaretColumn() {
        return caretColumn;
    }

    public void addCaretListener(DBCCaretListener listener) {
        caretListenerList.add(listener);
    }

    public void removeCaretListener(DBCCaretListener listener) {
        caretListenerList.remove(listener);
    }


    public Set<Integer> getLineBreakPoints() {
        if (lineBreakPoints == null) {
            return Collections.EMPTY_SET;
        }
        return Collections.unmodifiableSet(lineBreakPoints);
    }

    public void addLineBreakPoint(int i) {
        if (lineBreakPoints == null) {
            lineBreakPoints = new HashSet<Integer>();
        }
        if (lineBreakPoints.add(i)) {
            ImageIcon ico = (ImageIcon) getSession().getView().getIconSet().getIcon("AnchorBreakPoint");
            setLineImage(i, "breakpoint", ico.getImage());
            firePropertyChange("lineBreakPoint.Added", -1, i);
        }
    }

    public void removeLineBreakPoint(int i) {
        if (lineBreakPoints != null) {
            if (lineBreakPoints.remove(i)) {
                setLineImage(i, "breakpoint", null);
                firePropertyChange("lineBreakPoint.Removed", -1, i);
            }
        }
    }

    public boolean isLineBreakPoint(int i) {
        return lineBreakPoints != null && lineBreakPoints.contains(i);
    }

    public void clearBreakPoints() {
        if (lineBreakPoints != null) {
            for (Integer bp : new HashSet<Integer>(lineBreakPoints)) {
                removeLineBreakPoint(bp);
            }
        }
        lineBreakPoints = null;
        repaint();
    }
    
    public boolean isSingleStatement(){
        return singleStatement;
    }

    public void setSingleStatement(boolean singleStatement){
        this.singleStatement=singleStatement;
    }
}
