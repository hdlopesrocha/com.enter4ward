package hidrogine.math;


public interface RayCollisionHandler {

    
    public void onObjectCollision(final Space space,final Ray ray,final Object obj2);

    
   // public IntersectionInfo closestTriangle(final IBoundingSphere obj, final Ray ray);

}
