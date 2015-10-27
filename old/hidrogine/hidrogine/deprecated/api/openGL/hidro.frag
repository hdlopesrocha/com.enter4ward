
uniform sampler2D water;
uniform sampler2D sand;
uniform sampler2D grass;
uniform sampler2D rock;
uniform sampler2D snow;




varying float height;
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
  if(height < 6378135.0+10.0)       gl_FragColor = texture2D(water, gl_TexCoord[0].st*64.0); 
  else if(height < 6378135.0+400.0)   gl_FragColor = texture2D(sand, gl_TexCoord[0].st*64.0);    
  else if(height < 6378135.0+4000.0)   gl_FragColor = texture2D(grass, gl_TexCoord[0].st*64.0); 
  else if(height < 6378135.0+8000.0)   gl_FragColor = texture2D(rock, gl_TexCoord[0].st*64.0); 
  else                            gl_FragColor = texture2D(snow, gl_TexCoord[0].st*64.0);  


  gl_FragColor.w *= diffuse.w;
  gl_FragColor.xyz *= (dif*diffuse.xyz + ambient.xyz);
  gl_FragColor.xyz += spec*specular.xyz;


}
