package hidrogine.lwjgl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

/**
 * The Class Game.
 */
public abstract class Game {



    // Shader variables
    protected ShaderProgram program;




 

    /** The width. */
    private int width;

    /**
     * Gets the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /** The height. */
    private int height;

    /**
     * Instantiates a new game.
     *
     * @param width
     *            the width
     * @param height
     *            the height
     */
    public Game(int width, int height) {
        try {
            LibraryLoader.loadNativeLibraries();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.width = width;
        this.height = height;
        setupOpenGL();
        program = new ShaderProgram("vertex.glsl", "fragment.glsl", width, height);
        setup();
        while (!Display.isCloseRequested()) {
            // Do a single loop (logic/render)
            update();
            program.update();
            draw();
            // Force a maximum FPS of about 60
            Display.sync(60);
            // Let the CPU synchronize with the GPU if GPU is tagging behind
            Display.update();
        }

        Display.destroy();

    }


    /**
     * Setup open gl.
     */
    private void setupOpenGL() {
        // Setup an OpenGL context with API version 3.2
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                    .withForwardCompatible(true).withProfileCore(true);

            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle("Game");
            Display.create(pixelFormat, contextAtrributes);

            GL11.glViewport(0, 0, width, height);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, width, height);

        this.exitOnGLError("setupOpenGL");
    }

    /**
     * Exit on gl error.
     *
     * @param errorMessage
     *            the error message
     */
    protected void exitOnGLError(String errorMessage) {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println("ERROR - " + errorMessage + ": " + errorString);

            if (Display.isCreated())
                Display.destroy();
            System.exit(-1);
        }
    }

   
    /**
     * Use default shader.
     */
    public void useDefaultShader() {
        program.use();
    }

   
 




    /**
     * Update.
     */
    public abstract void update();

    /**
     * Draw.
     */
    public abstract void draw();

    /**
     * Setup.
     */
    public abstract void setup();

}
