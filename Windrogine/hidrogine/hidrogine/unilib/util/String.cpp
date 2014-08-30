#include "String.h"
using namespace std;

namespace unilib {

	deque<string> String::Split(string str,char sep){
						
			deque<string> ret;
			
			stringstream ss(str);
			string item;
			while (getline(ss, item, sep)) {
				ret.push_back(item);
			}
			ss.clear();
			
			return ret;
		}
		
		
		StringComparator * String::GetComparator(){
			static StringComparator * Comparator = new StringComparator();
			return Comparator;
		}		


		string String::Random( size_t length )
		{
			static bool randInited = false;
			if(!randInited){
				srand((unsigned)time(NULL));
				randInited = true;
			}
		
			auto randchar = []() -> char
			{
				const char charset[] =
				"0123456789"
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				"abcdefghijklmnopqrstuvwxyz";
				const size_t max_index = (sizeof(charset) - 1);
				return charset[ rand() % max_index ];
			};
			string str(length,0);
			generate_n( str.begin(), length, randchar );
			return str;
		}


		// trim from start
		string String::ltrim(string s) {
				s.erase(s.begin(), find_if(s.begin(), s.end(), not1(ptr_fun<int, int>(isspace))));
				return s;
		}

		// trim from end
		string String::rtrim(string s) {
				s.erase(find_if(s.rbegin(), s.rend(), not1(ptr_fun<int, int>(isspace))).base(), s.end());
				return s;
		}

		// trim from both ends
		string String::Trim(string s) {
				return ltrim(rtrim(s));
		}

	
	

}
