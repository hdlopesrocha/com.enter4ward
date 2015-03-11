package hidrogine.lwjgl;

import hidrogine.math.BoundingSphere;
import hidrogine.math.IModel3D;
import hidrogine.math.IObject3D;
import hidrogine.math.Matrix;
import hidrogine.math.Vector2;
import hidrogine.math.Vector3;

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

// TODO: Auto-generated Javadoc
/**
 * The Class Model3D.
 */
public class Model3D implements IModel3D {


	/** The groups. */
	private List<Group> groups = new ArrayList<Group>();
	private BoundingSphere container;

	/** The materials. */
	private TreeMap<String, Material> materials = new TreeMap<String, Material>();
	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.Model#draw(hidrogine.lwjgl.ShaderProgram)
	 */
	/** The box. */
	private static DrawableBox box = new DrawableBox();
	
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
	public Model3D(String materials, String geometry, float scale, boolean explodeTriangles) {
		try {
			loadMaterials(materials);
			loadGeometry(geometry, scale,explodeTriangles);
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
	private void loadMaterials(String filename) throws JSONException,
			IOException {

		final JSONObject jObject = new JSONObject(new String(
				Files.readAllBytes(Paths.get(filename))));
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
	private void loadGeometry(final String filename, final float scale, final boolean explodeTriangles)
			throws JSONException, IOException {
		final List<Vector3> points = new ArrayList<Vector3>();
		final FileInputStream file = new FileInputStream(filename);
		final JSONTokener tokener = new JSONTokener(file);
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
				final BufferObject currentSubGroup = new BufferObject(explodeTriangles);
				currentGroup.addBuffer(currentSubGroup);
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
					Vector3 pos = new Vector3(vx, vy, vz);
					Vector3 nrm = new Vector3(nx, ny, nz);
					Vector2 tex = new Vector2(tx, 1f-ty);
					groupPoints.add(pos);
					points.add(pos);
					currentSubGroup.addPosition(pos);
					currentSubGroup.addNormal(nrm);
					currentSubGroup.addTextureCoord(tex);

				}
				final JSONArray ii = jSubGroup.getJSONArray("ii");
				for (int k = 0; k < ii.length(); ++k) {
					currentSubGroup.addIndex((short) ii.getInt(k));
				}
				currentSubGroup.buildBuffer();
			}
			
			currentGroup.createFromPoints(groupPoints);
		}
		file.close();
		container = new BoundingSphere().createFromPoints(points);
	}



	/**
	 * Draw.
	 *
	 * @param shader
	 *            the shader
	 * @param handler
	 *            the handler
	 */
	public void draw(IObject3D obj, ShaderProgram shader, DrawHandler handler) {
		for (Group g : groups) {
			for (BufferObject sg : g.getBuffers()) {
				sg.bind(shader);
				Matrix mat=	handler.onDraw(obj,g, sg);
				if(mat!=null){
					shader.setModelMatrix(mat);	
					sg.draw(shader);
				}
			}
		}
		
	}

	/**
	 * Draw boxs.
	 *
	 * @param shader
	 *            the shader
	 */
	public void drawBoxs(ShaderProgram shader) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		for (Group g : groups) {
			box.draw(shader, Vector3.temp().set(g).subtract(g.getRadius()), Vector3.temp().set(g).add(g.getRadius()));
		}
	}

}
