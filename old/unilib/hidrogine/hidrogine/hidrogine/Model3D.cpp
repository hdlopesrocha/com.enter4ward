#include "Model3D.h"
#include "../unilib/json/Json.h"
using namespace unilib;

namespace hidrogine  {

    void Model3D::loadMaterials(string filename) {

		JSONObject * jObject = new JSONObject(Utils::fileToString(filename));
		Iterator<Entry<string,JSONNode*>> * it = jObject->GetIterator();
		while (it->IsValid()) {
			string key = it->GetValue().Key;
            JSONObject * jMat = jObject->GetJSONObject(key);
            Material * currentMaterial = new Material(key);

            materials->Put(key, currentMaterial);

            if (jMat->Has("map_Kd")) {
                currentMaterial->setTexture(jMat->GetString("map_Kd"));
            }
            if (jMat->Has("Ka")) {
                unilib::JSONArray * array = jMat->GetJSONArray("Ka");
                currentMaterial->Ka = new float[3];
                for (int j = 0; j < 3; ++j)
                    currentMaterial->Ka[j] = (float) array->GetDouble(j);
            }
            if (jMat->Has("Kd")) {
                unilib::JSONArray * array = jMat->GetJSONArray("Kd");
                currentMaterial->Kd = new float[3];
                for (int j = 0; j < 3; ++j)
                    currentMaterial->Kd[j] = (float) array->GetDouble(j);
            }
            if (jMat->Has("Ks")) {
                unilib::JSONArray * array = jMat->GetJSONArray("Ks");
                currentMaterial->Ks = new float[3];
                for (int j = 0; j < 3; ++j)
                    currentMaterial->Ks[j] = (float) array->GetDouble(j);
            }
            if (jMat->Has("Tf")) {
                currentMaterial->Tf = (float) jMat->GetDouble("Tf");
            }
            if (jMat->Has("illum")) {
                currentMaterial->illum = (float) jMat->GetDouble("illum");
            }
            if (jMat->Has("d")) {
                currentMaterial->d = (float) jMat->GetDouble("d");
            }
            if (jMat->Has("Ns")) {
                currentMaterial->Ns = (float) jMat->GetDouble("Ns");
            }
            if (jMat->Has("sharpness")) {
                currentMaterial->sharpness = (float) jMat->GetDouble("sharpness");
            }
            if (jMat->Has("Ni")) {
                currentMaterial->Ni = (float) jMat->GetDouble("Ni");
            }
        }
		
		delete it;
    }



   void Model3D::loadGeometry(string filename, float scale) {

		JSONObject * jObject = new JSONObject(Utils::fileToString(filename));
		Iterator<Entry<string,JSONNode*>> * it = jObject->GetIterator();
		while (it->IsValid()) {
			Group * currentGroup = new Group(it->GetValue().Key);
            groups->Add(currentGroup);
			JSONArray * subGroups = (JSONArray*) it->GetValue().Value;
            for (int j = 0; j < subGroups->Length(); ++j) {
                JSONObject * jSubGroup = subGroups->GetJSONObject(j);
                BufferObject* currentSubGroup = new BufferObject();
				currentGroup->subGroups->Add(currentSubGroup);
                if (jSubGroup->Has("mm")) {
                    currentSubGroup->setMaterial(materials->Get(jSubGroup->GetString("mm")));
                }
                JSONArray * vv = jSubGroup->GetJSONArray("vv");
                JSONArray * vn = jSubGroup->GetJSONArray("vn");
                JSONArray * vt = jSubGroup->GetJSONArray("vt");
                for (int k = 0; k < vv->Length() / 3; ++k) {
                    float vx = (float) vv->GetDouble(k * 3 + 0) * scale;
                    float vy = (float) vv->GetDouble(k * 3 + 1) * scale;
                    float vz = (float) vv->GetDouble(k * 3 + 2) * scale;
                    float nx = (float) vn->GetDouble(k * 3 + 0);
                    float ny = (float) vn->GetDouble(k * 3 + 1);
                    float nz = (float) vn->GetDouble(k * 3 + 2);
                    float tx = (float) vt->GetDouble(k * 2 + 0);
                    float ty = (float) vt->GetDouble(k * 2 + 1);
                    currentGroup->addVertex(vx, vy, vz);
                    currentSubGroup->addPosition(vx, vy, vz);
                    currentSubGroup->addNormal(nx, ny, nz);
                    currentSubGroup->addTextureCoord( tx, ty);
                }
                JSONArray * ii = jSubGroup->GetJSONArray("ii");
                for (int k = 0; k < ii->Length(); ++k) {
                    currentSubGroup->addIndex((short) ii->GetInt(k));
                }
                currentSubGroup->buildBuffer();
            }
        }
		delete it;
    }

    Model3D::Model3D(string material, string geometry, float scale) {
        	groups = new LinkedList<Group*>();
			materials = new TreeMap<string, Material>(String::Comparator);

            loadMaterials(material);
            loadGeometry(geometry, scale);
        
    }

    void Model3D::draw(ShaderProgram *shader) {
		Iterator<Group*> * it1 = groups->GetIterator(); 
		while (it1->IsValid()) {
            glBindTexture(GL_TEXTURE_2D, 0);
			Iterator<BufferObject*> * it2 = it1->GetValue()->subGroups->GetIterator();
			while (it2->IsValid()) {
				it2->GetValue()->bind(shader);
                it2->GetValue()->draw(shader);
            }
			delete it2;
        }
		delete it1;
    }

    void Model3D::draw(ShaderProgram * shader, DrawHandler * handler) {

		Iterator<Group*> * it1 = groups->GetIterator(); 
		while (it1->IsValid()) {
            glBindTexture(GL_TEXTURE_2D, 0);
			Iterator<BufferObject*> * it2 = it1->GetValue()->subGroups->GetIterator();
			while (it2->IsValid()) {
				it2->GetValue()->bind(shader);
				handler->beforeDraw(it1->GetValue(), it2->GetValue()->getMaterial());
                it2->GetValue()->draw(shader);
                handler->afterDraw(it1->GetValue(), it2->GetValue()->getMaterial());
			}
			delete it2;
        }
		delete it1;
	
	}

    void Model3D::drawBoxs(ShaderProgram * shader) {
/*    	glBindTexture(GL_TEXTURE_2D, 0);
        for (Group g : groups) {
   //         box.draw(shader, g.getMin(), g.getMax());
        }*/
    }
}
