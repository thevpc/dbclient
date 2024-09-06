/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.plugin.sql.jstsql.evaluator;

import java.io.IOException;

/**
 *
 * @author vpc
 */
public interface JSTSqlCompiler {
     public Class compile(JavaSource javaSource) throws IOException ;
   
}
