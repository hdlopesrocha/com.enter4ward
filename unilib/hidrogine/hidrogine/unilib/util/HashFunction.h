#ifndef unilib_util_HashFunction
#define unilib_util_HashFunction

#include <string>
#include <string.h>
#include <stdlib.h>
#include <iostream>
using namespace std;


namespace unilib {
	template <class Type> class HashFunction
	{
		// Returns the compare value.
		public: virtual long Calculate(Type obj1)=0;
	}; 

   	class HashString : public HashFunction<string>
    {
       // Returns the compare value.
        public: virtual long Calculate(string obj1){
        	long total =0;
        	int len = obj1.length();
        	for(int i=0; i < len ; ++i){
        		total+=obj1.at(i);
        	}
            return total;
        }
    };


}

#endif
