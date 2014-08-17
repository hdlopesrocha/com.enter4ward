package hidrogine.lwjgl;



public class Material {
    public Float[] Ka;
    public Float[] Kd;
    public Float[] Ks;
    public Float Tf;
    public Float illum;
    public Float d;
    public Float Ns;
    public Float sharpness;
    public Float Ni;
    public int texture = 0;

    public Material() {

    }



    
    
    public void setTexture(String filename) {
        texture = TextureLoader.loadTexture(filename);
    }
}
