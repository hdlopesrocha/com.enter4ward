varying vec3 normal;
varying vec4 pos;
varying vec4 ShadowCoord;
varying float height;
uniform float inv_log_far;



void main() {
	normal = gl_NormalMatrix * gl_Normal;
	gl_TexCoord[0] = gl_MultiTexCoord0;	
 	pos = gl_ModelViewMatrix * gl_Vertex;
 	height = length(gl_Vertex);

  	gl_Position = gl_ProjectionMatrix * pos;   
    gl_Position.z = (2.0*log(gl_Position.w + 1.0) * inv_log_far - 1.0) * gl_Position.w;
}