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
        if(!node.onlyContains(sph)) {
            node.containerRemove(obj);
            node.clearChild();
            node = node.parent;
            if(node!=null)
                node = node.update(sph);
            
            
            if(node==null){
                node = root = root.expand(sph);
            }
            node = node.insert(sph);
            node.containerAdd(obj);
            root = root.compress();
        }
        return node;
    }

   

    /**
     * Insert.
     *
     * @param obj
     *            the obj
     * @return the space node
     */
    SpaceNode insert(BoundingSphere sph, Object obj) {
        root = root.expand(sph);     
        SpaceNode node = root.insert(sph);
        node.containerAdd(obj);
        root = root.compress();
        return node;
    }



}
