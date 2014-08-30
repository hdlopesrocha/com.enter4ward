#ifndef unilib_util_Collection
#define unilib_util_Collection

#include <stdlib.h>
#include "Iterator.h"

namespace unilib {
	template <class E> class Collection
	{
		public: virtual Collection<E> * Clear(void (*func)(E, void*),void * args)=0;
		public: virtual Collection<E> * Clear(){
			return Clear(NULL,NULL);
		}
		public: virtual bool Contains(E o)=0;
		public: virtual int HashCode()=0;
		public: virtual bool IsEmpty()=0;
		public: virtual int Size()=0;
		public: virtual E* ToArray()=0;

		public: virtual Iterator<E> * GetIterator(){
			return new ArrayIterator<E>(ToArray(), Size());
		}
	};
}

#endif

