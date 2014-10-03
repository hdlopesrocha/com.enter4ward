package hidrogine.webserver;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * The Class Node.
 */
public class Node {

    /** The clazz. */
    private Method  method= null;
    private Class<?>  clazz= null;

    
    /** The child. */
    private Map<String, Node> child = new TreeMap<String, Node>();

    /** The parent. */
    private Node parent;

    /** The label. */
    private String name, label;

    /**
     * Instantiates a new node.
     *
     * @param p
     *            the p
     * @param n
     *            the n
     * @param l
     *            the l
     * @param c
     *            the c
     */
    public Node(final Node p, final String n, final String l, final String m) {
        parent = p;
        label = l;
        name = n;
        if (parent != null) {
            parent.child.put(n, this);

        }
        String className = m.substring(0, m.lastIndexOf('.'));
        String methodName = m.substring(m.lastIndexOf('.') + 1,
                m.length());
        System.out.println(className);
        System.out.println(methodName);
        try {
            clazz = Class.forName(className);

            method = clazz.getMethod(methodName);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
        
    }

    /**
     * Gets the page.
     *
     * @return the page
     */
    public final Method getMethod() {
        
        return method;
    }

    public final Class<?> getClazz(){
        return clazz;
    }
    
    /**
     * Gets the url.
     *
     * @return the url
     */
    public final String getUrl() {
        String url = "";
        for (Node n = this; n != null; n = n.parent) {
            if (n.parent != null) {
                url = "/" + n.name + url;
            }
        }
        return url;
    }

    /**
     * Gets the label.
     *
     * @return the label
     */
    public final String getLabel() {
        return label;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets the child.
     *
     * @param s
     *            the s
     * @return the child
     */
    public final Node getChild(final String s) {
        return child.get(s);
    }

    /**
     * Gets the child.
     *
     * @return the child
     */
    public final Collection<Node> getChild() {
        return child.values();
    }
}
