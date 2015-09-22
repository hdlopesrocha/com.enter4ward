package com.enter4ward.lwjgl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class Game.
 */
public abstract class Game {

	// Shader variables
	/** The program. */
	private ShaderProgram program;

	/**
	 * Gets the program.
	 *
	 * @return the program
	 */
	public ShaderProgram getProgram() {
		return program;
	}

	/**
	 * Sets the program.
	 *
	 * @param program
	 *            the new program
	 */
	public void setProgram(ShaderProgram program) {
		this.program = program;
	}

	/**
	 * Instantiates a new game.
	 *
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public Game(int w, int h) {
		try {
			LibraryLoader.loadNativeLibraries();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Setup an OpenGL context with API version 3.2
		try {
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
					.withForwardCompatible(true).withProfileCore(true);

			Display.setDisplayMode(new DisplayMode(w, h));
			Display.setTitle("Game");
			Display.create(pixelFormat, contextAtrributes);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.err.println("GL_VENDOR: " + GL11.glGetString(GL11.GL_VENDOR));
		System.err
				.println("GL_RENDERER: " + GL11.glGetString(GL11.GL_RENDERER));
		System.err.println("GL_VERSION: " + GL11.glGetString(GL11.GL_VERSION));

		try {
			program = new ShaderProgram("vertex.glsl", "fragment.glsl");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup an XNA like background color
		GL11.glClearColor(0.2f, 0.2f, 0.2f, 0f);
		GL11.glViewport(0, 0, w, h);
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glEnable(GL11.GL_CULL_FACE);
		// Enable transparency
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		setup();
		System.gc();
		
		
		long time = getTime();
		long oldTime = time;
		//long i =0;
		while (!Display.isCloseRequested()) {
			// Do a single loop (logic/render)
			time = getTime();
			update((time-oldTime)/1000f);

	      //  if(i == 0)
	      //  	GL11.glAccum(GL11.GL_LOAD, 1.0f / 10);
	      //  else
	      //  	GL11.glAccum(GL11.GL_ACCUM, 1.0f / 10);
		  //  ++i;
			draw();
			// Force a maximum FPS of about 60
			Display.sync(60);
			// Let the CPU synchronize with the GPU if GPU is tagging behind
			program.use();
			
			Display.update();
			oldTime = time;
		}

		Display.destroy();

	}

	public long getTime() {
	    return System.nanoTime() / 1000000;
	}
	
	/**
	 * Use default shader.
	 */
	public void useDefaultShader() {
		program.use();
	}

	/**
	 * Update.
	 * @param deltaTime 
	 */
	public abstract void update(float deltaTime);

	/**
	 * Draw.
	 */
	public abstract void draw();

	/**
	 * Setup.
	 */
	public abstract void setup();

}
