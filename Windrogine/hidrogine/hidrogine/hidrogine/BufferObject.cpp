#include "BufferObject.h"

namespace hidrogine {
    Material* BufferObject::getMaterial() {
        return material;
    }



    /**
     * Instantiates a new buffer object.
     */
    BufferObject::BufferObject() {
		
		positions = new LinkedList<float>();
		normals = new LinkedList<float>();
		textureCoords = new LinkedList<float>();
		indexData = new LinkedList<short>();
    }

   void BufferObject::setMaterial(Material *f) {
        material = f;
    }

	void BufferObject::addPosition(float vx, float vy, float vz) {
        positions->Add(vx);
        positions->Add(vy);
        positions->Add(vz);

    }

   void BufferObject::addNormal(float nx, float ny, float nz) {
        normals->Add(nx);
        normals->Add(ny);
        normals->Add(nz);
    }

    void BufferObject::addTextureCoord(float tx, float ty) {

        textureCoords->Add(tx);
        textureCoords->Add(1 - ty);
    }

    void BufferObject::addIndex(short f) {
        indexData->Add(f);
    }

    void BufferObject::buildBuffer() {
		LinkedList<float> * packedVector = new LinkedList<float>();
        while (positions->Size() > 0 && normals->Size() > 0
                && textureCoords->Size() > 0) {
            packedVector->Add(positions->Remove(0));
            packedVector->Add(positions->Remove(0));
            packedVector->Add(positions->Remove(0));
            packedVector->Add(normals->Remove(0));
            packedVector->Add(normals->Remove(0));
            packedVector->Add(normals->Remove(0));
            packedVector->Add(textureCoords->Remove(0));
            packedVector->Add(textureCoords->Remove(0));
        }
		
        indexCount = indexData->Size();
  
		
        // Create a new Vertex Array Object in memory and select it (bind)
		glGenVertexArrays(1,&vaoId);
        glGenBuffers(1,&vboiId);
        glGenBuffers(1,&vboId);


        glBindVertexArray(vaoId);
        // Create a new Vertex Buffer Object in memory and select it (bind)
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, packedVector->Size(), packedVector->ToArray(), GL_STATIC_DRAW);

        // Put the position coordinates in attribute list 0
		
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8*4, (void*)(0));
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8*4, (void*)(3*4));
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8*4, (void*)(6*4));

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Deselect (bind to 0) the VAO
        glBindVertexArray(0);

        // Create a new VBO for the indices and select it (bind) - INDICES
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboiId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexData->Size(),indexData->ToArray(), GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		delete indexData->Clear();
        delete normals->Clear();
        delete positions->Clear();
        delete textureCoords->Clear();
		delete packedVector->Clear();
    }

    void BufferObject::bind(ShaderProgram * shader) {
        int tex = material != NULL ? material->texture : 0;
        // Bind the texture according to the set texture filter
        if (material != NULL) {
            if (material->Ns != NULL)
                shader->setMaterialShininess(material->Ns);
            if (material->Ks != NULL)
                shader->setMaterialSpecular(material->Ks[0], material->Ks[1],
                        material->Ks[2]);
            if (material->Kd != NULL)
                shader->setDiffuseColor(material->Kd[0], material->Kd[1],
                        material->Kd[2]);
            if (material->d != NULL)
                shader->setMaterialAlpha(material->d);

        }

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, tex);

    }

	void BufferObject::draw(ShaderProgram * shader) {
        // Bind to the VAO that has all the information about the vertices
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        // Bind to the index VBO that has all the information about the
        // order of
        // the vertices
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboiId);

        shader->updateModelMatrix();
        // Draw the vertices
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_SHORT, 0);

        // Put everything back to default (deselect)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
        shader->setMaterialAlpha(1.0f);

    }
}
