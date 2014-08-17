package hidrogine.lwjgl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Model3D extends Model {

    public TreeMap<String,Group> groups = new TreeMap<String, Group>();
    public TreeMap<String, Material> materials = new TreeMap<String, Material>();


    private void loadMaterials(String filename) throws JSONException, IOException{

        JSONObject jObject = new JSONObject(new String(Files.readAllBytes(Paths.get(filename))));
        Iterator<String> keys = jObject.keys();
        
        
        while (keys.hasNext()) {
            String key=keys.next();
            JSONObject jMat = jObject.getJSONObject(key);
            Material currentMaterial = new Material();

            materials.put(key, currentMaterial);
            
            if (jMat.has("map_Kd")) {
                currentMaterial.setTexture(jMat.getString("map_Kd"));
            }
            if (jMat.has("Ka")) {
                JSONArray array = jMat.getJSONArray("Ka");
                currentMaterial.Ka = new Float[3];
                for (int j = 0; j < 3; ++j)
                    currentMaterial.Ka[j] = (float) array.getDouble(j);
            }
            if (jMat.has("Kd")) {
                JSONArray array = jMat.getJSONArray("Kd");
                currentMaterial.Kd = new Float[3];
                for (int j = 0; j < 3; ++j)
                    currentMaterial.Kd[j] = (float) array.getDouble(j);
            }
            if (jMat.has("Ks")) {
                JSONArray array = jMat.getJSONArray("Ks");
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
    private void loadGeometry(String filename, float scale) throws JSONException, IOException{
        JSONObject jObject = new JSONObject(new String(Files.readAllBytes(Paths.get(filename))));
        Iterator<String> groupNames = jObject.keys();
        
        
        while (groupNames.hasNext()) {
            String groupName= groupNames.next();
            
            Group currentGroup = new Group();
            groups.put(groupName,currentGroup);
            
            JSONArray subGroups = jObject.getJSONArray(groupName);
            for (int j = 0; j < subGroups.length(); ++j) {
                JSONObject jSubGroup = subGroups.getJSONObject(j);
                BufferObject currentSubGroup = new BufferObject();
                currentGroup.subGroups.add(currentSubGroup);

                if (jSubGroup.has("mm")) {
                    currentSubGroup.setMaterial(materials.get(jSubGroup.getString("mm")));
                }
                JSONArray vv = jSubGroup.getJSONArray("vv");
                for (int k = 0; k < vv.length(); ++k) {
                    float val = (float) vv.getDouble(k) * scale;
                    if (k % 3 == 1) {

                        currentGroup.maxY = Math.max(currentGroup.maxY, val);
                    }

                    currentSubGroup.addVertex(val);
                }
                JSONArray vn = jSubGroup.getJSONArray("vn");
                for (int k = 0; k < vn.length(); ++k) {
                    currentSubGroup.addNormal((float) vn.getDouble(k));
                }
                JSONArray vt = jSubGroup.getJSONArray("vt");
                for (int k = 0; k < vt.length(); ++k) {
                    currentSubGroup.addTexture((float) vt.getDouble(k));
                }
                JSONArray ii = jSubGroup.getJSONArray("ii");
                for (int k = 0; k < ii.length(); ++k) {
                    currentSubGroup.addIndex((short) ii.getInt(k));
                }

                currentSubGroup.buildBuffer();
            }
        }
    }
    
    public Model3D(String materials, String geometry, float scale) {
        try {
            loadMaterials(materials);
            loadGeometry(geometry, scale);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }        
    }

    public void draw(ShaderProgram shader) {
        for (Group g : groups.values()) {
            for (BufferObject sg : g.subGroups) {
                sg.draw(shader);
            }
        }
    }
}
