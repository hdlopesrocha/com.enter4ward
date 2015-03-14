package hidrogine.math;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

public abstract class IBufferObject extends BoundingSphere {
    private final static ArrayList<Float> vertexData = new ArrayList<Float>(
            100000);
    private final static ArrayList<Float> normalData = new ArrayList<Float>(
            100000);
    private final static ArrayList<Float> textureData = new ArrayList<Float>(
            100000);
    private final static ArrayList<Short> indexData = new ArrayList<Short>(
            100000);

    protected FloatBuffer vertexBuffer;
    protected ShortBuffer indexBuffer;
    protected IntBuffer vbo;
    protected int vboSize;
    protected int indexCount;

    /** The triangles. */
    private List<Triangle> triangles = new ArrayList<Triangle>();
    /** The explode triangles. */
    private boolean explodeTriangles;

    float maxX = 0, maxY = 0, maxZ = 0, minX = 0, minY = 0, minZ = 0;
    boolean inited = false;

    public IBufferObject(boolean explodeTriangles) {
        this.explodeTriangles = explodeTriangles;
    }

    public void addVertex(float x, float y, float z) {
        vertexData.add(x);
        vertexData.add(y);
        vertexData.add(z);

        if (!inited) {
            minX = maxX = x;
            minY = maxY = y;
            minZ = maxZ = z;
            inited = true;
        } else {
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            minZ = Math.min(minZ, z);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            maxZ = Math.max(maxZ, z);
        }
    }

    public void addNormal(float x, float y, float z) {
        normalData.add(x);
        normalData.add(y);
        normalData.add(z);
    }

    public void addTexture(float u, float v) {
        textureData.add(u);
        textureData.add(v);
    }

    public void addIndex(short f) {
        indexData.add(f);
    }

    private Vector3 getVector3(int i){
        float x = vertexData.get(i*3+0);
        float y = vertexData.get(i*3+1);
        float z = vertexData.get(i*3+2);
        return new Vector3(x,y,z);
        
        
    }
    
    
    public void buildBuffer() {

        /* BUILD SPHERE */

        setX((minX + maxX) / 2f);
        setY((minY + maxY) / 2f);
        setZ((minZ + maxZ) / 2f);

        setRadius((float) (Math
                .sqrt((maxX - minX) * (maxX - minX) + (maxY - minY)
                        * (maxY - minY) + (maxZ - minZ) * (maxZ - minZ)) / 2d));

        /* EXTRACT TRIANGLES */
        if (explodeTriangles) {
            for (int i = 0; i < indexData.size(); i += 3) {
                
                
                
                Vector3 a = getVector3(indexData.get(i));
                Vector3 b = getVector3(indexData.get(i + 1));
                Vector3 c = getVector3(indexData.get(i + 2));
                triangles.add(new Triangle(a, b, c));
            }
        }

        /* BUILD BUFFERS */

        int vv = 0, vt = 0, vn = 0;
        vboSize = vertexData.size();
        int size = vertexData.size() + normalData.size() + textureData.size();
        vertexBuffer = ByteBuffer.allocateDirect(size * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        while (vv < vertexData.size() && vn < normalData.size()
                && vt < textureData.size()) {
            vertexBuffer.put(vertexData.get(vv++));
            vertexBuffer.put(vertexData.get(vv++));
            vertexBuffer.put(vertexData.get(vv++));
            vertexBuffer.put(normalData.get(vn++));
            vertexBuffer.put(normalData.get(vn++));
            vertexBuffer.put(normalData.get(vn++));
            vertexBuffer.put(textureData.get(vt++));
            vertexBuffer.put(textureData.get(vt++));
        }
        indexCount = indexData.size();
        vertexBuffer.position(0);

        indexBuffer = ByteBuffer.allocateDirect(indexData.size() * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(toShortArray(indexData)).position(0);

        // CLEAR
        vertexData.clear();
        normalData.clear();
        textureData.clear();
        indexData.clear();

    }

    protected static short[] toShortArray(ArrayList<Short> list) {
        short[] ret = new short[list.size()];
        for (int i = 0; i < ret.length; ++i)
            ret[i] = list.get(i);
        return ret;
    }

    /**
     * To float array.
     *
     * @param list
     *            the list
     * @return the float[]
     */
    protected static float[] toFloatArray(final ArrayList<Float> list) {
        float[] ret = new float[list.size()];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    
    /**
     * Gets the triangles.
     *
     * @return the triangles
     */
    public Iterable<Triangle> getTriangles() {
            return triangles;
    }
}
