package hidrogine.lwjgl;

import hidrogine.math.BoundingSphere;

import java.util.ArrayList;
import java.util.List;

public class Group extends BoundingSphere {
    private List<BufferObject> subGroups = new ArrayList<BufferObject>();
    private String name;
    
    public Group(String n) {
        name=n;
    }

    public String getName() {
        return name;
    }

    
    
    public void addBuffer(BufferObject buffer){
    	subGroups.add(buffer);
    }
    
    public Iterable<BufferObject> getBuffers(){
    	return subGroups;
    }
  
}
