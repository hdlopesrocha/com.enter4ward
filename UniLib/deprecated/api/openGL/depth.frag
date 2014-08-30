
void main() {
  gl_FragColor.xyz = vec3(gl_FragCoord.z);
  gl_FragColor.w = 1.0;
}

