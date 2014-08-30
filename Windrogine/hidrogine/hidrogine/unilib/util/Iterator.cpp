#ifndef unilib_util_Iterator
#define unilib_util_Iterator



#include <stdlib.h>

namespace unilib {
	template <class E> class Iterator
	{
		public: virtual ~Iterator(){};
		public: virtual E GetValue()=0;
		public: virtual bool IsValid()=0;
		public: virtual void Next()=0;
	};

	template <class E> class ArrayIterator : public Iterator<E>
	{
		private: E * _array;
		private: int _index;
		private: int _size;

		public: ArrayIterator<E>(E * a, int s){
			_array = a;
			_size = s;
			_index = 0;
		}

		public: ~ArrayIterator<E>(){
			delete[] _array;
		}

		public: virtual E GetValue(){
			return _array[_index];
		}

		public: virtual bool IsValid(){
			return _index < _size;
		}
		
		public: virtual void Next(){
			++_index;
		}
	};
}

#define iterate(type,var,iterator) for(type var=iterator->GetValue(); it->IsValid(); it->Next(),var=it->GetValue())
#endif

