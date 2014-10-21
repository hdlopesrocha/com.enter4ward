package hidrogine.math.api;


public abstract class IObject3D extends ISphere{
    private IVector3 position;   
    private IModel3D model;
    
    public IObject3D(IVector3 position, IModel3D model) {
        this.position = position;
        this.model = model;
    }
    
    public float getRadius() {
        return model.getContainer().getRadius();
    }

    public void setRadius(float radius) {
        throw new RuntimeException("Readonly property!");
    }

    
    public IVector3 getPosition() {
        return position;
    }

    public void setPosition(IVector3 position) {
        this.position = position;
    }
    
}
