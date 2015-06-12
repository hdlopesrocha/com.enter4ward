#ifndef unilib_util_TreeMap
#define unilib_util_TreeMap

#include <stdlib.h>
#include "Comparator.h"


namespace unilib {


	//-------------------------------------------------
	// TreeMap's Class
	//-------------------------------------------------
	class TreeMap {
		private: class Node;		
		private: Node * _root;
		public: void * put(void * value, Comparator * comparator);
		public: void * get(Comparator * comparator);
		public: void * remove(Comparator * comparator);
		public: void iterate(void (*func)(void*));
		public: TreeMap * clear(void (*func)(void*));
		public: TreeMap * clear();
	};
}

#endif 

