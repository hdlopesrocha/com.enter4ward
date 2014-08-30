#include "../hidrogine.hpp"

namespace framework {

    Planet::Planet(int l, float r,SDL_Surface * s0,SDL_Surface * s1,SDL_Surface * s2,SDL_Surface * s3,SDL_Surface * s4,SDL_Surface * s5){
        _radius = r;
        _levels = l;
       
        _sub[0] =new Region(this,0 ,NULL,Vector3(-1,+1,+1),Vector2(0,0),Vector3(+1,+1,+1),Vector2(1,0),Vector3(+1,-1,+1),Vector2(1,1),Vector3(-1,-1,+1),Vector2(0,1),s0); //+Z brazil (OK)
        _sub[1] =new Region(this,1 ,NULL,Vector3(+1,+1,+1),Vector2(0,0),Vector3(+1,+1,-1),Vector2(1,0),Vector3(+1,-1,-1),Vector2(1,1),Vector3(+1,-1,+1),Vector2(0,1),s1); //+x africa (OK)
        _sub[2] =new Region(this,2 ,NULL,Vector3(+1,+1,-1),Vector2(0,0),Vector3(-1,+1,-1),Vector2(1,0),Vector3(-1,-1,-1),Vector2(1,1),Vector3(+1,-1,-1),Vector2(0,1),s2); //-Z asia
        _sub[3] =new Region(this,3 ,NULL,Vector3(-1,+1,-1),Vector2(0,0),Vector3(-1,+1,+1),Vector2(1,0),Vector3(-1,-1,+1),Vector2(1,1),Vector3(-1,-1,-1),Vector2(0,1),s3); //-X australia (OK)
        _sub[4] =new Region(this,4 ,NULL,Vector3(-1,+1,+1),Vector2(0,0),Vector3(-1,+1,-1),Vector2(1,0),Vector3(+1,+1,-1),Vector2(1,1),Vector3(+1,+1,+1),Vector2(0,1),s4); //+Y polo norte (OK)
        _sub[5] =new Region(this,5 ,NULL,Vector3(+1,-1,+1),Vector2(0,0),Vector3(+1,-1,-1),Vector2(1,0),Vector3(-1,-1,-1),Vector2(1,1),Vector3(-1,-1,+1),Vector2(0,1),s5); //-Y polo sul (OK)
        
        for(int i=0; i < 6; ++i){
            _sub[i]->SetNeighbors(this);
        }

    }


    Region * Planet::GetRegion(Vector3 vec, int level){
        if(_sub!=NULL){
            for (int i = 0; i < 6; ++i)
            {
                if(_sub[i]!=NULL && _sub[i]->InsideRegion(vec))
                    return GetRegion(_sub[i],vec,level);
            }
        }
        return NULL;
    }


    Region * Planet::GetRegion(Region * reg ,Vector3 vec, int level){
        if(level==0)
            return reg;

        else if(reg->_sub!=NULL){
            for (int i = 0; i < 4; ++i)
            {
                if(reg->_sub[i]!=NULL && reg->_sub[i]->InsideRegion(vec))
                    return GetRegion(reg->_sub[i],vec,level-1);
            }
        }
        return NULL;
    }

};
