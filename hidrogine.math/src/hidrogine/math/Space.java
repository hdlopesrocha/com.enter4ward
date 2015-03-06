package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Space.
 */
public class Space {

    /**
     * Iterate.
     *
     * @param frustum
     *            the frustum
     * @param handler
     *            the handler
     */
    public void handleVisibleObjects(BoundingFrustum frustum,
            VisibleObjectHandler handler) {
        if (root != null) {
            root.handleVisibleObjects(frustum, handler);
        }
    }

    /**
     * Iterate.
     *
     * @param frustum
     *            the frustum
     */
    @Deprecated
    public void handleVisibleNodes(BoundingFrustum frustum,
            VisibleNodeHandler handler) {
        if (root != null) {
            root.handleVisibleNodes(frustum, handler, 0);
        }
    }

    public void handleObjectCollisions(BoundingSphere sphere,
            ObjectCollisionHandler handler) {
        if (root != null) {
            root.handleObjectCollisions(sphere, handler);
        }
    }

    public void handleRayCollisions(Ray ray, RayCollisionHandler handler) {
        // System.out.println(handler+":"+ ray);
        if (root != null) {
            root.handleRayCollisions(this, ray, handler);
        }
    }

    /** The root. */
    private SpaceNode root;

    /**
     * Instantiates a new space.
     */
    public Space() {
        root = new SpaceNode();
    }

    /**
     * Update.
     * @param iObject3D 
     *
     * @param obj
     *            the obj
     * @return the space node
     */
    protected SpaceNode update(BoundingSphere sph, SpaceNode node, Object obj) {
        node.containerRemove(obj);
        node = node.update(sph);
        if(node==null){
            node = root = root.expand(sph);
        }
        node = insert(sph, node);
        node.containerAdd(obj);
        root = root.compress();
        return node;
    }

    /**
     * Insert.
     *
     * @param obj
     *            the obj
     * @param node
     *            the node
     * @return the space node
     */
    private SpaceNode insert(BoundingSphere sph, SpaceNode node) {

        
        // insertion
        while (true) {

            if (node.canSplit()) {
                float lenX = node.getLengthX();
                float lenY = node.getLengthY();
                float lenZ = node.getLengthZ();
                int i = node.containsIndex(sph, lenX, lenY, lenZ);
                if (i >= 0) {
                    node = node.getChild(i, lenX, lenY, lenZ);
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }

        /*
         * for (SpaceNode s = node; s != null; s = s.parent) {
         * if(s.clearChild()) System.out.println("clear!"); }
         */
        // System.out.println("=== COMPRESSION ===");
        // System.out.println(root.toString());

        return node;
    }

    /**
     * Insert.
     *
     * @param obj
     *            the obj
     * @return the space node
     */
    protected SpaceNode insert(BoundingSphere sph, Object obj) {
        root = root.expand(sph);     
        SpaceNode node = insert(sph, root);
        node.containerAdd(obj);
        root = root.compress();
        return node;
    }



}
