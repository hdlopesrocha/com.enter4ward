#ifndef unilib_util_String
#define unilib_util_String

#include <fstream>
#include <iostream>
#include <string>
#include <deque>
#include <sstream>
#include <algorithm>    // std::generate_n

#include <stdlib.h>


using namespace std;

namespace unilib {
	class String {
	

	
		public: static deque<string> Split(string str,char sep){
						
			deque<string> ret;
			
			stringstream ss(str);
			string item;
			while (getline(ss, item, sep)) {
				ret.push_back(item);
			}
			ss.clear();
			
			return ret;
		}
		
		
		public: static StringComparator * GetComparator(){
			static StringComparator * Comparator = new StringComparator();
			return Comparator;
		}		


		public: static string Random( size_t length )
		{
		static bool randInited = false;
			if(!randInited){
				srand(time(NULL));
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
public: static inline string ltrim(string s) {
        s.erase(s.begin(), find_if(s.begin(), s.end(), not1(ptr_fun<int, int>(isspace))));
        return s;
}

// trim from end
public: static inline string rtrim(string s) {
        s.erase(find_if(s.rbegin(), s.rend(), not1(ptr_fun<int, int>(isspace))).base(), s.end());
        return s;
}

// trim from both ends
public: static inline string Trim(string s) {
        return ltrim(rtrim(s));
}

	
	};

}

#endif

