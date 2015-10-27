#include "Group.h"
#include <math.h>


namespace hidrogine {
    
    Group::Group(string n) {
        name=n;
		subGroups = new LinkedList<BufferObject*>();
    }

    string Group::getName() {
        return name;
    }

    void Group::addVertex(float vx, float vy, float vz) {
       if(max==NULL){
           max = Vector3(vx,vy,vz);
           min = Vector3(vx,vy,vz);
       }
       else {
           max.X = std::max(max.X, vx);
           max.Y = std::max(max.Y, vy);
           max.Z = std::max(max.Z, vz);
           min.X = std::min(min.X, vx);
           min.Y = std::min(min.Y, vy);
           min.Z = std::min(min.Z, vz);
       }
    }

	Vector3 Group::getCenter(){
        return (min+max)/2;
    }
    
    Vector3 Group::getMin() {
        return min;
    }
    
    Vector3 Group::getMax() {
        return max;
    }
}