var bimk2
// XXX
var mouseDown = false;
var lastMouseX = null;
var lastMouseY = null;
var lightingDirection = [-1.0,-1.0,-1.0];
var obj;
var ROT = 0.0;

function handleMouseDown(event) {
	mouseDown = true;
	lastMouseX = event.clientX;
	lastMouseY = event.clientY;
}

function handleMouseUp(event) {
	mouseDown = false;
}

function handleMouseMove(event) {
	if (!mouseDown) {
		return;
	}
	var newX = event.clientX;
	var newY = event.clientY;
	var deltaX = newX - lastMouseX
	var newRotationMatrix = mat4.create();
	mat4.identity(newRotationMatrix);
	mat4.rotate(newRotationMatrix, degToRad(deltaX / 10), [0, 1, 0]);
	var deltaY = newY - lastMouseY;
	mat4.rotate(newRotationMatrix, degToRad(deltaY / 10), [1, 0, 0]);
	mat4.multiply(newRotationMatrix, obj.matrix, obj.matrix);
	lastMouseX = newX
	lastMouseY = newY;
}

function drawScene() {
	gl.viewport(0, 0, gl.viewportWidth, gl.viewportHeight);
	gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
	mat4.perspective(45, gl.viewportWidth / gl.viewportHeight, 0.1, 100.0, pMatrix);
	
	var adjustedLD = vec3.create();
	vec3.normalize(lightingDirection, adjustedLD);
	vec3.scale(adjustedLD, -1);
	gl.uniform3fv(bimk_program.lightingDirectionUniform, adjustedLD);	
	mat4.identity(mvMatrix);
		
	var ZZ = document.getElementById("ZZ").value/10.0;	
	mat4.translate(mvMatrix, [0, 0, -10+ZZ]);

	var TT = document.getElementById("TT").checked;
	gl.uniform1i(bimk_program.useTexturesUniform, TT);

	obj.draw();
}

function tick() {
	requestAnimFrame(tick);
	drawScene();
	
	var RR = document.getElementById("RR").value/100.0;
	var GG = document.getElementById("GG").value/100.0;
	var BB = document.getElementById("BB").value/100.0;
	var CC = document.getElementById("CC").checked;
	var VV = document.getElementById("VV").value/300.0;	
	var DD = -document.getElementById("DD").value/180.0;	
	var	rRR = document.getElementById("rRR").value/100.0;
	var	rGG = document.getElementById("rGG").value/100.0;
	var	rBB = document.getElementById("rBB").value/100.0;
	ROT+=VV;

	if(obj.materials!=null){
		obj.materials["c0"].Kd = [ RR,GG,BB];
		obj.materials["badge"].Kd =  [ RR,GG,BB];
		obj.materials["badge"].d = 0.999;
		if(CC)
			mat4.rotate(obj.matrix, 0.001, [0, 0, 1]);
			
		obj.materials["rim"].Kd  = [ rRR,rGG,rBB];
		obj.materials["rim0"].Kd = [ rRR,rGG,rBB];
		obj.materials["rim1"].Kd = [ rRR,rGG,rBB];
		obj.materials["rim2"].Kd = [ rRR,rGG,rBB];
	}
	if(obj.groups!=null){
		mat4.rotateX(obj.groups["w3"].matrix, VV);		
		mat4.rotateX(obj.groups["w2"].matrix, VV);		
		mat4.identity(obj.groups["w0"].matrix);
		mat4.identity(obj.groups["w1"].matrix);
		mat4.rotateZ(obj.groups["w0"].matrix, DD);
		mat4.rotateZ(obj.groups["w1"].matrix, DD);
		mat4.rotateX(obj.groups["w0"].matrix, ROT);
		mat4.rotateX(obj.groups["w1"].matrix, ROT);
	}
}

function webGLStart() {
	var canvas = document.getElementById("lesson11-canvas");
	bimk2 = new	bimk(canvas);
	obj = bimk2.loadModel("car.mat","car.geo");
	canvas.onmousedown = handleMouseDown;
	document.onmouseup = handleMouseUp;
	document.onmousemove = handleMouseMove;
	gl.clearColor(0.0, 0.0, 0.0, 0.0);
	gl.enable(gl.DEPTH_TEST);
	tick();
}
