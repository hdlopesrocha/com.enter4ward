uniform mat4 depthMVP;

void main() {

 	vec4 pos = gl_ModelViewMatrix * gl_Vertex;
	gl_Position =  depthMVP * vec4(pos.xyz,1);

}
