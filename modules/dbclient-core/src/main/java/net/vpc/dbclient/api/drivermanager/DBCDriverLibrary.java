/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api.drivermanager;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.configmanager.DBCDriverInfo;
import net.vpc.prs.softreflect.SoftClassNotFoundException;
import net.vpc.prs.softreflect.classloader.SoftClassLoader;
import net.vpc.prs.softreflect.classloader.URLSoftClassLoader;
import net.vpc.util.Version;

/**
 *
 * @author vpc
 */
public class DBCDriverLibrary implements Comparable<DBCDriverLibrary> {

    private int id;
    private String name;
    private List<URL> resources;
    private Version version;
    private URLSoftClassLoader softClassLoader;
    private ClassLoader classLoader;
    private DBCApplication application;

    public DBCDriverLibrary(int id,DBCApplication application, String name, List<URL> resources, Version version) {
        this.id = id;
        this.application = application;
        this.name = name;
        this.resources = resources;
    }

    public DBCApplication getApplication() {
        return application;
    }


    public Version getVersion() {
        return version;
    }
    
    

    public String getName() {
        return name;
    }

    public List<URL> getResources() {
        return resources;
    }

    public boolean accept(String driverClass) {
        try{
            getSoftClassLoader().findClass(driverClass);
        }catch(SoftClassNotFoundException e){
            return false;
        }
        return true;
    }

    public boolean accept(DBCDriverInfo info) {
        try{
            getSoftClassLoader().findClass(info.getDriverClassName());
        }catch(SoftClassNotFoundException e){
            return false;
        }
        return true;
    }

    public SoftClassLoader getSoftClassLoader() {
        if (softClassLoader == null) {
            softClassLoader = new URLSoftClassLoader(name, resources.toArray(new URL[resources.size()]), null, application.getPluginManager().getSoftCoreClassLoader());
        }
        return softClassLoader;
    }
    
    public ClassLoader getClassLoader() {
        if (classLoader == null) {
            classLoader = new URLClassLoader(resources.toArray(new URL[resources.size()]), application.getPluginManager().getCoreClassLoader());
        }
        return classLoader;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(DBCDriverLibrary o) {
        if(version==null && o.version!=null){
            return 1;
        }
        if(version!=null && o.version==null){
            return -1;
        }
        if(version!=null && o.version!=null){
            int r=version.compareTo(o.version);
            if(r!=0){
                return r;
            }
        }
        return getId()-o.getId();
    }
 
    
}
