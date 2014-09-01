#include "Model3D.h"

namespace hidrogine  {


    void Model3D::loadMaterials(string filename) {

        JSONObject jObject = new JSONObject(new String(Files.readAllBytes(Paths.get(filename))));
        Iterator<string> keys = jObject.keys();

        while (keys.hasNext()) {
            final String key = keys.next();
            final JSONObject jMat = jObject.getJSONObject(key);
            final Material currentMaterial = new Material(key);

            materials.put(key, currentMaterial);

            if (jMat.has("map_Kd")) {
                currentMaterial.setTexture(jMat.getString("map_Kd"));
            }
            if (jMat.has("Ka")) {
                final JSONArray array = jMat.getJSONArray("Ka");
                currentMaterial.Ka = new Float[3];
                for (int j = 0; j < 3; ++j)
                    currentMaterial.Ka[j] = (float) array.getDouble(j);
            }
            if (jMat.has("Kd")) {
                final JSONArray array = jMat.getJSONArray("Kd");
                currentMaterial.Kd = new Float[3];
                for (int j = 0; j < 3; ++j)
                    currentMaterial.Kd[j] = (float) array.getDouble(j);
            }
            if (jMat.has("Ks")) {
                final JSONArray array = jMat.getJSONArray("Ks");
                currentMaterial.Ks = new Float[3];
                for (int j = 0; j < 3; ++j)
                    currentMaterial.Ks[j] = (float) array.getDouble(j);
            }
            if (jMat.has("Tf")) {
                currentMaterial.Tf = (float) jMat.getDouble("Tf");
            }
            if (jMat.has("illum")) {
                currentMaterial.illum = (float) jMat.getDouble("illum");
            }
            if (jMat.has("d")) {
                currentMaterial.d = (float) jMat.getDouble("d");
            }
            if (jMat.has("Ns")) {
                currentMaterial.Ns = (float) jMat.getDouble("Ns");
            }
            if (jMat.has("sharpness")) {
                currentMaterial.sharpness = (float) jMat.getDouble("sharpness");
            }
            if (jMat.has("Ni")) {
                currentMaterial.Ni = (float) jMat.getDouble("Ni");
            }
        }
    }



   void Model3D::loadGeometry(final String filename, final float scale)
            throws JSONException, IOException {
        final FileInputStream file = new FileInputStream(filename);
        final JSONTokener tokener = new JSONTokener(file);
        final JSONObject jObject = new JSONObject(tokener);
        final Iterator<String> groupNames = jObject.keys();
        while (groupNames.hasNext()) {
            final String groupName = groupNames.next();
            final Group currentGroup = new Group(groupName);
            groups.add(currentGroup);
            final JSONArray subGroups = jObject.getJSONArray(groupName);
            for (int j = 0; j < subGroups.length(); ++j) {
                final JSONObject jSubGroup = subGroups.getJSONObject(j);
                final IBufferObject currentSubGroup = new BufferObject();
                currentGroup.subGroups.add(currentSubGroup);
                if (jSubGroup.has("mm")) {
                    currentSubGroup.setMaterial(materials.get(jSubGroup
                            .getString("mm")));
                }
                final JSONArray vv = jSubGroup.getJSONArray("vv");
                final JSONArray vn = jSubGroup.getJSONArray("vn");
                final JSONArray vt = jSubGroup.getJSONArray("vt");
                for (int k = 0; k < vv.length() / 3; ++k) {
                    float vx = (float) vv.getDouble(k * 3 + 0) * scale;
                    float vy = (float) vv.getDouble(k * 3 + 1) * scale;
                    float vz = (float) vv.getDouble(k * 3 + 2) * scale;
                    float nx = (float) vn.getDouble(k * 3 + 0);
                    float ny = (float) vn.getDouble(k * 3 + 1);
                    float nz = (float) vn.getDouble(k * 3 + 2);
                    float tx = (float) vt.getDouble(k * 2 + 0);
                    float ty = (float) vt.getDouble(k * 2 + 1);
                    currentGroup.addVertex(vx, vy, vz);
                    currentSubGroup.addPosition(vx, vy, vz);
                    currentSubGroup.addNormal(nx, ny, nz);
                    currentSubGroup.addTextureCoord( tx, ty);

                
                }
                final JSONArray ii = jSubGroup.getJSONArray("ii");
                for (int k = 0; k < ii.length(); ++k) {
                    currentSubGroup.addIndex((short) ii.getInt(k));
                }
                currentSubGroup.buildBuffer();
            }
        }
        file.close();
    }

    Model3D::Model3D(string materials, string geometry, float scale) {
        	groups = new LinkedList<Group*>();
			materials = new TreeMap<string, Material>();

            loadMaterials(materials);
            loadGeometry(geometry, scale);
        
    }

    void Model3D::draw(ShaderProgram *shader) {
        for (Group g : groups) {
            glBindTexture(GL_TEXTURE_2D, 0);
            box.draw(shader, g.getMin(), g.getMax());
            for (BufferObject sg : g.subGroups) {
                sg.bind(shader);
                sg.draw(shader);
            }
        }
    }

    void Model3D::draw(ShaderProgram * shader, DrawHandler * handler) {
        for (Group g : groups) {
            for (BufferObject sg : g.subGroups) {
                sg.bind(shader);
                handler.beforeDraw(g, sg.getMaterial());
                sg.draw(shader);
                handler.afterDraw(g, sg.getMaterial());
            }
        }
    }

    void Model3D::drawBoxs(ShaderProgram * shader) {
    	glBindTexture(GL_TEXTURE_2D, 0);
        for (Group g : groups) {
            box.draw(shader, g.getMin(), g.getMax());
        }
    }
}
