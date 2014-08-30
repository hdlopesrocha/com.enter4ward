#include "Utils.h"

using namespace std;
namespace hidrogine {
	string Utils::fileToString(string filename){
		ifstream ifs(filename.c_str());
		string content( (istreambuf_iterator<char>(ifs) ),
					(istreambuf_iterator<char>()    ) );
		ifs.close();
		return content;
	}
}