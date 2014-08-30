#ifndef FRAMEWORK_HIDROGINE
#define FRAMEWORK_HIDROGINE

#define NB_UP 3
#define NB_RIGHT 0
#define NB_DOWN 1
#define NB_LEFT 2


namespace framework {
    class VertexDataBase;
    class VertexPositionNormal;
    class Planet;
    class Region;
}

#include <string>
#include <math.h>
#include <cstdlib>
#include <iostream>
#include <sstream>
#include <vector>
#include "math.hpp"
#include "util.hpp"
#include "graphic.hpp"
#include "../api/openGL/OGLModel.hpp"

using namespace api;
using namespace std;

namespace framework {
 
    
    class VertexPositionNormal{
        public: Vector3 position;
        public: Vector3 normal;
        public: Vector2 texCoord;
        public: VertexPositionNormal();
        public: VertexPositionNormal(float px, float py, float pz);
        public: VertexPositionNormal(Vector3 pos, Vector2 tc);
        public: VertexPositionNormal(Vector3 pos, Vector3 nrm);
    };


    class VertexDataBase {
        private:TreeMap<float,TreeMap<float,TreeMap<float,Vector3*>*>*>* _database;
        public: Stack<Vector3*> *_all;
        public: VertexDataBase();
        public: ~VertexDataBase();
        public: Vector3 * Insert(Vector3 vertex);
    };



    class Region {
        public: int _list;
        public: Region ** _sub;
        public: float _minHeight, _maxHeight;
        public: BoundingSphere _sphere;
        public: char _index;
        public: Region * _parent;
        public: Region * _neighbor[4];
        public: Vector3 _points[4];
        public: Vector2 _texCoords[4];
        public: SDL_Surface * _surf;

        public: Region(Planet * planet,char index,Region * parent,   
                    Vector3 a,Vector2 ta,
                    Vector3 b,Vector2 tb,
                    Vector3 c,Vector2 tc, 
                    Vector3 d,Vector2 td,SDL_Surface * surf);

        public: static Vector3 ToAngle(Vector3 vec);
        public: Region * Divide(Planet * planet,int i);
        public: Region * GetRegion(Planet * planet,int i);
        public: bool InsideRegion(Vector3 vec);
        public: Vector3 EstimateRelative(int direction);
        public: Vector3 GetNormal(int i);
        public: Vector3 GetSimpleNormal(int i);

        public: Vector3 Closest(Vector3 vec);
        public: float Intersects(Ray ray);
        public: Plane GetPlane();
        public: bool FastInside(Vector3 vec);
        public: void SetNeighbors(Planet * planet);
        public: string GetName();
        public: void Draw(Planet * planet);
        public: void SimpleDraw();
        public: void SubDraw(Planet * planet,int l);


    };





    class Planet {

        public: Region * _sub[6];
        public: int tex[6];
        public: int tex0;
        public: float _radius;
        public: int _levels;

        public: Planet(int l, float r,SDL_Surface * s0,SDL_Surface * s1,SDL_Surface * s2,SDL_Surface * s3,SDL_Surface * s4,SDL_Surface * s5);
        public: Region * GetRegion(Vector3 vec, int level);
        public: Region * GetRegion(Region * reg ,Vector3 vec, int level);

    };



}
#endif