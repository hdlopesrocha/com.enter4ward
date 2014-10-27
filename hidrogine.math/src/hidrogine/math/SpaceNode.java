package hidrogine.math;

import java.util.ArrayList;
import java.util.List;

class SpaceNode extends BoundingBox {

    static final int LEFT = 0;
    static final int RIGHT = 1;
    static final int CENTER = 2;
    
    List<IObject3D> container = new ArrayList<IObject3D>();
    SpaceNode[] child;
    SpaceNode parent;
    Long count;

    public SpaceNode() {
        super(new Vector3(0, 0, 0), new Vector3(1, 1, 1));
        this.count = 0l;
        this.parent = null;
    }

    private SpaceNode(SpaceNode parent,IVector3 min, IVector3 max) {
        super(min, max);
        this.count = 0l;
        this.parent = parent;
    }

    private SpaceNode(SpaceNode node, int i, IVector3 min, IVector3 max) {
        super(min, max);
        this.child = new SpaceNode[3];
        this.child[i] = node;
        this.count = node.count;
        node.parent = this;
    }

    public String toString() {
        return super.toString();
    }


    private SpaceNode build(int i) {
        float lenX = getLengthX();
        float lenY = getLengthY();
        float lenZ = getLengthZ();
                
        
        if (lenX >= lenY && lenX >= lenZ) {
            if (i == LEFT) {
                return new SpaceNode(this, getMin(), new Vector3(getMax()).addX(-lenX / 2));
            } else if (i == RIGHT) {
                return new SpaceNode(this, new Vector3(getMin()).addX(lenX / 2), getMax());
            } else {
                return new SpaceNode(this, new Vector3(getMin()).addX(lenX / 4), new Vector3(getMax()).addX(-lenX / 4));
            }
        } else if (lenY >= lenZ) {
            if (i == LEFT) {
                return new SpaceNode(this, getMin(), new Vector3(getMax()).addY(-lenY / 2));
            } else if (i == RIGHT) {
                return new SpaceNode(this, new Vector3(getMin()).addY(lenY / 2), getMax());
            } else {
                return new SpaceNode(this, new Vector3(getMin()).addY(lenY / 4), new Vector3(getMax()).addY(-lenY / 4));
            }
        } else {
            if (i == LEFT) {
                return new SpaceNode(this, getMin(), new Vector3(getMax()).addZ(-lenZ / 2));
            } else if (i == RIGHT) {
                return new SpaceNode(this, new Vector3(getMin()).addZ(lenZ / 2), getMax());
            } else {
                return new SpaceNode(this, new Vector3(getMin()).addZ(lenZ / 4), new Vector3(getMax()).addZ(-lenZ / 4));
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
    


    protected void clearChild(){
        if(child!=null){
            for(int i=0;i < 3 ; ++i){
                SpaceNode node = child[i];
                if(node!=null && node.count==0){
                    node.child = null;
                }
            }
        }
    }
    
    public SpaceNode expand(IBoundingSphere obj) {
        IVector3 pos = obj.getPosition();
        float lenX = getLengthX();
        float lenY = getLengthY();
        float lenZ = getLengthZ();
        if (lenX <= lenY && lenX <= lenZ) {
            if (pos.getX() >= getCenterX()) {
                return new SpaceNode(this, LEFT, getMin(), new Vector3(getMax()).addX(lenX));
            } else {
                return new SpaceNode(this, RIGHT,new Vector3(getMin()).addX(-lenX), getMax());
            }
        } else if (lenY <= lenZ) {
            if (pos.getY() >= getCenterY()) {
                return new SpaceNode(this, LEFT, getMin(), new Vector3(getMax()).addY(lenY));
            } else {
                return new SpaceNode(this, RIGHT,new Vector3(getMin()).addY(-lenY), getMax());
            }
        } else {
            if (pos.getZ() >= getCenterZ()) {
                return new SpaceNode(this, LEFT, getMin(), new Vector3(getMax()).addZ(lenZ));
            } else {
                return new SpaceNode(this, RIGHT,new Vector3(getMin()).addZ(-lenZ), getMax());
            }
        }
    }

    protected boolean canSplit() {
        return getMin().distance(getMax()) > 1f;
    }

    public void iterate(BoundingFrustum frustum, NodeIteratorHandler nodeh ,int j){
     //  String tabs = "";
      //  for(int k = 0; k < j; ++k){
      //      tabs += "  |  ";
      //  }
        nodeh.handle2(this);
       // System.out.println(tabs+"["+container.size()+"/"+count+"] "+toString());
        
        if(child!=null){
            for(int i=0; i < 3; ++i){
                SpaceNode node = child[i];
                if(node!=null && node.count>0 && frustum.contains(node)!=ContainmentType.Disjoint){
                    node.iterate(frustum,nodeh,1+j);
                }
            } 
        }
    }

    
    public void iterate(BoundingFrustum frustum, ObjectIterator handler){
        for(IObject3D obj : container){
            if(frustum.contains(obj)!=ContainmentType.Disjoint){
                handler.handleObject(obj);
            }
        }
        
        if(child!=null){
            for(int i=0; i < 3; ++i){
                SpaceNode node = child[i];
                if(node!=null && node.count>0 && frustum.contains(node)!=ContainmentType.Disjoint){
                    node.iterate(frustum, handler);
                }
            } 
        }
    }

    public void remove(IObject3D obj) {
        SpaceNode node = this;
        container.remove(obj);
        
        
        while(node!=null){
            node.count--;
            node.clearChild();
            node = node.parent;
        }
        
        
    }



}
