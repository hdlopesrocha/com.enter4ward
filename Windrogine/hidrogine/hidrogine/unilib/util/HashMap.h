#ifndef unilib_util_HashMap
#define unilib_util_HashMap

#include <stdlib.h>
#include "TreeMap.h"
#include "HashFunction.h"

namespace unilib {
	//-------------------------------------------------
	// Queue's Class
	//-------------------------------------------------
	template <class K, class V> class HashMap {
		private: TreeMap<K,V> ** array;
		private: HashFunction<K> * hashFunction;
		private: Comparator<K> * comparator;
		
		// Queue constructor  
		public: HashMap<K,V>(Comparator<K> * c, HashFunction<K> * h){
			array = new TreeMap<K,V>*[128];
			for(int i=0; i < 128 ; ++i){
				array[i] = new TreeMap<K,V>(c);
			}
				
			hashFunction = h;
			comparator = c;
		}

		public: virtual ~HashMap<K,V>(){

		}
	
		public: V * Put(K key, V * value){
			int index = (int)(hashFunction->Calculate(key)%128);
			return array[index]->Put(key,value);
		}

		public: V * Remove(K key){
			int index = (int)(hashFunction->Calculate(key)%128);
			return array[index]->Remove(key);
		}
		
		public: virtual long Size(){
			long size =0 ;
			for(int i=0; i < 128 ; ++i){
				size += array[i]->Size();
			}
			return size;
		}


		public: virtual V * Get(K key){
			int index = (int)(hashFunction->Calculate(key)%128);
			return array[index]->Get(key);
		}

	};
}

#endif 

