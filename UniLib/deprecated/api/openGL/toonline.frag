uniform sampler2D tex;

varying vec2 texCoord;
 
varying vec3 normal;
varying vec4 pos;

void main() {
  gl_FragColor = texture2D(tex, texCoord);
  gl_FragColor.xyz *= 0.0; 
  if(gl_FragColor.w<1.0)
    gl_FragColor.w = 0.0;    


}