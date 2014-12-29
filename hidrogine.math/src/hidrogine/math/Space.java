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
    public void handleVisibleObjects(BoundingFrustum frustum, VisibleObjectHandler handler) {
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
    public void handleVisibleNodes(BoundingFrustum frustum, VisibleNodeHandler handler) {
        if (root != null) {
            root.handleVisibleNodes(frustum, handler, 0);
        }
    }

    public void handleObjectCollisions(IBoundingSphere sphere, ObjectCollisionHandler handler) {
        if (root != null) {
            root.handleObjectCollisions(sphere, handler);
        }
    }


    public void handleRayCollisions(Ray ray, float maxDistance, RayCollisionHandler handler) {
       // System.out.println(handler+":"+ ray);
        if (root != null) {
            root.handleRayCollisions(ray, maxDistance, handler);
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
     *
     * @param obj
     *            the obj
     * @return the space node
     */
    protected SpaceNode update(IBoundingSphere obj, SpaceNode node) {
        node.container.remove(obj);

        while (node != null && node.contains(obj) != ContainmentType.Contains) {
            node.count--;
            node.clearChild();
            node = node.parent;
        }

        if (node == null) {
            expand(obj);
            node = root;
        } else {
            node.count--;
        }
        return insert(obj, node);
    }

    /**
     * Expand.
     *
     * @param obj
     *            the obj
     */
    private void expand(IBoundingSphere obj) {
        // System.out.println("=== EXPANSION ===");
        // System.out.println(root.toString());
        while (root.contains(obj) != ContainmentType.Contains) {
            root.clearChild();
            root = root.expand(obj);

            // System.out.println(root.toString());

        }
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
    protected SpaceNode insert(IBoundingSphere obj, SpaceNode node) {
        // insertion
        while (true) {
            ++node.count;

            boolean childContains = false;
            boolean canSplit = node.canSplit();
            if (canSplit) {
                for (int i = 0; i < 3; ++i) {
                    SpaceNode child = node.getChild(i);
                    if (child.contains(obj) == ContainmentType.Contains) {
                        childContains = true;
                        node = child;
                        break;
                    }
                }
            }

            if (!canSplit || !childContains) {
                break;
            }
        }

        node.container.add(obj);
        for (SpaceNode s = node; s != null; s = s.parent) {
            s.clearChild();
        }

        // root compression
        compress();
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
    protected SpaceNode insert(IBoundingSphere obj) {
        // expand phase
        expand(obj);

        // insertion
        return insert(obj, root);
    }

    /**
     * Compress.
     */
    private void compress() {
        while (true) {
            if (root.container.size() == 0) {
                boolean emptyLeft = root.child[SpaceNode.LEFT] == null
                        || root.child[SpaceNode.LEFT].count == 0;
                boolean emptyCenter = root.child[SpaceNode.CENTER] == null
                        || root.child[SpaceNode.CENTER].count == 0;
                boolean emptyRight = root.child[SpaceNode.RIGHT] == null
                        || root.child[SpaceNode.RIGHT].count == 0;

                if (emptyLeft && emptyCenter && !emptyRight) {
                    root = root.child[SpaceNode.RIGHT];
                } else if (emptyLeft && !emptyCenter && emptyRight) {
                    root = root.child[SpaceNode.CENTER];
                } else if (!emptyLeft && emptyCenter && emptyRight) {
                    root = root.child[SpaceNode.LEFT];
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        root.parent = null;
    }

}
