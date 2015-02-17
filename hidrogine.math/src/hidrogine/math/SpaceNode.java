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

    static final BoundingBox TEMP_BOX = new BoundingBox();
    static final IVector3 TEMP_MIN = new Vector3();
    static final IVector3 TEMP_MAX = new Vector3();


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
    private SpaceNode(SpaceNode parent, IVector3 min, IVector3 max) {
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
    private SpaceNode(SpaceNode node, int i, IVector3 min, IVector3 max) {
        super(min, max);
        this.child = new SpaceNode[3];
        this.child[i] = node;
        this.count = node.count;
        node.parent = this;
    }

    public ContainmentType childContains(final int i, final IBoundingSphere sphere, float lenX, float lenY, float lenZ) {
   

        if (lenX >= lenY && lenX >= lenZ) {
            if (i == LEFT) {
                TEMP_BOX.setMin(getMin());
                TEMP_BOX.setMax(TEMP_MAX.set(getMax()).addX(-lenX / 2));
            } else if (i == RIGHT) {
                TEMP_BOX.setMin(TEMP_MIN.set(getMin()).addX(lenX / 2));
                TEMP_BOX.setMax(getMax());
            } else {
                TEMP_BOX.setMin(TEMP_MIN.set(getMin()).addX(lenX / 4));
                TEMP_BOX.setMax(TEMP_MAX.set(getMax()).addX(-lenX / 4));
            }
        } else if (lenY >= lenZ) {
            if (i == LEFT) {
                TEMP_BOX.setMin(getMin());
                TEMP_BOX.setMax(TEMP_MAX.set(getMax()).addY(-lenY / 2));
            } else if (i == RIGHT) {
                TEMP_BOX.setMin(TEMP_MIN.set(getMin()).addY(lenY / 2));
                TEMP_BOX.setMax(getMax());
            } else {
                TEMP_BOX.setMin(TEMP_MIN.set(getMin()).addY(lenY / 4));
                TEMP_BOX.setMax(TEMP_MAX.set(getMax()).addY(-lenY / 4));
            }
        } else {
            if (i == LEFT) {
                TEMP_BOX.setMin(getMin());
                TEMP_BOX.setMax(TEMP_MAX.set(getMax()).addZ(-lenZ / 2));
            } else if (i == RIGHT) {
                TEMP_BOX.setMin(TEMP_MIN.set(getMin()).addZ(lenZ / 2));
                TEMP_BOX.setMax(getMax());
            } else {
                TEMP_BOX.setMin(TEMP_MIN.set(getMin()).addZ(lenZ / 4));
                TEMP_BOX.setMax(TEMP_MAX.set(getMax()).addZ(-lenZ / 4));
            }
        }
        
        return TEMP_BOX.contains(sphere);
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
            child[i] = build(i,lenX, lenY,lenZ);
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
    private SpaceNode expandAux(IBoundingSphere obj) {
        IVector3 pos = obj.getCenter();
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

    public SpaceNode update(IBoundingSphere sph) {
        SpaceNode node = this;
        while (node != null && node.contains(sph) != ContainmentType.Contains) {
            node.count--;
            node.clearChild();
            node = node.parent;
        }
        return node;
    }

    public SpaceNode expand(IBoundingSphere obj) {
        SpaceNode node = this;
        // System.out.println("=== EXPANSION ===");
        // System.out.println(root.toString());
        while (node.contains(obj) != ContainmentType.Contains) {
            clearChild();
            node = node.expandAux(obj);
        }
        return node;
    }

}
