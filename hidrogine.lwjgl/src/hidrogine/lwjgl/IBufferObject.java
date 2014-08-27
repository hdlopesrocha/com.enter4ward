package hidrogine.lwjgl;


public interface IBufferObject {

    void setMaterial(final Material f) ;
    void addPosition(final float vx, final float vy, final float vz) ;

    void addNormal(final float nx, final float ny, final float nz) ;
    
    void addTextureCoord(final float tx,
            final float ty) ;
    void addIndex(final short f);
    void buildBuffer();
    void bind(final ShaderProgram shader);
    void draw(final ShaderProgram shader);
	Material getMaterial();
}
