/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.plugin.sql.jstsql.evaluator;

/**
 *
 * @author vpc
 */
public class StringJavaSource implements JavaSource{
    public String className;
    public String packageName;
    public String source;

    public StringJavaSource(String className, String packageName, String source) {
        this.className = className;
        this.packageName = packageName;
        this.source = source;
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getSource() {
        return source;
    }
    
}
