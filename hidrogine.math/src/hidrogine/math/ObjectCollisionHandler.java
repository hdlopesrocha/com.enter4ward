package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Interface ObjectCollisionHandler.
 */
public interface ObjectCollisionHandler {

    /**
     * On object collision.
     *
     * @param obj1
     *            the obj1
     * @param obj2
     *            the obj2
     */
    public void onObjectCollision(IBoundingSphere obj1, IBoundingSphere obj2);
}
