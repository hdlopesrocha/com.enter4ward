import hidrogine.lwjgl.Model3D;
import hidrogine.lwjgl.Object3D;
import hidrogine.math.IVector3;
import hidrogine.math.Matrix;


public class MyObject3D extends Object3D {

	
	private static final Matrix IDENTITY = new Matrix().identity();

	public MyObject3D(IVector3 position, Model3D model) {
		super(position, model);
		// TODO Auto-generated constructor stub
	}

}
