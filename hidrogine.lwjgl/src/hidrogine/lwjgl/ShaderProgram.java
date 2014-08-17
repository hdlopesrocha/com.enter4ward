package hidrogine.lwjgl;


import java.util.Stack;


public class ShaderProgram {
    private float[] tempMatrix = new float[16];

    private int mProgramHandle;
    private float[] mModelMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];
    private float[] mTemporaryMatrix = new float[16];
    private float[] mLightModelMatrix = new float[16];
    private int mMVPMatrixHandle;
    private int mMVMatrixHandle;
    private int mLightPosHandle;
    private int mTextureUniformHandle;
    public int mPositionHandle;
    public int mNormalHandle;
    public int mTextureCoordinateHandle;
    private int mAmbientColor;
    private int mDiffuseColor;

    private final float[] mLightPosInModelSpace = new float[]{10.0f, 10.0f, 10.0f, 1.0f};
    private final float[] mLightPosInWorldSpace = new float[4];
    private final float[] mLightPosInEyeSpace = new float[4];
    Stack<float[]> matrixStack = new Stack<float[]>();

/*
    public ShaderProgram(Context contexts) {
        final String vertexShader = RawResourceReader.readTextFileFromRawResource(contexts, R.raw.vertex);
        final String fragmentShader = RawResourceReader.readTextFileFromRawResource(contexts, R.raw.fragment);

        final int vertexShaderHandle = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        mProgramHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                new String[]{"a_Position", "a_Normal", "a_TexCoordinate"});

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_MVPMatrix");
        mMVMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_MVMatrix");
        mLightPosHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_LightPos");
        mAmbientColor = GLES20.glGetUniformLocation(mProgramHandle, "u_AmbientColor");
        mDiffuseColor = GLES20.glGetUniformLocation(mProgramHandle, "u_DiffuseColor");

        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_Texture");
        mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Position");
        mNormalHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Normal");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_TexCoordinate");
        GLES20.glUseProgram(mProgramHandle);

        setDefaultColors();
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glFrontFace(GLES20.GL_CCW);
        GLES20.glLineWidth(2);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LESS);
        GLES20.glEnable(GLES20.GL_BLEND); // Critical for alpha-ing out the appropriate bits
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public int getProgram() {
        return mProgramHandle;
    }

    public void setDefaultColors() {
        setAmbientColor(0.0f, 0.0f, 0.0f, 0.0f);
        setDiffuseColor(1.0f, 1.0f, 1.0f, 1.0f);
    }


    public void applyCamera(Camera cam) {
        cam.apllyTo(mViewMatrix);
    }

    public void updateViewPort(int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
       // final float left = -ratio;
       // final float right = ratio;
       // final float bottom = -1.0f;
       // final float top = 1.0f;
       // final float near = 1.0f;
       // final float far = 1000.0f;
        Matrix.perspectiveM(mProjectionMatrix, 0, 45, ratio, 1.0f, 100f);
        //  Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public void use() {
        GLES20.glUseProgram(mProgramHandle);

        Matrix.setIdentityM(mLightModelMatrix, 0);
        Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, -1.0f);
        Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, mViewMatrix, 0, mLightPosInWorldSpace, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);
        Matrix.multiplyMM(mTemporaryMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        System.arraycopy(mTemporaryMatrix, 0, mMVPMatrix, 0, 16);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glUniform3f(mLightPosHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);

    }

    public void bindTexture(int tex) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex);
        GLES20.glUniform1i(mTextureUniformHandle, 0);
    }


    public void pushMatrix() {
        float[] copy = new float[16];
        System.arraycopy(mModelMatrix, 0, copy, 0, 16);
        matrixStack.push(copy);
    }

    public void popMatrix() {
        if (matrixStack.size() > 0) {
            mModelMatrix = matrixStack.pop();

        }
    }

    public void setIdentity() {
        Matrix.setIdentityM(mModelMatrix, 0);
    }

    public void scale(float s) {
        Matrix.scaleM(tempMatrix, 0, mModelMatrix, 0, s, s, s);
        float[] temp = mModelMatrix;
        mModelMatrix = tempMatrix;
        tempMatrix = temp;
    }

    public void scale(float x, float y, float z) {
        Matrix.scaleM(tempMatrix, 0, mModelMatrix, 0, x, y, z);
        float[] temp = mModelMatrix;
        mModelMatrix = tempMatrix;
        tempMatrix = temp;
    }


    public void translate(float x, float y, float z) {
        Matrix.translateM(tempMatrix, 0, mModelMatrix, 0, x, y, z);
        float[] temp = mModelMatrix;
        mModelMatrix = tempMatrix;
        tempMatrix = temp;
    }

    public void rotate(float a, float x, float y, float z) {
        Matrix.rotateM(tempMatrix, 0, mModelMatrix, 0, a, x, y, z);
        float[] temp = mModelMatrix;
        mModelMatrix = tempMatrix;
        tempMatrix = temp;
    }


    public void setDiffuseColor(float r, float g, float b, float a) {
        GLES20.glUniform4f(mDiffuseColor, r, g, b, a);

    }

    public void setAmbientColor(float r, float g, float b, float a) {
        GLES20.glUniform4f(mAmbientColor, r, g, b, a);

    }
*/
}
