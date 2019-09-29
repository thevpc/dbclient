package net.vpc.dbclient.api.actionmanager;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBClientActionTreeBuilder {

    private Map<String, ActionPathNode> map = new HashMap<String, ActionPathNode>();
    private ActionPathNode root;

    public DBClientActionTreeBuilder() {
        root = new ActionPathNode("/", "/", null, null);
        map.put(root.getPath(), root);
    }

    public ActionPathNode getRoot() {
        return root;
    }

    public void register(DBClientAction action, DBCActionLocation locationId) {
        Collection<String> paths = action.getLocationPaths(locationId);
        ActionPathNode root = map.get("/");
        if (root == null) {
            root = new ActionPathNode("/", "/", null, null);
            map.put(root.getPath(), root);
        }
        for (String p : paths) {
            ActionPathNode last = root;
            StringBuilder sb = new StringBuilder();
            String[] rr = null;
            if (p.equals("") || p.equals("/")) {
                rr = new String[]{""};
            } else if (p.startsWith("/")) {
                rr = p.substring(1).split("/");
            }
            for (String aRr : rr) {
                sb.append("/").append(aRr);
                ActionPathNode nn = map.get(sb.toString());
                if (nn == null) {
                    nn = new ActionPathNode(aRr, sb.toString(), null, last);
                    last.addChild(nn);
                    map.put(sb.toString(), nn);
                }
                last = nn;
            }
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '/') {
                sb.append("/");
            }
            sb.append(action.getShortId());
            ActionPathNode me = map.get(sb.toString());
            if (me == null) {
                me = new ActionPathNode(action.getShortId(), sb.toString(), null, last);
                last.addChild(me);
                map.put(me.getPath(), me);
            }
            me.setAction(action);

        }
    }

    public void checkConsistency() {
        Stack<ActionPathNode> stack = new Stack<ActionPathNode>();
        stack.push(getRoot());
        while (!stack.isEmpty()) {
            ActionPathNode i = stack.pop();
            for (ActionPathNode c : i.getChildren()) {
                if (c.getAction() == null) {
                    Logger.getLogger(DBClientActionTreeBuilder.class.getName()).log(Level.SEVERE, "Action Path {0} not found!", c.getPath());
                    if (c.getChildren() != null) {
                        for (ActionPathNode child : c.getChildren()) {
                            Logger.getLogger(DBClientActionTreeBuilder.class.getName()).log(Level.SEVERE, "Action Path Child not found {0} : {1}", new Object[]{child.getPath(), c.getAction()});
                        }
                    }
                    throw new IllegalArgumentException("Action Path " + c.getPath() + " not found!");
                }
            }
            for (ActionPathNode c : i.getChildren()) {
                stack.push(c);
            }
        }
    }

    public void fillComponent(Component comp, DBCActionFilter filter) {
        checkConsistency();
        ActionPathNode root = getRoot();
        Stack<ActionPathNode> stack = new Stack<ActionPathNode>();
        Stack<Component> componentsStack = new Stack<Component>();
        List<ActionPathNode> children = new ArrayList<ActionPathNode>(root.getChildren());
        Collections.sort(children);
        for (int i = children.size() - 1; i >= 0; i--) {
            ActionPathNode pathNode = children.get(i);
            if (acceptActionPathNode(pathNode, filter)) {
                stack.push(pathNode);
                componentsStack.push(comp);
            }
        }
        while (!stack.isEmpty()) {
            ActionPathNode actionPath = stack.pop();
            Component cc = componentsStack.pop();
            Component r = actionPath.getAction().addToComponent(cc);
            List<ActionPathNode> nodes = new ArrayList<ActionPathNode>(actionPath.getChildren());
            Collections.sort(nodes);
            for (int i = nodes.size() - 1; i >= 0; i--) {
                ActionPathNode pathNode = nodes.get(i);
                if (acceptActionPathNode(pathNode, filter)) {
                    stack.push(pathNode);
                    componentsStack.push(r);
                }
            }
        }

    }

    private boolean acceptActionPathNode(ActionPathNode pathNode, DBCActionFilter filter) {
        return (filter == null)
                || (pathNode.getAction() == null)
                || (pathNode.getAction().getActionType() != DBClientActionType.ACTION)
                || filter.accept(pathNode.getAction());
    }
}
