package hidrogine.math;

import hidrogine.math.api.IObject3D;
import hidrogine.math.api.IVector3;

import java.util.ArrayList;
import java.util.List;

public class Space {

    class SpaceNode extends Box {
        final List<IObject3D> container= new ArrayList<IObject3D>();
        SpaceNode left, right;

        public SpaceNode() {
            super(new Vector3(-1, -1, -1), new Vector3(1, 1, 1));
            this.left = this.right = null;
        }

        public SpaceNode(IVector3 min, IVector3 max) {
            super(min, max);
            this.left = this.right = null;
        }

        public SpaceNode(SpaceNode left, SpaceNode right) {
            super(left.getMin(), right.getMax());
            this.left = left;
            this.right = right;
        }

        public void split() {
            float lenX = getLengthX();
            float lenY = getLengthY();
            float lenZ = getLengthZ();
            float ctrX = getCenterX();
            float ctrY = getCenterY();
            float ctrZ = getCenterZ();

            if (lenX >= lenY && lenX >= lenZ) {
                left = new SpaceNode(getMin(), new Vector3(
                        getMax()).setX(ctrX));
                right = new SpaceNode(
                        new Vector3(getMin()).setX(ctrX), getMax());
            } else if (lenY >= lenZ) {
                left = new SpaceNode(getMin(), new Vector3(
                        getMax()).setY(ctrY));
                right = new SpaceNode(
                        new Vector3(getMin()).setY(ctrY), getMax());
            } else {
                left = new SpaceNode(getMin(), new Vector3(
                        getMax()).setZ(ctrZ));
                right = new SpaceNode(
                        new Vector3(getMin()).setZ(ctrZ), getMax());
            }
        }

        public SpaceNode expand(IVector3 vec) {
            float lenX = getLengthX();
            float lenY = getLengthY();
            float lenZ = getLengthZ();
            float ctrX = getCenterX();
            float ctrY = getCenterY();
            float ctrZ = getCenterZ();

            if (lenX <= lenY && lenX <= lenZ) {
                if (vec.getX() >= ctrX) {
                    return new SpaceNode(this, new SpaceNode(new Vector3(
                            getMin()).addX(lenX),
                            new Vector3(getMax()).addX(lenX)));
                } else {
                    return new SpaceNode(new SpaceNode(
                            new Vector3(getMin()).addX(-lenX), new Vector3(
                                    getMax()).addX(-lenX)), this);
                }
            } else if (lenY <= lenZ) {
                if (vec.getY() >= ctrY) {
                    return new SpaceNode(this, new SpaceNode(new Vector3(
                            getMin()).addY(lenY),
                            new Vector3(getMax()).addY(lenY)));
                } else {
                    return new SpaceNode(new SpaceNode(
                            new Vector3(getMin()).addY(-lenY), new Vector3(
                                    getMax()).addY(-lenY)), this);
                }
            } else {
                if (vec.getZ() >= ctrZ) {
                    return new SpaceNode(this, new SpaceNode(new Vector3(
                            getMin()).addZ(lenZ),
                            new Vector3(getMax()).addZ(lenZ)));
                } else {
                    return new SpaceNode(new SpaceNode(
                            new Vector3(getMin()).addZ(-lenZ), new Vector3(
                                    getMax()).addZ(-lenZ)), this);
                }
            }
        }
        
        public boolean canSplit(){
            return getMin().distance(getMax()) > 1f;
        }
    }

    private SpaceNode root;
    
    public Space() {
        root = new SpaceNode();
        root.split();
        root = root.expand(new Vector3(1, 0, 0));
    }

}
