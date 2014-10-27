package hidrogine.math;


public class Space {



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

    protected SpaceNode insert(IObject3D obj) {
        // expand phase
      //  System.out.println("=== EXPANSION ===");
      //  System.out.println(root.toString());
        while (root.contains(obj)!=ContainmentType.Contains) {
            root = root.expand(obj);
        //    System.out.println(root.toString());

        }
        
        // insertion
        SpaceNode node = root.insert(obj);
        
        // root compression
        compress();
       // System.out.println("=== COMPRESSION ===");
       // System.out.println(root.toString());
        return node;
    }

    private void compress() {
        while(true){
            if(root.container.size()==0){
                boolean emptyLeft = root.child[SpaceNode.LEFT]==null || root.child[SpaceNode.LEFT].count==0;
                boolean emptyCenter = root.child[SpaceNode.CENTER]==null || root.child[SpaceNode.CENTER].count==0;
                boolean emptyRight = root.child[SpaceNode.RIGHT]==null || root.child[SpaceNode.RIGHT].count==0;
                
                if(emptyLeft && emptyCenter && !emptyRight){
                    root = root.child[SpaceNode.RIGHT];
                } else if(emptyLeft && !emptyCenter && emptyRight){
                    root = root.child[SpaceNode.CENTER];   
                } else if(!emptyLeft && emptyCenter && emptyRight){
                    root = root.child[SpaceNode.LEFT];
                } else {
                    break;
                }
            } else {
                break;
            }
        }        
    }

}
