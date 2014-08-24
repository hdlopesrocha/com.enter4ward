#version 330 core

attribute vec3 in_Position;
attribute vec3 in_Normal;
attribute vec2 in_TextureCoord;


uniform vec3 cameraPosition;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 ambientColor;



varying vec3 vPosition;
varying vec3 vNormal;
varying vec2 vTexCoord;

void main(void) {
	mat4 modelViewProjection = projectionMatrix * viewMatrix * modelMatrix;
	vPosition = mat3(modelMatrix)*in_Position;
	vNormal = mat3(modelMatrix)*in_Normal;
	vTexCoord = in_TextureCoord;
	gl_Position = modelViewProjection * vec4(in_Position,1.0);
}
