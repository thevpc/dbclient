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
package net.thevpc.dbclient.api.configmanager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 8 sept. 2007 18:00:33
 */
public abstract class DBCAbstractInfo implements Serializable {

    protected Map<String,Object> map = new HashMap<String, Object>();

    public int size() {
        return map.size();
    }

    public Set keySet() {
        return map.keySet();
    }

    public Object getProperty(String p) {
        return map.get(p);
    }

    public DBCAbstractInfo setProperty(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public boolean containsProperty(String p) {
        return map.containsKey(p);
    }

    public DBCAbstractInfo unsetProperty(String p) {
        map.remove(p);
        return this;
    }

    public Set<Map.Entry<String,Object>> entrySet() {
        return map.entrySet();
    }

    public void setAllProperties(DBCAbstractInfo m) {
        setAllProperties(m.map);
    }

    public void setAllProperties(Map m) {
        for (Map.Entry o : (Set<Map.Entry>) m.entrySet()) {
            setProperty((String) o.getKey(), o.getValue());
        }
    }

    public String encode() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Object entryObj : map.entrySet()) {
            Map.Entry e = (Map.Entry) entryObj;
            String name = (String) e.getKey();
            Object v = e.getValue();
            String type = null;
            if (v == null) {
                type = "null";
            } else if (v instanceof Double) {
                type = "double";
            } else if (v instanceof Integer) {
                type = "int";
            } else if (v instanceof Long) {
                type = "long";
            } else if (v instanceof Boolean) {
                type = "boolean";
            } else if (v instanceof String) {
                type = "string";
            } else if (v instanceof DBCAbstractInfo) {
                type = v.getClass().getName();
            } else {
                throw new IllegalArgumentException("Unsupported Type " + v.getClass());
            }
            if (sb.length() > 1) {
                sb.append(";");
            }
            sb.append(toStringCode(name)).append(":").append(type).append(":").append(toStringCode(v));
        }
        sb.append("}");
        return sb.toString();
    }

    public void decode(String code) {
        if (code == null) {
            return;
        }
        if (!code.startsWith("{") || code.startsWith("}")) {
            throw new IllegalArgumentException("Bad format, code must start with '{' and end with '}'");
        }
        StringBuilder n = new StringBuilder();
        StringBuilder t = new StringBuilder();
        StringBuilder v = new StringBuilder();
        StringBuilder cur = null;
        int token = 0;
        for (int i = 1; i < code.length(); i++) {
            if (cur == null) {
                cur = token == 0 ? n : token == 1 ? t : v;
            }
            char c = code.charAt(i);
            switch (c) {
                case '\\': {
                    i++;
                    c = code.charAt(i);
                    switch (c) {
                        case 'n': {
                            cur.append('\n');
                            break;
                        }
                        case 't': {
                            cur.append('\n');
                            break;
                        }
                        case 'r': {
                            cur.append('\n');
                            break;
                        }
                        case 'f': {
                            cur.append('\n');
                            break;
                        }
                        default: {
                            cur.append(c);
                        }
                    }
                    break;
                }
                case '{': {
                    throw new IllegalArgumentException("unexpected char '{'");
                }
                case '}':
                case ';':
                case ':': {
                    switch (token) {
                        case 0:
                        case 1: {
                            if (c != ':') {
                                throw new IllegalArgumentException("Expected ':'");
                            }
                            break;
                        }
                        case 2: {
                            if (c == ':') {
                                throw new IllegalArgumentException("Expected ';' or '}'");
                            }
                            break;
                        }
                    }
                    if (c == '}' && i != code.length() - 1) {
                        throw new IllegalArgumentException("Expected end of pattern");
                    }
                    cur = null;
                    token++;
                    if (token > 2) {
                        String ts = t.toString();
                        if (ts.equals("null")) {
                            setProperty(n.toString(), null);
                        } else if (ts.equals("string")) {
                            setProperty(n.toString(), v.toString());
                        } else if (ts.equals("int")) {
                            setProperty(n.toString(), Integer.parseInt(v.toString()));
                        } else if (ts.equals("double")) {
                            setProperty(n.toString(), Double.parseDouble(v.toString()));
                        } else if (ts.equals("long")) {
                            setProperty(n.toString(), Long.parseLong(v.toString()));
                        } else if (ts.equals("boolean")) {
                            setProperty(n.toString(), "true".equals(v.toString()));
                        } else {
                            try {
                                Object oo = Class.forName(ts).newInstance();
                                if (oo instanceof DBCAbstractInfo) {
                                    DBCAbstractInfo ii = (DBCAbstractInfo) oo;
                                    ii.decode(code);
                                }
                                setProperty(n.toString(), oo);
                            } catch (Exception e) {
                                throw new IllegalArgumentException(e);
                            }
                        }
                        token = 0;
                        t.delete(0, t.length());
                        v.delete(0, v.length());
                        n.delete(0, n.length());
                    }
                    break;
                }
                default: {
                    cur.append(c);
                }
            }
        }
    }

    private String toStringCode(Object o) {
        if (o == null) {
            return "null";
        } else if (o instanceof String) {
            String s = (String) o;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case ';':
                    case ',':
                    case ':':
                    case '{':
                    case '}': {
                        sb.append('\\');
                        sb.append(c);
                        break;
                    }
                    case '\n': {
                        sb.append("\\n");
                        break;
                    }
                    case '\t': {
                        sb.append("\\f");
                        break;
                    }
                    case '\f': {
                        sb.append("\\f");
                        break;
                    }
                    case '\r': {
                        sb.append("\\r");
                        break;
                    }
                    default: {
                        sb.append(c);
                        break;
                    }
                }
            }
            return sb.toString();
        } else if (o instanceof Double) {
            return toStringCode(o.toString());
        } else if (o instanceof Integer) {
            return toStringCode(o.toString());
        } else if (o instanceof Long) {
            return toStringCode(o.toString());
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? "true" : "false";
        } else if (o instanceof DBCAbstractInfo) {
            return ((DBCAbstractInfo) o).encode();
        } else {
            throw new IllegalArgumentException("Unsupported Type for " + o.getClass());
        }
    }
}
