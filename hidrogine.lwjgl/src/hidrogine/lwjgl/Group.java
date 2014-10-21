package hidrogine.lwjgl;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class Group {
    public List<BufferObject> subGroups = new ArrayList<BufferObject>();
    private Vector3f min;
    private Vector3f max;
    
    private String name;
    
    public Group(String n) {
        name=n;
    }

    public String getName() {
        return name;
    }

    public void addVertex(float vx, float vy, float vz) {
       if(max==null){
           max = new Vector3f(vx,vy,vz);
           min = new Vector3f(vx,vy,vz);
       }
       else {
           max.x = Math.max(max.x, vx);
           max.y = Math.max(max.y, vy);
           max.z = Math.max(max.z, vz);
           min.x = Math.min(min.x, vx);
           min.y = Math.min(min.y, vy);
           min.z = Math.min(min.z, vz);
       }
    }

    public Vector3f getCenter(){
        return new Vector3f((min.x+max.x)/2,(min.y+max.y)/2,(min.z+max.z)/2);
        
    }
    
    public Vector3f getMin() {
        return min;
    }
    
    public Vector3f getMax() {
        return max;
    }
}
