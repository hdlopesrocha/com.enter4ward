#include "../hidrogine.hpp"

namespace framework {


    Vector3 Region::ToAngle(Vector3 vec){
        vec.X = atan(vec.X);
        vec.Y = atan(vec.Y);
        vec.Z = atan(vec.Z);
        return vec;
    }


    Region::Region(Planet * planet,char index,Region * parent,   
                Vector3 a,Vector2 ta,
                Vector3 b,Vector2 tb,
                Vector3 c,Vector2 tc, 
                Vector3 d,Vector2 td, SDL_Surface * surf){

        _surf = surf;
        _list = 0;

        _points[0] = Vector3::Normalize(a)* (float)(getHeight(_surf,ta,8848.0*4.0)+planet->_radius);
        _points[1] = Vector3::Normalize(b)* (float)(getHeight(_surf,tb,8848.0*4.0)+planet->_radius);
        _points[2] = Vector3::Normalize(c)* (float)(getHeight(_surf,tc,8848.0*4.0)+planet->_radius);
        _points[3] = Vector3::Normalize(d)* (float)(getHeight(_surf,td,8848.0*4.0)+planet->_radius);


        _neighbor[0]=_neighbor[1]=_neighbor[2]=_neighbor[3]=NULL;


        _texCoords[0] = ta;
        _texCoords[1] = tb;
        _texCoords[2] = tc;
        _texCoords[3] = td;




        _sphere = BoundingSphere(_points[0], 1);
        for(int i=1 ; i < 4 ; ++i)
           _sphere = BoundingSphere::CreateMerged(_sphere, BoundingSphere(_points[i],1)); 


    


        _index = index;
        _sub =NULL;
        _parent = parent;


        _minHeight = _maxHeight = planet->_radius;

        int i;
        _sphere = BoundingSphere(_points[0], 1);
        for(i=1 ; i < 4 ; ++i)
           _sphere = BoundingSphere::CreateMerged(_sphere, BoundingSphere(_points[i],1)); 



    }


    void Region::SubDraw(Planet * planet,int l){

        if(_list==0){
            if(l<=0){
                SimpleDraw();
            }
            else {
                for(int i=0;i < 4 ; ++i){
                   GetRegion(planet,i)->SubDraw(planet,l-1);
                }
            }
        }
    }

    void Region::SimpleDraw(){
        for (int i = 3; i >= 0 ; --i){
            Vector3  * vpn = &(_points[i]);
            Vector2 * vtex = &(_texCoords[i]);
            glTexCoord2fv((float*)vtex);

            Vector3 nrm = GetNormal(i);

            glNormal3fv((float*)&nrm);
            glVertex3fv((float*)vpn);
        }

    }



    void Region::Draw(Planet * planet){
        if(_list==0){
            _list = glGenLists(1);
            glNewList(_list, GL_COMPILE);
            SubDraw(planet,4);
            glEndList();
        }
        glCallList(_list);
    }

    void Region::SetNeighbors(Planet * planet){
        Vector3 rel;
        int levels = 0;
        for(Region * ptr=this;ptr!=NULL;ptr=ptr->_parent)
            ++levels;

        for(int i=0; i <4 ; ++i){
            if(_neighbor[i]==NULL){
                rel =  EstimateRelative(i);
                _neighbor[i] = planet->GetRegion(rel,levels-1);
                if(_neighbor[i]!=NULL){
                    _neighbor[i]->SetNeighbors(planet);
                }
            }
        }
    }


    string Region::GetName(){
        string s = "";
        if(_parent!=NULL)
            s = _parent->GetName() + (char)(_index+'0');
        else
            s = (char)(_index+'0');

        return s;
    }



//XXX ESTA INCORRECTO, FORMA SUBDIVISOES ERRADAS
    Region * Region::Divide(Planet * planet,int i){
        Vector3 centerP =(_points[0] + _points[1] + _points[2] + _points[3])*0.25;
        Vector2 centerT =(_texCoords[0] + _texCoords[1] + _texCoords[2] + _texCoords[3])*0.25;

        switch(i){
            case 0: return new Region(planet,i,this,
                        _points[0], _texCoords[0],
                        (_points[0]+_points[1])*0.5, (_texCoords[0]+_texCoords[1])*0.5,
                        centerP,centerT,
                        (_points[0]+_points[3])*0.5,(_texCoords[0]+_texCoords[3])*0.5,_surf);

            case 1: return new Region(planet,i,this,
                        (_points[0]+_points[1])*0.5, (_texCoords[0]+_texCoords[1])*0.5,
                        _points[1], _texCoords[1],
                        (_points[1]+_points[2])*0.5,(_texCoords[1]+_texCoords[2])*0.5,
                        centerP,centerT,_surf);

            case 2: return new Region(planet,i,this,
                        centerP,centerT,
                        (_points[1]+_points[2])*0.5, (_texCoords[1]+_texCoords[2])*0.5,
                        _points[2], _texCoords[2],
                        (_points[2]+_points[3])*0.5,(_texCoords[2]+_texCoords[3])*0.5,_surf);

            case 3: return new Region(planet,i,this,
                        (_points[0]+_points[3])*0.5, (_texCoords[0]+_texCoords[3])*0.5,
                        centerP,centerT,
                        (_points[2]+_points[3])*0.5,(_texCoords[2]+_texCoords[3])*0.5,
                        _points[3], _texCoords[3],_surf);
            default: break;

        }
        return NULL;
    }

    Region * Region::GetRegion(Planet * planet,int i){
        if(_sub==NULL){
            _sub = new Region*[4];
            for(int j=0; j < 4; ++j)
                _sub[j]=NULL;
        }

        if(_sub[i]==NULL){
            _sub[i] = Divide(planet,i);
            _sub[i]->SetNeighbors(planet);
        }
        return _sub[i];
    }

    // 4 planes 
    // each plane has 2 points of region and center of planet
    // point must be up those 4 planes
    bool Region::InsideRegion(Vector3 vec){
        Plane plane;
        for(int i=0; i <4 ; ++i){
            plane = Plane(Vector3::Zero(), _points[(i+1)%4],_points[i]);
            if(plane.DotNormal(vec)<0)
                return false;
        }
        return true;
    }


    // refPlane = plane of direction we whant
    // midPoint = midploint (intersects with plane)

    Vector3 Region::EstimateRelative(int direction){
        Vector3 center = (_points[0] + _points[1] + _points[2] + _points[3])*0.25;
        Vector3 midPoint = (_points[(direction+1)%4] + _points[direction])*0.5;
        return midPoint*2 - center;
    }


    Vector3 Region::GetSimpleNormal(int i){
        Vector3 a = Vector3::Cross(_points[MathHelper::Mod(i+3,4)]-_points[i], _points[MathHelper::Mod(i+2,4)]-_points[i]);
        Vector3 b = Vector3::Cross(_points[MathHelper::Mod(i+2,4)]-_points[i], _points[MathHelper::Mod(i+1,4)]-_points[i]);


        return  Vector3::Normalize(a+b);

    }


    Vector3 Region::GetNormal(int i){
        Vector3 nrms = GetSimpleNormal(i);

        Region * reg;
        reg = _neighbor[(i+3)%4];
        if(reg!=NULL){
            nrms += reg->GetSimpleNormal(MathHelper::Mod(i+1,4));
            reg = reg->_neighbor[i];
            if(reg!=NULL)
                nrms += reg->GetSimpleNormal(MathHelper::Mod(i+2,4));
        }

        reg = _neighbor[i];
        if(reg!=NULL)
            nrms += reg->GetSimpleNormal(MathHelper::Mod(i+3,4));


        return Vector3::Normalize(nrms);
    }



    Vector3 Region::Closest(Vector3 vec){
        Plane plane = GetPlane();
        Ray ray = Ray(vec,plane.Normal*(-1));
        
        float inter = ray.Intersects(plane);
        if(inter>0 && FastInside(ray.Direction*inter+ray.Position)){
            return ray.Position + ray.Direction*inter;
        }
        else{
            float d0 = (vec - _points[0]).Length();
            float d1 = (vec - _points[1]).Length();
            float d2 = (vec - _points[2]).Length();
            float d3 = (vec - _points[3]).Length();
            
            float min = d0;
            int mindex = 0;
            if(d1<min) {min = d1;mindex=1;}
            if(d2<min) {min = d2;mindex=2;}
            if(d3<min) {min = d3;mindex=3;}


            return _points[mindex];
        }
    }



    float Region::Intersects(Ray ray){
        float inter = ray.Intersects(GetPlane());
        if(inter>0 && FastInside(ray.Direction*inter+ray.Position)){
            return inter;
        }
        return -1;
    }

    Plane Region::GetPlane(){
        return Plane(_points[0],_points[1],_points[3]);
    }

    bool Region::FastInside(Vector3 vec){
        float len = (_points[0]-_points[2]).Length();
        for(int i=0 ; i < 4 ; ++i){
            if((vec-_points[i]).Length()>len)
                return false;
        }
        return true;
    }

};
