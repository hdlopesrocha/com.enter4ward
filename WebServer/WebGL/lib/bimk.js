var bimk_program;
var gl;
var mvMatrix = mat4.create();
var mvMatrixStack = [];
var pMatrix = mat4.create();
var default_texture;

function mvPushMatrix() {
	var copy = mat4.create();
	mat4.set(mvMatrix, copy);
	mvMatrixStack.push(copy);
}

function mvPopMatrix() {
	if (mvMatrixStack.length == 0) {
		throw "Invalid popMatrix!";
	}
	mvMatrix = mvMatrixStack.pop();
}

function degToRad(degrees) {
	return degrees * Math.PI / 180;
}

function loadFile2(url, event) {
	$.ajax({type:"GET",url: url}).done(event);
}

// === BIMK INDEPENDENT METHODS ===
function loadFile(url) {
	var request = new XMLHttpRequest();
	request.open("GET", url,false);
	request.send(null);
	if (request.readyState == 4 && request.status == 200) {
	    return request.responseText;
	}
}

function bimk(canvas){

	// === INITIALIZATION ===
	try {
		gl = canvas.getContext("experimental-webgl");
		gl.viewportWidth = canvas.width;
		gl.viewportHeight = canvas.height;
	} catch (e) {
	}
	if (!gl) {
		alert("Could not initialise WebGL, sorry :-(");
	}
	default_texture = gl.createTexture();
	bimk_program = gl.createProgram();
	
	var fragmentShader =  gl.createShader(gl.FRAGMENT_SHADER);
	var vertexShader =  gl.createShader(gl.VERTEX_SHADER);

	loadFile2("vertex.shader",function(data){
		gl.shaderSource(vertexShader,data);
		gl.compileShader(vertexShader);
		if (!gl.getShaderParameter(vertexShader, gl.COMPILE_STATUS)) {
			alert(gl.getShaderInfoLog(vertexShader));
		}

		loadFile2("fragment.shader",function(data){



			gl.shaderSource(fragmentShader,data);
			gl.compileShader(fragmentShader);
			if (!gl.getShaderParameter(fragmentShader, gl.COMPILE_STATUS)) {
				alert(gl.getShaderInfoLog(fragmentShader));
			}
			gl.attachShader(bimk_program, vertexShader);
			gl.attachShader(bimk_program, fragmentShader);
			gl.linkProgram(bimk_program);
			if (!gl.getProgramParameter(bimk_program, gl.LINK_STATUS)) {
				alert("Could not initialise shaders");
			}
			gl.useProgram(bimk_program);
			bimk_program.vertexPositionAttribute = gl.getAttribLocation(bimk_program, "aVertexPosition");
			gl.enableVertexAttribArray(bimk_program.vertexPositionAttribute);
			bimk_program.vertexNormalAttribute = gl.getAttribLocation(bimk_program, "aVertexNormal");
			gl.enableVertexAttribArray(bimk_program.vertexNormalAttribute);
			bimk_program.textureCoordAttribute = gl.getAttribLocation(bimk_program, "aTextureCoord");
			gl.enableVertexAttribArray(bimk_program.textureCoordAttribute);
			bimk_program.pMatrixUniform = gl.getUniformLocation(bimk_program, "uPMatrix");
			bimk_program.mvMatrixUniform = gl.getUniformLocation(bimk_program, "uMVMatrix");
			bimk_program.nMatrixUniform = gl.getUniformLocation(bimk_program, "uNMatrix");
			bimk_program.samplerUniform = gl.getUniformLocation(bimk_program, "uSampler");
			bimk_program.materialAmbientColorUniform = gl.getUniformLocation(bimk_program, "uMaterialAmbientColor");
			bimk_program.materialDiffuseColorUniform = gl.getUniformLocation(bimk_program, "uMaterialDiffuseColor");
			bimk_program.materialSpecularColorUniform = gl.getUniformLocation(bimk_program, "uMaterialSpecularColor");
			bimk_program.materialShininessUniform = gl.getUniformLocation(bimk_program, "uMaterialShininess");
			bimk_program.materialAlphaUniform = gl.getUniformLocation(bimk_program, "uMaterialAlpha");
			bimk_program.materialEmissiveColorUniform = gl.getUniformLocation(bimk_program, "uMaterialEmissiveColor");
			bimk_program.showSpecularHighlightsUniform = gl.getUniformLocation(bimk_program, "uShowSpecularHighlights");
			bimk_program.useTexturesUniform = gl.getUniformLocation(bimk_program, "uUseTextures");
			bimk_program.ambientLightingColorUniform = gl.getUniformLocation(bimk_program, "uAmbientLightingColor");
			bimk_program.pointLightingLocationUniform = gl.getUniformLocation(bimk_program, "uPointLightingLocation");
			bimk_program.pointLightingSpecularColorUniform = gl.getUniformLocation(bimk_program, "uPointLightingSpecularColor");
			bimk_program.pointLightingDiffuseColorUniform = gl.getUniformLocation(bimk_program, "uPointLightingDiffuseColor");
			// === OPENGL SETTINGS ===
			gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA);
			gl.enable ( gl.BLEND ) ;
			gl.enable(gl.CULL_FACE);
			// === DEFAULT SETTINGS ===
			gl.uniform1i(bimk_program.useLightingUniform, true);
			gl.uniform3f(bimk_program.ambientColorUniform, 0.8, 0.8, 0.8);
			gl.uniform3f(bimk_program.directionalColorUniform, 0.2, 0.2, 0.2);
			gl.uniform1i(bimk_program.showSpecularHighlightsUniform, true);
			gl.uniform3f(bimk_program.pointLightingLocationUniform, -1, 2, -1);
			gl.uniform3f(bimk_program.ambientLightingColorUniform, 0.05, 0.05, 0.05);
			gl.uniform3f(bimk_program.pointLightingDiffuseColorUniform, 0.8, 0.8, 0.8);
			gl.uniform3f(bimk_program.pointLightingSpecularColorUniform, 0.8, 0.8, 0.8);
			gl.uniform3f(bimk_program.materialAmbientColorUniform, 1.0, 1.0, 1.0);
			gl.uniform3f(bimk_program.materialDiffuseColorUniform, 1.0, 1.0, 1.0);
			gl.uniform3f(bimk_program.materialSpecularColorUniform, 1.5, 1.5, 1.5);
			gl.uniform1f(bimk_program.materialShininessUniform, 5);
			gl.uniform3f(bimk_program.materialEmissiveColorUniform, 0.0, 0.0, 0.0);
			


		});



	});


	// === BIMK METHODS ===
	function bimk_setMatrixUniforms(matrix) {
		gl.uniformMatrix4fv(bimk_program.pMatrixUniform, false, pMatrix);
		gl.uniformMatrix4fv(bimk_program.mvMatrixUniform, false, matrix);
		var normalMatrix = mat3.create();
		mat4.toInverseMat3(matrix, normalMatrix);
		mat3.transpose(normalMatrix);
		gl.uniformMatrix3fv(bimk_program.nMatrixUniform, false, normalMatrix);
	}
	function bimk_initMaterials(materials){
		for (var name in materials) {
			var m = materials[name];
			// Texture Loading
			m.texture=gl.createTexture();
			m.texture.image=new Image();
			m.texture.image.xxx = m.texture;
			m.texture.image.onload = function () {
				//gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, true);
			 	gl.pixelStorei(gl.UNPACK_PREMULTIPLY_ALPHA_WEBGL, false);
			 	gl.bindTexture(gl.TEXTURE_2D, this.xxx);
				gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, this);
				gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
				gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_NEAREST);
				gl.generateMipmap(gl.TEXTURE_2D);
				gl.bindTexture(gl.TEXTURE_2D, null);
			}
			if(m.map_Kd!=null){
				m.texture.image.src = m.map_Kd;
			}
		}
	}

	function bimk_initGroups(groups){
		for (var name in groups) {
			var g = groups[name];

			g.matrix = mat4.create();
			mat4.identity(g.matrix);

			for(var j=0 ; j < g.length ; ++j){
				var m = g[j];
				{
					var min_xx =m.vv[0];
					var min_yy =m.vv[1];
					var min_zz =m.vv[2];
					var max_xx =m.vv[0];
					var max_yy =m.vv[1];
					var max_zz =m.vv[2];
					for(var k=0; k< m.vv.length/3 ; ++k){
						min_xx = Math.min(m.vv[0+k*3],min_xx);
						min_yy = Math.min(m.vv[1+k*3],min_yy);
						min_zz = Math.min(m.vv[2+k*3],min_zz);
						max_xx = Math.max(m.vv[0+k*3],max_xx);
						max_yy = Math.max(m.vv[1+k*3],max_yy);
						max_zz = Math.max(m.vv[2+k*3],max_zz);
					}
					g.center=[(min_xx+max_xx)/2,(min_yy+max_yy)/2,(min_zz+max_zz)/2];
				}
				// Normal Buffer Initialization
				{
					m.nb=gl.createBuffer();
					gl.bindBuffer(gl.ARRAY_BUFFER,m.nb);
					gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(m.vn),gl.STATIC_DRAW);
					m.nb.itemSize=3;
					m.nb.numItems=m.vn.length/3;
				}
				// Texture Coordinates Buffer Initialization
				{
					m.tb=gl.createBuffer();
					gl.bindBuffer(gl.ARRAY_BUFFER,m.tb);
					gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(m.vt),gl.STATIC_DRAW);
					m.tb.itemSize=2;
					m.tb.numItems=m.vt.length/2;
				}
				// Vertex Buffer Initialization
				{
					m.vb=gl.createBuffer();
					gl.bindBuffer(gl.ARRAY_BUFFER,m.vb);
					gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(m.vv),gl.STATIC_DRAW);
					m.vb.itemSize=3;
					m.vb.numItems=m.vv.length/3;
				}
				// Index Buffer Initialization
				{
					m.ib=gl.createBuffer();
					gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER,m.ib);
					gl.bufferData(gl.ELEMENT_ARRAY_BUFFER,new Uint16Array(m.ii),gl.STATIC_DRAW);
					m.ib.itemSize=1;
					m.ib.numItems=m.ii.length;
				}
			}
		}
	}

	function bimk_drawModel(model){
		if(model.groups==null)
			return;

		mvPushMatrix();
		mat4.multiply(mvMatrix, model.matrix);
		var components = [];
		// GET MATERIALS
		for (var name in model.groups) {
			var g = model.groups[name];
			for(var j=0 ; j < g.length ; ++j){
				var m = g[j];
				var mat = model.materials[g[j].mm];
				if(mat==null) {
					break;
				}
				else if(mat.d!=null && mat!=1.0){
					components.push(m);
					components.push(g);
				}
				else {
					components.unshift(g);
					components.unshift(m);
				}
			}
		}
		// DRAW BY ORDER
		while(components.length!=0){
			var m = components.shift();
			var g = components.shift();
			var mat=model.materials[m.mm];
			


			if(mat!=null){
				if(mat.Ka!=null)
					gl.uniform3f(bimk_program.materialAmbientColorUniform,mat.Ka[0],mat.Ka[1],mat.Ka[2]);
				else
					gl.uniform3f(bimk_program.materialAmbientColorUniform,0.0,0.0,0.0);
				if(mat.Kd!=null)
					gl.uniform3f(bimk_program.materialDiffuseColorUniform,mat.Kd[0],mat.Kd[1],mat.Kd[2]);
				else
					gl.uniform3f(bimk_program.materialDiffuseColorUniform,0.0,0.0,0.0);
				if(mat.Ks!=null)
					gl.uniform3f(bimk_program.materialSpecularColorUniform,mat.Ks[0],mat.Ks[1],mat.Ks[2]);
				else
					gl.uniform3f(bimk_program.materialSpecularColorUniform,1.0,1.0,1.0);
				if(mat.Ns!=null)
					gl.uniform1f(bimk_program.materialShininessUniform,mat.Ns);
				else
					gl.uniform1f(bimk_program.materialShininessUniform,0.0,0.0);
				if(mat.d!=null)
					gl.uniform1f(bimk_program.materialAlphaUniform,mat.d);
				else
					gl.uniform1f(bimk_program.materialAlphaUniform,1.0);
			}
			gl.activeTexture(gl.TEXTURE0);
			gl.bindTexture(gl.TEXTURE_2D, mat!=null ? mat.texture : default_texture);
			gl.uniform1i(bimk_program.samplerUniform,0);
			gl.bindBuffer(gl.ARRAY_BUFFER,m.vb);
			gl.vertexAttribPointer(bimk_program.vertexPositionAttribute,m.vb.itemSize,gl.FLOAT,false,0,0);
			gl.bindBuffer(gl.ARRAY_BUFFER,m.tb);
			gl.vertexAttribPointer(bimk_program.textureCoordAttribute,m.tb.itemSize,gl.FLOAT,false,0,0);
			gl.bindBuffer(gl.ARRAY_BUFFER,m.nb);
			gl.vertexAttribPointer(bimk_program.vertexNormalAttribute,m.nb.itemSize,gl.FLOAT,false,0,0);
			gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER,m.ib);
			mvPushMatrix();
			mat4.translate(mvMatrix, g.center);
			mat4.multiply(mvMatrix, g.matrix);
			mat4.translate(mvMatrix, [-g.center[0],-g.center[1],-g.center[2]]);
			bimk_setMatrixUniforms(mvMatrix);
			gl.drawElements(gl.TRIANGLES,m.ib.numItems,gl.UNSIGNED_SHORT,0);
			mvPopMatrix();
		}
		mvPopMatrix();
	}



	this.loadModel = function (urlMat,urlGeo) {
		var model = new Object();


		loadFile2(urlMat, function(data) {
			model.materials= JSON.parse(data);
			bimk_initMaterials(model.materials);
		});

		loadFile2(urlGeo, function(data){
			model.groups = JSON.parse(data);
			bimk_initGroups(model.groups);
		});	


		model.matrix = mat4.create();
		mat4.identity(model.matrix);
		mat4.rotate(model.matrix, 3.1415, [0, 0.5, 0.65]);

		model.draw = function (){
			bimk_drawModel(model);
		}
		return model;
	}
}
