#ifndef unilib_util_LinkedList
#define unilib_util_LinkedList


#include <stdlib.h>
#include "Collection.cpp"
#include "Iterator.cpp"


namespace unilib {

	//-------------------------------------------------
	// LinkedList's Class
	//-------------------------------------------------
	template <class E> class LinkedList : public Collection<E>{
		//-------------------------------------------------
		// Node's Class (private)
		//-------------------------------------------------
		private : class Node {
			public : E _value;
			public : Node * _next, * _prev;

			// Node constructor
			public : Node(E value, Node * prev, Node * next){
				_value = value;
				_next = next;
				_prev = prev;
			}; 

		};

		private: class LinkedListIterator : public Iterator<E>
		{
			private: Node * _node;


			public: LinkedListIterator(Node * n){
				_node = n;
			}

			public: ~LinkedListIterator(){
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

		private: Node * _first;	// First node of LinkedList
		private: Node * _last;	// First node of LinkedList
		private: int _count;				// Number of nodes

		// LinkedList constructor  
		public: LinkedList<E>(){
			_first = _last = NULL;
			_count = 0;
		};

		// Adds an object at the end of the LinkedList.
		public: void Add(E value){
			if(_first== NULL)
				_first = _last = new Node(value, NULL,NULL);
			else {
				_last->_next  = new Node(value, _last,NULL);
				_last = _last->_next;
			}
			++_count;
		};


		// Adds an object at the index of the LinkedList.
		public : void Add(E value, int index){
			Node * ptr = NULL;
			if(index <= 0){
				_first = new Node(value, NULL,_first);
				if(_last == NULL)
					_last = _first;
			}
			else if(index >= _count){
				_last->_next  = new Node(value, _last,NULL);
				_last = _last->_next;
			}
			else {
				if(index<_count/2)	
					for(ptr=_first; index-->0 ;	ptr = ptr->_next);
				else
					for(ptr=_last; ++index<_count;	ptr = ptr->_prev);

				Node * node = new Node(value, ptr->_prev,ptr);
				ptr->_prev->_next = node;
				ptr->_prev = node;
			}
			++_count;
		};

		// Removes and returns the first object of the LinkedList.
		public : E Remove(){
			Node * next = _first->_next;
			E ret = _first->_value;
			delete _first;
			_first = next;
			if(_first!=NULL)
				_first->_prev = NULL;
			--_count;
			return ret;
		}



		// Removes and returns from the LinkedList the object placed in the position given by index.
		public : E Remove(int index){
			Node * ptr = NULL;
			E ret;
			if(index <= 0){
				ptr = _first;
				_first = _first->_next;
				_first->_prev = NULL;
				ret = ptr->_value;
				delete ptr;
			}
			else if(index >= _count-1){
				ptr = _last;
				_last = _last->_prev;
				_last->_next = NULL;
				ret = ptr->_value;
				delete ptr;
			}
			else {
				if(index<_count/2)	
					for(ptr=_first; index-->0 ;	ptr = ptr->_next);
				else
					for(ptr=_last; ++index<_count;	ptr = ptr->_prev);

				ptr->_prev->_next = ptr->_next;
				ptr->_next->_prev = ptr->_prev;
				ret = ptr->_value;
				delete ptr;
			}
			return ret;
		}


		// Returns the first object of the LinkedList without removing it.
		public: E Peek(){
			return _first->info;
		}

		// Gets the number of elements contained in the LinkedList.
		public: int Size(){
			return _count;
		}

		public: virtual LinkedList<E> * Clear( void (*func)(E,void *), void * args){
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


		// Creates an Iterator for this LinkedList
		public: virtual Iterator<E> * GetIterator(){
			return new LinkedListIterator(_first);
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

		public: virtual E* ToArray(){
			return NULL;
		}
	};
}

#endif 
