package com.enter4ward.math;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

// TODO: Auto-generated Javadoc
/**
 * The Class Model3D.
 */
public class Model3D implements IModel3D {

    /** The groups. */
    protected List<Group> groups = new ArrayList<Group>();
    // private BoundingSphere container;

    /** The materials. */
    protected TreeMap<String, Material> materials = new TreeMap<String, Material>();

    /** The container. */
    private BoundingSphere container;

    /**
     * Gets the groups.
     *
     * @return the groups
     */
    public Iterable<Group> getGroups() {
        return groups;
    }

    /**
     * Instantiates a new model3 d.
     */
    public Model3D() {

    }

    /** The lights. */
    private List<Vector3> lights = new ArrayList<Vector3>();

    /**
     * Gets the lights.
     *
     * @return the lights
     */
    public List<Vector3> getLights() {
        return lights;
    }

    /**
     * Instantiates a new model3 d.
     *
     * @param fis
     *            the fis
     * @param scale
     *            the scale
     * @param builder
     *            the builder
     * @param rot
     *            the rot
     */
    

    
    public Model3D(String filename, float scale, IBufferBuilder builder,
            final Quaternion rot) {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(filename);

    	
        try {

            JsonParser jsonParser = new JsonFactory().createParser(stream);
            // loop through the JsonTokens
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String name = jsonParser.getCurrentName();
                if ("materials".equals(name)) {
                    jsonParser.nextToken();
                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                        String materialName = jsonParser.getCurrentName();
                        Material currentMaterial = new Material(materialName);
                        materials.put(materialName, currentMaterial);
                        jsonParser.nextToken();
                        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                            String key = jsonParser.getCurrentName();
                            jsonParser.nextToken();
                            if ("map_Kd".equals(key)) {
                                String value = jsonParser.getValueAsString();
                                currentMaterial.setTexture(value);
                            } else if ("Tf".equals(key)) {

                                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                    float x = jsonParser.getFloatValue();
                                    jsonParser.nextToken();
                                    float y = jsonParser.getFloatValue();
                                    jsonParser.nextToken();
                                    float z = jsonParser.getFloatValue();
                                    currentMaterial.Tf = new Float[3];
                                    currentMaterial.Tf[0] = x;
                                    currentMaterial.Tf[1] = y;
                                    currentMaterial.Tf[2] = z;
                                }

                            } else if ("Ka".equals(key)) {

                                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                    float x = jsonParser.getFloatValue();
                                    jsonParser.nextToken();
                                    float y = jsonParser.getFloatValue();
                                    jsonParser.nextToken();
                                    float z = jsonParser.getFloatValue();
                                    currentMaterial.Ka = new Float[3];
                                    currentMaterial.Ka[0] = x;
                                    currentMaterial.Ka[1] = y;
                                    currentMaterial.Ka[2] = z;
                                }

                            } else if ("Kd".equals(key)) {

                                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                    float x = jsonParser.getFloatValue();
                                    jsonParser.nextToken();
                                    float y = jsonParser.getFloatValue();
                                    jsonParser.nextToken();
                                    float z = jsonParser.getFloatValue();
                                    currentMaterial.Kd = new Float[3];
                                    currentMaterial.Kd[0] = x;
                                    currentMaterial.Kd[1] = y;
                                    currentMaterial.Kd[2] = z;
                                }

                            } else if ("Ks".equals(key)) {

                                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                    float x = jsonParser.getFloatValue();
                                    jsonParser.nextToken();
                                    float y = jsonParser.getFloatValue();
                                    jsonParser.nextToken();
                                    float z = jsonParser.getFloatValue();
                                    currentMaterial.Ks = new Float[3];
                                    currentMaterial.Ks[0] = x;
                                    currentMaterial.Ks[1] = y;
                                    currentMaterial.Ks[2] = z;
                                }

                            } else if ("illum".equals(key)) {
                                Float value = jsonParser.getFloatValue();
                                currentMaterial.illum = value;
                            } else if ("d".equals(key)) {
                                Float value = jsonParser.getFloatValue();
                                currentMaterial.d = value;
                            } else if ("Ns".equals(key)) {
                                Float value = jsonParser.getFloatValue();
                                currentMaterial.Ns = value;
                            } else if ("sharpness".equals(key)) {
                                Float value = jsonParser.getFloatValue();
                                currentMaterial.sharpness = value;
                            } else if ("Ni".equals(key)) {
                                Float value = jsonParser.getFloatValue();
                                currentMaterial.Ni = value;
                            }
                        }
                    }

                } else if ("lights".equals(name)) {
                    jsonParser.nextToken(); // [
                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) { // [[
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY) { // [[(...)]
                            float x = jsonParser.getFloatValue() * scale;
                            jsonParser.nextToken();
                            float y = jsonParser.getFloatValue() * scale;
                            jsonParser.nextToken();
                            float z = jsonParser.getFloatValue() * scale;
                            Vector3 vec = new Vector3(x, y, z);
                            lights.add(vec);
                        }
                    }

                } else if ("groups".equals(name)) {
                    final List<Vector3> points = new ArrayList<Vector3>();
                    jsonParser.nextToken(); // {
                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                        String groupName = jsonParser.getCurrentName();
                        jsonParser.nextToken(); // [
                        Group currentGroup = new Group(groupName);
                        final List<Vector3> groupPoints = new ArrayList<Vector3>();

                        groups.add(currentGroup);
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                            // jsonParser.nextToken(); // {
                            IBufferObject currentSubGroup = builder.build();
                            currentGroup.addBuffer(currentSubGroup);
                            // System.out.println("JSON INIT");
                            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                String key = jsonParser.getCurrentName();

                                jsonParser.nextToken(); // [
                                if ("mm".equals(key)) {
                                    String mm = jsonParser.getValueAsString();
                                    Material mat = materials.get(mm);
                                    if (mat != null)
                                        currentSubGroup.setMaterial(mat);
                                } else if ("vv".equals(key)) {
                                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                        float x = jsonParser.getFloatValue()
                                                * scale;

                                        jsonParser.nextToken();
                                        float y = jsonParser.getFloatValue()
                                                * scale;
                                        jsonParser.nextToken();
                                        float z = jsonParser.getFloatValue()
                                                * scale;
                                        Vector3 vec = new Vector3(x, y, z);

                                        if (rot != null) {
                                            vec.transform(rot);
                                        }
                                        points.add(vec);
                                        groupPoints.add(vec);
                                        currentSubGroup.addVertex(vec.getX(),
                                                vec.getY(), vec.getZ());
                                    }
                                } else if ("vn".equals(key)) {

                                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                        float x = jsonParser.getFloatValue();
                                        jsonParser.nextToken();
                                        float y = jsonParser.getFloatValue();
                                        jsonParser.nextToken();
                                        float z = jsonParser.getFloatValue();
                                        Vector3 vec = new Vector3(x, y, z);
                                        if (rot != null) {
                                            vec.transform(rot);
                                        }
                                        currentSubGroup.addNormal(vec.getX(),
                                                vec.getY(), vec.getZ());
                                    }
                                } else if ("vt".equals(key)) {

                                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                        float x = jsonParser.getFloatValue();
                                        jsonParser.nextToken();
                                        float y = 1f - jsonParser
                                                .getFloatValue();
                                        currentSubGroup.addTexture(x, y);
                                    }
                                } else if ("ii".equals(key)) {

                                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                        short val = jsonParser.getShortValue();
                                        currentSubGroup.addIndex(val);
                                    }
                                }
                            }
                            // System.out.println("JSON END");
                            currentSubGroup.buildBuffer();
                            // System.out.println("JSON BUILD BUFFER");
                        }
                        currentGroup.createFromPoints(groupPoints);
                    }
                    container = new BoundingSphere().createFromPoints(points);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.enter4ward.math.api.IModel3D#getContainer()
     */
    @Override
    public BoundingSphere getContainer() {

        return container;
    }

}
