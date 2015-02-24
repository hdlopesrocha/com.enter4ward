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
     *
     * @param obj
     *            the obj
     * @return the space node
     */
    protected SpaceNode update(BoundingSphere sph, SpaceNode node) {
        node = node.update(sph);

        if (node == null) {
            root = root.expand(sph);
            node = root;
        } else {
            node.count--;
        }
        return insert(sph, node);
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
    protected SpaceNode insert(BoundingSphere sph, SpaceNode node) {
        // insertion
        while (true) {
            ++node.count;

            boolean childContains = false;
            boolean canSplit = node.child!=null || node.canSplit();
            if (canSplit) {
                float lenX = node.getLengthX();
                float lenY = node.getLengthY();
                float lenZ = node.getLengthZ();

                for (int i = 0; i < 3; ++i) {
                    if (node.childContains(i, sph, lenX, lenY, lenZ) == ContainmentType.Contains) {
                        childContains = true;
                        node = node.getChild(i, lenX, lenY, lenZ);
                        break;
                    }
                }
            }

            if (!canSplit || !childContains) {
                break;
            }
        }

        /*
         * for (SpaceNode s = node; s != null; s = s.parent) {
         * if(s.clearChild()) System.out.println("clear!"); }
         */
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
    protected SpaceNode insert(BoundingSphere sph) {
        // expand phase
        root = root.expand(sph);
        SpaceNode node = insert(sph, root);

        // insertion
        return node;
    }

    /**
     * Compress.
     */
    private void compress() {
        while (true) {
            if (root.containerSize() == 0 && root.child!=null) {
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
