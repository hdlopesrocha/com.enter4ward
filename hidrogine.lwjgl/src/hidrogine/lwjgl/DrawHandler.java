package hidrogine.lwjgl;

import hidrogine.math.Matrix;
import hidrogine.math.api.IObject3D;

public interface DrawHandler {

	public Matrix onDraw(IObject3D obj, Group group, Material material);


}
