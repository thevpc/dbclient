package net.thevpc.dbclient.api.actionmanager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by IntelliJ IDEA.
* User: vpc
* Date: 29 août 2009
* Time: 16:40:45
* To change this template use File | Settings | File Templates.
*/
public class ActionPathNode implements Comparable<ActionPathNode> {

    private DBClientAction action;
    private String id;
    private String path;
    private java.util.List<ActionPathNode> children = new ArrayList<ActionPathNode>();
    private ActionPathNode parent;

    public ActionPathNode(String id, String path, DBClientAction action, ActionPathNode parent) {
        this.id = id;
        this.path = path;
        this.action = action;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "{'" + id + "','" + path + "'," + (action == null ? "null" : action.getId()) + "}";
    }

    public int compareTo(ActionPathNode o) {
        return this.action.compareTo(o.action);
    }

    public String dump() {
        StringBuilder sb = new StringBuilder(toString());
        if (children.size() > 0) {
            sb.append("{");
            boolean first = true;
            for (ActionPathNode actionTreeNode : children) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(actionTreeNode.dump());
            }
            sb.append("}");
        }
        return sb.toString();
    }

    public DBClientAction getAction() {
        return action;
    }

    public void addChild(ActionPathNode n) {
        children.add(n);
    }
    
    public java.util.List<ActionPathNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public String getId() {
        return id;
    }

    public ActionPathNode getParent() {
        return parent;
    }

    public String getPath() {
        return path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setParent(ActionPathNode parent) {
        this.parent = parent;
    }

    public void setAction(DBClientAction action) {
        this.action = action;
    }

}
