package com.enter4ward.physics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.enter4ward.lwjgl.BufferObject;
import com.enter4ward.lwjgl.DrawableBox;
import com.enter4ward.lwjgl.Game;
import com.enter4ward.lwjgl.LWJGLModel3D;
import com.enter4ward.lwjgl.Object3D;
import com.enter4ward.math.BoundingBox;
import com.enter4ward.math.Camera;
import com.enter4ward.math.IBufferBuilder;
import com.enter4ward.math.IBufferObject;
import com.enter4ward.math.Matrix;
import com.enter4ward.math.ObjectCollisionHandler;
import com.enter4ward.math.Quaternion;
import com.enter4ward.math.Space;
import com.enter4ward.math.Vector3;
import com.enter4ward.math.VisibleObjectHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class TheQuadExampleMoving.
 */
public class Main extends Game implements VisibleObjectHandler, ObjectCollisionHandler {

	/** The box. */
	private DrawableBox cubeModel;
	private LWJGLModel3D carModel;
	private LWJGLModel3D boxModel;
	private LWJGLModel3D surfaceModel;	
	
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
	private static int scene = 1;

	/** The buffer builder. */
	private static IBufferBuilder bufferBuilder = new IBufferBuilder() {
		public IBufferObject build() {
			return new BufferObject(true);
		}
	};

	/**
	 * Instantiates a new the quad example moving.
	 */
	public Main() {
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
		new Main();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.lwjgl.Game#setup()
	 */
	@Override
	public void setup() {
		camera = new Camera(0.1f, 512 + 256);
		camera.update(1280, 720);
		objects = new ArrayList<MyObject3D>();
		space = new Space(16);
		cubeModel = new DrawableBox();
		try {
			carModel = new LWJGLModel3D("car.json", 1f,
					new Quaternion().createFromAxisAngle(new Vector3(1, 0, 0), (float) (-Math.PI / 2)), bufferBuilder);
			boxModel = new LWJGLModel3D("box.json", 1f, bufferBuilder);
			surfaceModel = new LWJGLModel3D("half.json", 50f, bufferBuilder);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object3D obj1 = new Object3D(new Vector3(0, 0, 0), surfaceModel) {
		};

		Random rand = new Random();

		if (scene == 0) {
			obj1.insert(space);
			for (int i = 0; i < 128; ++i) {
				objects.add((MyObject3D) new MyObject3D(new Vector3(rand.nextInt(40) - 20, 10f, rand.nextInt(40) - 20),
						boxModel) {
				}.insert(space));
			}
		}
		if (scene == 1) {

			int size = 8 * 1024;
			for (int i = 0; i < 1000000; ++i) {
				if (i % 10000 == 0)
					System.out.println(i);
				new MyObject3D(new Vector3(rand.nextInt(size) - size / 2, rand.nextInt(size) - size / 2,
						rand.nextInt(size) - size / 2), boxModel).insert(space);

			}
		}
		if (scene == 2) {

			(concreteCar = new MyCar3D(new Vector3(0, 0, 0), carModel)).insert(space);

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
	 * @see com.enter4ward.lwjgl.Game#update()
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
		System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb + " Draws:" + draws
				+ " Lens:" + Space.LENS);
		/*
		 * Display.setTitle("Used Memory:" + (runtime.totalMemory() -
		 * runtime.freeMemory()) / mb + " Draws:" + draws);
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.lwjgl.Game#draw()
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

		getProgram().setAmbientColor(0f, 0f, 0f);
		GL20.glUseProgram(0);
		// setTitle();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.enter4ward.math.ObjectCollisionHandler#onObjectCollision(java.lang.
	 * Object)
	 */
	public void onObjectCollision(Object obj) {
		if (obj instanceof MyObject3D) {
			((MyObject3D) obj).collided = true;
			((MyObject3D) obj).getVelocity().setY(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.VisibleObjectHandler#onObjectVisible(java.lang.
	 * Object)
	 */
	/** The Constant TEMP_MIN. */
	private static final Vector3 TEMP_MIN = new Vector3();

	/** The Constant TEMP_MAX. */
	private static final Vector3 TEMP_MAX = new Vector3();
	
	public void onObjectVisible(Object obj) {

		if (obj instanceof BoundingBox) {
			BoundingBox box = (BoundingBox) obj;
			getProgram().setMaterialAlpha(1f);
			getProgram().setAmbientColor(1f, 1f, 1f);
			Vector3 min = TEMP_MIN.set(box.getMinX(), box.getMinY(), box.getMinZ());
			Vector3 max = TEMP_MAX.set(box.getMaxX(), box.getMaxY(), box.getMaxZ());
			cubeModel.draw(getProgram(), min, max);
		} else if (obj instanceof Object3D) {
			Object3D obj3d = (Object3D) obj;
			obj3d.draw(getProgram(), camera);
		}
	}
}
