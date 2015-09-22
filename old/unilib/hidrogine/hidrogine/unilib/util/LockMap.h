#ifndef unilib_util_LockMap
#define unilib_util_LockMap

#include <stdlib.h>
#include "Collection.h"
#include "Stack.cpp"
#include "Map.cpp"
#include "../lang/Semaphore.cpp"
#include <mutex>

namespace unilib {
	template <class K, class V> class LockMap {
		private: class Node {
			public: V * value;
			public: Semaphore sem;
			
			public: Node(V * v){
				value = v;
			}

			
		};
		private: int _size;
		private: TreeMap<K,Node> * nodes;
		
		public: LockMap(Comparator<K> * comp){
			nodes = new TreeMap<K,Node>(comp);
			_size = 0;
		}
		
		public: void Put(K k, V* v){
			Node * n = nodes->Get(k);
			if(n==NULL){
				n = new Node(v);
				nodes->Put(k,n);
			}
			else {
				n->value=v;
			}
				++_size;
			n->sem.Notify();
			
		}
		
		
		public: V* Get(K k){
			Node * n = nodes->Get(k);
			if(n==NULL){
				n = new Node(NULL);
				nodes->Put(k,n);
				
			}
			

			n->sem.Wait();
			n->sem.Notify();
			return n->value;
			
		}
		
		public: int Size(){
			return _size;
		}
		
	};
}

#endif 

