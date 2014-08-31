package ru.serjik.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MeshLoader
{
	private List<Vector> verticies = new ArrayList<Vector>();
	private List<Vector> normals = new ArrayList<Vector>();
	private List<TexCoord> texCoordinates = new ArrayList<TexCoord>();
	private List<Face> faces = new ArrayList<Face>();
	private Map<String, MeshData> meshes = new HashMap<String, MeshData>();
	private String currentMeshName = null;

	public MeshLoader(List<String> lines)
	{
		for (String line : lines)
		{
			String[] values = line.split(" ", 0);

			if (values.length > 0)
			{
				if (values[0].equals("o"))
				{
					meshes.put(values[1], new MeshData(faces.size(), 0));

					if (currentMeshName != null)
					{
						meshes.get(currentMeshName).end = faces.size();
					}

					currentMeshName = values[1];
				}

				if (values[0].equals("v"))
				{
					verticies.add(new Vector(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float
							.parseFloat(values[3])));
				}

				if (values[0].equals("vn"))
				{
					normals.add(new Vector(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float
							.parseFloat(values[3])));
				}

				if (values[0].equals("vt"))
				{
					texCoordinates.add(new TexCoord(Float.parseFloat(values[1]), Float.parseFloat(values[2])));
				}

				if (values[0].equals("f"))
				{
					faces.add(new Face(values));
				}
			}
		}

		if (currentMeshName != null)
		{
			meshes.get(currentMeshName).end = faces.size();
		}
	}

	public String[] meshes()
	{
		Set<String> keySet = meshes.keySet();
		String[] keys = new String[keySet.size()];
		keySet.toArray(keys);
		return keys;
	}

	public FloatBuffer data(String meshName)
	{
		// vx vy vz nx ny nz tu tv

		MeshData meshData = meshes.get(meshName);

		FloatBuffer fb = ByteBuffer.allocateDirect(4 * 8 * 3 * (meshData.end - meshData.start))
				.order(ByteOrder.nativeOrder()).asFloatBuffer();

		fb.position(0);

		for (int i = meshData.start; i < meshData.end; i++)
		{
			Face face = faces.get(i);

			for (int j = 0; j < 3; j++)
			{
				try
				{
					fb.put(verticies.get(face.v[j]).x);
					fb.put(verticies.get(face.v[j]).y);
					fb.put(verticies.get(face.v[j]).z);

					fb.put(normals.get(face.n[j]).x);
					fb.put(normals.get(face.n[j]).y);
					fb.put(normals.get(face.n[j]).z);

					fb.put(texCoordinates.get(face.t[j]).u);
					fb.put(texCoordinates.get(face.t[j]).v);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return fb;
	}

	private class MeshData
	{
		public int start;
		public int end;

		public MeshData(int start, int end)
		{
			this.start = start;
			this.end = end;
		}
	}

	private class Vector
	{
		public float x, y, z;

		public Vector(float x, float y, float z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	private class TexCoord
	{
		public float u, v;

		public TexCoord(float u, float v)
		{
			this.u = u;
			this.v = 1.0f - v;
		}
	}

	private class Face
	{
		public short v[] = new short[3];
		public short t[] = new short[3];
		public short n[] = new short[3];

		public Face(String[] valuesLine)
		{
			String[] values = valuesLine[1].split("/", 0);
			v[0] = (short) (Short.parseShort(values[0]) - 1);
			t[0] = (short) (Short.parseShort(values[1]) - 1);
			n[0] = (short) (Short.parseShort(values[2]) - 1);

			values = valuesLine[2].split("/", 0);
			v[1] = (short) (Short.parseShort(values[0]) - 1);
			t[1] = (short) (Short.parseShort(values[1]) - 1);
			n[1] = (short) (Short.parseShort(values[2]) - 1);

			values = valuesLine[3].split("/", 0);
			v[2] = (short) (Short.parseShort(values[0]) - 1);
			t[2] = (short) (Short.parseShort(values[1]) - 1);
			n[2] = (short) (Short.parseShort(values[2]) - 1);
		}
	}

}
