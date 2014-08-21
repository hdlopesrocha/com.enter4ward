#version 330 core
uniform vec3 cameraPosition;
uniform vec3 cameraDirection;

uniform vec3 lightPosition[10];
uniform vec3 lightSpecularColor[10];
uniform sampler2D texture_diffuse;
uniform vec3 diffuseColor;
uniform vec3 ambientColor;
uniform float materialShininess;


varying vec3 vPosition;
varying vec3 vNormal;
varying vec2 vTexCoord;


void main(void) {
	vec3 lightDir = -normalize(vPosition-lightPosition[0]);
	vec3 normal = normalize(vNormal);
	

        
        float specularLightBrightness = pow(max(0.0, dot(normalize(reflect(lightDir, normal)), normalize(vPosition-cameraPosition))),
	      materialShininess);
        vec3 specularLightWeighting = lightSpecularColor[0] * specularLightBrightness;
  

	// Override out_Color with our texture pixel
	gl_FragColor = texture2D(texture_diffuse, vTexCoord);
	
	
	gl_FragColor.xyz *=  diffuseColor*max(dot(normal, lightDir), 0.0);
	
	gl_FragColor.xyz+=ambientColor+specularLightWeighting;
	gl_FragColor = clamp(gl_FragColor,0.0,1.0);

	//gl_FragColor.xyz = lightSpecularColor[0];
	
	//gl_FragColor.xyz = (normal+vec3(1.0,1.0,1.0))/2.0;
	//gl_FragColor.w=1.0;
	//gl_FragColor.xyz = (normalize(vPosition)+vec3(1.0,1.0,1.0))/2.0;
	
}