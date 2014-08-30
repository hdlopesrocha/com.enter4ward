#include "Graphic.hpp"

namespace unilib {
	Material::Material(){
		strcpy(name,"default");
    strcpy(filename,"");

  		alpha = 1;
  		ns = 1;
  		ni = 1;
  		dif[0]=1;
  		dif[1]=1;
  		dif[2]=1;  	
      dif[3]=1;

  		amb[0]=1;
  		amb[1]=1;
  		amb[2]=1;
      amb[3]=1;

  		spec[0]=1;
  		spec[1]=1;
  		spec[2]=1;
      spec[3]=1;

  		illum = 0;
  		texture = 0;
  	}
}