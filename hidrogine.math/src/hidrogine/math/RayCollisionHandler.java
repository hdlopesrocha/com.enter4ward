package hidrogine.math;


public interface RayCollisionHandler {

    
    public boolean onObjectCollision(final Space space,final Ray ray,final IBoundingSphere obj2, final IntersectionInfo info);

    
    public IntersectionInfo closestTriangle(final IBoundingSphere obj, final Ray ray);

}
