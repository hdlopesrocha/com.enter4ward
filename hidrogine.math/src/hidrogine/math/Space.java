package hidrogine.math;

import hidrogine.math.api.IBoundingSphere;
import hidrogine.math.api.IObject3D;
import hidrogine.math.api.IVector3;

import java.util.ArrayList;
import java.util.List;

public class Space {
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int CENTER = 2;

    private class SpaceNode extends BoundingBox {

        private final List<IObject3D> container = new ArrayList<IObject3D>();
        private SpaceNode[] child;
        private Long count;

        public SpaceNode() {
            super(new Vector3(-32, -32, -32), new Vector3(32, 32, 32));
            this.count = 0l;
        }

        private SpaceNode(IVector3 min, IVector3 max) {
            super(min, max);
            this.count = 0l;
        }

        private SpaceNode(SpaceNode node, int i, IVector3 min, IVector3 max) {
            super(min, max);
            this.child = new SpaceNode[3];
            this.child[i] = node;
            this.count = node.count;
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
        
        public void insert(IObject3D obj){
            boolean childContains=false;
            
            if(canSplit()){
                for(int i=0;i < 3 ; ++i){
                    SpaceNode node = getChild(i);
                    if(node.contains(obj)==ContainmentType.Contains){
                        childContains=true;
                        node.insert(obj);
                        break;
                    }
                }
            }
            ++count;
            
            if(!canSplit() || !childContains) {
               // System.out.println("=== INSERTION ===");
               // System.out.println(toString());
                container.add(obj);
            }
        
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

        public boolean canSplit() {
            return getMin().distance(getMax()) > 1f;
        }

        public void iterate(BoundingFrustum frustum, NodeIteratorHandler nodeh ,int j){
            /*String tabs = "";
            for(int k = 0; k < j; ++k){
                tabs += "  |  ";
            }*/
            nodeh.handle2(this);
           // System.out.println(tabs+"["+container.size()+"/"+count+"] "+toString()+" "+expanded);
            
            if(child!=null){
                for(int i=0; i < 3; ++i){
                    SpaceNode node = child[i];
                    if(node!=null && frustum.contains(node)!=ContainmentType.Disjoint){
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
                    if(node!=null && frustum.contains(node)!=ContainmentType.Disjoint){
                        node.iterate(frustum, handler);
                    }
                } 
            }
        }
   

    
    }

    public void iterate(BoundingFrustum frustum, ObjectIterator handler){
        if(root!=null){
            root.iterate(frustum, handler);
        }
    }

    

    public void iterate(BoundingFrustum frustum, NodeIteratorHandler nodeh){
        if(root!=null){
            root.iterate(frustum, nodeh,0);
        }
    }
    
    private SpaceNode root;

    public Space() {
        root = new SpaceNode();
    }

    public void insert(IObject3D obj) {
        // expand phase
      //  System.out.println("=== EXPANSION ===");
      //  System.out.println(root.toString());
        while (root.contains(obj)!=ContainmentType.Contains) {
            root = root.expand(obj);
        //    System.out.println(root.toString());

        }
        
        // insertion
        root.insert(obj);
        
        // root compression
        compress();
       // System.out.println("=== COMPRESSION ===");
       // System.out.println(root.toString());
    }

    private void compress() {
        while(true){
            if(root.container.size()==0){
                boolean emptyLeft = root.child[LEFT]==null || root.child[LEFT].count==0;
                boolean emptyCenter = root.child[CENTER]==null || root.child[CENTER].count==0;
                boolean emptyRight = root.child[RIGHT]==null || root.child[RIGHT].count==0;
                
                if(emptyLeft && emptyCenter && !emptyRight){
                    root = root.child[RIGHT];
                } else if(emptyLeft && !emptyCenter && emptyRight){
                    root = root.child[CENTER];   
                } else if(!emptyLeft && emptyCenter && emptyRight){
                    root = root.child[LEFT];
                } else {
                    break;
                }
            } else {
                break;
            }
        }        
    }

}
