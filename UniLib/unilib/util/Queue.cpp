#ifndef unilib_util_Queue
#define unilib_util_Queue


#include <stdlib.h>
#include "Collection.cpp"
#include "Iterator.cpp"


namespace unilib {

	//-------------------------------------------------
	// Queue's Class
	//-------------------------------------------------
	template <class E> class Queue : public Collection<E>{
		//-------------------------------------------------
		// Node's Class (private)
		//-------------------------------------------------
		private : class Node{
			public : E _value;
			public : Node * _next;

			// Node constructor
			public : Node(E value){
				_value = value;
				_next = NULL;
			}; 

			// Gets the next node
			public:	virtual Node * Next()
	        {
	            return _next;
	        }

	        // Returns the value stored in this node
			public: virtual	E GetValue()
	        {
	            return _value;
	        }
		};

		private: class QueueIterator : public Iterator<E>
		{
			private: Node * _node;


			public: QueueIterator(Node * n){
				_node = n;
			}

			public: ~QueueIterator(){
			}

			public: virtual E GetValue(){
				return _node!=NULL ? _node->_value : NULL;
			}

			public: virtual bool IsValid(){
				return _node!=NULL;
			}
			
			public: virtual void Next(){
				_node = _node->_next;
			}

		};

		private: Node * _first;	// First node of Queue
		private: Node * _last;		// First node of Queue
		private: int _count;			// Number of nodes

		// Queue constructor  
		public: Queue<E>(){
			_first = _last = NULL;
			_count = 0;
		};

		// Adds an object at the top of the Queue.
		public: void Add(E value){
			if(_first== NULL)
				_first = _last = new Node(value);
			else {
				_last->_next  = new Node(value);
				_last = _last->_next;
			}
			++_count;
		};

		// Removes and returns the object at the top of the Queue.
		public: E Remove(){
			Node * next = _first->_next;
			E ret = _first->_value;
			delete _first;
			_first = next;
			--_count;
			return ret;
		}

		// Returns the object at the top of the Queue without removing it.
		public : E Peek(){
			return _first->info;
		}

		// Returns the object at the top of the Queue without removing it.
		public : E ValueAt(int i){
			Node * ptr = _first;
			while(ptr!=NULL && i!=0){
				--i;
				ptr=ptr->_next;
			}
			return ptr!= NULL? ptr->_value: NULL;
			
		}


		public: virtual Queue<E> * Clear( void (*func)(E,void *), void * args){
			Node * ptr = _first;
			Node * next;
			_count =0;
			while(ptr!=NULL){
				next = ptr->_next;
				if(func!=NULL)
					func(ptr->_value, args);
				delete ptr;
				ptr = next;
			}
			return this;
		}

		public: virtual Queue<E> * Clear(){
			return Clear(NULL,NULL);
		}


		// Creates an Iterator for this Queue
		public: virtual Iterator<E> * GetIterator(){
			return new QueueIterator(_first);
		}

		public: virtual bool Contains(E o){
			return false;
		}
		public: virtual int HashCode(){
			return 0;
		}
		
		public: virtual bool IsEmpty(){
			return _count==0;
		}

		public: virtual int Size(){
			return _count;
		}

		public: virtual E* ToArray(){
			return NULL;
		}


	};
}

#endif
