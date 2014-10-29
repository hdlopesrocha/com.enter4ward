package hidrogine.lwjgl;

import hidrogine.math.IObject3D;
import hidrogine.math.Matrix;

public interface DrawHandler {

	public Matrix onDraw(IObject3D obj, Group group, BufferObject buffer);


}
