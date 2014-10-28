import hidrogine.lwjgl.DrawHandler;
import hidrogine.lwjgl.DrawableBox;
import hidrogine.lwjgl.Game;
import hidrogine.lwjgl.Grid;
import hidrogine.lwjgl.Group;
import hidrogine.lwjgl.Material;
import hidrogine.lwjgl.Model3D;
import hidrogine.math.BoundingBox;
import hidrogine.math.BoundingFrustum;
import hidrogine.math.Camera;
import hidrogine.math.ContainmentType;
import hidrogine.math.IBoundingBox;
import hidrogine.math.IBoundingSphere;
import hidrogine.math.IObject3D;
import hidrogine.math.IVector3;
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

/**
 * The Class TheQuadExampleMoving.
 */
public class TheQuadExampleMoving extends Game implements DrawHandler,
		VisibleObjectHandler, ObjectCollisionHandler, VisibleNodeHandler {
	public static final Matrix IDENTITY = new Matrix().identity();
	public static final Matrix ROTATION = new Matrix();
	public static final Matrix TRANSLATION = new Matrix();
	public DrawableBox box;
	public IObject3D moving;
	public Camera camera;
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

	/** The time. */
	float time = 0;
	
	IObject3D hitedObject;

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
		//Model3D car = new Model3D("car.mat", "car.geo", 1f);
	    Model3D box = new Model3D("box.mat", "box.geo", 1f);

		(new IObject3D(new Vector3(0, 0, 0), box) {
		}).insert(space);
	
		(new IObject3D(new Vector3(-10, 0, 0), box) {
		}).insert(space);
		
		(moving = new IObject3D(new Vector3(), box) {
		}).insert(space);

		camera.lookAt(0, 0, 48, 0, 0, 0);
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
		// / moving.remove();
		moving.setPosition(new Vector3((float) (10 * Math.cos(time * 4)),
				(float) (10 * Math.sin(time * 4)), 0f));
		moving.update(space);
		hitedObject = null;
		space.handleObjectCollisions(moving,this);
		
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

	int draws = 0;
	BoundingFrustum frustum;
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

	@Override
	public Matrix onDraw(IObject3D obj, Group group, Material material) {
		Matrix matrix = TRANSLATION.identity();

		if(obj.equals(hitedObject)){
			getProgram().setDiffuseColor(1,0,0);
		}else {
			getProgram().setDiffuseColor(1,1,1);

		}
		
		
		if (material.getName().equals("c0")) {
			getProgram().setDiffuseColor(
					(float) (Math.sin(time) + 1) / 2f,
					(float) (Math.cos(time * Math.E / 2) + 1) / 2f,
					(float) (Math.sin(time * Math.PI / 2)
							* Math.cos(time * Math.PI / 2) + 1) / 2f);
		}
		if (obj.equals(moving)) {
			if (group.getName().startsWith("w")
					&& group.getName().length() == 2) {
				IVector3 center = new Vector3(group.getCenter()).multiply(-1f);
				matrix.translate(center).multiply(
						ROTATION.createRotationX(time * 48));
				center.multiply(-1f);
				matrix.translate(center);
			}
			matrix.multiply(new Matrix()
					.createRotationZ((float) (Math.PI + time * 4)));
		}
		return matrix.translate(obj.getPosition());
	}

	@Override
	public void onObjectVisible(IObject3D obj) {
		draws++;
		Model3D model = (Model3D) obj.getModel();
		model.draw(obj, getProgram(), TheQuadExampleMoving.this);
		// model.drawBoxs(program);
	}

	@Override
	public void onObjectCollision(IBoundingSphere obj1, IBoundingSphere obj2) {
		hitedObject = (IObject3D) obj2;
	}

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
