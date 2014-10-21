package hidrogine.math;

import hidrogine.math.api.ISphere;
import hidrogine.math.api.IVector3;

public class Object3D extends ISphere{
    private IVector3 position;    
    public Object3D(IVector3 position) {
        this.position = position;
    }
    
    public float getRadius() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setRadius(float radius) {
        // TODO Auto-generated method stub
        
    }

    
    public IVector3 getPosition() {
        return position;
    }

    public void setPosition(IVector3 position) {
        this.position = position;
    }
    
}
