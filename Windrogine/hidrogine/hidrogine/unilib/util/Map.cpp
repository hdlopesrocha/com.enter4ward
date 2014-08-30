#ifndef unilib_util_Map
#define unilib_util_Map


#include <stdlib.h>
#include "Iterator.cpp"
#include "Collection.cpp"


namespace unilib {
	template <class K,class V> class Entry
	{
		public: K Key;
		public: V Value;


		public: Entry<K,V>(){

		}
		
		public: virtual ~Entry<K,V>(){

		}
		
		public: Entry<K,V>(K key,V value){
			Key = key;
			Value = value;
		}


	}; 

	template <class K, class V> class Map
	{
		public: virtual Map<K,V> * Clear( void (*func)(Entry<K,V*> ,void*), void * args)=0;
		public: virtual Map<K,V> * Clear(){
			return Clear(NULL,NULL);
		}
		public: virtual ~Map(){
		}


		public: virtual bool ContainsKey(K key)=0;
		public: virtual bool ContainsValue(V * value)=0;
		public: virtual bool Equals(void * o)=0;
		public: virtual V * Get(K key)=0;
		public: virtual int HashCode()=0;
		public: virtual bool IsEmpty()=0;
		public: virtual V * Put(K key, V * value)=0;
		public: virtual void PutAll(Map<K,V> * m)=0;

		public: virtual V * Remove(K key)=0;
		public: virtual int Size()=0;
		public: virtual Collection<V*> * Values()=0;
		public: virtual V** ToArray()=0;
		public: virtual Iterator<Entry<K,V*> > * GetIterator()=0;

	};



}

#endif

