varying vec3 normal;

void main() {
  vec3 n = normalize(normal);
  gl_FragColor.xyz = (vec3(1.0,1.0,1.0) + normal)/2.0;
  gl_FragColor.w = 1.0;
}

