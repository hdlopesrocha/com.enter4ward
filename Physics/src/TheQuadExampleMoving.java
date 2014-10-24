import hidrogine.lwjgl.DrawHandler;
import hidrogine.lwjgl.Game;
import hidrogine.lwjgl.Grid;
import hidrogine.lwjgl.Group;
import hidrogine.lwjgl.Material;
import hidrogine.lwjgl.Model3D;
import hidrogine.math.BoundingFrustum;
import hidrogine.math.IteratorHandler;
import hidrogine.math.Matrix;
import hidrogine.math.Space;
import hidrogine.math.Vector3;
import hidrogine.math.api.IObject3D;
import hidrogine.math.api.IVector3;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

// TODO: Auto-generated Javadoc
/**
 * The Class TheQuadExampleMoving.
 */
public class TheQuadExampleMoving extends Game {
	public static final Matrix IDENTITY = new Matrix().identity(); 
	public static final Matrix ROTATION = new Matrix(); 
	public static final Matrix TRANSLATION = new Matrix(); 

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



	/** The grid. */
	Grid grid;

	/** The space. */
	Space space;

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
		space = new Space();

		/** The box. */
		Model3D car = new Model3D("car.mat", "car.geo", 1f);

		IObject3D obj = new IObject3D(new Vector3(253.1f, 61.1f, 125.1f), car) {
		};

		space.insert(obj);

		camera.lookAt(0, 0, 3, 0, 0, 0);
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
					IVector3 center = new Vector3(group.getCenter()).multiply(-1f);
					Matrix matrix = TRANSLATION.createTranslation(center).multiply(ROTATION.createRotationX(time * 32));
					center.multiply(-1f);
					matrix.translate(center);
					program.setModelMatrix(matrix);
				}
			}
			
			@Override
			public void afterDraw(Group group, Material material) {
				if (group.getName().startsWith("w")
						&& group.getName().length() == 2) {
					program.setModelMatrix(IDENTITY);
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
			camera.move(sense, 0, 0);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.move(-sense, 0, 0);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.move(0, 0, sense);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.move(0, 0, -sense);
		}
		Vector3 camPos = camera.getPosition();
		program.setLightPosition(0, camPos);

		program.setTime(time * 10);

	}

	/**
	 * Sets the title.
	 */
	public void setTitle() {
		float mb = 1024 * 1024;

		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		// Print used memory
		Display.setTitle("Used Memory:"
				+ (runtime.totalMemory() - runtime.freeMemory()) / mb);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.Game#draw()
	 */

	int draws=0;
	@Override
	public void draw() {
		BoundingFrustum frustum = camera.getBoundingFrustum();
		//System.out.println(frustum.toString());
		draws =0;
		final IteratorHandler<IObject3D> iterator = new IteratorHandler<IObject3D>() {
			@Override
			public void handle(IObject3D obj) {
				Model3D model = (Model3D) obj.getModel();
				model.draw(program, boxHandler);
				++draws;
			}
		};

		program.use();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		useDefaultShader();
		program.setOpaque(true);
		grid.draw(program);
		space.iterate(frustum,iterator);
		program.setOpaque(false);
		space.iterate(iterator);
		GL20.glUseProgram(0);
//	System.out.println("draws:"+draws);
	}

}
