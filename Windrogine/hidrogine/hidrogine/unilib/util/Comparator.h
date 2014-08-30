#ifndef unilib_util_Comparator
#define unilib_util_Comparator

#include <string>
#include <string.h>
#include <stdlib.h>
#include <iostream>
using namespace std;


namespace unilib {
	template <class Type> class Comparator
	{
		// Returns the compare value.
		public: virtual int Compare(Type obj1, Type obj2)=0;
	}; 


   	class TimeComparator : public Comparator<time_t>
    {
       // Returns the compare value.
        public: virtual int Compare(time_t obj1, time_t obj2){
           // cout << "#" <<obj2-obj1 << endl; 
            return obj2==obj1 ? 0 : obj2<obj1 ? -1 : 1;
        }
    };

   	class StringComparator : public Comparator<string>
    {
       // Returns the compare value.
        public: virtual int Compare(string obj1, string obj2){
           // cout << "#" <<obj2-obj1 << endl; 
            return strcmp(obj2.c_str(),obj1.c_str());
        }
    };

    class FloatComparator : public Comparator<float>
    {
       // Returns the compare value.
        public: virtual int Compare(float obj1, float obj2){
            return obj2==obj1 ? 0 : obj2<obj1 ? -1 : 1;
        }
    };


    class DoubleComparator : public Comparator<double>
    {
       // Returns the compare value.
        public: virtual int Compare(double obj1, double obj2){
            return obj2==obj1 ? 0 : obj2<obj1 ? -1 : 1;
        }
    };


    class IntComparator : public Comparator<int>
    {
       // Returns the compare value.
        public: virtual int Compare(int obj1, int obj2){
            return obj2==obj1 ? 0 : obj2<obj1 ? -1 : 1;
        }
    };

}

#endif
