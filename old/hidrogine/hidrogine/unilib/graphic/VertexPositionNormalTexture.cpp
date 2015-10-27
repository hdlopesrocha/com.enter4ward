#include "Graphic.hpp"

namespace unilib {

    VertexPositionNormalTexture::VertexPositionNormalTexture(Vector3 pos){
      position = pos;
    }

    VertexPositionNormalTexture::VertexPositionNormalTexture(float px, float py, float pz){
      position = Vector3(px,py,pz);
    }

    VertexPositionNormalTexture::VertexPositionNormalTexture(Vector3 pos, Vector3 nrm){
      position = pos;
      normal = nrm;
    }
    
    VertexPositionNormalTexture::VertexPositionNormalTexture(Vector3 pos, Vector3 nrm, Vector2 tex){
      position = pos;
      normal = nrm;
      texCoord = tex;
    }

}