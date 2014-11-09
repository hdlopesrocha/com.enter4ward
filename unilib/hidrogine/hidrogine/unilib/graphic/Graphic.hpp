#ifndef FRAMEWORK_GRAPHICS
#define FRAMEWORK_GRAPHICS

namespace unilib {
    class Model;
	class Material;
	class VertexPositionNormalTexture;
	class Camera;
	class Object;
	class ViewPort;
}

#include <string.h>
#include <string>
#include <math.h>
#include <cstdlib>
#include <iostream>
#include <sstream>
#include <ctime>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <fstream>
#include <vector>
#include <algorithm>
#include "../math/Math.hpp"

using namespace std;

namespace unilib {


   	class VertexPositionNormalTexture{
	  	public: Vector3 position;
	    public: Vector3 normal;
	    public: Vector2 texCoord;

	    public: VertexPositionNormalTexture(float px, float py, float pz);
	    public: VertexPositionNormalTexture(Vector3 pos);
	    public: VertexPositionNormalTexture(Vector3 pos, Vector3 nrm);
	    public: VertexPositionNormalTexture(Vector3 pos, Vector3 nrm, Vector2 tex);
	};




	class Material {
	  	public: char name[128];
	  	public: char filename[128];
	  	public: float alpha,ns,ni;
	  	public: float dif[4], amb[4], spec[4];
	  	public: int illum;
	  	public: int texture;
	  	public: Material();
	};

 	class Camera
    {

        public: Matrix _projectionMatrix, _viewMatrix, _worldMatrix;     // MATRICES NEEDED TO DRAW IN 3D
        public: Quaternion Rotation;

        public: BoundingFrustum _viewFrustum;                            // FRUSTUM (used to exclude objects outside the screen)
        public: Vector3 Position;          // CAMERA POSITION, DIRECTION, DIRECTION ROTATED
        public: Vector3 Up, Direction, Side;
        public: Camera();
		public: Camera(Vector3 position);

    	public: void Update(ViewPort * viewPort);
    };

	class Object
	{
		public: float yaw, pitch, roll;
		public: Vector3 position;
		public: Matrix matrix;

		public: Model * model;
		public: Object(Vector3 pos, Model * mod);
		public: Object(Matrix mat, Model * mod);
		public: void UpdateMatrix();

	};

	struct MaterialGroup {

		vector<unsigned int> indexs;
		string materialName;
		unsigned int indexsID;

		MaterialGroup(string mname){
			materialName = mname;
		};
	};

	struct Group {
		string name;
		int id;
		vector<MaterialGroup*> * materialGroups;
		float _rotW;
		BoundingSphere boundingSphere;
		vector<Vector3> vertices;

		Group(string n, int gid){
			materialGroups = new vector<MaterialGroup*>();
			name = n;
			id = gid;
			_rotW = 0;

		}
	};



	class ViewPort
	{
		public: unsigned int Width, Height;
		public: ViewPort();
		public: ViewPort(int w, int h);
	};	

	class Model
	{
		public: unsigned int _data;
		vector<Group*> groups;
		Group * defG;
		vector<Material*> allMaterials;
		vector<VertexPositionNormalTexture> vertex;
		BoundingSphere boundingSphere;


		public: Model();
		public: Model(string dir,string filename, float scale, float rx, float ry, float rz);
		public: void setDiffuse(string mat, float r, float g, float b);
	};	


	class COLOR
	{
		public: char R,G,B;
		public: static int clamp(int val, int min, int max);
		public: COLOR(int r,int g, int b);
	};

	class BITMAP
	{
		private: char * _data;
		private: int _height;
		private: int _width;
		private: int _bytesPerLine;
		private: int _bytesPerPixel;
		private: int _padding;
		private: int _offset;
		private: int _size;

		public: BITMAP(int width, int height);
		public: BITMAP(string path);
		public: void Clear(COLOR pixel);
		private: int _plot_l,_plot_c;
		public: void PixelPlot(int line, int column, COLOR pixel);
		public: void PlotReset();
		public: void PutPixel(int line, int column, COLOR pixel);
		public: COLOR GetPixel(int line, int column);
		public: void Save(string path);
		public: int GetHeight();
		public: int GetWidth();

	};

}
#endif