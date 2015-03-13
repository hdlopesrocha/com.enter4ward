package hidrogine.math;

import java.util.ArrayList;
import java.util.TreeMap;

// TODO: Auto-generated Javadoc
/**
 * The Class Space.
 */
public class Space {

    private static final Vector3 TEMP_LENGTH = new Vector3();

    private static TreeMap<Float, TreeMap<Float, TreeMap<Float, Vector3>>> lenghts = new TreeMap<Float, TreeMap<Float, TreeMap<Float, Vector3>>>();
    public static int LENS = 0;

    private static Vector3 recycle(final Vector3 v) {
        TreeMap<Float, TreeMap<Float, Vector3>> treeX = lenghts.get(v.getX());
        if (treeX == null) {
            treeX = new TreeMap<Float, TreeMap<Float, Vector3>>();
            lenghts.put(v.getX(), treeX);
        }

        TreeMap<Float, Vector3> treeY = treeX.get(v.getY());
        if (treeY == null) {
            treeY = new TreeMap<Float, Vector3>();
            treeX.put(v.getY(), treeY);
        }

        Vector3 r = treeY.get(v.getZ());
        if (r == null) {
            r = new Vector3(v);
            treeY.put(v.getZ(), r);
            ++LENS;
        }

        return r;

    }

    private float minSize;

    /**
     * The Class Node.
     */
    class Node extends BoundingBox {

        /** The Constant LEFT. */
        static final int LEFT = 0;

        /** The Constant RIGHT. */
        static final int RIGHT = 1;

        /** The Constant CENTER. */
        static final int CENTER = 2;

        /** The container. */
        private ArrayList<Object> container;

        /** The parent. */
        private Node parent, left, right, center;

        /**
         * Instantiates a new space node.
         */
        public Node() {
            super(new Vector3(0), new Vector3(minSize * 3));
            this.parent = null;
        }

        /**
         * Instantiates a new space node.
         *
         * @param parent
         *            the parent
         * @param min
         *            the min
         * @param len
         *            the len
         */
        private Node(Node parent, Vector3 min, Vector3 len) {
            super(min, len);
            this.parent = parent;
        }

        /**
         * Instantiates a new space node.
         *
         * @param node
         *            the node
         * @param i
         *            the i
         * @param min
         *            the min
         * @param len
         *            the len
         */
        private Node(Node node, int i, Vector3 min, Vector3 len) {
            super(min, len);
            switch (i) {
            case LEFT:
                left = node;
                break;
            case CENTER:
                center = node;
                break;
            case RIGHT:
                right = node;
                break;
            default:
                break;
            }

            node.parent = this;
        }

        /**
         * Contains index.
         *
         * @param sphere
         *            the sphere
         * @return the int
         */
        public int containsIndex(final BoundingSphere sphere) {
            final float sr = sphere.getRadius();
            final float lenX = getLengthX();
            final float lenY = getLengthY();
            final float lenZ = getLengthZ();

            // skip 4 main planes for each child

            // if(onlyContains(sphere)){
            if (lenX >= lenY && lenX >= lenZ) {
                final float dist = getCenterX() - sphere.getX();
                if (dist >= sr) {
                    return LEFT;
                } else if (-dist >= sr) {
                    return RIGHT;
                } else if (Math.abs(dist) + sr <= lenX * .25f) {
                    return CENTER;
                }
            } else if (lenY >= lenZ) {
                final float dist = getCenterY() - sphere.getY();
                if (dist >= sr) {
                    return LEFT;
                } else if (-dist >= sr) {
                    return RIGHT;
                } else if (Math.abs(dist) + sr <= lenY * .25f) {
                    return CENTER;
                }
            } else {
                final float dist = getCenterZ() - sphere.getZ();
                if (dist >= sr) {
                    return LEFT;
                } else if (-dist >= sr) {
                    return RIGHT;
                } else if (Math.abs(dist) + sr <= lenZ * .25f) {
                    return CENTER;
                }
            }
            // }
            return -1;
        }

        /**
         * Container add.
         *
         * @param obj
         *            the obj
         */
        public void containerAdd(final Object obj) {
            if (container == null)
                container = new ArrayList<Object>(1);
            container.add(obj);
            container.trimToSize();
        }

        /**
         * Container remove.
         *
         * @param obj
         *            the obj
         */
        void containerRemove(final Object obj) {
            if (container != null) {
                container.remove(obj);
                if (container.size() == 0) {
                    container = null;
                }
            }
        }

        /**
         * Container size.
         *
         * @return the int
         */
        public int containerSize() {
            return container == null ? 0 : container.size();
        }

        /*
         * (non-Javadoc)
         * 
         * @see hidrogine.math.BoundingBox#toString()
         */
        public String toString() {
            return super.toString();
        }

        /**
         * Builds the.
         *
         * @param i
         *            the i
         * @return the space node
         */

        private Node build(final int i) {

            final float lenX = getLengthX();
            final float lenY = getLengthY();
            final float lenZ = getLengthZ();

            if (lenX >= lenY && lenX >= lenZ) {
                final Vector3 len = recycle(TEMP_LENGTH.set(lenX * 0.5f, lenY,
                        lenZ));

                if (i == LEFT) {
                    return new Node(this, getMin(), len);
                } else if (i == RIGHT) {
                    return new Node(this, new Vector3(getMin()).addX(lenX / 2),
                            len);
                } else {
                    return new Node(this, new Vector3(getMin()).addX(lenX / 4),
                            len);
                }
            } else if (lenY >= lenZ) {
                final Vector3 len = recycle(TEMP_LENGTH.set(lenX, lenY * 0.5f,
                        lenZ));

                if (i == LEFT) {
                    return new Node(this, getMin(), len);
                } else if (i == RIGHT) {
                    return new Node(this, new Vector3(getMin()).addY(lenY / 2),
                            len);
                } else {
                    return new Node(this, new Vector3(getMin()).addY(lenY / 4),
                            len);
                }
            } else {
                final Vector3 len = recycle(TEMP_LENGTH.set(lenX, lenY,
                        lenZ * 0.5f));
                if (i == LEFT) {
                    return new Node(this, getMin(), len);
                } else if (i == RIGHT) {
                    return new Node(this, new Vector3(getMin()).addZ(lenZ / 2),
                            len);
                } else {
                    return new Node(this, new Vector3(getMin()).addZ(lenZ / 4),
                            len);
                }
            }
        }

        /**
         * Gets the child.
         *
         * @param i
         *            the i
         * @return the child
         */
        public Node getChild(int i) {

            switch (i) {
            case LEFT:
                if (left == null) {
                    left = build(i);
                }
                return left;
            case CENTER:
                if (center == null) {
                    center = build(i);
                }
                return center;
            case RIGHT:
                if (right == null) {
                    right = build(i);
                }
                return right;
            default:
                break;
            }
            return null;

        }

        /**
         * Child.
         *
         * @param i
         *            the i
         * @return the node
         */
        public Node child(int i) {
            switch (i) {
            case LEFT:
                return left;
            case CENTER:
                return center;
            case RIGHT:
                return right;
            default:
                break;
            }
            return null;

        }

        /**
         * Expand.
         *
         * @param obj
         *            the obj
         * @return the space node
         */
        private Node expandAux(final BoundingSphere obj) {
            final float lenX = getLengthX();
            final float lenY = getLengthY();
            final float lenZ = getLengthZ();

            if (lenX < lenY && lenX < lenZ) {
                final Vector3 len = recycle(TEMP_LENGTH.set(lenX * 2, lenY,
                        lenZ));

                if (obj.getX() >= getCenterX()) {
                    return new Node(this, LEFT, getMin(), len);
                } else {
                    return new Node(this, RIGHT,
                            new Vector3(getMin()).addX(-lenX), len);
                }
            } else if (lenY < lenZ) {
                final Vector3 len = recycle(TEMP_LENGTH.set(lenX, lenY * 2,
                        lenZ));

                if (obj.getY() >= getCenterY()) {
                    return new Node(this, LEFT, getMin(), len);
                } else {
                    return new Node(this, RIGHT,
                            new Vector3(getMin()).addY(-lenY), len);

                }
            } else {
                final Vector3 len = recycle(TEMP_LENGTH.set(lenX, lenY,
                        lenZ * 2));

                if (obj.getZ() >= getCenterZ()) {
                    return new Node(this, LEFT, getMin(), len);
                } else {
                    return new Node(this, RIGHT,
                            new Vector3(getMin()).addZ(-lenZ), len);

                }
            }
        }

        /**
         * Can split.
         *
         * @return true, if successful
         */
        protected boolean canSplit() {
            // if(containerSize()==0)
            // return false;

            return left != null || right != null || center != null
                    || getLengthX() > minSize|| getLengthY() > minSize|| getLengthZ() > minSize;
        }

        /**
         * Iterate.
         *
         * @param frustum
         *            the frustum
         * @param nodeh
         *            the nodeh
         * @param j
         *            the j
         */
        @Deprecated
        public void handleVisibleNodes(BoundingFrustum frustum,
                VisibleNodeHandler handler, int j) {
            // String tabs = "";
            // for(int k = 0; k < j; ++k){
            // tabs += "  |  ";
            // }
            handler.onNodeVisible(this, containerSize());
            // System.out.println(tabs+"["+container.size()+"/"+count+"] "+toString());

            int intersections = 0;
            for (int i = 0; i < 3; ++i) {
                final Node node = child(i);
                if (node != null
                        && (intersections == 2 || frustum.contains(node) != ContainmentType.Disjoint)) {
                    ++intersections;
                    node.handleVisibleNodes(frustum, handler, 1 + j);
                }
            }
        }

        /**
         * Iterate.
         *
         * @param frustum
         *            the frustum
         * @param handler
         *            the handler
         */
        public void handleVisibleObjects(final BoundingFrustum frustum,
                final VisibleObjectHandler handler) {
            if (container != null) {
                for (Object obj : container) {
                    handler.onObjectVisible(obj);
                }
            }

            int intersections = 0;

            for (int i = 0; i < 3; ++i) {
                Node node = child(i);
                if (node != null
                        && (intersections == 2 || frustum.contains(node) != ContainmentType.Disjoint)) {
                    ++intersections;
                    node.handleVisibleObjects(frustum, handler);
                }
            }

        }

        /**
         * Removes the.
         *
         * @param obj
         *            the obj
         */
        public void remove(final Object obj) {
            containerRemove(obj);

            Node node = this;
            while (node != null) {
                node.clearChild();
                node = node.parent;
            }
        }

        /**
         * Clear child.
         */
        protected void clearChild() {

            if (left != null && left.isEmpty()) {
                left = null;
            }

            if (right != null && right.isEmpty()) {
                right = null;
            }

            if (center != null && center.isEmpty()) {
                center = null;
            }

        }

        /**
         * Update.
         *
         * @param sph
         *            the sph
         * @return the node
         */
        public Node update(BoundingSphere sph) {
            Node node = this;
            while (node != null) {
                node.clearChild();

                if (node.onlyContains(sph)) {
                    break;
                } else {
                    node = node.parent;
                }
            }

            return node;
        }

        /**
         * Expand.
         *
         * @param obj
         *            the obj
         * @return the node
         */
        public Node expand(final BoundingSphere obj) {
            Node node = this;
            while (!node.onlyContains(obj)) {
                node.clearChild();
                node = node.expandAux(obj);
            }
            return node;
        }

        /**
         * Iterate.
         *
         * @param sph
         *            the sph
         * @param handler
         *            the handler
         */
        public void handleObjectCollisions(final BoundingSphere sph,
                final ObjectCollisionHandler handler) {
            if (container != null) {
                for (Object obj : container) {
                    handler.onObjectCollision(obj);
                }
            }
            int intersections = 0;
            for (int i = 0; i < 3; ++i) {
                Node node = child(i);
                if (node != null
                        && (intersections == 2 || node.contains(sph) != ContainmentType.Disjoint)) {
                    ++intersections;
                    node.handleObjectCollisions(sph, handler);
                }
            }

        }

        /**
         * Insert.
         *
         * @param sph
         *            the sph
         * @return the space node
         */
        protected Node insert(final BoundingSphere sph) {
            Node node = this;

            // insertion
            while (true) {

                if (node.canSplit()) {

                    int i = node.containsIndex(sph);
                    if (i >= 0) {
                        node = node.getChild(i);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            return node;
        }

        /**
         * Handle ray collisions.
         *
         * @param space
         *            the space
         * @param ray
         *            the ray
         * @param handler
         *            the handler
         */
        public boolean handleRayCollisions(final Space space, final Ray ray,
                final RayCollisionHandler handler) {
            final float len = ray.getDirection().length();
            boolean result = false;
            if (container != null) {
                for (Object obj : container) {
                    result |= handler.onObjectCollision(space, ray, obj);
                }
            }
            int intersections = 0;
            for (int i = 0; i < 3; ++i) {
                Node node = child(i);
                Float idist = null;
                if (node != null
                        && (intersections == 2
                                || node.contains(ray.getPosition()) != ContainmentType.Disjoint || ((idist = ray
                                .intersects(node)) != null && idist <= len))) {
                    ++intersections;
                    if (idist == null) {
                        idist = 0f;
                    }
                    // System.out.println("#"+node+"#"+ray+"#"+idist+"#"+handler);
                    result |= node.handleRayCollisions(space, ray, handler);
                }
            }
            return result;
        }

        /**
         * Checks if is empty.
         *
         * @return true, if is empty
         */
        public boolean isEmpty() {
            return containerSize() == 0
                    && (left == null && center == null && right == null);
        }

        /**
         * Compress.
         *
         * @return the node
         */
        public Node compress() {
            Node node = this;
            while (true) {
                if (node.containerSize() == 0) {
                    boolean emptyLeft = node.left == null;
                    boolean emptyCenter = node.center == null;
                    boolean emptyRight = node.right == null;

                    if (emptyLeft && emptyCenter && !emptyRight) {
                        node = node.right;
                    } else if (emptyLeft && !emptyCenter && emptyRight) {
                        node = node.center;
                    } else if (!emptyLeft && emptyCenter && emptyRight) {
                        node = node.left;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            node.parent = null;
            return node;
        }

        /*
         * public boolean handleRayCollisions(Space space, Ray ray,
         * RayCollisionHandler handler) { float len =
         * ray.getDirection().length(); boolean ret = false; if (container !=
         * null) { IntersectionInfo closestInfo = null; BoundingSphere
         * closestObject = null; for (Object obj2 : container) { Float idist =
         * ray.intersects(obj2); if ((idist != null && idist < len) ||
         * obj2.contains(ray.getPosition())) { IntersectionInfo info =
         * handler.closestTriangle(obj2, ray); if (closestInfo == null ||
         * info.distance < closestInfo.distance) { closestInfo = info;
         * closestObject = obj2; } } } if (closestInfo != null) { ret |=
         * handler.onObjectCollision(space, ray, closestObject, closestInfo); }
         * } if (child != null) { int intersections = 0; for (int i = 0; i < 3;
         * ++i) { SpaceNode node = child[i]; Float idist = null; if (node !=
         * null && node.count > 0 && (intersections == 2 ||
         * node.contains(ray.getPosition()) != ContainmentType.Disjoint ||
         * ((idist = ray .intersects(node)) != null && idist <= len))) {
         * ++intersections; if (idist == null) { idist = 0f; } //
         * System.out.println("#"+node+"#"+ray+"#"+idist+"#"+handler); ret |=
         * node.handleRayCollisions(space, ray, handler); } } } return ret; }
         */
    }

    /**
     * Iterate.
     *
     * @param frustum
     *            the frustum
     * @param handler
     *            the handler
     */
    public void handleVisibleObjects(final BoundingFrustum frustum,
            final VisibleObjectHandler handler) {
        if (root != null) {
            root.handleVisibleObjects(frustum, handler);
        }
    }

    /**
     * Iterate.
     *
     * @param frustum
     *            the frustum
     * @param handler
     *            the handler
     */
    @Deprecated
    public void handleVisibleNodes(BoundingFrustum frustum,
            VisibleNodeHandler handler) {
        if (root != null) {
            root.handleVisibleNodes(frustum, handler, 0);
        }
    }

    /**
     * Handle object collisions.
     *
     * @param sphere
     *            the sphere
     * @param handler
     *            the handler
     */
    public void handleObjectCollisions(final BoundingSphere sphere,
            final ObjectCollisionHandler handler) {
        if (root != null) {
            root.handleObjectCollisions(sphere, handler);
        }
    }

    /**
     * Handle ray collisions.
     *
     * @param ray
     *            the ray
     * @param handler
     *            the handler
     */
    public boolean handleRayCollisions(final Ray ray,
            final RayCollisionHandler handler) {
        // System.out.println(handler+":"+ ray);
        if (root != null) {
            return root.handleRayCollisions(this, ray, handler);
        }
        return false;
    }

    /** The root. */
    private Node root;

    /**
     * Instantiates a new space.
     */
    public Space(float minSize) {
        this.minSize = minSize;
        root = new Node();
    }

    /**
     * Update.
     *
     * @param sph
     *            the sph
     * @param node
     *            the node
     * @param obj
     *            the obj
     * @return the space node
     */
    protected Node update(final BoundingSphere sph, Node node, final Object obj) {
        if (!node.onlyContains(sph)) {
            node.containerRemove(obj);
            node.clearChild();
            node = node.parent;
            if (node != null)
                node = node.update(sph);

            if (node == null) {
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
     * @param sph
     *            the sph
     * @param obj
     *            the obj
     * @return the space node
     */
    protected Node insert(final BoundingSphere sph, final Object obj) {
        root = root.expand(sph);
        final Node node = root.insert(sph);
        node.containerAdd(obj);
        root = root.compress();
        return node;
    }

}
