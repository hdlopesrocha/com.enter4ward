package hidrogine.lwjgl;

import hidrogine.math.BoundingSphere;
import hidrogine.math.IBufferBuilder;
import hidrogine.math.Group;
import hidrogine.math.IBufferObject;
import hidrogine.math.IModel3D;
import hidrogine.math.Material;
import hidrogine.math.Vector3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

// TODO: Auto-generated Javadoc
/**
 * The Class Model3D.
 */
public class XModel3D implements IModel3D {


	/** The groups. */
	protected List<Group> groups = new ArrayList<Group>();
	private BoundingSphere container;

	/** The materials. */
	protected TreeMap<String, Material> materials = new TreeMap<String, Material>();
	private IBufferBuilder bufferBuilder;

	
	public Iterable<Group> getGroups() {
		return groups;
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
	public XModel3D(FileInputStream mat, FileInputStream geo, float scale, IBufferBuilder builder) {
		this.bufferBuilder = builder;
		try {
	
			
			loadMaterials(mat);
			loadGeometry(geo, scale);
			
			geo.close();
			mat.close();

		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.math.api.IModel3D#getContainer()
	 */
	@Override
	public BoundingSphere getContainer() {
		return container;
	}

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
	private void loadMaterials(FileInputStream fis) throws JSONException,
			IOException {

		final JSONTokener tokener = new JSONTokener(fis);
		final JSONObject jObject = new JSONObject(tokener);
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
				final JSONArray array = jMat.getJSONArray("Tf");
				currentMaterial.Tf = new Float[3];
				for (int j = 0; j < 3; ++j)
					currentMaterial.Tf[j] = (float) array.getDouble(j);
				
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
	@SuppressWarnings("unchecked")
	private void loadGeometry(FileInputStream fis, final float scale)
			throws JSONException, IOException {
		final List<Vector3> points = new ArrayList<Vector3>();
		final JSONTokener tokener = new JSONTokener(fis);
		final JSONObject jObject = new JSONObject(tokener);
		final Iterator<String> groupNames = jObject.keys();
		while (groupNames.hasNext()) {
			final String groupName = groupNames.next();
			final Group currentGroup = new Group(groupName);
			final List<Vector3> groupPoints = new ArrayList<Vector3>();
			
			groups.add(currentGroup);
			final JSONArray subGroups = jObject.getJSONArray(groupName);
			for (int j = 0; j < subGroups.length(); ++j) {
				final JSONObject jSubGroup = subGroups.getJSONObject(j);
				
				
				
				final IBufferObject currentSubGroup = bufferBuilder.build();
				currentGroup.addBuffer(currentSubGroup);
				if (jSubGroup.has("mm")) {
					((BufferObject)currentSubGroup).setMaterial(materials.get(jSubGroup
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
					Vector3 pos = new Vector3(vx, vy, vz);
					groupPoints.add(pos);
					points.add(pos);
					currentSubGroup.addVertex(vx, vy, vz);
					currentSubGroup.addNormal(nx, ny, nz);
					currentSubGroup.addTexture(tx, 1f-ty);

				}
				final JSONArray ii = jSubGroup.getJSONArray("ii");
				for (int k = 0; k < ii.length(); ++k) {
					currentSubGroup.addIndex((short) ii.getInt(k));
				}
				currentSubGroup.buildBuffer();
			}
			
			currentGroup.createFromPoints(groupPoints);
		}
		container = new BoundingSphere().createFromPoints(points);
	}


}
