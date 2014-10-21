package hidrogine.math;

import hidrogine.math.api.ISphere;
import hidrogine.math.api.IVector3;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class Sphere extends ISphere {

    /** The max. */
    private IVector3 position;

    /** The radius. */
    private float radius;

    /**
     * Instantiates a new sphere.
     *
     * @param position
     *            the position
     * @param radius
     *            the radius
     */
    public Sphere(IVector3 position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#getCenter()
     */
    @Override
    public IVector3 getPosition() {
        return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#setCenter(hidrogine.math.IVector3)
     */
    @Override
    public void setPosition(IVector3 position) {
        this.position = position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#getRadius()
     */
    @Override
    public float getRadius() {
        return radius;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#setRadius(float)
     */
    @Override
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * Creates the from points.
     *
     * @param points
     *            the points
     * @return the sphere
     */
    public static Sphere createFromPoints(Iterable<IVector3> points) {
        IVector3 min = new Vector3();
        IVector3 max = new Vector3();
        boolean inited = false;

        for (IVector3 vec : points) {
            if (!inited) {
                min.set(vec);
                max.set(vec);
                inited = true;
            }
            min.setX(Math.min(min.getX(), vec.getX()));
            min.setY(Math.min(min.getY(), vec.getY()));
            min.setZ(Math.min(min.getZ(), vec.getZ()));
            max.setX(Math.max(max.getX(), vec.getX()));
            max.setY(Math.max(max.getY(), vec.getY()));
            max.setZ(Math.max(max.getZ(), vec.getZ()));
        }
        return new Sphere(new Vector3(min).add(max).divide(2f),
                (float) (max.distance(min) / 2d));
    }
}
