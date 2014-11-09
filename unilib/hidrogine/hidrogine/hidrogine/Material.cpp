#include "Material.h"

namespace hidrogine {
    Material::Material(string n) {
        name = n;
    }
    
    void Material::setTexture(string filename) {
        texture = TextureLoader::loadTexture(filename);
    }

    string Material::getName() {
        return name;
    }
}