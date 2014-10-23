package hidrogine.lwjgl;

import hidrogine.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Group {
    public List<BufferObject> subGroups = new ArrayList<BufferObject>();
    private Vector3 min;
    private Vector3 max;
    
    private String name;
    
    public Group(String n) {
        name=n;
    }

    public String getName() {
        return name;
    }

    public void addVertex(float vx, float vy, float vz) {
       if(max==null){
           max = new Vector3(vx,vy,vz);
           min = new Vector3(vx,vy,vz);
       }
       else {
           max.setX(Math.max(max.getX(), vx));
           max.setY(Math.max(max.getY(), vy));
           max.setZ(Math.max(max.getZ(), vz));
           min.setX(Math.min(min.getX(), vx));
           min.setY(Math.min(min.getY(), vy));
           min.setZ(Math.min(min.getZ(), vz));
       }
    }

    public Vector3 getCenter(){
        return new Vector3((min.getX()+max.getX())/2,(min.getY()+max.getY())/2,(min.getZ()+max.getZ())/2);
        
    }
    
    public Vector3 getMin() {
        return min;
    }
    
    public Vector3 getMax() {
        return max;
    }
}
