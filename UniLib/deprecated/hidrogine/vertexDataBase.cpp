#include "../hidrogine.hpp"

namespace framework {
   
    VertexDataBase::VertexDataBase(){
        _database = new TreeMap<float,TreeMap<float,TreeMap<float,Vector3*>*>*>(new FloatComparator());
        _all = new Stack<Vector3*>();
    }

    VertexDataBase::~VertexDataBase(){
        Iterator<TreeMap<float,TreeMap<float,Vector3*>*>*> * it1 = _database->iterator();
        while(it1->isValid()){
            Iterator<TreeMap<float,Vector3*>*> * it2 = it1->getValue()->iterator();
            while(it2->isValid()){
                delete it2->getValue();
                it2->next();
            }
            delete it1->getValue();
            it1->next();
            delete it2;
        }
        delete it1;
        delete _database;
        delete _all;
    }

    Vector3 * VertexDataBase::Insert(Vector3 vertex){
        TreeMap<float,TreeMap<float,Vector3*>*>* xTarget = _database->get(vertex.X);
        if(xTarget==NULL){
            xTarget = new TreeMap<float,TreeMap<float,Vector3*>*>(new FloatComparator());
            _database->put(vertex.X,xTarget);
        }

        TreeMap<float,Vector3*>* yTarget = xTarget->get(vertex.Y);
        if(yTarget==NULL){
            yTarget = new TreeMap<float,Vector3*>(new FloatComparator());
            xTarget->put(vertex.Y,yTarget);
        }

        Vector3 * zTarget = yTarget->get(vertex.Z);
        if(zTarget==NULL){
            zTarget = new Vector3(vertex.X,vertex.Y,vertex.Z);
            _all->push(zTarget);
            yTarget->put(vertex.Z,zTarget);
        }
 
        return zTarget;
    }
}