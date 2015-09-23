#version 330 core

in vec3 in_Position;
in vec3 in_Normal;
in vec2 in_TextureCoord;


uniform vec3 cameraPosition;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 ambientColor;
uniform float ftime;



out vec3 vPosition;
out vec3 vNormal;
out vec2 vTexCoord;

void main(void) {
	mat4 modelViewProjection = projectionMatrix * viewMatrix * modelMatrix;
	vPosition = mat3(modelMatrix)*in_Position;
	vNormal = mat3(modelMatrix)*in_Normal;
	vTexCoord = in_TextureCoord;
	gl_Position = modelViewProjection * vec4(in_Position,1.0);
}
