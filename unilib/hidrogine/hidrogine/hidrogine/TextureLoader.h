#ifndef HIDROGINE_TEXTURE_LOADER
#define HIDROGINE_TEXTURE_LOADER
#include <string>
#include "../unilib/util/TreeMap.h"
using namespace unilib;
using namespace std;
namespace hidrogine {
	class TextureLoader {
		private: static TreeMap<string,int> loadedTextures;
		public: static int loadTexture(string filename);
	};
}

#endif