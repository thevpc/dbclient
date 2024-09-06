/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.plugin.sql.jstsql.evaluator;

/**
 *
 * @author vpc
 */
public interface JavaSource {
    public String getClassName();
    public String getPackageName();
    public String getSource();
}
