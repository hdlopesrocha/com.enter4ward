#include "Game.h"

using namespace std;

namespace hidrogine {
	void Game::looper(){
		useDefaultShader();
	    // Do a single loop (logic/render)
        update();
        program->update(camera);
        draw();
       	glutSwapBuffers();   // swapping image buffer for double buffering
		glFinish();
        // Let the CPU synchronize with the GPU if GPU is tagging behind
		glutPostRedisplay();
	}
	static Game * game;
	static void merda(){
		game->looper();
	}


	Game::Game(int w, int h) {
		char** argv = (char**) malloc(sizeof(char*)); 
		int argc =0;
		glutInit(&argc, argv);
		glutInitDisplayMode(GLUT_DOUBLE|GLUT_RGBA);
		glutInitWindowSize(w, h);
		glutInitWindowPosition(100, 100);
		glutCreateWindow("hidrogine");
		glutDisplayFunc(merda);

		printf("%s\n", glGetString(GL_VERSION));
		// Must be done after glut is initialized!
		GLenum res = glewInit();
		if (res != GLEW_OK) {
			fprintf(stderr, "Error: '%s'\n", glewGetErrorString(res));
			return ;
		}

		cout << "Glew Ok!" << endl;
        program = new ShaderProgram("vertex.glsl", "fragment.glsl");


		camera = new Camera(w,h);
        
        // Setup an XNA like background color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glViewport(0, 0, w, h);
        glEnable(GL_DEPTH_TEST);              
        glEnable(GL_CULL_FACE);
        // Enable transparency
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
		
    }

	void Game::start(Game * g){
		game = g;
		game->setup();
		glutMainLoop();
	}

    /**
     * Use default shader.
     */
    void Game::useDefaultShader() {
        program->use();
    }

	inline void Game::update(){}

	inline void Game::draw(){}

	inline void Game::setup(){}

}