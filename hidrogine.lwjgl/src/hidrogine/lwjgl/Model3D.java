package hidrogine.lwjgl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.lwjgl.opengl.GL11;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

// TODO: Auto-generated Javadoc
/**
 * The Class Model3D.
 */
public class Model3D extends Model {

    /** The groups. */
    public List<Group> groups = new ArrayList<Group>();

    /** The materials. */
    public TreeMap<String, Material> materials = new TreeMap<String, Material>();

    /**
     * Load materials.
     *
     * @param filename
     *            the filename
     * @throws JSONException
     *             the JSON exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void loadMaterials(String filename) throws JSONException,
            IOException {

        final JSONObject jObject = new JSONObject(new String(Files.readAllBytes(Paths
                .get(filename))));
        final Iterator<String> keys = jObject.keys();

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

    /**
     * Load geometry.
     *
     * @param filename
     *            the filename
     * @param scale
     *            the scale
     * @throws JSONException
     *             the JSON exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void loadGeometry2(final String filename, final float scale)
            throws JSONException, IOException {
        final JsonFactory factory = new JsonFactory();
        final JsonParser parser = factory.createParser(new File(filename));
        if (parser.nextToken() == JsonToken.START_OBJECT) {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                if (parser.nextToken() == JsonToken.START_ARRAY) {
                    final Group group = new Group(parser.getCurrentName());
                    groups.add(group);
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        final BufferObject buffer = new BufferObject();
               
                        while (true) {
                            final JsonToken token = parser.nextToken();
                            if (token == JsonToken.FIELD_NAME) {
                                switch (parser.getCurrentName()) {
                                case "mm":
                                    buffer.setMaterial(materials.get(parser
                                            .nextTextValue()));
                                    break;
                                case "vv":
                                    if (parser.nextToken() == JsonToken.START_ARRAY) {
                                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                                            float vx = parser.getFloatValue() * scale;
                                            float vy = parser.getFloatValue() * scale;
                                            float vz = parser.getFloatValue() * scale;
                                            group.addVertex(vx, vy, vz);

                                            buffer.addPosition(vx, vy, vz);
                                        }
                                    }
                                    break;
                                case "vn":
                                    if (parser.nextToken() == JsonToken.START_ARRAY) {
                                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                                            buffer.addNormal(parser.getFloatValue(),parser.getFloatValue(),parser.getFloatValue());
                                        }
                                    }
                                    break;
                                case "vt":
                                    if (parser.nextToken() == JsonToken.START_ARRAY) {
                                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                                            buffer.addTextureCoord(parser.getFloatValue(),parser.getFloatValue());
                                        }
                                    }
                                    break;
                                case "ii":
                                    if (parser.nextToken() == JsonToken.START_ARRAY) {
                                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                                            buffer.addIndex(parser.getShortValue());
                                        }
                                    }
                                    break;
                                default:
                                    break;
                                }
                            } else if (token == JsonToken.END_OBJECT) {

                                break;
                            }

                        }
                        
                        buffer.buildBuffer();
                        group.subGroups.add(buffer);
                    }
                }
            }
        }
        parser.close();
    }

    /**
     * Load geometry.
     *
     * @param filename
     *            the filename
     * @param scale
     *            the scale
     * @throws JSONException
     *             the JSON exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @SuppressWarnings("unchecked")
   private void loadGeometry(final String filename, final float scale)
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
                final IBufferObject currentSubGroup = new VertexBufferObject();
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

    /**
     * Instantiates a new model3 d.
     *
     * @param materials
     *            the materials
     * @param geometry
     *            the geometry
     * @param scale
     *            the scale
     */
    public Model3D(String materials, String geometry, float scale) {
        try {
            loadMaterials(materials);
            loadGeometry(geometry, scale);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.lwjgl.Model#draw(hidrogine.lwjgl.ShaderProgram)
     */
    public static Box box = new Box();

    public void draw(ShaderProgram shader) {
        for (Group g : groups) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            box.draw(shader, g.getMin(), g.getMax());
            for (IBufferObject sg : g.subGroups) {
                sg.bind(shader);
                sg.draw(shader);
            }
        }
    }

    /**
     * Draw.
     *
     * @param shader
     *            the shader
     * @param handler
     *            the handler
     */
    public void draw(ShaderProgram shader, DrawHandler handler) {
        for (Group g : groups) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            box.draw(shader, g.getMin(), g.getMax());
            for (IBufferObject sg : g.subGroups) {
                sg.bind(shader);
                handler.beforeDraw(g, sg.getMaterial());
                sg.draw(shader);
                handler.afterDraw(g, sg.getMaterial());
            }
        }
    }
}
