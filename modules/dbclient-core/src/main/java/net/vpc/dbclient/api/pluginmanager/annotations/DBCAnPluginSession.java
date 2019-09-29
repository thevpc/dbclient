/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.vpc.dbclient.api.pluginmanager.annotations;

import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author vpc
 */
@Target(ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DBCAnPluginSession {
    Class<? extends DBCPluginSession> value() default DBCPluginSession.class;
}
