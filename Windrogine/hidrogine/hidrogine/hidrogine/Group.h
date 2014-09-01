#ifndef HIDROGINE_GROUP
#define HIDROGINE_GROUP
#include <string>
#include "../unilib/util/LinkedList.h"
#include "../unilib/math/Math.hpp"
#include "BufferObject.h"
using namespace unilib;
using namespace std;

namespace hidrogine {

	class Group {
		public: LinkedList<BufferObject*> * subGroups; 
		private: Vector3 min;
		private: Vector3 max;
		private: string name;
		public: Group(string n);
		public: string getName();
		public: void addVertex(float vx, float vy, float vz);
		public: Vector3 getCenter();
		public: Vector3 getMin();
		public: Vector3 getMax();
	};
}

#endif