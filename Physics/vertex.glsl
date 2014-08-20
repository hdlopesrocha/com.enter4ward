#version 330 core

attribute vec3 in_Position;
attribute vec3 in_Normal;
attribute vec2 in_TextureCoord;


uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 light0Position;
uniform vec3 ambientColor;



varying vec3 position;
varying vec3 normal;
varying vec2 texCoord;

void main(void) {
	// === DO NOT TOUCH ===
	mat4 modelView = viewMatrix * modelMatrix;
	mat4 modelViewProjection = projectionMatrix * modelView;
	// ====================

	position =(modelView * vec4(in_Position,0.0)).xyz;
	normal = (modelView * vec4(in_Normal,0.0)).xyz;
	texCoord = in_TextureCoord;
	gl_Position = modelViewProjection * vec4(in_Position,1.0);
}
