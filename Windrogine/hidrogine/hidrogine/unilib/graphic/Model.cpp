#include "Graphic.hpp"

namespace unilib {

	Model::Model(){
	};

	Model::Model(string dir,string filename, float scale, float rx, float ry, float rz){
		vector<unsigned int> vertexGID;
    rx = MathHelper::ToRadians(rx);
    ry = MathHelper::ToRadians(ry);
    rz = MathHelper::ToRadians(rz);
    cout << "===== " << filename << " =====" << endl;
		vector<Vector3> normal;
		vector<Vector2> texCoord;
		int GID = 0;

    // default normal
		normal.push_back(Vector3(0,1,0));

    // default texCoord
		texCoord.push_back(Vector2(0,0));

    // default group
		groups.push_back(new Group("default",GID++));
		
    // default material
    Material * currentMaterial= new Material();
    allMaterials.push_back(currentMaterial);

		string s;
		ifstream file;

  		// ====================
  		// WAVEFRONT OBJ IMPORT
  		// ====================
	    file.open ((dir + filename).c_str());
	    if(file.is_open()){
		    while(!file.eof()) // To get all the lines.
		    {
		        getline(file,s); // Saves the line in s.

  				if (!s.compare(0, 2, "v ")){
					// VERTEX
					Vector3 vec;
					sscanf(s.c_str(),"v %f %f %f",&vec.X,&vec.Y,&vec.Z);
          vec= (Matrix::CreateTranslation(vec)*Matrix::CreateFromYawPitchRoll(ry,rx,rz)).Translation()*scale;
					vertex.push_back(VertexPositionNormalTexture(vec.X,vec.Y,vec.Z));
					vertexGID.push_back(groups[groups.size()-1]->id);
          groups[groups.size()-1]->vertices.push_back(vec);


				}
      	else if (!s.compare(0, 2, "vn")){
					// NORMAL
          Vector3 vec;
          sscanf(s.c_str(),"vn %f %f %f",&vec.X,&vec.Y,&vec.Z);
          vec= (Matrix::CreateTranslation(vec)*Matrix::CreateFromYawPitchRoll(ry,rx,rz)).Translation();
					normal.push_back(vec);
				}
  				else if (!s.compare(0, 2, "vt")){
					// TEXTURE
					float tx,ty;
					sscanf(s.c_str(),"vt %f %f",&tx,&ty);
					texCoord.push_back(Vector2(tx,1-ty)); // u , 1-v 
				}
  				else if (!s.compare(0, 2, "f ")){
  					// FACE
  					if(count(s.begin(),s.end(),' ')==4){
  						// QUAD
  						int v1=0,t1=0,n1=0;
  						int v2=0,t2=0,n2=0;
  						int v3=0,t3=0,n3=0;
  						int v4=0,t4=0,n4=0;

						if(sscanf(s.c_str(),"f %d %d %d %d",&v1,&v2,&v3, &v4)== 4);
						else if(sscanf(s.c_str(),"f %d/%d %d/%d %d/%d %d/%d",&v1,&t1,&v2,&t2,&v3,&t3,&v4,&t4)== 8);
						else if(sscanf(s.c_str(),"f %d//%d %d//%d %d//%d %d//%d",&v1,&n1,&v2,&n2,&v3,&n3,&v4,&n4)== 8);
						else if(sscanf(s.c_str(),"f %d/%d/%d %d/%d/%d %d/%d/%d %d/%d/%d",&v1,&t1,&n1,&v2,&t2,&n2,&v3,&t3,&n3,&v4,&t4,&n4)== 12);
  					}
  					else {
  						// TRI
  						int v1=0,t1=0,n1=0;
  						int v2=0,t2=0,n2=0;
  						int v3=0,t3=0,n3=0;

						if(sscanf(s.c_str(),"f %d %d %d",&v1,&v2,&v3)== 3);
						else if(sscanf(s.c_str(),"f %d/%d %d/%d %d/%d",&v1,&t1,&v2,&t2,&v3,&t3)== 6);
						else if(sscanf(s.c_str(),"f %d//%d %d//%d %d//%d",&v1,&n1,&v2,&n2,&v3,&n3)== 6);
						else if(sscanf(s.c_str(),"f %d/%d/%d %d/%d/%d %d/%d/%d",&v1,&t1,&n1,&v2,&t2,&n2,&v3,&t3,&n3)== 9);

// XXX
            vector<MaterialGroup*> * mgs = groups[vertexGID[v1-1]]->materialGroups;
            MaterialGroup * mg = NULL;
            for(unsigned int i=0 ;i< mgs->size(); ++i){
                if(mgs[0][i]->materialName == currentMaterial->name){
                    mg = mgs[0][i];
                    break;
                }
            }
						if(mg==NULL){
                mg = new MaterialGroup( currentMaterial->name );
      //          cout << "mg created: " << currentMaterial->name << endl;
						    mgs->push_back(mg);
						}


						mg->indexs.push_back(v1-1);
						mg->indexs.push_back(v2-1);
						mg->indexs.push_back(v3-1);

						vertex[v1-1].normal=normal[n1];
						vertex[v2-1].normal=normal[n2];
						vertex[v3-1].normal=normal[n3];
						
						vertex[v1-1].texCoord=texCoord[t1];
						vertex[v2-1].texCoord=texCoord[t2];
						vertex[v3-1].texCoord=texCoord[t3];
  					}
  				}

  				else if (!s.compare(0, 2, "g ")){
  					// USE MATERIAL
  					char name[128];
  					sscanf(s.c_str(),"g %s",name);
  					groups.push_back(new Group(name,GID++));
				}	


  				else if (!s.compare(0, 7, "usemtl ")){
  					// USE MATERIAL
  					char name[128];
  					sscanf(s.c_str(),"usemtl %s",name);
				
  					for(unsigned int i=0 ; i < allMaterials.size(); ++i){
  						if(strcmp(name, allMaterials[i]->name)==0 && strlen(name)==strlen(allMaterials[i]->name)){
  							currentMaterial = allMaterials[i];
  							break;
  						}
  					}

            if(currentMaterial==NULL)
                currentMaterial = allMaterials[0];


				}	
  				else if (!s.compare(0, 7, "mtllib ")){
  					// =============
  					// MTLLIB IMPORT
  					// =============
  					char mtldir[128], mtlfile[128];
  					sscanf(s.c_str(),"mtllib %s",mtlfile);
  					sprintf(mtldir,"%s%s",dir.c_str(),mtlfile);
  					ifstream mtlin(mtldir);
  					if(mtlin.is_open()){
  						string line;
  						Material * tmpMaterial=NULL;

  						while(!mtlin.eof()){
  							getline(mtlin,line);
  						
  							// chinese code...
    							if (!line.compare(0, 7, "newmtl ")){
    								tmpMaterial = new Material();
    								sscanf(line.c_str(),"newmtl %s",tmpMaterial->name);
    								allMaterials.push_back(tmpMaterial);
    							}
    							else if (!line.compare(0, 3, "Ns ")){
    								sscanf(line.c_str(),"Ns %f",&tmpMaterial->ns);
    							}
    							else if (!line.compare(0, 2, "d ")){
    								sscanf(line.c_str(),"d %f",&tmpMaterial->dif[3]);
    							}	  							
    							else if (!line.compare(0, 3, "Ni ")){
    								sscanf(line.c_str(),"Ni %f",&tmpMaterial->ni);
    							}		  							
    							else if (!line.compare(0, 3, "Ka ")){
    								sscanf(line.c_str(),"Ka %f %f %f",&tmpMaterial->amb[0],&tmpMaterial->amb[1],&tmpMaterial->amb[2]);
    							}
    							else if (!line.compare(0, 3, "Kd ")){
    								sscanf(line.c_str(),"Kd %f %f %f",&tmpMaterial->dif[0],&tmpMaterial->dif[1],&tmpMaterial->dif[2]);
    							}
    							else if (!line.compare(0, 3, "Ks ")){
    								sscanf(line.c_str(),"Ks %f %f %f",&tmpMaterial->spec[0],&tmpMaterial->spec[1],&tmpMaterial->spec[2]);
    							}  								  							  								  							
    							else if (!line.compare(0, 6, "illum ")){
    								sscanf(line.c_str(),"illum %d",&tmpMaterial->illum);
    							}
    							else if (!line.compare(0, 7, "map_Kd ")){
    								char mapfile[128];
    								sscanf(line.c_str(),"map_Kd %s",mapfile);
    								sprintf(tmpMaterial->filename,"%s%s",dir.c_str(),mapfile);
    							}	  								  								  							
  						}

  						mtlin.close();
  					}	
  					else
  						cout << "mtllib error!" << endl;
				}				
		    }
		    file.close();

        for(unsigned int i=0 ; i < groups.size() ; ++i){
            groups[i]->boundingSphere = BoundingSphere::CreateFromPoints(groups[i]->vertices);
            groups[i]->vertices.clear();
        }
        if(groups.size()>0){
            boundingSphere = groups[0]->boundingSphere;
            for(unsigned int i=1 ; i < groups.size() ; ++i){
                boundingSphere = BoundingSphere::CreateMerged(boundingSphere, groups[i]->boundingSphere);
            }
        }
		}
  	else cout << "Unable to open file " << dir;

    vertexGID.clear();
    normal.clear();
    texCoord.clear();

    cout << "\tgroups: " << groups.size() << endl;
    cout << "\tsize: " << vertex.size()*sizeof(VertexPositionNormalTexture) << endl;

	};

	void Model::setDiffuse(string mat, float r, float g, float b){
		unsigned int i=0;
		for (i = 0; i < allMaterials.size() && strcmp(mat.c_str(), allMaterials[i]->name); ++i);
		allMaterials[i]->dif[0]=r;
		allMaterials[i]->dif[1]=g;
		allMaterials[i]->dif[2]=b;

	}
}
