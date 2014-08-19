#version 330 core

uniform sampler2D texture_diffuse;
uniform vec4 light0Position;

varying vec3 position;
varying vec3 normal;
varying vec2 texCoord;


void main(void) {
	vec3 lightDir = normalize(light0Position.xyz - position);
	vec3 normal2 = normalize(normal);


	// Override out_Color with our texture pixel
	gl_FragColor = texture2D(texture_diffuse, texCoord);
	gl_FragColor.xyz *=  max(dot(normal2, lightDir), 0.0);	
	//gl_FragColor.xyz = (normal+vec3(1.0,1.0,1.0))/2.0;
	//gl_FragColor.xyz = (position+vec3(1.0,1.0,1.0))/2.0;
	
}
