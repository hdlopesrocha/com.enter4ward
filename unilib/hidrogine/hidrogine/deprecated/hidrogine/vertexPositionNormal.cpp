#include "../hidrogine.hpp"

namespace framework {

    VertexPositionNormal::VertexPositionNormal(){
        position = normal = Vector3::Zero();
    }

    VertexPositionNormal::VertexPositionNormal(Vector3 pos, Vector2 tc){
      position = pos;
      texCoord = tc;
      normal = Vector3::Zero();
    }

    VertexPositionNormal::VertexPositionNormal(float px, float py, float pz){
      position = Vector3(px,py,pz);
    }

    VertexPositionNormal::VertexPositionNormal(Vector3 pos, Vector3 nrm){
      position = pos;
      normal = nrm;
    }
    
}