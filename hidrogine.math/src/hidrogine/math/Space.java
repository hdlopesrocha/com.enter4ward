package hidrogine.math;

import hidrogine.math.api.ISphere;
import hidrogine.math.api.IVector3;

import java.util.ArrayList;
import java.util.List;

public class Space {

    class SpaceNode extends Box {
        private static final int LEFT = 0;
        private static final int RIGHT = 1;
        private static final int CENTER = 2;

        private final List<ISphere> container = new ArrayList<ISphere>();
        private SpaceNode[] child;
        private Long count;

        public SpaceNode() {
            super(new Vector3(0, 0, 0), new Vector3(1, 1, 1));
            this.count = 0l;
        }

        private SpaceNode(IVector3 min, IVector3 max) {
            super(min, max);
            this.count = 0l;
        }

        private SpaceNode(SpaceNode old, int i, IVector3 min, IVector3 max) {
            super(min, max);
            this.child = new SpaceNode[3];
            this.child[i] = old;
            this.count = old.count;
        }

        public String toString() {
            return super.toString();
        }

        public void clear() {
            this.child = new SpaceNode[3];
        }

        private SpaceNode build(int i) {
            float lenX = getLengthX();
            float lenY = getLengthY();
            float lenZ = getLengthZ();
            
            if (lenX >= lenY && lenX >= lenZ) {
                if (i == LEFT) {
                    return new SpaceNode(getMin(), new Vector3(getMax()).addX(-lenX / 2));
                } else if (i == RIGHT) {
                    return new SpaceNode(new Vector3(getMin()).addX(lenX / 2), getMax());
                } else {
                    return new SpaceNode(new Vector3(getMin()).addX(lenX / 4), new Vector3(getMax()).addX(-lenX / 4));
                }
            } else if (lenY >= lenZ) {
                if (i == LEFT) {
                    return new SpaceNode(getMin(), new Vector3(getMax()).addY(-lenY / 2));
                } else if (i == RIGHT) {
                    return new SpaceNode(new Vector3(getMin()).addY(lenY / 2), getMax());
                } else {
                    return new SpaceNode(new Vector3(getMin()).addY(lenY / 4), new Vector3(getMax()).addY(-lenY / 4));
                }
            } else {
                if (i == LEFT) {
                    return new SpaceNode(getMin(), new Vector3(getMax()).addZ(-lenZ / 2));
                } else if (i == RIGHT) {
                    return new SpaceNode(new Vector3(getMin()).addZ(lenZ / 2), getMax());
                } else {
                    return new SpaceNode(new Vector3(getMin()).addZ(lenZ / 4), new Vector3(getMax()).addZ(-lenZ / 4));
                }
            }
        }

        public SpaceNode getChild(int i) {
            if (canSplit()) {
                if (child == null) {
                    child = new SpaceNode[3];
                }
                if (child[i] == null) {
                    child[i] = build(i);
                }
                return child[i];
            }
            return null;
        }
        
        public void insert(ISphere obj){
            System.out.println(toString());

            IVector3 pos = obj.getPosition(); // XXX - MUST BE SPHERE
            if(canSplit()){
                for(int i=0;i < 3 ; ++i){

                    SpaceNode node = getChild(i);
                    if(node.contains(pos)){
                        node.insert(obj);
                        break;
                    }
                }
            }
            else {
                container.add(obj);
            }
            ++count;
        }

        public SpaceNode expand(ISphere obj) {
            IVector3 pos = obj.getPosition(); // XXX - MUST BE SPHERE

            float lenX = getLengthX();
            float lenY = getLengthY();
            float lenZ = getLengthZ();

            if (lenX <= lenY && lenX <= lenZ) {
                if (pos.getX() >= getCenterX()) {
                    return new SpaceNode(this, LEFT, getMin(), new Vector3(
                            getMax()).addX(lenX));
                } else {
                    return new SpaceNode(this, RIGHT,
                            new Vector3(getMin()).addX(-lenX), getMax());
                }
            } else if (lenY <= lenZ) {
                if (pos.getY() >= getCenterY()) {
                    return new SpaceNode(this, LEFT, getMin(), new Vector3(
                            getMax()).addY(lenY));
                } else {
                    return new SpaceNode(this, RIGHT,
                            new Vector3(getMin()).addY(-lenY), getMax());
                }
            } else {
                if (pos.getZ() >= getCenterZ()) {
                    return new SpaceNode(this, LEFT, getMin(), new Vector3(
                            getMax()).addZ(lenZ));
                } else {
                    return new SpaceNode(this, RIGHT,
                            new Vector3(getMin()).addZ(-lenZ), getMax());
                }
            }
        }

        public boolean canSplit() {
            return getMin().distance(getMax()) > 1f;
        }
    }

    private SpaceNode root;

    public Space() {
        root = new SpaceNode();
    }

    public void insert(ISphere obj) {
        IVector3 pos = obj.getPosition(); // XXX - MUST BE SPHERE
        System.out.println("=== EXPANSION ===");
        System.out.println(root.toString());

        // expand phase
        while (!root.contains(pos)) {
            root = root.expand(obj);
            System.out.println(root.toString());
        }
        System.out.println("=== INSERTION ===");
        // insertion
        root.insert(obj);
    }

}
