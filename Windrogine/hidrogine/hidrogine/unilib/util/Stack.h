#ifndef unilib_util_Stack
#define unilib_util_Stack


#include <stdlib.h>
#include "Collection.h"



namespace unilib {

	//-------------------------------------------------
	// Stack's Class
	//-------------------------------------------------
	template <class E> class Stack : public Collection<E>{
		//-------------------------------------------------
		// Node's Class (private)
		//-------------------------------------------------
		private: class Node{
			public: E _value;
			public: Node * _next;

			// Node constructor
			public: Node(E value, Node * next){
				_value = value;
				_next = next;
			}; 
			
			public: virtual ~Node(){
			}; 
		};

		private: class StackIterator : public Iterator<E>
		{
			private: Node * _node;

			public: StackIterator(Node * n){
				_node = n;
			}

			public: virtual ~StackIterator(){
			}

			public: virtual E GetValue(){
				return _node!=NULL ? _node->_value : (E)NULL;
			}

			public: virtual bool IsValid(){
				return _node!=NULL;
			}
			
			public: virtual void Next(){
				_node = _node->_next;
			}
		};

		private: Node * _top;	// First node of Stack
		private: int _count;		// Number of nodes

		// Stack constructor  
		public: Stack<E>(){
			_top = NULL;
			_count = 0;
		};

		// Stack constructor  
		public: virtual ~Stack<E>(){
			Clear();
		};

		// Inserts an object at the top of the Stack.
		public: void Push(E value){
			_top = new Node(value,_top);
			++_count;
		};

		// Removes and returns the object at the top of the Stack.
		public: E Pop(){
			if(_top!=NULL){
				Node * next = _top->_next;
				E ret = _top->_value;
				delete _top;
				_top = next;
				--_count;
				return ret;
			}
			else
				return (E) NULL;
		}

		// Returns the object at the top of the Stack without removing it.
		public : E Peek(){
			return _top!=NULL? _top->_value : NULL;
		}

		// Gets the number of elements contained in the Stack.
		public: virtual int Size(){
			return _count;
		}

		// Removes all objects from the Stack.
		public: virtual Stack<E> * Clear( void (*func)(E,void *), void * args){
			Node * ptr = _top;
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

		public: virtual Stack<E> * Clear(){
			return Clear(NULL,NULL);
		}

		public: virtual bool IsEmpty(){
			return _top == NULL;
		}

		public: virtual int HashCode(){
			return 0;
		}

		public: virtual bool Contains(E e){
			return false;
		}

		public: virtual E* ToArray(){
			E * ret = new E[_count];
			int i = 0;
			for(Node * ptr = _top ; ptr!=NULL ; ptr = ptr->_next){
				ret[i++] = ptr->_value;
			}
			return ret;
		}


		public: virtual Iterator<E> * GetIterator(){
			return new StackIterator(_top);
		}

	};
}

#endif
