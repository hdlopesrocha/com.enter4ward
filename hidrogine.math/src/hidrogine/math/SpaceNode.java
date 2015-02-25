package hidrogine.math;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class SpaceNode.
 */
class SpaceNode extends BoundingBox {

    /** The Constant LEFT. */
    static final int LEFT = 0;

    /** The Constant RIGHT. */
    static final int RIGHT = 1;

    /** The Constant CENTER. */
    static final int CENTER = 2;

    /** The container. */
    private List<Object> container;

    /** The child. */
    SpaceNode[] child;

    /** The parent. */
    SpaceNode parent;

    /** The count. */
    long count;

    /**
     * Instantiates a new space node.
     */
    public SpaceNode() {
        super(new Vector3(0, 0, 0), new Vector3(1, 1, 1));
        this.count = 0l;
        this.parent = null;
    }

    /**
     * Instantiates a new space node.
     *
     * @param parent
     *            the parent
     * @param min
     *            the min
     * @param max
     *            the max
     */
    private SpaceNode(SpaceNode parent, Vector3 min, Vector3 max) {
        super(min, max);
        this.count = 0l;
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
     * @param max
     *            the max
     */
    private SpaceNode(SpaceNode node, int i, Vector3 min, Vector3 max) {
        super(min, max);
        this.child = new SpaceNode[3];
        this.child[i] = node;
        this.count = node.count;
        node.parent = this;
    }

    
    public int containsIndex(final BoundingSphere sphere, float lenX, float lenY, float lenZ){
        Vector3 sc = sphere.getCenter();
        float sr = sphere.getRadius();
        
        // skip 4 main planes for each child
        
 //       if(onlyContains(sphere)){
            if (lenX >= lenY && lenX >= lenZ) {
                float dist = getCenterX() - sc.getX();
                if(dist >= sr){
                    return LEFT; 
                }
                else if(-dist>=sr){
                    return RIGHT; 
                }
                else if(Math.abs(dist)+sr <= lenX*.25f){
                    return CENTER; 
                }
            } else if (lenY >= lenZ) {
                float dist = getCenterY() - sc.getY();
                if(dist >= sr){
                    return LEFT; 
                }
                else if(-dist>=sr){
                    return RIGHT; 
                }
                else if(Math.abs(dist)+sr <= lenY*.25f){
                    return CENTER; 
                }
            } else {
                float dist = getCenterZ() - sc.getZ();
                if(dist >= sr){
                    return LEFT; 
                }
                else if(-dist>=sr){
                    return RIGHT; 
                }
                else if(Math.abs(dist)+sr <= lenZ*.25f){
                    return CENTER; 
                }
            }
   //     }
        return -1;
    }
    
    

    public void containerAdd(Object obj) {
        if (container == null)
            container = new ArrayList<Object>();
        container.add(obj);
    }

    public void containerRemove(Object obj) {
        if (container != null) {
            container.remove(obj);
            if (container.size() == 0) {
                container = null;
            }
        }
    }

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
     * @param lenZ
     * @param lenY
     * @param lenX
     * @return the space node
     */
    private SpaceNode build(int i, float lenX, float lenY, float lenZ) {

        if (lenX >= lenY && lenX >= lenZ) {
            if (i == LEFT) {
                return new SpaceNode(this, getMin(),
                        new Vector3(getMax()).addX(-lenX / 2));
            } else if (i == RIGHT) {
                return new SpaceNode(this,
                        new Vector3(getMin()).addX(lenX / 2), getMax());
            } else {
                return new SpaceNode(this,
                        new Vector3(getMin()).addX(lenX / 4), new Vector3(
                                getMax()).addX(-lenX / 4));
            }
        } else if (lenY >= lenZ) {
            if (i == LEFT) {
                return new SpaceNode(this, getMin(),
                        new Vector3(getMax()).addY(-lenY / 2));
            } else if (i == RIGHT) {
                return new SpaceNode(this,
                        new Vector3(getMin()).addY(lenY / 2), getMax());
            } else {
                return new SpaceNode(this,
                        new Vector3(getMin()).addY(lenY / 4), new Vector3(
                                getMax()).addY(-lenY / 4));
            }
        } else {
            if (i == LEFT) {
                return new SpaceNode(this, getMin(),
                        new Vector3(getMax()).addZ(-lenZ / 2));
            } else if (i == RIGHT) {
                return new SpaceNode(this,
                        new Vector3(getMin()).addZ(lenZ / 2), getMax());
            } else {
                return new SpaceNode(this,
                        new Vector3(getMin()).addZ(lenZ / 4), new Vector3(
                                getMax()).addZ(-lenZ / 4));
            }
        }
    }

    /**
     * Gets the child.
     *
     * @param i
     *            the i
     * @param lenZ
     * @param lenY
     * @param lenX
     * @return the child
     */
    public SpaceNode getChild(int i, float lenX, float lenY, float lenZ) {
        if (child == null) {
            child = new SpaceNode[3];
        }
        if (child[i] == null) {
            child[i] = build(i, lenX, lenY, lenZ);
        }
        return child[i];
    }

    /**
     * Clear child.
     */
    protected void clearChild() {
        if (count == 0 && child != null) {
            child = null;
        }
    }

    /**
     * Expand.
     *
     * @param obj
     *            the obj
     * @return the space node
     */
    private SpaceNode expandAux(BoundingSphere obj) {
        Vector3 pos = obj.getCenter();
        float lenX = getLengthX();
        float lenY = getLengthY();
        float lenZ = getLengthZ();

        if (lenX < lenY && lenX < lenZ) {
            if (pos.getX() >= getCenterX()) {
                return new SpaceNode(this, LEFT, getMin(),
                        new Vector3(getMax()).addX(lenX));
            } else {
                return new SpaceNode(this, RIGHT,
                        new Vector3(getMin()).addX(-lenX), getMax());
            }
        } else if (lenY < lenZ) {
            if (pos.getY() >= getCenterY()) {
                return new SpaceNode(this, LEFT, getMin(),
                        new Vector3(getMax()).addY(lenY));
            } else {
                return new SpaceNode(this, RIGHT,
                        new Vector3(getMin()).addY(-lenY), getMax());
            }
        } else {
            if (pos.getZ() >= getCenterZ()) {
                return new SpaceNode(this, LEFT, getMin(),
                        new Vector3(getMax()).addZ(lenZ));
            } else {
                return new SpaceNode(this, RIGHT,
                        new Vector3(getMin()).addZ(-lenZ), getMax());
            }
        }
    }

    /**
     * Can split.
     *
     * @return true, if successful
     */
    protected boolean canSplit() {
        return getMin().distanceSquared(getMax()) > 1f;
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
            VisibleNodeHandler nodeh, int j) {
        // String tabs = "";
        // for(int k = 0; k < j; ++k){
        // tabs += "  |  ";
        // }
        nodeh.onNodeVisible(this, containerSize());
        // System.out.println(tabs+"["+container.size()+"/"+count+"] "+toString());

        if (child != null) {
            int intersections = 0;
            for (int i = 0; i < 3; ++i) {
                SpaceNode node = child[i];
                if (node != null
                        && node.count > 0
                        && (intersections == 2 || frustum.contains(node) != ContainmentType.Disjoint)) {
                    ++intersections;
                    node.handleVisibleNodes(frustum, nodeh, 1 + j);
                }
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
    public void handleVisibleObjects(BoundingFrustum frustum,
            VisibleObjectHandler handler) {
        if (container != null) {
            for (Object obj : container) {
                handler.onObjectVisible(obj);
            }
        }

        if (child != null) {
            int intersections = 0;

            for (int i = 0; i < 3; ++i) {
                SpaceNode node = child[i];
                if (node != null
                        && node.count > 0
                        && (intersections == 2 || frustum.contains(node) != ContainmentType.Disjoint)) {
                    ++intersections;
                    node.handleVisibleObjects(frustum, handler);
                }
            }
        }
    }

    /**
     * Removes the.
     *
     * @param obj
     *            the obj
     */
    public void remove() {
        SpaceNode node = this;
        while (node != null) {
            node.count--;
            node.clearChild();
            node = node.parent;
        }
    }

    public SpaceNode update(BoundingSphere sph) {
        SpaceNode node = this;
        while (node != null && !node.onlyContains(sph)) {
            node.count--;
            node.clearChild();
            node = node.parent;
        }
        return node;
    }

    public SpaceNode expand(BoundingSphere obj) {
        SpaceNode node = this;
        // System.out.println("=== EXPANSION ===");
        // System.out.println(root.toString());
        while (!node.onlyContains(obj)) {
            clearChild();
            node = node.expandAux(obj);
        }
        return node;
    }

    /**
     * Iterate.
     *
     * @param obj1
     *            the obj1
     * @param handler
     *            the handler
     */
    public void handleObjectCollisions(BoundingSphere sph,
            ObjectCollisionHandler handler) {
        if (container != null) {
            for (Object obj : container) {
                handler.onObjectCollision(obj);
            }
        }
        if (child != null) {
            int intersections = 0;
            for (int i = 0; i < 3; ++i) {
                SpaceNode node = child[i];
                if (node != null
                        && node.count > 0
                        && (intersections == 2 || node.contains(sph) != ContainmentType.Disjoint)) {
                    ++intersections;
                    node.handleObjectCollisions(sph, handler);
                }
            }
        }
    }

    /**
     * Handle ray collisions.
     *
     * @param ray
     *            the ray
     * @param maxDistance
     *            the distance
     * @param handler
     *            the handler
     */
    public void handleRayCollisions(Space space, Ray ray,
            RayCollisionHandler handler) {
        float len = ray.getDirection().length();
        if (container != null) {
            for (Object obj : container) {
                handler.onObjectCollision(space, ray, obj);
            }
        }
        if (child != null) {
            int intersections = 0;
            for (int i = 0; i < 3; ++i) {
                SpaceNode node = child[i];
                Float idist = null;
                if (node != null
                        && node.count > 0
                        && (intersections == 2
                                || node.contains(ray.getPosition()) != ContainmentType.Disjoint || ((idist = ray
                                .intersects(node)) != null && idist <= len))) {
                    ++intersections;
                    if (idist == null) {
                        idist = 0f;
                    }
                    // System.out.println("#"+node+"#"+ray+"#"+idist+"#"+handler);
                    node.handleRayCollisions(space, ray, handler);
                }
            }
        }
    }

    /*
     * public boolean handleRayCollisions(Space space, Ray ray,
     * RayCollisionHandler handler) { float len = ray.getDirection().length();
     * boolean ret = false; if (container != null) { IntersectionInfo
     * closestInfo = null; BoundingSphere closestObject = null; for (Object
     * obj2 : container) { Float idist = ray.intersects(obj2); if ((idist !=
     * null && idist < len) || obj2.contains(ray.getPosition())) {
     * IntersectionInfo info = handler.closestTriangle(obj2, ray); if
     * (closestInfo == null || info.distance < closestInfo.distance) {
     * closestInfo = info; closestObject = obj2; } } } if (closestInfo != null)
     * { ret |= handler.onObjectCollision(space, ray, closestObject,
     * closestInfo); } } if (child != null) { int intersections = 0; for (int i
     * = 0; i < 3; ++i) { SpaceNode node = child[i]; Float idist = null; if
     * (node != null && node.count > 0 && (intersections == 2 ||
     * node.contains(ray.getPosition()) != ContainmentType.Disjoint || ((idist =
     * ray .intersects(node)) != null && idist <= len))) { ++intersections; if
     * (idist == null) { idist = 0f; } //
     * System.out.println("#"+node+"#"+ray+"#"+idist+"#"+handler); ret |=
     * node.handleRayCollisions(space, ray, handler); } } } return ret; }
     */
}
