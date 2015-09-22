#ifndef unilib_util_Comparator
#define unilib_util_Comparator

#include <string>
#include <string.h>
#include <stdlib.h>
#include <iostream>
using namespace std;


namespace unilib {
	class Comparator
	{
		// Returns the compare value.
		// you should save the value to compare on the constructor
		// to avoid garbage, use the same instance and create set methods

		public: virtual int compare(void * obj1)=0;
	}; 




}

#endif
