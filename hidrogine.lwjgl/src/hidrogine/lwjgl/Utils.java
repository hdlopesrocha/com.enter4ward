package hidrogine.lwjgl;

public class Utils {
    /** The pi. */
    private static final double PI = 3.14159265358979323846;
    /**
     * Co tangent.
     *
     * @param angle
     *            the angle
     * @return the float
     */
    public static float coTangent(float angle) {
        return (float) (1f / Math.tan(angle));
    }

    /**
     * Degrees to radians.
     *
     * @param degrees
     *            the degrees
     * @return the float
     */
    public static float degreesToRadians(float degrees) {
        return degrees * (float) (PI / 180d);
    }
}
