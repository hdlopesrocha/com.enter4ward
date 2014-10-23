import hidrogine.lwjgl.DrawHandler;
import hidrogine.lwjgl.Game;
import hidrogine.lwjgl.Grid;
import hidrogine.lwjgl.Group;
import hidrogine.lwjgl.Material;
import hidrogine.lwjgl.Model3D;
import hidrogine.math.Matrix;
import hidrogine.math.Space;
import hidrogine.math.BoundingSphere;
import hidrogine.math.Vector3;
import hidrogine.math.api.IModel3D;
import hidrogine.math.api.IObject3D;
import hidrogine.math.api.IBoundingSphere;
import hidrogine.math.api.IVector3;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

// TODO: Auto-generated Javadoc
/**
 * The Class TheQuadExampleMoving.
 */
public class TheQuadExampleMoving extends Game {


	
    /**
     * Instantiates a new the quad example moving.
     */
    public TheQuadExampleMoving() {
        super(1280, 720);
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

    /** The box. */
    Model3D car;
    Grid grid;
    /** The box handler. */
    DrawHandler boxHandler;

    /** The time. */
    float time = 0;

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.lwjgl.Game#setup()
     */
    @Override
    public void setup() {
    	
    	IModel3D model = new IModel3D() {
    		@Override
    		public IBoundingSphere getContainer() {
    			return new BoundingSphere(new Vector3(0f,6f,0f),46);
    		}
    	};
    	
    	IObject3D obj = new IObject3D(new Vector3(253.1f,61.1f,125.1f),model){
    	};
    	
    	Space space = new Space();
    	space.insert(obj);
    	
        camera.lookAt(0, 0, 3, 0, 0, 0);
        car = new Model3D("car.mat", "car.geo", 1f);
        grid = new Grid(32);
        program.setLightPosition(0, new Vector3(3, 3, 3));
        program.setAmbientColor(0, 0, 0);
        program.setDiffuseColor(1, 1, 1);
        program.setMaterialShininess(1000);
        program.setLightColor(0, new Vector3(1, 1, 1));

        boxHandler = new DrawHandler() {

            @Override
            public void beforeDraw(Group group, Material material) {
                if (material.getName().equals("c0")) {
                    program.setDiffuseColor(
                            (float) (Math.sin(time) + 1) / 2f,
                            (float) (Math.cos(time * Math.E / 2) + 1) / 2f,
                            (float) (Math.sin(time * Math.PI / 2)
                                    * Math.cos(time * Math.PI / 2) + 1) / 2f);
                }
                if (group.getName().startsWith("w")
                        && group.getName().length() == 2) {
                    program.pushMatrix();
                    IVector3 center = group.getCenter().multiply(-1f);
                    program.getModelMatrix().translate(center);
                    program.getModelMatrix().multiply(Matrix.createRotationX(time * 32));
                    center.multiply(-1f);
                    program.getModelMatrix().translate(center);

                }
            }

            @Override
            public void afterDraw(Group group, Material material) {
                if (group.getName().startsWith("w")
                        && group.getName().length() == 2) {
                    program.popMatrix();
                }
            }
        };
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.lwjgl.Game#update()
     */
    @Override
    public void update() {
        time += 0.003f;
        
        float sense = 0.03f;
        if (Mouse.isButtonDown(0)) {
            camera.rotate(0, 1, 0, Mouse.getDX() * sense * 0.2f);
            camera.rotate(1, 0, 0, -Mouse.getDY() * sense * 0.2f);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            camera.rotate(0, 0, 1, -sense);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            camera.rotate(0, 0, 1, sense);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            camera.move(-sense, 0, 0);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            camera.move(sense, 0, 0);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            camera.move(0, 0, -sense);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            camera.move(0, 0, sense);
        }
        Vector3 camPos = camera.getPosition();
        program.setLightPosition(0,  camPos);
       
        program.setTime(time*10);

    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.lwjgl.Game#draw()
     */
    
    @Override
    public void draw() {
    	/*
    	 float mb = 1024*1024;
        
        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();
         
                 //Print used memory
       Display.setTitle("Used Memory:"
            + (runtime.totalMemory() - runtime.freeMemory()) / mb);
    	*/
    	
    	
        program.use();
        program.setIdentity();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // box.draw(shader);
        useDefaultShader();

        program.setOpaque(true);
        grid.draw(program);
        program.pushMatrix();
        car.draw(program, boxHandler);
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
        	car.drawBoxs(program);
        }
        
        
        program.setOpaque(false);
        car.draw(program, boxHandler);
        program.popMatrix();
        GL20.glUseProgram(0);

    }

}
