/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.plugin.dbsupport.oracle.actions;

import net.thevpc.common.prs.Version;

import java.net.URL;

/**
 *
 * @author vpc
 */
public class DriverURLInfo {

    private URL[] urls;
    private String licence;
    private String name;
    private String description;
    private Version version;

    public DriverURLInfo(String name, String description, String licence, Version version, URL... urls) {
        this.urls = urls;
        this.licence = licence;
        this.name = name;
        this.description = description;
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public String getLicence() {
        return licence;
    }

    public String getName() {
        return name;
    }

    public URL[] getUrls() {
        return urls;
    }

    public Version getVersion() {
        return version;
    }
}
