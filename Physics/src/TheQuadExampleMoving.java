import hidrogine.lwjgl.DrawableBox;
import hidrogine.lwjgl.Game;
import hidrogine.lwjgl.Grid;
import hidrogine.lwjgl.Model3D;
import hidrogine.lwjgl.Object3D;
import hidrogine.math.BoundingBox;
import hidrogine.math.BoundingFrustum;
import hidrogine.math.Camera;
import hidrogine.math.ContainmentType;
import hidrogine.math.IBoundingBox;
import hidrogine.math.IBoundingSphere;
import hidrogine.math.Matrix;
import hidrogine.math.ObjectCollisionHandler;
import hidrogine.math.Space;
import hidrogine.math.Vector3;
import hidrogine.math.VisibleNodeHandler;
import hidrogine.math.VisibleObjectHandler;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

// TODO: Auto-generated Javadoc
/**
 * The Class TheQuadExampleMoving.
 */
public class TheQuadExampleMoving extends Game implements VisibleObjectHandler,
		ObjectCollisionHandler, VisibleNodeHandler {

	/** The Constant IDENTITY. */
	private static final Matrix IDENTITY = new Matrix().identity();

	/** The box. */
	private DrawableBox box;

	/** The moving. */
	private MyObject3D moving;

	/** The concrete car. */
	private MyCar3D concreteCar;
	/** The camera. */
	private Camera camera;
	/** The grid. */
	private Grid grid;

	/** The space. */
	private Space space;

	/** The time. */
	private float time = 0;

	/** The draws. */
	public static int draws = 0;

	/** The frustum. */
	private BoundingFrustum frustum;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.Game#setup()
	 */
	@Override
	public void setup() {
		camera = new Camera(1280, 720);

		space = new Space();
		box = new DrawableBox();
		/** The box. */
		Model3D car = new Model3D("car.mat", "car.geo", 1f, true);
		Model3D box = new Model3D("box.mat", "box.geo", 1f, true);
		Model3D surface = new Model3D("surface.mat", "surface.geo", 1f, true);

		(new MyObject3D(new Vector3(0, -2, 0), surface) {
		}).insert(space);

		(concreteCar = new MyCar3D(new Vector3(0, 0, 0), car) {
		}).insert(space);

		(new MyObject3D(new Vector3(-10, 0, 0), box) {
		}).insert(space);

		(moving = new MyObject3D(new Vector3(), box) {
		}).insert(space);

		camera.lookAt(0, 6, 24, 0, 0, 0);
		grid = new Grid(32);
		getProgram().setLightPosition(0, new Vector3(3, 3, 3));
		getProgram().setAmbientColor(0, 0, 0);
		getProgram().setDiffuseColor(1, 1, 1);
		getProgram().setMaterialShininess(1000);
		getProgram().setLightColor(0, new Vector3(1, 1, 1));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.Game#update()
	 */
	@Override
	public void update() {
		time += 0.003f;
		concreteCar.addTime(0.003f);

		// / moving.remove();
		moving.setPosition(new Vector3((float) (10 * Math.cos(time * 4)), 0f,
				(float) (10 * Math.sin(time * 4))));
		moving.getRotation().createFromAxisAngle(new Vector3(0, 1, 0),
				(float) -(Math.PI + time * 4));
		moving.update(space);
		space.handleObjectCollisions(moving, this);

		// moving.insert(space);
		float sense = 0.06f;
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
		getProgram().setLightPosition(0, camPos);
		getProgram().setTime(time * 10);
		getProgram().update(camera);
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
				+ (runtime.totalMemory() - runtime.freeMemory()) / mb
				+ " Draws:" + draws);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.Game#draw()
	 */

	@Override
	public void draw() {
		frustum = camera.getBoundingFrustum();

		draws = 0;
		getProgram().setModelMatrix(IDENTITY);
		getProgram().use();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		useDefaultShader();

		// grid.draw(program);
		getProgram().setOpaque(true);
		space.handleVisibleObjects(frustum, this);
		getProgram().setOpaque(false);
		space.handleVisibleObjects(frustum, this);

		space.handleVisibleNodes(frustum, this);
		getProgram().setMaterialAlpha(1f);
		getProgram().setAmbientColor(0f, 0f, 0f);
		GL20.glUseProgram(0);
		setTitle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.math.VisibleObjectHandler#onObjectVisible(hidrogine.math.
	 * IBoundingSphere)
	 */
	@Override
	public void onObjectVisible(IBoundingSphere obj) {
		Object3D obj3d = (Object3D) obj;
		// Model3D model = (Model3D) obj3d.getModel();

		// model.draw(obj3d, getProgram(), TheQuadExampleMoving.this);
		obj3d.draw(getProgram(), frustum);
		// model.drawBoxs(program);
		Vector3 dim = new Vector3(obj3d.getRadius());
		box.draw(getProgram(), new Vector3(obj3d.getCenter()).subtract(dim),
				new Vector3(obj3d.getCenter()).add(dim));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hidrogine.math.ObjectCollisionHandler#onObjectCollision(hidrogine.math
	 * .IBoundingSphere, hidrogine.math.IBoundingSphere)
	 */
	@Override
	public void onObjectCollision(IBoundingSphere obj1, IBoundingSphere obj2) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hidrogine.math.VisibleNodeHandler#onNodeVisible(hidrogine.math.IBoundingBox
	 * , int)
	 */
	@Override
	public void onNodeVisible(IBoundingBox obj, int storedObjectsCount) {
		if (storedObjectsCount > 0) {
			getProgram().setAmbientColor(0f, 0f, 1f);
			getProgram().setMaterialAlpha(.5f);
		} else {
			ContainmentType ct = frustum.contains((BoundingBox) obj);
			if (ct == ContainmentType.Contains) {
				getProgram().setAmbientColor(0f, 1f, 0f);
			} else {
				getProgram().setAmbientColor(1f, 1f, 1f);
			}

			getProgram().setMaterialAlpha(.2f);
		}
		box.draw(getProgram(), obj.getMin(), obj.getMax());
	}
}
