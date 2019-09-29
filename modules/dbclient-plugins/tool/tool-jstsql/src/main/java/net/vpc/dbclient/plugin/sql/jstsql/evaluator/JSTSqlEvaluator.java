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
package net.vpc.dbclient.plugin.sql.jstsql.evaluator;

import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.dbclient.api.sql.parser.SQLParser;
import net.vpc.dbclient.api.sql.parser.SQLToken;
import net.vpc.dbclient.api.sql.parser.SQLTokenGroup;
import net.vpc.dbclient.api.sql.util.ReaderProvider;
import net.vpc.dbclient.plugin.sql.jstsql.JSTSqlPlugin;
import net.vpc.util.ClassPath;
import net.vpc.util.IOUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Java Scripting Technology for SQL
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 21 juil. 2006 00:55:00
 */
public class JSTSqlEvaluator {

    private File defaultRootFolder;
//    private File sourceFile;
//    private int compilationStatus = Integer.MAX_VALUE;
    private String classPath = System.getProperty("java.class.path");
    private DBCPluginSession pluginSession;
    private JSTSqlCompiler compiler;

    public JSTSqlEvaluator(DBCPluginSession pluginSession) {
        this.pluginSession = pluginSession;
        JSTSqlPlugin plugin = (JSTSqlPlugin) pluginSession.getPlugin();
        ClassPath cp = pluginSession.getSession().getClassPath();
        setClassPath(cp.toString());
        this.defaultRootFolder = new File(plugin.getDescriptor().getWorkingDirectory(), "eval");
        compiler = new JSTSqlMemoryCompiler(cp,pluginSession.getSession().getClassLoader());
    }

    public void execute(ReaderProvider source, JSTSqlEvalContext context, PrintStream out) throws IOException {
        Class cls = compile(source, context);
        if (cls != null) {
            try {
                JSTSqlEvalTemplate instance = (JSTSqlEvalTemplate) cls.newInstance();
                //revalidate instances with the new classLoader
                instance.evaluate(out, context);
            } catch (Throwable e) {
                throw new JSTSqlException(e);
            }
        } else {
            throw new JSTSqlException("Syntax Error");
        }
    }

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    public Class compile(ReaderProvider reader, JSTSqlEvalContext context) throws IOException {
        JavaSource src = generateJava(reader, context);
        return compiler.compile(src);
//        boolean suntoolsLoaded = false;
//        File binFolder = null;
//        try {
////            File dbFolder = new File(defaultRootFolder,"db");
////            ClassLoader c = (ClassLoader) Class.forName("sun.rmi.rmic.iiop.DirectoryLoader").getConstructor(File.class).newInstance(binFolder);
//            JSTSqlTemplateCache tcache = null;
//            if (context.getContextId() != null) {
//                tcache = cache.loadCache(context);
//            }
//            if (tcache != null) {
//                return tcache.getImplClass(context);
//            }
//            Class<?> javacMainClass = Class.forName("com.sun.tools.javac.Main", true, pluginSession.getPlugin().getDescriptor().getClassLoader());
//            suntoolsLoaded = true;
//            String nextClassName = cache.nextClassName();
//            String path = nextClassName.indexOf('.') > 0 ? nextClassName.substring(0, nextClassName.indexOf('.')).replace('.', '/') : ".";
//            String pkg = nextClassName.indexOf('.') > 0 ? nextClassName.substring(0, nextClassName.indexOf('.')) : "";
//            String className = nextClassName.indexOf('.') > 0 ? nextClassName.substring(nextClassName.indexOf('.') + 1) : nextClassName;
//
//            return compiler.compile(pkg, className, reader);
//        } catch (Throwable e) {
//            System.out.println("e = " + e);
//            System.out.println("classpath = " + classPath);
//            System.out.println("command = ");
//            try {
//                System.out.println("javac -classpath " + classPath + " -d " + binFolder.getCanonicalPath() + " " + (sourceFile == null ? "<NO_SOURCE_FILE_FOUND>" : sourceFile.getCanonicalPath()));
//            } catch (Throwable e1) {
//                e1.printStackTrace();
//            }
//            if (!suntoolsLoaded) {
//                throw new JSTSqlException("Java Embedded Technology is disabled. Make sure sun tool 1.5 is located in $DBCLIENT_ROOT$/lib");
//            } else {
//                throw new JSTSqlException(e);
//            }
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    System.err.println(e);
//                    //
//                }
//            }
//        }
    }

    public StringJavaSource generateJava(ReaderProvider source, JSTSqlEvalContext context) throws IOException {
        String pkg="temp";
        String crc=IOUtils.computeCRC(source.getReader());
        String className="Temp_"+crc;
        return new StringJavaSource(className, pkg, generateJava(source.getReader(), context, className, pkg));
    }
    
    public String generateJava(Reader source, JSTSqlEvalContext context, String clasName, String pkg) throws IOException {
        StringWriter fos = new StringWriter();
//        PrintStream fos = new PrintStream(new FileOutputStream(file));
//        PrintStream fos = new PrintStream(w);
        if (pkg != null && pkg.length() > 0) {
            fos.write("package " + pkg + ";\n");
        }
        SQLParser parser;

        parser = context.getConnection().createParser();

        parser.setDocument(source);
        SQLToken token;
        StringBuffer curentLine = null;
        boolean headerWritten = false;
        while ((token = parser.readToken()) != null) {
            switch (token.getGroup()) {
                case SQLTokenGroup.SCRIPT: {
                    if (curentLine != null) {
                        if (!headerWritten) {
                            writeHeader(fos, clasName, context);
                            headerWritten = true;
                        }
                        writeJavaLiteral(curentLine.toString(), fos);
                        curentLine = null;
                    }
                    String value = token.getValue();
                    if (value.startsWith("<%@")) {
                        if (headerWritten) {
                            throw new JSTSqlException("Header not expected here");
                        }
                        //trait @ page import="...,..."
                    } else {
                        if (!headerWritten) {
                            writeHeader(fos, clasName, context);
                            headerWritten = true;
                        }
                        if (value.startsWith("<%=")) {
                            fos.write("    out.print(JSTSqlEvaluator.toString(" + value.substring(3, value.length() - 2) + "));\n");
                        } else {
                            fos.write(value.substring(2, value.length() - 2));
                            fos.write("\n");
                        }
                    }
//                    lastNonWhiteToken = token.getType();
                    break;
                }
                case SQLTokenGroup.WHITE: {
                    if (curentLine == null) {
                        curentLine = new StringBuffer();
                    }
                    curentLine.append(token.getValue());
                    break;
                }
                default: {
                    if (curentLine == null) {
                        curentLine = new StringBuffer();
                    }
                    curentLine.append(token.getValue());
//                    lastNonWhiteToken = token.getType();
                }
            }

        }
        if (curentLine != null) {
            if (!headerWritten) {
                writeHeader(fos, clasName, context);
                headerWritten = true;
            }
            writeJavaLiteral(curentLine.toString(), fos);
            curentLine = null;
        }
        if (!headerWritten) {
            writeHeader(fos, clasName, context);
            headerWritten = true;
        }
        fos.write("  }\n");
        fos.write("}\n");
        fos.close();
        source.close();
        //System.out.println("file = " + file);
        return fos.toString();
    }

    private void writeHeader(Writer fos, String className, JSTSqlEvalContext context) throws IOException {
        fos.write("import net.vpc.dbclient.api.sessionmanager.*;\n");
        fos.write("import net.vpc.dbclient.api.sql.*;\n");
        fos.write("import net.vpc.dbclient.api.sql.objects.*;\n");
        fos.write("import net.vpc.dbclient.plugin.sql.jstsql.*;\n");
        fos.write("import net.vpc.dbclient.plugin.sql.jstsql.evaluator.*;\n");
        fos.write("import java.util.*;\n");
        fos.write("import java.io.*;\n");
        fos.write("public class " + className + " extends JSTSqlEvalTemplate{\n");
        fos.write("  public void evaluate(PrintStream out,JSTSqlEvalContext context) throws Throwable{\n");
        fos.write("    this.out=out;\n");
        for (JSTSqlEvalContext.Var entry : context.getVars()) {
            fos.write("   " + entry.getClazz().getName() + " " + entry.getName() + "=(" + entry.getClazz().getName() + ")" + (entry.getExpression() != null ? entry.getExpression() : ("context.getValue(\"" + entry.getName() + "\")")) + ";\n");
        }
//        fos.println("    DBCSession session=session;");
//        fos.println("  " + nodeClassName + " node;");
//        fos.println("    DBClient dbclient=session==null?null:session.getApplication();");
    }

    private void writeJavaLiteral(String s, Writer ps) throws IOException {
        StringBuffer sb = new StringBuffer();
        int last = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\t': {
                    sb.append("\\t");
                    break;
                }
                case '\f': {
                    sb.append("\\f");
                    break;
                }
                case '\b': {
                    sb.append("\\b");
                    break;
                }
                case '\n': {
                    if (last != '\r') {
                        ps.write("  out.println(\"" + sb + "\");\n");
                        sb.delete(0, sb.length());
                    }
                    break;
                }
                case '\r': {
                    ps.write("  out.println(\"" + sb + "\");\n");
                    sb.delete(0, sb.length());
                    break;
                }
                case '\\': {
                    sb.append("\\\\");
                    break;
                }
                case '"': {
                    sb.append("\\\"");
                    break;
                }
                default: {
                    sb.append(c);
                }
            }
            last = c;
        }
        ps.write("  out.print(\"" + sb + "\");\n");
        sb.delete(0, sb.length());
    }

    public String compileToString(ReaderProvider source, JSTSqlEvalContext context) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        execute(source, context, ps);
        source.close();
        ps.close();
        return baos.toString();
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public ReaderProvider compileToReaderProvider(ReaderProvider source, JSTSqlEvalContext context) throws IOException {
        if (this.defaultRootFolder != null && !this.defaultRootFolder.exists()) {
            this.defaultRootFolder.mkdirs();
        }
        final File temp = File.createTempFile("JSTSqlEvaluator", ".temp", defaultRootFolder);
        PrintStream ps = new PrintStream(temp);
        execute(source, context, ps);
        source.close();
        ps.close();
        return new ReaderProvider() {

            public Reader getReader() throws IOException {
                return new BufferedReader(new FileReader(temp));
            }

            public void close() throws IOException {
                temp.delete();
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
                close();
            }
        };
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public void close() {
//        if (sourceFile != null) {
//            sourceFile.delete();
//            sourceFile = null;
//        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

//    public int getCompilationStatus() {
//        return compilationStatus;
//    }

    public static String toString(Object object) {
        if (object == null) {
            return "";
        } else if (object instanceof Object[] || object instanceof Collection) {
            if (object instanceof Object[]) {
                object = Arrays.asList((Object[]) object);
            }
            StringBuffer buf = new StringBuffer();
            Iterator i = ((Collection) object).iterator();
            boolean hasNext = i.hasNext();
            while (hasNext) {
                Object o = i.next();
                buf.append(String.valueOf(o));
                hasNext = i.hasNext();
                if (hasNext) {
                    buf.append(", ");
                }
            }

            return buf.toString();
        }
        return object.toString();
    }

    public String getClassPath() {
        return classPath;
    }

    public final void setClassPath(String classPath) {
        this.classPath = classPath;
    }
}
