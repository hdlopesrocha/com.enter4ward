import hidrogine.lwjgl.DrawableBox;
import hidrogine.lwjgl.DrawableSphere;
import hidrogine.lwjgl.Game;
import hidrogine.lwjgl.Model3D;
import hidrogine.lwjgl.Object3D;
import hidrogine.math.BoundingBox;
import hidrogine.math.BoundingFrustum;
import hidrogine.math.Camera;
import hidrogine.math.ContainmentType;
import hidrogine.math.BoundingBox;
import hidrogine.math.Matrix;
import hidrogine.math.ObjectCollisionHandler;
import hidrogine.math.Space;
import hidrogine.math.Vector3;
import hidrogine.math.VisibleNodeHandler;
import hidrogine.math.VisibleObjectHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


	/** The box. */
	private DrawableSphere sphere;
	private DrawableBox box;

	/** The moving. */
	private MyObject3D moving;

	/** The concrete car. */
	private MyCar3D concreteCar;
	/** The camera. */
	private Camera camera;

	private List<MyObject3D> objects;

	/** The space. */
	private Space space;

	/** The time. */
	private float time = 0;

	/** The draws. */
	public static int draws = 0;


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
		space = new Space();
		sphere = new DrawableSphere();
		box = new DrawableBox();
		/** The box. */
		Model3D car = new Model3D("car.mat", "car.geo", 1f, true);
		Model3D box = new Model3D("box.mat", "box.geo", 1f, true);
		// Model3D surface = new Model3D("surface.mat", "surface.geo", 2f, true);
		Model3D surface = new Model3D("surface.mat", "half.geo", 50f, true);

		Object3D obj1 = new Object3D(new Vector3(0, 0, 0), surface) {
		};

		/*
		 * moving = new MyObject3D(new Vector3(0, 0, 0), box) { };
		 */
		//obj1.insert(space);
		/*
		 * moving.insert(space);
		 */
		// new MyCar3D(new Vector3(-4, 4, 25), car) {}.insert(space);

		Random rand = new Random();
/*		
		  for (int i = 0; i < 128; ++i) { objects.add((MyObject3D) new
		  MyObject3D(new Vector3( rand.nextInt(40) - 20, 10f, rand.nextInt(40) -
		  20), box) { }.insert(space)); }
	*/	 
		
		
		
		int size = 8 * 1024;
		for (int i = 0; i < 1000000; ++i) {
			if(i%10000==0)
				System.out.println(i);
			new MyObject3D(new Vector3(rand.nextInt(size) - size / 2,
					rand.nextInt(size) - size / 2, rand.nextInt(size) - size / 2), box)
					.insert(space);
			
			
		}

		// objects.add((MyObject3D) new MyObject3D(new Vector3(-10.1f, 64, 0), box)
		// {}.insert(space));
		// objects.add((MyObject3D) new MyObject3D(new Vector3(-10, 68, 0), box)
		// {}.insert(space));
		// objects.add((MyObject3D) new MyObject3D(new Vector3(-10.1f, 72, 0), box)
		// {}.insert(space));
		// objects.add((MyObject3D) new MyObject3D(new Vector3(-10, 76, 0), box)
		// {}.insert(space));
		// objects.add((MyObject3D) new MyObject3D(new Vector3(-10.1f, 80, 0), box)
		// {}.insert(space));
		// objects.add((MyObject3D) new MyObject3D(new Vector3(-10, 4000, 0), box)
		// {}.insert(space));

		// objects.add(moving);

		/*
		 * (concreteCar = new MyCar3D(new Vector3(0, 0, 0), car) {}).insert(space);
		 * 
		 * concreteCar.getRotation().set( new Quaternion().createFromAxisAngle(new
		 * Vector3(1, 0, 0), (float) (-Math.PI / 2)));
		 */
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
		/*
		 * concreteCar .getRotation() .multiply( new
		 * Quaternion().createFromAxisAngle(new Vector3(0, 0, 1),
		 * -deltaTime)).normalize();
		 * 
		 * // / moving.remove(); moving.getPosition().setX((float) (10 *
		 * Math.cos(time))); moving.getPosition().setZ((float) (10 *
		 * Math.sin(time))); moving.getRotation().createFromAxisAngle(new Vector3(0,
		 * 1, 0), (float) -(Math.PI + time));
		 * 
		 * concreteCar.update(deltaTime, space);
		 */

		for (MyObject3D o : objects) {
			o.update(deltaTime, space);
		}

		// space.handleObjectCollisions(moving, this);

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
		getProgram().setTime(time);
		getProgram().update(camera);
	}

	/**
	 * Sets the title.
	 */
	public void setTitle() {
		float mb = 1024 * 1024;
	//	System.gc();
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
		getProgram().setMaterialAlpha(1f);
		getProgram().setAmbientColor(0f, 0f, 0f);
		GL20.glUseProgram(0);
		setTitle();
	}

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
			getProgram().setAmbientColor(0f, 0f, 1f);
		} else {
			ContainmentType ct = camera.contains((BoundingBox) obj);
			if (ct == ContainmentType.Contains) {
				getProgram().setAmbientColor(0f, 1f, 0f);
			} else {
				getProgram().setAmbientColor(1f, 1f, 1f);
			}
		}

		Vector3 min = Vector3.temp().set(obj.getMinX(), obj.getMinY(), obj.getMinZ());
		Vector3 max = Vector3.temp().set(obj.getMaxX(), obj.getMaxY(), obj.getMaxZ());

		box.draw(getProgram(), min, max);
	}

	@Override
	public void onObjectCollision(Object obj) {
		if (obj instanceof MyObject3D) {
			((MyObject3D) obj).collided = true;
			((MyObject3D) obj).getVelocity().setY(0);

		}
	}

	@Override
	public void onObjectVisible(Object obj) {
		// TODO Auto-generated method stub
		Object3D obj3d = (Object3D) obj;
		obj3d.draw(getProgram(), camera);
		// sphere.draw(getProgram(), obj3d);
	}
}
