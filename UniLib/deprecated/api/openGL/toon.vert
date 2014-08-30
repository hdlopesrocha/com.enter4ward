 varying vec3 normal;
varying vec4 pos;
varying vec4 rawpos;
varying vec2 texCoord;

void main() {
  normal = gl_NormalMatrix * gl_Normal;
  gl_Position = ftransform();
  pos = gl_ModelViewMatrix * gl_Vertex;
  rawpos = gl_Vertex;
  gl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;
  	texCoord= gl_MultiTexCoord0.xy;

}
