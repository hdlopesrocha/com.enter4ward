#include "openGL.hpp"

namespace api {

	char specialKey[256];
	char normalKey[256];



	
	OpenGL::OpenGL(int argc, char **argv,ViewPort * viewPort,void(*initFunction)(),void(*drawFunction)(), string title, bool fullScreen){
		_argc = argc;
		_argv = argv;
		_drawFunction = drawFunction;
		_title = title;
		_initFunction = initFunction;
		_fullScreen = fullScreen;
		_viewPort = viewPort;

		memset(specialKey,KEY_UP,sizeof(specialKey));
		memset(normalKey,KEY_UP,sizeof(normalKey));
	}

	static void keyPressed (unsigned char key, int x, int y) {
		//usleep(100);
		if(normalKey[key]==KEY_UP)
			normalKey[key]=KEY_UP_DOWN;
		else
			normalKey[key] = KEY_DOWN;

	}

	static void keyUp (unsigned char key, int x, int y) {
		//usleep(100);
		normalKey[key] = KEY_UP;
	}

	static void skeyPressed (int key, int x, int y) {
		//usleep(100);

		if(specialKey[key]==KEY_UP)
			specialKey[key]=KEY_UP_DOWN;
		else
			specialKey[key] = KEY_DOWN;
	}


	static void skeyUp (int key, int x, int y) {
		//usleep(100);
		specialKey[key] = KEY_UP;
	}		


	void OpenGL::UpdateKeyboard(){
		for(int i=0 ; i< 256; ++i){
			if(specialKey[i]==KEY_UP_DOWN)
				specialKey[i]=KEY_DOWN;
			if(normalKey[i]==KEY_UP_DOWN)
				normalKey[i]=KEY_DOWN;

		}		
	}

	char OpenGL::getKeyState(int key){
		return normalKey[key];
	}


	char OpenGL::getSpecialKeyState(int key){
		return specialKey[key];
	}

	int openProgram(string vert, string frag){
		GLenum program; 
		GLenum vertex_shader;
		GLenum fragment_shader;
		// Create Shader And Program Objects
		program = glCreateProgram();
		vertex_shader = glCreateShader(GL_VERTEX_SHADER);
		fragment_shader = glCreateShader(GL_FRAGMENT_SHADER);
		

		SetupShader(vertex_shader,vert.c_str());
		SetupShader(fragment_shader,frag.c_str());

		 
		// Attach The Shader Objects To The Program Object
		glAttachShader(program, vertex_shader);
		glAttachShader(program, fragment_shader);
		 
		// Link The Program Object
		glLinkProgram(program);
		glValidateProgram(program);
	

		// Use The Program Object Instead Of Fixed Function OpenGL
		glUseProgram(program);
		return program;
	}

	void OpenGL::init(float near,float far){
		glutInit(&_argc, _argv);  
		glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_ALPHA | GLUT_DEPTH | GLUT_STENCIL);
		glutInitWindowSize(_viewPort->Width, _viewPort->Height);  
		glutInitWindowPosition(0, 0);  
		glutCreateWindow(_title.c_str());  
		glutDisplayFunc(_drawFunction);  
		glutIdleFunc(_drawFunction);


		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);		// This Will Clear The Background Color To Black
		glClearDepth(1.0);				// Enables Clearing Of The Depth Buffer
		glDepthFunc(GL_LESS);				// The Type Of Depth Test To Do
		glEnable(GL_DEPTH_TEST);			// Enables Depth Testing
		glShadeModel(GL_SMOOTH);			// Enables Smooth Color Shading
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();				// Reset The Projection Matrix
		gluPerspective(45.0f,(GLfloat)_viewPort->Width/(GLfloat)_viewPort->Height,near,far);	// Calculate The Aspect Ratio Of The Window
		glMatrixMode(GL_MODELVIEW);

		if(_fullScreen)
			glutFullScreen();
/*			glutIdleFunc(drawFunction);
		glutReshapeFunc(&ReSizeGLScene);
*/
		glutSetKeyRepeat(GLUT_KEY_REPEAT_OFF);
		glutKeyboardFunc(&(keyPressed));
		glutSpecialFunc(&(skeyPressed));
		glutKeyboardUpFunc(&(keyUp));
		glutSpecialUpFunc(&(skeyUp));

glEnable(GL_BLEND | GL_DEPTH_TEST);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);


	  	#ifdef DEBUG
			printf("%s\n",glGetString(GL_VERSION));
		#endif
		glewInit();
	//	InitShader();



		SDL_Init(SDL_INIT_EVERYTHING);

		// ============
		// SHADER STUFF
		// ============
		_toonProgram = openProgram("api/openGL/toon.vert","api/openGL/toon.frag");
		_toonLineProgram = openProgram("api/openGL/toon.vert","api/openGL/toonline.frag");
		_program = openProgram("api/openGL/vertex.x","api/openGL/pixel.x");
		_depthProgram = openProgram("api/openGL/depth.vert","api/openGL/depth.frag");
		_normalProgram = openProgram("api/openGL/normal.vert","api/openGL/normal.frag");
		_hidroProgram = openProgram("api/openGL/hidro.vert","api/openGL/hidro.frag");
_whiteProgram= openProgram("api/openGL/hidro.vert","api/openGL/white.frag");
			
		// ============
		// SHADOW STUFF
		// ============

		// The texture we're going to render to
		glGenTextures(1, &shadowTexture);
		// "Bind" the newly created texture : all future texture functions will modify this texture
		glBindTexture(GL_TEXTURE_2D, shadowTexture);
		// Poor filtering. Needed !
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		//NULL means reserve texture memory, but texels are undefined
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, _viewPort->Width, _viewPort->Height, 0, GL_BGRA, GL_UNSIGNED_BYTE, NULL);

		//-------------------------
		// The framebuffer, which regroups 0, 1, or more textures, and 0 or 1 depth buffer.
		glGenFramebuffersEXT(1, &shadowTarget);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, shadowTarget);
		glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, shadowTexture, 0); 
		//-------------------------
		glGenRenderbuffersEXT(1, &shadowDepth);
		glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, shadowDepth);
		glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT24, 2048,2048);
		//-------------------------
		//Attach depth buffer to FBO
		glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, shadowDepth);
		//-------------------------
		//Does the GPU support current FBO configuration?
		GLenum status;
		status = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT);
		switch(status)
		{
			case GL_FRAMEBUFFER_COMPLETE_EXT:
				//cout<<"good"<<endl;
			break;
			default:
				cout<<"shadow buffer error!"<<endl;
		}
		

		#ifdef DEBUG
		    cout << glGetString(GL_VERSION) << endl;
		    cout << glGetString(GL_VENDOR) << endl;
		    cout << glGetString(GL_RENDERER) << endl;
		    cout << glGetString(GL_SHADING_LANGUAGE_VERSION) << endl;
		    cout << glGetString(GL_EXTENSIONS) << endl;
		#endif


		_initFunction();
		glutMainLoop();  

	}


	SDL_Surface * loadSurface(const char * filename,int * tex){
		SDL_Surface *surface;
		GLenum textureFormat;
		GLuint texture;

		surface = IMG_Load(filename);
		if (!surface){
			return 0;
		}

		switch (surface->format->BytesPerPixel) {
			case 4:
				if (SDL_BYTEORDER == SDL_BIG_ENDIAN)
					textureFormat = GL_BGRA;
				else
					textureFormat = GL_RGBA;
				break;

			case 3:
				if (SDL_BYTEORDER == SDL_BIG_ENDIAN)
					textureFormat = GL_BGR;
				else
					textureFormat = GL_RGB;
				break;
		}
		glGenTextures(1, &texture);
		glBindTexture(GL_TEXTURE_2D, texture);
    	glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    	glTexParameteri( GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE );
    	//glTexImage2D(GL_TEXTURE_2D, 0, surface->format->BytesPerPixel, surface->w,surface->h, 0, textureFormat, GL_UNSIGNED_BYTE, surface->pixels);
		gluBuild2DMipmaps( GL_TEXTURE_2D, surface->format->BytesPerPixel, surface->w,surface->h,textureFormat, GL_UNSIGNED_BYTE, surface->pixels ); 


		*tex = texture;
		return surface;
	}


	unsigned int loadTexture(const char * filename){
		SDL_Surface *surface;
		GLenum textureFormat;
		GLuint texture;

		surface = IMG_Load(filename);
		if (!surface){
			return 0;
		}

		switch (surface->format->BytesPerPixel) {
			case 4:
				if (SDL_BYTEORDER == SDL_BIG_ENDIAN)
					textureFormat = GL_BGRA;
				else
					textureFormat = GL_RGBA;
				break;

			case 3:
				if (SDL_BYTEORDER == SDL_BIG_ENDIAN)
					textureFormat = GL_BGR;
				else
					textureFormat = GL_RGB;
				break;
		}
		glGenTextures(1, &texture);
		glBindTexture(GL_TEXTURE_2D, texture);
    	glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    	glTexParameteri( GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE );
    	//glTexImage2D(GL_TEXTURE_2D, 0, surface->format->BytesPerPixel, surface->w,surface->h, 0, textureFormat, GL_UNSIGNED_BYTE, surface->pixels);
		gluBuild2DMipmaps( GL_TEXTURE_2D, surface->format->BytesPerPixel, surface->w,surface->h,textureFormat, GL_UNSIGNED_BYTE, surface->pixels ); 


		SDL_FreeSurface(surface);
		return texture;
	}



float getHeight(SDL_Surface *surface, Vector2 coord, float radius)
{
    int bpp = surface->format->BytesPerPixel;

 	int cx = coord.X*(surface->w);
 	int cy = coord.Y*(surface->h);
 	float px = coord.X*surface->w -cx;
	float py = coord.Y*surface->h -cy;

	radius/=255;
	float h1 = getRed(surface, cx, cy)*radius;
	float h2 = getRed(surface, cx+1, cy)*radius;
	float h3 = getRed(surface, cx, cy+1)*radius;
	float h4 = getRed(surface, cx+1, cy+1)*radius;

	float b1 = h1;
	float b2 = h2-h1;
	float b3 = h3-h1;
	float b4 = h1-h2-h3+h4;

    return b1 + b2*px + b3*py + b4*px*py;
}


int getRed(SDL_Surface *surface, int x, int y)
{
    int bpp = surface->format->BytesPerPixel;
   
   	x = MathHelper::Clamp(x,0,surface->w-1);
   	y = MathHelper::Clamp(y,0,surface->h-1);
    
   
    /* Here p is the address to the pixel we want to retrieve */
    Uint8 *p = (Uint8 *)surface->pixels + y * surface->pitch + x * bpp;
   // cout << (int) p[0] << endl;
    return p[0];

}


Uint32 getPixel(SDL_Surface *surface, int x, int y)
{
    int bpp = surface->format->BytesPerPixel;
    /* Here p is the address to the pixel we want to retrieve */
    Uint8 *p = (Uint8 *)surface->pixels + y * surface->pitch + x * bpp;

    switch(bpp) {
    case 1:
        return *p;
        break;

    case 2:
        return *(Uint16 *)p;
        break;

    case 3:
        if(SDL_BYTEORDER == SDL_BIG_ENDIAN)
            return p[0] << 16 | p[1] << 8 | p[2];
        else
            return p[0] | p[1] << 8 | p[2] << 16;
        break;

    case 4:
        return *(Uint32 *)p;
        break;

    default:
        return 0;       /* shouldn't happen, but avoids warnings */
    }
}


	void SetupShader(int shader,const char * path){
		vector<char*> strings;
		vector<int> lens;
		string line;
		int i=0;

		ifstream file;

	    file.open (path);
	    if(file.is_open()){
		    while(!file.eof()) // To get all the lines.
		    {
				getline(file,line); // Saves the line in s.

			
				strings.push_back(strdup(line.c_str()));
				lens.push_back(line.size());
				++i;
			}
		}
		file.close();
		glShaderSource(shader,i,(const char**) &strings[0],&lens[0]);
		glCompileShader(shader);

		GLint validation;


		glGetShaderiv(shader, GL_COMPILE_STATUS, &validation);
		if(validation==GL_FALSE){
			cout << "ERROR: " << path << endl;
			char str[1024];
			GLsizei len;
			glGetShaderInfoLog(shader,1024, &len,  str);
			cout << str << endl;
		}
	}
}



