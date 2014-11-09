 varying vec3 normal;
varying vec4 pos;
varying vec2 texCoord;
varying vec4 ShadowCoord;



void main() {
  normal = gl_NormalMatrix * gl_Normal;
  gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * gl_Vertex;
  pos = gl_ModelViewMatrix * gl_Vertex;
  gl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;
  texCoord= gl_MultiTexCoord0.xy;
}
