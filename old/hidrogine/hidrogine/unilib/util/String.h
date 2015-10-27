#ifndef unilib_util_String
#define unilib_util_String

#include <fstream>
#include <iostream>
#include <string>
#include <time.h>
#include <deque>
#include <sstream>
#include <algorithm>    
#include <stdlib.h>
#include "Comparator.h"
#include <functional>
#include <cstdlib>
#include <numeric>

using namespace std;

namespace unilib {
	class String {
	

	
		public: static deque<string> Split(string str,char sep);					
		public: static StringComparator * Comparator;
		public: static string Random( size_t length );
		public: static inline string ltrim(string s);
		public: static inline string rtrim(string s);
		public: static inline string Trim(string s);
	
	};

}

#endif

