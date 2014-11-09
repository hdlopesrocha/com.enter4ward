#include "ShaderProgram.h"
using namespace std;

namespace hidrogine {

	ShaderProgram::ShaderProgram(string vsFilename, string fsFilename)
	{
		stackPointer = 0;
		//Read our shaders into the appropriate buffers
		string vertexSource = Utils::fileToString(vsFilename);
		string fragmentSource = Utils::fileToString(fsFilename);


		//Create an empty vertex shader handle
		GLuint vertexShader = glCreateShader(GL_VERTEX_SHADER);

		//Send the vertex shader source code to GL
		//Note that string's .c_str is NULL character terminated.
		const GLchar *source = vertexSource.c_str();
		glShaderSource(vertexShader, 1, &source, NULL);

		//Compile the vertex shader
		glCompileShader(vertexShader);
			
		GLint isCompiled = 0;
		glGetShaderiv(vertexShader, GL_COMPILE_STATUS, &isCompiled);
		if(isCompiled == GL_FALSE)
		{
			GLint maxLength = 0;
			glGetShaderiv(vertexShader, GL_INFO_LOG_LENGTH, &maxLength);
			 
			//The maxLength includes the NULL character
			vector<GLchar> infoLog(maxLength);
			glGetShaderInfoLog(vertexShader, maxLength, &maxLength, &infoLog[0]);
			 
			//We don't need the shader anymore.
			glDeleteShader(vertexShader);
			 
			//Use the infoLog as you see fit.
			cout << string(infoLog.begin(),infoLog.end()) << endl;
			//In this simple program, we'll just leave
			return;
		}
			 
		//Create an empty fragment shader handle
		GLuint fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
			 
		//Send the fragment shader source code to GL
		//Note that string's .c_str is NULL character terminated.
		source = fragmentSource.c_str();
		glShaderSource(fragmentShader, 1, &source, NULL);
			 
		//Compile the fragment shader
		glCompileShader(fragmentShader);
			 
		glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &isCompiled);
		if(isCompiled == GL_FALSE)
		{
			GLint maxLength = 0;
			glGetShaderiv(fragmentShader, GL_INFO_LOG_LENGTH, &maxLength);
			 
			//The maxLength includes the NULL character
			vector<GLchar> infoLog(maxLength);
			glGetShaderInfoLog(fragmentShader, maxLength, &maxLength, &infoLog[0]);
			 
			//We don't need the shader anymore.
			glDeleteShader(fragmentShader);
			//Either of them. Don't leak shaders.
			glDeleteShader(vertexShader);
			 
			//Use the infoLog as you see fit.
			cout << string(infoLog.begin(),infoLog.end()) << endl;
			//In this simple program, we'll just leave
			return;
		}
			 
		//Vertex and fragment shaders are successfully compiled.
		//Now time to link them together into a program.
		//Get a program object.
		program = glCreateProgram();
			 
		//Attach our shaders to our program
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
			 
		//Link our program
		glLinkProgram(program);
			 
		//Note the different functions here: glGetProgram* instead of glGetShader*.
		GLint isLinked = 0;
		glGetProgramiv(program, GL_LINK_STATUS, (int *)&isLinked);
		if(isLinked == GL_FALSE)
		{
			GLint maxLength = 0;
			glGetProgramiv(program, GL_INFO_LOG_LENGTH, &maxLength);
			 
			//The maxLength includes the NULL character
			vector<GLchar> infoLog(maxLength);
			glGetProgramInfoLog(program, maxLength, &maxLength, &infoLog[0]);
			 
			//We don't need the program anymore.
			glDeleteProgram(program);
			//Don't leak shaders either.
			glDeleteShader(vertexShader);
			glDeleteShader(fragmentShader);
			 
			//Use the infoLog as you see fit.
			cout << string(infoLog.begin(),infoLog.end()) << endl;
			//In this simple program, we'll just leave
			return;
		}
			 
		//Always detach shaders after a successful link.
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);

		glUseProgram(program);

		// Get matrices uniform locations
        projectionMatrixLocation = glGetUniformLocation(program, "projectionMatrix");
        viewMatrixLocation = glGetUniformLocation(program, "viewMatrix");
        modelMatrixLocation = glGetUniformLocation(program, "modelMatrix");
        ambientColorLocation = glGetUniformLocation(program, "ambientColor");
        timeLocation = glGetUniformLocation(program, "ftime");

        diffuseColorLocation = glGetUniformLocation(program, "diffuseColor");
        cameraPositionLocation = glGetUniformLocation(program, "cameraPosition");
        opaqueLocation = glGetUniformLocation(program, "opaque");

        // material locations
        materialShininessLocation = glGetUniformLocation(program, "materialShininess");
        materialAlphaLocation = glGetUniformLocation(program, "materialAlpha");
        materialSpecularLocation = glGetUniformLocation(program,"materialSpecular");

        for (int i = 0; i < 10; ++i) {
			lightPositionLocation[i] = glGetUniformLocation(program,("lightPosition[" + to_string(i) + "]").c_str() );
            lightSpecularColorLocation[i] = glGetUniformLocation(program, ("lightSpecularColor[" + to_string(i) + "]").c_str());
        }
        setMaterialAlpha(1.0f);

	}

	



	ShaderProgram::~ShaderProgram(void)
	{
	}

	

    void ShaderProgram::use() {
        glUseProgram(program);
    }


    void ShaderProgram::update(Camera * camera) {
		Matrix modelView = camera->getViewMatrix()* matrixStack[stackPointer];
        // Upload matrices to the uniform variables
		glUniformMatrix4fv(projectionMatrixLocation,16, false, camera->getProjectionMatrix().M);
		glUniformMatrix4fv(viewMatrixLocation,16, false, camera->getViewMatrix().M);
		glUniformMatrix4fv(modelMatrixLocation,16, false, matrixStack[stackPointer].M);


        glUniform3f(cameraPositionLocation, camera->getPosition().X, camera->getPosition().Y, camera->getPosition().Z);

        for (int i = 0; i < 10; ++i) {
            Vector3 position = lightPositions[i];
            Vector3 specularColor = lightSpecularColor[i];
            if (position != NULL) {
                glUniform3fARB(lightPositionLocation[i], position.X, position.Y, position.Z);
            }
            if (specularColor != NULL) {
                glUniform3fARB(lightSpecularColorLocation[i], specularColor.X, specularColor.Y, specularColor.Z);
            }
        }
    }

	void ShaderProgram::setMaterialAlpha(float value) {

        glUniform1fARB(materialAlphaLocation, value);
    }

    void ShaderProgram::setMaterialShininess(float value) {
        glUniform1fARB(materialShininessLocation, value);
    }

    void ShaderProgram::setTime(float value) {
        glUniform1fARB(timeLocation, value);
    }

    void ShaderProgram::setAmbientColor(float r, float g, float b) {
        glUniform3fARB(ambientColorLocation, r, g, b);
    }

    void ShaderProgram::setMaterialSpecular(float r, float g, float b) {
        glUniform3fARB(materialSpecularLocation, r, g, b);
    }

    void ShaderProgram::setDiffuseColor(float r, float g, float b) {
        glUniform3fARB(diffuseColorLocation, r, g, b);
    }

    void ShaderProgram::setLightPosition(int index, Vector3 lightPosition) {
        lightPositions[index] = lightPosition;
    }

    void ShaderProgram::setLightColor(int index, Vector3 lightColor) {
        lightSpecularColor[index] = lightColor;
    }

	void ShaderProgram::setOpaque(bool value) {
        if (value)
            glEnable(GL_CULL_FACE);
        else
            glDisable(GL_CULL_FACE);

        glUniform1i(opaqueLocation, value ? 1 : 0);
    }

    
    void ShaderProgram::pushMatrix() {
        matrixStack[stackPointer+1]= matrixStack[stackPointer];
        ++stackPointer;
    }

    void ShaderProgram::popMatrix() {
        --stackPointer;
    }

    void ShaderProgram::updateModelMatrix() {
        use();
		glUniformMatrix4fv(modelMatrixLocation,16, false, getModelMatrix().M);
    }

    void ShaderProgram::setIdentity() {
		matrixStack[stackPointer]=Matrix::Identity();
    }

  
    Matrix ShaderProgram::getModelMatrix() {
        return matrixStack[stackPointer];
    }









}