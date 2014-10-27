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

    protected void update(IObject3D obj){
        SpaceNode node = obj.getNode();
        node.container.remove(obj);
        
        
        while(node!=null && node.contains(obj)!=ContainmentType.Contains){
            node.count--;
            node.clearChild();
            node = node.parent;
        }
        
        if(node==null){
            expandRoot(obj);
            node = root;
        }
        else {
            node.count--;
        }
       insert(obj, node);
    }
    
    
    private void expandRoot(IObject3D obj){
        //  System.out.println("=== EXPANSION ===");
        //  System.out.println(root.toString());
          while (root.contains(obj)!=ContainmentType.Contains) {
              root.clearChild();
              root = root.expand(obj);
              
          //    System.out.println(root.toString());

          }
    }

    
    protected SpaceNode insert(IObject3D obj, SpaceNode node) {
        // insertion
        while(true){
            ++node.count;

            boolean childContains=false;
            boolean canSplit = node.canSplit();
            if(canSplit){
                for(int i=0;i < 3 ; ++i){
                    SpaceNode child = node.getChild(i);
                    if(child.contains(obj)==ContainmentType.Contains){
                        childContains=true;
                        node=child;
                        break;
                    }
                }
            }
            
            if(!canSplit || !childContains) {
               // System.out.println("=== INSERTION ===");
               // System.out.println(toString());
                obj.setNode(node);
                node.container.add(obj);
                break;
            }
      
        }
        for(SpaceNode s =node; s!=null;s=s.parent){
            s.clearChild();
        }
        
        // root compression
        compress();
       // System.out.println("=== COMPRESSION ===");
       // System.out.println(root.toString());
        return node;
    }

    
    
    
    protected SpaceNode insert(IObject3D obj) {
        // expand phase
        expandRoot(obj);
        
        // insertion
        return insert(obj, root);
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
        root.parent=null;
    }

}
