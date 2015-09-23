package com.enter4ward.lwjgl;

import com.enter4ward.math.Group;
import com.enter4ward.math.IObject3D;
import com.enter4ward.math.Matrix;

public interface DrawHandler {

	public Matrix onDraw(IObject3D obj, Group group, BufferObject buffer);


}
