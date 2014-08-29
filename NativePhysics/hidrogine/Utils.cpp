#include <fstream>
#include <string>

using namespace std;

namespace hidrogine {

	class Utils {
		public: static string fileToString(string filename){
			ifstream ifs(filename.c_str());
			string content( (istreambuf_iterator<char>(ifs) ),
				       (istreambuf_iterator<char>()    ) );
			ifs.close();
			return content;
		}
	};
}
