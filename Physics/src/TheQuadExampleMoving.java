import hidrogine.lwjgl.Game;
import hidrogine.lwjgl.Model3D;
import hidrogine.lwjgl.TextureLoader;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * The Class TheQuadExampleMoving.
 */
public class TheQuadExampleMoving extends Game {

    /**
     * Instantiates a new the quad example moving.
     *
     * @param width
     *            the width
     * @param height
     *            the height
     */
    public TheQuadExampleMoving() {
        super(800,600);
    }

    // Entry point for the application
    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        new TheQuadExampleMoving();
    }

    // Texture variables
    /** The tex ids. */
    private int texId;

    Model3D box;
    


    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.lwjgl.Game#setup()
     */
    @Override
    public void setup() {
        getCamera().lookAt(0, 0, 3, 0, 0, 0,0,1,0);
        texId = TextureLoader.loadTexture("stGrid1.png");       
        box = new Model3D("box.mat", "teapot.geo", 0.00175f);
        program.setLightPosition(0, new Vector4f(3, 3, 3, 0));
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.lwjgl.Game#update()
     */
    @Override
    public void update() {
        while (Keyboard.next()) {
            // Only listen to events where the key was pressed (down event)
            if (!Keyboard.getEventKeyState())
                continue;
            
            if(Keyboard.getEventKey() == Keyboard.KEY_A){
                getCamera().getViewMatrix().rotate(0.1f, new Vector3f(0,1,0));
            }
            if(Keyboard.getEventKey() == Keyboard.KEY_D){
                getCamera().getViewMatrix().rotate(-0.1f, new Vector3f(0,1,0));
            }
     
            if(Keyboard.getEventKey() == Keyboard.KEY_W){
                getCamera().getViewMatrix().rotate(-0.1f, new Vector3f(1,0,0));
            }  

            if(Keyboard.getEventKey() == Keyboard.KEY_S){
                getCamera().getViewMatrix().rotate(0.1f, new Vector3f(1,0,0));
            }  
        }

        GL20.glUseProgram(0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.lwjgl.Game#draw()
     */
    @Override
    public void draw() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        //box.draw(shader);
        useDefaultShader();

        box.draw(program);
        
        GL20.glUseProgram(0);

    }

}
