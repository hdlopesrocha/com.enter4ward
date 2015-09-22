#include "OGLModel.hpp"

namespace api {


	OGLModel::OGLModel(string dir,string filename, float scale, float rx, float ry, float rz)
	: Model(dir,filename,scale,rx,ry,rz)
	{
	    // vertex data
        glGenBuffers(1,&_data);
		glBindBuffer(GL_ARRAY_BUFFER, _data);
		glBufferData(GL_ARRAY_BUFFER, vertex.size()*sizeof(VertexPositionNormalTexture),&vertex[0],GL_STATIC_DRAW);

		for(int i=0 ; i < groups.size() ; ++i){
			for(int j = 0; j < groups[i]->materialGroups->size() ; ++j){
				MaterialGroup * mg= (*groups[i]->materialGroups)[j];	
		    	// index data
			    glGenBuffers(1,&mg->indexsID);
			    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mg->indexsID);
			    glBufferData(GL_ELEMENT_ARRAY_BUFFER, mg->indexs.size()*sizeof(unsigned int),&mg->indexs[0],GL_STATIC_DRAW);
			}
		}

  		// load materials
		for(int j=0 ; j < allMaterials.size(); ++j){
			allMaterials[j]->texture = loadTexture(allMaterials[j]->filename);
		}


	};

	void OGLModel::draw(Object * obj,Camera * cam){
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
			
		for(int i=0 ; i < groups.size(); ++i){
			if(cam->_viewFrustum.Contains(groups[i]->boundingSphere.Transform(obj->matrix))!=ContainmentType::Disjoint){
				for(int j = 0; j < groups[i]->materialGroups->size() ; ++j){
					MaterialGroup * mg= (*groups[i]->materialGroups)[j];			
					glActiveTexture(GL_TEXTURE0);
					Material * mat;
					for(int j=0 ; j < allMaterials.size(); ++j){
						if(allMaterials[j]->name == mg->materialName){
							mat = allMaterials[j];
							break;
						}
					}
					if(mat==NULL)
						mat= allMaterials[0];
	  				
					glBindTexture(GL_TEXTURE_2D, mat->texture);
					
					glMaterialfv(GL_FRONT, GL_DIFFUSE, mat->dif);
					glMaterialfv(GL_FRONT, GL_AMBIENT, mat->amb);
					glMaterialfv(GL_FRONT, GL_SPECULAR, mat->spec);
					glMaterialf(GL_FRONT, GL_SHININESS, mat->ns);
					
				
					if(!groups[i]->name.compare(0, 1, "w")){
						glPushMatrix();
						glTranslatef(groups[i]->boundingSphere.Center.X,groups[i]->boundingSphere.Center.Y,groups[i]->boundingSphere.Center.Z);
						glRotatef(groups[i]->_rotW, 1.0f, 0.0f, 0.0f);
						groups[i]->_rotW+=0.5;
						glTranslatef(-groups[i]->boundingSphere.Center.X,-groups[i]->boundingSphere.Center.Y,-groups[i]->boundingSphere.Center.Z);
					}
	
	//				glActiveTexture(GL_TEXTURE1);
	//				glBindTexture(GL_TEXTURE_2D, 0);
	//				glUniform1iARB(glGetUniformLocationARB(_ogl->_program, "tex"),0);
	//				glUniform1iARB(glGetUniformLocationARB(_ogl->_program, "img"),1);
					
					glBindBuffer(GL_ARRAY_BUFFER, _data);
		    		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mg->indexsID);
				    glVertexPointer(3,GL_FLOAT, sizeof(VertexPositionNormalTexture), 0);
		    		glNormalPointer(GL_FLOAT, sizeof(VertexPositionNormalTexture), (void*)(3*sizeof(float)));
					glTexCoordPointer(2,GL_FLOAT, sizeof(VertexPositionNormalTexture), (void*)(6*sizeof(float)));
				    glDrawElements(GL_TRIANGLES,mg->indexs.size(),GL_UNSIGNED_INT,0);

					if(!groups[i]->name.compare(0, 1, "w")){
						glPopMatrix();
					}
				}
			}
		}
		glDisableClientState(GL_NORMAL_ARRAY);    
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
	}




	void OGLModel::draw(OpenGL * ogl, int shader, Object * obj, Camera * cam){
	    if(cam->_viewFrustum.Contains(boundingSphere.Transform(obj->matrix))!=ContainmentType::Disjoint){

			#ifdef DEBUG
			glPolygonMode( GL_FRONT_AND_BACK, GL_LINE);
			//renderSphere(boundingSphere.Transform(obj->matrix),8);
			for(int i=0 ; i < groups.size() ; ++i)
				renderSphere(groups[i]->boundingSphere.Transform(obj->matrix),8);
			glPolygonMode( GL_FRONT_AND_BACK, GL_FILL);
			#endif

			glPushMatrix();
			glTranslatef(obj->position.X,obj->position.Y,obj->position.Z);
			glRotatef(MathHelper::ToDegrees(obj->roll), 0.0f, 0.0f, 1.0f);
			glRotatef(MathHelper::ToDegrees(obj->pitch), 1.0f, 0.0f, 0.0f);
			glRotatef(MathHelper::ToDegrees(obj->yaw), 0.0f, 1.0f, 0.0f);

			switch(shader){
				case DEFAULT_SHADER:
					glUseProgram(ogl->_program);
					draw(obj,cam);
				break;
				case NORMAL_SHADER :
					glUseProgram(ogl->_normalProgram);
					draw(obj,cam);
				break;
				case DEPTH_SHADER :
					glUseProgram(ogl->_depthProgram);
					draw(obj,cam);	
				break;
				case HIDRO_SHADER :
					glUseProgram(ogl->_hidroProgram);
					draw(obj,cam);	
				break;
				case TOON_SHADER:
			        glEnable(GL_LIGHTING);
			        glEnable(GL_TEXTURE_2D);
			        glEnable(GL_DEPTH_TEST);
			        glUseProgram(ogl->_toonProgram);
			        draw(obj,cam);
					glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
			        glFrontFace(GL_CW);
			        glUseProgram(ogl->_toonLineProgram);
			        glLineWidth(8);
			        draw(obj,cam);
				    glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
			        glFrontFace(GL_CCW);
				break;
				default:
					glUseProgram(0);
					draw(obj,cam);
				break;
			}

			glPopMatrix();
		}
	}

	void renderSphere(float x, float y, float z, float radius,int subdivisions){
	    //the same quadric can be re-used for drawing many spheres
	    GLUquadricObj *quadric=gluNewQuadric();
	    glDisable(GL_LIGHTING);

	    gluQuadricNormals(quadric, GLU_SMOOTH);
	    glPushMatrix();
	    glTranslatef( x,y,z );
	    gluSphere(quadric, radius, subdivisions,subdivisions);
	    glPopMatrix();    
	    gluDeleteQuadric(quadric);
	  	glEnable(GL_LIGHTING);
	  
	}

	void renderSphere(BoundingSphere bs,int subdivisions){
		renderSphere(bs.Center.X, bs.Center.Y, bs.Center.Z, bs.Radius,subdivisions);
	}

}

