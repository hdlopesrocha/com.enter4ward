

uniform sampler2D tex;
varying vec2 texCoord; 
varying vec3 normal;
varying vec4 pos;

void main() {
  /* MATERIAL/LIGHT PROPERTIES */
  vec4 diffuse = gl_FrontMaterial.diffuse * gl_LightSource[0].diffuse;
  vec4 ambient = gl_FrontMaterial.ambient * gl_LightSource[0].ambient;
  vec4 specular = gl_FrontMaterial.specular* gl_LightSource[0].specular;
  vec4 light = gl_LightSource[0].position; 



  /* VECTOR PREPARATION */
  vec3 l;
  if(light.w == 0.0){
    /* DIRECTIONAL LIGHT */
    l = normalize(light.xyz);
  }
  else {
    /* SPOT LIGHT */
    l = -normalize(pos.xyz-light.xyz);
  }
  vec3 n = normalize(normal);
  vec3 r = normalize(reflect(-l, n));
  vec3 v = normalize(-pos.xyz);



  float ndots = dot(n, l);
  if(ndots>0.9) ndots = 1.0;
  else if(ndots>0.8) ndots = 0.9;
  else if(ndots>0.7) ndots = 0.8;
  else if(ndots>0.6) ndots = 0.7;
  else if(ndots>0.5) ndots = 0.6;
  else if(ndots>0.4) ndots = 0.5;
  else if(ndots>0.3) ndots = 0.4;
  else if(ndots>0.2) ndots = 0.3;
  else if(ndots>0.1) ndots = 0.2;
  else ndots = 0.1;

 
 
  /* LIGHT MATHS */
  float shininess = gl_FrontMaterial.shininess;
  float spec = clamp(shininess,0.0,1.0)* pow(max(0.0, dot(r, v)), shininess);
  float dif = max(0.0, ndots);


  /* COLOR DECISION */
  gl_FragColor = texture2D(tex, texCoord);
  gl_FragColor.xyz *= (dif*diffuse.xyz + ambient.xyz);
  gl_FragColor.xyz += spec*specular.xyz;

}

