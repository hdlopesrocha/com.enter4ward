package hidrogine.lwjgl;
public class VertexData {
        // Vertex data
        private float[] position= new float[] {0f, 0f, 0f};
        private float[] normal = new float[] {0f, 1f, 0f};
        private float[] st = new float[] {0f, 0f};
        
        // The amount of bytes an element has
        public static final int elementBytes = 4;
        
        // Elements per parameter
        public static final int positionElementCount = 3;
        public static final int normalElementCount = 3;
        public static final int textureElementCount = 2;
        
        // Bytes per parameter
        public static final int positionBytesCount = positionElementCount * elementBytes;
        public static final int normalByteCount = normalElementCount * elementBytes;
        public static final int textureByteCount = textureElementCount * elementBytes;
        
        // Byte offsets per parameter
        public static final int positionByteOffset = 0;
        public static final int normalByteOffset = positionByteOffset + positionBytesCount;
        public static final int textureByteOffset = normalByteOffset + normalByteCount;
        
        // The amount of elements that a vertex has
        public static final int elementCount = positionElementCount + 
                        normalElementCount + textureElementCount;        
        // The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
        public static final int stride = positionBytesCount + normalByteCount + 
                        textureByteCount;
        
        // Setters
        public void setPosition(float x, float y, float z) {
            this.position = new float[] {x, y, z};
        }
        
    
        
        public void setST(float s, float t) {
                this.st = new float[] {s, t};
        }
     
        public void setNormal(float r, float g, float b) {
                this.normal = new float[] {r, g, b};
        }
        
        // Getters      
        public float[] getElements() {
                float[] out = new float[VertexData.elementCount];
                int i = 0;
                
                // Insert XYZW elements
                out[i++] = this.position[0];
                out[i++] = this.position[1];
                out[i++] = this.position[2];
                // Insert RGBA elements
                out[i++] = this.normal[0];
                out[i++] = this.normal[1];
                out[i++] = this.normal[2];
                // Insert ST elements
                out[i++] = this.st[0];
                out[i++] = this.st[1];
                
                return out;
        }
 
   
}
