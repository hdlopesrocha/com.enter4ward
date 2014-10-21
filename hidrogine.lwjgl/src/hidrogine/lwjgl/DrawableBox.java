package hidrogine.lwjgl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class DrawableBox {
    
    
    BufferObject obj = new BufferObject();
    public DrawableBox(){
        
        float [] packedVector ={0,0,0,0,0,-1,1,0,0,1,0,0,0,-1,1,1,1,1,0,0,0,-1,0,1,1,0,0,0,0,-1,0,0,0,0,1,0,0,1,0,0,1,0,1,0,0,1,1,0,1,1,1,0,0,1,1,1,0,1,1,0,0,1,0,1,0,0,0,0,-1,0,0,0,1,0,0,0,-1,0,1,0,1,0,1,0,-1,0,1,1,0,0,1,0,-1,0,0,1,1,0,0,1,0,0,0,0,1,1,0,1,0,0,1,0,1,1,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,0,0,1,0,0,0,0,1,0,0,1,0,1,0,0,1,1,0,1,0,1,1,1,1,1,0,1,0,0,1,0,1,0,-1,0,0,0,0,0,0,0,-1,0,0,1,0,0,0,1,-1,0,0,1,1,0,1,1,-1,0,0,0,1};
        short[] ii = {0,1,2,2,3,0,4,5,6,6,7,4,8,9,10,10,11,8,12,13,14,14,15,12,16,17,18,18,19,16,20,21,22,22,23,20};
        
        for(int i=0; i < packedVector.length ; i+=8){
        	obj.addPosition(packedVector[i+0], packedVector[i+1], packedVector[i+2]);

        	obj.addNormal(packedVector[i+3], packedVector[i+4], packedVector[i+5]);
        	obj.addTextureCoord(packedVector[i+6], packedVector[i+7]);

        }
        
        for(int i=0; i < ii.length ; ++i){
        	obj.addIndex(ii[i]);
        }
        
        obj.buildBuffer();
    }
 
    
    /**
     * Draw.
     *
     * @param shader
     *            the shader
     */
    public final void draw(final ShaderProgram shader,Vector3f min, Vector3f max) {
        Vector3f dim = new Vector3f();
        Vector3f.sub(max, min, dim);
        shader.pushMatrix();
        shader.getModelMatrix().translate(min);
        shader.getModelMatrix().scale(dim);
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		shader.setAmbientColor(1f, 1f, 1f);
		obj.draw(shader);
		shader.setAmbientColor(0f, 0f, 0f);
        shader.popMatrix();

    }
    
}
