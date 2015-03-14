import hidrogine.lwjgl.BufferObject;
import hidrogine.lwjgl.DrawableBox;
import hidrogine.lwjgl.Game;
import hidrogine.lwjgl.LWJGLModel3D;
import hidrogine.lwjgl.Object3D;
import hidrogine.math.BoundingBox;
import hidrogine.math.Camera;
import hidrogine.math.IBufferBuilder;
import hidrogine.math.IBufferObject;
import hidrogine.math.Matrix;
import hidrogine.math.ObjectCollisionHandler;
import hidrogine.math.Quaternion;
import hidrogine.math.Space;
import hidrogine.math.Vector3;
import hidrogine.math.VisibleNodeHandler;
import hidrogine.math.VisibleObjectHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

// TODO: Auto-generated Javadoc
/**
 * The Class TheQuadExampleMoving.
 */
public class TheQuadExampleMoving extends Game implements VisibleObjectHandler,
		ObjectCollisionHandler, VisibleNodeHandler {

	/** The box. */
	private DrawableBox box;

	/** The concrete car. */
	private static MyCar3D concreteCar;
	/** The camera. */
	private Camera camera;

	/** The objects. */
	private List<MyObject3D> objects;

	/** The space. */
	private Space space;

	/** The time. */
	private float time = 0;

	/** The draws. */
	public static int draws = 0;

	/** The scene. */
	private static int scene = 2;

	/** The buffer builder. */
	private static IBufferBuilder bufferBuilder = new IBufferBuilder() {

		@Override
		public IBufferObject build() {
			return new BufferObject(true);
		}
	};

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
	 *          the arguments
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
		camera = new Camera(0.1f, 256);
		camera.update(1280, 720);
		objects = new ArrayList<MyObject3D>();
		space = new Space(16);
		box = new DrawableBox();
		/** The box. */
		LWJGLModel3D car = null, box = null, surface = null;
		try {
			car = new LWJGLModel3D("car.json", 1f,
					new Quaternion().createFromAxisAngle(new Vector3(1, 0, 0),
							(float) (-Math.PI / 2)), bufferBuilder);
			box = new LWJGLModel3D("box.json", 1f, bufferBuilder);
			surface = new LWJGLModel3D("half.json", 50f, bufferBuilder);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object3D obj1 = new Object3D(new Vector3(0, 0, 0), surface) {
		};

		Random rand = new Random();

		if (scene == 0) {
			obj1.insert(space);
			for (int i = 0; i < 128; ++i) {
				objects.add((MyObject3D) new MyObject3D(new Vector3(
						rand.nextInt(40) - 20, 10f, rand.nextInt(40) - 20), box) {
				}.insert(space));
			}
		}
		if (scene == 1) {

			int size = 8 * 1024;
			for (int i = 0; i < 1000000; ++i) {
				if (i % 10000 == 0)
					System.out.println(i);
				new MyObject3D(new Vector3(rand.nextInt(size) - size / 2,
						rand.nextInt(size) - size / 2, rand.nextInt(size) - size / 2), box)
						.insert(space);

			}
		}
		if (scene == 2) {

			(concreteCar = new MyCar3D(new Vector3(0, 0, 0), car)).insert(space);

		}

		camera.lookAt(new Vector3(0, 6, 32), new Vector3(), new Vector3(0, 1, 0));
		getProgram().setLightPosition(0, new Vector3(3, 3, 3));
		getProgram().setAmbientColor(0, 0, 0);
		getProgram().setDiffuseColor(1, 1, 1);
		getProgram().setMaterialShininess(1000);
		getProgram().setLightColor(0, new Vector3(1, 1, 1));
		getProgram().setLightPosition(0, new Vector3(128, 128, 128));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.Game#update()
	 */
	@Override
	public void update(float deltaTime) {
		time += deltaTime;
		if (scene == 2) {
			concreteCar.update(deltaTime, space);
		}

		for (MyObject3D o : objects) {
			o.update(deltaTime, space);
		}

		// space.handleObjectCollisions(moving, this);

		// moving.insert(space);
		float sense = 0.2f;
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
		getProgram().setTime(time);
		getProgram().update(camera);
	}

	/**
	 * Sets the title.
	 */
	public void setTitle() {
		float mb = 1024 * 1024;
		// System.gc();
		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		// Print used memory
		System.out.println("Used Memory:"
				+ (runtime.totalMemory() - runtime.freeMemory()) / mb + " Draws:"
				+ draws + " Lens:" + Space.LENS);
		/*
		 * Display.setTitle("Used Memory:" + (runtime.totalMemory() -
		 * runtime.freeMemory()) / mb + " Draws:" + draws);
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.Game#draw()
	 */

	@Override
	public void draw() {
		camera.update();

		draws = 0;
		getProgram().setModelMatrix(Matrix.IDENTITY);
		getProgram().use();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		useDefaultShader();

		// grid.draw(program);
		getProgram().setOpaque(true);
		space.handleVisibleObjects(camera, this);
		getProgram().setOpaque(false);
		space.handleVisibleObjects(camera, this);

		space.handleVisibleNodes(camera, this);
		getProgram().setAmbientColor(0f, 0f, 0f);
		GL20.glUseProgram(0);
		setTitle();
	}

	/** The Constant TEMP_MIN. */
	private static final Vector3 TEMP_MIN = new Vector3();

	/** The Constant TEMP_MAX. */
	private static final Vector3 TEMP_MAX = new Vector3();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hidrogine.math.VisibleNodeHandler#onNodeVisible(hidrogine.math.BoundingBox
	 * , int)
	 */
	@Override
	public void onNodeVisible(BoundingBox obj, int storedObjectsCount) {
		if (storedObjectsCount > 0) {
			getProgram().setMaterialAlpha(1f);
			getProgram().setAmbientColor(1f, 1f, 1f);
			Vector3 min = TEMP_MIN.set(obj.getMinX(), obj.getMinY(), obj.getMinZ());
			Vector3 max = TEMP_MAX.set(obj.getMaxX(), obj.getMaxY(), obj.getMaxZ());

			box.draw(getProgram(), min, max);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hidrogine.math.ObjectCollisionHandler#onObjectCollision(java.lang.Object)
	 */
	@Override
	public void onObjectCollision(Object obj) {
		if (obj instanceof MyObject3D) {
			((MyObject3D) obj).collided = true;
			((MyObject3D) obj).getVelocity().setY(0);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.math.VisibleObjectHandler#onObjectVisible(java.lang.Object)
	 */
	@Override
	public void onObjectVisible(Object obj) {
		// TODO Auto-generated method stub
		Object3D obj3d = (Object3D) obj;
		obj3d.draw(getProgram(), camera);
	}
}
