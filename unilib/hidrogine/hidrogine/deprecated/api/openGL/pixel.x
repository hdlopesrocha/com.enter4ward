uniform sampler2D tex;
uniform sampler2D shadowMap;

uniform mat4 biasMatrix;
uniform mat4 depthBiasMVP;

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

  
  /* LIGHT MATHS */
  float shininess = gl_FrontMaterial.shininess;
  float spec = clamp(shininess,0.0,1.0)* pow(max(0.0, dot(r, v)), shininess);
  float dif = max(0.0, dot(n, l));

  /* COLOR DECISION */
  gl_FragColor = texture2D(tex, texCoord);
  gl_FragColor.w *= diffuse.w;
  gl_FragColor.xyz *= (dif*diffuse.xyz + ambient.xyz);
  gl_FragColor.xyz += spec*specular.xyz;

float bias = 0.005;
float visibility = 1.0;
  vec4 ShadowCoord = depthBiasMVP * pos;

if ( texture2D(shadowMap,ShadowCoord.xy).z  <  ShadowCoord.z-bias){
    visibility = 0.5;
}
gl_FragColor.xyz*=visibility;
}
