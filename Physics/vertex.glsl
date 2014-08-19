#version 330 core

attribute vec3 in_Position;
attribute vec3 in_Normal;
attribute vec2 in_TextureCoord;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec4 light0Position;



varying vec3 position;
varying vec3 normal;
varying vec2 texCoord;

void main(void) {
	mat4 modelViewMatrix = viewMatrix * modelMatrix;

	position =(modelViewMatrix * vec4(in_Position,1.0)).xyz;
	normal = in_Normal;
	texCoord = in_TextureCoord;
	// Override gl_Position with our new calculated position
	gl_Position = projectionMatrix * modelViewMatrix * vec4(in_Position,1.0);
	
	

}
